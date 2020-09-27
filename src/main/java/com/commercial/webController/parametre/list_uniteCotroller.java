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

import com.commercial.entities.schema.static_data.unite;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.user_menu.users;

@Controller
@SessionAttributes("user")

public class list_uniteCotroller {
	
	@Autowired
	uniteRepository uniteRepo;
	
	public list_uniteCotroller() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/unite")
	public String get_list_unite(Model model){
		
		model.addAttribute("unite", uniteRepo.findAll());
		
		return "parametre/list_unite";
		
	}
	
	//-----------------------------------------------------------------------
	
	@RequestMapping(value="/add_edit_unite",method=RequestMethod.POST)
	public String insert_edit_unite(HttpServletRequest req,
			@RequestParam("designation") String designation,
			@RequestParam("id_unite") long id_unite,
			@RequestParam("identifiant") int identifiant,
			@SessionAttribute("user") users user){
			
			if(id_unite==0) {
				
				unite u = new unite(designation, identifiant);
				
				uniteRepo.save(u); uniteRepo.flush();
				
			}
			else {
				
				unite u = uniteRepo.getOne(id_unite);
				
				u.setNom_unite(designation);
				u.setIdentifiant(identifiant);
				
				uniteRepo.save(u); uniteRepo.flush();
				
			}
			
			return "redirect:/unite";
		
	}
	
}
