package com.commercial.entities.schema.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.entities.schema.client.category_client;

public interface category_clientRepository extends JpaRepository<category_client, Long> {
	
	@Query( " FROM category_client cat "
			
			+ " ORDER BY cat.nom_category ASC ")
	
	public List<category_client>  select_category_ordered();
	
}
