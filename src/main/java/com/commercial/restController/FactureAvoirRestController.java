package com.commercial.restController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;

@RestController
@SessionAttributes("user")

public class FactureAvoirRestController {
	
	@Autowired
	factureRepository factRepo;
	
	public FactureAvoirRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/test_rc_factAv")
	public String test_rc_factAv(@RequestParam("facts") String facts) throws IOException{
		
    	String [] ids = facts.split("-");
    	
    	List<Long> id_facts = new ArrayList<>();
    	
    	for (String s : ids) {
			
    		id_facts.add(Long.parseLong(s));
    		
		}
    	
    	boolean ret = true;
    	
    	facture fact = factRepo.getOne(id_facts.get(0));
    	
    	for (Long id : id_facts) {
    		
    		if( fact.getRegistre_commerce().getId() != factRepo.getOne(id).getRegistre_commerce().getId() ) {
    			
    			ret = false;
    			break;
    			
    		}
    		
		}
		
    	return Boolean.toString(ret);
    	
	}
	
}
