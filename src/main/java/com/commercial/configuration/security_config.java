package com.commercial.configuration;

import javax.sql.DataSource;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration

@EnableWebSecurity

public class security_config extends WebSecurityConfigurerAdapter{
	
	 @Autowired
	 DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("admin").password("123").roles("ADMIN");
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(NoOpPasswordEncoder.getInstance())
		.usersByUsernameQuery(
			"SELECT username,password, active FROM user_menu.users where username=?")
		.authoritiesByUsernameQuery(
			"SELECT username, 'ROLE_ADMIN' FROM user_menu.users where username=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		http.formLogin();
		http.authorizeRequests().antMatchers("/empty","/403").hasRole("ADMIN");
		*/
		http
        .authorizeRequests()
        	.antMatchers("/resources/**").permitAll()
        	.anyRequest().authenticated()
        .and()
        .headers()
        .frameOptions().disable()	
        .and()
        .formLogin()
        	.loginPage("/login").permitAll()
        .and().logout();//.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("auth_code", "JSESSIONID");
     /*   .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/logout.done").deleteCookies("JSESSIONID")
        .invalidateHttpSession(true) ;*/
        	//.logout().invalidateHttpSession(true).clearAuthentication(true);                                    
            //.permitAll();//
        
        //.and()
        //.httpBasic();
	}
	
}
