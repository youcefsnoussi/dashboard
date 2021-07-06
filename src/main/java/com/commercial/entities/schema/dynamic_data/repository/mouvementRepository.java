package com.commercial.entities.schema.dynamic_data.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.dynamic_data.mouvement;

public interface mouvementRepository  extends JpaRepository<mouvement, Long>{
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.client = :clt "
		  + " ORDER BY mvm.id ASC ")
	
	public List<mouvement>  mouvement_by_client(@Param("clt") client clt); //_intervall
	
	//---------------------------------------------------------------------------------
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.client = :clt "
		  + " AND CAST(mvm.date AS date) BETWEEN (:start) AND (:end)"
		  + " ORDER BY mvm.id ASC ")
	
	public List<mouvement>  mouvement_by_client_intervall(@Param("clt") client clt,@Param("start") Date start, @Param("end") Date end); 
	
	//_____________________________________________________________________________________________________________
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.registre_commerce = :rc "
		  
		  + " ORDER BY CAST(mvm.date AS date), mvm.id ASC ")
	
	public List<mouvement>  mouvement_by_rc(@Param("rc") registre_commerce rc); //_intervall
	
	//---------------------------------------------------------------------------------
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.registre_commerce = :rc "
		  + " AND CAST(mvm.date AS date) BETWEEN (:start) AND (:end) "
		  + " ORDER BY CAST(mvm.date AS date), mvm.id ASC")
	
	public List<mouvement>  mouvement_by_rc_intervall(@Param("rc") registre_commerce rc,@Param("start") Date start, @Param("end") Date end); 
	
	//---------------------------------------------------------------------------------
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.registre_commerce = :rc "
		  + " AND CAST(date as date) BETWEEN CAST(:start as date) AND CAST(:end as date)"
		  + " ORDER BY CAST(mvm.date AS date), mvm.id ASC")
	
	public List<mouvement> sold_debut_periode(@Param("rc") registre_commerce rc, @Param("start") String start, @Param("end") String end);
	
	//---------------------------------------------------------------------------------
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.registre_commerce = :rc "
		  + " AND CAST(date as date) BETWEEN CAST(:start as date) AND CAST(:end as date)"
		  + " ORDER BY CAST(mvm.date AS date), mvm.id DESC")
	
	public List<mouvement> sold_fin_periode(@Param("rc") registre_commerce rc, @Param("start") String start, @Param("end") String end);
	
}
