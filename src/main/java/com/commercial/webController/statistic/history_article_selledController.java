package com.commercial.webController.statistic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;

@Controller
@SessionAttributes("user")

public class history_article_selledController {
	
	@Autowired
	facture_detailRepository fact_detRepo;
	
	@Autowired
	facture_avoir_detailRepository fact_av_dRepo;
	
	@Autowired
	bon_livraison_facture_detailRepository bldRepo;
	
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
		
		//System.out.println("start = "+date_debut+" / end = "+date_fin);
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
			list = fact_detRepo.get_quantite_sold_fact(gtd.get_date(), gtd.get_date()); 
			list.addAll(fact_av_dRepo.get_quantite_sold_fact_av(gtd.get_date(), gtd.get_date()));
			
			date_debut = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_fin = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		else {
			
			list = fact_detRepo.get_quantite_sold_fact(conv.convertion_InputDate_to_MyDate(date_debut), 
														conv.convertion_InputDate_to_MyDate(date_fin)); 
			list.addAll(fact_av_dRepo.get_quantite_sold_fact_av(conv.convertion_InputDate_to_MyDate(date_debut), 
																conv.convertion_InputDate_to_MyDate(date_fin)));
			
		}
		
		model.addAttribute("start", date_debut);
		
		model.addAttribute("end", date_fin);
		
		model.addAttribute("list", list);
		
		
		return ret;
		
	}
	
	//---------------------------------------------------------------------------------
	
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
	
}
