package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;

public interface bon_livraison_detailRepository extends JpaRepository<bon_livraison_detail, Long>{
	
	@Query( " FROM bon_livraison_detail bl_d "
			
		  + " WHERE bl_d.bon_livraison = :bl AND bl_d.article.consignation = 'false' "
		  
		  + " ORDER BY bl_d.id ")
		 
	public List<bon_livraison_detail> get_bl_detail(@Param("bl") bon_livraison bl);
	
	//--------------------------------------------------------------------------------------
	
	@Query( " FROM bon_livraison_detail bl_d "
			
		  + " WHERE bl_d.bon_livraison = :bl  "
		  
		  + " ORDER BY bl_d.id ")
		 
	public List<bon_livraison_detail> get_bl_all_details(@Param("bl") bon_livraison bl);
	
	//--------------------------------------------------------------------------------------
	
	@Query( " FROM bon_livraison_detail bl_d "
			
		  + " WHERE bl_d.bon_livraison = :bl ")
		 
	public List<bon_livraison_detail> get_bl_detail_post(@Param("bl") bon_livraison bl);
	
	//--------------------------------------------------------------------------------------
	
	@Query( " FROM bon_livraison_detail bl_d "
			
		  + " WHERE bl_d.bon_livraison = :bl AND bl_d.article.consignation = 'true' ")
		 
	public List<bon_livraison_detail> get_bl_detail_articles_consignation(@Param("bl") bon_livraison bl);
	
	//--------------------------------------------------------------------------------------
	
	@Query( " FROM bon_livraison_detail bl_d "
			
		  + " WHERE bl_d.bon_livraison = :bl AND magasin = :mag ")
		 
	public List<bon_livraison_detail> get_bl_detail_magasin(@Param("bl") bon_livraison bl, @Param("mag") Magasin mag);
	
	//--------------------------------------------------------------------------------------
	
	@Query(	nativeQuery =true,value = "SELECT SUM(bld.montant_ht) AS montant_ht, SUM(bld.montant_tva) AS montant_tva, bld.prix_u_ht, "+
				
				" SUM(bld.quantite) AS quantite, bld.tva, bld.article, bld.unite_mesure" + 
	
				" FROM proforma_cmd_bl_fact.bon_livraison_detail bld" +
				
				" WHERE bld.bon_livraison IN (:bls)" +
				
				" GROUP BY bld.article, bld.prix_u_ht, bld.tva, bld.unite_mesure")
		 
	public List<Object[]> get_bl_bls_danon_by_id(@Param("bls") List<bon_livraison>  bls);
	
	//---------------------------------------------------------------------------------------
	
	@Query( " SELECT SUM(montant_ht), SUM(montant_remise), SUM(montant_ht_net), SUM(montant_tva), SUM(montant_ttc) " +
			
			" FROM bon_livraison_detail bl_d " +
			
		    " WHERE bl_d.bon_livraison = :bl")
	
	public List<Double[]> get_sum_for_bl(@Param("bl") bon_livraison bl);
	
}
