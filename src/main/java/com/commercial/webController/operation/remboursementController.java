package com.commercial.webController.operation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.remboursementRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;

@Controller
@SessionAttributes("user")

public class remboursementController {
	
	@Autowired
	remboursementRepository rmbRepo;
	
	public remboursementController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/list_remboursements")
	public String list__paiements(HttpServletRequest request,
						 @RequestParam(name="date_debut", defaultValue="0") String date_debut,
						 @RequestParam(name="date_fin", defaultValue="0") String date_fin,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "operation/list_remboursements";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		String date_d = (date_debut.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : date_debut; 
		
		String date_f = (date_fin.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : date_fin; 
		
		model.addAttribute("remboursements", rmbRepo.get_remboursements_interval(date_d, date_f));
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		//----------------------------------------------------------------
		
		return ret;
		
	}
	
}
