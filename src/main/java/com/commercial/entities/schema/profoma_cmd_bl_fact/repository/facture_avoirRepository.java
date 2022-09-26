package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;

public interface facture_avoirRepository extends JpaRepository<facture_avoir, Long> {
	
	public facture_avoir  findFirst1ByOrderByNumeroDesc();
	
	//-----------------------------------------------------------
	
	@Query( " FROM facture_avoir fact_a "
			
				+ " WHERE fact_a.date = :today")
		 
		public List<facture_avoir> today_facture_avoir(@Param("today") String today);
		
	//----------------------------------------------------------
	
	@Query( " FROM facture_avoir fact_a "
			
		  + " WHERE CAST(fact_a.date AS date) BETWEEN (:start) AND (:end)" )
	 
	public List<facture_avoir> date_between_facture_avoir(@Param("start") Date start, @Param("end") Date end);	
	
	//----------------------------------------------------------
	
	@Query( " SELECT DISTINCT SUBSTR(date, 7, 9) AS dates FROM facture_avoir "
			
			+ " WHERE date like '__/__/_%_%_%_%' "
			
			+ " ORDER BY dates ASC " )
	 
	public List<String> get_years_db();

	public List<facture_avoir> findByClientId(long id_client, Sort by);	
	
}
