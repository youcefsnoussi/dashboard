package com.commercial.restController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.commercial.entities.schema.article.article_consignation_relation;
import com.commercial.entities.schema.article.rc_consignation;
import com.commercial.entities.schema.article.repository.article_consignation_relationRepository;
import com.commercial.entities.schema.article.repository.rc_consignationRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.services.track_operations;

@RestController
public class ArticleConsignationRcRestController {
	
	@Autowired
	article_consignation_relationRepository art_cons_relRepo;
	
	@Autowired
	rc_consignationRepository rccRepo;
	
	@Autowired
	track_operations track;
	
	public ArticleConsignationRcRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping(value="/update_division_art_cons")
	public boolean update_division_relation_article_consignation(
					@RequestParam("id_relation") long id_relation_art_cons,
					@RequestParam(name="division", defaultValue="1") float division,
					@SessionAttribute("user") users user){
		
		Optional <article_consignation_relation> op = Optional.ofNullable(art_cons_relRepo.getOne(id_relation_art_cons));
		
		op.ifPresent( art_cons_rel -> {
			
			art_cons_rel.setDivision(division);
			
			art_cons_relRepo.save(art_cons_rel); art_cons_relRepo.flush();
			
			track.add_track("article_consignation_relation", "Modification diviseur relation article consignation",
					art_cons_rel.getId(), user);
			
		});
		
		return true;
		
	}
	
	//----------------------------------------<
	
	@GetMapping(value="/update_division_rc_cons")
	public boolean update_division_relation_rc_consignation(
					@RequestParam("id_relation") long id_relation_art_cons,
					@RequestParam(name="price", defaultValue="1") float prix,
					@SessionAttribute("user") users user){
		
		Optional <rc_consignation> op = Optional.ofNullable(rccRepo.getOne(id_relation_art_cons));
		
		op.ifPresent( rcc -> {
			
			rcc.setPrix_u_ht(prix);
			
			rccRepo.save(rcc); rccRepo.flush();
			
			track.add_track("rc_consignation", "Modification prix consignation et RC", rcc.getId(), user);
			
		});
		
		return true;
		
	}
	
}
