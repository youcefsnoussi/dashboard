package com.commercial.restController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.emballage_produit;
import com.commercial.entities.schema.article.pesage_produit;
import com.commercial.entities.schema.article.produit;
import com.commercial.entities.schema.article.sous_category_produit;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.user_menu.users;
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
	
	//=============================================================== get all articles for parent select AJAX REQ ======================================================================
	
	@RequestMapping(value="/get_all_articles_for_parent")
	public List<Map<String, Object>> get_all_articles_for_parent() {
		
		List<article> allArticles = artRepo.findAll();
		List<Map<String, Object>> result = new ArrayList<>();
		
		for (article a : allArticles) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", a.getId());
			map.put("code", a.getCode());
			map.put("libelle", a.getLibelle());
			result.add(map);
		}
		
		return result;
	}
	
	//===============================================================================================================================================================
	

	//=============================================================== create-on-the-fly AJAX REQs ===================================================================

	/*
	 * The "add article" form lets a missing reference be created inline, so a
	 * new product does not force the user out to another screen and back.
	 *
	 * Restricted to role 1 (Admin) and role 2 (Responsable). Checked here, on
	 * the server: hiding the button in the page would not stop anyone calling
	 * these URLs directly. Gated on the role ID, not the name - there are two
	 * roles called "Responsable" (ids 2 and 12) and only id 2 is allowed.
	 */
	private boolean may_create(users user){
		if(user == null || user.getRole() == null || user.getRole().getId() == null) return false;
		long id = user.getRole().getId();
		return (id == 1L || id == 2L);
	}

	private Map<String, Object> denied(){
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("ok", false);
		r.put("error", "Vous n'avez pas le droit d'ajouter cet élément.");
		return r;
	}

	private Map<String, Object> missing(String what){
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("ok", false);
		r.put("error", what);
		return r;
	}

	private Map<String, Object> made(Long id, String label, boolean existed){
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("ok", true);
		r.put("existed", existed);
		r.put("id", id);
		r.put("label", label);
		return r;
	}

	//---------------------------------------------------------------- category

	@RequestMapping(value="/add_cat_prod")
	public Map<String, Object> add_cat_prod(@RequestParam("nom") String nom,
											@SessionAttribute("user") users user) throws IOException{

		if(!may_create(user)) return denied();

		String name = (nom == null) ? "" : nom.trim();
		if(name.isEmpty()) return missing("Le nom de la catégorie est obligatoire.");

		category_produit existing = cat_prodRepo.get_cat_prod_by_name(name);
		if(existing != null) return made(existing.getId(), existing.getNom_category(), true);

		category_produit saved = cat_prodRepo.save(new category_produit(name));
		return made(saved.getId(), saved.getNom_category(), false);

	}

	//------------------------------------------------------------ sous-category

	@RequestMapping(value="/add_sous_cat_prod")
	public Map<String, Object> add_sous_cat_prod(@RequestParam("nom") String nom,
												 @RequestParam("id_parent") Long id_parent,
												 @SessionAttribute("user") users user) throws IOException{

		if(!may_create(user)) return denied();

		String name = (nom == null) ? "" : nom.trim();
		if(name.isEmpty())    return missing("Le nom de la sous-catégorie est obligatoire.");
		if(id_parent == null) return missing("Choisissez d'abord une catégorie.");

		category_produit parent = cat_prodRepo.getOne(id_parent);

		/* same name under the same parent = the same thing */
		for(sous_category_produit sc : sous_cat_prodRepo.get_sousCat_by_cat(parent)){
			if(sc.getNom_sous_category() != null && sc.getNom_sous_category().trim().equalsIgnoreCase(name)){
				return made(sc.getId(), sc.getNom_sous_category(), true);
			}
		}

		sous_category_produit saved = sous_cat_prodRepo.save(new sous_category_produit(name, parent));
		return made(saved.getId(), saved.getNom_sous_category(), false);

	}

	//------------------------------------------------------------------ produit

	@RequestMapping(value="/add_prod")
	public Map<String, Object> add_prod(@RequestParam("nom") String nom,
										@RequestParam("id_parent") Long id_parent,
										@SessionAttribute("user") users user) throws IOException{

		if(!may_create(user)) return denied();

		String name = (nom == null) ? "" : nom.trim();
		if(name.isEmpty())    return missing("La désignation du produit est obligatoire.");
		if(id_parent == null) return missing("Choisissez d'abord une sous-catégorie.");

		sous_category_produit parent = sous_cat_prodRepo.getOne(id_parent);

		for(produit pr : prodRepo.get_prod_by_sousCat(parent)){
			if(pr.getDesignation() != null && pr.getDesignation().trim().equalsIgnoreCase(name)){
				return made(pr.getId(), pr.getDesignation(), true);
			}
		}

		produit saved = prodRepo.save(new produit(name, parent));
		return made(saved.getId(), saved.getDesignation(), false);

	}

	//---------------------------------------------------------------- emballage

	@RequestMapping(value="/add_emballage")
	public Map<String, Object> add_emballage(@RequestParam("nom") String nom,
											 @SessionAttribute("user") users user) throws IOException{

		if(!may_create(user)) return denied();

		String name = (nom == null) ? "" : nom.trim();
		if(name.isEmpty()) return missing("Le nom de l'emballage est obligatoire.");

		for(emballage_produit e : emb_prodRepo.findAll()){
			if(e.getNom_emballage() != null && e.getNom_emballage().trim().equalsIgnoreCase(name)){
				return made(e.getId(), e.getNom_emballage(), true);
			}
		}

		emballage_produit saved = emb_prodRepo.save(new emballage_produit(name));
		return made(saved.getId(), saved.getNom_emballage(), false);

	}

	//------------------------------------------------------------------ pesage

	@RequestMapping(value="/add_pesage")
	public Map<String, Object> add_pesage(@RequestParam("pesage") String pesage,
										  @RequestParam("unite") String unite,
										  @SessionAttribute("user") users user) throws IOException{

		if(!may_create(user)) return denied();

		String u = (unite == null) ? "" : unite.trim();
		if(u.isEmpty()) return missing("L'unité de pesage est obligatoire.");

		double val;
		try{ val = Double.parseDouble((pesage == null ? "" : pesage.trim()).replace(",", ".")); }
		catch(NumberFormatException ex){ return missing("Le pesage doit être un nombre."); }

		for(pesage_produit pp : pes_prodRepo.findAll()){
			if(pp.getUnite_pesage() != null
					&& pp.getUnite_pesage().trim().equalsIgnoreCase(u)
					&& pp.getPesage() == val){
				return made(pp.getId(), pp.getPesage() + " " + pp.getUnite_pesage(), true);
			}
		}

		pesage_produit saved = pes_prodRepo.save(new pesage_produit(val, u));
		return made(saved.getId(), saved.getPesage() + " " + saved.getUnite_pesage(), false);

	}

	//===============================================================================================================================================================

}
