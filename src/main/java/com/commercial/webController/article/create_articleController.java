package com.commercial.webController.article;



import java.util.List;

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

import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.emballage_produit;
import com.commercial.entities.schema.article.magasin_article;
import com.commercial.entities.schema.article.pesage_produit;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.produit;
import com.commercial.entities.schema.article.repository.*;
import com.commercial.entities.schema.backup_edit.article_backup;
import com.commercial.entities.schema.backup_edit.prixUnitaire_article_categoryClient_backup;
import com.commercial.entities.schema.backup_edit.repository.article_backupRepository;
import com.commercial.entities.schema.backup_edit.repository.prixUnitaire_article_categoryClient_backupRepository;
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;
import com.commercial.services.track_operations;



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
	
	@Autowired
	magasin_articleRepository mag_artRepo;
	
	@Autowired
	article_backupRepository art_bRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_backupRepository prix_c_bRepo;
	
	@Autowired
	track_operations trk;
	
	@RequestMapping(value="/add_art")
	public String add_new_article(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("add_article")) ) 
		{ ret = "article/create_article"; }
		else { ret = "403"; }
		
		//----------------------------------------------------------------
		
		model.addAttribute("article", null);
		
		model.addAttribute("cat_produit", catpRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("cat_client", catclientRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("emballage", embRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("pesage", pesRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("tva", tvaRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unite_mesure", unite_mesureRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("magasin", magRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		return ret;
		
	}
	
	//---------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/edit_art")
	public String edit_article(HttpServletRequest request,
						 @RequestParam("id_art") long id_art,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("edit_article")) ) 
		{ ret = "article/create_article"; }
		else { ret = "article/info_article"; }
		
		//----------------------------------------------------------------
		
		model.addAttribute("article", artRepo.getOne(id_art));
		
		//----------------------< GET PRICES BY CATEGORY >-------------------------------------------
		
		List<category_client> list_ct = catclientRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
		List<prixUnitaire_article_categoryClient> list_prix = prix_u_art_catcRepo.get_prices_by_CatClient(artRepo.getOne(id_art));
		
		for(int i=0;i<list_ct.size();i++) {
			
			boolean wd = false;
			
			article art = null;
			
			for(int j=0; j<list_prix.size(); j++ ) {
				
				if(list_ct.get(i).equals(list_prix.get(j).getCategory_client())) {
					
					wd = true;
					
				}
				
				art = list_prix.get(j).getArticle();
				
			}
			
			if(wd==false) {
				
				prixUnitaire_article_categoryClient pip = new prixUnitaire_article_categoryClient(art, list_ct.get(i), -1, tvaRepo.getOne( (long)-1 ) );
				
				pip.setId((long)0);
				
				list_prix.add(pip );
				
			}
			
		}
		
		model.addAttribute("prices", list_prix);
		
		//---------------------------------< END -- GET PRICES BY CATEGORY >------------------------------------------------------
		
		List <Magasin> list_mag = mag_artRepo.get_magasin_by_article(artRepo.getOne(id_art));
		
		String ids="";
		
		for (int i = 0; i < list_mag.size(); i++) {
			
			ids = ids+list_mag.get(i).getId()+"/";
			
		}
		
		model.addAttribute("magasins", ids);
			
		model.addAttribute("cat_produit", catpRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("cat_client", catclientRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("emballage", embRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("pesage", pesRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("tva", tvaRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unite_mesure", unite_mesureRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("magasin", magRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		return ret;
		
	}
	
	//---------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/create_article",method=RequestMethod.POST)
	public String insert_article_DB(HttpServletRequest req,
		//@RequestParam("cat_prod") long cat_prod,
		//@RequestParam("sous_cat_prod") long sous_cat_prod,
		@RequestParam("prod") long prod,
		@RequestParam("magasin") long [] id_magasin,
		@RequestParam("emb_prod") long emb_prod,
		@RequestParam("pesage_prod") long pesage_prod,
		@RequestParam("code_art") String code_art,
		@RequestParam("tva") long [] id_tva,
		@RequestParam("unite_mesure_vente") long id_unite_mesure,
		@RequestParam("cat_client") long [] cat_client,
		@RequestParam("prix_category") double [] prix_category,
		@RequestParam("subvention") String check,
		@RequestParam("consignation") String check_c,
		@RequestParam("lib") String lib,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		System.out.println(id_magasin);
		
		for(int i=0;i<id_magasin.length;i++) {
			
			System.out.println("==>"+id_magasin[i]);
			
		}
		
		String ret = "succes";
		
		get_time_date gtd = new get_time_date();
		
		//category_produit cat_produit = catpRepo.getOne(cat_prod);
		
		//sous_category_produit sous_cat_produit = souscatpRepo.getOne(sous_cat_prod);
		
		produit produit = prodRepo.getOne(prod);
		
		emballage_produit emb_produit = embRepo.getOne(emb_prod);
		
		pesage_produit pes_produit = pesRepo.getOne(pesage_prod);
		
		boolean sub = (check.equals("on")) ? true : false;
		
		boolean consignation = (check_c.equals("on")) ? true : false;
		
		//--------------------------
		
		article art_if_code_existe = artRepo.if_code_art_exist(code_art);
		
		if(art_if_code_existe==null) {
			
			article art_if_same_specs = artRepo.if_art_same_spec_exist(produit, emb_produit, pes_produit, sub);
			
			if(art_if_same_specs==null) {
				
				article art = new article(code_art, produit, emb_produit, pes_produit, "", 0, gtd.get_date(),
						unite_mesureRepo.getOne(id_unite_mesure), sub, lib, consignation);
				
				artRepo.save(art);
				artRepo.flush();
				
				//-------------------- tracking operation -----------------------------------
				
				trk.add_track("article", "Ajout nouveau article", art.getId(), user);
				
				//-------------------- tracking operation -----------------------------------
				
				for(int i=0;i<id_magasin.length;i++) {
					
					magasin_article mag_art = new magasin_article(art, magRepo.getOne(id_magasin[i]));
					
					mag_artRepo.save(mag_art);mag_artRepo.flush();
					
					//-------------------- tracking operation -----------------------------------
					
					trk.add_track("magasin_article", "Ajout magasin ou l'article peut etre chargé", mag_art.getId(), user);
					
					//-------------------- tracking operation -----------------------------------
					
				}
				
				for(int i=0;i<cat_client.length;i++) {
					
					category_client cat_c = catclientRepo.getOne(cat_client[i]);
					
					tva tva = tvaRepo.getOne(id_tva[i]);
					
					//if(prix_category[i] != -1) {
					
						prixUnitaire_article_categoryClient prix_u_c = new prixUnitaire_article_categoryClient(art, cat_c, prix_category[i], tva);
						
						prix_u_art_catcRepo.save(prix_u_c);prix_u_art_catcRepo.flush();
						
						//-------------------- tracking operation -----------------------------------
						
						trk.add_track("prixUnitaire_article_categoryClient", "Ajout prix d'article pour catagory client", prix_u_c.getId(), user);
						
						//-------------------- tracking operation -----------------------------------
					
					//}
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
		@RequestParam("magasin") long [] id_magasin,
		@RequestParam("pesage_prod") long pesage_prod,
		@RequestParam("unite_mesure_vente") long id_unite_mesure,
		@RequestParam("code_art") String code_art,
		//@RequestParam("code_comptable") String code_comptable,
		@RequestParam("tva") long [] id_tva,
		@RequestParam("id_prix_u") long [] id_prix_u,
		@RequestParam("prix_category") double [] prix_category,
		@RequestParam("subvention") String check,
		@RequestParam("lib") String lib,
		@RequestParam("cat_client") long [] cat_client,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		String ret = "succes";
		
		//get_time_date gtd = new get_time_date();
		
		//category_produit cat_produit = catpRepo.getOne(cat_prod);
		
		//sous_category_produit sous_cat_produit = souscatpRepo.getOne(sous_cat_prod);
		
		produit produit = prodRepo.getOne(prod);
		
		emballage_produit emb_produit = embRepo.getOne(emb_prod);
		
		pesage_produit pes_produit = pesRepo.getOne(pesage_prod);
		
		unite_mesure unite_m = unite_mesureRepo.getOne(id_unite_mesure);
		
		boolean sub = false;
		
		if(check.equals("on")) {
			
			sub = true;
			
		}
		
		//--------------------------
		
		//article art_if_code_existe = artRepo.if_code_art_exist(code_art);
		
		article art = artRepo.getOne(id_art);
		
		//System.out.println("db code = "+art.getCode()+" || code saisie = "+code_art);
		
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
				//art.setMagasin_stock(magasin);
				art.setSubvension(sub);
				art.setLibelle(lib);
				
				artRepo.save(art);artRepo.flush();
				
				//-------------------- tracking operation + back up -----------------------------------
				
				article_backup art_b = new article_backup(art, user);
				art_bRepo.save(art_b);art_bRepo.flush(); 
				
				trk.add_track("article", "modification article", art.getId(), user);
				
				//-------------------- tracking operation ---------------------------------------------
				
				List <magasin_article> list_magasin_encours = mag_artRepo.list_magasin_by_article(art); 
				
				for (int i = 0; i < list_magasin_encours.size(); i++) {
					
					mag_artRepo.delete(list_magasin_encours.get(i));
					
				}
				
				for (int i = 0; i < id_magasin.length; i++) {
					
					magasin_article mag_art = new magasin_article(art, magRepo.getOne(id_magasin[i]));
					
					mag_artRepo.save(mag_art);mag_artRepo.flush();
					
				}
				
				for(int i=0;i<id_prix_u.length;i++) {
					
					prixUnitaire_article_categoryClient prix_u_c = null;
					
					if(id_prix_u[i]==0) {
						
						prix_u_c = new prixUnitaire_article_categoryClient(art, 
								catclientRepo.getOne(cat_client[i]), prix_category[i], tvaRepo.getOne(id_tva[i]));
						
						prix_u_art_catcRepo.save(prix_u_c);prix_u_art_catcRepo.flush();
						
					}
					else {
						
						prix_u_c = prix_u_art_catcRepo.getOne(id_prix_u[i]);
						
						prix_u_c.setPrix(prix_category[i]);
						
						tva tva = tvaRepo.getOne(id_tva[i]);
						
						prix_u_c.setTva(tva);
						
						prix_u_art_catcRepo.save(prix_u_c);prix_u_art_catcRepo.flush();
						
					}
					
					//-------------------- tracking operation + back up -----------------------------------
					
					prixUnitaire_article_categoryClient_backup pcb = new prixUnitaire_article_categoryClient_backup(prix_u_c, user);
					
					prix_c_bRepo.save(pcb);prix_c_bRepo.flush(); 
					
					trk.add_track("prixUnitaire_article_categoryClient", "Modification prix d'article pour catagory client", prix_u_c.getId(), user);
					
					//-------------------- tracking operation ---------------------------------------------
					
				}
				
			//}
			//else {
				
			//	ret = "same_specs";
				
			//}
			
		}
		else {
			
			ret = "code_existe";
			
		}
		
		return "redirect:/edit_art?ret="+ret+"&id_art="+id_art;
		
	}
	
}
