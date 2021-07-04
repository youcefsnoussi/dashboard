package com.commercial.entities.schema.static_data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.static_data.tva;

public interface tva_Repository extends JpaRepository<tva, Long> {
	
	@Query( " FROM tva "
			
		  + " WHERE taux_tva = 0 ")
	
	public tva  get_tva_0();
	
	//----------------------------------------------
	
}
