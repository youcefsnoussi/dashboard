package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_detail;

public interface bon_transfert_detailRepository extends JpaRepository<bon_transfert_detail, Long>{
	
	@Query( " FROM bon_transfert_detail btd " +
			
  			" WHERE CAST(btd.bon_transfert.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
  			
			" AND btd.bon_transfert.cancel = 'false' " +
			
			" ORDER BY btd.bon_transfert.id " )

	public List<bon_transfert_detail> date_between_bt_detail(@Param("start") String start, @Param("end") String end);
	
}
