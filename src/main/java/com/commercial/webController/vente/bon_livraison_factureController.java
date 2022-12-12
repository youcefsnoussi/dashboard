package com.commercial.webController.vente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.Magasin;
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
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.commande;
import com.commercial.entities.schema.profoma_cmd_bl_fact.commande_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.prof_cmd_bl_fact_client_rc_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commandeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commande_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.Connection_peseur;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.generateQRcode;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.services.ConsignationService;
import com.commercial.services.generate_Doc;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class bon_livraison_factureController {
	
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
	
	//---------------------------------------------
	@Autowired
	clientRepository cltRepo;
	
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_detRepo;
	
	@Autowired
	mouvementRepository mvmRepo;
	
	//-------------------------------------------
	
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
	bon_livraison_factureRepository blfRepo;
	
	@Autowired
	bon_livraison_facture_detailRepository blfdRepo;
	
	//----------------------------------------------------------
	
	@Autowired
	ConsignationService consService;
	
	@Autowired
	article_consignation_relationRepository art_cons_relRepo;
	
	@Autowired
	rc_consignationRepository rccRepo;
	
	public bon_livraison_factureController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/new_commande_bl_fact_post",method=RequestMethod.POST)
	public String new_commande_blf(HttpServletRequest req,
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
			@RequestParam("chauffeur") String chauffeur,
			
			@RequestParam("art") long [] article,
			@RequestParam("id_um") long [] id_unite_mesure,
			@RequestParam("id_magasin") long [] id_magasin,
			@RequestParam("montant_ht_art") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam("tva_art") double [] tva_art,
			@RequestParam("prix_unitaire") double [] prix_u_ht,
			@RequestParam("qte") double [] quantite,
			@RequestParam("montant_tva_art") double [] montant_tva_art,
			@RequestParam("montant_redux_art_val") double [] montant_redux_art_val,
			@RequestParam("montant_redux_art_pourc") double [] montant_redux_art_pourc,
			@RequestParam("montant_net_ht_art") double [] montant_net_ht_art,
			@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam("art_consign") List<String> art_consign,
			
			@SessionAttribute("user") users user){
			
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
			
			commande cmd = new commande(today, time, numero_cmd, montant_ht, montant_tva, montant_ttc, "", clt, rc, null, user, 
					false, matricule_camion ,mode_payRepo.getOne(id_mode_reg), clt_rc, pourc_reduction, mnt_reduction, observation,
					montant_ht_net);
			
			cmdRepo.save(cmd);cmdRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("commande", "Creation commande", cmd.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			for(int i=0;i<article.length;i++) {
				
				if(quantite[i]!=0) {
					
					commande_detail cmd_d = new commande_detail(cmd, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i], 
							montant_ht_art[i], montant_tva_art[i], tva_art[i], umRepo.getOne(id_unite_mesure[i]), 
							montant_redux_art_pourc[i], montant_redux_art_val[i], montant_net_ht_art[i], montant_ttc_art[i]);
					
					cmd_detRepo.save(cmd_d);cmd_detRepo.flush();
					
				}
				
			}
			
			bon_livraison_facture last_blf = blfRepo.findFirst1ByOrderByNumeroDesc();
			
			last_number = "";
			
			if(last_blf!=null) {
				
				last_number = last_blf.getNumero();
				
			}
			
			String numero_blf = nby.return_num_BonLivraisonFacture(last_number, (long)user.getUnite().getIdentifiant());
			/*
			bon_livraison_facture blf = new bon_livraison_facture(clt, rc, today, time, numero_blf, matricule_camion, cmd, user, 0, 
								montant_ht, montant_tva, montant_ttc, pourc_reduction, mnt_reduction, montant_ht_net, false, false,
								chauffeur);
			*/
			bon_livraison_facture blf = new bon_livraison_facture(clt, rc, today, time, numero_blf, matricule_camion, 
								cmd, user, chauffeur);
			
			blfRepo.save(blf);blfRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("bon_livraison_facture", "Génération de bon livraison a partir de la commande", blf.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			for(int i=0;i<article.length;i++) {
				
				if(quantite[i]!=0) {
					/*
					bon_livraison_facture_detail blfd = new bon_livraison_facture_detail(blf, artRepo.getOne(article[i]), 
							quantite[i], prix_u_ht[i], montant_ht_art[i], montant_tva_art[i], montant_ttc_art[i], 
							montant_redux_art_pourc[i], montant_redux_art_val[i], montant_net_ht_art[i], tva_art[i], user, 
							umRepo.getOne(id_unite_mesure[i]), false, magasinRepo.getOne(id_magasin[i]));
					*/
					prixUnitaire_article_categoryClient pu_obj = 
							prix_u_art_catcRepo.get_prix_articles_by_CatClient_Object(rc.getCategory(), artRepo.getOne(article[i]));
					
					//--------------------> TEST IS RC EXONERE TVA <------------------------------
					
					double taux_tva = (rc.getTva()==0) ? 0 : pu_obj.getTva().getTaux_tva();
					
					//----------------------------------------------------------------------------
					
					double prix_u = (!artRepo.getOne(article[i]).isConsignation()) ? pu_obj.getPrix() 
							: rccRepo.getRcConsignation(rc, artRepo.getOne(article[i])).getPrix_u_ht();
					
					bon_livraison_facture_detail blfd = new bon_livraison_facture_detail(blf, artRepo.getOne(article[i]), 
							quantite[i], prix_u, montant_redux_art_val[i], taux_tva,
							artRepo.getOne(article[i]).getUnite_mesure_vente(), magasinRepo.getOne(id_magasin[i]));
					
					blfdRepo.save(blfd);blfdRepo.flush();
					
					if(art_cons_relRepo.if_art_consigned(artRepo.getOne(article[i])).size()!=0) {
						
						consService.consignationBLF(blf, art_consign.get(i), rc, artRepo.getOne(article[i]), quantite[i], 
									magasinRepo.getOne(id_magasin[i]));
					
					}
					
				}
				
			}
			
			//-------------------------------------------- calcule total from detail blf and put it in BLF-----------------------
			
			List<Double[]> sum_details = blfdRepo.get_sum_for_blf(blf);
			
			blf.setMontant_ht(sum_details.get(0)[0]);
			blf.setMontant_remise(sum_details.get(0)[1]);
			blf.setMontant_ht_net(sum_details.get(0)[2]);
			blf.setMontant_tva(sum_details.get(0)[3]);
			blf.setMontant_ttc(sum_details.get(0)[4]);
			
			blf.setPourcentage_remise( (sum_details.get(0)[1] * 100) / sum_details.get(0)[0] ); 
			
			blfRepo.save(blf);blfRepo.flush();
			
			//------------------------- insert into MY SQL Peseur IF AIN ROMANA------------
			
			//if(user.getUnite().getIdentifiant()==1) {
				
				Connection_peseur cp = new Connection_peseur();
				
				if(cp.getconnection()!=null) {
					
					cp.insert_fct_to_peseur(numero_blf, today);
					
				}
				//------------------- zyada
				//cp.update_bl_fact_son(bl.getNumero(), fact.getNumero());
				
			//}
			
			//------------------------- END INSERT into MY SQL Peseur ---------------------------------------
			
			//---------------------------------------------------------------------------------------
			
			return "redirect:/commande?id_bl="+blf.getId()+"&num_bl="+blf.getNumero()+"&type=blf";
		
	}
	
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="/list_blf_no_f")
	public String list_blfs(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		get_time_date gtd = new get_time_date();
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List<bon_livraison_facture> blfs = new ArrayList<bon_livraison_facture>();
		
		if(start.equals("0")) {
			
			blfs = blfRepo.get_blf_non_factured_date(gtd.get_date(), gtd.get_date());
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			//System.out.println("start->"+start+" / end->"+end);
			
			blfs = blfRepo.get_blf_non_factured_date(start, end);
			model.addAttribute("start",start);
			model.addAttribute("end",end);
							
		}
		
		model.addAttribute("bls", blfs);
		
		List<bon_livraison_facture_detail> blfs_details = new ArrayList<bon_livraison_facture_detail>();
		
		for (bon_livraison_facture blf : blfs) {
			if(blf.getMontant_ht()!=null && blf.getMontant_ht()!=0) {
				
				blfs_details.add(blfdRepo.get_detail_by_blf(blf).get(0));
				
			}
		}
		
//		blfs.forEach(blf -> {
//			
//			if(blf.getMontant_ht()!=0) {
//				
//				blfs_details.add(blfdRepo.get_detail_by_blf(blf).get(0));
//				
//			}
//			
//		});
		
		model.addAttribute("bls_info", blfs_details);
		
		model.addAttribute("rcs_clt", clt_rcRepo.RC_bls_facture());
		
		boolean cancel_blf = false, edit_blf = false;
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getIds_banned().contains("cancel_blq")) 
		{ cancel_blf = true; }
		
		model.addAttribute("cancel_blf", cancel_blf);
		
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getIds_banned().contains("edit_blq")) 
		{ edit_blf = true; }
		
		model.addAttribute("edit_blf", edit_blf);
		
		//----------------------------------------------------------------
		
		return "vente/list_blf_no_facture";
		
	}
	
	//-------------------------------------------------------------------------------- list_blf_f
	
	@RequestMapping(value="/list_blf_f")
	public String list_blfs_facture(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		get_time_date gtd = new get_time_date();
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List<bon_livraison_facture> blfs = new ArrayList<bon_livraison_facture>();
		
		if(start.equals("0")) {
			
			blfs = blfRepo.get_blf_factured_date(gtd.get_date(), gtd.get_date());
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			//System.out.println("start->"+start+" / end->"+end);
			
			blfs = blfRepo.get_blf_factured_date(start, end);
			model.addAttribute("start",start);
			model.addAttribute("end",end);
							
		}
		
		model.addAttribute("bls", blfs);
		
		List<bon_livraison_facture_detail> blfs_details = new ArrayList<bon_livraison_facture_detail>();
		
		blfs.forEach(blf -> {
			
			//System.out.println("quant -> "+blfdRepo.get_detail_by_blf(blf).get(0).getQuantite());
			
			blfs_details.add(blfdRepo.get_detail_by_blf(blf).get(0));
			
		});
		
		model.addAttribute("bls_info", blfs_details);
		
		model.addAttribute("rcs_clt", clt_rcRepo.RC_bls_facture());
		
		boolean cancel_blf = false;
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getIds_banned().contains("cancel_blq")) 
		{ cancel_blf = true; }
		
		model.addAttribute("cancel_blf", cancel_blf);
		
		//----------------------------------------------------------------
		
		return "vente/list_blf_facture";
		
	}
	
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="/list_blf_by_rc")
	public String list_blfs_by_rc(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @RequestParam(value="rc_clt", defaultValue="0") long id_clt_rc,
						 @SessionAttribute("user") users user,
						 Model model){
		
		get_time_date gtd = new get_time_date();
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List<bon_livraison_facture> blfs = new ArrayList<bon_livraison_facture>();
		
		prixUnitaire_article_categoryClient price = new prixUnitaire_article_categoryClient();
		
		if(start.equals("0") && end.equals("0") && id_clt_rc == 0 ) {
			
			//blfs = blfRepo.get_blf_non_factured_date(gtd.get_date(), gtd.get_date());
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			blfs = blfRepo.get_blf_non_factured_rc_date(clt_rcRepo.getOne(id_clt_rc).getRegistre_commerce(), start, end);
			model.addAttribute("start",start);
			model.addAttribute("end",end);
			/*
			article art = blfdRepo.get_articles_from_blfs_detail(start, end);
			
			price = prix_u_art_catcRepo.get_instance_by_art_and_catClient(art, clt_rcRepo.getOne(id_clt_rc).getRegistre_commerce().getCategory());
			
			if(clt_rcRepo.getOne(id_clt_rc).getRegistre_commerce().getTva()==0) { //---------------------------> en cas exoneration
				
				price.setTva(tvaRepo.get_tva_0());
				
			}
			*/
		}
		
		model.addAttribute("bls", blfs);
		
		List<bon_livraison_facture_detail> blfs_details = new ArrayList<bon_livraison_facture_detail>();
		
		blfs.forEach(blf -> {
			
			blfs_details.add(blfdRepo.get_detail_by_blf(blf).get(0));
			
		});
		
		model.addAttribute("bls_info", blfs_details);
		
		model.addAttribute("rcs_clt", clt_rcRepo.RC_bls_facture());
		
		model.addAttribute("rc_clt_selcted", clt_rcRepo.getOne(id_clt_rc));
		
		model.addAttribute("price", price);
		
		return "vente/facturationOfBls";
		
	}
	
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="/edit_blf")
	public String edit_blf(HttpServletRequest request,
						 @RequestParam("id_blf") long id_blf,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		bon_livraison_facture blf = blfRepo.getOne(id_blf);
		
		model.addAttribute("blf", blf);
		
		model.addAttribute("detail_bl", blfdRepo.get_detail_by_blf(blf));
		
		List <prixUnitaire_article_categoryClient> list_art = prix_u_art_catcRepo.get_articles_by_CatClient
																	(blf.getRegistre_commerce().getCategory());
		
		if (blf.getRegistre_commerce().getTva()==0) {
			
			list_art.forEach(prix_art -> {
				
				prix_art.setTva((tva) tvaRepo.findAll().stream().filter(tva -> tva.getTaux_tva()==0).collect(Collectors.toList()).get(0));
				
			});
			
		}
		
		model.addAttribute("date_blf", conv.convertion_MyDate_to_InputDate(blf.getDate()));
		
		model.addAttribute("mags", magasinRepo.findAll());
		
		model.addAttribute("articles", list_art);
		
		return "vente/EditBlf";
		
	}
	
	//--------------------------------------------------------------------------------
	
	Connection_peseur con_p = new Connection_peseur();
	
	@RequestMapping(value="/edit_blf_post",method=RequestMethod.POST)
	public String edit_blf_Post(HttpServletRequest req,
			@RequestParam("id_blf") long id_blf,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,
			@RequestParam("matricule") String matricule,
			@RequestParam("chauffeur") String chauffeur,
			@RequestParam("date") String date,
			
			@RequestParam(name="art", defaultValue = "0") long [] article,
			@RequestParam(name="id_um", defaultValue = "0") long [] id_unite_mesure,
			@RequestParam(name="id_magasin",defaultValue = "0") long [] id_magasin,
			@RequestParam(name="montant_ht_art", defaultValue = "0") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam(name="tva_art", defaultValue = "0") double [] tva_art,
			@RequestParam(name="prix_unitaire", defaultValue = "0") double [] prix_u_ht,
			@RequestParam(name="qte", defaultValue = "0") double [] quantite,
			@RequestParam(name="tva_art", defaultValue = "0") double [] montant_tva_art,
			@RequestParam(name="montant_redux_art_val", defaultValue = "0") double [] montant_redux_art_val,
			
			@SessionAttribute("user") users user){
			
			bon_livraison_facture blf = blfRepo.getOne(id_blf);
			convert_string_to_date_util conv = new convert_string_to_date_util();
			/* 
			get_time_date gtd = new get_time_date();
			
			String today = gtd.get_date();
			
			String time = gtd.get_time();
			*/
			//------------------------- TEST PLAFOND
			/*
			int t = 0;
			*/
			registre_commerce rc = blf.getRegistre_commerce();
			
			String ret;
				
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("bon_livraison_facture", "Modification Bon livraison Facture", blf.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			//------------------------- edit BL --------------------------------------
			/*
			blf.setMontant_ht(montant_ht);
			blf.setMontant_ttc(montant_ttc);
			blf.setMontant_tva(montant_tva);
			blf.setMontant_ht_net(montant_ht);
			
			blf.setDate(conv.convertion_InputDate_to_MyDate(date));
			//blf.setTime(time);
			
			blf.setMatricule(matricule);
			
			blf.setChauffeur(chauffeur);
			
			blf.setUsers(user);
			
			blfRepo.save(blf); blfRepo.flush();
			*/
			//---------------------> UDATE OF BLF IN MYSQL DB <--------------------------------//
			
			Connection_peseur cp = new Connection_peseur();
			
			if(cp.getconnection()!=null) {
				
				cp.update_bl_when_edit(blf.getNumero(), blf.getDate());
				
			}
			
			//con_p.update_bl_when_edit(blf.getNumero(), conv.convertion_InputDate_to_MyDate(date));
			
			//---------------------------------------------------------------
			
			List <bon_livraison_facture_detail> bld = blfdRepo.get_detail_by_blf(blf);
			
			for(int i=0;i<bld.size();i++) {
				
				bon_livraison_facture_detail bl_d = bld.get(i);
				
				blfdRepo.delete(bl_d);blfdRepo.flush();
				
			}
			
			for(int i=0;i<quantite.length;i++) {
				
				if(article[i]!=0 && quantite[i]!=0) {
					/*
					bon_livraison_facture_detail bl_d = new bon_livraison_facture_detail(blf, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i],
							montant_ht_art[i], (montant_ht_art[i]*(tva_art[i]/100)), montant_ht_art[i] + (montant_ht_art[i]*(tva_art[i]/100)),
							0, 0, montant_ht_art[i], tva_art[i], null, umRepo.getOne(id_unite_mesure[i]), false, magasinRepo.getOne(id_magasin[i]));
					*/
					
					prixUnitaire_article_categoryClient pu_obj = 
							prix_u_art_catcRepo.get_prix_articles_by_CatClient_Object(rc.getCategory(), artRepo.getOne(article[i]));
					
					//--------------------> TEST IS RC EXONERE TVA <------------------------------
					
					double taux_tva = (rc.getTva()==0) ? 0 : pu_obj.getTva().getTaux_tva();
					
					//----------------------------------------------------------------------------
					
					bon_livraison_facture_detail blfd = new bon_livraison_facture_detail(blf, artRepo.getOne(article[i]), 
							quantite[i], pu_obj.getPrix(), montant_redux_art_val[i], taux_tva,
							artRepo.getOne(article[i]).getUnite_mesure_vente(), magasinRepo.getOne(id_magasin[i]));
					
					blfdRepo.save(blfd);blfdRepo.flush();
					
				}
				
			}
			
			//-------------------------------------------- calcule total from detail blf and put it in BLF-----------------------
			
			List<Double[]> sum_details = blfdRepo.get_sum_for_blf(blf);
			
			blf.setMontant_ht(sum_details.get(0)[0]);
			blf.setMontant_remise(sum_details.get(0)[1]);
			blf.setMontant_ht_net(sum_details.get(0)[2]);
			blf.setMontant_tva(sum_details.get(0)[3]);
			blf.setMontant_ttc(sum_details.get(0)[4]);
			
			blf.setPourcentage_remise( (sum_details.get(0)[1] * 100) / sum_details.get(0)[0] ); 
			
			blf.setDate(conv.convertion_InputDate_to_MyDate(date));
			
			blf.setMatricule(matricule);
			
			blf.setChauffeur(chauffeur);
			
			blf.setUsers(user);
			
			blfRepo.save(blf);blfRepo.flush();
			
			//---------------------------------> ------------------------------- <--------------------------
			
			String qr_code = generateQRcode.createQRcode(blf.getNumero(), "BL");
			
			String pdf = "";
			
			pdf = gd.generate_blf(blf, qr_code);
			
			ret="redirect:/display_pdf?file="+pdf;
				
			return ret;
	}
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="facture_blf")
	public String facture_bl_facture(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @RequestParam("id_rel_rc_clt") Long id_rc_clt,
						 Model model){
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		client_registreCommerce rc_clt =  clt_rcRepo.getOne(id_rc_clt);
		
		registre_commerce rc = rc_clt.getRegistre_commerce();
		
		date_d = conv.convertion_InputDate_to_MyDate(start);
		date_f = conv.convertion_InputDate_to_MyDate(end);
		
		List<Object[]> sample_all_detail_blf = blfdRepo.get_cumule_facture_blf(start, end, rc);
		
		List<Map<String, Object>> ret_all_details_blf = new ArrayList<Map<String, Object>>();
		
		double montant_ht=0, montant_tva=0, montant_ttc=0, montant_ht_net=0, montant_remise=0;
		
		for(int i=0; i<sample_all_detail_blf.size(); i++) {
			
			Object[] obj = sample_all_detail_blf.get(i);
			
			Map<String,Object> info = new  HashMap<String, Object>();
			
			info.put("prix_u_ht",(double)obj[0]);
			info.put("tva",(double)obj[1]);
			info.put("article",artRepo.getOne((long)obj[2]));
			info.put("magasin",magasinRepo.getOne((long)obj[3]));
			info.put("unite_mesure",umRepo.getOne((long)obj[4]));
			info.put("quantite",(double)obj[5]);
			info.put("montant_ht",(double)obj[6]);
			info.put("montant_tva",(double)obj[7]);
			info.put("montant_ttc",(double)obj[8]);
			info.put("montant_ht_net",(double)obj[9]);
			info.put("montant_remise",(double)obj[10]);
			info.put("pourcentage_remise", (double)obj[10] * ((double)obj[6] / 100) );
			
			montant_ht += (double)obj[6];
			montant_tva += (double)obj[7];
			montant_ttc += (double)obj[8];
			montant_ht_net += (double)obj[9];
			montant_remise += (double)obj[10];
			
			ret_all_details_blf.add(info);
			
		}
		/*
		System.out.println("montant HT -> "+montant_ht);
		
		System.out.println("montant TVA -> "+montant_tva);
		
		System.out.println("montant TTC -> "+montant_ttc);
		*/
		model.addAttribute("cumule_ble", ret_all_details_blf);
		
		model.addAttribute("montant_ht", montant_ht);
		
		model.addAttribute("montant_tva", montant_tva);
		
		model.addAttribute("montant_ttc", montant_ttc);
		
		model.addAttribute("montant_ht_net", montant_ht_net);
		
		model.addAttribute("montant_remise", montant_remise);
		
		model.addAttribute("rc_clt", rc_clt);
		
		//System.out.println("nom->"+rc_clt.getRegistre_commerce().getNom()+" / prenom ->"+rc_clt.getRegistre_commerce().getPrenom());
		
		//date_d = start; date_f = end;
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		String ret = "vente/info_blfacture";
		
		return ret;
		
	}
	
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="facture_blf_list_mais")
	public String facture_bl_by_ids_mais(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("id_rel_rc_clt") Long id_rc_clt,
						 @RequestParam("bls_f") String list_id_bls,
						 Model model){
		
		client_registreCommerce rc_clt =  clt_rcRepo.getOne(id_rc_clt);
		
		registre_commerce rc = rc_clt.getRegistre_commerce();
		
		String [] ids = list_id_bls.split("-");
		
		List<Long> idsBlfs = new ArrayList<Long>();
		
		if(!list_id_bls.isEmpty()) {
			
			for (String str : ids) {
				
				idsBlfs.add(Long.parseLong(str));
				
			}
			
		}
		
		//List<bon_livraison_facture_detail> sample_all_detail_blf = blfdRepo.get_detail_blfs_by_list_blf(idsBlfs);
		
		List<Object[]> sample_all_detail_blf = blfdRepo.get_article_cumuleQuant_from_detail_blfs(idsBlfs);
		
		List<Map<String, Object>> ret_all_details_blf = new ArrayList<Map<String, Object>>();
		
		double montant_ht=0, montant_tva=0, montant_ttc=0, montant_ht_net=0, montant_remise=0;
		
		for(int i=0; i<sample_all_detail_blf.size(); i++) {
			
			Object[] obj = sample_all_detail_blf.get(i);
			
			article art = artRepo.getOne((long) obj[0]);
			
			double quant = (double)obj[3];
			
			Magasin mag = magasinRepo.getOne((long)obj[1]);
			
			unite_mesure um = unite_mesureRepo.getOne((long)obj[2]);
			
			double price = prix_u_art_catcRepo.get_prix_articles_by_CatClient(rc.getCategory(), art);
			
			double tva = prix_u_art_catcRepo.get_instance_by_art_and_catClient(art, rc.getCategory()).getTva().getTaux_tva();
			
			if(rc.getTva()==0) { tva = 0;} //---------------------------> en cas exoneration
			
			double mnt_ht = quant * price;
			
			double mnt_rem = (double)obj[4];
			
			double mnt_ht_net = mnt_ht - mnt_rem;
			
			double mnt_tva = mnt_ht_net*( tva / 100);
			
			double mnt_ttc = mnt_ht + mnt_tva;
			
			if(ret_all_details_blf.isEmpty() || !ret_all_details_blf.get(ret_all_details_blf.size()-1).get("article").equals(art)) {
				
				Map<String,Object> info = new  HashMap<String, Object>();
				
				info.put("prix_u_ht", price);
				info.put("tva", tva);
				info.put("article", art);
				info.put("magasin", mag);
				info.put("unite_mesure", um);
				info.put("quantite", quant);
				info.put("montant_ht", mnt_ht);
				info.put("montant_remise", mnt_rem);
				info.put("montant_ht_net", mnt_ht);
				info.put("montant_tva", mnt_tva);
				info.put("montant_ttc", mnt_ttc);
				
				montant_ht += mnt_ht;
				montant_remise += mnt_rem;
				montant_ht_net += mnt_ht_net;
				montant_tva += mnt_tva;
				montant_ttc += mnt_ttc;
				
				ret_all_details_blf.add(info);
				
			}
			else {
				
				Map<String,Object> info = ret_all_details_blf.get(ret_all_details_blf.size()-1);
				
				info.put("quantite", (double) info.get("quantite") + quant);
				info.put("montant_ht", (double) info.get("montant_ht") + mnt_ht);
				info.put("montant_remise", (double) info.get("montant_remise") + mnt_rem);
				info.put("montant_ht_net", (double) info.get("montant_ht_net") + mnt_ht_net);
				info.put("montant_tva", (double) info.get("montant_tva") + mnt_tva);
				info.put("montant_ttc", (double) info.get("montant_ttc") + mnt_ttc);
				
				montant_ht += mnt_ht;
				montant_remise += mnt_rem;
				montant_ht_net += mnt_ht_net;
				montant_tva += mnt_tva;
				montant_ttc += mnt_ttc;
				
				ret_all_details_blf.set(ret_all_details_blf.size()-1, info);
				
			}
			
			
		}
		
		model.addAttribute("cumule_ble", ret_all_details_blf);
		
		model.addAttribute("montant_ht", montant_ht);
		
		model.addAttribute("montant_remise", montant_remise);
		
		model.addAttribute("montant_ht_net", montant_ht_net);
		
		model.addAttribute("montant_tva", montant_tva);
		
		model.addAttribute("montant_ttc", montant_ttc);
		
		model.addAttribute("rc_clt", rc_clt);
		
		model.addAttribute("bls_f", list_id_bls);
		
		model.addAttribute("mais",true);
		
		String ret = "";
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("fact_blfs"))) 
		{ ret = "vente/info_blfacture"; }
		else { ret = "403"; }
		
		return ret;
		
	}
	
	//--------------------------------------------------------------------------------
	
	@RequestMapping(value="facture_blf_list")
	public String facture_bl_by_ids(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("id_rel_rc_clt") Long id_rc_clt,
						 @RequestParam("bls_f") String list_id_bls,
						 Model model){
		
		client_registreCommerce rc_clt =  clt_rcRepo.getOne(id_rc_clt);
		
		//registre_commerce rc = rc_clt.getRegistre_commerce();
		
		String [] ids = list_id_bls.split("-");
		
		List<Long> idsBlfs = new ArrayList<Long>();
		
		if(!list_id_bls.isEmpty()) {
			
			for (String str : ids) {
				
				idsBlfs.add(Long.parseLong(str));
				
			}
			
		}
		
		//List<bon_livraison_facture_detail> sample_all_detail_blf = blfdRepo.get_detail_blfs_by_list_blf(idsBlfs);
		
		List<Object[]> sample_all_detail_blf = blfdRepo.get_article_cumuleAll_from_detail_blfs(idsBlfs);
		
		List<Map<String, Object>> ret_all_details_blf = new ArrayList<Map<String, Object>>();
		
		double montant_ht=0, montant_tva=0, montant_ttc=0, montant_ht_net=0, montant_remise=0;
		
		for(int i=0; i<sample_all_detail_blf.size(); i++) {
			
			Object[] obj = sample_all_detail_blf.get(i);
			
			article art = artRepo.getOne((long) obj[0]);
			
			//Magasin mag = magasinRepo.getOne((long)obj[1]);
			
			unite_mesure um = unite_mesureRepo.getOne((long)obj[1]);
			
			double price =(double)obj[2];
			
			double tva = (double)obj[3];
			
			double quant = (double)obj[4];
			
			double mnt_ht = (double)obj[5];
			
			double mnt_tva = (double)obj[6];
			
			double mnt_ttc = (double)obj[7];
			
			double mnt_ht_net = (double)obj[8];
			
			double mnt_remise = (double)obj[9];
			
			Map<String,Object> info = new  HashMap<String, Object>();
			
			info.put("prix_u_ht", price);
			info.put("tva", tva);
			info.put("article", art);
			//info.put("magasin", mag);
			info.put("unite_mesure", um);
			info.put("quantite", quant);
			info.put("montant_ht", mnt_ht);
			info.put("montant_tva", mnt_tva);
			info.put("montant_ttc", mnt_ttc);
			info.put("montant_ht_net", mnt_ht_net);
			info.put("montant_remise", mnt_remise);
			info.put("pourcentage_remise", (mnt_remise*100) / mnt_ht);
			
			montant_ht += mnt_ht;
			montant_tva += mnt_tva;
			montant_ttc += mnt_ttc;
			montant_ht_net += mnt_ht_net;
			montant_remise += mnt_remise;
			
			ret_all_details_blf.add(info);
			
		}
		
		model.addAttribute("cumule_ble", ret_all_details_blf);
		
		model.addAttribute("montant_ht", montant_ht);
		
		model.addAttribute("montant_tva", montant_tva);
		
		model.addAttribute("montant_ttc", montant_ttc);
		
		model.addAttribute("montant_ht_net", montant_ht_net);
		
		model.addAttribute("montant_remise", montant_remise);
		
		model.addAttribute("rc_clt", rc_clt);
		
		model.addAttribute("bls_f", list_id_bls);
		
		String ret = "";
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("fact_blfs"))) 
		{ ret = "vente/info_blfacture"; }
		else { ret = "403"; }
		
		return ret;
		
	}
	
	//----------------------------------------------------------------------------------------------
	/*
	@RequestMapping(value="/facture_blf_post",method=RequestMethod.POST)
	public String facture_blfs_post(HttpServletRequest req,
		@RequestParam("id_rel_rc_clt") long id_rc_clt,
		
		@RequestParam("date_d") String start,
		@RequestParam("date_f") String end,
		
		@SessionAttribute("user") users user){
		
		get_time_date gtd = new get_time_date();
		
		String today = gtd.get_date();
		
		String time = gtd.get_time();
		
		//------------------------------------------------------
		
		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_rc_clt);
		
		//-------------------- get details ------------------------------
		
		List<Object[]> sample_all_detail_blf = blfdRepo.get_cumule_facture_blf(start, end, clt_rc.getRegistre_commerce());
		
		List<Map<String, Object>> ret_all_details_blf = new ArrayList<Map<String, Object>>();
		
		double montant_ht=0, montant_tva=0, montant_ttc=0;
		
		for(int i=0; i<sample_all_detail_blf.size(); i++) {
			
			Object[] obj = sample_all_detail_blf.get(i);
			
			Map<String,Object> info = new  HashMap<String, Object>();
			
			info.put("prix_u_ht",(double)obj[0]);
			info.put("tva",(double)obj[1]);
			info.put("article",artRepo.getOne((long)obj[2]));
			//info.put("magasin",magasinRepo.getOne((long)obj[3]));
			info.put("unite_mesure",umRepo.getOne((long)obj[3]));
			info.put("quantite",(double)obj[4]);
			info.put("montant_ht",(double)obj[5]);
			info.put("montant_tva",(double)obj[6]);
			info.put("montant_ttc",(double)obj[7]);
			
			montant_ht += (double)obj[5];
			montant_tva += (double)obj[6];
			montant_ttc += (double)obj[7];
			
			ret_all_details_blf.add(info);
			
		}
		
		
		
		//-------------- update sold client
		
		client clt = clt_rc.getClient();
		
		double sold_encours_clt = clt.getSold_encours();
		
		double new_sold_clt = sold_encours_clt + montant_ttc;
		
		clt.setSold_encours(new_sold_clt);
		
		cltRepo.save(clt);cltRepo.flush();
		
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
		
		facture  last_fact = factRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_fact!=null) {
			
			last_number = last_fact.getNumero();
			
		}
		
		numerotation_by_year nby = new numerotation_by_year();
		
		String numero_fact = nby.return_num_facture(last_number, (long)user.getUnite().getIdentifiant());
		
		facture fact = new facture(clt, rc, clt_rc, today, time, numero_fact, 
				montant_ht, 0, " ", montant_ttc, montant_tva, 0, montant_ht, "", null, rc.getMode_paiement(),
				user, false,  montant_ttc, false, false, 0);
		
		
		factRepo.save(fact);factRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("facture", "creation facture apres validation BL", fact.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		//List<bon_livraison_detail> bld_list = bon_l_dRepo.get_bl_detail(bl);
		
		for(int i=0;i<ret_all_details_blf.size();i++) {
			
			//bon_livraison_detail bld = bld_list.get(i);
			
			facture_detail fct_d = new facture_detail(fact, (article)ret_all_details_blf.get(i).get("article"), (double)ret_all_details_blf.get(i).get("quantite"), 
					(double)ret_all_details_blf.get(i).get("prix_u_ht"), (double)ret_all_details_blf.get(i).get("montant_ht"), (double)ret_all_details_blf.get(i).get("tva"), 
					(double)ret_all_details_blf.get(i).get("montant_tva"), 
					((double)ret_all_details_blf.get(i).get("montant_ht") + (double)ret_all_details_blf.get(i).get("montant_tva")), 0, 0, 
					(double)ret_all_details_blf.get(i).get("montant_ht"),  
					(unite_mesure)ret_all_details_blf.get(i).get("unite_mesure"));
			
			fact_detRepo.save(fct_d);fact_detRepo.flush();
			
		}
		
		//------------------------------------------------ insert into mouvement table
		
		mouvement mvm = new mouvement(clt, rc, montant_ttc, "Facture", fact.getId(), gtd.get_date(), gtd.get_time(), "", sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
		
		mvmRepo.save(mvm);mvmRepo.flush();
		
		//------------------------------------------------ update facture to table regroupment
		
		prof_cmd_bl_fact_client_rc_avoir grp = new prof_cmd_bl_fact_client_rc_avoir(clt_rc, null, null, null, fact, null);
		
		grp.setFacture(fact);
		
		grpRepo.save(grp); grpRepo.flush();
		
		//---------------------------------------------- update bs sortie emp to factured------------------------------
		
		List<bon_livraison_facture> blfs = blfRepo.get_blf_non_factured_rc_date(rc, start, end);
		
		for(int i=0;i<blfs.size();i++) {
			
			bon_livraison_facture blf = blfs.get(i);
			
			blf.setFacture(fact);
			blf.setFactured(true);
			blf.setEtat_livraison(1);
			
			blfRepo.save(blf); blfRepo.flush();
			
		}
		
		
		//------------------------------------------------ END
		
		return "redirect:/print_fact?id_fact="+fact.getId();
		
	}
	*/
	//----------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/facture_blf_list_post",method=RequestMethod.POST)
	public String facture_blfs_list_post(HttpServletRequest req,
		@RequestParam("id_rel_rc_clt") long id_rc_clt,
		
		@RequestParam("bls_f") String list_id_bls,
		
		@SessionAttribute("user") users user){
		
		get_time_date gtd = new get_time_date();
		
		String today = gtd.get_date();
		
		String time = gtd.get_time();
		
		//------------------------------------------------------
		
		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_rc_clt);
		
		registre_commerce rc = clt_rc.getRegistre_commerce();
		
		//-------------------- get details ------------------------------
		
		String [] ids = list_id_bls.split("-");
		
		List<Long> idsBlfs = new ArrayList<Long>();
		
		for (String str : ids) {
			
			idsBlfs.add(Long.parseLong(str));
			
		}
		
		List<Object[]> sample_all_detail_blf = blfdRepo.get_article_cumuleAll_from_detail_blfs(idsBlfs);
		
		List<Map<String, Object>> ret_all_details_blf = new ArrayList<Map<String, Object>>();
		
		double montant_ht=0, montant_tva=0, montant_ttc=0, montant_ht_net=0, montant_remise=0;;
		
		for(int i=0; i<sample_all_detail_blf.size(); i++) {
			
			Object[] obj = sample_all_detail_blf.get(i);
			
			article art = artRepo.getOne((long) obj[0]);
			
			//Magasin mag = magasinRepo.getOne((long)obj[1]);
			
			unite_mesure um = unite_mesureRepo.getOne((long)obj[1]);
			
			double price =(double)obj[2];
			
			double tva = (double)obj[3];
			
			double quant = (double)obj[4];
			
			double mnt_ht = (double)obj[5];
			
			double mnt_tva = (double)obj[6];
			
			double mnt_ttc = (double)obj[7];
			
			double mnt_ht_net = (double)obj[8];
			
			double mnt_remise = (double)obj[9];
			
			Map<String,Object> info = new  HashMap<String, Object>();
			
			info.put("prix_u_ht", price);
			info.put("tva", tva);
			info.put("article", art);
			//info.put("magasin", mag);
			info.put("unite_mesure", um);
			info.put("quantite", quant);
			info.put("montant_ht", mnt_ht);
			info.put("montant_tva", mnt_tva);
			info.put("montant_ttc", mnt_ttc);
			info.put("montant_ht_net", mnt_ht_net);
			info.put("montant_remise", mnt_remise);
			info.put("pourcentage_remise", (mnt_remise*100) / mnt_ht);
			
			montant_ht += mnt_ht;
			montant_tva += mnt_tva;
			montant_ttc += mnt_ttc;
			montant_ht_net += mnt_ht_net;
			montant_remise += mnt_remise;
			
			ret_all_details_blf.add(info);
			
		}
		
		//-------------- update sold client
		
		client clt = clt_rc.getClient();
		
		double sold_encours_clt = clt.getSold_encours();
		
		double new_sold_clt = sold_encours_clt + montant_ttc;
		
		clt.setSold_encours(new_sold_clt);
		
		cltRepo.save(clt);cltRepo.flush();
		
		//-------------- update sold RC
		
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
		
		facture  last_fact = factRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_fact!=null) {
			
			last_number = last_fact.getNumero();
			
		}
		
		numerotation_by_year nby = new numerotation_by_year();
		
		String numero_fact = nby.return_num_facture(last_number, user.getUnite().getId());
		
		facture fact = new facture(clt, rc, clt_rc, today, time, numero_fact, 
				montant_ht, 0, " ", montant_ttc, montant_tva, montant_remise, montant_ht_net, "", null, rc.getMode_paiement(),
				user, false,  montant_ttc, false, false, montant_remise*(100/montant_ht));
		
		
		factRepo.save(fact);factRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("facture", "creation facture apres validation BL", fact.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		//List<bon_livraison_detail> bld_list = bon_l_dRepo.get_bl_detail(bl);
		
		for(int i=0;i<ret_all_details_blf.size();i++) {
			
			article art = (article)ret_all_details_blf.get(i).get("article");
			
			if(!art.isConsignation()) {
				
				facture_detail fct_d = new facture_detail(fact, art, 
						(double)ret_all_details_blf.get(i).get("quantite"), 
						(double)ret_all_details_blf.get(i).get("prix_u_ht"), 
						(double)ret_all_details_blf.get(i).get("montant_ht"), 
						(double)ret_all_details_blf.get(i).get("tva"), 
						(double)ret_all_details_blf.get(i).get("montant_tva"), 
						(double)ret_all_details_blf.get(i).get("montant_ttc"),
						(double)ret_all_details_blf.get(i).get("pourcentage_remise"),
						(double)ret_all_details_blf.get(i).get("montant_remise"), //------------> null pointer exception
						(double)ret_all_details_blf.get(i).get("montant_ht_net"),  
						(unite_mesure)ret_all_details_blf.get(i).get("unite_mesure"));
				
				fact_detRepo.save(fct_d);fact_detRepo.flush();
				
			}
			else {
				
				consService.consignationFACT(fact, art, rc, (double)ret_all_details_blf.get(i).get("quantite"));
				
			}
			
		}
		
		//------------------------------------------------ insert into mouvement table
		
		mouvement mvm = new mouvement(clt, rc, montant_ttc, "Facture", fact.getId(), gtd.get_date(), gtd.get_time(), "", 
				sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
		
		mvmRepo.save(mvm);mvmRepo.flush();
		
		//------------------------------------------------ update facture to table regroupment
		
		prof_cmd_bl_fact_client_rc_avoir grp = new prof_cmd_bl_fact_client_rc_avoir(clt_rc, null, null, null, fact, null);
		
		grp.setFacture(fact);
		
		grpRepo.save(grp); grpRepo.flush();
		
		//---------------------------------------------- update bs sortie emp to factured------------------------------
		
		List<bon_livraison_facture> blfs = blfRepo.findAllById(idsBlfs);
		
		for(int i=0;i<blfs.size();i++) {
			
			bon_livraison_facture blf = blfs.get(i);
			
			blf.setFacture(fact);
			blf.setFactured(true);
			blf.setEtat_livraison(1);
			
			blfRepo.save(blf); blfRepo.flush();
			
		}
		
		
		//------------------------------------------------ END
		
		return "redirect:/print_fact_a4?id_fact="+fact.getId();
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/facture_blf_list_mais_post",method=RequestMethod.POST)
	public String facture_blfs_list_mais_post(HttpServletRequest req,
		@RequestParam("id_rel_rc_clt") long id_rc_clt,
		
		@RequestParam("bls_f") String list_id_bls,
		
		@SessionAttribute("user") users user){
		
		get_time_date gtd = new get_time_date();
		
		String today = gtd.get_date();
		
		String time = gtd.get_time();
		
		//------------------------------------------------------
		
		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_rc_clt);
		
		registre_commerce rc = clt_rc.getRegistre_commerce();
		
		//-------------------- get details ------------------------------
		
		String [] ids = list_id_bls.split("-");
		
		List<Long> idsBlfs = new ArrayList<Long>();
		
		for (String str : ids) {
			
			idsBlfs.add(Long.parseLong(str));
			
		}
		
		List<bon_livraison_facture_detail> sample_all_detail_blf = blfdRepo.get_detail_blfs_by_list_blf(idsBlfs);
		
		List<Map<String, Object>> ret_all_details_blf = new ArrayList<Map<String, Object>>();
		
		double montant_ht=0, montant_tva=0, montant_ttc=0;
		
		for(int i=0; i<sample_all_detail_blf.size(); i++) {
			
			bon_livraison_facture_detail obj = sample_all_detail_blf.get(i);
			
			double price = prix_u_art_catcRepo.get_prix_articles_by_CatClient(rc.getCategory(), obj.getArticle());
			
			double tva = prix_u_art_catcRepo.get_instance_by_art_and_catClient(obj.getArticle(), rc.getCategory()).getTva().getTaux_tva();
			
			if(rc.getTva()==0) { tva = 0;} //---------------------------> en cas exoneration
			
			double mnt_ht = obj.getQuantite() * price;
			
			double mnt_tva = ( obj.getQuantite() * price )*( tva / 100);
			
			double mnt_ttc = mnt_ht + mnt_tva;
			
			if(ret_all_details_blf.isEmpty() || !ret_all_details_blf.get(ret_all_details_blf.size()-1).get("article").equals(obj.getArticle())) {
				
				Map<String,Object> info = new  HashMap<String, Object>();
				
				info.put("prix_u_ht", price);
				info.put("tva", tva);
				info.put("article", obj.getArticle());
				info.put("magasin", obj.getMagasin());
				info.put("unite_mesure", obj.getUnite_mesure());
				info.put("quantite", obj.getQuantite());
				info.put("montant_ht", mnt_ht);
				info.put("montant_tva", mnt_tva);
				info.put("montant_ttc", mnt_ttc);
				
				montant_ht += mnt_ht;
				montant_tva += mnt_tva;
				montant_ttc += mnt_ttc;
				
				ret_all_details_blf.add(info);
				
			}
			else {
				
				Map<String,Object> info = ret_all_details_blf.get(ret_all_details_blf.size()-1);
				
				info.put("quantite", (double) info.get("quantite") + obj.getQuantite());
				info.put("montant_ht", (double) info.get("montant_ht") + mnt_ht);
				info.put("montant_tva", (double) info.get("montant_tva") + mnt_tva);
				info.put("montant_ttc", (double) info.get("montant_ttc") + mnt_ttc);
				
				montant_ht += mnt_ht;
				montant_tva += mnt_tva;
				montant_ttc += mnt_ttc;
				
				ret_all_details_blf.set(ret_all_details_blf.size()-1, info);
				
			}
			
			
		}
		
		//-------------- update sold client
		
		client clt = clt_rc.getClient();
		
		double sold_encours_clt = clt.getSold_encours();
		
		double new_sold_clt = sold_encours_clt + montant_ttc;
		
		clt.setSold_encours(new_sold_clt);
		
		cltRepo.save(clt);cltRepo.flush();
		
		//-------------- update sold RC
		
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
		
		facture  last_fact = factRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_fact!=null) {
			
			last_number = last_fact.getNumero();
			
		}
		
		numerotation_by_year nby = new numerotation_by_year();
		
		String numero_fact = nby.return_num_facture(last_number, user.getUnite().getId());
		
		
		
		facture fact = new facture(clt, rc, clt_rc, today, time, numero_fact, 
				montant_ht, 0, " ", montant_ttc, montant_tva, 0, montant_ht, "", null, rc.getMode_paiement(),
				user, false,  montant_ttc, false, false, 0);
		
		
		factRepo.save(fact);factRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("facture", "creation facture apres validation BL", fact.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		//List<bon_livraison_detail> bld_list = bon_l_dRepo.get_bl_detail(bl);
		
		for(int i=0;i<ret_all_details_blf.size();i++) {
			
			//bon_livraison_detail bld = bld_list.get(i);
			
			facture_detail fct_d = new facture_detail(fact, (article)ret_all_details_blf.get(i).get("article"), (double)ret_all_details_blf.get(i).get("quantite"), 
					(double)ret_all_details_blf.get(i).get("prix_u_ht"), (double)ret_all_details_blf.get(i).get("montant_ht"), (double)ret_all_details_blf.get(i).get("tva"), 
					(double)ret_all_details_blf.get(i).get("montant_tva"), 
					((double)ret_all_details_blf.get(i).get("montant_ht") + (double)ret_all_details_blf.get(i).get("montant_tva")), 0, 0, 
					(double)ret_all_details_blf.get(i).get("montant_ht"),  
					(unite_mesure)ret_all_details_blf.get(i).get("unite_mesure"));
			
			fact_detRepo.save(fct_d);fact_detRepo.flush();
			
		}
		
		//------------------------------------------------ insert into mouvement table
		
		mouvement mvm = new mouvement(clt, rc, montant_ttc, "Facture", fact.getId(), gtd.get_date(), gtd.get_time(), "", sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
		
		mvmRepo.save(mvm);mvmRepo.flush();
		
		//------------------------------------------------ update facture to table regroupment
		
		prof_cmd_bl_fact_client_rc_avoir grp = new prof_cmd_bl_fact_client_rc_avoir(clt_rc, null, null, null, fact, null);
		
		grp.setFacture(fact);
		
		grpRepo.save(grp); grpRepo.flush();
		
		//---------------------------------------------- update bs sortie emp to factured------------------------------
		
		List<bon_livraison_facture> blfs = blfRepo.findAllById(idsBlfs);
		
		for(int i=0;i<blfs.size();i++) {
			
			bon_livraison_facture blf = blfs.get(i);
			
			blf.setFacture(fact);
			blf.setFactured(true);
			blf.setEtat_livraison(1);
			
			blfRepo.save(blf); blfRepo.flush();
			
		}
		
		
		//------------------------------------------------ END
		
		return "redirect:/print_fact_a4?id_fact="+fact.getId();
		
	}
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_blf")
	public String print_blf(HttpServletRequest request,
						 @RequestParam("id_bl") long id_blf,
						 @SessionAttribute("user") users user,
						 Model model){
		
		bon_livraison_facture blf = blfRepo.getOne(id_blf);
		
		String qr_code = generateQRcode.createQRcode(blf.getNumero(), "BL");
		
		String pdf = "";
		
		
		pdf = gd.generate_blf(blf, qr_code);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	@RequestMapping(value="/print_blf_t")
	public String print_blf_t(HttpServletRequest request,
						 @RequestParam("id_bl") long id_blf,
						 @SessionAttribute("user") users user,
						 Model model){
		
		bon_livraison_facture blf = blfRepo.getOne(id_blf);
		
		String qr_code = generateQRcode.createQRcode(blf.getNumero(), "BL");
		
		String pdf = "";
		
		
		pdf = gd.generate_blf_bt(blf, qr_code);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	@RequestMapping(value="/print_blq")
	public String print_blq(HttpServletRequest request,
						 @RequestParam("id_bl") long id_blf,
						 @SessionAttribute("user") users user,
						 Model model){
		
		bon_livraison_facture blf = blfRepo.getOne(id_blf);
		
		String qr_code = generateQRcode.createQRcode(blf.getNumero(), "BL");
		
		String pdf = "";
		
		
		pdf = gd.generate_blq(blf, qr_code);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}

	@RequestMapping(value="/print_blfs")
	public String print_blfs(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model) throws IOException{
		
		List<bon_livraison_facture> blfs = blfRepo.get_blfs_date(start, end);
		
		String pdf = "";
		
		
		pdf = gd.generate_blfs(blfs);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	@RequestMapping(value="/print_blfs_val")
	public String print_blfs_val(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model) throws IOException{
		
		List<bon_livraison_facture> blfs = blfRepo.get_blfs_date(start, end);
		
		String pdf = "";
		
		
		pdf = gd.generate_blfs_valorise(blfs);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	@RequestMapping(value="/print_fact_a4")
	public String print_fact_a4(HttpServletRequest request,
						 @RequestParam("id_fact") long id_fact,
						 @SessionAttribute("user") users user,
						 Model model){
		
		facture fact = factRepo.getOne(id_fact);
		
		String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		pdf = gd.generate_Fact_A4_blfs(fact, qr_code);
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//-----------------------------------------------------------------------------
	
}
