package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.cumule_facture_laiterie;

public interface cumule_facture_laiterieRepository extends JpaRepository<cumule_facture_laiterie, Long>{
	
	@Query(   " SELECT bon_livraison"
			
			+ " FROM prof_cmd_bl_fact_client_rc_avoir cmb "
			
			+ " WHERE cmb NOT IN ( SELECT combo FROM cumule_facture_laiterie ) "
			
			+ " AND cmb.client_registrecommerce.client = :clt ")
	
	public List<bon_livraison> get_bls_no_factured(@Param("clt") client client);
	
}
