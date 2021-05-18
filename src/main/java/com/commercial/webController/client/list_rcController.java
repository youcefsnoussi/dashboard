package com.commercial.webController.client;

import java.util.ArrayList;
import java.util.List;

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

import com.commercial.entities.schema.article.repository.wilayaRepository;
import com.commercial.entities.schema.backup_edit.registre_commerce_backup;
import com.commercial.entities.schema.backup_edit.repository.registre_commerce_backupRepository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.generate_Doc;
import com.commercial.functions.track_operations;

@Controller
@SessionAttributes("user")

public class list_rcController {

	public list_rcController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	client_registreCommerceRepository crcRepo;
	
	@Autowired
	clientRepository cltRepo;
	
	@Autowired
	registre_commerce_backupRepository rcbRepo;
	
	@Autowired
	track_operations trk;
	
	@Autowired
	wilayaRepository wilayaRepo;
	
	@Autowired
	uniteRepository uniteRepo;
	
	@Autowired
	category_clientRepository cat_clientRepo;
	
	@Autowired
	banqueRepository banqueRepo;
	
	@Autowired
	type_reglementRepository type_rRepo;
	
	@Autowired
	mode_paiementRepository mpRepo;
	
	@RequestMapping(value="/list_rc")
	public String rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String id_client = "";
		
		id_client = request.getParameter("id_c");
		
		if(Integer.parseInt(id_client)==0) {
			
			model.addAttribute("rc_info", rcRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
			
		}
		else {
			
			client clt = cltRepo.getOne(Long.parseLong(id_client));
			
			model.addAttribute("rc_info", crcRepo.rc_by_client(clt));
			
		}
		
		return "client/list_rc";
		
	}
	
	@RequestMapping(value="/info_rc")
	public String view_rc(HttpServletRequest request,
						 @RequestParam("id_rc") Long id_rc,
						 @SessionAttribute("user") users user,
						 Model model){
		
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		String role_edit = "no_edit";
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("edit_rc"))) 
		{ role_edit="edit"; }
		else { role_edit="no_edit"; }
		
		model.addAttribute("edit_option", role_edit);
		
		model.addAttribute("rc", rc);
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		model.addAttribute("wilaya", wilayaRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("date_emission", conv.convertion_MyDate_to_InputDate(rc.getDate_emission()));
		
		model.addAttribute("date_fin", conv.convertion_MyDate_to_InputDate(rc.getDate_fin()));
		
		model.addAttribute("cat_client", cat_clientRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unite", uniteRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("type_reg", type_rRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("banque", banqueRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("mode_pay", mpRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		return "client/info_rc";
		
	}
	
	
	@RequestMapping(value="/edit_rc",method=RequestMethod.POST)
	public String edit_rc_DB(HttpServletRequest req,
		@RequestParam("id_rc") Long id_rc,
		@RequestParam("nom") String nom,
		@RequestParam("prenom") String prenom,
		@RequestParam("num_rc") String num_rc,
		@RequestParam("num_art") String num_art,
		@RequestParam("num_nif") String num_nif,
		@RequestParam("date_emission") String date_emission,
		@RequestParam("date_fin") String date_fin,
		@RequestParam("adresse") String adresse,
		@RequestParam("comune") String comune,
		@RequestParam("wilaya") long id_wilaya,
		@RequestParam(value = "tva", required = false) String tva,
		@RequestParam("plafond") double plafond,
		@RequestParam("activite") String activite,
		@RequestParam("etat_blockage") String etat_blockage,
		@RequestParam("cat_client") long cat_client,
		@RequestParam("banque") long id_banque,
		@RequestParam("type_reg") long type_reg,
		@RequestParam("mode_pay") long mode_paiement,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		convert_string_to_date_util ctd = new convert_string_to_date_util();
		
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		date_emission = ctd.convertion_InputDate_to_MyDate(date_emission);
		
		date_fin = ctd.convertion_InputDate_to_MyDate(date_fin);
		
		rc.setActivite(activite);
		rc.setAdresse(adresse);
		rc.setComune(comune);
		rc.setDate_emission(date_emission);
		rc.setDate_fin(date_fin);
		rc.setEtat_blockage(etat_blockage);
		rc.setNom(nom);
		rc.setNumero_art(num_art);
		rc.setNumero_nif(num_nif);
		rc.setNumero_rc(num_rc);
		rc.setPlafond(plafond);
		rc.setPrenom(prenom);
		rc.setCategory(cat_clientRepo.getOne(cat_client));
		rc.setType_reglement(type_rRepo.getOne(type_reg));
		rc.setBanque(banqueRepo.getOne(id_banque));
		rc.setMode_paiement(mpRepo.getOne(mode_paiement));
		
		double ttva = 0;
		
		if(tva.equals("on")) {
			ttva = 1;
		}
		
		rc.setTva(ttva);
		rc.setWilaya(wilayaRepo.getOne(id_wilaya));
		
		rcRepo.save(rc);rcRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		//*********-*/654654dqsfsdfsdfsdfsd
		registre_commerce_backup rcb = new registre_commerce_backup(rc, user);
		
		System.out.println("===== ID ==>"+rcb.getId()+" / id_rc ===>"+rcb.getId_rc());
		
		rcbRepo.save(rcb);rcbRepo.flush();
		
		trk.add_track("registre_commerce", "Modification RC", rc.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		return "redirect:/info_rc?id_rc="+id_rc+"&resp=edit_ok";
		
	}
	
	//--------------------------------------------- RC Client Controller
	
	
	@RequestMapping(value="/list_rc_clt")
	public String list_rc_client(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam("id_client") long id_clt,
						 @RequestParam("id_rc") long id_rc,
						 Model model){
		
		
		List <client_registreCommerce> list_clt_rc = new ArrayList<client_registreCommerce>();
		
		if(id_clt!=0 && id_rc!=0) {
			
			list_clt_rc = crcRepo.if_relation_existe(cltRepo.getOne(id_clt), rcRepo.getOne(id_rc));
			
		}
		else if(id_clt==0 && id_rc!=0) {
			
			list_clt_rc = crcRepo.client_by_rc_all(rcRepo.getOne(id_rc));
			
		}
		else if(id_clt!=0 && id_rc==0) {
			
			list_clt_rc = crcRepo.rc_by_client_all(cltRepo.getOne(id_clt));
			
		}
		else {
			
			list_clt_rc = crcRepo.findAll();
			
		}
		
		model.addAttribute("rc_client", list_clt_rc);
		
		return "client/list_rc_client";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_etat_clt")
	public String print_bl(HttpServletRequest request,
						 @RequestParam("etat") String etat,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String pdf = "";
		
		
		pdf = gd.generate_etat_client(etat);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//-----------------------------------------------------------------------------
	
}
