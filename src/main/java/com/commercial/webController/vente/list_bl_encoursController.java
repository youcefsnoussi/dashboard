package com.commercial.webController.vente;

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
import org.springframework.web.bind.annotation.SessionAttributes;

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
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;
import com.commercial.entities.schema.backup_edit.bon_livraison_backup;
import com.commercial.entities.schema.backup_edit.bon_livraison_detail_backup;
import com.commercial.entities.schema.backup_edit.repository.bon_livraison_backupRepository;
import com.commercial.entities.schema.backup_edit.repository.bon_livraison_detail_backupRepository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.commande;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.prof_cmd_bl_fact_client_rc_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commandeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commande_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiement_factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.services.ConsignationService;
import com.commercial.services.PaletteService;
import com.commercial.services.generate_Doc;
import com.commercial.services.track_operations;
import com.commercial.functions.Connection_peseur;
import com.commercial.functions.generateQRcode;

@Controller
@SessionAttributes("user")

public class list_bl_encoursController {
	
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
	prixUnitaire_article_categoryClient_Repository pu_a_ctRepo;
	
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
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_detRepo;
	
	@Autowired
	mouvementRepository mvmRepo;
	
	@Autowired
	prof_cmd_bl_fact_client_rc_avoirRepository grpRepo;
	
	@Autowired
	bon_livraison_backupRepository blbRepo;
	
	@Autowired
	bon_livraison_detail_backupRepository bldbRepo;
	
	@Autowired
	magasin_articleRepository magRepo;
	
	@Autowired
	MagasinRepository magasinRepo;
	
	@Autowired
	paiementRepository payRepo;
	
	@Autowired
	paiement_factureRepository pay_factRepo;
	
	@Autowired
	track_operations trk;
	
	@Autowired
	PaletteService ps;
	
	@Autowired
	ConsignationService consService;
	
	@Autowired
	article_consignation_relationRepository art_cons_relRepo;
	
	public list_bl_encoursController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/list_bl_encours")
	public String list_bls_encours_for_magasin (HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		model.addAttribute("bls", bon_lRepo.get_bl_encours());
		
		return "vente/list_bl_encours";
		
	}
	
	@RequestMapping(value="/list_bl")
	public String list_bls_for_commercial(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		if(user.getRole().getNom_role().equals("Admin") || user.getRole().getNom_role().equals("Responsable") 
				|| user.getRole().getNom_role().equals("Commercial") ) {
			
			model.addAttribute("bls", bon_lRepo.get_bl_encours());
			
		}
		else {
			
			model.addAttribute("bls", bon_lRepo.get_bl_encours_with_user(user));
			
		}
		
		boolean cancel_bl = false;
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getNom_role().equals("Responsable") || 
				user.getRole().getIds_banned().contains("cancel_bl") ) 
		{ 
			cancel_bl = true; 
			model.addAttribute("displayUser", true);
		}
		
		model.addAttribute("cancel_bl", cancel_bl);
		
		//----------------------------------------------------------------
		
		return "vente/list_bl";
		
	}
	
	//-----------------------------------------------------------------------
	
	
	//-----------------------------------------------------------------------------
	
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/info_bl")
	public String info_bl(HttpServletRequest request,
						 @RequestParam("id_bl") long id_bl,
						 @SessionAttribute("user") users user,
						 Model model){
		
		model.addAttribute("bl", bon_lRepo.getOne(id_bl));
		
		model.addAttribute("etat_liv", bon_lRepo.getOne(id_bl).getEtat_livraison());
		
		if(!user.getRole().getNom_role().equals("Expédition")) {
			
			model.addAttribute("detail_bl", bon_l_dRepo.get_bl_all_details(bon_lRepo.getOne(id_bl)));
			
		}
		else {
			
			model.addAttribute("detail_bl", bon_l_dRepo.get_bl_detail_magasin(bon_lRepo.getOne(id_bl), user.getMagasin()));
			
		}
		
		
		client clt = bon_lRepo.getOne(id_bl).getClient();
		
		List <prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(clt.getCategory());
		
		model.addAttribute("articles", list_art);
		
		return "vente/info_bl";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_bl")
	public String print_bl(HttpServletRequest request,
						 @RequestParam("id_bl") long id_bl,
						 @SessionAttribute("user") users user,
						 Model model){
		
		bon_livraison bl = bon_lRepo.getOne(id_bl);
		
		String qr_code = generateQRcode.createQRcode(bl.getNumero(), "BL");
		
		String pdf = "";
		
		
		pdf = gd.generate_BL(bl.getId(), bl.getNumero(), bl.getMatricule(), qr_code);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/edit_bl")
	public String edit_bl(HttpServletRequest request,
						 @RequestParam("id_bl") long id_bl,
						 @RequestParam(name="plafond", defaultValue="") String plafond,
						 @SessionAttribute("user") users user,
						 Model model){
		
		model.addAttribute("bl", bon_lRepo.getOne(id_bl));
		
		model.addAttribute("plafond", plafond);
		
		model.addAttribute("detail_bl", bon_l_dRepo.get_bl_detail(bon_lRepo.getOne(id_bl)));
		
		registre_commerce rc = bon_lRepo.getOne(id_bl).getRegistre_commerce();
		
		List <prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(rc.getCategory());
		
		if (bon_lRepo.getOne(id_bl).getRegistre_commerce().getTva()==0) {
			
			list_art.forEach(prix_art -> {
				
				prix_art.setTva((tva) tvaRepo.findAll().stream().filter(tva -> tva.getTaux_tva()==0)
							.collect(Collectors.toList()).get(0));
				
			});
			
		}
		
		List<List<bon_livraison_detail>> list_art_cons = new ArrayList<>();
		
		List<String> list_art_cons_selected_id = new ArrayList<>();
		
		//List<bon_livraison_detail> bld_cons = bon_l_dRepo.get_bl_detail_articles_consignation(bon_lRepo.getOne(id_bl));
		
		String id_arts_cons = "";
		
		List<bon_livraison_detail> arts_cons = new ArrayList<>();
		
		int index = 0;
		
		for(bon_livraison_detail bl_detail : bon_l_dRepo.get_bl_all_details(bon_lRepo.getOne(id_bl)) ) {
			
			index++;
			
			//System.out.println("id entry "+bl_detail.getArticle().getId()+" / is CONSIGN "+bl_detail.getArticle().isConsignation());
			
			if(bl_detail.getArticle().isConsignation()==true) {
				
				//System.out.println("enter consign");
				
				arts_cons.add(bl_detail);
				
				id_arts_cons = id_arts_cons+bl_detail.getArticle().getId()+",";
				
			}
			else {
				
				if(!arts_cons.isEmpty()) {
					
					list_art_cons.add(arts_cons);
					
					list_art_cons_selected_id.add(id_arts_cons);
					
				}
				
				id_arts_cons = "";
				
				arts_cons = new ArrayList<>();
				
			}
			
			if(index==bon_l_dRepo.get_bl_all_details(bon_lRepo.getOne(id_bl)).size() && 
					bl_detail.getArticle().isConsignation()==true) {
				
				list_art_cons.add(arts_cons);
				
				list_art_cons_selected_id.add(id_arts_cons);
				
			}
			
		}
		
		model.addAttribute("articles", list_art);
		
		model.addAttribute("bld_cons", list_art_cons);
		
		list_art_cons_selected_id.stream().forEach(s -> {
			list_art_cons_selected_id.set(list_art_cons_selected_id.indexOf(s), s.substring(0,s.lastIndexOf(",")));
		});
		
		model.addAttribute("bld_cons_id", list_art_cons_selected_id);
		
		return "vente/edit_bl";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/edit_bl_post",method=RequestMethod.POST)
	public String edit_bl_Post(HttpServletRequest req,
			@RequestParam("id_bl") long id_bl,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,
			@RequestParam("matricule") String matricule,
			@RequestParam("pourc_reduction") double pourc_reduction, //----> lebes
			@RequestParam("montant_reduction") double mnt_reduction, //----> lebes
			@RequestParam("total_ht_net") double montant_ht_net,
			
			@RequestParam(name="art", defaultValue = "0") long [] article,
			@RequestParam(name="id_um", defaultValue = "0") long [] id_unite_mesure,
			@RequestParam(name="id_magasin",defaultValue = "0") long [] id_magasin,
			@RequestParam(name="montant_ht_art", defaultValue = "0") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
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
			
			bon_livraison bl = bon_lRepo.getOne(id_bl);
			/*
			get_time_date gtd = new get_time_date();
			
			String today = gtd.get_date();
			
			String time = gtd.get_time();
			*/
			//------------------------- TEST PLAFOND
			
			int t = 0;
			
			//client clt = bl.getClient();
			
			registre_commerce rc = bl.getRegistre_commerce();
			
			/**************      TEST CLIENT FATHER **********/
			/*
			double balance_clt = clt.getSold_encours();
			
			if((balance_clt+montant_ttc)>clt.getPlafond()) {
				
				t = 1;
				
			}  //----------------------------------------------> TEST rani ndiro  ajax kbel validation
			   
			   //----------------------------------------------> ajax_test_plafond f restController
			    
			*/
			/**************      TEST  RC **********/
			
			List <bon_livraison>list_bl = bon_lRepo.get_bl_encours_by_rc(rc);
			
			double montant_bls = 0;
			
			for(int j=0;j<list_bl.size();j++) {
				
				montant_bls = montant_bls + list_bl.get(j).getMontant_ttc();
				
			}
			
			//------------------------------------------------------- nA9ESS montant bl li rah encours mais avant modif hed test rah yesra
			
			if( list_bl.contains( bon_lRepo.getOne(id_bl) ) ) {
				
				montant_bls = montant_bls - bon_lRepo.getOne(id_bl).getMontant_ttc();
				
			}
			
			//----------------------------------------------------------------------------------------------
				
			//---------------------- TEST PALETTE + CACULE MONTANT PALETTE DJDID -----------
			/*
			double montant_p = 0;
				
			if(clt.isVentePalette()) {
				
				List <bon_livraison> tmp = bon_lRepo.get_bl_encours_by_clt(clt);
				
				tmp.remove(bl);
				
				if(ps.testPalettePlafond( tmp, clt, ps.getNombrePaletteFromInterface(article, quantite, clt)))  t=1;
				
				montant_p = ps.getMontantPaletteFromInterface(article, quantite, clt);
				
			}*/
			//System.out.println("prix total palette djdid------>"+montant_p);
			
			//---------------------------------------------------
			
			double balance_rc = rc.getSold_encours();
			
			if((balance_rc+montant_ttc+montant_bls/*+montant_p*/)>rc.getPlafond()) {
				
				t = 1;
				
			}
			
			String ret;
			
			if(t==0) {
				
				//-------------------- tracking operation -----------------------------------
				
				bon_livraison_backup bl_b = new bon_livraison_backup(bl, user);
				blbRepo.save(bl_b);bldbRepo.flush();
				
				trk.add_track("bon_livraison", "Modification Bon livraison", bl.getId(), user);
				
				//-------------------- tracking operation -----------------------------------
				
				List <bon_livraison_detail> bld = bon_l_dRepo.get_bl_detail_post(bon_lRepo.getOne(id_bl));
				
				for(int i=0;i<bld.size();i++) {
					
					bon_livraison_detail bl_d = bld.get(i);
					
					//---------------- backup bl detail before delete ----------------------
					
					bon_livraison_detail_backup bl_db = new bon_livraison_detail_backup(bl_d, bl_b);
					
					bldbRepo.save(bl_db); bldbRepo.flush();
					
					//--------------------------------------------------------------------
					
					bon_l_dRepo.delete(bl_d);
					
					bon_l_dRepo.flush();
					
				}
				
				for(int i=0;i<quantite.length;i++) {
					
					if(article[i]!=0 && quantite[i]!=0) {
						/*
						System.out.println("index->"+i+" / art->"+artRepo.getOne(article[i]).getCode()+" / quant-> "+quantite[i]+
								" / Mag-> "+magasinRepo.getOne(id_magasin[i]).getName());
						
						
						bon_livraison_detail bl_d = new bon_livraison_detail(bl, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i], 
								montant_ht_art[i], montant_tva_art[i], tva_art[i], umRepo.getOne(id_unite_mesure[i]), false, 
								magasinRepo.getOne(id_magasin[i]), montant_redux_art_pourc[i], montant_redux_art_val[i], 
								montant_net_ht_art[i],
								montant_ttc_art[i]);
						*/
						
						prixUnitaire_article_categoryClient pu_obj = 
							prix_u_art_catcRepo.get_prix_articles_by_CatClient_Object(rc.getCategory(), artRepo.getOne(article[i]));
						
						//--------------------> TEST IS RC EXONERE TVA <------------------------------
						
						double taux_tva = (rc.getTva()==0) ? 0 : pu_obj.getTva().getTaux_tva();
						
						//-------------------->	---------------------- <------------------------------
						
						bon_livraison_detail bl_d = new bon_livraison_detail(bl, artRepo.getOne(article[i]), quantite[i],
								pu_obj.getPrix(), taux_tva, montant_redux_art_val[i],
								artRepo.getOne(article[i]).getUnite_mesure_vente(), magasinRepo.getOne(id_magasin[i]));
						
						bon_l_dRepo.save(bl_d);bon_l_dRepo.flush();
						
						if(art_cons_relRepo.if_art_consigned(artRepo.getOne(article[i])).size()!=0) {
							
							consService.consignationBL(bl, art_consign.get(i), rc, artRepo.getOne(article[i]), quantite[i], 
										magasinRepo.getOne(id_magasin[i]));
						
						}
						
					}
					
				}
				
				//------------------------- edit BL --------------------------------------
				
				//-------------------- calcule total from detail bl and put it in BL
				
				List<Double[]> sum_details = bon_l_dRepo.get_sum_for_bl(bl);
				
				bl.setMontant_ht(sum_details.get(0)[0]);
				bl.setValeur_reduction(sum_details.get(0)[1]); //------> NULL pointer excpetion
				bl.setMontant_ht_net(sum_details.get(0)[2]);
				bl.setTva(sum_details.get(0)[3]);
				bl.setMontant_ttc(sum_details.get(0)[4]);
				
				bl.setPourcentage_reduction( (sum_details.get(0)[1] * 100) / sum_details.get(0)[0] ); 
				
				bl.setMatricule(matricule);
				
				bon_lRepo.save(bl);bon_lRepo.flush();
				
				/* -----------------> OLD TAKING VALUES FROM CLIENTS
				bl.setValeur_reduction(mnt_reduction);
				bl.setPourcentage_reduction(pourc_reduction);
				bl.setMontant_ht_net(montant_ht_net);
				bl.setMontant_ht(montant_ht);
				bl.setMontant_ttc(montant_ttc);
				bl.setTva(montant_tva);
				
				bl.setDate(today);
				bl.setTime(time);
				
				bl.setMatricule(matricule);
				
				bl.setUsers(user);
				
				bon_lRepo.save(bl); bon_lRepo.flush();
				*/
				//---------------------------------------------------------------
				
				//--------------------------- UPDATE PALETTE ---------------------
				/*
				if(clt.isVentePalette()) {
					
					ps.addingPaletteToBL(bl);
					
				}
				*/
				//----------------------------------------------------------------
				
				String qr_code = generateQRcode.createQRcode(bl.getNumero(), "BL");
				
				String pdf = "";
				
				pdf = gd.generate_BL(bl.getId(), bl.getNumero(), bl.getMatricule(), qr_code);
				
				ret="redirect:/display_pdf?file="+pdf;
				
			}
			else {
				
				ret="redirect:/edit_bl?id_bl="+bl.getId()+"&plafond=true";
				
			}
			return ret;
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/validate_bl_post",method=RequestMethod.POST)
	public String validate_bl(HttpServletRequest req,
		@RequestParam("id_bl") long id_bl,
		
		@RequestParam(name="id_bl_detail", defaultValue = "0") long [] id_bl_detail,
		@RequestParam(name="selection", defaultValue = "0") int [] selection,
		
		@SessionAttribute("user") users user){
		
		bon_livraison bl = bon_lRepo.getOne(id_bl);
		
		get_time_date gtd = new get_time_date();
		
		String today = gtd.get_date();
		
		String time = gtd.get_time();
		
		//-------------------------------
		
		if(bl.getEtat_livraison()==0) {
			
			//--------------------------- validate one row --------------------
			
			int index = 0;
			
			for(int i=0;i<selection.length;i++) {
				
				if(selection[i]==1) { index = i; }
				
			}
			
			bon_livraison_detail bl_d_one = bon_l_dRepo.getOne(id_bl_detail[index]);
			
			bl_d_one.setValidation(true);
			bl_d_one.setUser_magasin_validate(user);
			
			bon_l_dRepo.save(bl_d_one); bon_l_dRepo.flush();
			
			//--------------------- TEST if all row validated so (create facture) -------------
			
			List <bon_livraison_detail> list_bld = bon_l_dRepo.get_bl_detail(bl);
			
			int watchdog = 0;
			
			for(int i=0;i<list_bld.size();i++) {
				
				bon_livraison_detail b = list_bld.get(i);
				
				if(b.isValidation()==false) {
					
					watchdog = 1;
					
				}
				
			}
			
			if(watchdog==0) {
			
				//--------------- ----------------------------- -------------------
				
				client_registreCommerce clt_rc = bl.getCommande().getClient_registrecommerce();
				
				//-------------- update sold client
				
				client clt = clt_rc.getClient();
				
				double sold_encours_clt = clt.getSold_encours();
				
				double new_sold_clt = sold_encours_clt + bl.getMontant_ttc();
				
				clt.setSold_encours(new_sold_clt);
				
				clientRepo.save(clt);clientRepo.flush();
				
				//-------------- update sold RC
				
				registre_commerce rc = clt_rc.getRegistre_commerce();
				
				double sold_encours_rc = rc.getSold_encours();
				
				double new_sold_rc = sold_encours_rc + bl.getMontant_ttc();
				
				rc.setSold_encours(new_sold_rc);
				
				rcRepo.save(rc);rcRepo.flush();
				
				//-------------- update sold RC_client relationship
				
				double sold_encours = clt_rc.getMontant_actuel();
				
				double new_sold = sold_encours + bl.getMontant_ttc();
				
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
				
				facture fact = new facture(bl.getClient(), bl.getRegistre_commerce(), bl.getCommande().getClient_registrecommerce(), today, time,
						numero_fact, bl.getMontant_ht(), 0, bl.getMatricule(), bl.getMontant_ttc(), bl.getTva(), bl.getValeur_reduction(), 
						bl.getMontant_ht_net(), "", bl, bl.getCommande().getMode_paiement(), bl.getCommande().getUsers(), false, 
						bl.getMontant_ttc(), false, false, bl.getPourcentage_reduction());
				
				factRepo.save(fact);factRepo.flush();
				
				//--------------------- add date last facture to registre commerce -------------------
				
				rc.setDate_last_facture(fact.getDate());
				
				rcRepo.save(rc);rcRepo.flush();
				
				//----------------------------------------------------------------------------------
				
				//-------------------- tracking operation -----------------------------------
				
				trk.add_track("facture", "creation facture apres validation BL", fact.getId(), user);
				
				//-------------------- tracking operation -----------------------------------
				
				List<bon_livraison_detail> bld_list = bon_l_dRepo.get_bl_all_details(bl);
				
				for(int i=0;i<bld_list.size();i++) {
					
					bon_livraison_detail bld = bld_list.get(i);
					
					if(!bld.getArticle().isConsignation()) {
						
						facture_detail fct_d = new facture_detail(fact, bld.getArticle(), bld.getQuantite(), bld.getPrix_u_ht(), 
								bld.getMontant_ht(), bld.getTva(), bld.getMontant_tva(), bld.getMontant_ttc(), 
								bld.getPourcentage_remise(), bld.getMontant_remise(), bld.getMontant_ht_net(), bld.getUnite_mesure());
						
						fact_detRepo.save(fct_d);fact_detRepo.flush();
					
					
					}
					else{ //----------------- Consignation -------------<
						
						consService.consignationFACT(fact, bld.getArticle(), rc, bld.getQuantite());
						
					}
					
					//-----------------              -------------<
					
				}
				
				//------------------------------------------------ insert into mouvement table
				
				mouvement mvm = new mouvement(clt, rc, bl.getMontant_ttc(), "Facture", fact.getId(), gtd.get_date(), gtd.get_time(), "", 
												sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
				
				mvmRepo.save(mvm);mvmRepo.flush();
				
				//------------------------------------------------ update facture to table regroupment
				
				prof_cmd_bl_fact_client_rc_avoir grp = grpRepo.get_relation_by_bl(bl);
				
				grp.setFacture(fact);
				
				grpRepo.save(grp); grpRepo.flush();
				
				commande cmd = grp.getCommande();
				
				cmd.setCloturer(true);
				
				cmdRepo.save(cmd); cmdRepo.flush();
				
				//--------------------------
				
				bl.setEtat_livraison(1);
				
				bon_lRepo.save(bl); bon_lRepo.flush();
				
				//-------------------------	
				
				//-----------------Update SOLD PALETTE------------------
				/*
				if(clt.isVentePalette()) {
					
					ps.paletteOut(ps.getNbrPaletteFromBL(bl), fact);
					
				}
				*/
				
				//------------------------- insert into MY SQL Peseur IF AIN ROMANA------------
				
				//if(user.getUnite().getIdentifiant()==1) {
					
				Connection_peseur cp = new Connection_peseur();
				
				if(cp.getconnection()!=null) {
					
					cp.insert_fct_to_peseur(numero_fact, today);
					
					cp.update_bl_fact_son(bl.getNumero(), fact.getNumero());
					
				}
					
				//}
				
				//------------------------- END INSERT into MY SQL Peseur ---------------------------------------
				
				//------------ update sold factures AND sold paiement ----------------------------------
				
				List<paiement> list_pais = payRepo.get_paiements_not_solde_by_rc(rc);
				
				int watch_dog_out = 0;
				
				int i =0;
				
				double montant_buf = fact.getMontant_ttc();
				
				if(list_pais.size()==0) {
					
					watch_dog_out = 1;
					
				}
				
				while(watch_dog_out==0) {
					
					paiement pai = list_pais.get(i);
					  
					if(montant_buf <= pai.getSold_rest()) {
						
						double new_sold_restant = pai.getSold_rest() - montant_buf;
						
						pai.setSold_rest(new_sold_restant);
						
						fact.setSold_rest(0);
						
						fact.setEtat_sold(true);
						
						factRepo.save(fact);factRepo.flush();
						
						if(new_sold_restant==0) {
							
							pai.setEtat_sold(true);
							
						}
						
						payRepo.save(pai);payRepo.flush();
						
						paiement_facture pay_fac = new paiement_facture(pai, fact, montant_buf);
						
						pay_factRepo.save(pay_fac);pay_factRepo.flush();
						
						watch_dog_out = 1;
						
					}
					else {
						
						montant_buf = montant_buf - pai.getSold_rest();
						
						fact.setSold_rest(montant_buf);
						factRepo.save(fact);factRepo.flush();
						
						double sold_rest_pay = pai.getSold_rest();
						
						pai.setSold_rest(0);
						pai.setEtat_sold(true);
						payRepo.save(pai);payRepo.flush();
						
						paiement_facture pay_fac = new paiement_facture(pai, fact, sold_rest_pay);
						
						pay_factRepo.save(pay_fac);pay_factRepo.flush();
						
						if((i+1)<list_pais.size()) {
							
							i++;
							
						}
						else {
							
							watch_dog_out = 1;
							
						}
						
					}
					
				}
			
				//--------------- END ----------------------
				
			}
		
		}
		
		return "redirect:/info_bl?id_bl="+bl.getId();
		
	}
	
}
