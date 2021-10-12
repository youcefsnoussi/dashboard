package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.sous_category_produit;
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
	
	@Query( " SELECT fct_d.article.code, fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" fct_d.article.produit.sous_category_produit.nom_sous_category, "+
			
			" fct_d.article.libelle, "+
			
			" SUM(quantite), prix_u_ht, SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc)" + 
			
			" FROM bon_livraison_facture_detail fct_d" +
			
			" WHERE CAST(fct_d.bon_livraison_facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" AND fct_d.bon_livraison_facture.factured = 'false' AND fct_d.bon_livraison_facture.cancel = 'false' " + 
			
			" GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht")
		 
	public List<Object[]> get_quantite_sold_val_bl(@Param("start") String start, @Param("end") String end);
	
	
	
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
	
	@Query( " SELECT fct_d.article.produit.sous_category_produit.category_produit.nom_category,"+
			
			" SUM(quantite), SUM(montant_ht)" + 
	
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
			
			" AND fct_d.article.produit.sous_category_produit.category_produit = :cat_p" +  
			
			" GROUP BY fct_d.article.produit.sous_category_produit.category_produit, fct_d.article.produit.sous_category_produit.category_produit.nom_category" +
			
			" ORDER BY fct_d.article.produit.sous_category_produit.category_produit.id ")
		 
	public List<Object[]> get_info_sold_fact_by_category(@Param("start") String start, @Param("end") String end, 
														 @Param("cat_p") category_produit cat_p);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT CONCAT(fct_d.article.produit.sous_category_produit.category_produit.nom_category, ' ', " +
			
				"fct_d.article.produit.sous_category_produit.nom_sous_category), "+
			
			" SUM(quantite), SUM(montant_ht)" + 
	
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
			
			" AND fct_d.article.produit.sous_category_produit = :scat_p" +  
			
			" GROUP BY fct_d.article.produit.sous_category_produit.category_produit, " +
			
				"fct_d.article.produit.sous_category_produit.category_produit.nom_category, " +
				
				"fct_d.article.produit.sous_category_produit.nom_sous_category " +
			
			" ORDER BY fct_d.article.produit.sous_category_produit.category_produit.id ")
		 
	public List<Object[]> get_info_sold_fact_by_sous_category(@Param("start") String start, @Param("end") String end, 
														 @Param("scat_p") sous_category_produit scat_p);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT  DISTINCT(fct_d.article) " + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" ORDER BY fct_d.article.libelle ")
		 
	public List<article> get_all_articles_ordered_by_libelle(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT  DISTINCT(fct_d.article.produit.sous_category_produit.category_produit) " + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" ORDER BY fct_d.article.produit.sous_category_produit.category_produit.nom_category ")
		 
	public List<category_produit> get_all_category_articles_ordered_by_libelle(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT  quantite " + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE facture = :fct AND article = :art")
		 
	public Object get_quantite_by_article_facture(@Param("fct") facture fact, @Param("art") article art);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT  SUM(quantite) " + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE facture = :fct AND article.produit.sous_category_produit.category_produit = :cat_prod")
		 
	public Object get_quantite_by_category_article_facture(@Param("fct") facture fact, @Param("cat_prod") category_produit cat_prod);
	
	//-----------------------------------------------------------------------
	
	@Query( "SELECT fact_det.article.id, fact_det.unite_mesure.id, fact_det.prix_u_ht, SUM(fact_det.quantite), "+
			
			"SUM(fact_det.montant_ht), SUM(fact_det.montant_tva), SUM(fact_det.montant_ttc), fact_det.tva "+ 
			
			"FROM facture_detail fact_det " + 
			
			"WHERE fact_det.facture IN (:fcts) " + 
			
			//"AND blf_det.bon_livraison_facture.factured = 'false' " + 
			
			"GROUP BY article.id, unite_mesure.id, prix_u_ht, fact_det.tva " +
			 
			"ORDER BY article.id" )
	
	public List<Object[]> get_cumule_detail_facture(@Param("fcts") List<facture> facts);
	
	//---------------------------------------------------------------------
	/*
	@Query("SELECT fact.registre_commerce id_rc, rc.numero_rc, CONCAT(rc.nom,' ',rc.prenom,' ',cc.nom_category) libelle, rc.adresse, cc.lettre,"+
			
			" SUM(fact_d.quantite) - COALESCE(" + 
			"	(" + 
			"	SELECT SUM(fact_av_d.quantite)" + 
			"	FROM proforma_cmd_bl_fact.facture_avoir_detail fact_av_d" + 
			
			"	JOIN proforma_cmd_bl_fact.facture_avoir fact_av ON fact_av.id = facture_avoir" + 
			
			"	JOIN article.article art ON art.id = fact_av_d.article" + 
			"	JOIN article.produit prd ON prd.id = art.produit" + 
			"	JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit" + 
			"	JOIN article.category_produit cp ON cp.id = scp.category_produit" + 
			
			"	JOIN client.registre_commerce rc ON rc.id = fact_av.registre_commerce" + 
			"	JOIN client.category_client cc ON cc.id = rc.category_client" + 
			
			"	WHERE CAST(date AS date) BETWEEN CAST($P{start} AS date) AND CAST($P{end} AS date)" + 
			"	AND cp.id = $P{id_category} AND fact_av.registre_commerce = fact.registre_commerce" + 
			"	)" + 
			"	,0) quantite" + 
			
			
			
			"FROM proforma_cmd_bl_fact.facture_detail fact_d" + 
			
			"JOIN proforma_cmd_bl_fact.facture fact ON fact.id = facture" + 
			
			"JOIN article.article art ON art.id = fact_d.article" + 
			"JOIN article.produit prd ON prd.id = art.produit" + 
			"JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit" + 
			"JOIN article.category_produit cp ON cp.id = scp.category_produit" + 
			
			"JOIN client.registre_commerce rc ON rc.id = fact.registre_commerce" + 
			"JOIN client.category_client cc ON cc.id = rc.category_client" + 
			
			"WHERE CAST(date AS date) BETWEEN CAST($P{start} AS date) AND CAST($P{end} AS date)" + 
			"AND cp.id = $P{id_category}" + 
			
			"GROUP BY fact.registre_commerce, rc.numero_rc, CONCAT(rc.nom,' ',rc.prenom,' ',cc.nom_category) , rc.adresse, cc.lettre")
	public List<Object> req_stat_etat_vente_client(@Param("start") String start, @Param("end") String end, 
			@Param("cat_p") category_produit cat_p);
	*/
}
