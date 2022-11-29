package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.sous_category_produit;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir_detail;

public interface facture_avoir_detailRepository extends JpaRepository<facture_avoir_detail, Long> {
	
	@Query( " FROM facture_avoir_detail fact_a_det "+ 
	
			" WHERE fact_a_det.facture_avoir = :facture_a")
		 
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
	
	//----------------------------------------------------------------------------------
	
	@Query( " SELECT fct_av_d.article.code, fct_av_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" fct_av_d.article.produit.sous_category_produit.nom_sous_category, "+
			
			" fct_av_d.article.libelle, "+
			
			" SUM(quantite)*(-1), prix_u_ht, SUM(montant_ht)*(-1), SUM(montant_tva)*(-1), SUM(montant_ttc)*(-1)" + 
			
			" FROM facture_avoir_detail fct_av_d" +
			
			" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" AND fct_av_d.facture_avoir.registre_commerce = :rc" + 
			
			" GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht")
		 
	public List<Object[]> get_quantite_sold_val_fact_av_by_rc(@Param("start") String start, @Param("end") String end, 
																@Param("rc") registre_commerce rc);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_av_d.article.code, fct_av_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" fct_av_d.article.produit.sous_category_produit.nom_sous_category, "+
			
			" fct_av_d.article.libelle, "+
			
			" SUM(quantite)*(-1), prix_u_ht, SUM(montant_ht)*(-1), SUM(montant_remise)*(-1), SUM(montant_ht_net)*(-1),"+
			
			"SUM(montant_tva)*(-1), SUM(montant_ttc)*(-1)" + 
			
			" FROM facture_avoir_detail fct_av_d" +
			
			" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht")
		 
	public List<Object[]> get_quantite_sold_val_fact_av(@Param("start") String start, @Param("end") String end);
	
	//---------------------------------------------------------------------
	
	@Query( " SELECT fct_av_d.article.produit.sous_category_produit.category_produit.nom_category, fct_av_d.article.code, "+
			
			" fct_av_d.article.libelle, SUM(quantite)*(-1)" + 
	
			" FROM facture_avoir_detail fct_av_d" +
			
			" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" GROUP BY code, nom_category, libelle")
		 
	public List<Object[]> get_quantite_sold_fact_av(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_av_d.facture_avoir.numero, fct_av_d.facture_avoir.date, fct_av_d.facture_avoir.registre_commerce.code,"+
			
			" CONCAT(fct_av_d.facture_avoir.registre_commerce.nom,' ',fct_av_d.facture_avoir.registre_commerce.prenom), "+
			
			" quantite*(-1), prix_u_ht, montant_ht*(-1), montant_tva*(-1), montant_ttc*(-1)" + 
			
			" FROM facture_avoir_detail fct_av_d" +
			
			" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" AND fct_av_d.article = :art" + 
			
			" ORDER BY fct_av_d.facture_avoir.registre_commerce.code ")
		 
	public List<Object[]> get_details_sold_val_fact_av_by_art(@Param("start") String start, @Param("end") String end, 
															@Param("art") article art);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_av_d.facture_avoir.registre_commerce.code,"+
			
				" CONCAT(fct_av_d.facture_avoir.registre_commerce.nom,' ',fct_av_d.facture_avoir.registre_commerce.prenom) AS concat, "+
				
				" SUM(quantite), prix_u_ht, SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc)" + 
				
				" FROM facture_avoir_detail fct_av_d" +
				
				" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
				
				" AND fct_av_d.article = :art" +
				
				" GROUP BY code, concat, prix_u_ht" + 
				
				" ORDER BY fct_av_d.facture_avoir.registre_commerce.code ")
			 
		public List<Object[]> get_sum_vente_produit_by_clt(@Param("start") String start, @Param("end") String end, 
																@Param("art") article art);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_av_d.tva,"+
			
			" SUM(montant_ht_net), SUM(montant_tva) " + 
			
			" FROM facture_avoir_detail fct_av_d" +
			
			" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" GROUP BY fct_av_d.tva " + 
			
			" ORDER BY fct_av_d.tva DESC ")
		 
	public List<Object[]> get_sum_declaration_tva(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT fct_av_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" SUM(quantite)*(-1), SUM(montant_ht)*(-1)" + 
	
			" FROM facture_avoir_detail fct_av_d" +
			
			" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
			
			" AND fct_av_d.article.produit.sous_category_produit.category_produit = :cat_p" + 
			
			" GROUP BY fct_av_d.article.produit.sous_category_produit.category_produit, fct_av_d.article.produit.sous_category_produit.category_produit.nom_category" +
			
			" ORDER BY fct_av_d.article.produit.sous_category_produit.category_produit.id " )
		 
	public List<Object[]> get_info_sold_fact_av_by_category(@Param("start") String start, @Param("end") String end, 
															@Param("cat_p") category_produit cat_p);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT CONCAT(fct_av_d.article.produit.sous_category_produit.category_produit.nom_category, ' ', " + 
	
				" fct_av_d.article.produit.sous_category_produit.nom_sous_category), "+
				
			" SUM(quantite)*(-1), SUM(montant_ht)*(-1)" + 
	
			" FROM facture_avoir_detail fct_av_d" +
			
			" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
			
			" AND fct_av_d.article.produit.sous_category_produit = :scat_p" + 
			
			" GROUP BY fct_av_d.article.produit.sous_category_produit.category_produit, " +
			
				"fct_av_d.article.produit.sous_category_produit.category_produit.nom_category, " +
				
				"fct_av_d.article.produit.sous_category_produit.nom_sous_category" +
			
			" ORDER BY fct_av_d.article.produit.sous_category_produit.category_produit.id " )
			 
		public List<Object[]> get_info_sold_fact_av_by_sous_category(@Param("start") String start, @Param("end") String end, 
																@Param("scat_p") sous_category_produit scat_p);
	
		//----------------------------------------------------------------------
		
		@Query( " SELECT  fct_av_d.article.produit.sous_category_produit.category_produit.nom_category, "+
				
				" SUM(quantite)*(-1), SUM(montant_ht)*(-1), SUM(montant_remise)*(-1), SUM(montant_ht_net)*(-1),"+
				
				"SUM(montant_tva)*(-1), SUM(montant_ttc)*(-1)" + 
				
				" FROM facture_avoir_detail fct_av_d" +
				
				" WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
				
				" GROUP BY  nom_category")
			 
		public List<Object[]> get_quantite_sold_val_fact_av_categ(@Param("start") String start, @Param("end") String end);
	
}
