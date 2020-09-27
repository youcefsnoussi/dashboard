package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.entities.schema.profoma_cmd_bl_fact.commande;

public interface commandeRepository extends JpaRepository<commande, Long> {
	
	public commande findFirst1ByOrderByIdDesc();
	
}
