package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement_facture;

public interface paiement_factureRepository extends JpaRepository<paiement_facture, Long> {
/*	
@Query( " FROM paiement_facture pay_fact "
			
			+ " WHERE pay_fact.paiement = :pay "
			+ " AND pay_fact.facture = :fact ")
	
	public paiement_facture get_payfact_by_payfact(@Param("pay") paiement paiement, @Param("fact") facture facture);
*/
@Query( " FROM paiement_facture pay_fact "
		
			+ " WHERE pay_fact.paiement = :pay ")
	
	public List<paiement_facture> get_payfact_by_pay(@Param("pay") paiement paiement);

}
