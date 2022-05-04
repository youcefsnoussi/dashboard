package com.commercial.webController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.backup_edit.repository.article_backupRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.user_menu.login_track;
import com.commercial.entities.schema.user_menu.roles;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.entities.schema.user_menu.repository.login_trackRepository;
import com.commercial.entities.schema.user_menu.repository.menuRepository;
import com.commercial.entities.schema.user_menu.repository.roles_menuRepository;
import com.commercial.entities.schema.user_menu.repository.userRepository;
import com.commercial.functions.get_time_date;



@Controller
@SessionAttributes("user")

public class WebController {
	
	@ModelAttribute("user")
	   public users user() {
	      return new users();
	}
	
	
	
	@Autowired
	private userRepository userrepository;
	@Autowired
	private roles_menuRepository roles_menurepository;
	
	@Autowired
	private menuRepository menurepository;
	
	@Autowired
	private client_registreCommerceRepository client_rcRepo;
	
	@Autowired
	private login_trackRepository loginRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	article_backupRepository art_bRepo;
	
	
	@RequestMapping(value="/")
	
	public String request_application(HttpServletRequest request,Model model){
		
		get_time_date gtd = new get_time_date();
		
		String ret = "welcome";
		
		//---------------------------- add user information ---------------------------
		String user_name = request.getUserPrincipal().getName();
		
		users user  = userrepository.find_user_byusername(user_name); 
		
		model.addAttribute("user", user); //----------------- adding user object to session
		
		roles role = user.getRole();//roles_repository.getOne(user.getId_role().getId());
		
		//long id_role = role.getId();
		
		boolean one_interface = role.isOne_interface();
		
		//model.addAttribute("role",nom_role);
		
		//---------------------------- TRACK LOGIN v----------------------------------
		
		String adresse = request.getRemoteAddr();
		
		Cookie [] cookie = request.getCookies();
		
		for(int i=0;i<cookie.length;i++) {
			
			//System.out.println(cookie[i].getName()+" / "+cookie[i].getValue());
			
			if(cookie[i].getName().equals("JSESSIONID")) {
				
				String cookie_value = cookie[i].getValue();
				
				login_track lt = loginRepo.if_login_exist(cookie_value);
				
				if(lt==null) {
					
					login_track new_lt = new login_track(user, adresse, gtd.get_date(), gtd.get_time(), "", "", cookie_value);
					
					loginRepo.save(new_lt); loginRepo.flush();
					
				}
				
			}
			
		}
		
		//-----------------------------------------------------------------------------
		
		//------------------- Wich Interface to Redirect-------------------------------
		
		
		if(one_interface==false) {
			
			//---------------------------- MENU information ---------------------------
			List<Object[]> list_menu = roles_menurepository.get_menu_by_role(role);
			/*
			System.out.println("wow=="+submenu_repository.findAll().get(0).getId());
			
			System.out.println(">>>>>=="+list_menu);
			
			System.out.println("lm=="+list_menu.get(0).getId()+" // nm=="+list_menu.get(0).getNom_menu());
			*/
			
			model.addAttribute("list_menu", list_menu);
			
			ArrayList<Object> ll = new ArrayList<>();
			
			for(int i=0;i<list_menu.size();i++) {
				
				Object[] m =  list_menu.get(i);
				
				//System.out.println("obj size->"+m.length);
				
				//System.out.println("id_menu == "+m[0]+" // "+m[1]);
				
				List<Object[]> list_submenu = roles_menurepository.get_submenu_by_user_role(role, menurepository.getOne((long) m[0]));
				
				ll.add(list_submenu);
			}
			
			/*
			for(int i=0; i<ll.size();i++) {
				
				Object[] obj =   ll.get(i);
				
				System.out.println("=============>>"+obj[0]+"/"+obj[1]+"/"+obj[2]+"/"+obj[3]+"/"+obj[4]);
				
			}
			*/
			model.addAttribute("list_submenu",ll);
			
			model.addAttribute("notification",client_rcRepo.get_number_notification(gtd.get_date()));
			
			model.addAttribute("one_interface", false);
			
			//-----------------------------------------------------------------------------
			
		}
		else {
			
			model.addAttribute("one_interface", true);
			
		}
		
		//-----------------------------------------------------------------------------
		
		return ret;
		
	}
	
	//*******************************************************************************************************
	
	@RequestMapping(value="/notif")
	public String notification(HttpServletRequest request,Model model){
		
		get_time_date gtd = new get_time_date();
		
		model.addAttribute("rc_client",client_rcRepo.get_list_notification(gtd.get_date()));
		
		return "notification";
		
	}
	
	//*******************************************************************************************************
	
	@RequestMapping(value="/test")
	public String test(){
		
		return "one_interface";
		
	}
	
	@RequestMapping(value="/acceuil")
	public String acceuil(HttpServletRequest request, @SessionAttribute("user") users user){
		
		String ret = "acceuil";
		
		roles role = user.getRole();
		
		boolean one_interface = role.isOne_interface();
		
		if(one_interface==true) {
			
			ret = "redirect:/"+role.getPage_to_display();
			
		}
		
		if(role.getNom_role().equals("Admin") || role.getIds_banned().contains("dashboard")) {
			
			ret = "redirect:/dashboard?start=0&end=0&nbr_days=0";
			
		}
		
		return ret;
		
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, @SessionAttribute("user") users user){
		
		get_time_date gtd = new get_time_date();
		
		Cookie [] cookie = request.getCookies();
		
		for(int i=0;i<cookie.length;i++) {
			
			//System.out.println(cookie[i].getName()+" / "+cookie[i].getValue());
			
			if(cookie[i].getName().equals("JSESSIONID")) {
				
				String cookie_value = cookie[i].getValue();
				
				login_track lt = loginRepo.if_login_exist(cookie_value);
				
				if(lt!=null) {
					
					lt.setDate_logout(gtd.get_date());
					
					lt.setTime_logout(gtd.get_time());
					
					loginRepo.save(lt); loginRepo.flush();
					
				}
				
			}
			
		}
		
		response.addCookie(new Cookie("JSESSIONID", "0000"));
		
		return "redirect:/login";
		
	}
	
	//---------------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/login")
	public String login(){
		
		return "login";
		
	}
	
	//----------------------------------------------------------------------------------------------------
	
	@RequestMapping(value="/403")
	public String error(){
		
		return "403";
		
	}
	
	
}
