package com.commercial.webController.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.commercial.entities.schema.article.repository.wilayaRepository;
import com.commercial.entities.schema.backup_edit.repository.client_backupRepository;
import com.commercial.entities.schema.client.*;

import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.static_data.unite;
import com.commercial.entities.schema.static_data.wilaya;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;
import com.commercial.services.track_operations;

@Controller
@SessionAttributes("user")

public class add_clientController {
	
	@Autowired
	banqueRepository banqueRepo;
	
	@Autowired
	type_reglementRepository type_rRepo;
	
	@Autowired
	category_clientRepository cat_clientRepo;
	
	@Autowired
	clientRepository clientRepo;
	
	@Autowired
	uniteRepository uniteRepo;
	
	@Autowired
	client_backupRepository clt_bRepo;
	
	@Autowired
	track_operations trk;
	
	@Autowired
	wilayaRepository wilayaRepo;
	
	public add_clientController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/add_client")
	public String add_new_client(HttpServletRequest request,
						 @SessionAttribute("user") users user,
						 Model model){
		
		String ret = "";
		
		//----------------------ROLE TEST---------------------------------
		
		if(user.getRole().getNom_role().equals("Admin") || 
				(!user.getRole().getNom_role().equals("Admin") && user.getRole().getIds_banned().contains("add_client"))) 
		{ ret = "client/create_client"; }
		else { ret = "403"; }
		
		//----------------------------------------------------------------	
		
		//model.addAttribute("type_reg", type_rRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		//model.addAttribute("banque", banqueRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("cat_client", cat_clientRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("unite", uniteRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		model.addAttribute("wilaya", wilayaRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
		
		String s = user.getRole().getIds_banned();
		
		//---------------------- access control --------------
		/*
		if(s!=null && s.contains("add_client")) {
			
			//ret = "403";
			
		}
		else {
			
			ret = "403";
			
		}
		*/
		if(s!=null && s.contains("add_client_admin")) {
			
			model.addAttribute("role", "");
			
		}
		else {
			
			model.addAttribute("role", "add_client");
			
		}
		
		//---------------------- access control --------------
		
		return ret;
		
	}
	
	
	@RequestMapping(value="/add_client",method=RequestMethod.POST, consumes = {"multipart/form-data"})
	public String inster_client_DB(HttpServletRequest req,
		@RequestParam("nom") String nom,
		@RequestParam("prenom") String prenom,
		@RequestParam("adresse") String adresse,
		@RequestParam("unite") long unite,
		@RequestParam("wilaya") long id_wilaya,
		//@RequestParam("code_postal") String code_postal,
		//@RequestParam("telephone") String telephone,
		//@RequestParam("fax") String fax,
		//@RequestParam("email") String email,
		@RequestParam("cat_client") long cat_client,
		//@RequestParam("banque") long id_banque,
		//@RequestParam("type_reg") long type_reg,
		@RequestParam("plafond") double plafond,
		@Valid @RequestParam("img_client") MultipartFile img_client,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		get_time_date gtd = new get_time_date();
		
		double sold = 0;
		
		unite un = uniteRepo.getOne(unite);
		
		category_client cat_clt = cat_clientRepo.getOne(cat_client);
		
		//String code_client = un.getId()+cat_clt.getLettre()+new_number_code_client();
		
		wilaya wilaya = wilayaRepo.getOne(id_wilaya);
		
		client clt = new client(nom, prenom, adresse, wilaya, /*code_postal, email, telephone, fax, "",*/ gtd.get_date(), cat_clt, 
				/*banqueRepo.getOne(id_banque), type_rRepo.getOne(type_reg),*/ un, sold, plafond, false, false, "");
		
		clientRepo.save(clt);clientRepo.flush();
		
		String path_img_client = "";
		
		if(!img_client.isEmpty()) {
		
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
			
			clientRepo.save(clt);clientRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("client", "Ajout d'un client", clt.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
		}
		
		return "redirect:/add_client";
		
	}
	
	@RequestMapping(value="/add_client_basic",method=RequestMethod.POST, consumes = {"multipart/form-data"})
	public String inster_client_basic_DB(HttpServletRequest req,
		@RequestParam("nom") String nom,
		@RequestParam("prenom") String prenom,
		@RequestParam("adresse") String adresse,
		@RequestParam("unite") long unite,
		@RequestParam("wilaya") long id_wilaya,
		//@RequestParam("code_postal") String code_postal,
		//@RequestParam("telephone") String telephone,
		//@RequestParam("fax") String fax,
		//@RequestParam("email") String email,
		@RequestParam("cat_client") long cat_client,
		//@RequestParam("banque") long id_banque,
		//@RequestParam("type_reg") long type_reg,
		//@RequestParam("plafond") double plafond,
		@Valid @RequestParam("img_client") MultipartFile img_client,
		
		@SessionAttribute("user") users user){
		
		//------------ mazal khedma ta3 code client kifeh ngenerih --------------------//
		
		get_time_date gtd = new get_time_date();
		
		double sold = 0;
		
		unite un = uniteRepo.getOne(unite);
		
		category_client cat_clt = cat_clientRepo.getOne(cat_client);
		
		//String code_client = un.getId()+cat_clt.getLettre()+new_number_code_client();
		
		wilaya wilaya = wilayaRepo.getOne(id_wilaya);
		
		client clt = new client(nom, prenom, adresse, wilaya, /*code_postal, email, telephone, fax, code_client,*/ gtd.get_date(), cat_clt, 
				/*banqueRepo.getOne(id_banque), type_rRepo.getOne(type_reg),*/ un, sold, 10, true, false, "");
		
		clientRepo.save(clt);clientRepo.flush();
		
		String path_img_client = "";
		
		if(!img_client.isEmpty()) {
		
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
			
			clientRepo.save(clt);clientRepo.flush();
			
			//-------------------- tracking operation -----------------------------------
			
			trk.add_track("client", "Ajout d'un client", clt.getId(), user);
			
			//-------------------- tracking operation -----------------------------------
			
		}
		
		return "redirect:/add_client";
		
	}
	/*
	public String new_number_code_client() {
		
		String ret= "00001";
		
		client c = clientRepo.findFirst1ByOrderByIdDesc();
		
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
	*/
}
