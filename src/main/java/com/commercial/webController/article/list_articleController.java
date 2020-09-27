package com.commercial.webController.article;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.client.registre_commerce;

@Controller
@SessionAttributes("user")

public class list_articleController {

	public list_articleController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	articleRepository artRepo;
	
	@RequestMapping(value="/list_art")
	public String client(HttpServletRequest request,
						 Model model){
		
		model.addAttribute("articles", artRepo.findAll());
		return "article/list_article";
		
	}
	
}
