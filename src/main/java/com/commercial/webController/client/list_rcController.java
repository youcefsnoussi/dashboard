package com.commercial.webController.client;

import java.util.ArrayList;
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
import com.commercial.functions.get_time_date;

@Controller
@SessionAttributes("user")

public class list_rcController {

	public list_rcController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	client_registreCommerceRepository crcRepo;
	
	@Autowired
	clientRepository cltRepo;
	
	@RequestMapping(value="/list_rc")
	public String rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String id_client = "";
		
		String role_access_rc_client = "access_rc_client";
		
		String role_add_rc = "add_rc";
		
		id_client = request.getParameter("id_c");
		
		if(Integer.parseInt(id_client)==0) {
			
			model.addAttribute("rc_info", rcRepo.findAll());
			
		}
		else {
			
			client clt = cltRepo.getOne(Long.parseLong(id_client));
			
			model.addAttribute("rc_info", crcRepo.rc_by_client(clt));
			
			role_access_rc_client = "no_access_rc_client";
			
			role_add_rc = "no_add_rc";
			
		}
		
		//------------------------ access controle
		
		String s = user.getRole().getIds_banned();
		
		if(s!=null && s.contains("rc_client")) {
			
			role_access_rc_client = "access_rc_client"; //------------- yakder yarbet rc m3a client
			
		}
		else {
			
			role_access_rc_client = "no_access_rc_client"; //------------- meyakderch yarbet rc m3a client
			
		}
		
		if(s!=null && s.contains("add_rc")) {
			
			role_add_rc = "add_rc";  //---------- yakder yahouti RC
			
		}
		else {
			
			role_add_rc = "no_add_rc"; //---------- mayakderch yahouti RC
			
		}
		
		//------------------------ ---------------------------------------------
		
		model.addAttribute("access_rc_client", role_access_rc_client);
		
		model.addAttribute("add_rc", role_add_rc);
		
		return "client/list_rc";
		
	}
	
	@RequestMapping(value="/info_rc")
	public String view_rc(HttpServletRequest request,
						 @RequestParam("id_rc") Long id_rc,
						 @SessionAttribute("user") users user,
						 Model model){
		
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		String s = user.getRole().getIds_banned();
		
		String role_edit = "no_edit";
		
		if(s!=null && s.contains("edit_rc")) {
			
			role_edit="edit";
			
		}
		
		model.addAttribute("edit_option", role_edit);
		
		model.addAttribute("rc", rc);
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		model.addAttribute("date_emission", conv.convertion_MyDate_to_InputDate(rc.getDate_emission()));
		
		model.addAttribute("date_fin", conv.convertion_MyDate_to_InputDate(rc.getDate_fin()));
		
		return "client/info_rc";
		
	}
	
	
	@RequestMapping(value="/edit_rc",method=RequestMethod.POST)
	public String edit_rc_DB(HttpServletRequest req,
		@RequestParam("id_rc") Long id_rc,
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
		@RequestParam("etat_blockage") String etat_blockage,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util ctd = new convert_string_to_date_util();
		
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		 date_emission = ctd.convertion_InputDate_to_MyDate(date_emission);
		
		//date_fin = ctd.convertion_InputDate_to_MyDate(date_fin);
		
		rc.setActivite(activite);
		rc.setAdresse(adresse);
		rc.setComune(comune);
		rc.setDate_emission(date_emission);
		rc.setDate_fin(date_fin);
		rc.setEtat_blockage(etat_blockage);
		rc.setNom(nom);
		rc.setNumero_art(num_art);
		rc.setNumero_nif(num_nif);
		rc.setNumero_rc(num_rc);
		rc.setPlafond(plafond);
		rc.setPrenom(prenom);
		
		float ttva = 0;
		if(tva.equals("on")) {
			
			ttva = 1;
			
		}
		
		rc.setTva(ttva);
		rc.setWilaya(wilaya);
		
		rcRepo.save(rc);rcRepo.flush();
		
		return "redirect:/info_rc?id_rc="+id_rc+"&resp=edit_ok";
		
	}
	
	//--------------------------------------------- RC Client Controller
	
	
	@RequestMapping(value="/list_rc_clt")
	public String list_rc_client(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("id_client") long id_clt,
						 @RequestParam("id_rc") long id_rc,
						 Model model){
		
		
		List <client_registreCommerce> list_clt_rc = new ArrayList<client_registreCommerce>();
		
		if(id_clt!=0 && id_rc!=0) {
			
			list_clt_rc = crcRepo.if_relation_existe(cltRepo.getOne(id_clt), rcRepo.getOne(id_rc));
			
		}
		else if(id_clt==0 && id_rc!=0) {
			
			list_clt_rc = crcRepo.client_by_rc_all(rcRepo.getOne(id_rc));
			
		}
		else if(id_clt!=0 && id_rc==0) {
			
			list_clt_rc = crcRepo.rc_by_client_all(cltRepo.getOne(id_clt));
			
		}
		else {
			
			list_clt_rc = crcRepo.findAll();
			
		}
		
		model.addAttribute("rc_client", list_clt_rc);
		
		return "client/list_rc_client";
		
	}
	
}
