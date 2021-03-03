package com.commercial.restController.article;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.track_operations;

@RestController
@SessionAttributes("user")

public class bon_livraisonRestController {
	
	@Autowired
	bon_livraisonRepository blRepo;
	
	@Autowired
	track_operations trk;
	
	public bon_livraisonRestController() {
		// TODO Auto-generated constructor stub
	}
	
	//___________________________________________________________________________________________________
	
	@RequestMapping(value="/cancel_bl")
	public String cancel_bl(
		@RequestParam("id_bl") long id_bl,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		String ret  = "";
		
		bon_livraison bl = blRepo.getOne(id_bl);
		
		if(bl.getEtat_livraison()==0) {
			
			bl.setCancel(true);
			
			blRepo.save(bl); blRepo.flush();
			
			trk.add_track("bon_livraison", "Annulation Bon de Sortie", bl.getId(), user);
			
		}
		
		return ret;
		
	}
	
	//____________________________________________________________________________________________________
	
	
	
}
