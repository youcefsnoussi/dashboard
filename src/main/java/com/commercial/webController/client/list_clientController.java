package com.commercial.webController.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;

@Controller
@SessionAttributes("user")

public class list_clientController {
	
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
	client_registreCommerceRepository crcRepo;
	
	public list_clientController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/list_client")
	public String client(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String id_rc = "";
		
		String role_add_client = "add_client";
		
		id_rc = request.getParameter("id_rc");
		
		if(Integer.parseInt(id_rc)==0) {
			
			model.addAttribute("clients_info", clientRepo.findAll());
			
		}
		else {
			
			registre_commerce rc = rcRepo.getOne(Long.parseLong(id_rc));
			
			model.addAttribute("clients_info", crcRepo.client_by_rc(rc));
			
			role_add_client = "no_add_client";
			
		}
		
		String s = user.getRole().getIds_banned();
		
		if(s!=null && s.contains("add_client")) {
			
			//role_add_client = "no_add_client";
			
		}
		else {
			
			role_add_client = "no_add_client";
			
		}
		
		model.addAttribute("add_client", role_add_client);
		
		return "client/list_client";
		
	}
	
	@RequestMapping(value="/info_client")
	public String view_client(HttpServletRequest request,
						 @RequestParam("id_c") Long id_c,
						 @SessionAttribute("user") users user,
						 Model model){
		
		client c = clientRepo.getOne(id_c);
		
		String s = user.getRole().getIds_banned();
		
		String role_edit = "edit";
		
		if(s!=null && s.contains("edit_client")) {
			
			role_edit="edit"; //---------- ibedel f client
			
		}
		else {
			
			role_edit="no_edit"; //----------- ichof bark client
			
		}
		
		model.addAttribute("edit_option", role_edit);
		
		model.addAttribute("client", c);
		
		model.addAttribute("type_reg", type_rRepo.findAll());
		
		model.addAttribute("banque", banqueRepo.findAll());
		
		model.addAttribute("cat_client", cat_clientRepo.findAll());
		
		return "client/info_client";
		
	}
	
	
	@RequestMapping(value="/edit_client",method=RequestMethod.POST, consumes = {"multipart/form-data"})
	public String edit_client_DB(HttpServletRequest req,
		@RequestParam("id_c") Long id_c,
		@RequestParam("nom") String nom,
		@RequestParam("prenom") String prenom,
		@RequestParam("adresse") String adresse,
		@RequestParam("etat_blockage") boolean etat_blockage,
		@RequestParam("wilaya") String wilaya,
		@RequestParam("code_postal") String code_postal,
		@RequestParam("telephone") String telephone,
		@RequestParam("fax") String fax,
		@RequestParam("email") String email,
		@RequestParam("cat_client") long cat_client,
		@RequestParam("banque") long id_banque,
		@RequestParam("type_reg") long type_reg,
		@RequestParam("plafond") double plafond,
		@Valid @RequestParam("img_client") MultipartFile img_client,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		get_time_date gtd = new get_time_date();
		
		double sold = 0;
		
		client clt = clientRepo.getOne(id_c);
		
		String path_img_client = "";
		
		
		
		if(!img_client.isEmpty() /*&& clt.getImg()!="D:/Commercial/Client/"+clt.getId()+"/"+ img_client.getOriginalFilename()*/) {
		
				path_img_client = "D:/Commercial/Client/"+clt.getId()+"/"+ img_client.getOriginalFilename();
				
				try {
					
					String pp="D:\\Commercial\\Client\\"+clt.getId();    
					File file = new File(pp);
					
					if (!file.exists()) {
			            
			            file.mkdir();
			            
			            byte[] bytes = img_client.getBytes();
			            Path path = Paths.get("D:\\Commercial\\Client\\"+clt.getId()+"\\"+img_client.getOriginalFilename());
			            
			            Files.write(path, bytes);
			            
			        }
					else {
						
						byte[] bytes = img_client.getBytes();
			            Path path = Paths.get("D:\\Commercial\\Client\\"+clt.getId()+"\\"+img_client.getOriginalFilename());
			            
			            Files.write(path, bytes);
						
					}
		            // Get the file and save it somewhere
		            
	
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				clt.setImg(path_img_client);
				
			}
			
			clt.setAdresse(adresse);
			clt.setBanque(banqueRepo.getOne(id_banque));
			clt.setCategory(cat_clientRepo.getOne(cat_client));
			clt.setCode("");
			clt.setCode_postal(code_postal);
			clt.setEmail(email);
			clt.setEtat_blockage(etat_blockage); 
			clt.setFax(fax);
			//clt.setImg(img);
			clt.setNom(nom);
			clt.setPlafond(plafond);
			clt.setPrenom(prenom);
			//clt.setRemise(remise);
			clt.setTelephone(telephone);
			clt.setType_reglement(type_rRepo.getOne(type_reg));
			clt.setWilaya(wilaya);
			
			clientRepo.save(clt);clientRepo.flush();
			
			
		
		
		return "redirect:/info_client?id_c="+id_c;
		
	}
	
	
}
