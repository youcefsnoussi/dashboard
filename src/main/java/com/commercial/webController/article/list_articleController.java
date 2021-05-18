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
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;

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
	
	@Autowired
	tva_Repository tvaRepo;
	
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
		
		List <category_client> list_cat_c = cat_cltRepo.select_category_ordered();
		
		model.addAttribute("articles", list_art);
		
		List <List <prixUnitaire_article_categoryClient> > list_prix = new ArrayList< List <prixUnitaire_article_categoryClient> >();
		
		for(int i=0; i<list_art.size() ; i++) {
			
			//List <prixUnitaire_article_categoryClient> lp = prixRepo.listing_prices(list_art.get(i));
			
			List <prixUnitaire_article_categoryClient> lp = new ArrayList<prixUnitaire_article_categoryClient>();
			
			for (category_client cat_c : list_cat_c) {
				
				prixUnitaire_article_categoryClient price = prixRepo.get_instance_by_art_and_catClient(list_art.get(i), cat_c);
				
				if(price!=null) {
					
					lp.add(price);
					
				}
				else {
					
					lp.add(new prixUnitaire_article_categoryClient(list_art.get(i), cat_c, -1, tvaRepo.getOne((long)1)));
					
				}
				
			}
			
			list_prix.add(lp);
			
		}
		
		model.addAttribute("list_prix", list_prix);
		
		return "article/listing_prix_article";
		
	}
	
}
