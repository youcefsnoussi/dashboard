package com.commercial.webController.vente;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

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
import com.commercial.entities.schema.profoma_cmd_bl_fact.prof_cmd_bl_fact_client_rc_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commandeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commande_detailRepository;
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
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.functions.track_operations;
import com.commercial.functions.generateQRcode;
import com.commercial.functions.generate_Doc;

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
	track_operations trk;
	
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
		
		if(user.getRole().getNom_role().equals("Admin")) {
			
			model.addAttribute("bls", bon_lRepo.get_bl_encours());
			
		}
		else {
			
			model.addAttribute("bls", bon_lRepo.get_bl_encours_with_user(user));
			
		}
		
		return "vente/list_bl";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/info_bl")
	public String info_bl(HttpServletRequest request,
						 @RequestParam("id_bl") long id_bl,
						 @SessionAttribute("user") users user,
						 Model model){
		
		model.addAttribute("bl", bon_lRepo.getOne(id_bl));
		
		model.addAttribute("etat_liv", bon_lRepo.getOne(id_bl).getEtat_livraison());
		
		model.addAttribute("detail_bl", bon_l_dRepo.get_bl_detail(bon_lRepo.getOne(id_bl)));
		
		client clt = bon_lRepo.getOne(id_bl).getClient();
		
		List <prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(clt.getCategory());
		
		model.addAttribute("articles", list_art);
		
		return "vente/info_bl";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@Autowired
    private DataSource localDataSource;
	
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
		
		try {
			
			pdf = gd.generate_BL(bl.getId(), bl.getNumero(), bl.getMatricule(), qr_code, localDataSource.getConnection());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		
		client clt = bon_lRepo.getOne(id_bl).getClient();
		
		List <prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(clt.getCategory());
		
		model.addAttribute("articles", list_art);
		
		return "vente/edit_bl";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/edit_bl_post",method=RequestMethod.POST)
	public String edit_bl(HttpServletRequest req,
			@RequestParam("id_bl") long id_bl,
			@RequestParam("total_tva") double montant_tva,
			@RequestParam("total_ttc") double montant_ttc,
			@RequestParam("total_ht") double montant_ht,
			
			@RequestParam(name="art", defaultValue = "0") long [] article,
			@RequestParam(name="id_um", defaultValue = "0") long [] id_unite_mesure,
			@RequestParam(name="id_magasin",defaultValue = "0") long [] id_magasin,
			@RequestParam(name="montant_ht_art", defaultValue = "0") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam(name="tva_art", defaultValue = "0") double [] tva_art,
			@RequestParam(name="prix_unitaire", defaultValue = "0") double [] prix_u_ht,
			@RequestParam(name="qte", defaultValue = "0") double [] quantite,
			@RequestParam(name="tva_art", defaultValue = "0") double [] montant_tva_art,
			
			@SessionAttribute("user") users user){
			
			bon_livraison bl = bon_lRepo.getOne(id_bl);
			
			get_time_date gtd = new get_time_date();
			
			String today = gtd.get_date();
			
			String time = gtd.get_time();
			
			//------------------------- TEST PLAFOND
			
			int t = 0;
			
			client clt = bl.getClient();
			
			registre_commerce rc = bl.getRegistre_commerce();
			
			double balance_clt = clt.getSold_encours();
			
			if((balance_clt+montant_ttc)>clt.getPlafond()) {
				
				t = 1;
				
			}
			
			double balance_rc = rc.getSold_encours();
			
			if((balance_rc+montant_ttc)>rc.getPlafond()) {
				
				t = 1;
				
			}
			
			//----------------------------------------------
			
			String ret;
			
			if(t==0) {
				
				//-------------------- tracking operation -----------------------------------
				
				bon_livraison_backup bl_b = new bon_livraison_backup(bl, user);
				blbRepo.save(bl_b);bldbRepo.flush();
				
				trk.add_track("bon_livraison", "Modification Bon livraison", bl.getId(), user);
				
				//-------------------- tracking operation -----------------------------------
				
				//------------------------- edit BL --------------------------------------
				
				bl.setMontant_ht(montant_ht);
				bl.setMontant_ttc(montant_ttc);
				bl.setTva(montant_tva);
				
				bl.setDate(today);
				bl.setTime(time);
				
				bl.setUsers(user);
				
				bon_lRepo.save(bl); bon_lRepo.flush();
				
				//---------------------------------------------------------------
				
				List <bon_livraison_detail> bld = bon_l_dRepo.get_bl_detail(bon_lRepo.getOne(id_bl));
				
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
						
						bon_livraison_detail bl_d = new bon_livraison_detail(bl, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i], 
								montant_ht_art[i], tva_art[i], montant_tva_art[i], null, umRepo.getOne(id_unite_mesure[i]),
								false, magasinRepo.getOne(id_magasin[i]));
						
						bon_l_dRepo.save(bl_d);bon_l_dRepo.flush();
						
					}
					
				}
				
				String qr_code = generateQRcode.createQRcode(bl.getNumero(), "BL");
				
				String pdf = "";
				
				generate_Doc gd = new generate_Doc();
				
				try {
					
					pdf = gd.generate_BL(bl.getId(), bl.getNumero(), bl.getMatricule(), qr_code, localDataSource.getConnection());
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
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
			
			//--------------------- TEST if all row validated so validate all (create facture) -------------
			
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
				
				facture  last_fact = factRepo.findFirst1ByOrderByIdDesc();
				
				String last_number = "";
				
				if(last_fact!=null) {
					
					last_number = last_fact.getNumero();
					
				}
				
				numerotation_by_year nby = new numerotation_by_year();
				
				String numero_fact = nby.return_num_facture(last_number, user.getUnite().getId());
				
				facture fact = new facture(bl.getClient(), bl.getRegistre_commerce(), bl.getCommande().getClient_registrecommerce(), today, time, numero_fact, 
						bl.getMontant_ht(), 0, bl.getMatricule(), bl.getMontant_ttc(), bl.getTva(), "", bl, bl.getCommande().getMode_paiement(), user, false, 
						bl.getMontant_ttc(), false);
				
				factRepo.save(fact);factRepo.flush();
				
				//-------------------- tracking operation -----------------------------------
				
				trk.add_track("facture", "creation facture apres validation BL", fact.getId(), user);
				
				//-------------------- tracking operation -----------------------------------
				
				List<bon_livraison_detail> bld_list = bon_l_dRepo.get_bl_detail(bl);
				
				for(int i=0;i<bld_list.size();i++) {
					
					bon_livraison_detail bld = bld_list.get(i);
					
					facture_detail fct_d = new facture_detail(fact, bld.getArticle(), bld.getQuantite(), bld.getPrix_u_ht(), bld.getMontant_ht(), bld.getTva(),
							bld.getMontant_tva(), (bld.getMontant_ht() + bld.getMontant_tva()), bld.getUnite_mesure());
					
					fact_detRepo.save(fct_d);fact_detRepo.flush();
					
				}
				
				//------------------------------------------------ insert into mouvement table
				
				mouvement mvm = new mouvement(clt, rc, bl.getMontant_ttc(), "Facture", fact.getId(), gtd.get_date(), gtd.get_time(), "", sold_encours_clt, sold_encours_rc, new_sold_clt, new_sold_rc);
				
				mvmRepo.save(mvm);mvmRepo.flush();
				
				//------------------------------------------------ update facture to table regroupment
				
				prof_cmd_bl_fact_client_rc_avoir grp = grpRepo.get_relation_by_bl(bl);
				
				grp.setFacture(fact);
				
				grpRepo.save(grp); grpRepo.flush();
				
				commande cmd = grp.getCommande();
				
				cmd.setCloturer(true);
				
				cmdRepo.save(cmd); cmdRepo.flush();
				
				//------------------------------------------------ Create QR Code img
				
				
				//------------------------------------------------ prepare and create PDF fact
				
				
				//------------------------------------------------ END
				
				bl.setEtat_livraison(1);
				
				bon_lRepo.save(bl); bon_lRepo.flush();
			
			//---------------	
			}
		
		}
		
		return "redirect:/info_bl?id_bl="+bl.getId();
		
	}
	
}
