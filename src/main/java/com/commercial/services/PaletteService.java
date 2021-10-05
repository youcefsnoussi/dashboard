package com.commercial.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.magasin_articleRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.client.HistoriquePalette;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.repository.HistoriquePaletteRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.functions.get_time_date;

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
	
	public boolean testPalettePlafond(List<bon_livraison> bls, client clt, long nbrPCurrentBL) {
		
		long soldEncours = clt.getSoldPalette();
		
		long nbrPBLS = nbrPCurrentBL;
		
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
	
}
