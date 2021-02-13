package com.commercial.entities.schema.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.category_client;

public interface category_clientRepository extends JpaRepository<category_client, Long> {
	
	@Query( " FROM category_client cat "
			
			+ " ORDER BY cat.nom_category ASC ")
	
	public List<category_client>  select_category_ordered();
	
	//-------------------------------------------------------
	
	@Query( " FROM category_client cat " +
			
			" WHERE nom_category = :nom ")
	
	public category_client  get_category_by_name(@Param("nom") String nom);
	
}
