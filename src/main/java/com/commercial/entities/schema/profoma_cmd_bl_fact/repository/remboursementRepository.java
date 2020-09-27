package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.entities.schema.profoma_cmd_bl_fact.remboursement;

public interface remboursementRepository extends JpaRepository<remboursement, Long>{
	
	public remboursement findFirst1ByOrderByIdDesc();
	
}
