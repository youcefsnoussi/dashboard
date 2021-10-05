package com.commercial.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.services.PaletteService;

@CrossOrigin()
@RestController

public class PaletteRestController {
	
	@Autowired
	PaletteService ps;
	
	@Autowired
	clientRepository cltRepo;
	
	public PaletteRestController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@GetMapping(value="/getClientsPalette")
	public List<client> getClientsPalette(){
		
		System.out.println("access get client");
		
		List<client> lstClientsPal = cltRepo.client_vente_palette();
		
		return lstClientsPal;
		
	}
	
	@PostMapping(value="/retourPalette")
	public void retourPalette(
			@RequestParam("id_client") long id_client,
			@RequestParam("nbr_palette") long nbr_palette
			) 
	{
		
		ps.paletteIN(nbr_palette, cltRepo.getOne(id_client));
		
	}
	
}
