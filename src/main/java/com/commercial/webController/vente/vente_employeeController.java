package com.commercial.webController.vente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.repository.MagasinRepository;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.emballage_produitRepository;
import com.commercial.entities.schema.article.repository.magasin_articleRepository;
import com.commercial.entities.schema.article.repository.pesage_produitRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.article.repository.produitRepository;
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.prof_cmd_bl_fact_client_rc_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detail_employeeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_employeeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.ConnectionGRHLogistique;
import com.commercial.functions.Connection_RH;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.generateQRcode;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.services.generate_Doc;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class vente_employeeController {
	
	@Autowired
	category_clientRepository cat_clientRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository pu_a_ctRepo;
	
	@Autowired
	track_operations trk;
	
	@Autowired
	Connection_RH con_rh;

	@Autowired
	ConnectionGRHLogistique con_logistique;
	
	//------------------------------------------------------------
	
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
	unite_mesureRepository umRepo;
	
	@Autowired
	magasin_articleRepository magRepo;
	
	@Autowired
	MagasinRepository magasinRepo;
	
	@Autowired
	bon_livraison_employeeRepository bleRepo;
	
	@Autowired
	bon_livraison_detail_employeeRepository ble_dRepo;
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	@Autowired
	clientRepository cltRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_detRepo;
	
	@Autowired
	mouvementRepository mvmRepo;
	
	@Autowired
	prof_cmd_bl_fact_client_rc_avoirRepository grpRepo;
	
	@Autowired
	mode_paiementRepository mdpRepo;
	
	//------------------------------------------------------------
	
	public vente_employeeController() {
		// TODO Auto-generated constructor stub
	}
	
	//--------------------------------------------------------------------
	
	@RequestMapping(value="/vente_employee")
	public String vente_employee(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/vente_employee";
		
		List< Map<String,String> > emp = new ArrayList< Map<String,String> >();
		
		if(Connection_RH.getconnection() != null) {
			
			emp =  Connection_RH.get_employee(user.getUnite());
			
		}
			
		for (Map<String, String> map : emp) {
			
			boolean vente = (bleRepo.test_if_it_got_bl(map.get("matricule")).isEmpty()) ? false : true;
			
			map.put("vente", Boolean.toString(vente));
			
		}
		
		model.addAttribute("list_emp", emp);
		
		model.addAttribute("articles", pu_a_ctRepo.get_articles_by_CatClient(cat_clientRepo.get_category_by_name("Personnel")));
		
		return ret;
		
	}
	
	//--------------------------------------------------------
	
	@RequestMapping(value="/vente_employee_grh")
	public String vente_employee_grh(HttpServletRequest request,
						 @RequestParam("id_ble") long id_ble,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/vente_employee_grh";
		
		bon_livraison_employee ble_rh = con_rh.get_ble_grh_id(id_ble);
		
		List <bon_livraison_detail_employee> ble_rh_d = con_rh.get_detail_ble_grh(id_ble);
		
		model.addAttribute("ble", ble_rh);
		
		model.addAttribute("ble_detail", ble_rh_d);
		
		return ret;
		
	}

	//--------------------------------------------------------

	@RequestMapping(value="/vente_employee_logistique")
	public String vente_employee_logistique(HttpServletRequest request,
					 @RequestParam("id_ble") long id_ble,
					 @SessionAttribute("user") users user,
					 Model model){
		
		String ret = "vente/vente_employee_logistique";
		
		bon_livraison_employee ble_log = con_logistique.get_ble_logistique_id(id_ble);
		
		List <bon_livraison_detail_employee> ble_log_d = con_logistique.get_detail_ble_logistique(id_ble);
		
		model.addAttribute("ble", ble_log);
		
		model.addAttribute("ble_detail", ble_log_d);
		
		return ret;
		
	}
	
	//__________________________________________POST____________________________________________________________________
	
	@RequestMapping(value="/new_commande_employee",method=RequestMethod.POST)
	public String new_commande_employee_Post(HttpServletRequest req,
			//@RequestParam("id_client") long id_client,
			@RequestParam("employee") String employee,
			@RequestParam("tva") double tva,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,
			//@RequestParam("pourc_reduction") double pourc_reduction,
			
			@RequestParam(name = "art", defaultValue = "0") long [] article,
			@RequestParam(name = "id_um", defaultValue = "0") long [] id_unite_mesure,
			@RequestParam(name = "id_magasin", defaultValue = "0") long [] id_magasin,
			@RequestParam(name = "montant_ht_art", defaultValue = "0") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam(name = "tva_art", defaultValue = "0") double [] tva_art,
			@RequestParam(name = "prix_unitaire", defaultValue = "0") double [] prix_u_ht,
			@RequestParam(name = "qte", defaultValue = "0") double [] quantite,
			@RequestParam(name = "tva_art", defaultValue = "0") double [] montant_tva_art,
			
			@SessionAttribute("user") users user){
		
		get_time_date gtd = new get_time_date();
		
		numerotation_by_year nby = new numerotation_by_year();
		
		bon_livraison_employee last_ble = bleRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_ble!=null) {
			
			last_number = last_ble.getNumero();
			
		}
		
		String numero = nby.return_num_BonSortie(last_number, user.getUnite().getId());
		
		String [] s = employee.split("/");
		
		String date = gtd.get_date(), time = gtd.get_time();
		
		//System.out.println("mat before =>"+s[0]);
		
		String matricule = s[0];
		
		//System.out.println("mat after =>"+matricule);
		
		String nom = s[1];
		
		String prenom = s[2];
		/*
		bon_livraison_employee ble = new bon_livraison_employee(matricule, nom, prenom, date, time, numero, "", null, user, 0, 
										montant_ht, montant_tva, montant_ttc, false);
		*/
		bon_livraison_employee ble = new bon_livraison_employee(matricule, nom, prenom, date, time, numero, user, null, null);
		
		bleRepo.save(ble); bleRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("bon_livraison_employee", "Creation commande pour employée", ble.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		for(int i=0;i<article.length;i++) {
			
			if(quantite[i]!=0) {
				/*
				bon_livraison_detail_employee ble_d = new bon_livraison_detail_employee(ble, artRepo.getOne(article[i]), quantite[i],
						prix_u_ht[i], montant_ht_art[i], (montant_ht_art[i]*(tva_art[i]/100)), tva_art[i], user, 
						umRepo.getOne(id_unite_mesure[i]), false, magasinRepo.getOne(id_magasin[i]));
				*/
				prixUnitaire_article_categoryClient pu_obj = 
						prix_u_art_catcRepo.get_prix_articles_by_CatClient_Object(cat_clientRepo.get_category_by_name("Personnel"), 
						artRepo.getOne(article[i]));
				
				bon_livraison_detail_employee ble_d = new bon_livraison_detail_employee(ble, artRepo.getOne(article[i]), quantite[i],
						pu_obj.getPrix(), pu_obj.getTva().getTaux_tva(), user, artRepo.getOne(article[i]).getUnite_mesure_vente(), 
						magasinRepo.getOne(id_magasin[i]));
				
				ble_dRepo.save(ble_d);ble_dRepo.flush();
				
			}
			
		}
		
		//-------------------- calcule total from detail ble and put it in BLE -------------
		
		List<Double[]> sum_details = ble_dRepo.get_sum_for_ble(ble);
		
		ble.setMontant_ht(sum_details.get(0)[0]); //sum_details[0]
		ble.setMontant_tva(sum_details.get(0)[1]);
		ble.setMontant_ttc(sum_details.get(0)[2]);
		
		bleRepo.save(ble); bleRepo.flush();
		
		return "redirect:/vente_employee?id_bl="+ble.getId()+"&num_bl="+ble.getNumero()+"&type=ble";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/new_commande_employee_grh",method=RequestMethod.POST)
	public String new_commande_employee_grh_Post(HttpServletRequest req,
			
			@RequestParam("id_bl_grh") long id_bl_grh,
			@RequestParam("employee") String employee,
			@RequestParam("card_number") String cardNumber,
			//@RequestParam("tva") double tva,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,
			//@RequestParam("pourc_reduction") double pourc_reduction,
			
			@RequestParam(name = "art", defaultValue = "0") long [] article,
			@RequestParam(name = "id_um", defaultValue = "0") long [] id_unite_mesure,
			//@RequestParam(name = "id_magasin", defaultValue = "0") long [] id_magasin,
			@RequestParam(name = "montant_ht_art", defaultValue = "0") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam(name = "tva_art", defaultValue = "0") double [] tva_art,
			@RequestParam(name = "prix_unitaire", defaultValue = "0") double [] prix_u_ht,
			@RequestParam(name = "qte", defaultValue = "0") double [] quantite,
			@RequestParam(name = "tva_art", defaultValue = "0") double [] montant_tva_art,
			
			@SessionAttribute("user") users user){
		
		get_time_date gtd = new get_time_date();
		
		numerotation_by_year nby = new numerotation_by_year();
		
		bon_livraison_employee last_ble = bleRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_ble!=null) {
			
			last_number = last_ble.getNumero();
			
		}
		
		String numero = nby.return_num_BonSortie(last_number, user.getUnite().getId());
		
		String [] s = employee.split("/");
		
		String date = gtd.get_date(), time = gtd.get_time();
		
		//System.out.println("mat before =>"+s[0]);
		
		String matricule = s[0];
		
		//System.out.println("mat after =>"+matricule);
		
		String nom = s[1];
		
		String prenom = s[2];
		/*
		bon_livraison_employee ble = new bon_livraison_employee(matricule, nom, prenom, date, time, numero, "", null, user, 0, 
										montant_ht, montant_tva, montant_ttc, false);
		*/
		bon_livraison_employee ble = new bon_livraison_employee
						(matricule, nom, prenom, date, time, numero, user, cardNumber, "Agro");
		
		bleRepo.save(ble); bleRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("bon_livraison_employee", "Creation commande pour employée", ble.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		for(int i=0;i<article.length;i++) {
			
			if(quantite[i]!=0) {
				/*
				bon_livraison_detail_employee ble_d = new bon_livraison_detail_employee(ble, artRepo.getOne(article[i]), quantite[i],
						prix_u_ht[i], montant_ht_art[i], (montant_ht_art[i]*(tva_art[i]/100)), tva_art[i], user, 
						umRepo.getOne(id_unite_mesure[i]), false, magasinRepo.getOne(id_magasin[i]));
				*/
				prixUnitaire_article_categoryClient pu_obj = 
						prix_u_art_catcRepo.get_prix_articles_by_CatClient_Object(cat_clientRepo.get_category_by_name("Personnel"), 
						artRepo.getOne(article[i]));
				
				bon_livraison_detail_employee ble_d = new bon_livraison_detail_employee(ble, artRepo.getOne(article[i]), quantite[i],
						pu_obj.getPrix(), pu_obj.getTva().getTaux_tva(), user, artRepo.getOne(article[i]).getUnite_mesure_vente(), 
						null);
				
				ble_dRepo.save(ble_d);ble_dRepo.flush();
				
			}
			
		}
		
		//-------------------- calcule total from detail ble and put it in BLE -------------
		
		List<Double[]> sum_details = ble_dRepo.get_sum_for_ble(ble);
		
		ble.setMontant_ht(sum_details.get(0)[0]); //sum_details[0]
		ble.setMontant_tva(sum_details.get(0)[1]);
		ble.setMontant_ttc(sum_details.get(0)[2]);
		
		bleRepo.save(ble); bleRepo.flush();
		
		con_rh.update_ble_grh(ble, id_bl_grh);
		
		return "redirect:/print_ble?id_bl="+ble.getId();
		
	}

	//-------------------------------------------------------------

	@RequestMapping(value="/new_commande_employee_logistique",method=RequestMethod.POST)
	public String new_commande_employee_logistique_Post(HttpServletRequest req,

			@RequestParam("id_bl_logistique") long id_bl_logistique,
			@RequestParam("employee") String employee,
			@RequestParam("card_number") String cardNumber,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,

			@RequestParam(name = "art", defaultValue = "0") long [] article,
			@RequestParam(name = "id_um", defaultValue = "0") long [] id_unite_mesure,
			@RequestParam(name = "montant_ht_art", defaultValue = "0") double [] montant_ht_art,
			@RequestParam(name = "tva_art", defaultValue = "0") double [] tva_art,
			@RequestParam(name = "prix_unitaire", defaultValue = "0") double [] prix_u_ht,
			@RequestParam(name = "qte", defaultValue = "0") double [] quantite,
			@RequestParam(name = "tva_art", defaultValue = "0") double [] montant_tva_art,

			@SessionAttribute("user") users user){

		get_time_date gtd = new get_time_date();

		numerotation_by_year nby = new numerotation_by_year();

		bon_livraison_employee last_ble = bleRepo.findFirst1ByOrderByNumeroDesc();

		String last_number = "";

		if(last_ble!=null) {
			
			last_number = last_ble.getNumero();
			
		}

		String numero = nby.return_num_BonSortie(last_number, user.getUnite().getId());

		String [] s = employee.split("/");

		String date = gtd.get_date(), time = gtd.get_time();

		String matricule = s[0];

		String nom = s[1];

		String prenom = s[2];

		bon_livraison_employee ble = new bon_livraison_employee
					(matricule, nom, prenom, date, time, numero, user, cardNumber , "Logistique");	

		bleRepo.save(ble); bleRepo.flush();

		trk.add_track("bon_livraison_employee", "Creation commande pour employée logistique", ble.getId(), user);

		for(int i=0;i<article.length;i++) {
			
			if(quantite[i]!=0) {
				prixUnitaire_article_categoryClient pu_obj = 
						prix_u_art_catcRepo.get_prix_articles_by_CatClient_Object(cat_clientRepo.get_category_by_name("Personnel"), 
						artRepo.getOne(article[i]));
				
				bon_livraison_detail_employee ble_d = new bon_livraison_detail_employee(ble, artRepo.getOne(article[i]), quantite[i],
						pu_obj.getPrix(), pu_obj.getTva().getTaux_tva(), user, artRepo.getOne(article[i]).getUnite_mesure_vente(), 
						null);

				ble_dRepo.save(ble_d);ble_dRepo.flush();
				
			}
			
		}

		List<Double[]> sum_details = ble_dRepo.get_sum_for_ble(ble);
		
		ble.setMontant_ht(sum_details.get(0)[0]);
		ble.setMontant_tva(sum_details.get(0)[1]);
		ble.setMontant_ttc(sum_details.get(0)[2]);
		
		bleRepo.save(ble); bleRepo.flush();

		con_logistique.update_ble_logistique(ble, id_bl_logistique);

		return "redirect:/print_ble?id_bl="+ble.getId();
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/print_ble")
	public String print_ble(HttpServletRequest request,
						 @RequestParam("id_bl") long id_ble,
						 @SessionAttribute("user") users user,
						 Model model){
		
		bon_livraison_employee ble = bleRepo.getOne(id_ble);
		
		String qr_code = generateQRcode.createQRcode(ble.getNumero(), "BL");
		
		String pdf = "";
		
		
		pdf = gd.generate_BLE(ble.getId(), qr_code);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	@RequestMapping(value="/print_etat_ble")
	public String print_etat_ble(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start = conv.convertion_InputDate_to_MyDate(start);
		
		end = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_vente_employee(start, end);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/bl_encours_employee")
	public String list_bls_encours_employee (HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/list_bl_employee";
		
		get_time_date gtd = new get_time_date();
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		if(start.equals("0") && end.equals("0")) {
			
			model.addAttribute("list_ble", bleRepo.date_between_ble(gtd.get_date(), gtd.get_date()));
			
			date_d = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_f = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		}
		else {
			
			date_d = conv.convertion_InputDate_to_MyDate(start);
			date_f = conv.convertion_InputDate_to_MyDate(end);
			
			model.addAttribute("list_ble", bleRepo.date_between_ble(date_d, date_f));
			
			date_d = start; date_f = end;
			
		}
		//model.addAttribute("bls", bon_lRepo.get_bl_encours());
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);


		
		
		//----------------------ROLE TEST---------------------------------
		
		boolean cancel_ble = false;
		
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getIds_banned().contains("cancel_ble")) 
		{ cancel_ble = true; }
		
		model.addAttribute("cancel_ble", cancel_ble);
		
		//----------------------------------------------------------------
		
		return ret;
		
	}
	
	//-----------------------------------------------------------------------------

	@RequestMapping(value="/bl_encours_employee_log")
	public String list_bls_encours_employee_2 (HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/list_bl_employee_log";
		
		get_time_date gtd = new get_time_date();
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		if(start.equals("0") && end.equals("0")) {
			
			model.addAttribute("list_ble", bleRepo.date_between_ble(gtd.get_date(), gtd.get_date()));
			
			date_d = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_f = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		}
		else {
			
			date_d = conv.convertion_InputDate_to_MyDate(start);
			date_f = conv.convertion_InputDate_to_MyDate(end);
			
			model.addAttribute("list_ble", bleRepo.date_between_ble_logistique(date_d, date_f));
			
			date_d = start; date_f = end;
			
		}
		//model.addAttribute("bls", bon_lRepo.get_bl_encours());
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);


		
		
		//----------------------ROLE TEST---------------------------------
		
		boolean cancel_ble = false;
		
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getIds_banned().contains("cancel_ble")) 
		{ cancel_ble = true; }
		
		model.addAttribute("cancel_ble", cancel_ble);
		
		//----------------------------------------------------------------
		
		return ret;
		
	}

	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/bl_encours_employee_grh")
	public String list_bls_encours_employee_grh (HttpServletRequest request,
						 @RequestParam(name="start", defaultValue="0") String start,
						 @RequestParam(name="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/list_bl_employee_grh";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		String date_d = (start.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : start; 
		
		String date_f = (end.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : end;
		
		model.addAttribute("list_ble", con_rh.get_ble_grh(date_d, date_f));
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		return ret;
		
	}
	
	//----------------------------------------------------------------------------- 

	@RequestMapping(value="/bl_encours_employee_logistique")
	public String list_bls_encours_employee_logistique (HttpServletRequest request,
					 @RequestParam(name="start", defaultValue="0") String start,
					 @RequestParam(name="end", defaultValue="0") String end,
					 @SessionAttribute("user") users user,
					 Model model){
		
		String ret = "vente/list_bl_employee_logistique";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		String date_d = (start.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : start; 
		
		String date_f = (end.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : end;
		
		model.addAttribute("list_ble", con_logistique.get_ble_logistique(date_d, date_f));
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		return ret;
		
	}
	
	//-----------------------------------------------------------------------------
	
	
	//------------------------------------------------------------------------------
	
	@RequestMapping(value="info_ble")
	public String info_ble(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 
						 @RequestParam("id_ble") long id_ble,
						 
						 Model model){
		
		bon_livraison_employee ble = bleRepo.getOne(id_ble);
		
		model.addAttribute("ble", ble);
		
		model.addAttribute("detail_ble", ble_dRepo.get_ble_detail(ble));
		
		String ret = "vente/info_bl_employee";
		
		return ret;
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="validation_ble_detail")
	public String validation_ble_detail(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam(name = "card_number", defaultValue = "") String cardNumber,
						 
						 Model model){
		
		List<bon_livraison_detail_employee> ble_d = new ArrayList<>();
		
		ble_d = ble_dRepo.get_ble_details_by_card_number(cardNumber);
		
		model.addAttribute("ble_d", ble_d);
		
		model.addAttribute("cardNumber", (cardNumber!=null) ? cardNumber : "");
		
		String ret = "vente/validation_ble_detail";
		
		return ret;
		
	}
	
	//-------------------------------------------------------------------------------
	
	@RequestMapping(value="facture_ble")
	public String facture_ble(HttpServletRequest request,
					 @SessionAttribute("user") users user,
					 @RequestParam("start") String start,
					 @RequestParam("end") String end,
					 @RequestParam(name = "type_rc", defaultValue = "Agro") String type_rc,
					 Model model){
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		date_d = conv.convertion_InputDate_to_MyDate(start);
		date_f = conv.convertion_InputDate_to_MyDate(end);
		
		List<Object[]> sample_all_detail_ble = ble_dRepo.get_cumule_facture_ble(date_d, date_f, type_rc);
		
		List<Map<String, Object>> ret_all_details_ble = new ArrayList<Map<String, Object>>();
		
		List<Object []> total = ble_dRepo.get_total_facture_ble(date_d, date_f, type_rc);
		
		double montant_ht = 0, montant_tva = 0, montant_ttc = 0;
		
		if(!total.isEmpty() && total.get(0) != null) {
			Object[] totals = total.get(0);
			montant_ht = (totals[0] != null) ? ((Number)totals[0]).doubleValue() : 0;
			montant_tva = (totals[1] != null) ? ((Number)totals[1]).doubleValue() : 0;
			montant_ttc = (totals[2] != null) ? ((Number)totals[2]).doubleValue() : 0;
		}
		
		for(int i=0; i<sample_all_detail_ble.size(); i++) {
			
			Object[] obj = sample_all_detail_ble.get(i);
			
			Map<String,Object> info = new  HashMap<String, Object>();
			
			info.put("prix_u_ht",(double)obj[0]);
			info.put("tva",(double)obj[1]);
			info.put("article",artRepo.getOne((long)obj[2]));
			//info.put("magasin",magasinRepo.getOne((long)obj[3]));
			info.put("unite_mesure",umRepo.getOne((long)obj[4]));
			info.put("quantite",(double)obj[5]);
			info.put("montant_ht",(double)obj[6]);
			info.put("montant_tva",(double)obj[7]);
			info.put("montant_ttc",(double)obj[8]);
			/*
			montant_ht += (double)obj[6];
			montant_tva += (double)obj[7];
			montant_ttc += (double)obj[8];
			*/
			ret_all_details_ble.add(info);
			
		}
		
		model.addAttribute("cumule_ble", ret_all_details_ble);
		
		model.addAttribute("montant_ht", montant_ht);
		
		model.addAttribute("montant_tva", montant_tva);
		
		model.addAttribute("montant_ttc", montant_ttc);
		
		List <client_registreCommerce> clt_rc = clt_rcRepo.RC_employee();
		
		model.addAttribute("rcs_clt", clt_rc);
		
		//date_d = start; date_f = end;
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		model.addAttribute("type_rc", type_rc);
		
		String ret = "vente/info_facture_employee";
		
		return ret;
		
	}
	
	//-------------------------------------------------------------------------------
	
	@RequestMapping(value="/facture_employee_post",method=RequestMethod.POST)
	public String facture_employee_post(HttpServletRequest req,
		@RequestParam("id_rel_rc_clt") long id_rc_clt,
		
		@RequestParam("date_d") String date_d,
		@RequestParam("date_f") String date_f,
		@RequestParam("type_rc") String type_rc,
		
		@SessionAttribute("user") users user){
		
		get_time_date gtd = new get_time_date();
		
		String today = gtd.get_date();
		
		String time = gtd.get_time();
		
		//-------------------- get details ------------------------------
		
		List<Object[]> sample_all_detail_ble = ble_dRepo.get_cumule_facture_ble(date_d, date_f, type_rc);
		
		List<Map<String, Object>> ret_all_details_ble = new ArrayList<Map<String, Object>>();
		
		double montant_ht=0, montant_tva=0, montant_ttc=0;
		
		for(int i=0; i<sample_all_detail_ble.size(); i++) {
			
			Object[] obj = sample_all_detail_ble.get(i);
			
			Map<String,Object> info = new  HashMap<String, Object>();
			
			info.put("prix_u_ht",(double)obj[0]);
			info.put("tva",(double)obj[1]);
			info.put("article",artRepo.getOne((long)obj[2]));
			//info.put("magasin",magasinRepo.getOne((long)obj[3]));
			info.put("unite_mesure",umRepo.getOne((long)obj[4]));
			info.put("quantite",(double)obj[5]);
			info.put("montant_ht",(double)obj[6]);
			info.put("montant_tva",(double)obj[7]);
			info.put("montant_ttc",(double)obj[8]);
			
			montant_ht += (double)obj[6];
			montant_tva += (double)obj[7];
			montant_ttc += (double)obj[8];
			
			ret_all_details_ble.add(info);
			
		}
		
		//------------------------------------------------------
		
		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_rc_clt);
		
		//-------------- update sold client
		
		client clt = clt_rc.getClient();
		
		double sold_encours_clt = clt.getSold_encours();
		
		double new_sold_clt = sold_encours_clt + montant_ttc;
		
		clt.setSold_encours(new_sold_clt);
		
		cltRepo.save(clt);cltRepo.flush();
		
		//-------------- update sold RC
		
		registre_commerce rc = clt_rc.getRegistre_commerce();
		
		double sold_encours_rc = rc.getSold_encours();
		
		double new_sold_rc = sold_encours_rc + montant_ttc;
		
		rc.setSold_encours(new_sold_rc);
		
		rcRepo.save(rc);rcRepo.flush();
		
		//-------------- update sold RC_client relationship
		
		double sold_encours = clt_rc.getMontant_actuel();
		
		double new_sold = sold_encours + montant_ttc;
		
		clt_rc.setMontant_actuel(new_sold);
		
		clt_rcRepo.save(clt_rc);clt_rcRepo.flush();
		
		//--------------------------------------insert to facture table ------
		
		facture  last_fact = factRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_fact!=null) {
			
			last_number = last_fact.getNumero();
			
		}
		
		numerotation_by_year nby = new numerotation_by_year();
		
		String numero_fact = nby.return_num_facture(last_number, user.getUnite().getId());
		
		facture fact = new facture(clt, rc, clt_rc, today, time, numero_fact, 
				montant_ht, 0, " ", montant_ttc, montant_tva, 0, montant_ht, "", null, rc.getMode_paiement(),
				user, false,  montant_ttc, false, false, 0);
		
		
		factRepo.save(fact);factRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("facture", "creation facture apres validation BL Employé", fact.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		//List<bon_livraison_detail> bld_list = bon_l_dRepo.get_bl_detail(bl);
		
		for(int i=0;i<ret_all_details_ble.size();i++) {
			
			//bon_livraison_detail bld = bld_list.get(i);
			
			facture_detail fct_d = new facture_detail(fact, (article)ret_all_details_ble.get(i).get("article"), 
					(double)ret_all_details_ble.get(i).get("quantite"), 
					(double)ret_all_details_ble.get(i).get("prix_u_ht"), 
					(double)ret_all_details_ble.get(i).get("montant_ht"), 
					(double)ret_all_details_ble.get(i).get("tva"), 
					(double)ret_all_details_ble.get(i).get("montant_tva"), 
					(double)ret_all_details_ble.get(i).get("montant_ttc"), 0, 0, 
					(double)ret_all_details_ble.get(i).get("montant_ht"),  
					(unite_mesure)ret_all_details_ble.get(i).get("unite_mesure"));
			
			fact_detRepo.save(fct_d);fact_detRepo.flush();
			
		}
		
		//------------------------------------------------ insert into mouvement table
		
		mouvement mvm = new mouvement(clt, rc, montant_ttc, "Facture", fact.getId(), gtd.get_date(), gtd.get_time(), "", 
					sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
		
		mvmRepo.save(mvm);mvmRepo.flush();
		
		//------------------------------------------------ update facture to table regroupment
		
		prof_cmd_bl_fact_client_rc_avoir grp = new prof_cmd_bl_fact_client_rc_avoir(clt_rc, null, null, null, fact, null);
		
		grp.setFacture(fact);
		
		grpRepo.save(grp); grpRepo.flush();
		
		//---------------------------------------------- update bs sortie emp to factured------------------------------
		
		List<bon_livraison_employee> bls_emp = bleRepo.get_ble_factured(date_d, date_f, type_rc);
		
		for(int i=0;i<bls_emp.size();i++) {
			
			bon_livraison_employee ble = bls_emp.get(i);
			
			ble.setFacture(fact);
			ble.setFactured(true);
			ble.setEtat_livraison(1);
			
			bleRepo.save(ble); bleRepo.flush();
			
		}
		
		//-------------------------------------------------------------------------------------------------------------
		
		//------------------------------------------------ Create QR Code img
		
		
		//------------------------------------------------ prepare and create PDF fact
		
		
		//------------------------------------------------ END
		
		return "redirect:/print_fact?id_fact="+fact.getId();
		
	}
	
	//-------------------------------------------------------------------------------
}
