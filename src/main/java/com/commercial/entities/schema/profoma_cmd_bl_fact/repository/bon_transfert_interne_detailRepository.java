package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_interne_detail;

public interface bon_transfert_interne_detailRepository extends JpaRepository<bon_transfert_interne_detail, Long>{
	
	@Query( " SELECT btid.article.produit.sous_category_produit.category_produit.nom_category, btid.article.code, "+
			
			" btid.article.libelle, SUM(quantite)" + 
	
			" FROM bon_transfert_interne_detail btid" +
			
			" WHERE CAST(btid.bon_transfert_interne.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			
			" AND btid.bon_transfert_interne.cancel = 'false' " + 
			
			" GROUP BY code, nom_category, libelle")
		 
	public List<Object[]> get_quantite_sold_bon_transfert_interne(@Param("start") String start, @Param("end") String end);
	
}
