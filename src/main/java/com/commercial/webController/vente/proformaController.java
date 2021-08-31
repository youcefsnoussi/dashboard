package com.commercial.webController.vente;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.proforma;
import com.commercial.entities.schema.profoma_cmd_bl_fact.proforma_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.proformaRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.proforma_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.generateQRcode;
import com.commercial.functions.get_time_date;
import com.commercial.functions.numerotation_by_year;
import com.commercial.services.generate_Doc;
import com.commercial.wrapperObjects.ProformaDetailsDto;

@Controller
@SessionAttributes("user")

public class proformaController {
	
	@Autowired
	proformaRepository prfmRepo;
	
	@Autowired
	proforma_detailRepository prfmDRepo;
	
	@Autowired
	client_registreCommerceRepository clt_rcRepo;
	
	@Autowired
	articleRepository artRepo;
	
	public proformaController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/proforma")
	public String proforma( 
							@SessionAttribute("user") users user,
							Model model){
		
		get_time_date gtd = new get_time_date();
		
		String ret = "vente/proforma";
		
		model.addAttribute("proforma", new proforma());
		
		ProformaDetailsDto details = new ProformaDetailsDto();
		
		for (int i = 0; i < 10 ;i++) {
			
			details.addDetails(new proforma_detail());
		}
		
		model.addAttribute("ProformaDetailsDto", details);
		
		model.addAttribute("clients", clt_rcRepo.ListRCwithCLIENT_active(gtd.get_date()));
		
		model.addAttribute("articles", artRepo.select_articles_ordered());
		
		return ret;
		
	}
	
	@PostMapping("/new_proforma")
	public String newProforma(
								@SessionAttribute("user") users user,
								@Valid proforma proforma,
								@Valid ProformaDetailsDto details) {
		
		proforma prf = prfmRepo.findFirst1ByOrderByNumeroDesc();
		
		numerotation_by_year num = new numerotation_by_year();
		
		String numero = (prf==null) ? "" : prf.getNumero();
		
		get_time_date gtd = new get_time_date();
		
		System.out.println("Montant TVA-->"+proforma.getTotal_tva());
		
		proforma.setUsers(user);
		proforma.setDate(gtd.get_date());
		proforma.setTime(gtd.get_time());
		proforma.setNumero(num.return_num_proforma(numero, user.getUnite().getId()));
		
		prfmRepo.save(proforma);prfmRepo.flush();
		
		for (proforma_detail item : details.getProformaDetails()) {
			
			if(item.getArticle()!=null && item.getQuantite() > 0 && item.getPrix_u_ht() > 0) {
				
				item.setProformat(proforma);
				
				prfmDRepo.save(item);prfmDRepo.flush();
				
			}
			
		}
		
		return "redirect:/proforma?id_prof="+proforma.getId()+"&num_prof="+proforma.getNumero();
		
	}	
	
	@RequestMapping(value="/list_proforma")
	public String list_proforma(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 @RequestParam(value="date_debut", defaultValue="0") String date_debut,
						 @RequestParam(value="date_fin", defaultValue="0") String date_fin,
						 Model model){
		
		String ret = "vente/list_proforma";
		
		get_time_date gtd = new get_time_date();
		
		String date_d = "", date_f = "";
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		if(date_debut.equals("0") && date_fin.equals("0")) {
			
			model.addAttribute("list_proforma", prfmRepo.get_proforma_dates(gtd.get_date(), gtd.get_date()));
			
			date_d = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			date_f = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		}
		else {
					
			model.addAttribute("list_proforma", prfmRepo.get_proforma_dates(date_debut, date_fin));
			
			date_d = date_debut;
			
			date_f = date_fin;
				
		}
		
		model.addAttribute("date_d", date_d);
		
		model.addAttribute("date_f", date_f);
		
		return ret;
		
	}
	
	//--------------------------------------------------------------------------------
	
	//------------------------------------ PRINT 
	
	@Autowired
	generate_Doc gd;
	
	@GetMapping("/print_proforma/{id}")
	public String print_bl(HttpServletRequest request,
						 @PathVariable("id") long id_proforma,
						 @SessionAttribute("user") users user,
						 Model model){
		
		proforma proforma = prfmRepo.getOne(id_proforma);
		
		String qr_code = generateQRcode.createQRcode(proforma.getNumero(), "BL");
		
		String pdf = "";
		
		pdf = gd.generate_Proforma(proforma, qr_code);
			
		
		return "redirect:/display_pdf?file="+pdf;
		
	}
	
	//-----------------------------------------------------------------------------
	
}
