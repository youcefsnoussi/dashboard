package com.commercial.webController.statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.generate_Doc;
import com.commercial.functions.get_time_date;

@Controller
@SessionAttributes("user")

public class vente_statistiqueController {
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fact_dRepo;
	
	@Autowired
	facture_avoirRepository fact_avRepo;
	
	@Autowired
	facture_avoir_detailRepository fact_av_dRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	articleRepository artRepo;
	
	public vente_statistiqueController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/vente_client")
	public String vente_client_controller(HttpServletRequest request,
						 @RequestParam("id_rc") long id_rc,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		/*
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		List <Object[]> cumule_fact = fact_dRepo.get_quantite_sold_val_by_rc(start, end, rc);
		
		List <Object[]> cumule_fact_av = fact_av_dRepo.get_quantite_avoir_val_by_rc(start, end, rc);
		
		for(int i=0; i<cumule_fact.size(); i++ ) {
			
			Object[] obj_fact = cumule_fact.get(i);
			
			for(int j=0; j<cumule_fact_av.size(); j++) {
				
				Object[] obj_fact_av = cumule_fact_av.get(j);
				
				if( obj_fact[0].toString().equals( obj_fact_av[0].toString() )) {
					
					double quant = (double) obj_fact[2] - (double) obj_fact_av[2];
					double mnt_ht = (double) obj_fact[3] - (double) obj_fact_av[3];
					double mnt_ttc = (double) obj_fact[5] - (double) obj_fact_av[5];
					
					obj_fact[2] = quant;
					obj_fact[3]	= mnt_ht;	
					obj_fact[5] = mnt_ttc;
					
					cumule_fact.set(i, obj_fact);
				}
				
			}
			
		}
		
		List< Map<String,String> > ret = new ArrayList<Map<String,String>>();
		
		for(int i=0; i<cumule_fact.size(); i++ ) {
			
			Object[] obj_fact = cumule_fact.get(i);
			
			for(int j=0; j<obj_fact.length; j++) {
				
				System.out.println(obj_fact[j]);
				
			}
			
			System.out.println("________________________________");
			
		}
		
		model.addAttribute("info", cumule_fact);
		*/
		
		model.addAttribute("list_rc", rcRepo.findAll());
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		if(start.equals("0")) {
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			model.addAttribute("start", start);
			
			model.addAttribute("end", end);
			
		}
		
		return "statistic/vente_par_client";		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/vente_produit")
	public String vente_produit(HttpServletRequest request,
						 @RequestParam("id_art") long id_art,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		model.addAttribute("list_art", artRepo.findAll(Sort.by(Sort.Direction.ASC,"code")));
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		if(start.equals("0")) {
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			model.addAttribute("start", start);
			
			model.addAttribute("end", end);
			
		}
		
		
		return "statistic/vente_par_produit";		
	}
	
	
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/vente_produit_val")
	public String vente_produit_val(HttpServletRequest request,
						 @RequestParam("id_art") long id_art,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		model.addAttribute("list_art", artRepo.findAll(Sort.by(Sort.Direction.ASC,"code")));
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		if(start.equals("0")) {
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			model.addAttribute("start", start);
			
			model.addAttribute("end", end);
			
		}
		
		
		return "statistic/vente_par_produit_val";		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/vente_produit_client")
	public String vente_produit_client(HttpServletRequest request,
						 @RequestParam("id_rc") long id_rc,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		model.addAttribute("list_rc", rcRepo.findAll());
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		if(start.equals("0")) {
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			model.addAttribute("start", start);
			
			model.addAttribute("end", end);
			
		}
		
		return "statistic/vente_produit_client";		
	}
	
	//-----------------------------------------------------------------------------
	
	@Autowired
	generate_Doc gd;
	
	@RequestMapping(value="/print_vente_client")
	public String print_vente_client(HttpServletRequest request,
						 @RequestParam("id_rc") long id_rc,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_vente_client(start, end, rc);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//----__________----
	
	@RequestMapping(value="/print_vente_produit")
	public String print_vente_produit(HttpServletRequest request,
						 @RequestParam("id_art") long id_art,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		article art = artRepo.getOne(id_art);
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_vente_produit(start, end, art);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//------------------------------------
	
	@RequestMapping(value="/print_vente_produit_val")
	public String print_vente_produit_val(HttpServletRequest request,
						 @RequestParam("id_art") long id_art,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		article art = artRepo.getOne(id_art);
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_vente_produit_val(start, end, art);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//------------------------------------
	
	@RequestMapping(value="/print_vente_produit_global")
	public String print_vente_produit_global(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_vente_produit_global(start, end);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//------------------------------------
	
	@RequestMapping(value="/print_vente_produit_client")
	public String print_vente_produit_client(HttpServletRequest request,
						 @RequestParam("id_rc") long id_rc,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		registre_commerce rc = rcRepo.getOne(id_rc);
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_vente_produit_client(start, end, rc);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
}
