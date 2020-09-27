package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;

public interface bon_livraison_detailRepository extends JpaRepository<bon_livraison_detail, Long>{
	
	@Query( " FROM bon_livraison_detail bl_d "
			
		  + " WHERE bl_d.bon_livraison = :bl ")
		 
	public List<bon_livraison_detail> get_bl_detail(@Param("bl") bon_livraison bl);
	
}
