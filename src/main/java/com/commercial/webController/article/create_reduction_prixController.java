package com.commercial.webController.article;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.reduction_client_prixU_article;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.reduction_client_prixU_articleRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class create_reduction_prixController {
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	reduction_client_prixU_articleRepository reduxRepo;
	
	@Autowired
	clientRepository cltRepo;
	
	@Autowired
	track_operations trk;
	
	@RequestMapping(value="/new_reduction")
	public String new_reduction(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("add_reduction"))) 
		{ ret = "article/create_reduction_prix"; }
		else { ret = "403"; }
		
		//----------------------------------------------------------------	
		
		model.addAttribute("articles", artRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
			
		model.addAttribute("clients", cltRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		return ret;
		
	}
	
	//______________________________________________ POST _______________________________________
	
	@RequestMapping(value="/create_reduction_prix",method=RequestMethod.POST)
	public String new_reduction(HttpServletRequest req,
		@RequestParam("id_client") long id_clt,
		@RequestParam("id_article") long id_art,
		@RequestParam("old_price") double old_price,
		@RequestParam("new_price") double new_price,
		@RequestParam("date_debut") String date_debut,
		@RequestParam("date_fin") String date_fin,
		@RequestParam("obs") String obs,
		
		@SessionAttribute("user") users user){
		
		get_time_date gtd = new get_time_date();
		
		client clt = cltRepo.getOne(id_clt);
		
		article art = artRepo.getOne(id_art);
		
		convert_string_to_date_util cc = new convert_string_to_date_util();
		
		String date_d = cc.convertion_InputDate_to_MyDate(date_debut);
		
		String date_f = cc.convertion_InputDate_to_MyDate(date_fin);
		
		reduction_client_prixU_article red = new reduction_client_prixU_article(clt, art, old_price, new_price, date_d, date_f, 
				gtd.get_date(), obs, user, true);
		
		reduxRepo.save(red);reduxRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("reduction_client_prixU_article", "Ajout reduction prix article pour client", red.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		return "redirect:/new_reduction";
		
	}
	
}
