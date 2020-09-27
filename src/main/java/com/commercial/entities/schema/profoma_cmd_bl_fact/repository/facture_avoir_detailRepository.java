package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir_detail;

public interface facture_avoir_detailRepository extends JpaRepository<facture_avoir_detail, Long> {
	
		  @Query( " FROM facture_avoir_detail fact_a_det "
			
				+ " WHERE fact_a_det.facture_avoir = :facture_a")
		 
	public List<facture_avoir_detail> get_facture_avoir_detail(facture_avoir facture_a);
	
}
