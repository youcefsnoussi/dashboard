package com.commercial.restController;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;

@RestController
public class EditFactureRestController {
	
	@Autowired
	factureRepository factRepo;
	
	public EditFactureRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/get_facts_by_num")
	public List<Object[]> get_sous_cat_prod(@RequestParam("num_fact") String num_fact) throws IOException{
		
    	return factRepo.get_facts_by_num(num_fact);
		
	}
	
}
