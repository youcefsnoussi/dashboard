package com.commercial.webController.parametre;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.commercial.entities.schema.static_data.information_entreprise;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.information_entrepriseRepository;
import com.commercial.entities.schema.user_menu.users;

@Controller
@SessionAttributes("user")

public class entreprise_informationCotroller {
	
	@Autowired
	information_entrepriseRepository info_ent_repo;
	
	@Autowired
	banqueRepository banqueRepo;
	
	public entreprise_informationCotroller() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/info_ent")
	public String get_entreprise_info(Model model){
		
		information_entreprise info_e; 
		
		List <information_entreprise> list_info = info_ent_repo.findAll();
		
		model.addAttribute("banque", banqueRepo.findAll());
		
		long id_banque_encours = 0;
		
		if(!list_info.isEmpty()) {
			
			info_e = list_info.get(0);
			
			id_banque_encours = info_e.getBanque().getId();
			
		}
		else {
			
			info_e = new information_entreprise();
			
		}
		
		model.addAttribute("info_entreprise", info_e);
		
		model.addAttribute("id_banque", id_banque_encours);
		
		return "parametre/entreprise_information";
		
	}
	
	//-----------------------------------------------------------------------
	
	@RequestMapping(value="/entreprise_info",method=RequestMethod.POST, consumes = {"multipart/form-data"})
	public String insert_edit_entreprise_info(HttpServletRequest req,
			@RequestParam("nom_entreprise") String nom_entreprise,
			@RequestParam("capitale") double capitale,
			@RequestParam("adresse_facturation") String adresse_facturation,
			@RequestParam("commune") String commune,
			@RequestParam("wilaya") String wilaya,
			@RequestParam("code_postal") String code_postal,
			@RequestParam("telephone") String telephone,
			@RequestParam("fax") String fax,
			@RequestParam("email") String email,
			@RequestParam("banque") long id_banque,
			@RequestParam("activite") String activite,
			@RequestParam("num_rc") String num_rc,
			@RequestParam("num_nif") String num_nif,
			@RequestParam("num_nis") String num_nis,
			@RequestParam("num_art") String num_art,
			@Valid @RequestParam("logo") MultipartFile logo,
			
			@SessionAttribute("user") users user){
			
			//System.out.println(" ||==========> "+logo.getName()+" //==========> "+logo.getSize());
			
			information_entreprise info_e; 
			
			String logo_path = "D:/Commercial/parametre/" + logo.getOriginalFilename();
			
			try {

	            // Get the file and save it somewhere
	            byte[] bytes = logo.getBytes();
	            Path path = Paths.get("D:\\Commercial\\parametre\\" + logo.getOriginalFilename());
	            Files.write(path, bytes);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			List <information_entreprise> list_info = info_ent_repo.findAll();
			
			if(!list_info.isEmpty()) {
				
				info_e = list_info.get(0);
				
				info_e.setActivite_entreprise(activite);
				info_e.setAdresse_facturation(adresse_facturation);
				info_e.setBanque(banqueRepo.getOne(id_banque));
				info_e.setChemain_logo(logo_path);
				info_e.setCode_postal(code_postal);
				info_e.setCommune(commune);
				info_e.setEmail(email);
				info_e.setFax(fax);
				info_e.setNom_entreprise(nom_entreprise);
				info_e.setNum_art(num_art);
				info_e.setNum_nif(num_nif);
				info_e.setNum_nis(num_nis);
				info_e.setNum_rc(num_rc);
				info_e.setTelephone(telephone);
				info_e.setWilaya(wilaya);
				info_e.setCapitale(capitale);
				
				info_ent_repo.save(info_e); info_ent_repo.flush();
				
			}
			else {
				
				info_e = new information_entreprise(nom_entreprise, adresse_facturation, activite, 
						commune, wilaya, code_postal, telephone, fax, email, num_rc, num_nif, num_art, num_nis, capitale, banqueRepo.getOne(id_banque), logo_path);
				
				info_ent_repo.save(info_e); info_ent_repo.flush();
				
			}
			
			return "redirect:/info_ent";
		
	}
	
}
