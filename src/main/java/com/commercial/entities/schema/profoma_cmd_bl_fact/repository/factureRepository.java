package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;

public interface factureRepository extends JpaRepository<facture, Long> {
	
	public facture  findFirst1ByOrderByIdDesc();
	
	//----------------------------------------------------------
	
		@Query( " FROM facture fact "
				
				+ " WHERE fact.etat_sold = 'false' "
				+ " AND fact.client = :clt")
		 
		public List<facture> client_active_only(@Param("clt") client clt);
	
	//----------------------------------------------------------
	
		@Query( " FROM facture fact "
				
				+ " WHERE fact.date = :today OR fact.printed = 'FALSE' ")
		 
		public List<facture> today_facture_no_printed(@Param("today") String today);
		
	//----------------------------------------------------------
	
		@Query( " FROM facture fact "
				
			  + " WHERE CAST(fact.date AS date) BETWEEN (:start) AND (:end)" )
		 
		public List<facture> date_between_facture(@Param("start") Date start, @Param("end") Date end);
		
		
	//----------------------------------------------------------
	
		@Query( " FROM facture fact "
				
			  + " WHERE fact.notification = 'FALSE' " )
		 
		public List<facture> get_notification();
		
	//----------------------------------------------------------
	
		@Query( " SELECT DISTINCT SUBSTR(date, 7, 9) AS dates FROM facture "
				
				+ " WHERE date like '__/__/_%_%_%_%' "
				
				+ " ORDER BY dates ASC " )
		 
		public List<String> get_years_db();		
	
}
