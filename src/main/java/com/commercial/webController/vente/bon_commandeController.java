package com.commercial.webController.vente;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.emballage_produitRepository;
import com.commercial.entities.schema.article.repository.pesage_produitRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.article.repository.produitRepository;
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.BonCommande;
import com.commercial.entities.schema.profoma_cmd_bl_fact.BonCommandeDetail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.BonCommandeDetailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.BonCommandeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commandeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commande_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;

@Controller
@SessionAttributes("user")

public class bon_commandeController {
	
	@Autowired
	banqueRepository banqueRepo;
	
	@Autowired
	type_reglementRepository type_rRepo;
	
	@Autowired
	category_clientRepository cat_clientRepo;
	
	@Autowired
	clientRepository clientRepo;
	
	@Autowired
	uniteRepository uniteRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	category_produitRepository catpRepo;
	
	@Autowired
	sous_category_produitRepository souscatpRepo;
	
	@Autowired
	emballage_produitRepository embRepo;
	
	@Autowired
	pesage_produitRepository pesRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository prix_u_art_catcRepo;
	
	@Autowired
	produitRepository prodRepo;
	
	@Autowired
	tva_Repository tvaRepo;
	
	@Autowired
	category_clientRepository catclientRepo;
	
	@Autowired
	unite_mesureRepository unite_mesureRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	mode_paiementRepository mode_payRepo;
	
	@Autowired
	unite_mesureRepository umRepo;
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	//---------------------------------------------
	
	@Autowired
	commandeRepository cmdRepo;
	
	@Autowired
	commande_detailRepository cmd_detRepo;
	
	@Autowired
	bon_livraisonRepository bon_lRepo;
	
	@Autowired
	bon_livraison_detailRepository bon_l_dRepo;
	
	@Autowired
	prof_cmd_bl_fact_client_rc_avoirRepository grpRepo;
	
	@Autowired
	BonCommandeRepository bon_cmdRepo;
	
	@Autowired
	BonCommandeDetailRepository bon_cmd_dRepo;
	
	public bon_commandeController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/bon_cmd")
	public String bon_cmd(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/bon_commande";
		
		model.addAttribute("clients", clientRepo.client_active_only());
		
		//model.addAttribute("articles", artRepo.findAll());
		
		//model.addAttribute("cat_client", cat_clientRepo.findAll());
		
		//model.addAttribute("unite", uniteRepo.findAll());
		/*
		String s = user.getRole().getIds_banned();
		
		if(s!=null && s.contains("no_add_client")) {
			
			ret = "403";
			
		}
		*/
		return ret;
		
	}
	
	//__________________________________________POST____________________________________________________________________
	
		@RequestMapping(value="/new_BonCommande_post",method=RequestMethod.POST)
		public String new_bon_commande(HttpServletRequest req,
				@RequestParam("id_rel_rc_clt") long id_relation_rc_clt,
				@RequestParam("date_debut") String date_d,
				@RequestParam("date_fin") String date_f,
				
				@RequestParam("art") long [] article,
				@RequestParam("id_um") long [] id_unite_mesure,
				@RequestParam("qte") double [] quantite,
				
				@SessionAttribute("user") users user){
				
				BonCommande last_bon_cmd = bon_cmdRepo.findFirst1ByOrderByIdDesc();
				
				String last_number = "";
				
				if(last_bon_cmd!=null) {
					
					last_number = last_bon_cmd.getNumero();
					
				}
				
				get_time_date gtd = new get_time_date();
				
				numerotation_by_year nby = new numerotation_by_year();
				
				String numero_bon_cmd = nby.return_num_BonCommande(last_number);
				
				String today = gtd.get_date();
				
				String time = gtd.get_time();
				
				client_registreCommerce clt_rc = clt_rcRepo.getOne(id_relation_rc_clt);
				
				convert_string_to_date_util ccs = new convert_string_to_date_util();
				
				String date_debut = ccs.convertion_InputDate_to_MyDate(date_d);
				
				String date_fin = ccs.convertion_InputDate_to_MyDate(date_f);
				
				BonCommande bon_cmd = new BonCommande(today, time, date_debut, date_fin, numero_bon_cmd, clt_rc, "", user);
				
				bon_cmdRepo.save(bon_cmd);
				bon_cmdRepo.flush();
				
				for(int i=0;i<article.length;i++) {
					
					if(quantite[i]!=0) {
						
						BonCommandeDetail bon_cmd_d = new BonCommandeDetail(bon_cmd, artRepo.getOne(article[i]), quantite[i]);
						
						bon_cmd_dRepo.save(bon_cmd_d);bon_cmd_dRepo.flush();
						
					}
					
				}
				
				//------------------------------------------------ insert table regroupment
				
				
				return "redirect:/bon_cmd";
			
		}
	
}
