package com.commercial.restController;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_interne;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfertRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_interneRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.services.track_operations;

@RestController
@SessionAttributes("user")

public class bon_transfertRestController {
	
	@Autowired
	bon_transfertRepository btRepo;
	
	@Autowired
	bon_transfert_interneRepository btiRepo;
	
	@Autowired
	track_operations trk;
	
	public bon_transfertRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/cancel_bt")
	public String cancel_bt(
		@RequestParam("id_bt") long id_bt,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		String ret  = "";
		
		bon_transfert bt = btRepo.getOne(id_bt);
			
		bt.setCancel(true);
		
		btRepo.save(bt); btRepo.flush();
		
		trk.add_track("bon_transfert", "Annulation Bon de Transfert", bt.getId(), user);
		
		return ret;
		
	}
	
	@RequestMapping(value="/cancel_bti")
	public String cancel_bti(
		@RequestParam("id_bti") long id_bti,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		String ret  = "";
		
		bon_transfert_interne bti = btiRepo.getOne(id_bti);
			
		bti.setCancel(true);
		
		btiRepo.save(bti); btRepo.flush();
		
		trk.add_track("bon_transfert_interne", "Annulation Bon de Transfert interne", bti.getId(), user);
		
		return ret;
		
	}
	
}
