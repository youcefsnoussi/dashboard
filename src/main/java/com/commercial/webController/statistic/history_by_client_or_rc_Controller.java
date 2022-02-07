package com.commercial.webController.statistic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_ristourneRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.prof_cmd_bl_fact_client_rc_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.remboursementRepository;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.services.generate_Doc;


@Controller
@SessionAttributes("user")

public class history_by_client_or_rc_Controller {

	public history_by_client_or_rc_Controller() {
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
	
	//---------------------------------------------
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_detRepo;
	
	@Autowired
	facture_avoirRepository fact_avoirRepo;
	
	@Autowired
	facture_avoir_detailRepository fact_avoir_detRepo;
	
	@Autowired
	prof_cmd_bl_fact_client_rc_avoirRepository grpRepo;
	
	@Autowired
	mouvementRepository mvmRepo;
	
	@Autowired
	facture_ristourneRepository fact_risRepo;
	
	@Autowired
	paiementRepository payRepo;
	
	@Autowired
	remboursementRepository rmbRepo;
	
	@RequestMapping(value="/historic_clt")
	public String history_by_clt(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 @RequestParam("clt") long clt,
						 Model model){
		
		
		
		String ret = "statistic/history_by_client";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		if(clt==0) {
			
			model.addAttribute("id_client",clt);
			model.addAttribute("client",clientRepo.findAll());
			model.addAttribute("history",null);
			model.addAttribute("sold_debut_clt",0);
			model.addAttribute("sold_debut_rc",0);
			model.addAttribute("sold_fin_clt",0);
			model.addAttribute("sold_fin_rc",0);
			model.addAttribute("start",conv.convertion_MyDate_to_InputDate(gtd.get_date()) );
			model.addAttribute("end",conv.convertion_MyDate_to_InputDate(gtd.get_date()) );
			
		}
		else {
			
			if(date_debut.equals("0") && date_fin.equals("0")) {
				
				List<mouvement> mvml = mvmRepo.mouvement_by_client(clientRepo.getOne(clt));
				
				double sold_debut_clt = mvml.get(0).getOld_sold_client();
				double sold_debut_rc = mvml.get(0).getOld_sold_rc();
				
				double sold_fin_clt = mvml.get(mvml.size()-1).getNew_sold_client();
				double sold_fin_rc = mvml.get(mvml.size()-1).getNew_sold_rc();
				
				model.addAttribute("id_client",clt);
				model.addAttribute("client",clientRepo.findAll());
				model.addAttribute("history",mvml);
				model.addAttribute("sold_debut_clt",sold_debut_clt);
				model.addAttribute("sold_debut_rc",sold_debut_rc);
				model.addAttribute("sold_fin_clt",sold_fin_clt);
				model.addAttribute("sold_fin_rc",sold_fin_rc);
				model.addAttribute("start",conv.convertion_MyDate_to_InputDate(gtd.get_date()) );
				model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()) );
				
			}
			else {
				
				List<mouvement> mvml=null;
				
				double sold_debut_clt = 0;
				double sold_debut_rc = 0;
				
				double sold_fin_clt = 0;
				double sold_fin_rc = 0;
				
				//------------------------------------------------------
				
				try {
					
					mvml = mvmRepo.mouvement_by_client_intervall(clientRepo.getOne(clt), 
													conv.convertion_from_InputDate(date_debut), conv.convertion_from_InputDate(date_fin));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("===========>size mvl > "+mvml.size());
				
				if(mvml.size()==0) {
					
					sold_debut_clt = 0;
					sold_debut_rc = 0;
					
					sold_fin_clt = 0;
					sold_fin_rc = 0;
					
				}
				else {
					
					sold_debut_clt = mvml.get(0).getOld_sold_client();
					sold_debut_rc = mvml.get(0).getOld_sold_rc();
					
					sold_fin_clt = mvml.get(mvml.size()-1).getNew_sold_client();
					sold_fin_rc = mvml.get(mvml.size()-1).getNew_sold_rc();
					
				}
				
				
				model.addAttribute("id_client",clt);
				model.addAttribute("client",clientRepo.findAll());
				model.addAttribute("history",mvml);
				model.addAttribute("sold_debut_clt",sold_debut_clt);
				model.addAttribute("sold_debut_rc",sold_debut_rc);
				model.addAttribute("sold_fin_clt",sold_fin_clt);
				model.addAttribute("sold_fin_rc",sold_fin_rc);
				model.addAttribute("start",date_debut);
				model.addAttribute("end",date_fin);
				
			}
			
		}
		
		return ret;
		
	}
	
	//____________________________________________________________________________RC History__________________
	
	@RequestMapping(value="/historic_rc")
	public String history_by_rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("start") String date_debut,
						 @RequestParam("end") String date_fin,
						 @RequestParam("rc") long rc,
						 Model model){
		
		
		
		String ret = "statistic/history_by_rc";
		
		get_time_date gtd = new get_time_date();
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		if(rc==0) {
			
			model.addAttribute("id_rc",rc);
			model.addAttribute("rc",rcRepo.findAll());
			model.addAttribute("history",null);
			//model.addAttribute("sold_debut_clt",0);
			model.addAttribute("sold_debut_rc",0);
			//model.addAttribute("sold_fin_clt",0);
			model.addAttribute("sold_fin_rc",0);
			model.addAttribute("start",conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			model.addAttribute("end",conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			if(date_debut.equals("0") && date_fin.equals("0")) {
				
				List<mouvement> mvml = mvmRepo.mouvement_by_rc(rcRepo.getOne(rc));
				
				//double sold_debut_clt = mvml.get(0).getOld_sold_client();
				double sold_debut_rc = mvml.get(0).getOld_sold_rc();
				
				//double sold_fin_clt = mvml.get(mvml.size()-1).getNew_sold_client();
				double sold_fin_rc = mvml.get(mvml.size()-1).getNew_sold_rc();
				
				System.out.println("--->"+mvml.get(mvml.size()-1).getNew_sold_rc());
				
				model.addAttribute("id_rc",rc);
				model.addAttribute("rc",rcRepo.findAll());
				model.addAttribute("history",mvml);
				
				if(mvml.size()==0) {
					
					model.addAttribute("sold_debut_rc",rcRepo.getOne(rc).getSold_encours());
					model.addAttribute("sold_fin_rc",rcRepo.getOne(rc).getSold_encours());
					
				}
				else {
					
					model.addAttribute("sold_debut_rc",sold_debut_rc);
					model.addAttribute("sold_fin_rc",sold_fin_rc);
					
				}
				
				model.addAttribute("start",conv.convertion_MyDate_to_InputDate(gtd.get_date()));
				model.addAttribute("end",conv.convertion_MyDate_to_InputDate(gtd.get_date()));
				
			}
			else {
				
				List<mouvement> mvml=null;
				
				//double sold_debut_clt = 0;
				double sold_debut_rc = 0;
				
				//double sold_fin_clt = 0;
				double sold_fin_rc = 0;
				
				//------------------------------------------------------
					
				mvml = mvmRepo.mouvement_by_rc_intervall(rcRepo.getOne(rc), date_debut, date_fin);
				
				List <String> nums = new ArrayList<String>();
				
				if(mvml.size()==0) {
					
					sold_debut_rc = rcRepo.getOne(rc).getSold_encours();
					
					sold_fin_rc = rcRepo.getOne(rc).getSold_encours();
					
				}
				else {
					
					//sold_debut_clt = mvml.get(0).getOld_sold_client();
					sold_debut_rc = mvml.get(0).getOld_sold_rc();
					
					//sold_fin_clt = mvml.get(mvml.size()-1).getNew_sold_client();
					sold_fin_rc = mvml.get(mvml.size()-1).getNew_sold_rc();
					
					for(mouvement mvm : mvml) {
						
						switch (mvm.getType_operation()) {
						
							case "Facture":
							{
								
								nums.add(factRepo.getOne(mvm.getId_operation()).getNumero());
								
							}
							break;
							
							case "Facture Avoire":
							{
								
								nums.add(fact_avoirRepo.getOne(mvm.getId_operation()).getNumero());
								
							}
							break;
							
							case "Facture Ristourne":
							{
								
								nums.add(fact_risRepo.getOne(mvm.getId_operation()).getNumero());
								
							}
							break;
							
							case "Paiement":
							{
								
								nums.add(payRepo.getOne(mvm.getId_operation()).getId().toString());
								
							}
							break;
							
							case "Annulation Paiement":
							{
								
								nums.add(payRepo.getOne(mvm.getId_operation()).getId().toString());
								
							}
							break;
							
							case "Remboursement":
							{
								
								nums.add(rmbRepo.getOne(mvm.getId_operation()).getId().toString());
								
							}
							break;
							
							case "Regularisation Sold (-)":
							{
								
								nums.add("");
								
							}
							break;
							
							case "Regularisation Sold (+)":
							{
								
								nums.add("");
								
							}
							break;
							
							default:
							break;
						
						}
						
					}
					
				}
				
				
				model.addAttribute("id_rc",rc);
				model.addAttribute("rc",rcRepo.findAll());
				model.addAttribute("history",mvml);
				model.addAttribute("nums",nums);
				//model.addAttribute("sold_debut_clt",sold_debut_clt);
				model.addAttribute("sold_debut_rc",sold_debut_rc);
				//model.addAttribute("sold_fin_clt",sold_fin_clt);
				model.addAttribute("sold_fin_rc",sold_fin_rc);
				model.addAttribute("start",date_debut);
				model.addAttribute("end",date_fin);
				
			}
			
		}
		
		return ret;
		
	}
	
	//----------------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_releve_client")
	public String print_vente_client(HttpServletRequest request,
						 @RequestParam("id_rc") long id_rc,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_releve_client(start, end, rc);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
}
