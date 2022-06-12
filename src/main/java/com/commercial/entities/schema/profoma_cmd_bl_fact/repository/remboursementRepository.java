package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.remboursement;

public interface remboursementRepository extends JpaRepository<remboursement, Long>{
	
	public remboursement findFirst1ByOrderByIdDesc();
	
	@Query( " FROM remboursement rmb "
			
			+ " WHERE rmb.cancel = 'false' "
			+ " AND CAST(rmb.date_saisie AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)")
	
	public List<remboursement> get_remboursements_interval(@Param("start") String date_d, @Param("end") String date_f);
	
}
