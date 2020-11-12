package com.commercial.webController.client;

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

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.time_between;
import com.commercial.functions.track_operations;

@Controller
@SessionAttributes("user")

public class rc_client_relationController {
	
	@Autowired
	clientRepository cltRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	client_registreCommerceRepository crcRepo;
	
	@Autowired
	track_operations trk;
	
	public rc_client_relationController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/clt_rc")
	public String clt_rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("rc_client"))) 
		{ ret = "client/rc_client"; }
		else { ret = "403"; }
		
		//----------------------------------------------------------------	
		
		model.addAttribute("today", rcRepo.rc_active_only());
		
		model.addAttribute("client", cltRepo.client_active_only());
		
		model.addAttribute("rc", rcRepo.rc_active_only());
		
		return ret;
		
	}
	
	@RequestMapping(value="/relation_rc_clt",method=RequestMethod.POST)
	public String relation_rc_clt(HttpServletRequest req,
		@RequestParam("id_client") long id_client,
		@RequestParam("date_debut") String date_debut,
		@RequestParam("date_fin") String date_fin,
		@RequestParam("id_rc") long id_rc,
		
		@SessionAttribute("user") users user){
		
		String ret = "no_succes";
		
		time_between tb = new time_between(); 
		
		convert_string_to_date_util ctd = new convert_string_to_date_util();
		
		date_debut = ctd.convertion_InputDate_to_MyDate(date_debut);
		
		date_fin = ctd.convertion_InputDate_to_MyDate(date_fin);
		
		client clt = cltRepo.getOne(id_client); 
		
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		List<client_registreCommerce> crc = crcRepo.if_relation_existe(clt, rc);
		
		if(crc.size()==0) {
			
			client_registreCommerce new_crc = new client_registreCommerce(clt, rc, date_debut, date_fin, 0);
			
			crcRepo.save(new_crc); crcRepo.flush();
			
			trk.add_track("client_registreCommerce", "Creation relation client RC", new_crc.getId(), user);
			
			ret = "succes";
			
		}
		
		else {
			
				int last_index = crc.size()-1;
				
				if(tb.if_after_2(date_debut, crc.get(last_index).getDate_fin()) && tb.if_after_2(date_fin, date_debut)) {
					
					client_registreCommerce new_crc = new client_registreCommerce(clt, rc, date_debut, date_fin, 0);
					
					crcRepo.save(new_crc); crcRepo.flush();
					
					trk.add_track("client_registreCommerce", "Creation relation client RC", new_crc.getId(), user);
					
					ret = "succes";
					
				}
				
			
			
		}
		
		return "redirect:/clt_rc?ret="+ret;
		
	}
	
	
}
