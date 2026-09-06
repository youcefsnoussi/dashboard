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


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        	.antMatchers("/manual-login").permitAll() 
        	.antMatchers("/display_img/**").permitAll() 
        	.antMatchers("/resources/**","/login/**").permitAll()
        	.anyRequest().authenticated()
        .and()
        .headers()
        .frameOptions().disable()	
        .and()
        .csrf().disable()
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
	
	 @Bean
	    public CorsFilter corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("*");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }
	
	 
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**") // Allow CORS on all endpoints
	                        .allowedOrigins("http://105.96.0.95:9090") // Specify your front-end origin
	                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify allowed methods
	                        .allowCredentials(true);
	            }
	        };
	    }
}
