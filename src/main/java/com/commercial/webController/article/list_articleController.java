package com.commercial.webController.article;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.repository.articleRepository;

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
		
		model.addAttribute("articles", artRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		return "article/list_article";
		
	}
	
}
