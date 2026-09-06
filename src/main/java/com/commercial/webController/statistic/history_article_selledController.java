package com.commercial.webController.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_interne_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.services.generate_Doc;

@Controller
@SessionAttributes("user")

public class history_article_selledController {
	
	@Autowired
	facture_detailRepository fact_detRepo;
	
	@Autowired
	facture_avoir_detailRepository fact_av_dRepo;
	
	@Autowired
	bon_livraison_facture_detailRepository bldRepo;
	
	@Autowired
	bon_transfert_interne_detailRepository btidRepo;
	
	@Autowired
	generate_Doc gd;
	
	@Autowired
	factureRepository factRepo;
	
	public history_article_selledController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/historic_ventes")
	public String history_vente_quantite(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/selled_article";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		List <Object[]> list1 = new ArrayList<Object[]>();
		
		List <Object[]> list2 = new ArrayList<Object[]>();
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
			list = fact_detRepo.get_quantite_sold_fact(gtd.get_date(), gtd.get_date());
			
			list.addAll(fact_av_dRepo.get_quantite_sold_fact_av(gtd.get_date(), gtd.get_date()));
			
			list1 = bldRepo.get_quantite_sold_val_bl(gtd.get_date(), gtd.get_date());
			
			list2 = btidRepo.get_quantite_sold_bon_transfert_interne(gtd.get_date(), gtd.get_date());
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list = fact_detRepo.get_quantite_sold_fact(conv.convertion_InputDate_to_MyDate(date_debut), 
														conv.convertion_InputDate_to_MyDate(date_fin)); 
			list.addAll(fact_av_dRepo.get_quantite_sold_fact_av(conv.convertion_InputDate_to_MyDate(date_debut), 
																conv.convertion_InputDate_to_MyDate(date_fin)));
			
			list1 = bldRepo.get_quantite_sold_val_bl(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
			
			list2 = btidRepo.get_quantite_sold_bon_transfert_interne(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
			
		}
		
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
		model.addAttribute("list1", list1);
		
		model.addAttribute("list2", list2);
		
		return ret;
		
	}
	
	//---------------------------------------------------------------------------------
	//ici
	@RequestMapping(value="/historic_ventes_val")
	public String history_vente_quantite_val(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/selled_article_val";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		List <Object[]> list1 = new ArrayList<Object[]>();
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
			list = fact_detRepo.get_quantite_sold_val_fact(gtd.get_date(), gtd.get_date());
			
			list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av(gtd.get_date(), gtd.get_date()));
			
			list1 = bldRepo.get_quantite_sold_val_bl(gtd.get_date(), gtd.get_date());
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list =  fact_detRepo.get_quantite_sold_val_fact(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
			list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin)));
			
			list1 = bldRepo.get_quantite_sold_val_bl(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
			
		}
		
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
		model.addAttribute("list1", list1);
		
		return ret;
		
	}
	
	@RequestMapping(value="/print_historic_ventes_facture")
	public String print_historic_ventes_facture(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_historic_ventes_facture(start, end);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	@RequestMapping(value="/print_historic_ventes_bls")
	public String print_historic_ventes_bls(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_historic_ventes_blse(start, end);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	@RequestMapping(value="/print_historic_ventes_transfert")
	public String print_historic_ventes_transfert(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_historic_ventes_transfert(start, end);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	@RequestMapping(value="/historic_ventes_val_categ")
	public String getHistoryquantityAndValByCategory(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/selled_article_val_categ";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		List <Object[]> list1 = new ArrayList<Object[]>();
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
			list = fact_detRepo.get_quantite_sold_val_fact_cat(gtd.get_date(), gtd.get_date());
			
			list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_categ(gtd.get_date(), gtd.get_date()));
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(gtd.get_date(), gtd.get_date());
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list =  fact_detRepo.get_quantite_sold_val_fact_cat(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
			list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin)));
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
//					conv.convertion_InputDate_to_MyDate(date_fin));
			
		}
		
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
//		model.addAttribute("list1", list1);
		
		return ret;
		
	}
	
	@RequestMapping(value="/historic_ventes_val_sous_categ")
	public String getHistoryquantityAndValBySousCategory(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/selled_article_val_sous_categ";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
//		List <Object[]> list1 = new ArrayList<Object[]>();
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
			list = fact_detRepo.get_quantite_sold_val_fact_sous_cat(gtd.get_date(), gtd.get_date());
			
			list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(gtd.get_date(), gtd.get_date()));
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(gtd.get_date(), gtd.get_date());
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list =  fact_detRepo.get_quantite_sold_val_fact_sous_cat(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
			list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin)));
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
//					conv.convertion_InputDate_to_MyDate(date_fin));
			
		}
		
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
//		model.addAttribute("list1", list1);
		
		return ret;
		
	}
	
	/****************************** FODHIL *******************************/
	
	@RequestMapping(value="/vente_client_categ_prod_blida")
	public String getvente_client_categ_prod_blida(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/selled_client_categ_prod_blida";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
//		List <Object[]> list1 = new ArrayList<Object[]>();
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
		//	list = fact_detRepo.get_quantite_client_category_produit_blida(gtd.get_date(), gtd.get_date());
			
		//	list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(gtd.get_date(), gtd.get_date()));
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(gtd.get_date(), gtd.get_date());
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list =  fact_detRepo.get_quantite_client_category_produit_blida1(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
		/*	list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin)));*/
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
//					conv.convertion_InputDate_to_MyDate(date_fin));
			
		}
		
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
//		model.addAttribute("list1", list1);
		
		return ret;
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value="/vente_client_categ_prod_alger")
	public String getvente_client_categ_prod_alger(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/selled_client_categ_prod_alger";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
//		List <Object[]> list1 = new ArrayList<Object[]>();
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
		//	list = fact_detRepo.get_quantite_client_category_produit_alger(gtd.get_date(), gtd.get_date());
			
		//	list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(gtd.get_date(), gtd.get_date()));
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(gtd.get_date(), gtd.get_date());
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list =  fact_detRepo.get_quantite_client_category_produit_alger1(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
		/*	list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin)));*/
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
//					conv.convertion_InputDate_to_MyDate(date_fin));
			
		}
		
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
//		model.addAttribute("list1", list1);
		
		return ret;
		
	}
	//////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/vente_client_categ_prod_hors_blida")
	public String getvente_client_categ_prod_hors_blida(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/selled_client_categ_prod_hors_blida";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
//		List <Object[]> list1 = new ArrayList<Object[]>();
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
		///	list = fact_detRepo.get_quantite_client_category_produit_hors_blida(gtd.get_date(), gtd.get_date());
			
		//	list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(gtd.get_date(), gtd.get_date()));
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(gtd.get_date(), gtd.get_date());
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list =  fact_detRepo.get_quantite_client_category_produit_hors_blida1(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
		/*	list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin)));*/
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
//					conv.convertion_InputDate_to_MyDate(date_fin));
			
		}
		
	
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
//		model.addAttribute("list1", list1);
		
		return ret;
		
	}

	
	
	
	
	/////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping(value="/vente_client_categ_prod_hors_blida_alger")
	public String getvente_client_categ_prod_hors_blida_alger(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/selled_client_categ_prod_hors_blida_alger";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
//		List <Object[]> list1 = new ArrayList<Object[]>();
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
		///	list = fact_detRepo.get_quantite_client_category_produit_hors_blida_alger(gtd.get_date(), gtd.get_date());
			
		//	list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(gtd.get_date(), gtd.get_date()));
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(gtd.get_date(), gtd.get_date());
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list =  fact_detRepo.get_quantite_client_category_produit_hors_blida_alger1(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin));
		/*	list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_sous_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
					conv.convertion_InputDate_to_MyDate(date_fin)));*/
			
//			list1 = bldRepo.get_quantite_sold_val_bl_categ(conv.convertion_InputDate_to_MyDate(date_debut), 
//					conv.convertion_InputDate_to_MyDate(date_fin));
			
		}
		
	
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
//		model.addAttribute("list1", list1);
		
		return ret;
		
	}

	
	
	
	
	/////////////////////////////////////////////////////////////////////////
	
		//////////////////////////////////////////////////////////////////////////////////
			
		@RequestMapping(value="/vente_client_par_category")
		public String vente_client_par_category(HttpServletRequest request,
		@SessionAttribute("user") users user,
		@RequestParam("start") String date_debut,
		@RequestParam("end") String date_fin,
		Model model){
		
		String ret = "statistic/vente_client_par_category.html";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		
		List <Object[]> list_final =new ArrayList<Object[]>();
		
		
		//List <Object[]> list1 = new ArrayList<Object[]>();
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
		
		date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		
		date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		//list = fact_detRepo.get_quantite_client_category_produit_hors_blida(gtd.get_date(), gtd.get_date());

			
		
		

		}
		else {
		
			
		System.out.println("debut vente_client_par_category");
		
			List <Object[]> list_client = new ArrayList<Object[]>();
		
		list_client = fact_detRepo.get_client_facture_periode(conv.convertion_InputDate_to_MyDate(date_debut), conv.convertion_InputDate_to_MyDate(date_fin));
		
		
		List <Object[]> list_client_farine_qte = 	fact_detRepo.get_qte_client_souscategorie1(conv.convertion_InputDate_to_MyDate(date_debut), conv.convertion_InputDate_to_MyDate(date_fin),"Farine");
		
		List <Object[]> list_client_semoule_qte = 	fact_detRepo.get_qte_client_souscategorie1(conv.convertion_InputDate_to_MyDate(date_debut), conv.convertion_InputDate_to_MyDate(date_fin),"Semoule");
		
		List <Object[]> list_client_Pates_qte = 	fact_detRepo.get_qte_client_souscategorie1(conv.convertion_InputDate_to_MyDate(date_debut), conv.convertion_InputDate_to_MyDate(date_fin),"Pates");
		
		List <Object[]> list_client_Couscous_qte = 	fact_detRepo.get_qte_client_souscategorie1(conv.convertion_InputDate_to_MyDate(date_debut), conv.convertion_InputDate_to_MyDate(date_fin),"Couscous");
		
		List <Object[]> list_client_Son_qte = 		fact_detRepo.get_qte_client_souscategorie1(conv.convertion_InputDate_to_MyDate(date_debut), conv.convertion_InputDate_to_MyDate(date_fin),"Son");
		
		
		
	
		
		
	       for (Object[] objArray : list_client) {
	            // Création d'un nouvel objet avec la nouvelle colonne ajoutée
	            Object[] modifiedObjArray = new Object[objArray.length + 10];
	            System.arraycopy(objArray, 0, modifiedObjArray, 0, objArray.length);
	           // modifiedObjArray[objArray.length] = 10;
	            // Ajout de l'objet modifié à la nouvelle liste
	            list_final.add(modifiedObjArray);
	        }
		
		
		for (int i = 0; i < list_final.size(); i++) {
			
		for (int j = 0; j < list_client_farine_qte.size(); j++) {
			
				if(list_client_farine_qte.get(j)[0].equals(list_final.get(i)[3]))
				{
					list_final.get(i)[4]=list_client_farine_qte.get(j)[1];
					list_final.get(i)[5]=list_client_farine_qte.get(j)[2];
					list_client_farine_qte.remove(j);
					break;
				}
			}
		//////////////////////////////////////////////////////////	
		for (int j = 0; j < list_client_semoule_qte.size(); j++) {
		
			if(list_client_semoule_qte.get(j)[0].equals(list_final.get(i)[3]))
			{
				list_final.get(i)[6]=list_client_semoule_qte.get(j)[1];
				list_final.get(i)[7]=list_client_semoule_qte.get(j)[2];
				list_client_semoule_qte.remove(j);
				break;
			}
		}
			///////////////////////////////////////////////	
		for (int j = 0; j < list_client_Pates_qte.size(); j++) {
		
			if(list_client_Pates_qte.get(j)[0].equals(list_final.get(i)[3]))
			{
				list_final.get(i)[8]=list_client_Pates_qte.get(j)[1];
				list_final.get(i)[9]=list_client_Pates_qte.get(j)[2];
				list_client_Pates_qte.remove(j);
				break;
			}
		}
	//////////////////////////////////////////////////////////		
		
		for (int j = 0; j < list_client_Couscous_qte.size(); j++) {
		
			if(list_client_Couscous_qte.get(j)[0].equals(list_final.get(i)[3]))
			{
				list_final.get(i)[10]=list_client_Couscous_qte.get(j)[1];
				list_final.get(i)[11]=list_client_Couscous_qte.get(j)[2];
				list_client_Couscous_qte.remove(j);
				break;
			}
		}
	//////////////////////////////////////////////////////////	
			
		for (int j = 0; j < list_client_Son_qte.size(); j++) {
		
			if(list_client_Son_qte.get(j)[0].equals(list_final.get(i)[3]))
			{
				list_final.get(i)[12]=list_client_Son_qte.get(j)[1];
				list_final.get(i)[13]=list_client_Son_qte.get(j)[2];
				list_client_Son_qte.remove(j);
				break;
			}
		}
	//////////////////////////////////////////////////////////	
		
			
			}
		
		
	/*	for (int j = 0; j < list_final.size(); j++) {
			
			for (int kk = 0; kk < list_final.get(j).length; kk++) {
			System.out.print(list_final.get(j)[kk]+" ");
		}
		System.out.println("");
		
		}*/
	
		
		
		
		
	
		
		
		
		
		}
		


		
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list_final);
		
		//model.addAttribute("list1", list1);
		System.out.println("fin  vente_client_par_category");
		
		return ret;
		
		}
		
		
		
		
		
		/////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	

	/****************************** FODHIL *******************************/
	
	
	
	
	
	
	
	
	
}
