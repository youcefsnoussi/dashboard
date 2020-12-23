package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;

public interface paiementRepository extends JpaRepository<paiement, Long> {
	
	public paiement  findFirst1ByOrderByIdDesc();
	
	@Query( " FROM paiement pay "
			
			+ " WHERE pay.cancel = 'false' ")
	
	public List<paiement> get_payments_no_cancled();
	
	@Query( " FROM paiement pay "
			
			+ " WHERE pay.cancel = 'false' "
			+ " AND CAST(pay.date_saisie AS date) BETWEEN (:start) AND (:end)")
	
	public List<paiement> get_payments_no_cancled_interval(@Param("start") Date date_d, @Param("end") Date date_f);
	
}
