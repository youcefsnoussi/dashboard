package com.commercial.webController.vente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.emballage_produitRepository;
import com.commercial.entities.schema.article.repository.pesage_produitRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.article.repository.produitRepository;
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.BonCommande;
import com.commercial.entities.schema.profoma_cmd_bl_fact.BonCommandeDetail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.BonCommandeDetailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.BonCommandeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commandeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.commande_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;

@Controller
@SessionAttributes("user")

public class bon_commandeController {
	
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
	BonCommandeRepository bon_cmdRepo;
	
	@Autowired
	BonCommandeDetailRepository bon_cmd_dRepo;
	
	@Autowired
	facture_detailRepository fact_dRepo;
	
	public bon_commandeController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/bon_cmd")
	public String bon_cmd(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "vente/bon_commande";
		
		//model.addAttribute("clients", clientRepo.client_active_only());
		
		model.addAttribute("clients", clt_rcRepo.ListRCwithCLIENT_active(LocalDate.now().toString()));
		
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
	
	@RequestMapping(value="/new_BonCommande_post",method=RequestMethod.POST)
	public String new_bon_commande(HttpServletRequest req,
			@RequestParam("id_rel_rc_clt") long id_relation_rc_clt,
			@RequestParam("date_debut") String date_d,
			@RequestParam("date_fin") String date_f,
			
			@RequestParam("art") long [] article,
			@RequestParam("id_um") long [] id_unite_mesure,
			@RequestParam("qte") double [] quantite,
			
			@SessionAttribute("user") users user){
				
				BonCommande last_bon_cmd = bon_cmdRepo.findFirst1ByOrderByNumeroDesc();
				
				String last_number = "";
				
				if(last_bon_cmd!=null) {
					
					last_number = last_bon_cmd.getNumero();
					
				}
				
				get_time_date gtd = new get_time_date();
				
				numerotation_by_year nby = new numerotation_by_year();
				
				String numero_bon_cmd = nby.return_num_BonCommande(last_number);
				
				String today = gtd.get_date();
				
				String time = gtd.get_time();
				
				client_registreCommerce clt_rc = clt_rcRepo.getOne(id_relation_rc_clt);
				
				convert_string_to_date_util ccs = new convert_string_to_date_util();
				
				String date_debut = ccs.convertion_InputDate_to_MyDate(date_d);
				
				String date_fin = ccs.convertion_InputDate_to_MyDate(date_f);
				
				BonCommande bon_cmd = new BonCommande(today, time, date_debut, date_fin, numero_bon_cmd, clt_rc.getClient(),
														clt_rc.getRegistre_commerce(), "", user);
				
				bon_cmdRepo.save(bon_cmd);
				bon_cmdRepo.flush();
				
				for(int i=0;i<article.length;i++) {
					
					if(quantite[i]!=0) {
						
						BonCommandeDetail bon_cmd_d = new BonCommandeDetail(bon_cmd, artRepo.getOne(article[i]), quantite[i], 
																umRepo.getOne(id_unite_mesure[i]));
						
						bon_cmd_dRepo.save(bon_cmd_d);bon_cmd_dRepo.flush();
						
					}
					
				}
				
				//------------------------------------------------ insert table regroupment
				
				
				return "redirect:/bon_cmd";
			
		}
	
		//------------------------------------------------------------- BON de COMMANDE TA3 TMENYIK -------------------
		
		@RequestMapping(value="/cmd_list_rc")
		public String cmd_list_rc(HttpServletRequest request,
								 @RequestParam(value="id_rc", defaultValue="0") long id_rc,
								 @RequestParam(value="start", defaultValue="0") String start,
								 @RequestParam(value="end", defaultValue="0") String end,
								 @RequestParam(value="type", defaultValue="0") int type,
								 @SessionAttribute("user") users user,
								 Model model){
			
			//get_time_date gtd = new get_time_date();
			
			String ret = "statistic/CumuleCommandeRC";
			
			List<Object[]> result = new ArrayList<>();
			
			model.addAttribute("list_rc", rcRepo.findAll(Sort.by(Sort.Direction.ASC,"code")));
			
			model.addAttribute("selected_rc", id_rc);
			
			model.addAttribute("selected_type", type);
			
			if(id_rc !=0) {
				
				result = (type==0) ? cmd_detRepo.get_commande_details(id_rc, start, end) 
							 : cmd_detRepo.get_commande_details_dates(id_rc, start, end);
				
			}
			
			model.addAttribute("result", result);
			
			return ret;
			
		}
		
		//----------------------------------------------------------------------
		
		@RequestMapping(value="/vente_commande_client")
		public String vente_commande_client(HttpServletRequest request,
							 @RequestParam(value="id_rc", defaultValue="0") long id_rc,
							 @RequestParam(value="start", defaultValue="0") String start,
							 @RequestParam(value="end", defaultValue="0") String end,
							 @SessionAttribute("user") users user,
							 Model model){
			
			model.addAttribute("list_rc", rcRepo.findAll());
			
			System.out.println("---------> ENTER DJDID <----------------");
			
			convert_string_to_date_util conv = new convert_string_to_date_util();
			
			get_time_date gtd = new get_time_date();
			
			List <Object[]> list = new ArrayList<Object[]>();
			
			if(id_rc==0) {
				
				model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
				
				model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
				
				model.addAttribute("list", list);
				
				model.addAttribute("id_rc", id_rc);
				
			}
			else {
				
				model.addAttribute("start", start);
				
				model.addAttribute("end", end);
				
				registre_commerce rc = rcRepo.getOne(id_rc);
				
				list = fact_dRepo.get_quantite_sold_val_fact_by_rc(conv.convertion_InputDate_to_MyDate(start), 
																	conv.convertion_InputDate_to_MyDate(end), rc);
				
				for (Object[] obj : list) {
					
					int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1)*10;
					
					obj[5] = (double) obj[4] + randomNum;
					
					//System.out.println("->"+obj[5]);
				}
				
				/*
				list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_by_rc(conv.convertion_InputDate_to_MyDate(start), 
																	conv.convertion_InputDate_to_MyDate(end), rc) );
				*/
				model.addAttribute("list", list);
				
				model.addAttribute("id_rc", id_rc);
				
			}
			
			return "statistic/quantite_vendu_commander_client";		
		}
		
}
