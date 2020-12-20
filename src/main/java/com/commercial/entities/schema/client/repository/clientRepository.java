package com.commercial.entities.schema.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.entities.schema.client.client;

public interface clientRepository extends JpaRepository<client, Long> {
	
	@Query( " FROM client c "
			
			+ " WHERE c.etat_blockage = 'false' ")
	
	public List<client> client_active_only();
	
	//---------------------
	
	public client  findFirst1ByOrderByIdDesc();
	
	//---------------------
	
	@Query( " FROM client c "
			
			+ " WHERE c.code = :code ")
	
	public client get_client_by_code(String code);
	
	//---------------------
	
	@Query( " FROM client c "
			
			+ " WHERE c.etat_blockage = 'false' AND c.category.special_treatment = 'sanders' ")
	
	public List<client> get_client_laiteries();
	
	//-------------------------
	
	/*
	@Query(   "SELECT client "
			
			+ "FROM client c,  category_client cc "
			
			+ "WHERE cc.special_treatment = :st ")
	
	public client get_client_by_category_Danon(@Param("st") String special_treatment);
	*/
	
}
