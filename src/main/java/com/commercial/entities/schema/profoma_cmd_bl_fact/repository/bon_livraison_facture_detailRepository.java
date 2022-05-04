package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture_detail;

public interface bon_livraison_facture_detailRepository extends JpaRepository<bon_livraison_facture_detail, Long>{
	
	@Query( " SELECT blf_det.prix_u_ht, blf_det.tva, blf_det.article.id, blf_det.unite_mesure.id, " +
			
			" SUM(quantite), SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc), SUM(montant_ht_net), SUM(montant_remise) " +
			
			" FROM bon_livraison_facture_detail blf_det " + 
			
			" WHERE CAST( blf_det.bon_livraison_facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			
			" AND blf_det.bon_livraison_facture.cancel = 'false' AND blf_det.bon_livraison_facture.factured = 'false' " +
			
			" AND blf_det.bon_livraison_facture.registre_commerce = :rc " +
			
			" GROUP BY blf_det.article.id, blf_det.prix_u_ht, blf_det.tva, blf_det.unite_mesure.id")
		 
	public List<Object []> get_cumule_facture_blf(@Param("start") String start, @Param("end") String end, 
				@Param("rc") registre_commerce rc);
	
	//---------------------------------------------------------------------
	
	@Query( "SELECT blf_det.article.id, blf_det.unite_mesure.id, blf_det.prix_u_ht, blf_det.tva,"+
			
			"SUM(blf_det.quantite), SUM(blf_det.montant_ht), SUM(blf_det.montant_tva), SUM(blf_det.montant_ttc), "+
			 
			"SUM(montant_ht_net), SUM(montant_remise) "+  //blf_det.article.code,
			
			"FROM bon_livraison_facture_detail blf_det " + 
			
			"WHERE blf_det.bon_livraison_facture.id IN (:blfs) " + 
			
			"GROUP BY blf_det.article.id, blf_det.unite_mesure.id, blf_det.prix_u_ht, blf_det.tva, "+
			
			"blf_det.article.code "+
			
			"ORDER BY blf_det.article.code" )
	
	public List<Object[]> get_article_cumuleAll_from_detail_blfs(@Param("blfs") List<Long> blfs);
	
	//---------------------------------------------------------------------
	
	@Query( 
			" FROM bon_livraison_facture_detail blf_det " + 
			
			" WHERE blf_det.bon_livraison_facture = :blf ") //---------> special mais ta3 shkoupi
		 
	public List<bon_livraison_facture_detail> get_detail_by_blf(@Param("blf") bon_livraison_facture blf );
	
	//---------------------------------------------------------------------
	
	@Query( " SELECT DISTINCT(article) " +
	
			" FROM bon_livraison_facture_detail blf_det " + 
			
			" WHERE CAST( blf_det.bon_livraison_facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) "+
			
			" ORDER BY blf_det.article.code ") //---------> special mais ta3 shkoupi
		 
	public List<article> get_articles_from_blfs_detail(@Param("start") String start, @Param("end") String end );
	
	//---------------------------------------------------------------------
	
	@Query( " FROM bon_livraison_facture_detail blf_det " + 
			
			" WHERE blf_det.bon_livraison_facture.id IN (:blfs) " +
			
			" AND blf_det.bon_livraison_facture.factured = 'false' ") //---------> special mais ta3 shkoupi
		 
	public List<bon_livraison_facture_detail> get_detail_blfs_by_list_blf(@Param("blfs") List<Long> blfs );
	
	//---------------------------------------------------------------------
	
	@Query( "SELECT blf_det.article.id, blf_det.magasin.id, blf_det.unite_mesure.id, SUM(blf_det.quantite), " +
			
			"SUM(blf_det.montant_remise) "+ 
	
			"FROM bon_livraison_facture_detail blf_det " + 
			
			"WHERE blf_det.bon_livraison_facture.id IN (:blfs) " + 
			
			"AND blf_det.bon_livraison_facture.factured = 'false' " + 
			
			"GROUP BY blf_det.article.id, blf_det.magasin.id, blf_det.unite_mesure.id " )
	
	public List<Object[]> get_article_cumuleQuant_from_detail_blfs(@Param("blfs") List<Long> blfs);
	
	//-----------------------------------------------------------------------
	
	@Query( " SELECT SUM(montant_ht), SUM(montant_remise), SUM(montant_ht_net), SUM(montant_tva), SUM(montant_ttc) " +
			
			" FROM bon_livraison_facture_detail blf_d " +
			
		    " WHERE blf_d.bon_livraison_facture = :blf")
	
	public List<Double[]> get_sum_for_blf(@Param("blf") bon_livraison_facture blf);
	
	//-----------------------------------------------------------------------
	
	@Query( " SELECT fct_d.article.code, fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" fct_d.article.produit.sous_category_produit.nom_sous_category, "+
			
			" fct_d.article.libelle, "+
			
			" SUM(quantite), prix_u_ht, SUM(montant_ht), SUM(montant_remise), SUM(montant_ht_net), SUM(montant_tva), "+
			
			" SUM(montant_ttc)" + 
			
			" FROM bon_livraison_facture_detail fct_d" +
			
			" WHERE CAST(fct_d.bon_livraison_facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" AND fct_d.bon_livraison_facture.factured = 'false' AND fct_d.bon_livraison_facture.cancel = 'false' " + 
			
			" GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht")
		 
	public List<Object[]> get_quantite_sold_val_bl(@Param("start") String start, @Param("end") String end);
	
}
