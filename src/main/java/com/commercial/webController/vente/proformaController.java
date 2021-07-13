package com.commercial.webController.vente;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.proformaRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.proforma_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;

public class proformaController {
	
	@Autowired
	proformaRepository prfmRepo;
	
	@Autowired
	proforma_detailRepository prfmDRepo;
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	public proformaController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="proforma")
	public String bon_cmd(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		get_time_date gtd = new get_time_date();
		
		String ret = "vente/bon_commande";
		
		model.addAttribute("clients", clt_rcRepo.ListRCwithCLIENT_active(gtd.get_date()));
		
		//model.addAttribute("articles", artRepo.findAll());
		
		//model.addAttribute("cat_client", cat_clientRepo.findAll());
		
		//model.addAttribute("unite", uniteRepo.findAll());
		/*
		String s = user.getRole().getIds_banned();
		
		if(s!=null && s.contains("no_add_client")) {
			
			ret = "403";
			
		}
		*/
		return ret;
		
	}
	
}
