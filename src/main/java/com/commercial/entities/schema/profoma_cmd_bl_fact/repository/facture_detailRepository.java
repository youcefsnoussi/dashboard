package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
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
		 
	public List<Object[]> get_quantite_sold_fact(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_d.article.code, fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" fct_d.article.produit.sous_category_produit.nom_sous_category, "+
			
			" fct_d.article.libelle, "+
			
			" SUM(quantite), prix_u_ht, SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc)" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht")
		 
	public List<Object[]> get_quantite_sold_val_fact(@Param("start") String start, @Param("end") String end);
	
	
	
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
	
	@Query( " SELECT fct_d.article.code, fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" fct_d.article.produit.sous_category_produit.nom_sous_category, "+
			
			" fct_d.article.libelle, "+
			
			" SUM(quantite), prix_u_ht, SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc)" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" AND fct_d.facture.registre_commerce = :rc" + 
			
			" GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht")
		 
	public List<Object[]> get_quantite_sold_val_fact_by_rc(@Param("start") String start, @Param("end") String end, 
															@Param("rc") registre_commerce rc);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_d.facture.numero, fct_d.facture.date, fct_d.facture.registre_commerce.code,"+
			
			" CONCAT(fct_d.facture.registre_commerce.nom,' ',fct_d.facture.registre_commerce.prenom), "+
			
			" quantite, prix_u_ht, montant_ht, montant_tva, montant_ttc" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" AND fct_d.article = :art" + 
			
			" ORDER BY fct_d.facture.registre_commerce.code ")
		 
	public List<Object[]> get_details_sold_val_fact_by_art(@Param("start") String start, @Param("end") String end, 
															@Param("art") article art);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_d.facture.registre_commerce.code,"+
				
			" CONCAT(fct_d.facture.registre_commerce.nom,' ',fct_d.facture.registre_commerce.prenom) AS concat, "+
			
			" SUM(quantite), prix_u_ht, SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc)" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" AND fct_d.article = :art" +
			
			" GROUP BY code, concat, prix_u_ht" + 
			
			" ORDER BY fct_d.facture.registre_commerce.code ")
		 
	public List<Object[]> get_sum_vente_produit_by_clt(@Param("start") String start, @Param("end") String end, 
															@Param("art") article art);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_d.tva, "+
				
			" SUM(montant_ht), SUM(montant_tva) " + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" GROUP BY fct_d.tva " + 
			
			" ORDER BY fct_d.tva DESC ")
		 
	public List<Object[]> get_sum_declaration_tva(@Param("start") String start, @Param("end") String end);	
		
	//----------------------------------------------------------------------
	
	@Query( " SELECT  DISTINCT(fct_d.article) " + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" AND fct_d.facture.registre_commerce = :rc " + 
			
			" ORDER BY fct_d.article.code ")
		 
	public List<article> get_articles(@Param("start") String start, @Param("end") String end, @Param("rc") registre_commerce rc);	
		
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_d.facture.numero, fct_d.facture.date, fct_d.quantite, fct_d.prix_u_ht, fct_d.montant_ht, fct_d.tva, "+
			
			" fct_d.montant_ttc, fct_d.facture.matricule_camion" + 
			
			" FROM facture_detail fct_d" + 
			
			" WHERE CAST( fct_d.facture.date as date) BETWEEN CAST( :start AS date) AND CAST( :end AS date)" + 
			
			" AND fct_d.facture.registre_commerce = :rc AND fct_d.article = :art ")
		 
	public List<Object[]> get_detail_fact_rc_art(@Param("start") String start, @Param("end") String end, @Param("rc") registre_commerce rc
													, @Param("art") article art);	
	
	//----------------------------------------------------------------------
}
