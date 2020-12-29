package com.commercial.restController.article;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.magasin_articleRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.reduction_client_prixU_article;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.reduction_client_prixU_articleRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiement_factureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.Connection_peseur;
import com.commercial.functions.get_time_date;

@RestController
@SessionAttributes("user")

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
	
	@Autowired
	reduction_client_prixU_articleRepository reduxRepo;
	
	@Autowired
	magasin_articleRepository magRepo;
	
	@Autowired
	bon_livraisonRepository bon_lRepo;
	
	public factureRestController() {
		// TODO Auto-generated constructor stub
	}
	
	//----------------------------------------------------------------
	
	@RequestMapping(value="/get_matricules")
	public List<String> get_matricules_from_peuseurDB() throws IOException, ParseException{
		
		Connection_peseur con = new Connection_peseur();
		
		List <String> lm = con.get_matricule_from_peseur();
		
		return lm;
		
	}
	
	//----------------------------------------------------------------
	
	@RequestMapping(value="/ajax_get_client_by_code")
	public client get_client_by_code(
		@RequestParam("code") String code) throws IOException, ParseException{
		
		client clt = clientRepo.get_client_by_code(code);
		
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
		
		get_time_date gtd = new get_time_date();
		
		List <prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(clt.getCategory());
		
		//------------------------- get reduction if existe -----------------------
		
		for(int i=0;i<list_art.size();i++) {
			
			prixUnitaire_article_categoryClient pu = list_art.get(i);
			
			reduction_client_prixU_article red = reduxRepo.get_reduction_by_clt_art(clt, pu.getArticle(), gtd.get_date()); 
			
			if(red != null) {
				
				pu.setPrix(red.getNouveau_prix()); // naba3to b - bach ndetecti article li fih reduction o f client side nredo normal
				
				pu.setId((long) -1);
				
				list_art.set(i, pu);
				
			}
			
		}
		
		//--------------------------------------------------------------------------
		
		return list_art;
		
	}
	
	//------------------------------------------------------------------
	
	@RequestMapping(value="/ajax_get_art_by_rc_cat")
	public List<prixUnitaire_article_categoryClient> get_art_by_rc_cat(
		@RequestParam("id_rc_clt") long id_rc_clt) throws IOException, ParseException{
		
		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_rc_clt);
		
		get_time_date gtd = new get_time_date();
		
		List <prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(clt_rc.getRegistre_commerce().getCategory());
		
		//------------------------- get reduction if existe -----------------------
		
		
		
		for(int i=0;i<list_art.size();i++) {
			
			prixUnitaire_article_categoryClient pu = list_art.get(i);
			
			reduction_client_prixU_article red = reduxRepo.get_reduction_by_clt_art(clt_rc.getClient(), pu.getArticle(), gtd.get_date()); 
			
			if(red != null) {
				
				pu.setPrix(red.getNouveau_prix()); // naba3to b - bach ndetecti article li fih reduction o f client side nredo normal
				
				pu.setId((long) -1);
				
				list_art.set(i, pu);
				
			}
			
		}
		
		//--------------------------------------------------------------------------
		
		return list_art;
		
	}
	
	//----------------------------------------------------------------
	
	@RequestMapping(value="/ajax_get_magasin_by_art")
	public List<Magasin> get_magasin_by_article(
		@RequestParam("id_article") long id_article) throws IOException, ParseException{
		
		List<Magasin> list_mag = magRepo.get_magasin_by_article(artRepo.getOne(id_article));
		
		return list_mag;
	}
	
	//------------------------------------------------------------------
	
	@RequestMapping(value="/ajax_test_plafond")
	public Map<String, Integer> test_plafond(
		//@RequestParam("id_client") long id_client,
		@RequestParam("id_rc_clt") long id_relation_rc_client,
		@RequestParam("montant_ttc") double montant_ttc) throws IOException, ParseException{
		
		//JSONArray arr_obj = new JSONArray();
		
		HashMap<String, Integer> map = new HashMap<>();
		
		client_registreCommerce rc_clt = clt_rcRepo.getOne(id_relation_rc_client);
		
		
		client clt = rc_clt.getClient();
		
		List <bon_livraison> list_bl = bon_lRepo.get_bl_encours_by_clt(clt);
		
		double montant = 0;
		
		for(int j=0;j<list_bl.size();j++) {
			
			montant = montant + list_bl.get(j).getMontant_ttc();
			
		}
		
		double sold_encours = clt.getSold_encours();
		
		sold_encours = sold_encours + montant;
		
		//clt_rc.get(i).getRegistre_commerce().setSold_encours(sold_encours);
		
		//double balance_clt = clt.getSold_encours();
		
		if(sold_encours>clt.getPlafond()) {
			
			map.put("plafond_client", 1);
			
		}
		else {
			 
			map.put("plafond_client", 0);
			
		}
		
		registre_commerce rc = rc_clt.getRegistre_commerce();
		
		list_bl = bon_lRepo.get_bl_encours_by_rc(rc);
		
		montant = 0;
		
		for(int j=0;j<list_bl.size();j++) {
			
			montant = montant + list_bl.get(j).getMontant_ttc();
			
		}
		
		sold_encours = rc.getSold_encours();
		
		sold_encours = sold_encours + montant;
		
		System.out.println("SOLD RC -> "+sold_encours);
		
		//double balance_rc = rc.getSold_encours();
		
		//System.out.println("("+(balance_rc+montant_ttc)+")>"+rc.getPlafond());
		
		if(sold_encours>rc.getPlafond()) {
			
			map.put("plafond_rc", 1);
			
		}
		else {
			  
			map.put("plafond_rc", 0);
			
		}
		
		return map;
		
	}
	
	//------------------------------------------------------------------
	
	@RequestMapping(value="/get_notification")
	public List <facture> notification_facture_ready(
			@SessionAttribute("user") users user
			) throws IOException, ParseException{
		
		//System.out.println("user entred -> "+user.getUsername());
		
		List <facture> list_fct = new ArrayList<facture>();
		
		List <facture> ret = new ArrayList <facture>();
		
		if(user.getRole().getNom_role().equals("Admin")) {
			
			list_fct = factRepo.get_notifications_admin();
			
			ret = list_fct;
			
		}
		
		else if(user.getRole().getNom_role().equals("A.C.imprimer")) {
			
			list_fct = factRepo.get_notifications_admin();
			
			for(int i=0;i<list_fct.size();i++) {
				
				facture fct = list_fct.get(i);
				
				fct.setNotification(true);
				
				factRepo.save(fct);factRepo.flush();
				
			}
			
			ret = list_fct;
			
		}
		
		else {
			
			list_fct = factRepo.get_notification_by_user(user);
			
			for(int i=0;i<list_fct.size();i++) {
				
				facture fct = list_fct.get(i);
				
				fct.setNotification(true);
				
				factRepo.save(fct);factRepo.flush();
				
			}
			
			ret = list_fct;
			
		}
		
		return ret;
		
	}
	
	//_____________________________________ hedi ta3 REST Controller article mabid dertha hna _____________
	
	@RequestMapping(value="/get_prix_by_client")
	public Map<String, Double> get_prix_article_by_client(
			@RequestParam("id_client") long id_client,
			@RequestParam("id_article") long id_article
			) throws IOException, ParseException{
		
		double prix = 0;
		
		category_client cat_client = clientRepo.getOne(id_client).getCategory();
		
		article art = artRepo.getOne(id_article);
		
		prix = pu_a_ctRepo.get_prix_articles_by_CatClient(cat_client, art);
		
		Map<String, Double> ret = new HashMap<>();
		
		ret.put("prix", prix);
		
		return ret;
		
	}
	
}
