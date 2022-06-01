package com.commercial;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.commercial.services.DBInitialisation;

@SpringBootApplication
public class CommercialStableApplication implements CommandLineRunner {
	
	@Autowired
	DBInitialisation Dbi;
	
	public static void main(String[] args) {
		SpringApplication.run(CommercialStableApplication.class, args);
		
	}
	
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Dbi.initMenu();
		Dbi.initSubMenu();
		Dbi.initRoles();
		Dbi.initRolesMenu();
		Dbi.initUsers();
		Dbi.initRegion();
		Dbi.initWilaya();
		Dbi.initUniteMesure();
		Dbi.initUnite();
		Dbi.initTypeReglement();
		Dbi.initTva();
		Dbi.initModePaiement();
		Dbi.initCauseFactureAvoir();
		Dbi.initBanque();
		Dbi.initCategoryClient();
		
	}
	
}
