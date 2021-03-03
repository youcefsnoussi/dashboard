package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;

public interface facture_detailRepository extends JpaRepository<facture_detail, Long> {
	
	@Query( " FROM facture_detail fact_det "
			
				+ " WHERE fact_det.facture = :facture")
		 
	public List<facture_detail> get_facture_detail(facture facture);
	
	//---------------------------------------------------------------------
	
	@Query( " SELECT fct_d.article.produit.sous_category_produit.category_produit.nom_category, fct_d.article.code, "+
			
			" fct_d.article.libelle, SUM(quantite)" + 
	
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" GROUP BY code, nom_category, libelle")
		 
	public List<Object[]> get_quantite_sold(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_d.article.code, fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" fct_d.article.produit.sous_category_produit.nom_sous_category, "+
			
			" fct_d.article.libelle, "+
			
			" SUM(quantite), prix_u_ht, SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc)" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht")
		 
	public List<Object[]> get_quantite_sold_val(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_d.article.code, fct_d.article.libelle, "+
			
			" SUM(quantite), SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc)" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
			
			" AND fct_d.facture.registre_commerce =  :rc " + 
			
			" GROUP BY code, libelle "+
			
			" ORDER BY fct_d.article.code")
		 
	public List<Object[]> get_quantite_sold_val_by_rc(@Param("start") String start, @Param("end") String end,  @Param("rc") registre_commerce rc);
	
	//----------------------------------------------------------------------
	/*
	@Query( " SELECT fct_d.article.code, fct_d.article.libelle, SUM(fct_d.quantite) quant, SUM(fct_d.montant_ht) mnt_ht, " +
			" SUM(fct_d.montant_ttc) mnt_ttc " + 
			 
			" FROM facture_detail fct_d " + 
			
			" WHERE CAST( fct_d.facture.date AS date ) BETWEEN CAST( :start AS date) AND CAST( :end AS date) " + 
			" AND  fct_d.facture.registre_commerce = :rc " + 
			
			" GROUP BY fct_d.article.code, fct_d.article.libelle " + 
			
			" UNION ALL " + 
			
			" SELECT fct_av_d.article.code, fct_av_d.article.libelle, (-1)*SUM(fct_av_d.quantite) quant, (-1)*SUM(fct_av_d.montant_ht) mnt_ht, "+
			" (-1)*SUM(fct_av_d.montant_ttc) mnt_ttc " + 
			
			" FROM facture_avoir_detail fct_av_d " + 
			
			" WHERE CAST( fct_av_d.facture_avoir.date AS date ) BETWEEN CAST( :start AS date) AND CAST( :end AS date) " + 
			" AND  fct_av_d.facture_avoir.registre_commerce = :rc " + 
			
			" GROUP BY fct_av_d.article.code, fct_av_d.article.libelle ")
		 
	public List<Object[]> get_quantite_sold_val_by_rc_without_av(@Param("start") String start, @Param("end") String end,  
			@Param("rc") registre_commerce rc);
	
	*/
}
