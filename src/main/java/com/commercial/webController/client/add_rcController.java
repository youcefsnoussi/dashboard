package com.commercial.webController.client;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.backup_edit.repository.registre_commerce_backupRepository;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.track_operations;

@Controller
@SessionAttributes("user")

public class add_rcController {
	
	@Autowired
	clientRepository clientRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	tva_Repository tvaRepo;
	
	@Autowired
	registre_commerce_backupRepository rcbRepo;
	
	@Autowired
	track_operations trk;
	
	public add_rcController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/add_rc")
	public String add_new_rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "client/create_rc";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("add_rc"))) 
		{ ret = "client/create_rc"; }
		else { ret = "403"; }
		
		//----------------------------------------------------------------	
		
		model.addAttribute("client", clientRepo.findAll());
		
		return ret;
		
	}
	
	@RequestMapping(value="/create_rc",method=RequestMethod.POST)
	public String inster_rc_DB(HttpServletRequest req,
		@RequestParam("nom") String nom,
		@RequestParam("prenom") String prenom,
		@RequestParam("num_rc") String num_rc,
		@RequestParam("num_art") String num_art,
		@RequestParam("num_nif") String num_nif,
		@RequestParam("date_emission") String date_emission,
		@RequestParam("date_fin") String date_fin,
		@RequestParam("adresse") String adresse,
		@RequestParam("comune") String comune,
		@RequestParam("wilaya") String wilaya,
		@RequestParam(value = "tva", required = false) String tva,
		@RequestParam("plafond") double plafond,
		@RequestParam("activite") String activite,
		
		@SessionAttribute("user") users user){
		
		convert_string_to_date_util ctd = new convert_string_to_date_util();
		
		System.out.println("tva==="+tva);
		
		registre_commerce rc = rcRepo.if_rc_exist_db(num_rc, num_nif, num_art, nom, prenom);
		
		String ret = "exist";
		
		date_emission = ctd.convertion_InputDate_to_MyDate(date_emission);
		
		date_fin = ctd.convertion_InputDate_to_MyDate(date_fin);
		
		float taux_tva = 0;
		
		if(rc==null) {
			
			if(tva=="on") {
				
				//tva tt = tvaRepo.getOne((long) 1);
				
				taux_tva = 1; // ========> taux tva represente if ndirlo tva or not
				
			}
			
			System.out.println("watch dog");
			
			//new registre_commerce(nom, prenom, numero_rc, numero_art, numero_nif, date_emission, date_fin, adresse, comune, wilaya, etat, tva, plafond, balance, activite, etat_blockage)
			
			rc = new registre_commerce(nom, prenom, num_rc, num_art, num_nif, date_emission, date_fin, adresse, comune, wilaya, 
						"active", taux_tva, plafond, 0, activite, "active");
			
			//new registre_commerce(nom, prenom, numero_rc, numero_art, numero_nif, date_emission, date_fin, adresse, comune, wilaya, etat, tva, plafond, sold_encours, activite, etat_blockage)
			
			rcRepo.save(rc);rcRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("registre_commerce", "Ajout d'un RC", rc.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			ret = "added";
			
		}
		
		
		return "redirect:/add_rc?ret="+ret;
		
	}
	
}	
