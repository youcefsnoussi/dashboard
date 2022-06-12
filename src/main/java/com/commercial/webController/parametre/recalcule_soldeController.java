package com.commercial.webController.parametre;

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

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.RecalculeSold;

@Controller
@SessionAttributes("user")

public class recalcule_soldeController {
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	RecalculeSold rs;
	
	public recalcule_soldeController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/recalcule")
	public String recalcule_sold(
			@SessionAttribute("user") users user,
			//@RequestParam(name="codes", defaultValue="") String codes,
			Model model){
		
		List<registre_commerce> rcs = rcRepo.findByCodeIsNotNull();
		
		//System.out.println("codes->"+codes);
		
		//String [] codes_ = codes.split(",");
		
		model.addAttribute("rc", rcs);
		
		//model.addAttribute("codes", codes_);
		
		return "parametre/recalcule_sold";
		
	}
	
	@RequestMapping(value="/recalcule_sold",method=RequestMethod.POST)
	public String recalcule_sold_post(HttpServletRequest req,
			@RequestParam(name="rc", defaultValue="") String codes,
			@SessionAttribute("user") users user){
			
			String [] codes_ = codes.split(",");
			
			String req_codes = "";
			
			for (String str : codes_) {
				req_codes = req_codes+"'"+str+"',";
			}
			
			req_codes = req_codes.substring(0, req_codes.length()-1);
			
			rs.recalcule_sold_rc_client(req_codes, user.getUnite().getIdentifiant());
			
			return "redirect:/recalcule";
		
	}
	
}
