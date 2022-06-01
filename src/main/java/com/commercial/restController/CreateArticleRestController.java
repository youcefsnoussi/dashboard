package com.commercial.restController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.entities.schema.article.produit;
import com.commercial.entities.schema.article.sous_category_produit;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.emballage_produitRepository;
import com.commercial.entities.schema.article.repository.pesage_produitRepository;
import com.commercial.entities.schema.article.repository.produitRepository;
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;

@RestController

public class CreateArticleRestController {

	public CreateArticleRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	category_produitRepository cat_prodRepo;
	
	@Autowired
	emballage_produitRepository emb_prodRepo;
	
	@Autowired
	pesage_produitRepository pes_prodRepo;
	
	@Autowired
	produitRepository prodRepo;
	
	@Autowired
	sous_category_produitRepository sous_cat_prodRepo;
	
	//=============================================================== get sous cat prod AJAX REQ ======================================================================
	
	@RequestMapping(value="/get_sous_cat_prod")
	public List<sous_category_produit> get_sous_cat_prod(@RequestParam("id_cat_prod") Long id_cat_prod) throws IOException{
		
    	List<sous_category_produit> list_sous_cat=  
    			(id_cat_prod==null) ? new ArrayList<sous_category_produit>() : 
    			sous_cat_prodRepo.get_sousCat_by_cat(cat_prodRepo.getOne(id_cat_prod));
    	
		return list_sous_cat;
		
	}
	
	//===============================================================================================================================================================
	
	//=============================================================== get prod AJAX REQ ======================================================================
	
	@RequestMapping(value="/get_prod_sousCat")
	public List<produit> get_prod(@RequestParam("id_sous_cat_prod") Long id_sous_cat_prod) throws IOException{
		
    	List<produit> list_prod=  
    			(id_sous_cat_prod==null) ? new ArrayList<produit>()
    			: prodRepo.get_prod_by_sousCat(sous_cat_prodRepo.getOne(id_sous_cat_prod));
    	
		return list_prod;
		
	}
	
	//===============================================================================================================================================================
	
}
