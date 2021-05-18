package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert;

public interface bon_transfertRepository extends JpaRepository<bon_transfert, Long>{
	
	public bon_transfert findFirst1ByOrderByNumeroDesc();
	
	//---------------------------------------------------------------
	
	@Query( " FROM bon_transfert bt " +
			
  			" WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
  			
			" AND cancel = 'false' " +
			
			" ORDER BY numero" )

	public List<bon_transfert> date_between_bt(@Param("start") String start, @Param("end") String end);
	
}
