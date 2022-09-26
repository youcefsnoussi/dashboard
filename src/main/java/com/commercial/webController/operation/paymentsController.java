package com.commercial.webController.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.remboursement;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiement_factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.remboursementRepository;
import com.commercial.entities.schema.static_data.banque;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.services.generate_Doc;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class paymentsController {
	
	@Autowired
	banqueRepository banqueRepo;
	
	@Autowired
	type_reglementRepository type_rRepo;
	
	@Autowired
	category_clientRepository cat_clientRepo;
	
	@Autowired
	clientRepository clientRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	client_registreCommerceRepository c_rcRepo;
	
	@Autowired
	uniteRepository uniteRepo;
	
	@Autowired
	mode_paiementRepository mode_payRepo;
	
	@Autowired
	paiementRepository payRepo;
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	paiement_factureRepository pay_factRepo;
	
	@Autowired
	mouvementRepository mvmRepo;
	
	@Autowired
	remboursementRepository rembRepo;
	
	@Autowired
	track_operations trk;
	
	public paymentsController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping(value="/new_payment")
	public String nouveau_paiement(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("add_payment"))) 
		{ ret = "operation/new_payment"; }
		else { ret = "403"; }
		
		//----------------------------------------------------------------
		
		model.addAttribute("mode_payement", mode_payRepo.findAll().stream().filter(mp-> mp.isDisplay()).toArray() );
		
		model.addAttribute("bank", banqueRepo.get_banks_displayed());
		
		//model.addAttribute("client", clientRepo.findAll());
		
		model.addAttribute("clt_rc",c_rcRepo.findAll());
		
		//model.addAttribute("unite", uniteRepo.findAll());
		
		return ret;
		
	}
	
	//-------------------------------------------------------------------------------
	
	@RequestMapping(value="/new_rmb")
	public String nouveau_remboursement(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("add_remboursement"))) 
		{ ret = "operation/remboursement"; }
		else { ret = "403"; }
		
		//----------------------------------------------------------------
		
		model.addAttribute("mode_payement", mode_payRepo.findAll());
		
		model.addAttribute("bank", banqueRepo.findAll());
		
		//model.addAttribute("client", clientRepo.findAll());
		
		model.addAttribute("clt_rc",c_rcRepo.findAll());
		
		//model.addAttribute("unite", uniteRepo.findAll());
		
		return ret;
		
	}
	
	//-------------------------------------------------------------------------------
	
	@GetMapping("/edit_payment/{id}")
	public String edit_paiement_get(HttpServletRequest request,
						 @PathVariable("id") long id_payement,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		String ret = "";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("edit_payment"))) 
		{ ret = "operation/edit_payment"; }
		else { return "403"; }
		
		//----------------------------------------------------------------
		
		paiement pay =  payRepo.findById(id_payement).orElseThrow(() -> new IllegalArgumentException("ID Paiement Invalid:" + id_payement));
		
		if(pay.isVerification()) return "403";
		
		pay.setDate(conv.convertion_MyDate_to_InputDate(pay.getDate()));
		
		//model.addAttribute("paiement", new paiement()); 
		
		model.addAttribute("paiement", pay);
		
		//model.addAttribute("pay", pay);
		
		model.addAttribute("mode_payement", mode_payRepo.findAll());
		
		model.addAttribute("bank", banqueRepo.get_banks_displayed());
		
		return ret;
		
	}
	
	//-------------------------------------------------------------------------------
	
	@PostMapping("/update_payement/{id}")
	public String updatePayment(
							 @PathVariable("id") long id, 
							 @Valid paiement pay,  
							 @SessionAttribute("user") users user,
							  BindingResult result, 
							  Model model) {
	    
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
	    Optional<paiement> pp = payRepo.findById(id);
		
	    paiement p = pp.get();
	    
	    p.setBanque(pay.getBanque());
	    p.setMode_paiement(pay.getMode_paiement());
	    p.setDate(conv.convertion_InputDate_to_MyDate(pay.getDate()));
	    p.setNumero_piece(pay.getNumero_piece());
	    p.setInfo_supp_banque(pay.getInfo_supp_banque());
	    p.setObservation(pay.getObservation());
	    
	    payRepo.save(p); payRepo.flush();
	    
	    trk.add_track("paiement", "Modification Paiement", p.getId(), user);
	    
	    return "redirect:/edit_payment/"+pay.getId();
	}
	
	//-------------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_bordereau_pay")
	public String print_bordereau(HttpServletRequest request,
						 @RequestParam("mode_pay") long [] mode_pay,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start = conv.convertion_InputDate_to_MyDate(start);
		
		end = conv.convertion_InputDate_to_MyDate(end);
		
		String pdf = "";
		
		if( mode_pay.length !=1 ) pdf = gd.generate_bordereau_pay(start, end, user.getUnite().getNom_unite(), mode_pay); 
		
		else if( mode_pay [0] != 0) pdf = gd.generate_bordereau_pay(start, end, user.getUnite().getNom_unite(), mode_pay);
		
		else pdf = gd.generate_impaye();
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//-------------------------------------------------------------------------------
	
	@RequestMapping(value="/list_payments")
	public String list__paiements(HttpServletRequest request,
						 @RequestParam("date_debut") String date_debut,
						 @RequestParam("date_fin") String date_fin,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "operation/list_payments";
		
		boolean cancel_pay = false, edit_pay = false;
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("cancel_payment"))) 
		{ cancel_pay = true; }
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("edit_payment"))) 
		{ edit_pay = true; }
		
		//----------------------------------------------------------------
		
		get_time_date gtd = new get_time_date();
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		try {
		
			if(date_debut.equals("0") && date_fin.equals("0")) {
				
				
					model.addAttribute("payments", payRepo.get_payments_no_cancled_interval(conv.convertion_from_my_date(gtd.get_date()), 
																							conv.convertion_from_my_date(gtd.get_date())));
				
				
				date_d = conv.convertion_MyDate_to_InputDate(gtd.get_date());
				
				date_f = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			}
			else if(date_debut.equals("1") && date_fin.equals("1")) {
				
				//model.addAttribute("list_facture", factRepo.findAll());
				
			}
			else {
					
					if(date_debut.contains("/")) {
						
						model.addAttribute("payments", 
									payRepo.get_payments_no_cancled_interval(conv.convertion_from_my_date(date_debut), 
																			 conv.convertion_from_my_date(date_fin)));
						
						model.addAttribute("selected_year",date_debut.substring(6));
						
						date_d =  conv.convertion_MyDate_to_InputDate(date_debut);
						
						date_f =  conv.convertion_MyDate_to_InputDate(date_fin);
						
					}
					else {
						
						model.addAttribute("payments", payRepo.get_payments_no_cancled_interval(conv.convertion_from_InputDate(date_debut), 
																								conv.convertion_from_InputDate(date_fin)));
						
						date_d = date_debut;
						
						date_f = date_fin;
						
					}
					
			}
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("count_impaye", payRepo.get_count_paiements_impaye());
		
		model.addAttribute("impayments", payRepo.get_paiements_impaye());
		
		model.addAttribute("years", factRepo.get_years_db());
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		//----------------------------------------------------------------
		
		model.addAttribute("cancel_pay", cancel_pay);
		
		model.addAttribute("edit_pay", edit_pay);
		
		model.addAttribute("mode_payement", mode_payRepo.findAll());
		
		return ret;
		
	}
	
	//__________________________________________POST____________________________________________________________________
	
	@RequestMapping(value="/new_payment_post",method=RequestMethod.POST, consumes = {"multipart/form-data"})
	public String insert_new_payment(HttpServletRequest req,
			//@RequestParam("client") long id_client,
			//@RequestParam("rc") long id_rc,
			@RequestParam("rc_client") long id_clt_rc,
			@RequestParam("mode_pay") long mode_pay,
			@RequestParam("bank") long bank,
			@RequestParam("num_piece") String num_piece,
			@RequestParam("montant") double montant,
			@RequestParam("date_piece") String date,
			@RequestParam("observation") String obs,
			@RequestParam("info_supp_bank") String info_supp_bank,
			@Valid @RequestParam("img_piece") MultipartFile img_piece,
			
			@SessionAttribute("user") users user){
			
			//System.out.println(" ||==========> "+logo.getName()+" //==========> "+logo.getSize());
			/*
			paiement pp = payRepo.findFirst1ByOrderByIdDesc();
			
			long new_id = 1;
		
			if(pp!=null) {
				
				new_id = pp.getId()+1;
				
			}
			*/
			String img_path = "";
			
			if(!img_piece.isEmpty()) {
				
				img_path = "D:/Commercial/payments/" +img_piece.getName();
				
				try {

		            // Get the file and save it somewhere
		            byte[] bytes = img_piece.getBytes();
		            Path path = Paths.get("D:\\Commercial\\payments\\" + img_piece.getName());
		            Files.write(path, bytes);

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
			}
			
			get_time_date gtd = new get_time_date();
			convert_string_to_date_util cc = new convert_string_to_date_util();
			
			// ---------------- insert table paiment
			
			banque banque = null;
			
			String ret = "";
			
			if(bank!=0) {
				
				banque = banqueRepo.getOne(bank);
				
			}
			
			long id_client = c_rcRepo.getOne(id_clt_rc).getClient().getId();
			
			long id_rc = c_rcRepo.getOne(id_clt_rc).getRegistre_commerce().getId();
			
			//System.out.println("banc = "+banque.getNom_banque()+" / "+num_piece+" / "+date+" / "+montant+" / "+rcRepo.getOne(id_rc).getNom());
			
			List<paiement> p = payRepo.if_payment_already_exist(banque, num_piece, cc.convertion_InputDate_to_MyDate(date), 
											/*montant,*/ rcRepo.getOne(id_rc));
			
			System.out.println("if pay exist "+p.size());
			
			long id_pay = 0;
			
			if(p.size()==0) {
				
				paiement pay = new paiement(clientRepo.getOne(id_client), rcRepo.getOne(id_rc), montant, 
											cc.convertion_InputDate_to_MyDate(date), gtd.get_date(), gtd.get_time(), mode_payRepo.getOne(mode_pay),
						banque, user, num_piece, img_path, false, obs, info_supp_bank, false, montant);
				
				
				payRepo.save(pay);payRepo.flush();
				
				id_pay = pay.getId();
				
				//-------------------- tracking operation -----------------------------------
				
				trk.add_track("paiement", "Ajout nouveau paiement", pay.getId(), user);
				
				//-------------------- tracking operation -----------------------------------
				
				//-------------- update sold client
				
				client clt = clientRepo.getOne(id_client);
				
				double sold_encours_clt = clt.getSold_encours();
				
				double new_sold_clt = sold_encours_clt - montant;
				
				clt.setSold_encours(new_sold_clt);
				
				clientRepo.save(clt);clientRepo.flush();
				
				//-------------- update sold RC
				
				registre_commerce rc = rcRepo.getOne(id_rc);
				
				double sold_encours_rc = rc.getSold_encours();
				
				double new_sold_rc = sold_encours_rc - montant;
				
				rc.setSold_encours(new_sold_rc);
				
				rcRepo.save(rc);rcRepo.flush();
				
				//-------------- update sold RC client relationship
			
				//List<client_registreCommerce> crc = c_rcRepo.if_relation_existe(clt, rc);
				
				//int last_index = crc.size()-1;
				
				client_registreCommerce c_rc = c_rcRepo.getOne(id_clt_rc);
				
				double sold_encours = c_rc.getMontant_actuel();
				
				double new_sold = sold_encours - montant;
				
				c_rc.setMontant_actuel(new_sold);
				
				c_rcRepo.save(c_rc);c_rcRepo.flush();
				
				//-------------- insert to mouvement 
				
				//mouvement mvm = new mouvement(clt, rc, montant, "paiement", pay.getId(), date, gtd.get_time(), banque.getNom_banque());
				
				mouvement mvm = new mouvement(clt, rc, montant, "Paiement", pay.getId(), gtd.get_date(), gtd.get_time(), "", sold_encours_clt, sold_encours_rc,
						new_sold_clt, new_sold_rc);
				
				mvmRepo.save(mvm);mvmRepo.flush();
				
				//------------ update sold factures AND sold paiement
				
				List<facture> list_fact = factRepo.get_factures_not_solde_by_rc(rc);
				
				int watch_dog_out = 0;
				
				int i =0;
				
				double montant_buf = montant;
				
				if(list_fact.size()==0) {
					
					watch_dog_out = 1;
					
				}
				
				while(watch_dog_out==0) {
					
					facture fct = list_fact.get(i);
					  
					if(montant_buf <= fct.getSold_rest()) {
						
						double new_sold_restant = fct.getSold_rest() - montant_buf;
						
						fct.setSold_rest(new_sold_restant);
						
						pay.setSold_rest(0);
						
						pay.setEtat_sold(true);
						
						payRepo.save(pay);payRepo.flush();
						
						if(new_sold_restant==0) {
							
							fct.setEtat_sold(true);
							
						}
						
						factRepo.save(fct);factRepo.flush();
						
						paiement_facture pay_fac = new paiement_facture(pay, fct, montant_buf);
						
						pay_factRepo.save(pay_fac);pay_factRepo.flush();
						
						watch_dog_out = 1;
						
					}
					else {
						
						montant_buf = montant_buf - fct.getSold_rest();
						
						pay.setSold_rest(montant_buf);
						payRepo.save(pay);payRepo.flush();
						
						double sold_rest_fct = fct.getSold_rest();
						
						fct.setSold_rest(0);
						fct.setEtat_sold(true);
						factRepo.save(fct);factRepo.flush();
						
						paiement_facture pay_fac = new paiement_facture(pay, fct, sold_rest_fct);
						
						pay_factRepo.save(pay_fac);pay_factRepo.flush();
						
						if((i+1)<list_fact.size()) {
							
							i++;
							
						}
						else {
							
							watch_dog_out = 1;
							
						}
						
					}
					
				}
			
			//--------------- end
				
				ret = "ee";
				
			}
			
			return "redirect:/new_payment?ret="+ret+"&id_p="+id_pay;
		
	}
	
	//_____________________________________ ADD image payment ________________________________________---
	
	@RequestMapping(value="/add_img_payment",method=RequestMethod.POST, consumes = {"multipart/form-data"})
	public String add_img_payment(HttpServletRequest req,
			@RequestParam("id-payment") long id_pay,
			@Valid @RequestParam("img_piece") MultipartFile img_piece,
			
			@SessionAttribute("user") users user){
			
			String ret = "operation/list_payments";
			
			String img_path = "";
			
			if(!img_piece.isEmpty()) {
				
				img_path = "D:/Commercial/payments/" + id_pay;
				
				try {

		            // Get the file and save it somewhere
		            byte[] bytes = img_piece.getBytes();
		            Path path = Paths.get("D:\\Commercial\\payments\\" + id_pay);
		            Files.write(path, bytes);

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
			}
			
			paiement pay = payRepo.getOne(id_pay);
			
			pay.setPath_img(img_path);
			
			payRepo.save(pay);payRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("paiement", "Ajout l'image du paiement", pay.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			return ret;
		
	}
	
	//_____________________________________ remboursement ________________________________________---
	
	@RequestMapping(value="/remboursement_post",method=RequestMethod.POST, consumes = {"multipart/form-data"})
	public String remboursement(HttpServletRequest req,
			//@RequestParam("client") long id_client,
			//@RequestParam("rc") long id_rc,
			@RequestParam("rc_client") long id_clt_rc,
			@RequestParam("mode_pay") long mode_pay,
			@RequestParam("bank") long bank,
			@RequestParam("num_piece") String num_piece,
			@RequestParam("montant") double montant,
			@RequestParam("date_piece") String date,
			@Valid @RequestParam("img_piece") MultipartFile img_piece,
			
			@SessionAttribute("user") users user){
			
			remboursement remb = rembRepo.findFirst1ByOrderByIdDesc();
			
			long id_client = c_rcRepo.getOne(id_clt_rc).getClient().getId();
			
			long id_rc = c_rcRepo.getOne(id_clt_rc).getRegistre_commerce().getId();
			
			long new_id = 1;
			
			if(remb!=null) {
				
				new_id = remb.getId()+1;
				
			}
			
			String img_path = "";
			
			if(!img_piece.isEmpty()) {
				
				img_path = "D:/Commercial/remboursement/" + new_id;
				
				try {
	
		            // Get the file and save it somewhere
		            byte[] bytes = img_piece.getBytes();
		            Path path = Paths.get("D:\\Commercial\\remboursement\\" + new_id);
		            Files.write(path, bytes);
	
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
			}
			
			get_time_date gtd = new get_time_date();
			convert_string_to_date_util cc = new convert_string_to_date_util();
			
			// ---------------- insert table paiment
			
			banque banque = null;
			
			if(bank!=0) {
				
				banque = banqueRepo.getOne(bank);
				
			}
			
			remboursement rmb = new remboursement(clientRepo.getOne(id_client), rcRepo.getOne(id_rc), montant, cc.convertion_InputDate_to_MyDate(date), 
					gtd.get_date(), gtd.get_time(), mode_payRepo.getOne(mode_pay), banque, user, num_piece, img_path, false);
			
			
			rembRepo.save(rmb);rembRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("remboursement", "Ajout d'un rembourssement", rmb.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			//-------------- update sold client
			
			client clt = clientRepo.getOne(id_client);
			
			double sold_encours_clt = clt.getSold_encours();
			
			double new_sold_clt = sold_encours_clt + montant;
			
			clt.setSold_encours(new_sold_clt);
			
			clientRepo.save(clt);clientRepo.flush();
			
			//-------------- update sold RC
			
			registre_commerce rc = rcRepo.getOne(id_rc);
			
			double sold_encours_rc = rc.getSold_encours();
			
			double new_sold_rc = sold_encours_rc + montant;
			
			rc.setSold_encours(new_sold_rc);
			
			rcRepo.save(rc);rcRepo.flush();
			
			//-------------- update sold RC client relationship
			
			List<client_registreCommerce> crc = c_rcRepo.if_relation_existe(clt, rc);
			
			int last_index = crc.size()-1;
			
			client_registreCommerce c_rc = crc.get(last_index);
			
			double sold_encours = c_rc.getMontant_actuel();
			
			double new_sold = sold_encours - montant;
			
			c_rc.setMontant_actuel(new_sold);
			
			c_rcRepo.save(c_rc);c_rcRepo.flush();
			
			//-------------- insert to mouvement 
			
			//mouvement mvm = new mouvement(clt, rc, montant, "paiement", pay.getId(), date, gtd.get_time(), banque.getNom_banque());
			
			mouvement mvm = new mouvement(clt, rc, montant, "Remboursement", rmb.getId(), gtd.get_date(), gtd.get_time(), "", sold_encours_clt, sold_encours_rc,
					new_sold_clt, new_sold_rc);
			
			mvmRepo.save(mvm);mvmRepo.flush();
			
			return "redirect:/new_rmb?ret=ee&id_rmb="+rmb.getId();
		
	}
	
	@RequestMapping(value="/list_payment_cl")
	public String list_paiements_clients(HttpServletRequest request,
			 @SessionAttribute("user") users user,
			 @RequestParam(value="id_client", defaultValue="0") long id_client,
			 Model model){
		
		String ret = "client/clientinfo/list_payments_cl";
		
		
		
		//----------------------------------------------------------------
		
		
		model.addAttribute("payments", payRepo.findByClientIdAndCancel(id_client, false));
		
		model.addAttribute("mode_payement", mode_payRepo.findAll());
		
		return ret;
		
	}
	
}
