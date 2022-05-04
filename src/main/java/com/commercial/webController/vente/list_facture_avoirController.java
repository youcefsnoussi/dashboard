package com.commercial.webController.vente;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
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
import com.commercial.services.FactureAvoirFromMultipleFactService;
import com.commercial.services.generate_Doc;

@Controller
@SessionAttributes("user")

public class list_facture_avoirController {
	
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
	
	//---------------------------------------------
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_detRepo;
	
	@Autowired
	facture_avoirRepository fact_avoirRepo;
	
	@Autowired
	facture_avoir_detailRepository fact_avoir_detRepo;
	
	@Autowired
	prof_cmd_bl_fact_client_rc_avoirRepository grpRepo;
	
	@Autowired
	mouvementRepository mvmRepo;
	
	@Autowired
	FactureAvoirFromMultipleFactService factAvServ;
	
	public list_facture_avoirController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/list_fact_avoir")
	public String list_fact_avoir(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam(name="date_debut", defaultValue="0") String date_debut,
						 @RequestParam(name="date_fin", defaultValue="0") String date_fin,
						 Model model) throws ParseException{
		
		String ret = "vente/list_facture_avoir";
		
		get_time_date gtd = new get_time_date();
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
			//model.addAttribute("list_facture_avoir", fact_avoirRepo.today_facture_avoir(gtd.get_date()));
			
			model.addAttribute("list_facture_avoir", factAvServ.get_fact_avoir_list(gtd.get_date(), gtd.get_date()));
			
			date_d = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_f = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else if(date_debut.equals("1") && date_fin.equals("1")) {
			
			model.addAttribute("list_facture_avoir", fact_avoirRepo.findAll());
			
		}
		else {
			
			try {
				
				//if(date_debut.contains("/")) {
					
					model.addAttribute("list_facture_avoir", factAvServ.get_fact_avoir_list(conv.convertion_InputDate_to_MyDate(date_debut),
							conv.convertion_InputDate_to_MyDate(date_fin)));
					
					model.addAttribute("selected_year",date_debut.substring(6));
					
					date_d = date_debut;
					
					date_f = date_fin;
					
				//}
				/*else {
					
					model.addAttribute("list_facture_avoir", 
							fact_avoirRepo.date_between_facture_avoir(conv.convertion_from_InputDate(date_debut), 
									conv.convertion_from_InputDate(date_fin)));
					
					date_d = date_debut;
					
					date_f = date_fin;
					
				} */
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		model.addAttribute("years", factRepo.get_years_db());
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		//model.addAttribute("list_facture", factRepo.today_facture(gtd.get_date()));
		
		//model.addAttribute("mode_paiements", mode_payRepo.findAll());
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
	
	
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="info_fact_avoir")
	public String info_fact_avoir(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 
						 @RequestParam("id_fact_a") long id_facture_avoir,
						 
						 Model model){
		
		facture_avoir fact_a = fact_avoirRepo.getOne(id_facture_avoir);
		
		model.addAttribute("facture_avoir", fact_a);
		
		model.addAttribute("detail_facture_avoir", fact_avoir_detRepo.get_facture_avoir_detail(fact_a));
		
		String ret = "vente/info_facture_avoir";
		
		return ret;
		
	}
	
	//--------------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_fact_av")
	public String print_bl(HttpServletRequest request,
						 @RequestParam("id_fact") long id_fact,
						 @SessionAttribute("user") users user,
						 Model model){
		
		facture_avoir fact_av = fact_avoirRepo.getOne(id_fact);
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		List<facture> lst_fct = factRepo.get_facts_by_fact_avoir(fact_av);
		
		String facts = "";
		
		for (facture fct : lst_fct) {
			
			facts += fct.getNumero()+", ";
			
		}
		
		String pdf = "";
		
		pdf = gd.generate_Fact_avoir(fact_av, facts);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//--------------------------------------------------------------------------------
	
}
