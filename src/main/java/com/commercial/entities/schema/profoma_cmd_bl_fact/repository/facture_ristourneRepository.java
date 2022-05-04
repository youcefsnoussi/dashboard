package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_ristourne;

public interface facture_ristourneRepository extends JpaRepository<facture_ristourne, Long>{
	
	public facture_ristourne  findFirst1ByOrderByNumeroDesc();
	
	//--------------------------------------------
	
	@Query(" FROM facture_ristourne prof " +
		   " WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) ")
	public List<facture_ristourne> get_ristourne_dates(@Param("start") String start, @Param("end") String end); 
	
}
