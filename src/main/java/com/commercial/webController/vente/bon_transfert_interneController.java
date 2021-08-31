package com.commercial.webController.vente;

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

import com.commercial.entities.schema.article.repository.MagasinRepository;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.client.repository.unite_interneRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_interne;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_interne_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_interneRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_interne_detailRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.generateQRcode;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.services.generate_Doc;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class bon_transfert_interneController {
	
	@Autowired
	unite_interneRepository uiRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	bon_transfert_interneRepository btiRepo;
	
	@Autowired
	bon_transfert_interne_detailRepository bti_dRepo;
	
	@Autowired
	MagasinRepository magasinRepo;
	
	@Autowired
	unite_mesureRepository umRepo;
	
	@Autowired
	track_operations trk;
	
	public bon_transfert_interneController() {
		// TODO Auto-generated constructor stub
	}
	
	//--------------------------------------------------------------------
	
	@RequestMapping(value="/bon_transfert_interne")
	public String bon_transfert_interne(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "transfert/bon_transfert_interne";
		
		model.addAttribute("articles", artRepo.findAll(Sort.by(Sort.Direction.ASC,"code")));
		
		return ret;
		
	}
	
	//__________________________________________POST____________________________________________________________________
	
	@RequestMapping(value="/new_bon_transfert_interne",method=RequestMethod.POST)
	public String new_bon_transfert_interne(HttpServletRequest req,
			@RequestParam("destination") String destination,
			
			@RequestParam(name = "art", defaultValue = "0") long [] article,
			@RequestParam(name = "id_um", defaultValue = "0") long [] id_unite_mesure,
			@RequestParam(name = "id_magasin", defaultValue = "0") long [] id_magasin,
			@RequestParam(name = "montant_ht_art", defaultValue = "0") double [] montant_ht_art,
			//@RequestParam("montant_ttc_art") double [] montant_ttc_art,
			@RequestParam(name = "tva_art", defaultValue = "0") double [] tva_art,
			@RequestParam(name = "prix_unitaire", defaultValue = "0") double [] prix_u_ht,
			@RequestParam(name = "qte", defaultValue = "0") double [] quantite,
			@RequestParam(name = "tva_art", defaultValue = "0") double [] montant_tva_art,
			
			@SessionAttribute("user") users user){
		
		
		get_time_date gtd = new get_time_date();
		
		numerotation_by_year nby = new numerotation_by_year();
		
		bon_transfert_interne last_ble = btiRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_ble!=null) {
			
			last_number = last_ble.getNumero();
			
		}
		
		String numero = nby.return_num_BonTransfertInterne(last_number, user.getUnite().getId());
		
		bon_transfert_interne bti = new bon_transfert_interne(numero, user, false, gtd.get_date(), gtd.get_time(), destination);
		
		btiRepo.save(bti); btiRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("bon_transfert_interne", "Creation Bon de transfert Interne déstiné a "+destination, bti.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		for(int i=0;i<article.length;i++) {
			
			if(quantite[i]!=0) {
				
				bon_transfert_interne_detail bt_d = new bon_transfert_interne_detail(bti, artRepo.getOne(article[i]), quantite[i], umRepo.getOne(id_unite_mesure[i]), 
						magasinRepo.getOne(id_magasin[i]));
				
				bti_dRepo.save(bt_d);bti_dRepo.flush();
				
			}
			
		}
		
		return "redirect:/bon_transfert_interne?id_bl="+bti.getId()+"&num_bl="+bti.getNumero()+"&type=bti";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_bti")
	public String print_ble(HttpServletRequest request,
						 @RequestParam("id_bl") long id_bl,
						 @SessionAttribute("user") users user,
						 Model model){
		
		bon_transfert_interne bti = btiRepo.getOne(id_bl);
		
		String qr_code = generateQRcode.createQRcode(bti.getNumero(), "BL");
		
		String pdf = "";
		
		
		pdf = gd.generate_BonTransfertInterne(bti.getId(), qr_code);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//-----------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/list_bon_transfert_interne")
	public String list_bon_transfert_interne (HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "transfert/list_bon_transfert_interne";
		
		get_time_date gtd = new get_time_date();
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		if(start.equals("0") && end.equals("0")) {
			
			model.addAttribute("list_bti", btiRepo.date_between_bti(gtd.get_date(), gtd.get_date()));
			
			date_d = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_f = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		}
		else {
			
			date_d = conv.convertion_InputDate_to_MyDate(start);
			date_f = conv.convertion_InputDate_to_MyDate(end);
			
			model.addAttribute("list_bti", btiRepo.date_between_bti(date_d, date_f));
			
			date_d = start; date_f = end;
			
		}
		//model.addAttribute("bls", bon_lRepo.get_bl_encours());
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		return ret;
		
	}

}
