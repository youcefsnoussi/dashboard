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
import com.commercial.entities.schema.article.repository.emballage_produitRepository;
import com.commercial.entities.schema.article.repository.pesage_produitRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.article.repository.produitRepository;
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;

@Controller
@SessionAttributes("user")

public class factureController {
	
	@Autowired
	banqueRepository banqueRepo;
	
	@Autowired
	type_reglementRepository type_rRepo;
	
	@Autowired
	category_clientRepository cat_clientRepo;
	
	@Autowired
	clientRepository clientRepo;
	
	@Autowired
	uniteRepository uniteRepo;
	
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
	registre_commerceRepository rcRepo;
	
	@Autowired
	mode_paiementRepository mode_payRepo;
	
	@Autowired
	unite_mesureRepository umRepo;
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	//---------------------------------------------
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_detRepo;
	
	@Autowired
	prof_cmd_bl_fact_client_rc_avoirRepository grpRepo;
	
	@Autowired
	mouvementRepository mvmRepo;
	
	public factureController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/fact")
	public String new_facture(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/facture";
		
		model.addAttribute("clients", clientRepo.client_active_only());
		
		model.addAttribute("mode_paiements", mode_payRepo.findAll());
		//model.addAttribute("articles", artRepo.findAll());
		
		//model.addAttribute("cat_client", cat_clientRepo.findAll());
		
		//model.addAttribute("unite", uniteRepo.findAll());
		/*
		String s = user.getRole().getIds_banned();
		
		if(s!=null && s.contains("no_add_client")) {
			
			ret = "403";
			
		}
		*/
		return ret;
		
	}
	
	//-----------------------------------------********************------------------- POST -----------------------
	
	//__________________________________________POST____________________________________________________________________
	
		@RequestMapping(value="/new_facture_post",method=RequestMethod.POST)
		public String insert_new_payment(HttpServletRequest req,
				@RequestParam("id_client") long id_client,
				@RequestParam("id_rel_rc_clt") long id_relation_rc_clt,
				@RequestParam("matricule") String matricule_camion,
				@RequestParam("mode_reg") long id_mode_reg,
				@RequestParam("tva") double tva,
				@RequestParam("total_tva") double montant_tva,
				@RequestParam("total_ttc") double montant_ttc,
				@RequestParam("total_ht") double montant_ht,
				@RequestParam("pourc_reduction") double pourc_reduction,
				
				@RequestParam("art") long [] article,
				@RequestParam("id_um") long [] id_unite_mesure,
				@RequestParam("montant_ht_art") double [] montant_ht_art,
				//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
				@RequestParam("tva_art") double [] tva_art,
				@RequestParam("prix_unitaire") double [] prix_u_ht,
				@RequestParam("qte") double [] quantite,
				@RequestParam("tva_art") double [] montant_tva_art,
				
				@SessionAttribute("user") users user){
				/*
				facture  last_fact = factRepo.findFirst1ByOrderByNumeroDesc();
				
				String last_number = "";
				
				if(last_fact!=null) {
					
					last_number = last_fact.getNumero();
					
				}
				*/
				//get_time_date gtd = new get_time_date();
				
				//numerotation_by_year nby = new numerotation_by_year();
				
				//String numero_fact = nby.return_num_facture(last_number, (long) user.getUnite().getIdentifiant());
				
				//String today = gtd.get_date();
				
				//String time = gtd.get_time();
				
				//--------------- 
				
				client_registreCommerce clt_rc = clt_rcRepo.getOne(id_relation_rc_clt);
				
				//-------------- update sold client
				
				client clt = clientRepo.getOne(id_client);
				
				double sold_encours_clt = clt.getSold_encours();
				
				double new_sold_clt = sold_encours_clt + montant_ttc;
				
				clt.setSold_encours(new_sold_clt);
				
				clientRepo.save(clt);clientRepo.flush();
				
				//-------------- update sold RC
				
				registre_commerce rc = clt_rc.getRegistre_commerce();
				
				double sold_encours_rc = rc.getSold_encours();
				
				double new_sold_rc = sold_encours_rc + montant_ttc;
				
				rc.setSold_encours(new_sold_rc);
				
				rcRepo.save(rc);rcRepo.flush();
				
				//-------------- update sold RC_client relationship
				
				double sold_encours = clt_rc.getMontant_actuel();
				
				double new_sold = sold_encours + montant_ttc;
				
				clt_rc.setMontant_actuel(new_sold);
				
				clt_rcRepo.save(clt_rc);clt_rcRepo.flush();
				
				//--------------------------------------insert to facture table ------
				/*
				facture fact = new facture(clt, rc, clt_rc, today, time, numero_fact, 
										montant_ht, tva, matricule_camion, montant_ttc, montant_tva, 0, montant_ht, ""
										, null, mode_payRepo.getOne(id_mode_reg), user, false, montant_ttc, true, false, pourc_reduction);
				
				//new facture(client, registre_commerce, client_registrecommerce, date, time, numero, montant_ht, tva, matricule_camion, montant_ttc, montant_tva, link_pdf, bon_livraison, mode_paiement, users, etat_sold, sold_rest)
				
				factRepo.save(fact);fact_detRepo.flush();
				*/
				//------------------------------------------------- insert details facture table
				/*
				for(int i=0;i<article.length;i++) {
					
					if(quantite[i]!=0) {
						
						new facture_detail(facture, article, quantite, prix_u_ht, montant_ht, tva, montant_tva, montant_ttc, pourcentage_remise,
								montant_remise, montant_ht_net, unite_mesure)
						
						facture_detail fact_d = new facture_detail(fact, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i], montant_ht_art[i],
								tva_art[i], montant_tva_art[i], 0, umRepo.getOne(id_unite_mesure[i]));
						
						fact_detRepo.save(fact_d);fact_detRepo.flush();
						
					}
					
				}
				*/
				//------------------------------------------------ insert table regroupment
				/*
				prof_cmd_bl_fact_client_rc_avoir grp = new prof_cmd_bl_fact_client_rc_avoir(clt_rc, null, null, null, fact, null);
				
				grpRepo.save(grp); grpRepo.flush();
				*/
				//------------------------------------------------ insert into mouvement table
				/*
				mouvement mvm = new mouvement(clt, rc, montant_ttc, "Facture", fact.getId(), gtd.get_date(), gtd.get_time(), "", sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
				
				mvmRepo.save(mvm);mvmRepo.flush();
				*/
				//------------------------------------------------ Create QR Code img
				
				
				//------------------------------------------------ prepare and create PDF fact
				
				
				//------------------------------------------------ END
				
				return "redirect:/fact";
			
		}
	
	//-----------------------------------------********************------------------------------------------------
	
}
