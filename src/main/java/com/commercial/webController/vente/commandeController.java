package com.commercial.webController.vente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.repository.MagasinRepository;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.article_consignation_relationRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.emballage_produitRepository;
import com.commercial.entities.schema.article.repository.magasin_articleRepository;
import com.commercial.entities.schema.article.repository.pesage_produitRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.article.repository.produitRepository;
import com.commercial.entities.schema.article.repository.rc_consignationRepository;
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.commande;
import com.commercial.entities.schema.profoma_cmd_bl_fact.commande_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.prof_cmd_bl_fact_client_rc_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commandeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commande_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.ConnectionParc;
import com.commercial.functions.Connection_peseur;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.services.ConsignationService;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class commandeController {
	
	public commandeController() {
		// TODO Auto-generated constructor stub
	}
	
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
	
	@Autowired
	magasin_articleRepository magRepo;
	
	@Autowired
	MagasinRepository magasinRepo;
	
	@Autowired
	track_operations trk;
	
	@Autowired
	rc_consignationRepository rccRepo;
	
	//---------------------------------------------
	
	@Autowired
	commandeRepository cmdRepo;
	
	@Autowired
	commande_detailRepository cmd_detRepo;
	
	@Autowired
	bon_livraisonRepository bon_lRepo;
	
	@Autowired
	bon_livraison_detailRepository bon_l_dRepo;
	
	@Autowired
	prof_cmd_bl_fact_client_rc_avoirRepository grpRepo;
	
	@Autowired
	ConnectionParc con_parc;
	
	@RequestMapping(value="/commande")
	public String cmd(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/commande";
		
		get_time_date gtd = new get_time_date();
		
		//model.addAttribute("clients", clientRepo.client_active_only());
		
		List <client_registreCommerce> clt_rc = clt_rcRepo.ListRCwithCLIENT_active(gtd.get_date());
		
		List <client_registreCommerce> clt_rc_ret = new ArrayList<client_registreCommerce>();
		
		article transport= artRepo.findByCode("1000");
		
		for(int i=0; i<clt_rc.size();i++) {
			
			client_registreCommerce cl_rc = clt_rc.get(i);
			
			List <bon_livraison> list_bl = bon_lRepo.get_bl_encours_by_rc(cl_rc.getRegistre_commerce());
			
			double montant = 0;
			
			for(int j=0;j<list_bl.size();j++) {
				
				montant += list_bl.get(j).getMontant_ttc();
				
			}
			
			double sold_encours = cl_rc.getRegistre_commerce().getSold_encours();
			
			sold_encours = sold_encours + montant;
			
			cl_rc.getRegistre_commerce().setSold_encours(sold_encours);
			
			clt_rc_ret.add(cl_rc);
			
		}
		
		model.addAttribute("rcs_clt", clt_rc_ret);
		
		model.addAttribute("mode_paiements", mode_payRepo.findAll(Sort.by(Sort.Direction.ASC,"id")));
		
		model.addAttribute("magasin", magasinRepo.findAll(Sort.by(Sort.Direction.ASC,"id")));
		
		List<Map<String, String>> vehs = con_parc.get_vehicules();
		
		model.addAttribute("vehicules", vehs);
		model.addAttribute("transport",transport);
		
		boolean remise_fact = false;
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getIds_banned().contains("remise_fact")) {
			remise_fact = true; 
		 }
		
		model.addAttribute("remise_fact", remise_fact);
		
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
	
	//__________________________________________POST____________________________________________________________________
	
	@Autowired
	ConsignationService consService;
	
	@Autowired
	article_consignation_relationRepository art_cons_relRepo;
	
	@RequestMapping(value="/new_commande_post",method=RequestMethod.POST)
	public String new_commande(HttpServletRequest req,
			//@RequestParam("id_client") long id_client,
			@RequestParam("id_rel_rc_clt") long id_relation_rc_clt,
			@RequestParam("matricule") String matricule_camion,
			@RequestParam("mode_reg") long id_mode_reg,
			@RequestParam("tva") double tva,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht, //----> lebes
			@RequestParam("pourc_reduction") double pourc_reduction, //----> lebes
			@RequestParam("montant_reduction") double mnt_reduction, //----> lebes
			@RequestParam("total_ht_net") double montant_ht_net, //----> lebes
			@RequestParam("observation") String observation,
			
			@RequestParam("art") long [] article,
			@RequestParam("id_um") long [] id_unite_mesure,
			@RequestParam("id_magasin") long [] id_magasin,
			@RequestParam("montant_ht_art") double [] montant_ht_art,
			@RequestParam("tva_art") double [] tva_art,
			@RequestParam("prix_unitaire") double [] prix_u_ht,
			@RequestParam("qte") double [] quantite,
			@RequestParam("montant_tva_art") double [] montant_tva_art,
			@RequestParam("montant_redux_art_val") double [] montant_redux_art_val,
			@RequestParam("montant_redux_art_pourc") double [] montant_redux_art_pourc,
			@RequestParam("montant_net_ht_art") double [] montant_net_ht_art,
			@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam("art_consign") List<String> art_consign,
			@RequestParam("type_vehicule") Integer type_vehicule,
			@SessionAttribute("user") users user){
		
		boolean remise_fact = false;
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getIds_banned().contains("remise_fact")) {
			remise_fact = true; 
		 }
		if(!remise_fact && (pourc_reduction>0.0 || mnt_reduction>0.0)) {
			String ret =  "403";
			return ret;
		}
		
			commande last_cmd = cmdRepo.findFirst1ByOrderByNumeroDesc();
			
			String last_number = "";
			
			if(last_cmd!=null) {
				
				last_number = last_cmd.getNumero();
				
			}
			
			get_time_date gtd = new get_time_date();
			
			numerotation_by_year nby = new numerotation_by_year();
			
			String numero_cmd = nby.return_num_commande(last_number);
			
			String today = gtd.get_date();
			
			String time = gtd.get_time();
			
			client_registreCommerce clt_rc = clt_rcRepo.getOne(id_relation_rc_clt);
			
			client clt = clt_rc.getClient();
			
			registre_commerce rc = clt_rc.getRegistre_commerce();
			
			commande cmd = new commande(today, time, numero_cmd, montant_ht, montant_tva, montant_ttc, "", clt, rc, null, user, false, 
					matricule_camion ,mode_payRepo.getOne(id_mode_reg), clt_rc, pourc_reduction, mnt_reduction, observation, montant_ht_net);
			
			cmdRepo.save(cmd);cmdRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("commande", "Creation commande", cmd.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			System.out.println("articles lengh = " + article.length);
			for(int i=0;i<article.length;i++) {
				System.out.println("i = " + i);
				if(quantite[i]!=0) {
					System.out.println("qtt not null i = " + i);
					
					commande_detail cmd_d = new commande_detail(cmd, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i], montant_ht_art[i], 
							montant_tva_art[i], tva_art[i], umRepo.getOne(id_unite_mesure[i]), montant_redux_art_pourc[i], 
							montant_redux_art_val[i], montant_net_ht_art[i], montant_ttc_art[i]);
					
					cmd_detRepo.save(cmd_d);cmd_detRepo.flush();
					
				}
				
			}
			
			bon_livraison last_bl = bon_lRepo.findFirst1ByOrderByNumeroDesc();
			
			last_number = "";
			
			if(last_bl!=null) {
				
				last_number = last_bl.getNumero();
				
			}
			
			String numero_bl = nby.return_num_BonLivraison(last_number);
			/*
			bon_livraison bl = new bon_livraison(clt, rc, today, time, numero_bl, matricule_camion, "", cmd, null, user, 0, montant_ht, 
					pourc_reduction, mnt_reduction, montant_ht_net, montant_tva, montant_ttc, false);
			*/
			bon_livraison bl = new bon_livraison(clt, rc, today, time, numero_bl, matricule_camion, cmd, user);
			
			bon_lRepo.save(bl);bon_lRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("bon_livraison", "Génération de bon livraison a partir de la commande", bl.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			//boolean if_son = false; //----------------> testi ila son bach ninsiri f tabla ta3 nkhala
			
			for(int i=0;i<article.length;i++) {
				
				if(quantite[i]!=0) {
					 /*
					bon_livraison_detail bl_d = new bon_livraison_detail(bl, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i], 
							montant_ht_art[i], montant_tva_art[i], tva_art[i], umRepo.getOne(id_unite_mesure[i]), false, 
							magasinRepo.getOne(id_magasin[i]), montant_redux_art_pourc[i], montant_redux_art_val[i], montant_net_ht_art[i], 
							montant_ttc_art[i]);
					*/
					
					//System.out.println("art consignation for "+i+" -->"+ art_consign.get(i));
					article art = artRepo.getOne(article[i]);
					
					double taux_tva = 0.0;
					double prix_u = 0.0;
					
					
					
					//--------------------> TEST IS RC EXONERE TVA <------------------------------
					
					if(!art.getCode().equals("1000")) {
						prixUnitaire_article_categoryClient pu_obj = 
								prix_u_art_catcRepo.get_prix_articles_by_CatClient_Object(rc.getCategory(), artRepo.getOne(article[i]));
						
					 taux_tva = (rc.getTva()==0) ? 0 : pu_obj.getTva().getTaux_tva();
					
					//----------------------------------------------------------------------------
					
					 prix_u = (!artRepo.getOne(article[i]).isConsignation()) ? pu_obj.getPrix() 
							: rccRepo.getRcConsignation(rc, artRepo.getOne(article[i])).getPrix_u_ht();
					 }else {
						 taux_tva = 19.0;
						 prix_u =  con_parc.getPriceByTypeAndWilaya(type_vehicule,rc.getWilaya().getId());
					 }
					
					bon_livraison_detail bl_d = new bon_livraison_detail(bl, artRepo.getOne(article[i]), quantite[i],
							prix_u, taux_tva, montant_redux_art_val[i], artRepo.getOne(article[i]).getUnite_mesure_vente(),
							magasinRepo.getOne(id_magasin[i]));
					
					bon_l_dRepo.save(bl_d);bon_l_dRepo.flush();
					
					if(art_cons_relRepo.if_art_consigned(artRepo.getOne(article[i])).size()!=0) {
					
						consService.consignationBL(bl, art_consign.get(i), rc, artRepo.getOne(article[i]), quantite[i], 
									magasinRepo.getOne(id_magasin[i]));
					
					}
					
					/*
					article art = artRepo.getOne(article[i]);
					
					if(art.getProduit().getSous_category_produit().getCategory_produit().getId()==5) {
						
						if_son = true;
						
					}
					*/
				}
				
			}
			
			//-------------------- calcule total from detail bl and put it in BL -------------
			
			List<Double[]> sum_details = bon_l_dRepo.get_sum_for_bl(bl);
			
			bl.setMontant_ht(sum_details.get(0)[0]); //sum_details[0]
			bl.setValeur_reduction(sum_details.get(0)[1]);
			bl.setMontant_ht_net(sum_details.get(0)[2]);
			bl.setTva(sum_details.get(0)[3]);
			bl.setMontant_ttc(sum_details.get(0)[4]);
			
			bl.setPourcentage_reduction( (sum_details.get(0)[1] * 100) / sum_details.get(0)[0] ); 
			
			bon_lRepo.save(bl);bon_lRepo.flush();
			
			//------------------------------------------------ insert table regroupment
			
			prof_cmd_bl_fact_client_rc_avoir grp = new prof_cmd_bl_fact_client_rc_avoir(clt_rc, null, cmd, bl, null, null);
			
			grpRepo.save(grp); grpRepo.flush();
			
			//-------------------------- Partie palette --------------------------------
			/*
			if(bl.getClient().isVentePalette()) {
				
				PalServ.addingPaletteToBL(bl);
				
			}
			*/
			//----------------------------------------------- --------------------------
			
			//-------------------------------------- INSERT F TABLE NKHALA MYSQL --------------------<
			
			//if(if_son==true && user.getUnite().getIdentifiant()==1) {
				
				Connection_peseur cp = new Connection_peseur();
				
				if(cp.getconnection()!=null) {
					
					cp.function_son_bl(bl.getNumero(), bl.getDate());
					
				}
				
			//}
			
			//---------------------------------------------------------------------------------------
			
			return "redirect:/commande?id_bl="+bl.getId()+"&num_bl="+bl.getNumero()+"&type=bl";
		
	}
	
}
