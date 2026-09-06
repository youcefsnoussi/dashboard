package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;

public interface bon_livraison_factureRepository extends JpaRepository<bon_livraison_facture, Long>{
	
	public bon_livraison_facture findFirst1ByOrderByNumeroDesc();
	
	//------------------------------------------------------------
	
	@Query( " FROM bon_livraison_facture blf "
			
		  + " WHERE blf.facture = :fact ")
		 
	public List<bon_livraison_facture> get_blf_by_facture(@Param("fact") facture fact);
	
	//------------------------------------------------------------
	
	@Query( " FROM bon_livraison_facture blf "
			
		  + " WHERE blf.cancel = 'FALSE' AND blf.factured = 'FALSE' ")
		 
	public List<bon_livraison_facture> get_blf_non_factured();
	
	//------------------------------------------------------------
	
	@Query( " FROM bon_livraison_facture blf "
			
		  + " WHERE blf.cancel = 'FALSE' AND blf.factured = 'FALSE' "
		  
		  + " AND CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) ")
		 
	public List<bon_livraison_facture> get_blf_non_factured_date(@Param("start") String start, @Param("end") String end);
	
	//-----------------------------------------------------------
	
	@Query( " FROM bon_livraison_facture blf "
			
		  + " WHERE blf.factured = 'True' "
		  
		  + " AND CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) ")
		 
	public List<bon_livraison_facture> get_blf_factured_date(@Param("start") String start, @Param("end") String end);
	
	//-----------------------------------------------------------
	
	@Query( " FROM bon_livraison_facture blf "
			
		  + " WHERE blf.cancel = 'FALSE' AND blf.factured = 'FALSE' "
		  
		  + " AND blf.registre_commerce = :rc "
		  
		  + " AND CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) ")
		 
	public List<bon_livraison_facture> get_blf_non_factured_rc_date(@Param("rc") registre_commerce rc, @Param("start") String start,
			@Param("end") String end );
	
	
	
	//-----------------------------------------------------------
	
	@Query( " FROM bon_livraison_facture blf "
			
		  + " WHERE blf.cancel = 'FALSE' "
		  
		  + " AND CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) "
		  
		  + " ORDER BY blf.numero")
		 
	public List<bon_livraison_facture> get_blfs_date(@Param("start") String start, @Param("end") String end);
	
	//-----------------------------------------------------------
	
	@Query( " FROM bon_livraison_facture blf "
			
		  + " WHERE blf.cancel = 'FALSE' AND blf.factured = 'FALSE' "
		  
		  + " AND blf.registre_commerce = :rc ")
		 
	public List<bon_livraison_facture> get_blf_non_factured_rc(@Param("rc") registre_commerce rc );
	
}
