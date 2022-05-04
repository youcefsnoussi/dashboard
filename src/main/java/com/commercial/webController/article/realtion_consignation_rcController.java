package com.commercial.webController.article;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.commercial.entities.schema.article.rc_consignation;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.article_consignation_relationRepository;
import com.commercial.entities.schema.article.repository.rc_consignationRepository;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class realtion_consignation_rcController {

	@Autowired
	articleRepository artRepo;
	
	@Autowired
	article_consignation_relationRepository art_cons_relRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	rc_consignationRepository rccRepo;
	
	@Autowired
	track_operations track;
	
	public realtion_consignation_rcController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/rc_cons_rel")
	public String relation_consignation_rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "article/relation_consignation_rc";
		
		model.addAttribute("rcs", rcRepo.findByConsignationTrue());
		
		model.addAttribute("articles_consignation", artRepo.findByConsignationTrue());
		
		return ret;
		
	}
	
	@RequestMapping(value="/relation_consignation_rc_post", method=RequestMethod.POST)
	public String relation_consignation_rc_post(HttpServletRequest request,
						 @RequestParam("id_rc") long id_rc,
						 @RequestParam("id_art_cons") long id_art_cons,
						 @RequestParam("prix_u") double prix_u,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String status = "";
		
		String e_message = "";
		
		try {
		
			article art_cons = artRepo.getOne(id_art_cons);
			
			registre_commerce rc = rcRepo.getOne(id_rc);
			
			Optional<rc_consignation> div = Optional.ofNullable(rccRepo.getRcConsignation(rc, art_cons));
			
			if(!div.isPresent() && prix_u >= 0) {
				
				rc_consignation rcc = new rc_consignation(rc, art_cons, prix_u);
				
				rccRepo.save(rcc); rccRepo.flush();
				
				track.add_track("rc_consignation", "Ajout de relation entre RC et Consignation", rcc.getId(), user);
				
				status = "ok";
				
				e_message = URLEncoder.encode("Opération effectuer avec succés", "UTF-8");
				
			}
			
			else {
				
				status = "exist";
				e_message = URLEncoder.encode("Article et Registre Commerce sont déja relier", "UTF-8");
			}
		
		}catch (Exception e) {
			
			List<StackTraceElement> lst = Arrays.asList(e.getStackTrace());
			
			List<StackTraceElement> ste = lst.stream().filter(s -> 
				s.getClassName().contains("com.commercial")
			).collect(Collectors.toList());;
			
			status = "error";
			e_message = "Message d'erreur : "+e.toString()+"$ Method : "+ste.get(0).getMethodName()+
					"$ Line : "+ste.get(0).getLineNumber();
		}
		
		return "redirect:/rc_cons_rel?status="+status+"&e_message="+e_message;
		
	}
	
	//--------------------------------------------------
	
	@RequestMapping(value="/list_rc_cons")
	public String list_relation_consignation_rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "article/list_rc_consignation";
		
		model.addAttribute("rcs_cons", rccRepo.findAll(Sort.by(Sort.Direction.ASC,"id")));
		
		return ret;
		
	}
	
}
