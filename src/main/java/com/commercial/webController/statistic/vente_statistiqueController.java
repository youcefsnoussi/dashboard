package com.commercial.webController.statistic;


import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.services.generate_Doc;

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
	
	@Autowired
	category_produitRepository cat_prodRepo;
	
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
			
		}
		
		model.addAttribute("list", list);
		
		return "statistic/declaration_tva";		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/etat_vente_client")
	public String etat_vente_client(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @RequestParam(value="id_category", defaultValue="0") Long id_category,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		model.addAttribute("start", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
		
		model.addAttribute("end", conv.convertion_MyDate_to_InputDate(gtd.get_date()));
		
		model.addAttribute("cat_prod", cat_prodRepo.findAll());
		
		return "statistic/etat_vente_par_client";		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/etat_sortie_article")
	public String etat_sortie_article(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		LocalTime start_time = LocalTime.now();
		
		System.out.println("start ------->"+start_time);
		
		List <Object[]> rows = new ArrayList<Object[]>();
		
		//List<article> lst_art = new ArrayList<article>();
		
		if(start.equals("0")) {
			
			start = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			end = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		
		final List<article> lst_art = fact_dRepo.get_all_articles_ordered_by_libelle(start, end);
		
		List<facture> facts = factRepo.get_code_rc_num_date(start, end);
		
		System.out.println("getting facts ------->"+LocalTime.now()+" | diff ---> "+ChronoUnit.MINUTES.between(start_time, LocalTime.now())+":"+
				ChronoUnit.SECONDS.between(start_time, LocalTime.now()));
		
		facts.parallelStream().forEach(fct -> {
			
			Object [] obj = new Object [lst_art.size()+3];
			
			obj [0] = fct.getRegistre_commerce().getCode();
			obj [1] = fct.getRegistre_commerce().getNom()+" "+fct.getRegistre_commerce().getPrenom();
			obj [2] = fct.getNumero();
			
			for (int i = 0; i<lst_art.size(); i++) {
				
				Object quant = 0;
				
				quant = fact_dRepo.get_quantite_by_article_facture(fct, lst_art.get(i));
				
				obj[i+3] = (quant!=null) ? quant : 0;
				
			}
			
			rows.add(obj);
			
		});
		
		/*
		for (facture fct : facts) {
			
			Object [] obj = new Object [lst_art.size()+3];
			
			obj [0] = fct.getRegistre_commerce().getCode();
			obj [1] = fct.getRegistre_commerce().getNom()+" "+fct.getRegistre_commerce().getPrenom();
			obj [2] = fct.getNumero();

			for (int i = 0; i<lst_art.size(); i++) {
				
				Object quant = 0;
				
				quant = fact_dRepo.get_quantite_by_article_facture(fct, lst_art.get(i));
				
				//System.out.println("-->"+quant);
				
				if(quant!=null) {
					
					obj[i+3] = quant;
					
				}
				else {
					
					obj[i+3] = 0;
					
				}
				
			}
			
			rows.add(obj);
			
		}*/
		
		model.addAttribute("articles", lst_art);
		model.addAttribute("rows", rows);
		model.addAttribute("start",start);
		model.addAttribute("end",end);
		model.addAttribute("unite",user.getUnite().getNom_unite());
		
		LocalTime end_time = LocalTime.now();
		
		System.out.println("end ------->"+end_time+" | diff ---> "+ChronoUnit.MINUTES.between(start_time, end_time)+":"+
					ChronoUnit.SECONDS.between(start_time, end_time));
		
		return "statistic/etat_sortie_article";		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/etat_sortie_category")
	public String etat_sortie_categorie(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		List <Object[]> rows = new ArrayList<Object[]>();
		
		if(start.equals("0")) {
			
			start = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			end = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		
		final List<category_produit> lst_art = fact_dRepo.get_all_category_articles_ordered_by_libelle(start, end);
		
		List<facture> facts = factRepo.get_code_rc_num_date(start, end);
		
		facts.parallelStream().forEach(fct -> {
			
			Object [] obj = new Object [lst_art.size()+3];
			
			obj [0] = fct.getRegistre_commerce().getCode();
			obj [1] = fct.getRegistre_commerce().getNom()+" "+fct.getRegistre_commerce().getPrenom();
			obj [2] = fct.getNumero();
			
			//System.out.println("->"+fct.getNumero());
			
			for (int i = 0; i<lst_art.size(); i++) {
				
				Object quant = 0;
				
				quant = fact_dRepo.get_quantite_by_category_article_facture(fct, lst_art.get(i));
				
				obj[i+3] = (quant!=null) ? quant : 0;
				
			}
			
			rows.add(obj);
			
		});
		
		model.addAttribute("articles", lst_art);
		model.addAttribute("rows", rows);
		model.addAttribute("start",start);
		model.addAttribute("end",end);
		model.addAttribute("unite",user.getUnite().getNom_unite());
		
		
		
		return "statistic/etat_sortie_category_article";		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/etat_sortie_subvension")
	public String etat_sortie_subvnsion(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		if(start.equals("0")) {
			
			start = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			end = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
		}
		
		model.addAttribute("start",start);
		model.addAttribute("end",end);
		
		return "statistic/etat_sortie_subvension";		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/etat_104")
	public String etat_104(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @RequestParam(value="id_category", defaultValue="0") List<Long> id_category,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		start = (start.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : start;
		
		end = (end.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : end;
		
		List<category_produit> lst_cat = (id_category.size()==1 && id_category.get(0)==0) ? cat_prodRepo.findAll() 
											: cat_prodRepo.findByIdIn(id_category);
		
		List <Object[]> list = fact_dRepo.Etat104(start, end, lst_cat);
		
		model.addAttribute("start", start);
		
		model.addAttribute("end", end);
		
		model.addAttribute("id_cat_prod", id_category);
		
		model.addAttribute("cat_prod", cat_prodRepo.findAll());
		
		model.addAttribute("data", list);
		
		model.addAttribute("tout", (id_category.contains( (long)0 )) ? true : false );
		
		return "statistic/Etat104";		
	}
	
	//-----------------------------------------------------------------------------
	
	@RequestMapping(value="/rapport_wilaya")
	public String rapport_wilaya(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @RequestParam(value="id_category", defaultValue="0") Long id_category,
						 //@RequestParam(value="sub", defaultValue="false") boolean sub,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		String ret_start = (start.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : start;
		
		String ret_end = (end.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : end;
		
		if(id_category!=0) {
			
			category_produit cat_p = cat_prodRepo.getOne(id_category);

			list = cat_prodRepo.get_quantite_vendu_by_wilaya_fact(ret_start, ret_end, cat_p.getId()) ;	
					
		}
		
		model.addAttribute("categories", cat_prodRepo.findAll(Sort.by(Sort.Direction.ASC,"id")));
		
		model.addAttribute("selected_category", id_category);
		
		model.addAttribute("start", ret_start);
		
		model.addAttribute("end", ret_end);
		
		//model.addAttribute("sub", sub);
		
		model.addAttribute("list", list);
		
		return "statistic/rapport_wilaya";		
	}
	
	//------------------------------------------------------------------------------
	
	@RequestMapping(value="/rapport_wilaya_detail")
	public String rapport_wilaya_detail(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @RequestParam(value="id_category", defaultValue="0") Long id_category,
						 @SessionAttribute("user") users user,
						 Model model){
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		String ret_start = (start.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : start;
		
		String ret_end = (end.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : end;
		
		if(id_category!=0) {
			
			category_produit cat_p = cat_prodRepo.getOne(id_category);

			list = cat_prodRepo.get_quantite_vendu_by_wilaya_fact_detail(ret_start, ret_end, cat_p.getId()) ;	
					
		}
		
		model.addAttribute("categories", cat_prodRepo.findAll(Sort.by(Sort.Direction.ASC,"id")));
		
		model.addAttribute("selected_category", id_category);
		
		model.addAttribute("start", ret_start);
		
		model.addAttribute("end", ret_end);
		
		model.addAttribute("list", list);
		
		return "statistic/rapport_wilaya_detail";		
	}
	
	//------------------------------------------------------------------------------
	
	@RequestMapping(value="/rapport_ultra_detailler")
	public String rapport_ultra_detailler(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		/*
		LocalTime start_time = LocalTime.now();
		
		System.out.println("start ------->"+start_time);
		*/
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		String ret_start = (start.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : start;
		
		String ret_end = (end.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : end;
		
		list = fact_dRepo.RapoortUltraDetailler(ret_start, ret_end) ;	
		
		model.addAttribute("start", ret_start);
		
		model.addAttribute("end", ret_end);
		
		model.addAttribute("list", list);
		/*
		LocalTime end_time = LocalTime.now();
		
		System.out.println("end ------->"+end_time+" | diff ---> "+ChronoUnit.MINUTES.between(start_time, end_time)+":"+
					ChronoUnit.SECONDS.between(start_time, end_time));
		*/
		return "statistic/rapport_ultra_detailler";		
	}
	
	//------------------------------------------------------------------------------
	
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
	
	//----------------------------------------------------------------------------
	
	@RequestMapping(value="/print_etat_vente_client")
	public String print_etat_vente_client(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @RequestParam("id_category") long id_cat_prod,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String pdf = "";
		
		//String qr_code = generateQRcode.createQRcode(fact.getNumero(), "FCT");
		/*
		category_produit cat_prod = cat_prodRepo.getOne(id_cat_prod);
		
		
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		start  = conv.convertion_InputDate_to_MyDate(start);
		
		end  = conv.convertion_InputDate_to_MyDate(end);
		
		pdf = gd.generate_etat_vente_client(start, end, cat_prod, user.getUnite().getNom_unite());
		*/
		
		pdf = gd.generate_etat_vente_client(start, end, cat_prodRepo.getOne(id_cat_prod), user.getUnite().getNom_unite());
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//----------------------------------------------------------------------------
	
	@RequestMapping(value="/print_quant_sub")
	public String print_print_quant_sub(HttpServletRequest request,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String pdf = gd.generate_quantite_sub_vendu(start, end);
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//----------------------------------------------------------------------------
	
	@RequestMapping(value="/print_etat_104")
	public String print_etat_104(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @RequestParam(value="id_category", defaultValue="0") long id_cat_prod,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String pdf = "";
		
		category_produit cat_p = (id_cat_prod==0) ? new category_produit((long) 0,"Tout") : cat_prodRepo.getOne(id_cat_prod);
		
		pdf = gd.generate_etat_104(start, end, cat_p, user.getUnite().getNom_unite());
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//----------------------------------------------------------------------------
}
