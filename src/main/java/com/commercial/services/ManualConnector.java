package com.commercial.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.commercial.entities.schema.user_menu.login_track;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.entities.schema.user_menu.repository.login_trackRepository;
import com.commercial.functions.DBConnection_pg;
import com.commercial.functions.get_time_date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ManualConnector {
	
	@Autowired
	private login_trackRepository loginRepo;

	
    @GetMapping("/manual-login")
    public ResponseEntity<Object> manualLogin(
    		@RequestParam("username") String username,
    		@RequestParam("password") String password
    		) throws SQLException {
		
		
		System.out.println("ENTER manualLogin !! "+username+" "+password);
		
		DBConnection_pg db= new DBConnection_pg();		
		Connection conn = null;
		
		boolean find = false;
		
	    try {
	    	conn = db.getconnection();
            String query = "SELECT password FROM user_menu.users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                    	System.out.println("ENTER find !! ");
                    	find = true;
                    	   // Create and set authentication
                   /*     Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                                username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        return ResponseEntity.ok().build();*/
                    }
                }
            }
        } catch (SQLException e) {
        	
        	conn.close();
            e.printStackTrace();
            System.out.println(">> INTERNAL_SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	    
		try {
			conn.close();
			//con_oracle.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(find) {
			System.out.println("FOUND");
			authenticateUserManually(username,password);
			 return ResponseEntity.ok().build();
		}
		
		 System.out.println(">> UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();    
		
    }
    
    
   
    
	@RequestMapping(value="/manual-logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		
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
	
    
    
    
    

    private void authenticateUserManually(String user_, String password_) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User(user_, password_, authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}