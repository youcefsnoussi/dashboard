package com.commercial.webController.client;

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
import com.commercial.entities.schema.backup_edit.repository.registre_commerce_backupRepository;
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.static_data.unite;
import com.commercial.entities.schema.static_data.wilaya;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.track_operations;

@Controller
@SessionAttributes("user")

public class add_rcController {
	
	@Autowired
	clientRepository clientRepo;
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	tva_Repository tvaRepo;
	
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
	
	public add_rcController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/add_rc")
	public String add_new_rc(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "client/create_rc";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("add_rc"))) 
		{ ret = "client/create_rc"; }
		else { ret = "403"; }
		
		//----------------------------------------------------------------	
		
		model.addAttribute("client", clientRepo.findAll());
		model.addAttribute("wilaya", wilayaRepo.findAll(Sort.by(Sort.Direction.ASC, "code")));
		model.addAttribute("cat_client", cat_clientRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("unite", uniteRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("type_reg", type_rRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("banque", banqueRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		model.addAttribute("mode_pay", mpRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		return ret;
		
	}
	
	@RequestMapping(value="/create_rc",method=RequestMethod.POST)
	public String inster_rc_DB(HttpServletRequest req,
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
		@RequestParam("cat_client") long cat_client,
		@RequestParam("banque") long id_banque,
		@RequestParam("type_reg") long type_reg,
		@RequestParam("unite") long unite,
		@RequestParam("mode_pay") long mode_paiement,
		
		@SessionAttribute("user") users user){
		
		convert_string_to_date_util ctd = new convert_string_to_date_util();
		
		System.out.println("tva==="+tva);
		
		registre_commerce rc = rcRepo.if_rc_exist_db(num_rc, num_nif, num_art, nom, prenom);
		
		String ret = "exist";
		
		date_emission = ctd.convertion_InputDate_to_MyDate(date_emission);
		
		date_fin = ctd.convertion_InputDate_to_MyDate(date_fin);
		
		float taux_tva = 0;
		
		if(rc==null) {
			
			if(tva.equals("on")) {
				
				//tva tt = tvaRepo.getOne((long) 1);
				
				taux_tva = 1; //===/=/=/=/=/=/=/=> taux tva represente if ndirlo tva or not
				
			}
			
			System.out.println("watch dog");
			
			wilaya wilaya = wilayaRepo.getOne(id_wilaya);
			
			unite un = uniteRepo.getOne(unite);
			
			category_client cat_rc = cat_clientRepo.getOne(cat_client);
			
			String code_rc = un.getId()+cat_rc.getLettre()+new_number_code_rc(cat_rc);
			
			rc = new registre_commerce(nom, prenom, code_rc, cat_rc, num_rc, num_art, num_nif, date_emission, date_fin, adresse, comune, 
					wilaya, "active", taux_tva, plafond, 0, activite, "active", banqueRepo.getOne(id_banque), type_rRepo.getOne(type_reg),
					mpRepo.getOne(mode_paiement), un);
			
			
			rcRepo.save(rc);rcRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("registre_commerce", "Ajout d'un RC", rc.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
			ret = "added";
			
		}
		
		return "redirect:/add_rc?ret="+ret;
		
	}
	
	//------------------------------------------------
	
	public String new_number_code_rc(category_client cat) {
		
		String ret= "00001";
		
		List<registre_commerce> list_c = rcRepo.last_rc_by_category(cat);
		
		registre_commerce c = list_c.get(list_c.size()-1);
		
		if(c!=null) {
			
			String code = c.getCode();
			
			String num = code.substring(2);
			
			long n = Long.parseLong(num);
			
			n = n+1;
			
			ret = ""+n;
			
			if(n<=9999) {
				ret = "0"+n;
			}
			if(n<=999) {
				ret = "00"+n;
			}
			if(n<=99) {
				ret = "000"+n;
			}
			if(n<=9) {
				ret = "0000"+n;
			}
			
		}
		
		return ret;
		
	}
	
}	
