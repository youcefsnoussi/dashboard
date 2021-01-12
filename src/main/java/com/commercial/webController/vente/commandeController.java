package com.commercial.webController.vente;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.repository.MagasinRepository;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.emballage_produitRepository;
import com.commercial.entities.schema.article.repository.magasin_articleRepository;
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
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.functions.track_operations;

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
	
	@RequestMapping(value="/commande")
	public String cmd(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/commande";
		
		get_time_date gtd = new get_time_date();
		
		//model.addAttribute("clients", clientRepo.client_active_only());
		
		
		
		List <client_registreCommerce> clt_rc = clt_rcRepo.ListRCwithCLIENT_active(gtd.get_date());
		
		List <client_registreCommerce> clt_rc_ret = new ArrayList<client_registreCommerce>();
		
		for(int i=0; i<clt_rc.size();i++) {
			
			client_registreCommerce cl_rc = clt_rc.get(i);
			
			List <bon_livraison> list_bl = bon_lRepo.get_bl_encours_by_rc(cl_rc.getRegistre_commerce());
			
			double montant = 0;
			
			for(int j=0;j<list_bl.size();j++) {
				
				montant = montant + list_bl.get(j).getMontant_ttc();
				
			}
			
			double sold_encours = cl_rc.getRegistre_commerce().getSold_encours();
			
			sold_encours = sold_encours + montant;
			
			cl_rc.getRegistre_commerce().setSold_encours(sold_encours);
			
			clt_rc_ret.add(cl_rc);
			
		}
		
		model.addAttribute("rcs_clt", clt_rc_ret);
		
		model.addAttribute("mode_paiements", mode_payRepo.findAll(Sort.by(Sort.Direction.ASC,"id")));
		
		model.addAttribute("magasin", magasinRepo.findAll(Sort.by(Sort.Direction.ASC,"id")));
		
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
	
	@RequestMapping(value="/new_commande_post",method=RequestMethod.POST)
	public String new_commande(HttpServletRequest req,
			//@RequestParam("id_client") long id_client,
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
			@RequestParam("id_magasin") long [] id_magasin,
			@RequestParam("montant_ht_art") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam("tva_art") double [] tva_art,
			@RequestParam("prix_unitaire") double [] prix_u_ht,
			@RequestParam("qte") double [] quantite,
			@RequestParam("tva_art") double [] montant_tva_art,
			
			@SessionAttribute("user") users user){
			
			commande last_cmd = cmdRepo.findFirst1ByOrderByIdDesc();
			
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
			
			commande cmd = new commande(today, time, numero_cmd, montant_ht, montant_tva, montant_ttc, "", null, null, user, false, matricule_camion
					,mode_payRepo.getOne(id_mode_reg), clt_rc, pourc_reduction);
			
			cmdRepo.save(cmd);cmdRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("commande", "Creation commande", cmd.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			for(int i=0;i<article.length;i++) {
				
				if(quantite[i]!=0) {
					
					commande_detail cmd_d = new commande_detail(cmd, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i], montant_ht_art[i], 
							(montant_ht_art[i]*(tva_art[i]/100)), tva_art[i], umRepo.getOne(id_unite_mesure[i]));
					
					cmd_detRepo.save(cmd_d);cmd_detRepo.flush();
					
				}
				
			}
			
			bon_livraison last_bl = bon_lRepo.findFirst1ByOrderByIdDesc();
			
			last_number = "";
			
			if(last_bl!=null) {
				
				last_number = last_bl.getNumero();
				
			}
			
			String numero_bl = nby.return_num_BonLivraison(last_number);
			
			bon_livraison bl = new bon_livraison(clt, rc, today, time, numero_bl, matricule_camion, "", cmd, null, user, 0, montant_ht, montant_tva, montant_ttc);
			
			bon_lRepo.save(bl);bon_lRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("bon_livraison", "Génération de bon livraison a partir de la commande", cmd.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			for(int i=0;i<article.length;i++) {
				
				if(quantite[i]!=0) {
					
					bon_livraison_detail bl_d = new bon_livraison_detail(bl, artRepo.getOne(article[i]), quantite[i], prix_u_ht[i], montant_ht_art[i], 
							(montant_ht_art[i]*(tva_art[i]/100)), tva_art[i], user, umRepo.getOne(id_unite_mesure[i]), false, magasinRepo.getOne(id_magasin[i]));
					
					
					bon_l_dRepo.save(bl_d);bon_l_dRepo.flush();
					
				}
				
			}
			
			//------------------------------------------------ insert table regroupment
			
			prof_cmd_bl_fact_client_rc_avoir grp = new prof_cmd_bl_fact_client_rc_avoir(clt_rc, null, cmd, bl, null, null);
			
			grpRepo.save(grp); grpRepo.flush();
			
			return "redirect:/commande?id_bl="+bl.getId()+"&num_bl="+bl.getNumero();
		
	}
	
}
