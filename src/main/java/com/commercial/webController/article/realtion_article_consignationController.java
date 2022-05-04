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
import com.commercial.entities.schema.article.article_consignation_relation;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.article_consignation_relationRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class realtion_article_consignationController {
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	article_consignation_relationRepository art_cons_relRepo;
	
	@Autowired
	track_operations track;
	
	public realtion_article_consignationController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/art_cons_rel")
	public String relation_consignation_article(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "article/relation_consignation_article";
		
		model.addAttribute("articles", artRepo.findByConsignationFalse());
		
		model.addAttribute("articles_consignation", artRepo.findByConsignationTrue());
		
		return ret;
		
	}
	
	//---------------------------------------------------
	
	@RequestMapping(value="/relation_consignation_article_post", method=RequestMethod.POST)
	public String relation_consignation_article_post(HttpServletRequest request,
						 @RequestParam("id_art") long id_art,
						 @RequestParam("id_art_cons") long id_art_cons,
						 @RequestParam("division") double division,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String status = "";
		
		String e_message = "";
		
		try {
		
		article art = artRepo.getOne(id_art), art_cons = artRepo.getOne(id_art_cons);
		
			Optional<Double> div = Optional.ofNullable(art_cons_relRepo.getDivisioner(art, art_cons));
			
			if(!div.isPresent() && division > 0) {
				
				article_consignation_relation art_rel = new article_consignation_relation(art, art_cons, division);
				
				art_cons_relRepo.save(art_rel); art_cons_relRepo.flush();
				
				track.add_track("article_consignation_relation", "Ajout de relation entre article et consignation", art_rel.getId(),
						user);
				
				status = "ok";
				
				e_message = URLEncoder.encode("Opération effectuer avec succés", "UTF-8");
				
			}
			else {
				
				status = "exist";
				
				e_message = URLEncoder.encode("Ces 2 articles sont déja relier", "UTF-8");
				
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
		
		//System.out.println("e message : "+e_message);
		
		return "redirect:/art_cons_rel?status="+status+"&e_message="+e_message;
		
	}
	
	//--------------------------------------------------
	
	@RequestMapping(value="/list_art_cons")
	public String list_relation_consignation_article(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "article/list_article_consignation";
		
		model.addAttribute("arts_cons", art_cons_relRepo.findAll(Sort.by(Sort.Direction.ASC,"article")));
		
		return ret;
		
	}
	
}
