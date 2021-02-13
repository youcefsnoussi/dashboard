package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_interne;

public interface bon_transfert_interneRepository extends JpaRepository<bon_transfert_interne, Long>{
	
	public bon_transfert_interne findFirst1ByOrderByIdDesc();
	
	@Query( " FROM bon_transfert_interne bti " +
			
  			" WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
  			
			" AND cancel = 'false' " )

	public List<bon_transfert_interne> date_between_bti(@Param("start") String start, @Param("end") String end);
	
}	
