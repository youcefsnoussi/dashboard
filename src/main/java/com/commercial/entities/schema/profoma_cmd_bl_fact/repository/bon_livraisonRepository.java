package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.user_menu.users;

public interface bon_livraisonRepository extends JpaRepository<bon_livraison, Long> {
	
	public bon_livraison findFirst1ByOrderByIdDesc();
	
	//____________________________________________________
	
	@Query( " FROM bon_livraison bl "
			
		  + " WHERE bl.etat_livraison = '0' AND cancel = 'FALSE' ")
		 
	public List<bon_livraison> get_bl_encours();
	
	//____________________________________________________
	
	@Query( " FROM bon_livraison bl "
			
		  + " WHERE bl.etat_livraison = '0' AND users = :user AND cancel = 'FALSE' ")
		 
	public List<bon_livraison> get_bl_encours_with_user(@Param("user") users user);
	
	//____________________________________________________
	
	@Query( " FROM bon_livraison bl "
			
		  + " WHERE bl.etat_livraison = '0' AND registre_commerce = :rc AND cancel = 'FALSE' ")
		 
	public List<bon_livraison> get_bl_encours_by_rc(@Param("rc") registre_commerce rc);
	
	//____________________________________________________
	
	@Query( " FROM bon_livraison bl "
			
		  + " WHERE bl.etat_livraison = '0' AND client = :clt AND cancel = 'FALSE' ")
		 
	public List<bon_livraison> get_bl_encours_by_clt(@Param("clt") client clt);
	
}
