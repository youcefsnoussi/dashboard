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
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_employeeRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.track_operations;

@RestController
@SessionAttributes("user")

public class bon_livraisonRestController {
	
	@Autowired
	bon_livraisonRepository blRepo;
	
	@Autowired
	bon_livraison_employeeRepository bleRepo;
	
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
	
	@RequestMapping(value="/cancel_ble")
	public String cancel_ble(
		@RequestParam("id_ble") long id_ble,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		String ret  = "";
		
		bon_livraison_employee ble = bleRepo.getOne(id_ble);
		
		if(ble.isCancel()==false) {
			
			ble.setCancel(true);
			
			bleRepo.save(ble); blRepo.flush();
			
			trk.add_track("bon_livraison_employee", "Annulation Bon de Employee", ble.getId(), user);
			
		}
		
		return ret;
		
	}
	
	//____________________________________________________________________________________________________
	
	
}
