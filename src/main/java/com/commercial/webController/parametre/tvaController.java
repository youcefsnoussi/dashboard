package com.commercial.webController.parametre;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.user_menu.users;

@Controller
@SessionAttributes("user")

public class tvaController {
	
	@Autowired
	tva_Repository tvaRepo;
	
	public tvaController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/tva")
	public String get_tva(Model model){
		
		List<tva> l_tva = tvaRepo.findAll();
		/*
		if(l_tva.isEmpty()) {
			
			model.addAttribute("tva", "");
			
		}
		else {
			
			model.addAttribute("tva", l_tva.get(0).getTaux_tva());
			
		}
		*/
		
		model.addAttribute("tva", l_tva);
		
		return "parametre/tva";
		
	}
	
	@RequestMapping(value="/add_edit_tva",method=RequestMethod.POST)
	public String insert_edit_tva(HttpServletRequest req,
			@RequestParam("id_tva") long id_tva,
			@RequestParam("tva") double tva,
			@SessionAttribute("user") users user){
			
			//List<tva> l_tva = tvaRepo.findAll();
			
			if(id_tva==0) {
				
				tva t = new tva(tva);
				tvaRepo.save(t); tvaRepo.flush();
				
			}
			else {
				
				tva t = tvaRepo.getOne(id_tva);
				
				t.setTaux_tva(tva);
				
				tvaRepo.save(t); tvaRepo.flush();
				
			}
			
			return "redirect:/tva";
		
	}
	
}
