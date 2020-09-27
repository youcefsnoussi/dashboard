package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.entities.schema.profoma_cmd_bl_fact.BonCommande;

public interface BonCommandeRepository  extends JpaRepository<BonCommande, Long> {
	
	public BonCommande  findFirst1ByOrderByIdDesc();
	
}
