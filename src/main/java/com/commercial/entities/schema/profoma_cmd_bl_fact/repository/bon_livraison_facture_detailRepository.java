package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture_detail;

public interface bon_livraison_facture_detailRepository extends JpaRepository<bon_livraison_facture_detail, Long>{
	
	@Query( " SELECT blf_det.prix_u_ht, blf_det.tva, blf_det.article.id, blf_det.magasin.id, blf_det.unite_mesure.id, " +
			
			" SUM(quantite), SUM(montant_ht), SUM(montant_tva), SUM(montant_ht+montant_tva) " +
			
			" FROM bon_livraison_facture_detail blf_det " + 
			
			" WHERE CAST( blf_det.bon_livraison_facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			
			" AND blf_det.bon_livraison_facture.cancel = 'false' AND blf_det.bon_livraison_facture.factured = 'false' " +
			
			" AND blf_det.bon_livraison_facture.registre_commerce = :rc " +
			
			" GROUP BY blf_det.article.id, blf_det.prix_u_ht, blf_det.tva, blf_det.magasin.id, blf_det.unite_mesure.id")
		 
	public List<Object []> get_cumule_facture_blf(@Param("start") String start, @Param("end") String end, @Param("rc") registre_commerce rc);
	
	//---------------------------------------------------------------------
	
	@Query( 
			" FROM bon_livraison_facture_detail blf_det " + 
			
			" WHERE blf_det.bon_livraison_facture = :blf ") //---------> special mais ta3 shkoupi
		 
	public List<bon_livraison_facture_detail> get_detail_by_blf(@Param("blf") bon_livraison_facture blf );
	
	//---------------------------------------------------------------------
	
}
