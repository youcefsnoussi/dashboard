package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.entities.schema.profoma_cmd_bl_fact.proforma;
import com.commercial.entities.schema.profoma_cmd_bl_fact.proforma_detail;

public interface proforma_detailRepository extends JpaRepository<proforma_detail, Long> {
	
	@Query( " FROM proforma_detail prof_det "
			
				+ " WHERE prof_det.proformat = :proforma")
		 
	public List<proforma_detail> get_proforma_detail(proforma proforma);
	
}
