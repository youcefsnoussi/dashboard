package com.commercial.webController.vente;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_ristourne;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_ristourne_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_ristourneRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_ristourne_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.services.generate_Doc;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class facture_ristourneController {
	
	@Autowired
	mouvementRepository mvmRepo;
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	prixUnitaire_article_categoryClient_Repository pu_a_ctRepo;
	
	@Autowired
	category_produitRepository cat_prodRepo;
	
	@Autowired
	facture_ristourneRepository fct_risRepo;
	
	@Autowired
	facture_ristourne_detailRepository fct_ris_dRepo;
	
	@Autowired
	track_operations trk;
	
	@Autowired
	clientRepository cltRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	public facture_ristourneController() {
		// TODO Auto-generated constructor stub
	}
	
	//--------------------------------------------------------------------
	
	@RequestMapping(value="/facture_ristourne")
	public String fct_ristourne(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/facture_ristourne";
		
		model.addAttribute("clt_rc", clt_rcRepo.findAll());
		
		model.addAttribute("articles", pu_a_ctRepo.get_articles_ristourne(cat_prodRepo.get_cat_prod_by_name("Ristourne")));
		
		System.out.println(pu_a_ctRepo.get_articles_ristourne(cat_prodRepo.get_cat_prod_by_name("Ristourne")).get(0).getArticle().getId());
		
		return ret;
		
	}
	
	//__________________________________________/°-POST-°\____________________________________________________________________
	
	@RequestMapping(value="/new_facture_ristourne",method=RequestMethod.POST)
	public String new_facture_ristourne(HttpServletRequest req,
			@RequestParam("rc_client") long id_rc_client,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,
			
			@RequestParam("art") long [] article,
			@RequestParam("id_um") long [] id_unite_mesure,
			@RequestParam("montant_ht_art") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam("tva_art") double [] tva_art,
			@RequestParam("prix_unitaire") double [] prix_u_ht,
			@RequestParam("qte") double [] quantite,
			//@RequestParam("tva_art") double [] montant_tva_art,
			
			@SessionAttribute("user") users user){
		
			facture_ristourne  last_fact_ris = fct_risRepo.findFirst1ByOrderByNumeroDesc();
			
			String last_number = "";
			
			if(last_fact_ris!=null) {
				
				last_number = last_fact_ris.getNumero();
				
			}
			
			get_time_date gtd = new get_time_date();
			
			numerotation_by_year nby = new numerotation_by_year();
			
			String numero = nby.return_num_facture_ristourne(last_number, user.getUnite().getId());
			
			String today = gtd.get_date();
			
			String time = gtd.get_time();
			
			//-------------- update sold client
			
			client clt = clt_rcRepo.getOne(id_rc_client).getClient();
			
			double sold_encours_clt = clt.getSold_encours();
			
			double new_sold_clt = sold_encours_clt - montant_ttc;
			
			clt.setSold_encours(new_sold_clt);
			
			cltRepo.save(clt);cltRepo.flush();
			
			//-------------- update sold RC
			
			registre_commerce rc = clt_rcRepo.getOne(id_rc_client).getRegistre_commerce();
			
			double sold_encours_rc = rc.getSold_encours();
			
			double new_sold_rc = sold_encours_rc - montant_ttc;
			
			rc.setSold_encours(new_sold_rc);
			
			rcRepo.save(rc);rcRepo.flush();
			
			//--------------------------------------insert to facture ristourne table ------
			
			facture_ristourne fact_ris = new facture_ristourne(clt, rc, today, time, numero, montant_ht, montant_tva, montant_ttc, "", user);
			
			
			fct_risRepo.save(fact_ris); fct_risRepo.flush();
			
			//------------------------------------------------- insert details facture table
			
			for(int i=0;i<article.length;i++) {
				
				System.out.println("id_art=========>"+article[i]);
				
				if(quantite[i]!=0) {
					
					facture_ristourne_detail fact_ris_d = new facture_ristourne_detail(fact_ris, artRepo.getOne(article[i]), quantite[i], 										prix_u_ht[i], montant_ht_art[i], tva_art[i], (montant_ht_art[i]*(tva_art[i]/100)), (montant_ht_art[i] + 										(montant_ht_art[i]*(tva_art[i]/100))) );
					
					fct_ris_dRepo.save(fact_ris_d);fct_ris_dRepo.flush();
					
				}
				
			}
			
			//------------------------------------------------ insert into mouvement table
			
			mouvement mvm = new mouvement(clt, rc, montant_ttc, "Facture Ristourne", fact_ris.getId(), gtd.get_date(), gtd.get_time(), "", 										sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
			
			mvmRepo.save(mvm);mvmRepo.flush();
			
			//------------------------------- Track operation
			
			trk.add_track("facture_ristourne", "creation facture ristourne", fact_ris.getId(), user);
			
			//------------------------------------------------ END
			
			return "redirect:/print_fact_ris?id_fact_ris="+fact_ris.getId();
		
	}
	
	//---------------------------- PRINT 
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_fact_ris")
	public String print_ristourne(HttpServletRequest request,
						 @RequestParam("id_fact_ris") long id_fact_ris,
						 @SessionAttribute("user") users user,
						 Model model){
		
		facture_ristourne fact_ris = fct_risRepo.getOne(id_fact_ris);
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		pdf = gd.generate_Fact_ristourne(fact_ris);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//--------------------------------------------------------------------------------
	
}
