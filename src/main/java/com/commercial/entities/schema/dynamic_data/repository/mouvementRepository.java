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
			
		  + " WHERE mvm.client = :clt ")
	
	public List<mouvement>  mouvement_by_client(@Param("clt") client clt); //_intervall
	
	//---------------------------------------------------------------------------------
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.client = :clt "
		  + " AND CAST(mvm.date AS date) BETWEEN (:start) AND (:end)")
	
	public List<mouvement>  mouvement_by_client_intervall(@Param("clt") client clt,@Param("start") Date start, @Param("end") Date end); 
	
	//_____________________________________________________________________________________________________________
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.registre_commerce = :rc ")
	
	public List<mouvement>  mouvement_by_rc(@Param("rc") registre_commerce rc); //_intervall
	
	//---------------------------------------------------------------------------------
	
	@Query( " FROM mouvement mvm "
			
		  + " WHERE mvm.client = :rc "
		  + " AND CAST(mvm.date AS date) BETWEEN (:start) AND (:end)")
	
	public List<mouvement>  mouvement_by_rc_intervall(@Param("rc") registre_commerce rc,@Param("start") Date start, @Param("end") Date end); 
	
	
}
