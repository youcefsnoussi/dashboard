package com.commercial.webController.parametre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.article_consignation_relationRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.dynamic_data.repository.mouvement_consignationRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.RecalculeSold;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.services.ConsignationService;

@Controller

public class edit_factureController {
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_dRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository pu_a_ctRepo;
	
	@Autowired
	tva_Repository tvaRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository prix_u_art_catcRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	ConsignationService consService;
	
	@Autowired
	RecalculeSold rs;
	
	@Autowired
	article_consignation_relationRepository art_cons_relRepo;
	
	
	@Autowired
	mouvement_consignationRepository mvm_cRepo;
	
	public edit_factureController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/edit_fact")
	public String edit_facture(HttpServletRequest request,
						 @RequestParam(name="id_fact", defaultValue="0") long id_fact,
						 @SessionAttribute("user") users user,
						 Model model){
		
		if(id_fact==0) { return "parametre/edit_facture";}
		
		facture fact = (id_fact!=0) ? factRepo.getOne(id_fact) : new facture();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		fact.setDate(conv.convertion_MyDate_to_InputDate(fact.getDate()));
		
		model.addAttribute("fact", fact);
		
		model.addAttribute("detail_fact", fact_dRepo.get_facture_detail_without_cons(fact));
		
		registre_commerce rc = fact.getRegistre_commerce();
		
		List <prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(rc.getCategory());
		
		list_art.stream().forEach(pArt -> {
			pArt.setPrix(pArt.getPrix()*pArt.getArticle().getMultiplicator());
		});
		
		if (fact.getRegistre_commerce().getTva()==0) {
			
			list_art.forEach(prix_art -> {
				
				prix_art.setTva((tva) tvaRepo.findAll().stream().filter(tva -> tva.getTaux_tva()==0)
							.collect(Collectors.toList()).get(0));
				
			});
			
		}
		
		List<List<article>> list_art_cons = new ArrayList<>();
		
		List<String> list_art_cons_selected_id = new ArrayList<>();
		
		//List<bon_livraison_detail> bld_cons = bon_l_dRepo.get_fact_detail_articles_consignation(bon_lRepo.getOne(id_bl));
		
		String id_arts_cons = "";
		
		List<article> selected_arts_cons = new ArrayList<>();
		
		int index = 0;
		
		for(facture_detail fact_detail : fact_dRepo.get_facture_detail(fact) ) {
			
			if(index==0 && fact_detail.getArticle().isConsignation()==false) {
				
				selected_arts_cons = new ArrayList<>();
				
				id_arts_cons = "";
				
			}
			else if(index > 0 && index < fact_dRepo.get_facture_detail(fact).size()-1 && 
					fact_detail.getArticle().isConsignation()==false)	{
				
				list_art_cons.add(selected_arts_cons);
				
				list_art_cons_selected_id.add(id_arts_cons);
				
				selected_arts_cons = new ArrayList<>();
				
				id_arts_cons = "";
				
			}
			else if(index == fact_dRepo.get_facture_detail(fact).size()-1 && 
					fact_detail.getArticle().isConsignation()==false) {
				
				list_art_cons.add(selected_arts_cons);
				
				list_art_cons_selected_id.add(id_arts_cons);
				
				list_art_cons.add(new ArrayList<>());
				
				list_art_cons_selected_id.add("");
				
			}
			
			if(fact_detail.getArticle().isConsignation()==true && 
					index < fact_dRepo.get_facture_detail(fact).size()-1) {
				
				selected_arts_cons.add(fact_detail.getArticle());
				
				id_arts_cons = id_arts_cons+fact_detail.getArticle().getId()+",";
				
			}
			else if(fact_detail.getArticle().isConsignation()==true && 
					index == fact_dRepo.get_facture_detail(fact).size()-1) {
				
				selected_arts_cons.add(fact_detail.getArticle());
				
				id_arts_cons = id_arts_cons+fact_detail.getArticle().getId()+",";
				
				list_art_cons.add(selected_arts_cons);
				
				list_art_cons_selected_id.add(id_arts_cons);
				
			}
			
			index++;
			
		}
		
		model.addAttribute("articles", list_art);
		
		model.addAttribute("fact_cons", list_art_cons);
		/*
		list_art_cons_selected_id.stream().forEach(s -> {
			list_art_cons_selected_id.set(list_art_cons_selected_id.indexOf(s), s.substring(0,s.lastIndexOf(",")));
		});*/
		
		list_art_cons_selected_id.stream().forEach(s -> {
			//System.out.println("s ===>("+s+")");
			if(!s.equals("")) {
				list_art_cons_selected_id.set(list_art_cons_selected_id.indexOf(s), s.substring(0,s.lastIndexOf(",")));
			}/*
			else {
				
				list_art_cons_selected_id.
				
			}*/
		});
		
		model.addAttribute("fact_cons_id", list_art_cons_selected_id);
		
		return "parametre/edit_facture";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/edit_facture_post",method=RequestMethod.POST)
	public String edit_facture_Post(HttpServletRequest req,
			@RequestParam("id_facture") long id_fact,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,
			@RequestParam("matricule") String matricule,
			@RequestParam("date") String date,
			@RequestParam("pourc_reduction") double pourc_reduction, //----> lebes
			@RequestParam("montant_reduction") double mnt_reduction, //----> lebes
			@RequestParam("total_ht_net") double montant_ht_net,
			
			@RequestParam(name="art", defaultValue = "0") long [] article,
			@RequestParam(name="id_um", defaultValue = "0") long [] id_unite_mesure,
			@RequestParam(name="montant_ht_art", defaultValue = "0") double [] montant_ht_art,
			@RequestParam(name="tva_art", defaultValue = "0") double [] tva_art,
			@RequestParam(name="prix_unitaire", defaultValue = "0") double [] prix_u_ht,
			@RequestParam(name="qte", defaultValue = "0") double [] quantite,
			@RequestParam("montant_tva_art") double [] montant_tva_art,
			@RequestParam("montant_redux_art_val") double [] montant_redux_art_val,
			@RequestParam("montant_redux_art_pourc") double [] montant_redux_art_pourc,
			@RequestParam("montant_net_ht_art") double [] montant_net_ht_art,
			@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam("art_consign") List<String> art_consign,
			
			@SessionAttribute("user") users user){
			
			facture fact = factRepo.getOne(id_fact);
			
			convert_string_to_date_util conv = new convert_string_to_date_util();
			
			String newDate = conv.convertion_InputDate_to_MyDate(date);
			
			//----------------- EDIT facture summary ---------------------
			
			fact.setMontant_ht(montant_ht);
			fact.setMontant_remise(mnt_reduction);
			fact.setPourcentage_reduction(pourc_reduction);
			fact.setMontant_ht_net(montant_ht_net);
			fact.setMontant_tva(montant_tva);
			fact.setMontant_ttc(montant_ttc);
			
			fact.setMatricule_camion(matricule);
			fact.setDate(newDate);
			
			//------------------------------------------------------------
			
			registre_commerce rc = fact.getRegistre_commerce();
			
			String ret;
			
			//-------------------- tracking operation -----------------------------------
			
			mvm_cRepo.list_all_mvmC_by_fact(fact).stream().forEach(mvmc ->{
				
				mvm_cRepo.delete(mvmc); mvm_cRepo.flush();
				
			});
			
			List <facture_detail> factD = fact_dRepo.get_facture_detail(fact);
			
			for (facture_detail fact_d : factD) {
				
				fact_dRepo.delete(fact_d);
				
				fact_dRepo.flush();
				
			}
			
			
			for(int i=0;i<quantite.length;i++) {
				
				if(article[i]!=0 && quantite[i]!=0) {
					
					if(!artRepo.getOne(article[i]).isConsignation()) {
					
						prixUnitaire_article_categoryClient pu_obj = 
							prix_u_art_catcRepo.get_prix_articles_by_CatClient_Object(rc.getCategory(), artRepo.getOne(article[i]));
						
						//--------------------> TEST IS RC EXONERE TVA <------------------------------
						
						double taux_tva = (rc.getTva()==0) ? 0 : pu_obj.getTva().getTaux_tva();
						
						//-------------------->	---------------------- <------------------------------
						
						facture_detail fact_d = new facture_detail(fact, artRepo.getOne(article[i]), 
								quantite[i] * artRepo.getOne(article[i]).getMultiplicator(), pu_obj.getPrix(), taux_tva, 
								montant_redux_art_val[i]);
						
						fact_dRepo.save(fact_d);fact_dRepo.flush();
						
						if(art_cons_relRepo.if_art_consigned(artRepo.getOne(article[i])).size()!=0 && 
								!art_consign.get(i).equals("") ) {
							
							consService.consignationFactEdit(fact, art_consign.get(i), rc, artRepo.getOne(article[i]),
									quantite[i]);
						
						}
					
					}
					else {
						
						consService.consignationFACT(fact, artRepo.getOne(article[i]), rc, quantite[i]);
						
					}
				}
				
			}
			
			//------------------------- edit BL --------------------------------------
			
			//-------------------- recalcule sold --------------------
			
			rs.recalcule_sold_rc_client("'"+fact.getRegistre_commerce().getCode()+"'", user.getUnite().getIdentifiant());
			
			ret="redirect:/edit_fact?id_fact="+id_fact;
				
			
			return ret;
		
	}
	
}
