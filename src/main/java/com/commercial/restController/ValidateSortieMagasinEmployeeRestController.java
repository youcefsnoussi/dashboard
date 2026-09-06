package com.commercial.restController;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detail_employeeRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.services.track_operations;

@RestController
@SessionAttributes("user")

public class ValidateSortieMagasinEmployeeRestController {
	
	@Autowired
	bon_livraison_detail_employeeRepository ble_dRepo;
	
	@Autowired
	track_operations trk;
	
	public ValidateSortieMagasinEmployeeRestController() {
		
	}

	@RequestMapping(value="/validate_ble_d")
	public String validate_ble_d(
		@RequestParam("id_ble_d") long id_ble_d,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		String ret  = "";
		
		bon_livraison_detail_employee ble_d = ble_dRepo.getOne(id_ble_d);
			
		ble_d.setValidation(true);
		ble_d.setUpdateDate(LocalDateTime.now());
		
		ble_d.setUser_magasin_validate(user);
		
		ble_dRepo.save(ble_d); ble_dRepo.flush();
		
		trk.add_track("bon_livraison_detail_employee", "Validation article pour bon livraison employe",
				ble_d.getId(), user);
		
		return ret;
		
	}
	
}
