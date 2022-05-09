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

import com.commercial.entities.schema.static_data.banque;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.user_menu.users;

@Controller
@SessionAttributes("user")

public class list_banqueController {
	
	@Autowired
	banqueRepository banqueRepo;
	
	public list_banqueController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/list_banq")
	public String get_banque_list(Model model){
		
		model.addAttribute("banque", banqueRepo.findAll());
		
		return "parametre/list_banque";
		
	}
	
	//-----------------------------------------------------------------------
	
	@RequestMapping(value="/add_edit_banque",method=RequestMethod.POST)
	public String insert_edit_banque(HttpServletRequest req,
			@RequestParam("nom_banque") String nom_banque,
			@RequestParam("code") String code,
			@RequestParam("num_compte") String num_compte_entreprise,
			@RequestParam("id_banque") long id_banque,
			@RequestParam(name="display", defaultValue="on") String display,
			@SessionAttribute("user") users user){
			
			if(id_banque==0) {
				
				banque b = new banque(code, nom_banque, num_compte_entreprise);
				
				banqueRepo.save(b); banqueRepo.flush();
				
			}
			else {
				
				banque b = banqueRepo.getOne(id_banque);
				
				b.setCode(code);
				b.setNom_banque(nom_banque);
				b.setNumero_compte_entreprise(num_compte_entreprise);
				b.setDisplay( (display.equals("on")) ? true : false );
				
				banqueRepo.save(b); banqueRepo.flush();
				
			}
			
			return "redirect:/list_banq";
		
	}
	
}
