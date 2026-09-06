package com.commercial.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.magasin_articleRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.client.gestion_palette;
import com.commercial.entities.schema.client.repository.HistoriquePaletteRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.gestion_paletteRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;

@Service
public class PaletteService {

	public PaletteService() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	clientRepository cltRepo;
	
	@Autowired
	HistoriquePaletteRepository histPalRepo;
	
	@Autowired
	bon_livraisonRepository blRepo;
	
	@Autowired
	bon_livraison_detailRepository bldRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository priceRepo;
	
	@Autowired
	magasin_articleRepository magArtRepo;
	
	@Autowired
	gestion_paletteRepository gpRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Get the list of clients with their palette balance for 2026.
	 * Palette out = SUM(facture_detail.quantite) for "Palette" article where facture.date >= '2026-01-01'
	 * Palette returned = SUM(gestion_palette.quantity) by client_rc
	 * Balance = out - returned
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getClientsPaletteData() {
		
		String sql = "SELECT rc.numero_rc, rc.nom || ' ' || rc.prenom as rc_name, "
				+ "COALESCE(SUM(fd.quantite), 0) as total_out "
				+ "FROM proforma_cmd_bl_fact.facture_detail fd "
				+ "JOIN proforma_cmd_bl_fact.facture f ON fd.facture = f.id "
				+ "JOIN article.article a ON fd.article = a.id "
				+ "JOIN client.registre_commerce rc ON f.registre_commerce = rc.id "
				+ "WHERE cast(f.date as date) >= '2026-01-01' AND a.libelle = 'Palette' "
				+ "GROUP BY rc.numero_rc, rc.nom, rc.prenom "
				+ "ORDER BY rc.nom, rc.prenom";
		
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> results = query.getResultList();
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();
			String numeroRc = (String) row[0];
			String rcName = (String) row[1];
			double totalOut = ((Number) row[2]).doubleValue();
			double totalReturned = gpRepo.getTotalReturnedByRc(numeroRc);
			double balance = totalOut - totalReturned;
			
			map.put("numero_rc", numeroRc);
			map.put("rc_name", rcName);
			map.put("total_out", totalOut);
			map.put("total_returned", totalReturned);
			map.put("balance", balance);
			
			data.add(map);
		}
		
		return data;
	}
	
	/**
	 * Get palette history for a specific RC: outgoing (from facture_details) and incoming (from gestion_palette)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPaletteHistory(String numeroRc) {
		
		// Outgoing palettes from factures
		String sqlOut = "SELECT f.date as op_date, fd.quantite as quantity, f.numero as ref "
				+ "FROM proforma_cmd_bl_fact.facture_detail fd "
				+ "JOIN proforma_cmd_bl_fact.facture f ON fd.facture = f.id "
				+ "JOIN article.article a ON fd.article = a.id "
				+ "JOIN client.registre_commerce rc ON f.registre_commerce = rc.id "
				+ "WHERE cast(f.date as date) >= '2026-01-01' AND a.libelle = 'Palette' AND rc.numero_rc = :numeroRc "
				+ "ORDER BY f.date DESC";
		
		Query queryOut = entityManager.createNativeQuery(sqlOut);
		queryOut.setParameter("numeroRc", numeroRc);
		List<Object[]> outResults = queryOut.getResultList();
		
		// Incoming palettes (returns)
		List<gestion_palette> returns = gpRepo.getHistoryByRc(numeroRc);
		
		List<Map<String, Object>> history = new ArrayList<>();
		
		// Add outgoing entries
		for (Object[] row : outResults) {
			Map<String, Object> map = new HashMap<>();
			map.put("date", (String) row[0]);
			map.put("quantity", ((Number) row[1]).doubleValue());
			map.put("type", "Sortie");
			map.put("reference", (String) row[2]);
			history.add(map);
		}
		
		// Add incoming entries (returns)
		for (gestion_palette gp : returns) {
			Map<String, Object> map = new HashMap<>();
			map.put("date", gp.getCreated_at());
			map.put("quantity", gp.getQuantity());
			map.put("type", "Retour");
			map.put("reference", "Retour Palette");
			history.add(map);
		}
		
		// Sort combined list by date descending
		history.sort((a, b) -> {
			String dateA = (String) a.get("date");
			String dateB = (String) b.get("date");
			if (dateA == null) dateA = "";
			if (dateB == null) dateB = "";
			return dateB.compareTo(dateA);
		});
		
		return history;
	}
	
	/**
	 * Record a palette return
	 */
	public gestion_palette recordReturn(String clientRc, String clientName, double quantity, Long userSessionId) {
		
		article paletteArt = artRepo.findByLibelle("Palette");
		Long articleId = (paletteArt != null) ? paletteArt.getId() : 0L;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String now = sdf.format(new Date());
		
		gestion_palette gp = new gestion_palette(clientRc, clientName, articleId, quantity, userSessionId, now);
		gpRepo.save(gp);
		gpRepo.flush();
		
		return gp;
	}
	
	public void addingPaletteToBL(bon_livraison bl) {
		
		List<bon_livraison_detail> listBLD = bldRepo.get_bl_detail(bl);
		
		double nbrPalette = 0;
		
		bon_livraison_detail detPalette = null;
		
		for (bon_livraison_detail bld : listBLD) {
			
			if(bld.getArticle().getLibelle().equals("Palette")) {
				
				detPalette = bld;
				
			}
			else {
				
				nbrPalette += bld.getQuantite() / bld.getArticle().getPesagePalette();
				
			}
			
		}
		
		if(detPalette==null) {
			
			article art = artRepo.findByLibelle("Palette");
			
			prixUnitaire_article_categoryClient price = priceRepo.get_prix_articles_by_CatClient_Object(bl.getRegistre_commerce().getCategory(), art);
			
			double mnt_ht = nbrPalette * price.getPrix(), mnt_tva = mnt_ht*(price.getTva().getTaux_tva()/100);
			
			Magasin mag = magArtRepo.get_magasin_by_article(art).get(0);
			
			bon_livraison_detail detP = new bon_livraison_detail(bl, art, nbrPalette, price.getPrix(), mnt_ht, mnt_tva, price.getTva().getTaux_tva(), 
								bl.getUsers(), art.getUnite_mesure_vente(), true, mag);
			
			bldRepo.save(detP); bldRepo.flush();
			
		}
		else {
			
			detPalette.setQuantite(nbrPalette);
			detPalette.setMontant_ht(nbrPalette*detPalette.getPrix_u_ht());
			detPalette.setMontant_tva((nbrPalette*detPalette.getPrix_u_ht())*(detPalette.getTva()/100));
			
			bldRepo.save(detPalette);bldRepo.flush();
		}
		
		//--------------- recalcule total BL ----------
		
		listBLD = bldRepo.get_bl_detail(bl);
		
		double total_ht = 0, total_tva = 0, total_ttc = 0;
		
		for (bon_livraison_detail bld : listBLD) {
			
			total_ht += bld.getMontant_ht();
			total_tva += bld.getMontant_tva();
			total_ttc += bld.getMontant_ht() + bld.getMontant_tva(); 
			
		}
		
		System.out.println("ht->"+total_ht+" / tva->"+total_tva+" / ttc->"+total_ttc);
		
		bl.setMontant_ht(total_ht);
		bl.setTva(total_tva);
		bl.setMontant_ttc(total_ttc);
		
		blRepo.save(bl); blRepo.flush();
		
	}
	/*
	public void paletteOut ( long nbrPalette, facture fact) {
		
		get_time_date gtd = new get_time_date();
		
		client clt = fact.getClient();
		
		long sold_p = clt.getSoldPalette();
		
		HistoriquePalette hp = new HistoriquePalette(gtd.get_date(), clt, "Sortie", sold_p, sold_p-nbrPalette, fact);
		
		histPalRepo.save(hp); histPalRepo.flush();
		
		clt.setSoldPalette(sold_p-nbrPalette);
		
		cltRepo.save(clt); cltRepo.flush();
		
	}
	
	public void paletteIN ( long nbrPalette, client clt) {
		
		get_time_date gtd = new get_time_date();
		
		long sold_p = clt.getSoldPalette();
		
		HistoriquePalette hp = new HistoriquePalette(gtd.get_date(), clt, "Retour", sold_p, sold_p+nbrPalette, null);
		
		histPalRepo.save(hp); histPalRepo.flush();
		
		clt.setSoldPalette(sold_p+nbrPalette);
		
		cltRepo.save(clt); cltRepo.flush();
		
	}
	
	public boolean testPalettePlafond(List<bon_livraison> bls, client clt, double nbrPCurrentBL) {
		
		long soldEncours = clt.getSoldPalette();
		
		double nbrPBLS = nbrPCurrentBL;
		
		for (bon_livraison bl : bls) {
			
			List<bon_livraison_detail> blds = bldRepo.get_bl_detail(bl);
			
			for (bon_livraison_detail bld : blds) {
				
				if(bld.getArticle().getLibelle().equals("Palette")) {
					
					nbrPBLS += bld.getQuantite();
					
				}
				
			}
			
		}
		
		boolean ret = false;
		
		System.out.println(soldEncours+" - "+nbrPBLS+" = "+(soldEncours - nbrPBLS));
		
		if( (soldEncours - nbrPBLS) < 0)
			ret = true;
		
		return ret;
		
	}
	
	public long getNbrPaletteFromBL(bon_livraison bl) {
		
		List<bon_livraison_detail> listBLD = bldRepo.get_bl_detail(bl);
		
		long nbrPalette = 0;
		
		for (bon_livraison_detail bld : listBLD) {
			
			if(!bld.getArticle().getLibelle().equals("Palette")) {
				
				nbrPalette += bld.getQuantite()/bld.getArticle().getPesagePalette();
				
			}
		}
		
		return nbrPalette;
		
	}
	
	public double getMontantPaletteFromInterface(long[] id_arts, double[] quantite, client clt ) {
		
		long nbrPalette = 0;
		
		for(int i=0; i< id_arts.length;i++) {
			
			if(id_arts[i]!=0 && quantite[i]!=0 && !artRepo.getOne(id_arts[i]).getLibelle().equals("Palette")) {
				
				nbrPalette += quantite[i] / artRepo.getOne(id_arts[i]).getPesagePalette();
				
			}
			
		}
		
		return nbrPalette * priceRepo.get_prix_articles_by_CatClient_Object(clt.getCategory(), artRepo.findByLibelle("Palette")).getPrix();
		
	}
	
	public long getNombrePaletteFromInterface(long[] id_arts, double[] quantite, client clt ) {
		
		long nbrPalette = 0;
		
		for(int i=0; i< id_arts.length;i++) {
			
			if(id_arts[i]!=0 && quantite[i]!=0 && !artRepo.getOne(id_arts[i]).getLibelle().equals("Palette")) {
				
				nbrPalette += quantite[i] / artRepo.getOne(id_arts[i]).getPesagePalette();
				
			}
			
		}
		
		return nbrPalette;
		
	}
	*/
}
