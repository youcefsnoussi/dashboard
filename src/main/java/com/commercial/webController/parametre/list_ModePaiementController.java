package com.commercial.webController.parametre;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.static_data.mode_paiement;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.user_menu.users;

@Controller
@SessionAttributes("user")

public class list_ModePaiementController {
	
	@Autowired
	mode_paiementRepository mpRepo;
	
	public list_ModePaiementController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/list_mode_p")
	public String get_mode_pay(Model model){
		
		model.addAttribute("mode_pay", mpRepo.findAll());
		
		return "parametre/liste_mode_paiement";
		
	}
	
	//-----------------------------------------------------------------------
	
	@RequestMapping(value="/add_edit_mode_payment",method=RequestMethod.POST)
	public String insert_edit_mode_pay(HttpServletRequest req,
			@RequestParam("designation") String designation,
			@RequestParam("id_mode_payment") long id_mode_payment,
			@RequestParam(name="display", defaultValue="on") String display,
			@SessionAttribute("user") users user){
			
			if(id_mode_payment==0) {
				
				mode_paiement mp = new mode_paiement(designation);
				
				mpRepo.save(mp); mpRepo.flush();
				
			}
			else {
				
				mode_paiement mp = mpRepo.getOne(id_mode_payment);
				
				mp.setDesignation(designation);
				mp.setDisplay( (display.equals("on")) ? true : false );
				
				mpRepo.save(mp); mpRepo.flush();
				
			}
			
			return "redirect:/list_mode_p";
		
	}
	
}
