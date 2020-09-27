package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.prof_cmd_bl_fact_client_rc_avoir;

public interface prof_cmd_bl_fact_client_rc_avoirRepository extends JpaRepository<prof_cmd_bl_fact_client_rc_avoir, Long> {
	
	@Query( " FROM prof_cmd_bl_fact_client_rc_avoir pcbf "
			
			+ " WHERE pcbf.facture = :fct ")
	 
	public prof_cmd_bl_fact_client_rc_avoir get_relation_by_facture(@Param("fct") facture fct);
	
	@Query( " FROM prof_cmd_bl_fact_client_rc_avoir pcbf "
			
			+ " WHERE pcbf.bon_livraison = :bl ")
	 
	public prof_cmd_bl_fact_client_rc_avoir get_relation_by_bl(@Param("bl") bon_livraison bl);
	
}
