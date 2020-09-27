package com.commercial.webController.article;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.emballage_produit;
import com.commercial.entities.schema.article.pesage_produit;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.produit;
import com.commercial.entities.schema.article.repository.*;
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.dynamic_data.Magasin;
import com.commercial.entities.schema.dynamic_data.repository.MagasinRepository;
import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;



@Controller
@SessionAttributes("user")

public class create_articleController {

	public create_articleController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	category_produitRepository catpRepo;
	
	@Autowired
	sous_category_produitRepository souscatpRepo;
	
	@Autowired
	emballage_produitRepository embRepo;
	
	@Autowired
	pesage_produitRepository pesRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository prix_u_art_catcRepo;
	
	@Autowired
	produitRepository prodRepo;
	
	@Autowired
	tva_Repository tvaRepo;
	
	@Autowired
	category_clientRepository catclientRepo;
	
	@Autowired
	unite_mesureRepository unite_mesureRepo;
	
	@Autowired
	MagasinRepository magRepo;
	
	@RequestMapping(value="/add_art")
	public String client(HttpServletRequest request,
						 @RequestParam("id_art") long id_art,
						 Model model){
		
		if(id_art==0) {
			
			model.addAttribute("article", null);
			
		}
		else {
			
			model.addAttribute("article", artRepo.getOne(id_art));
			model.addAttribute("prices", prix_u_art_catcRepo.get_prices_by_CatClient(artRepo.getOne(id_art)));
			
		}
		
		model.addAttribute("cat_produit", catpRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("cat_client", catclientRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("emballage", embRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("pesage", pesRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("tva", tvaRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unite_mesure", unite_mesureRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("magasin", magRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		return "article/create_article";
		
	}
	
	@RequestMapping(value="/create_article",method=RequestMethod.POST)
	public String inster_article_DB(HttpServletRequest req,
		//@RequestParam("cat_prod") long cat_prod,
		//@RequestParam("sous_cat_prod") long sous_cat_prod,
		@RequestParam("prod") long prod,
		@RequestParam("magasin") long id_magasin,
		@RequestParam("emb_prod") long emb_prod,
		@RequestParam("pesage_prod") long pesage_prod,
		@RequestParam("code_art") String code_art,
		@RequestParam("code_comptable") String code_comptable,
		@RequestParam("tva") long [] id_tva,
		@RequestParam("unite_mesure_vente") long id_unite_mesure,
		@RequestParam("cat_client") long [] cat_client,
		@RequestParam("prix_category") double [] prix_category,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		String ret = "succes";
		
		get_time_date gtd = new get_time_date();
		
		//category_produit cat_produit = catpRepo.getOne(cat_prod);
		
		//sous_category_produit sous_cat_produit = souscatpRepo.getOne(sous_cat_prod);
		
		produit produit = prodRepo.getOne(prod);
		
		emballage_produit emb_produit = embRepo.getOne(emb_prod);
		
		pesage_produit pes_produit = pesRepo.getOne(pesage_prod);
		  
		//--------------------------
		
		article art_if_code_existe = artRepo.if_code_art_exist(code_art);
		
		if(art_if_code_existe==null) {
			
			article art_if_same_specs = artRepo.if_art_same_spec_exist(produit, emb_produit, pes_produit, code_comptable);
			
			if(art_if_same_specs==null) {
				
				//tva tva = tvaRepo.getOne(id_tva);
				
				article art = new article(code_art, produit, emb_produit, pes_produit, "", 0, gtd.get_date(), code_comptable, 
						unite_mesureRepo.getOne(id_unite_mesure), magRepo.getOne(id_magasin)); //----- last parametre image
				
				artRepo.save(art);artRepo.flush();
				
				for(int i=0;i<cat_client.length;i++) {
					
					category_client cat_c = catclientRepo.getOne(cat_client[i]);
					
					tva tva = tvaRepo.getOne(id_tva[i]);
					
					prixUnitaire_article_categoryClient prix_u_c = new prixUnitaire_article_categoryClient(art, cat_c, prix_category[i], tva);
					
					prix_u_art_catcRepo.save(prix_u_c);prix_u_art_catcRepo.flush();
					
				}
				
			}
			else {
				
				ret = "same_specs";
				
			}
			
		}
		else {
			
			ret = "code_existe";
			
		}
		
		return "redirect:/add_art?ret="+ret+"&id_art=0";
		
	}
	
	//------------------------------------------------------------------------ EDIT ---
	
	@RequestMapping(value="/edit_article",method=RequestMethod.POST)
	public String edit_article_DB(HttpServletRequest req,
		@RequestParam("id_art") long id_art,
		@RequestParam("prod") long prod,
		@RequestParam("emb_prod") long emb_prod,
		@RequestParam("magasin") long id_magasin,
		@RequestParam("pesage_prod") long pesage_prod,
		@RequestParam("unite_mesure_vente") long id_unite_mesure,
		@RequestParam("code_art") String code_art,
		//@RequestParam("code_comptable") String code_comptable,
		@RequestParam("tva") long [] id_tva,
		@RequestParam("cat_client") long [] cat_client,
		@RequestParam("prix_category") double [] prix_category,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		String ret = "succes";
		
		get_time_date gtd = new get_time_date();
		
		//category_produit cat_produit = catpRepo.getOne(cat_prod);
		
		//sous_category_produit sous_cat_produit = souscatpRepo.getOne(sous_cat_prod);
		
		produit produit = prodRepo.getOne(prod);
		
		emballage_produit emb_produit = embRepo.getOne(emb_prod);
		
		pesage_produit pes_produit = pesRepo.getOne(pesage_prod);
		
		unite_mesure unite_m = unite_mesureRepo.getOne(id_unite_mesure);
		
		Magasin magasin = magRepo.getOne(id_magasin);
		
		//--------------------------
		
		//article art_if_code_existe = artRepo.if_code_art_exist(code_art);
		
		article art = artRepo.getOne(id_art);
		
		System.out.println("db code = "+art.getCode()+" || code saisie = "+code_art);
		
		if(art.getCode().equals(code_art)) {
			
			//article art_if_same_specs = artRepo.if_art_same_spec_exist(produit, emb_produit, pes_produit, code_comptable);
			
			//if(art_if_same_specs==null) {
				
				//tva tva = tvaRepo.getOne(id_tva);
				
				//article art = new article("code", produit, emb_produit, pes_produit, "", 0, gtd.get_date(), tva, unite_mesureRepo.getOne(id_unite_mesure)); //----- last parametre image
				
				//article art = artRepo.getOne(id_art);
				
				art.setCode(code_art);
				art.setEmballage_produit(emb_produit);
				//art.setImage_article(image_article);
				art.setPesage_produit(pes_produit);
				art.setProduit(produit);
				//art.setTva(tva);
				art.setUnite_mesure_vente(unite_m);
				art.setMagasin(magasin);
				
				artRepo.save(art);artRepo.flush();
				
				for(int i=0;i<cat_client.length;i++) {
					
					prixUnitaire_article_categoryClient prix_u_c = prix_u_art_catcRepo.getOne(cat_client[i]);
					
					prix_u_c.setPrix(prix_category[i]);
					
					tva tva = tvaRepo.getOne(id_tva[i]);
					
					prix_u_c.setTva(tva);
					
					prix_u_art_catcRepo.save(prix_u_c);prix_u_art_catcRepo.flush();
					
				}
				
			//}
			//else {
				
			//	ret = "same_specs";
				
			//}
			
		}
		else {
			
			ret = "code_existe";
			
		}
		
		return "redirect:/add_art?ret="+ret+"&id_art="+id_art;
		
	}
	
}
