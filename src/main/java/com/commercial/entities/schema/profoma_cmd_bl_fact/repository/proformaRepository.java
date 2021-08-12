package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.proforma;

public interface proformaRepository extends JpaRepository<proforma, Long> {
	
	public proforma  findFirst1ByOrderByNumeroDesc();
	
	//----------------------------------------------------
	
	@Query(" FROM proforma prof " +
		   " WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) ")
	public List<proforma> get_proforma_dates(@Param("start") String start, @Param("end") String end); 
	
}
