package com.commercial;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.commercial.entities.schema.user_menu.repository.menuRepository;
import com.commercial.entities.schema.user_menu.repository.sub_menuRepository;

@SpringBootApplication
public class CommercialStableApplication implements CommandLineRunner {
	
	@Autowired
	menuRepository menuRepo;
	
	@Autowired
	sub_menuRepository subRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CommercialStableApplication.class, args);
		
	}
	
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("running========");
		
	}
	
}
