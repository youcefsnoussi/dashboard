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

import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;

@Controller
@SessionAttributes("user")

public class history_buy_by_rcController {
	
	@Autowired
	factureRepository factRepo;
	
	public history_buy_by_rcController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/historic_ventes_rc")
	public String history_by_rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 Model model){
		
		String ret = "statistic/get_rc_today_buy";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		if(date_debut.equals("0")) {
			
			list = factRepo.get_rc_buy(gtd.get_date(), gtd.get_date());
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			list =  factRepo.get_rc_buy(conv.convertion_InputDate_to_MyDate(date_debut), conv.convertion_InputDate_to_MyDate(date_fin));
			
			model.addAttribute("start", date_debut);
			
			model.addAttribute("end", date_fin);
			
		}
		
		
		
		model.addAttribute("list", list);
		
		
		return ret;
		
	}
	
}
