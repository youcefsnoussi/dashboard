package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_ristourne;

public interface facture_ristourneRepository extends JpaRepository<facture_ristourne, Long>{
	
	public facture_ristourne  findFirst1ByOrderByNumeroDesc();
	
}
