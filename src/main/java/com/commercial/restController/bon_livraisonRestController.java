package com.commercial.restController;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_employeeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfertRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
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
	
	@Autowired
	bon_livraison_factureRepository blfRepo;
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	@RequestMapping(value="/cancel_blf")
	public String cancel_blf(
		@RequestParam("id_bl") long id_blf,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		String ret  = "";
		
		bon_livraison_facture blf = blfRepo.getOne(id_blf);
		
		if(blf.getEtat_livraison()==0) {
			
			blf.setCancel(true);
			
			blfRepo.save(blf); blfRepo.flush();
			
			trk.add_track("bon_livraison_facture", "Annulation Bon de Livraison", blf.getId(), user);
			
		}
		
		return ret;
		
	}
	
	//------------------------------
	
	@RequestMapping(value="/changeRcBls")
	public String changeRcBls(
		@RequestParam("rc_clt") long rc_clt,
		@RequestParam("blfs") String blfs,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		String ret  = "change";
		
		registre_commerce newRc = clt_rcRepo.getOne(rc_clt).getRegistre_commerce();
		
		String [] ids = blfs.split("-");
		
		List<Long> idsBlfs = new ArrayList<Long>();
		
		if(!blfs.isEmpty()) {
			
			for (String str : ids) {
				
				idsBlfs.add(Long.parseLong(str));
				
			}
			
		}
		
		idsBlfs.forEach(l ->{
			
			bon_livraison_facture blf = blfRepo.getOne(l);
			
			blf.setRegistre_commerce(newRc);
			
			blfRepo.save(blf); blfRepo.flush();
			
		});
		
		return ret;
		
	}
	
	//-------------------------------- 
	
	@RequestMapping(value="/changeDateBls")
	public String changeDateBls(
		@RequestParam("date") String date,
		@RequestParam("blfs") String blfs,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		String ret  = "change";
		
		String [] ids = blfs.split("-");
		
		List<Long> idsBlfs = new ArrayList<Long>();
		
		if(!blfs.isEmpty()) {
			
			for (String str : ids) {
				
				idsBlfs.add(Long.parseLong(str));
				
			}
			
		}
		
		idsBlfs.forEach(l ->{
			
			bon_livraison_facture blf = blfRepo.getOne(l);
			
			blf.setDate(conv.convertion_InputDate_to_MyDate(date));
			
			blfRepo.save(blf); blfRepo.flush();
			
		});
		
		return ret;
		
	}
	
	//____________________________________________________________________________________________________
	
	@Autowired
	bon_transfertRepository btRepo;
	
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
	
	//____________________________________________________________________________________________________
	
}
