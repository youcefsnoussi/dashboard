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

import com.commercial.entities.schema.static_data.type_reglement;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.user_menu.users;

@Controller
@SessionAttributes("user")

public class list_TypeReglementController {
	
	@Autowired
	type_reglementRepository typ_rRepo;
	
	public list_TypeReglementController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/list_type_reg")
	public String get_list_type_reg(Model model){
		
		model.addAttribute("type_reg", typ_rRepo.findAll());
		
		return "parametre/list_type_reglement";
		
	}
	
	//-----------------------------------------------------------------------
	
	@RequestMapping(value="/add_edit_type_reg",method=RequestMethod.POST)
	public String insert_edit_type_rege(HttpServletRequest req,
			@RequestParam("designation") String designation,
			@RequestParam("id_type_reg") long id_type_reg,
			@SessionAttribute("user") users user){
			
			if(id_type_reg==0) {
				
				type_reglement tr = new type_reglement(designation);
				
				typ_rRepo.save(tr); typ_rRepo.flush();
				
			}
			else {
				
				type_reglement tr = typ_rRepo.getOne(id_type_reg);
				
				tr.setDesignation(designation);
				
				typ_rRepo.save(tr); typ_rRepo.flush();
				
			}
			
			return "redirect:/list_type_reg";
		
	}
	
}
