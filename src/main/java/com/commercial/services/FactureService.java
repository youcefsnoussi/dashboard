package com.commercial.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.rc_consignation;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.article.repository.rc_consignationRepository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.static_data.mode_paiement;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;

@Service

public class FactureService {
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_dRepo;
	
	@Autowired
	rc_consignationRepository rccRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository priceArtRepo;
	
	public FactureService() {
		// TODO Auto-generated constructor stub
	}
	
	public String GetNumberForNewFacture(users user) {
		
		facture  last_fact = factRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_fact!=null) {
			
			last_number = last_fact.getNumero();
			
		}
		
		numerotation_by_year nby = new numerotation_by_year();
		
		String numero_fact = nby.return_num_facture(last_number, (long)user.getUnite().getIdentifiant());
		
		return numero_fact;
		
	}
	
	public facture InsertFactureDB(client_registreCommerce cltRc, String matricule, bon_livraison bl, mode_paiement mdp,
			users user, List<Map<String,Object>> detail) {
		
		get_time_date gtd = new get_time_date();
		
		String numFact = GetNumberForNewFacture(user);
		
		registre_commerce rc = cltRc.getRegistre_commerce();
		
		client clt = cltRc.getClient();
		
		facture fact = new facture(clt, rc, cltRc, gtd.get_date(), gtd.get_time(), 
				numFact, matricule, bl, mdp, user);
		
		factRepo.save(fact); factRepo.flush();
		
		double total_ht = 0, total_remise = 0, total_ht_net = 0, total_tva = 0, total_ttc = 0;
		
		for (Map<String, Object> map : detail) {
			
			article art = (article) map.get("article");
			
			double quantite = (double) map.get("quantite");
			
			double remise = (double) map.get("montant_remise");   
			
			rc_consignation rcc = rccRepo.getRcConsignation(rc, art);
			
			prixUnitaire_article_categoryClient priceArtCat = priceArtRepo.get_instance_by_art_and_catClient(art, rc.getCategory());
			
			double prix = (art.isConsignation()) ? rcc.getPrix_u_ht() 
					: priceArtRepo.get_prix_articles_by_CatClient(rc.getCategory(), art);
			
			double tva = (art.isConsignation()) ? 0 : priceArtCat.getTva().getTaux_tva();
			
			facture_detail fact_d = new facture_detail(fact, art, quantite, prix, tva, remise);
			
			fact_dRepo.save(fact_d); fact_dRepo.flush();
			
			total_ht += fact_d.getMontant_ht();
			
			total_remise += fact_d.getMontant_remise();
			
			total_ht_net += fact_d.getMontant_ht_net();
			
			total_tva += fact_d.getMontant_tva();
			
			total_ttc += fact_d.getMontant_ttc();
			
		}
		
		fact.setMontant_ht(total_ht);
		fact.setMontant_remise(total_remise);
		fact.setMontant_ht_net(total_ht_net);
		fact.setPourcentage_reduction((total_remise * 100) / total_ht);
		fact.setMontant_tva(total_tva);
		fact.setMontant_ttc(total_ttc);
		
		factRepo.save(fact); factRepo.flush();
		
		return fact;
		
	}
	
}
