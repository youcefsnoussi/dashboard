package com.commercial.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.rc_consignation;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.article_consignation_relationRepository;
import com.commercial.entities.schema.article.repository.rc_consignationRepository;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.dynamic_data.mouvement_consignation;
import com.commercial.entities.schema.dynamic_data.repository.mouvement_consignationRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.functions.get_time_date;

@Service

public class ConsignationService {
	
	@Autowired
	bon_livraison_detailRepository bl_DRepo;
	
	@Autowired
	bon_livraison_facture_detailRepository blf_DRepo;
	
	@Autowired
	rc_consignationRepository rccRepo;
	
	@Autowired
	article_consignation_relationRepository art_c_rRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	facture_detailRepository fct_dRepo;
	
	@Autowired
	mouvement_consignationRepository mvm_cRepo;
	
	public ConsignationService() {
		// TODO Auto-generated constructor stub
	}
	
	public void consignationBL(bon_livraison bl, String id_arts_con, registre_commerce rc, article art, double quantite,
			Magasin magasin) {
		
		if(!rc.isConsignation()) return;
		
		String [] sp = id_arts_con.split(",");
		
		for (String s : sp) {
			
			Optional<rc_consignation> opt = Optional.ofNullable(rccRepo.getRcConsignation(rc, artRepo.getOne(Long.parseLong(s))));
			
			opt.ifPresent(rcc -> {
				
				Optional<Double> division = Optional.ofNullable(art_c_rRepo.getDivisioner(art, artRepo.getOne(Long.parseLong(s))));
				
				division.ifPresent(div -> {
					
					double price = rcc.getPrix_u_ht();
					
					double quant = quantite / division.get();
					/*
					double ht = quant*price;
					
					bon_livraison_detail bl_d = new bon_livraison_detail(bl, rcc.getArticle_consignation(), quant,
							price, ht, 0, 0, null, rcc.getArticle_consignation().getUnite_mesure_vente(), true, magasin);
					
					*/
					
					bon_livraison_detail bl_d = new bon_livraison_detail(bl, rcc.getArticle_consignation(), quant, price, 0, 0, 
							rcc.getArticle_consignation().getUnite_mesure_vente(), magasin);
					
					bl_d.setValidation(true);
					
					bl_DRepo.save(bl_d);bl_DRepo.flush();
					
				});
				
				
				
			});
			
		}
		
	}
	
	//--------------------------------------------------------------------------------------------
	
	public void consignationBLF(bon_livraison_facture blf, String id_arts_con, registre_commerce rc, article art, double quantite,
			Magasin magasin) {
		
		if(!rc.isConsignation()) return;
		
		String [] sp = id_arts_con.split(",");
		
		for (String s : sp) {
			
			Optional<rc_consignation> opt = Optional.ofNullable(rccRepo.getRcConsignation(rc, artRepo.getOne(Long.parseLong(s))));
			
			opt.ifPresent(rcc -> {
				
				Optional<Double> division = Optional.ofNullable(art_c_rRepo.getDivisioner(art, artRepo.getOne(Long.parseLong(s))));
				
				division.ifPresent(div -> {
					
					double price = rcc.getPrix_u_ht();
					
					double quant = quantite / division.get();
					/*
					double ht = quant*price;
					
					bon_livraison_detail bl_d = new bon_livraison_detail(bl, rcc.getArticle_consignation(), quant,
							price, ht, 0, 0, null, rcc.getArticle_consignation().getUnite_mesure_vente(), true, magasin);
					
					*/
					
					bon_livraison_facture_detail blf_d = new bon_livraison_facture_detail(blf, rcc.getArticle_consignation(), quant, price, 0, 0, 
							rcc.getArticle_consignation().getUnite_mesure_vente(), magasin);
					
					blf_d.setValidation(true);
					
					blf_DRepo.save(blf_d);blf_DRepo.flush();
					
				});
				
				
				
			});
			
		}
		
	}
	
	//----------------------------------------------<FACT CONSIGNATION>---------------//
	
	public void consignationFACT(facture fact, article art_consignation, registre_commerce rc, double quantite) {
		
		if(!rc.isConsignation()) return;
			
		Optional<rc_consignation> opt = Optional.ofNullable(rccRepo.getRcConsignation(rc, art_consignation));
		
		opt.ifPresent(rcc -> {
			
				double price = rcc.getPrix_u_ht();
				
				double ht = quantite*price;
				
				facture_detail fact_d = new facture_detail(fact, art_consignation, quantite, price, ht, 
						0, 0, ht, 0, 0, ht, rcc.getArticle_consignation().getUnite_mesure_vente());
				
				fct_dRepo.save(fact_d);fct_dRepo.flush();
				
				updatingSoldConsigantionAndHistory(quantite, ht, rcc, fact);
				
		});
			
	}
	
	public Map<String,Double> deConsignationFACT(facture fact, List<article> arts_consignation, registre_commerce rc, double quantite) {
		
		Map<String,Double> result = new HashMap<String, Double>();
		
		AtomicReference<Double> total_ht = new AtomicReference<Double>((double) 0);
		
		//double total_ht = 0;
		
		if(!rc.isConsignation()) return result;
		
		for (article art : arts_consignation) {
			
			Optional<rc_consignation> opt = Optional.ofNullable(rccRepo.getRcConsignation(rc, art));
			
			opt.ifPresent(rcc -> {
				
					double price = rcc.getPrix_u_ht();
					
					double ht = quantite * price;
					
					facture_detail fact_d = new facture_detail(fact, art, quantite, price, ht, 
							0, 0, ht, 0, 0, ht, rcc.getArticle_consignation().getUnite_mesure_vente());
					
					fct_dRepo.save(fact_d);fct_dRepo.flush();
					
					updatingSoldConsigantionAndHistory(quantite, ht, rcc, fact);
					
					total_ht.set(total_ht.get() + ht);
					
			});
			
		}
		
		result.put("total_ht", total_ht.get());
		result.put("total_tva", (double) 0);
		result.put("total_ttc", total_ht.get());
		
		return result;
		
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	private void updatingSoldConsigantionAndHistory(double montant_physic, double montant_valorise, rc_consignation rcc,
			facture fact) {
		
		get_time_date gtd = new get_time_date();
		
		String to = (montant_physic > 0) ? "Consignation" : "Déconsignation";
		
		mouvement_consignation mc = new mouvement_consignation(rcc.getRegistre_commerce(), rcc.getArticle_consignation(),
				gtd.get_date(), gtd.get_time(), montant_physic, montant_valorise, rcc.getSold_physique(), rcc.getSold_valorise(),
				to, fact);
		
		mvm_cRepo.save(mc); mvm_cRepo.flush();
		
		rcc.setSold_physique(rcc.getSold_physique() + montant_physic);

		rcc.setSold_valorise(rcc.getSold_valorise() + montant_valorise);
		
		rccRepo.save(rcc); rccRepo.flush();
		
	}
	
}
