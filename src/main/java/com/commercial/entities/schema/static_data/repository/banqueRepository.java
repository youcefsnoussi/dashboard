package com.commercial.entities.schema.static_data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.entities.schema.static_data.banque;

public interface banqueRepository extends JpaRepository<banque, Long> {
	
	@Query( " FROM banque b "
			
			+ " WHERE b.display = 'true' ")
	
	public List<banque> get_banks_displayed();
	
}
