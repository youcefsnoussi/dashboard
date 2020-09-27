package com.commercial.restController.article;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiement_factureRepository;
import com.commercial.functions.get_time_date;

@RestController

public class factureRestController {
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	clientRepository clientRepo;
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	@Autowired
	paiementRepository payRepo;
	
	@Autowired
	paiement_factureRepository pay_factRepo;
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository pu_a_ctRepo;
	
	public factureRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/ajax_get_client_by_code")
	public client get_client_by_code(
		@RequestParam("code") String code) throws IOException, ParseException{
		
		client clt = clientRepo.get_client_by_code(code);
		
		System.out.println(clt.getSold_encours());
		
		return clt;
	}
	
	//----------------------------------------------------------------
	
	@RequestMapping(value="/ajax_get_rc_by_client_fact")
	public List<client_registreCommerce> get_rc_by_client_fact(
		@RequestParam("id_client") long id_client) throws IOException, ParseException{
		
		get_time_date gtd = new get_time_date();
		
		client clt = clientRepo.getOne(id_client);
		
		List <client_registreCommerce> list_rc = clt_rcRepo.get_list_rc_by_client_for_facture(clt, gtd.get_date());
		
		return list_rc;
	}
	
	//------------------------------------------------------------------
	
	@RequestMapping(value="/ajax_get_art_by_client_cat")
	public List<prixUnitaire_article_categoryClient> get_art_by_client_cat(
		@RequestParam("id_client") long id_client) throws IOException, ParseException{
		
		client clt = clientRepo.getOne(id_client);
		
		List <prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(clt.getCategory());
		
		System.out.println(list_art);
		
		return list_art;
		
	}
	
	//------------------------------------------------------------------
	
	@RequestMapping(value="/ajax_test_plafond")
	public Map<String, Integer> test_plafond(
		@RequestParam("id_client") long id_client,
		@RequestParam("id_rc") long id_relation_rc_client,
		@RequestParam("montant_ttc") double montant_ttc) throws IOException, ParseException{
		
		//JSONArray arr_obj = new JSONArray();
		
		HashMap<String, Integer> map = new HashMap<>();
		
		client clt = clientRepo.getOne(id_client);
		
		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_relation_rc_client);
		
		//registre_commerce rc = rcRepo.getOne(id_rc);
		
		registre_commerce rc = clt_rc.getRegistre_commerce();
		
		double balance_clt = clt.getSold_encours();
		
		//System.out.println("("+(balance_clt+montant_ttc)+")>"+clt.getPlafond());
		
		if((balance_clt+montant_ttc)>clt.getPlafond()) {
			
			map.put("plafond_client", 1);
			
		}
		else {
			 
			map.put("plafond_client", 0);
			
		}
		
		double balance_rc = rc.getSold_encours();
		
		//System.out.println("("+(balance_rc+montant_ttc)+")>"+rc.getPlafond());
		
		if((balance_rc+montant_ttc)>rc.getPlafond()) {
			
			map.put("plafond_rc", 1);
			
		}
		else {
			  
			map.put("plafond_rc", 0);
			
		}
		
		return map;
		
	}
	
	//------------------------------------------------------------------
	
	@RequestMapping(value="/get_notification")
	public List <facture> notification_facture_ready() throws IOException, ParseException{
		
		List <facture> list_fct = factRepo.get_notification();
		
		for(int i=0;i<list_fct.size();i++) {
			
			facture fct = list_fct.get(i);
			
			fct.setNotification(true);
			
			factRepo.save(fct);factRepo.flush();
			
		}
		
		return list_fct;
		
	}
	
}
