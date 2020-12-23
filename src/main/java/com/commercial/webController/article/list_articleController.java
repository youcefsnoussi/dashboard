package com.commercial.webController.article;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.client.repository.category_clientRepository;

@Controller
@SessionAttributes("user")

public class list_articleController {

	public list_articleController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	category_clientRepository cat_cltRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository prixRepo;
	
	@RequestMapping(value="/list_art")
	public String client(HttpServletRequest request,
						 Model model){
		
		model.addAttribute("articles", artRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		return "article/list_article";
		
	}
	
	@RequestMapping(value="/listing_prix")
	public String price_listing(HttpServletRequest request,
						 Model model){
		
		model.addAttribute("cat_client", cat_cltRepo.select_category_ordered());
		
		List <article> list_art = artRepo.select_articles_ordered();
		
		model.addAttribute("articles", list_art);
		
		List <List <prixUnitaire_article_categoryClient> > list_prix = new ArrayList< List <prixUnitaire_article_categoryClient> >();
		
		for(int i=0; i<list_art.size() ; i++) {
			
			List <prixUnitaire_article_categoryClient> lp = prixRepo.listing_prices(list_art.get(i));
			
			list_prix.add(lp);
			
		}
		
		model.addAttribute("list_prix", list_prix);
		
		return "article/listing_prix_article";
		
	}
	
}
