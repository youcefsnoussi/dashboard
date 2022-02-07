package com.commercial.webController.vente;

import java.io.IOException;
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

import com.commercial.entities.schema.article.repository.MagasinRepository;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.client.unite_interne;
import com.commercial.entities.schema.client.repository.unite_interneRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfertRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_detailRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.services.generate_Doc;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class bon_transfertController {
	
	@Autowired
	unite_interneRepository uiRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	bon_transfertRepository btRepo;
	
	@Autowired
	bon_transfert_detailRepository bt_dRepo;
	
	@Autowired
	MagasinRepository magasinRepo;
	
	@Autowired
	unite_mesureRepository umRepo;
	
	@Autowired
	track_operations trk;
	
	public bon_transfertController() {
		// TODO Auto-generated constructor stub
	}
	
	//--------------------------------------------------------------------
	
	@RequestMapping(value="/bon_transfert")
	public String bon_transfert(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "transfert/bon_transfert";
		
		model.addAttribute("unite_interne", uiRepo.findAll());
		
		model.addAttribute("articles", artRepo.findAll(Sort.by(Sort.Direction.ASC,"code")));
		
		return ret;
		
	}
	
	//__________________________________________POST____________________________________________________________________
	
	@RequestMapping(value="/new_bon_transfert",method=RequestMethod.POST)
	public String new_bon_transfert(HttpServletRequest req,
			@RequestParam("unite_interne") Long id_unite_interne,
			@RequestParam("matricule_vehicule") String matricule_vehicule,
			@RequestParam("chauffeur") String chauffeur,
			@RequestParam("matricule_chauffeur") String matricule_chauffeur,
			
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
		
		//System.out.println(unite_interne.getDesignation());
		
		unite_interne unite_interne = uiRepo.getOne(id_unite_interne);
		
		get_time_date gtd = new get_time_date();
		
		numerotation_by_year nby = new numerotation_by_year();
		
		bon_transfert last_ble = btRepo.findFirst1ByOrderByNumeroDesc();
		
		String last_number = "";
		
		if(last_ble!=null) {
			
			last_number = last_ble.getNumero();
			
		}
		
		String numero = nby.return_num_BonTransfert(last_number, user.getUnite().getId());
		
		bon_transfert bt = new bon_transfert(unite_interne, numero, matricule_vehicule, user, false, chauffeur, matricule_chauffeur, 
				gtd.get_date(), gtd.get_time());
		
		btRepo.save(bt); btRepo.flush();
		
		//-------------------- tracking operation -----------------------------------
		
		trk.add_track("bon_transfert", "Creation Bon de transfert", bt.getId(), user);
		
		//-------------------- tracking operation -----------------------------------
		
		for(int i=0;i<article.length;i++) {
			
			if(quantite[i]!=0) {
				
				bon_transfert_detail bt_d = new bon_transfert_detail(bt, artRepo.getOne(article[i]), quantite[i], 
						umRepo.getOne(id_unite_mesure[i]), magasinRepo.getOne(id_magasin[i]));
				
				bt_dRepo.save(bt_d);bt_dRepo.flush();
				
			}
			
		}
		
		return "redirect:/bon_transfert?id_bt="+bt.getId()+"&num_bt="+bt.getNumero()+"&type=bt";
		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/list_bon_transfert")
	public String list_bon_transfert (HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "transfert/list_bon_transfert";
		
		get_time_date gtd = new get_time_date();
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List<bon_transfert_detail> bts_d = new ArrayList<>();
		
		if(start.equals("0") && end.equals("0")) {
			
			bts_d = bt_dRepo.date_between_bt_detail(gtd.get_date(), gtd.get_date());
			
			date_d = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_f = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		}
		else {
			
			date_d = conv.convertion_InputDate_to_MyDate(start);
			date_f = conv.convertion_InputDate_to_MyDate(end);
			
			bts_d =  bt_dRepo.date_between_bt_detail(date_d, date_f);
			
			date_d = start; date_f = end;
			
		}
		
		model.addAttribute("list_btd", bts_d);
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		boolean cancel_bt = false;

		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") ||  user.getRole().getIds_banned().contains("cancel_bt")) 
		{ cancel_bt = true; }
		
		model.addAttribute("cancel_bt", cancel_bt);
		
		return ret;
		
	}
	
	//---------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_bt")
	public String print_bt(HttpServletRequest request,
						 @RequestParam("id_bt") long id_bt,
						 @SessionAttribute("user") users user,
						 Model model){
		
		bon_transfert bt = btRepo.getOne(id_bt);
		
		//String qr_code = generateQRcode.createQRcode(blf.getNumero(), "BL");
		
		String pdf = "";
		
		
		pdf = gd.generate_bt(bt);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//---------------------------------------------------------------
	
	@RequestMapping(value="/print_bts")
	public String print_bts(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model) throws IOException{
		
		List<bon_transfert> bts = btRepo.date_between_bt(start, end);
		
		//String qr_code = generateQRcode.createQRcode(blf.getNumero(), "BL");
		
		String pdf = "";
		
		
		pdf = gd.generate_bts(bts);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
}
