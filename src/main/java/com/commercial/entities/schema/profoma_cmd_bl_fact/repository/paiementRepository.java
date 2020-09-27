package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;

public interface paiementRepository extends JpaRepository<paiement, Long> {
	
	public paiement  findFirst1ByOrderByIdDesc();
	
@Query( " FROM paiement pay "
			
			+ " WHERE pay.cancel = 'false' ")
	
	public List<paiement> get_payments_no_cancled();
	
}
