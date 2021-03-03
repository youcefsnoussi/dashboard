package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir_detail;

public interface facture_avoir_detailRepository extends JpaRepository<facture_avoir_detail, Long> {
	
	@Query( " FROM facture_avoir_detail fact_a_det "
			
				+ " WHERE fact_a_det.facture_avoir = :facture_a")
		 
	public List<facture_avoir_detail> get_facture_avoir_detail(facture_avoir facture_a);
	
	//--------------------------------------------------------------------------------------
	
	@Query( " SELECT fct_a_d.article.code, fct_a_d.article.libelle, "+
			
			" SUM(quantite), SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc)" + 
			
			" FROM facture_avoir_detail fct_a_d" +
			
			" WHERE CAST(fct_a_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
			
			" AND fct_a_d.facture_avoir.registre_commerce =  :rc " + 
			
			" GROUP BY code, libelle " +
			
			" ORDER BY fct_a_d.article.code")
		 
	public List<Object[]> get_quantite_avoir_val_by_rc(@Param("start") String start, @Param("end") String end,  @Param("rc") registre_commerce rc);
	
	
	
}
