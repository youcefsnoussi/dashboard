package com.commercial.restController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detail_employeeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_employeeRepository;
import com.commercial.entities.schema.user_menu.users;

@RestController
@SessionAttributes("user")

public class CheckCountDetailBleValidationRestController {
	
	@Autowired
	bon_livraison_detail_employeeRepository ble_dRepo;
	
	@Autowired
	bon_livraison_employeeRepository bleRepo;
	
	@RequestMapping(value="/check_ble_detail_validation")
	public List<bon_livraison_detail_employee> validate_ble_d(
		@RequestParam("id_ble") long id_ble,
		@SessionAttribute("user") users user) throws IOException, ParseException{
		
		
		bon_livraison_employee ble = bleRepo.getOne(id_ble);
			
		List<bon_livraison_detail_employee> validateDetails = ble_dRepo.check_ble_detail_validation(ble);
		
		return validateDetails;
		
	}
	
}
