package com.commercial.webController.vente;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
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
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.prof_cmd_bl_fact_client_rc_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.causes_facture_avoirRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.generateQRcode;
import com.commercial.functions.generate_Doc;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;

@Controller
@SessionAttributes("user")

public class list_factureController {
	
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
	causes_facture_avoirRepository causeRepo;
	
	public list_factureController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/list_fact")
	public String list_fact(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("date_debut") String date_debut,
						 @RequestParam("date_fin") String date_fin,
						 Model model){
		
		String ret = "vente/list_facture";
		
		get_time_date gtd = new get_time_date();
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
			model.addAttribute("list_facture", factRepo.today_facture(gtd.get_date()));
			
			date_d = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_f = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		}
		else if(date_debut.equals("1") && date_fin.equals("1")) {
			
			model.addAttribute("list_facture", factRepo.findAll());
			
		}
		else {

			try {
				
				if(date_debut.contains("/")) {
					
					model.addAttribute("list_facture", 
							factRepo.date_between_facture(conv.convertion_from_my_date(date_debut), conv.convertion_from_my_date(date_fin)));
					
					model.addAttribute("selected_year",date_debut.substring(6));
					
					date_d = date_debut;
					
					date_f = date_fin;
					
				}
				else {
					
					model.addAttribute("list_facture", 
							factRepo.date_between_facture(conv.convertion_from_InputDate(date_debut), conv.convertion_from_InputDate(date_fin)));
					
					date_d = date_debut;
					
					date_f = date_fin;
					
				}
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		model.addAttribute("years", factRepo.get_years_db());
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		return ret;
		
	}
	
	
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="info_fact")
	public String info_fact(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 
						 @RequestParam("id_fact") long id_facture,
						 
						 Model model){
		
		facture fact = factRepo.getOne(id_facture);
		
		model.addAttribute("facture", fact);
		
		model.addAttribute("detail_facture", fact_detRepo.get_facture_detail(fact));
		
		model.addAttribute("avoir","false");
		
		String ret = "vente/info_facture";
		
		return ret;
		
	}
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_fact")
	public String print_bl(HttpServletRequest request,
						 @RequestParam("id_fact") long id_fact,
						 @SessionAttribute("user") users user,
						 Model model){
		
		facture fact = factRepo.getOne(id_fact);
		
		String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		pdf = gd.generate_Fact(fact, qr_code);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="/fact_avoir")
	public String fact_avoir(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 
						 @RequestParam("id_fact") long id_facture,
						 
						 Model model){
		
		facture fact = factRepo.getOne(id_facture);
		
		prof_cmd_bl_fact_client_rc_avoir grp = grpRepo.get_relation_by_facture(fact);
		
		String ret = "vente/facture_avoir";
		
		if(grp.getFacture_avoir()==null) {
			
			model.addAttribute("facture", fact);
			
			model.addAttribute("causes", causeRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
			
			model.addAttribute("detail_facture", fact_detRepo.get_facture_detail(fact));
			
			//----------------------ROLE TEST---------------------------------
			
			if(user.getRole().getNom_role().equals("Admin") || 
					(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("facture_av"))) 
			{ ret = "vente/facture_avoir"; }
			else { ret = "403"; }
			
			//----------------------------------------------------------------
			
		}
		else {
			
			model.addAttribute("facture_avoir", grp.getFacture_avoir());
			
			model.addAttribute("avoir","true");
			
			model.addAttribute("detail_facture_avoir", fact_avoir_detRepo.get_facture_avoir_detail(grp.getFacture_avoir()));
			
			ret = "vente/info_facture_avoir";
			
		}
		
		return ret;
		
	}


	//__________________________________________POST____________________________________________________________________
	
	@RequestMapping(value="/facture_avoir_post",method=RequestMethod.POST)
	public String fact_avoir_post(HttpServletRequest req,
			@RequestParam("id_fact") long id_fact,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,
			@RequestParam("cause") long cause,
			
			@RequestParam("id_art") long [] article,
			@RequestParam("id_um") long [] id_unite_mesure,
			@RequestParam("montant_ht_art") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam("tva_art") double [] tva_art,
			@RequestParam("prix_unitaire") double [] prix_u_ht,
			@RequestParam("qte") double [] quantite,
			//@RequestParam("tva_art") double [] montant_tva_art,
			
			@SessionAttribute("user") users user){
			
			facture_avoir  last_fact_av = fact_avoirRepo.findFirst1ByOrderByIdDesc();
			
			String last_number = "";
			
			if(last_fact_av!=null) {
				
				last_number = last_fact_av.getNumero();
				
			}
			
			get_time_date gtd = new get_time_date();
			
			numerotation_by_year nby = new numerotation_by_year();
			
			String numero = nby.return_num_facture_avoir(last_number, user.getUnite().getId());
			
			String today = gtd.get_date();
			
			String time = gtd.get_time();
			
			//--------------- 
			
			facture fct = factRepo.getOne(id_fact);
			
			//-------------- update sold client
			
			client clt = fct.getClient();
			
			double sold_encours_clt = clt.getSold_encours();
			
			double new_sold_clt = sold_encours_clt - montant_ttc;
			
			clt.setSold_encours(new_sold_clt);
			
			clientRepo.save(clt);clientRepo.flush();
			
			//-------------- update sold RC
			
			registre_commerce rc = fct.getRegistre_commerce();
			
			double sold_encours_rc = rc.getSold_encours();
			
			double new_sold_rc = sold_encours_rc - montant_ttc;
			
			rc.setSold_encours(new_sold_rc);
			
			rcRepo.save(rc);rcRepo.flush();
			
			//--------------------------------------insert to facture avoir table ------
			
			facture_avoir fact_av = new facture_avoir(clt, rc, today, time, numero, montant_ht, montant_tva, montant_ttc, "", fct, user, "", 0, 
														causeRepo.getOne(cause));
			
			//new facture(client, registre_commerce, date, time, numero, montant_ht, tva, matricule_camion, montant_ttc, montant_tva, link_pdf, bon_livraison, mode_paiement, users, etat_sold, sold_rest)
			
			fact_avoirRepo.save(fact_av);fact_avoirRepo.flush();
			
			//------------------------------------------------- insert details facture table
			
			for(int i=0;i<article.length;i++) {
				
				//if(quantite[i]!=0) {
					
				facture_avoir_detail fact_avoir_d = new facture_avoir_detail(fact_av, artRepo.getOne(article[i]), quantite[i],
							prix_u_ht[i], montant_ht_art[i], tva_art[i], (montant_ht_art[i]*(tva_art[i]/100)), (montant_ht_art[i] + (montant_ht_art[i]*(tva_art[i]/100))) );
				
				fact_avoir_detRepo.save(fact_avoir_d);fact_avoir_detRepo.flush();  
					
				//}
				
			}
			
			//------------------------------------------------ insert table regroupment
			
			prof_cmd_bl_fact_client_rc_avoir grp = grpRepo.get_relation_by_facture(fct);
			
			grp.setFacture_avoir(fact_av);
			
			grpRepo.save(grp); grpRepo.flush();
			
			//------------------------------------------------ insert into mouvement table
			
			mouvement mvm = new mouvement(clt, rc, montant_ttc, "Facture Avoire", fact_av.getId(), gtd.get_date(), gtd.get_time(), "", sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
			
			mvmRepo.save(mvm);mvmRepo.flush();
			
			//------------------------------------------------ Create QR Code img
			
			
			//------------------------------------------------ prepare and create PDF fact
			
			
			//------------------------------------------------ END
			
			return "redirect:/fact_avoir?id_fact="+id_fact;
		
	}
		
	
}
