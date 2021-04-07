package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_ristourne;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_ristourne_detail;

public interface facture_ristourne_detailRepository extends JpaRepository<facture_ristourne_detail, Long>{
	
	@Query( " FROM facture_ristourne_detail fact_ris_det "+ 
			
			" WHERE fact_ris_det.facture_ristourne = :fct_ris")
		 
	public List<facture_ristourne_detail> get_facture_ristourne_detail(@Param("fct_ris") facture_ristourne facture_ris);
	
}
