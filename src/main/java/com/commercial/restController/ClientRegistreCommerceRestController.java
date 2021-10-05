package com.commercial.restController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;

@RestController
@SessionAttributes("user")

public class ClientRegistreCommerceRestController {
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	public ClientRegistreCommerceRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping(value="/update_date_end_relation")
	public boolean update_date_end_relation(
					@RequestParam("id_relation") long id_relation,
					@RequestParam("end") String end,
					@SessionAttribute("user") users user){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_relation);
		
		clt_rc.setDate_fin(conv.convertion_InputDate_to_MyDate(end));
		
		clt_rcRepo.save(clt_rc); clt_rcRepo.flush();
		
		return true;
		
	}
	
}
