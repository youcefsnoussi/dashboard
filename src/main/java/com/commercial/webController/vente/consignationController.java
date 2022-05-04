package com.commercial.webController.vente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.services.FactureService;
import com.commercial.services.generate_Doc;

@Controller
@SessionAttributes("user")

public class consignationController {
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	//--------------------- Services ------------------<
	
	@Autowired
	generate_Doc gd;
	
	@Autowired
	FactureService factServ;
	
	public consignationController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/consignation")
	public String cmd(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/consignation";
		
		List <client_registreCommerce> clt_rc = clt_rcRepo.RC_consignation();
		
		
		model.addAttribute("rcs_clt", clt_rc);
		
		
		return ret;
		
	}
	
	//-------------------------------- POST VALIDATION --------------------------<
	
	@RequestMapping(value="/new_consignation_post",method=RequestMethod.POST)
	public String new_commande(HttpServletRequest req,
			//@RequestParam("id_client") long id_client,
			@RequestParam("id_rel_rc_clt") long id_relation_rc_clt,
			
			@RequestParam("art") long [] article,
			@RequestParam("qte") double [] quantite,
			
			@SessionAttribute("user") users user){
		
		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_relation_rc_clt);
		
		List<Map<String, Object>> details = new ArrayList<>();
		
		for (int i=0; i < quantite.length; i++) {
			
			if(quantite[i] != 0) {
				
				Map<String,Object> mp = new HashMap<>();
				
				mp.put("article", article[i]);
				mp.put("quantite", quantite[i]);
				mp.put("remise", 0);
				
				details.add(mp);
				
			}
			
		}
		
		facture fact = factServ.InsertFactureDB(clt_rc, "", null, clt_rc.getRegistre_commerce().getMode_paiement(), user, details);
		
		return "redirect:/consignation?id_fact="+fact.getId()+"&num_fact="+fact.getNumero()+"&type=fact";
		
	}
	
}
