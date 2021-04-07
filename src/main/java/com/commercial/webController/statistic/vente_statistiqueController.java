package com.commercial.webController.statistic;


import java.util.ArrayList;
import java.util.HashMap;
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
		
		model.addAttribute("list_rc", rcRepo.findAll());
		
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
			
			list.addAll(fact_av_dRepo.get_quantite_sold_val_fact_av_by_rc(conv.convertion_InputDate_to_MyDate(start), 
																conv.convertion_InputDate_to_MyDate(end), rc) );
			
			model.addAttribute("list", list);
			
			model.addAttribute("id_rc", id_rc);
			
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
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		article art = artRepo.getOne(id_art);
		
		if(id_art==0) {
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("list", list);
			
			model.addAttribute("id_art", id_art);
			
		}
		else {
			
			model.addAttribute("start", start);
			
			model.addAttribute("end", end);
			
			list = fact_dRepo.get_details_sold_val_fact_by_art(start, end, art);

			list.addAll(fact_av_dRepo.get_details_sold_val_fact_av_by_art(start, end, art) );
			
			model.addAttribute("list", list);
			
			model.addAttribute("id_art", id_art);
			
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
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		article art = artRepo.getOne(id_art);
		
		if(start.equals("0")) {
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("list", list);
			
			model.addAttribute("id_art", id_art);
			
		}
		else {
			
			model.addAttribute("start", start);
			
			model.addAttribute("end", end);
			
			list = fact_dRepo.get_sum_vente_produit_by_clt(start, end, art);

			list.addAll(fact_av_dRepo.get_sum_vente_produit_by_clt(start, end, art) );
			
			model.addAttribute("list", list);
			
			model.addAttribute("id_art", id_art);
			
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
		
		List < Map<String,List<Object[]>> > list = new ArrayList< Map<String,List<Object[]>> >();
		
		if(start.equals("0")) {
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("id_rc", id_rc);
			
			model.addAttribute("list", list);
			
		}
		else {
			
			model.addAttribute("start", start);
			
			model.addAttribute("end", end);
			
			model.addAttribute("id_rc", id_rc);
			
			registre_commerce rc = rcRepo.getOne(id_rc);
			
			List<article> lst_art = fact_dRepo.get_articles(start, end, rc);
			
			List <String> arts = new ArrayList<String>();
			
			for(int i=0; i<lst_art.size(); i++) {
				
				article art = lst_art.get(i);
				
				List<Object[]> det = fact_dRepo.get_detail_fact_rc_art(start, end, rc, art);
				
				Map<String,List<Object[]>> mp = new HashMap<String, List<Object[]>>();
				
				mp.put(art.getCode()+" - "+art.getLibelle(), det);
				
				arts.add(art.getCode()+" - "+art.getLibelle());
				
				list.add(mp);
				
			}
			
			model.addAttribute("list", list);
			
			model.addAttribute("arts", arts);
			
		}
		
		return "statistic/vente_produit_client";		
	}
	

	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/declaration_tva")
	public String declaration_tva(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		if(start.equals("0") ) {
			
			model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
			model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
			
		}
		else {
			
			model.addAttribute("start", start);
			
			model.addAttribute("end", end);
			
			list = fact_dRepo.get_sum_declaration_tva(start, end);
			
			//System.out.println("---------->"+fact_av_dRepo.get_sum_declaration_tva(start, end).get(0)[0]);
			
			List <Object[]> list_av = fact_av_dRepo.get_sum_declaration_tva(start, end);
			
			//if(list_av.size()!=0) {
			
				for(int i=0; i<list_av.size();i++) {
					
					if(list_av.get(i)[0].equals(list.get(i)[0])) {
						
						double new_val_ht = ( (double) list.get(i)[1] ) - ( (double) fact_av_dRepo.get_sum_declaration_tva(start, end).get(i)[1] );
						double new_val_tva = ( (double) list.get(i)[2] ) - ( (double) fact_av_dRepo.get_sum_declaration_tva(start, end).get(i)[2] );
						
						list.get(i)[1] = new_val_ht;
						list.get(i)[2] = new_val_tva;
						
					}
					
				}
			
			//}
			
			model.addAttribute("list", list);
			
		}
		
		return "statistic/declaration_tva";		
	}
	
	
	//___________________________________________/°=-PRINT FUNCTIONS-=°\_______________________________
	
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
	
	//---------------------------------------------------------------
	
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
	
	//-------------------------------------------------------------------------
	
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
	
	//----------------------------------------------------------------------------------
	
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
	
	//----------------------------------------------------------------------------
	
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
	
	//----------------------------------------------------------------------------
	
	@RequestMapping(value="/print_declaration_tva")
	public String print_declaration_tva(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		
		String pdf = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_declaration_tva(start, end);
			
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
}
