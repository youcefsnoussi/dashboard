package com.commercial.webController.statistic;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.sous_category_produit;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;

@Controller
@SessionAttributes("user")

public class dashboardController {
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_dRepo;
	
	@Autowired
	facture_avoirRepository fact_avRepo;
	
	@Autowired
	facture_avoir_detailRepository fact_av_dRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	category_produitRepository cat_pRepo;
	
	@Autowired
	sous_category_produitRepository scat_pRepo;
	
	public dashboardController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/dashboard")
	public String dashboard(HttpServletRequest request,
						 @RequestParam("nbr_days") String intervalle,
						 @RequestParam(name = "type_val", defaultValue = "1") int type_val,
						 @RequestParam(name = "list_category", defaultValue = "") long[] list_category,
						 @RequestParam(name = "test_category", defaultValue = "") String test_category,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		/*
		System.out.println("test cat -> "+test_category);
		
		System.out.println("size ->"+list_category.length);
		
		for (long l : list_category) {
			System.out.println("------> list_category ->"+l);
		}
		*/
		//model.addAttribute("list_rc", rcRepo.findAll());
		
		if(list_category.length==0) {
			
			List<category_produit> lcp = cat_pRepo.findAll();
			
			long[] tmp = new long [lcp.size()];
			
			for(int i=0; i< lcp.size();i++) {
				
				tmp[i] = lcp.get(i).getId();
				
			}
			
			list_category = tmp;
			
		}
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		LocalDate s, e;
		
		if(start.equals("0")) {
			
			s = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			
			e = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());;
			
			intervalle = "6";
			
		}
		else {
			
			s = LocalDate.parse(start);
			
			e = LocalDate.parse(end);
			
		}
		
		List <List<Object[]>> list = new ArrayList<List<Object[]>>();
		
		LocalDate tmp = s;
		
		List <String> categories  = new ArrayList<String>();
		
		List <String> weeks  = new ArrayList<String>();
		
		int k = 1;
		/*
		if(!intervalle.equals("m")) {
			
			e = LocalDate.parse(tmp.toString()).with(TemporalAdjusters.lastDayOfMonth());
			
		}
		*/
		while(tmp.isBefore(e)){
			
			//System.out.println("-period "+k+" = "+jump_date_start+" -> "+jump_date);
			
			LocalDate firstDayOfMonth = LocalDate.parse(tmp.toString());
			
			LocalDate lastDayOfMonth;
			
			if(intervalle.equals("m")) {
				
				lastDayOfMonth = LocalDate.parse(tmp.toString()).with(TemporalAdjusters.lastDayOfMonth());
				
			}
			else {
				
				//lastDayOfMonth = LocalDate.parse(tmp.toString()).plusWeeks(Long.parseLong(intervalle));
				lastDayOfMonth = LocalDate.parse(tmp.toString()).plusDays(Long.parseLong(intervalle)).isBefore(
						LocalDate.parse(tmp.toString()).with(TemporalAdjusters.lastDayOfMonth()).minusDays(3)) 
						? LocalDate.parse(tmp.toString()).plusDays(Long.parseLong(intervalle))
						: LocalDate.parse(tmp.toString()).with(TemporalAdjusters.lastDayOfMonth());
				
			}
			
			List <Object[]> list_temp = new ArrayList<Object[]>();
			
			weeks.add("Période "+k+" = "+conv.convertion_InputDate_to_MyDate(firstDayOfMonth.toString())+" "+
							conv.convertion_InputDate_to_MyDate(lastDayOfMonth.toString()));
			
			for(int c=0;c<list_category.length;c++) {
				
				category_produit cat_p = cat_pRepo.getOne(list_category[c]);
				
				List <Object[]> sum_fct = new ArrayList<Object[]>();
				
				List <Object[]> sum_fct_av = new ArrayList<Object[]>();
				
				double qte = 0;
				
				if(!cat_p.getNom_category().equals("Pates")) {
					
					sum_fct = fact_dRepo.get_info_sold_fact_by_category(firstDayOfMonth.toString(), lastDayOfMonth.toString(), cat_p);
					
					sum_fct_av = fact_av_dRepo.get_info_sold_fact_av_by_category(firstDayOfMonth.toString(), lastDayOfMonth.toString(), cat_p);
					
					for(int i=0;i<sum_fct.size();i++) {
						
						String name = (String) sum_fct.get(i)[0];
							
						categories.add(name);
						
						qte = (Double) sum_fct.get(i)[type_val];
						
						for(int j=0; j<sum_fct_av.size();j++ ) {
							
							if(name.equals(sum_fct_av.get(j)[0])) {
								
								qte = qte + (Double) sum_fct_av.get(j)[type_val];
								
							}
							
						}
						
						//double qte = (Double) sum_fct.get(i)[1] + (Double) sum_fct_av.get(i)[1];
						
					}
					
					Object[] obj = new Object [2];
					
					obj[0] = "Période "+k+" = "+conv.convertion_InputDate_to_MyDate(firstDayOfMonth.toString())+" "+
							conv.convertion_InputDate_to_MyDate(lastDayOfMonth.toString());
					obj[1] = qte;
					
					list_temp.add(obj);
					
				}
				else {
					
					List<sous_category_produit> l_scat = scat_pRepo.get_sousCat_by_cat(cat_p);
					
					for(sous_category_produit scat :l_scat) {
						
						sum_fct = fact_dRepo.get_info_sold_fact_by_sous_category(firstDayOfMonth.toString(), lastDayOfMonth.toString(), scat);
						
						sum_fct_av = fact_av_dRepo.get_info_sold_fact_av_by_sous_category(firstDayOfMonth.toString(), lastDayOfMonth.toString(), scat);
						
						for(int i=0;i<sum_fct.size();i++) {
							
							String name = (String) sum_fct.get(i)[0];
							
							categories.add(name);
								
							qte = (Double) sum_fct.get(i)[type_val];
							
							for(int j=0; j<sum_fct_av.size();j++ ) {
								
								if(name.equals(sum_fct_av.get(j)[0])) {
									
									qte = qte + (Double) sum_fct_av.get(j)[type_val];
									
								}
								
							}
							
							//double qte = (Double) sum_fct.get(i)[1] + (Double) sum_fct_av.get(i)[1];
							
						}
						
						Object[] obj = new Object [2];
						
						obj[0] = "Période "+k+" = "+conv.convertion_InputDate_to_MyDate(firstDayOfMonth.toString())+" "+
								conv.convertion_InputDate_to_MyDate(lastDayOfMonth.toString());
						obj[1] = qte;
						
						list_temp.add(obj);
						
					};
					
				}
				
				
			
			}
			
			list.add(list_temp);
			
			if(intervalle.equals("m")) {
				
				tmp = lastDayOfMonth.plusDays(1);
				
			}
			else {
				
				tmp = lastDayOfMonth.plusDays(1);
				
			}
			
			k++;
			
		}
		
		model.addAttribute("start", s.toString());
		
		model.addAttribute("end", e.toString());
		
		model.addAttribute("nbr_days", intervalle);
		
		model.addAttribute("type_val", type_val);
		
		model.addAttribute("list", list);
		
		model.addAttribute("weeks", weeks);
		
		System.out.println(categories.stream().distinct().collect(Collectors.toList()));
		
		model.addAttribute("cats", categories.stream().distinct().collect(Collectors.toList()));
		
		model.addAttribute("cat_selected", list_category);
		
		model.addAttribute("cat_prod", cat_pRepo.findAll().stream().filter(cat -> !cat.getNom_category().equals("Ristourne") )
				.collect(Collectors.toList()) );
		
		return "statistic/dashboard";		
	}
	
}
