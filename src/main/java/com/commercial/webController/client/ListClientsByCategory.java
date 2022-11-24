package com.commercial.webController.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.repository.wilayaRepository;
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.static_data.wilaya;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.services.generate_Doc;

@Controller
@SessionAttributes("user")

public class ListClientsByCategory {
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	category_clientRepository cat_cRepo;
	
	@Autowired
	wilayaRepository wilayaRepository;
	
	public ListClientsByCategory() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/GetClientsByCategory")
	public String GetClientsByCategory( @RequestParam(value="start", defaultValue="0") String start,
						 				@RequestParam(value="end", defaultValue="0") String end,
						 				@RequestParam(value="cat_c", defaultValue="0") Long id_cat_c, 
						 				@RequestParam(value="wilaya", defaultValue="0") Long wilaya, 
										@SessionAttribute("user") users user,
										Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		if(start.equals("0")) {
			
			start = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			end = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		}
		
		List <registre_commerce> ListClients = new ArrayList<registre_commerce>();
		
		if(id_cat_c !=0 && wilaya!=0) {
			
			category_client cat_c = cat_cRepo.getOne(id_cat_c);
			wilaya ww = wilayaRepository.getOne(wilaya);
			
			ListClients = factRepo.GetClientsByCategoryAndWilaya(start, end, cat_c,ww);
			
		}else if(id_cat_c !=0 ) {
			
			category_client cat_c = cat_cRepo.getOne(id_cat_c);
			
			ListClients = factRepo.GetClientsByCategory(start, end, cat_c);
			
		}else if(wilaya !=0 ) {
			
			wilaya ww = wilayaRepository.getOne(wilaya);
			
			ListClients = factRepo.GetClientsByWilaya(start, end, ww);
			
		}
		else {
			
			ListClients = factRepo.GetClients(start, end);
			
		}	
		
		model.addAttribute("ListClients", ListClients);
		model.addAttribute("CategoryClient", cat_cRepo.findAll());
		model.addAttribute("wilayas", wilayaRepository.findAll());
		model.addAttribute("cat_encours", id_cat_c);
		model.addAttribute("wialaya_encours", wilaya);
		
		model.addAttribute("date_d", start);
		model.addAttribute("date_f", end);
		
		return "client/ClientsByCategoryBuyPeriode";		
		
	}
	
	//---------------------------------------- ----------------------------- -------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_rc_buyer")
	public String print_rc_buyer(
							 @RequestParam(value="start", defaultValue="0") String start,
							 @RequestParam(value="end", defaultValue="0") String end,
							 @RequestParam(value="cat_c", defaultValue="0") Long id_cat_c,
							 @RequestParam(value="wilaya", defaultValue="0") Long wilaya,
							 @SessionAttribute("user") users user,
							 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		if(start.equals("0")) {
			
			start = end = gtd.get_date();
			
		}
		else {
			
			start = conv.convertion_InputDate_to_MyDate(start);
			
			end = conv.convertion_InputDate_to_MyDate(end);
			
		}
		
	
		String pdf = "";
		
		
		pdf = gd.generate_rc_buyer(start, end, id_cat_c,wilaya);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
}
