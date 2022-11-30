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
	
	@Query( "DELETE FROM facture_detail fact_det "
			
				+ " WHERE fact_det.facture = :facture")
		 
	public void delete_detail_facture_by_facture(facture facture);
	
	//---------------------------------------------------------------------
	
	@Query( " FROM facture_detail fact_det "
			
				+ " WHERE fact_det.facture = :facture")
		 
	public List<facture_detail> get_facture_detail(facture facture);
	
	//---------------------------------------------------------------------
	
	@Query( " FROM facture_detail fact_det "
			
				+ " WHERE fact_det.facture = :facture AND fact_det.article.consignation = 'false'")
		 
	public List<facture_detail> get_facture_detail_without_cons(facture facture);
	
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
			
			" SUM(quantite), prix_u_ht, SUM(montant_ht), SUM(montant_remise), SUM(montant_ht_net), SUM(montant_tva), "+
			
			"SUM(montant_ttc)" + 
			
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
	/*
	@Query( " SELECT fct_d.tva, "+
				
			" SUM(montant_ht_net), SUM(montant_tva) " + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"+
			
			" GROUP BY fct_d.tva " + 
			
			" ORDER BY fct_d.tva DESC ")
		 
	public List<Object[]> get_sum_declaration_tva(@Param("start") String start, @Param("end") String end);	
	*/	
	@Query( " SELECT fct_d.tva, "+
			
			" SUM(fct_d.montant_ht_net) - COALESCE(( " + 
			"	SELECT SUM(fct_a_d.montant_ht_net) " +
			"	FROM facture_avoir_detail fct_a_d " + 
			"	WHERE CAST(fct_a_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			"	AND fct_d.tva = fct_a_d.tva " + 
			"	),0) - COALESCE(( " + 
			"	SELECT SUM(fct_a_d.montant_ht) "  + 
			"	FROM facture_ristourne_detail fct_a_d " + 
			"	WHERE CAST(fct_a_d.facture_ristourne.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"	AND fct_d.tva = fct_a_d.tva " + 
			"	),0) " +
			//" montant_ht_net " +
			
			
			", SUM(fct_d.montant_tva) - COALESCE(( " + 
			"	SELECT SUM(fct_a_d.montant_tva) " + 
			"	FROM facture_avoir_detail fct_a_d " + 
			"	WHERE CAST(fct_a_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"	AND fct_d.tva = fct_a_d.tva " + 
			"	),0) - COALESCE(( " + 
			"	SELECT SUM(fct_a_d.montant_tva) " + 
			"	FROM facture_ristourne_detail fct_a_d " + 
			"	WHERE CAST(fct_a_d.facture_ristourne.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"	AND fct_d.tva = fct_a_d.tva " + 
			"	),0) " +
			//"  montant_tva " + 
			
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
			
			"SUM(fact_det.montant_ht), SUM(fact_det.montant_tva), SUM(fact_det.montant_ttc), fact_det.tva, "+
			
			"SUM(fact_det.montant_remise), SUM(fact_det.montant_ht_net)"+ 
			
			"FROM facture_detail fact_det " + 
			
			"WHERE fact_det.facture IN (:fcts) " + 
			
			//"AND blf_det.bon_livraison_facture.factured = 'false' " + 
			
			"GROUP BY article.id, unite_mesure.id, prix_u_ht, fact_det.tva " +
			 
			"ORDER BY article.id" )
	
	public List<Object[]> get_cumule_detail_facture(@Param("fcts") List<facture> facts);
	
	//---------------------------------------------------------------------
	
	@Query( value=
			"(SELECT date, CONCAT(clt.nom,' ',clt.prenom) client_pere, rc.code c, CONCAT(rc.nom,' ',rc.prenom) rc, "+
			" c_clt.nom_category AS rc_category, rc.adresse, rc.numero_rc, rc.numero_art, rc.numero_nif, w.designation wilaya, "+
			"reg.designation region, art.code, cp.nom_category, scp.nom_sous_category, prd.designation, art.subvension, "+
			"emb.nom_emballage, art.libelle, " +
			"quantite, prix_u_ht, fct_d.montant_ht, fct_d.montant_remise, fct_d.montant_ht_net, fct_d.tva, fct_d.montant_tva, " +
			"fct_d.montant_ttc, numero, fct.matricule_camion, 'Facture' " + 
			" " + 
			"FROM proforma_cmd_bl_fact.facture_detail fct_d " + 
			" " + 
			"JOIN proforma_cmd_bl_fact.facture fct ON fct.id = facture " + 
			"JOIN client.registre_commerce rc ON rc.id = registre_commerce " + 
			"JOIN client.client clt ON clt.id = client " +
			"JOIN client.category_client c_clt ON c_clt.id = rc.category_client " + 
			" " + 
			"JOIN article.article art ON art.id = fct_d.article " + 
			"JOIN article.produit prd ON prd.id = art.produit " +
			"JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit " + 
			"JOIN article.category_produit cp ON cp.id = scp.category_produit " + 
			"JOIN article.emballage_produit emb ON emb.id = art.emballage_produit " +
			"" + 
			"JOIN static_data.wilaya w ON w.id = rc.wilaya " + 
			"JOIN static_data.region reg ON reg.id = region " + 
			" " + 
			"WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			" " + 
			" ) " + 
			" " + 
			"UNION ALL  " + 
			" " + 
			"(SELECT date, CONCAT(clt.nom,' ',clt.prenom) client_pere, rc.code c, CONCAT(rc.nom,' ',rc.prenom) rc, "+
			"c_clt.nom_category AS rc_category, rc.adresse, " +
			"rc.numero_rc, rc.numero_art, rc.numero_nif, w.designation wilaya, reg.designation region, art.code," +
			" cp.nom_category, scp.nom_sous_category, prd.designation, art.subvension, emb.nom_emballage, art.libelle, " +
			"quantite*(-1), prix_u_ht, fct_d.montant_ht*(-1), fct_d.montant_remise, fct_d.montant_ht_net*(-1), " +
			"fct_d.tva, fct_d.montant_tva*(-1), fct_d.montant_ttc*(-1), numero, '', 'Facture Avoir' " + 
			" " + 
			"FROM proforma_cmd_bl_fact.facture_avoir_detail fct_d " + 
			" " + 
			"JOIN proforma_cmd_bl_fact.facture_avoir fct ON fct.id = facture_avoir " + 
			"JOIN client.registre_commerce rc ON rc.id = registre_commerce " + 
			"JOIN client.client clt ON clt.id = client " +
			"JOIN client.category_client c_clt ON c_clt.id = rc.category_client " + 
			" " + 
			"JOIN article.article art ON art.id = fct_d.article " + 
			"JOIN article.produit prd ON prd.id = art.produit " +
			"JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit " + 
			"JOIN article.category_produit cp ON cp.id = scp.category_produit " + 
			"JOIN article.emballage_produit emb ON emb.id = art.emballage_produit " +
			" " + 
			"join static_data.wilaya w on w.id = rc.wilaya " + 
			"join static_data.region reg on reg.id = region " + 
			" " + 
			"WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			" " + 
			" ) " + 
			" " + 
			"UNION ALL " + 
			" " + 
			"( " + 
			" " + 
			"(SELECT date, CONCAT(clt.nom,' ',clt.prenom) client_pere, rc.code c, CONCAT(rc.nom,' ',rc.prenom) rc, " +
			"c_clt.nom_category AS rc_category, rc.adresse, rc.numero_rc, rc.numero_art, rc.numero_nif, w.designation wilaya, " +
			"reg.designation region, art.code, cp.nom_category, scp.nom_sous_category, prd.designation, art.subvension, " +
			"emb.nom_emballage,  art.libelle, " +
			"quantite, prix_u_ht, fct_d.montant_ht, fct_d.montant_remise, fct_d.montant_ht_net, fct_d.tva, fct_d.montant_tva, " +
			"fct_d.montant_ttc, numero, matricule, 'Bon Livraison' " + 
			" " + 
			"FROM proforma_cmd_bl_fact.bon_livraison_facture_detail fct_d " + 
			" " + 
			"JOIN proforma_cmd_bl_fact.bon_livraison_facture fct ON fct.id = bon_livraison_facture " + 
			"JOIN client.registre_commerce rc ON rc.id = registre_commerce " + 
			"JOIN client.client clt ON clt.id = client " +
			"JOIN client.category_client c_clt ON c_clt.id = rc.category_client " + 
			" " + 
			"JOIN article.article art ON art.id = fct_d.article " + 
			"JOIN article.produit prd ON prd.id = art.produit " +
			"JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit " + 
			"JOIN article.category_produit cp ON cp.id = scp.category_produit " + 
			"JOIN article.emballage_produit emb ON emb.id = art.emballage_produit " +
			"" + 
			"JOIN static_data.wilaya w ON w.id = rc.wilaya " + 
			"JOIN static_data.region reg ON reg.id = region " + 
			" " + 
			"WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			" " + 
			"AND factured = 'false' AND cancel = 'false' " + 
			" " + 
			" ) " + 
			" " + 
			")  " + 
			" " + 
			"ORDER BY date, numero", nativeQuery=true )
	
	public List<Object[]> RapoortUltraDetailler(@Param("start") String start, @Param("end") String end);
	
	//---------------------------------------------------------------------
	
	@Query("SELECT fact.registre_commerce.code, fact.registre_commerce.nom, fact.registre_commerce.prenom, " +
			"fact.registre_commerce.category.nom_category, " +
			"fact.registre_commerce.adresse, fact.registre_commerce.numero_nif, " +
			"fact.registre_commerce.numero_art, fact.registre_commerce.numero_rc, " + 
			"" + 
			"SUM(fact.montant_ht_net) - COALESCE( " + 
			"	( " +
			"	SELECT SUM(fact_av.montant_ht_net) " + 
			"	FROM facture_avoir fact_av " + 
			"" + 
			"	WHERE CAST(fact_av.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"	AND  fact_av.registre_commerce = fact.registre_commerce " + 
			"	) " + 
			"	,0) - COALESCE( " + 
			"	(" + 
			"	SELECT SUM(ris.montant_ht) " + 
			"	FROM facture_ristourne ris " + 
			"" + 
			"	WHERE CAST(ris.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"	AND ris.registre_commerce = fact.registre_commerce " + 
			"	)" + 
			"	,0), " + 
			"" + 
			"SUM(fact.montant_tva) - COALESCE( " + 
			"	( " + 
			"	SELECT SUM(fact_av.tva) " + 
			"	FROM facture_avoir fact_av " + 
			"" + 
			"	WHERE CAST(fact_av.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"	AND  fact_av.registre_commerce = fact.registre_commerce " + 
			"	) " + 
			"	,0) - COALESCE( " + 
			"	( " + 
			"	SELECT SUM(ris.tva) " + 
			"	FROM facture_ristourne ris " + 
			"" + 
			"	WHERE CAST(ris.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"	AND  ris.registre_commerce = fact.registre_commerce " + 
			"	) " + 
			"	,0), " + 
			"" + 
			"SUM(fact.montant_ttc) - COALESCE( " + 
			"	( " + 
			"	SELECT SUM(fact_av.montant_ttc) " + 
			"	FROM facture_avoir fact_av " + 
			"	WHERE CAST(fact_av.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"	AND  fact_av.registre_commerce = fact.registre_commerce " + 
			"	) " + 
			"" + 
			"	,0) - COALESCE( " + 
			"	( " + 
			"	SELECT SUM(ris.montant_ttc) " + 
			"	FROM facture_ristourne ris " + 
			"	WHERE CAST(ris.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			"	AND  ris.registre_commerce = fact.registre_commerce" + 
			"	)" + 
			"	,0) " + 
			"" + 
			"FROM facture fact " + 
			"" + 
			"WHERE ( CAST(fact.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) ) " + 
			"" + 
			"AND fact.registre_commerce IN ( "+
			"SELECT fact_d.facture.registre_commerce FROM facture_detail fact_d " +
			"WHERE (CAST(fact_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)) AND "+
			"fact_d.article.produit.sous_category_produit.category_produit IN (:cat_p) " +
			") " + 
			"" + 
			"GROUP BY fact.registre_commerce.nom,fact.registre_commerce.prenom, " +
			"			fact.registre_commerce.code, " + 
			"			fact.registre_commerce.category.nom_category, " + 
			"			fact.registre_commerce.adresse, fact.registre_commerce.numero_nif, " + 
			"			fact.registre_commerce.numero_art, fact.registre_commerce.numero_rc, fact.registre_commerce " + 
			"" + 
			"ORDER BY fact.registre_commerce.nom")
	
	public List<Object[]> Etat104(@Param("start") String start, @Param("end") String end, 
			@Param("cat_p") List<category_produit> cat_p);
	
	
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
			
			"	WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
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
			
			"WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			"AND cp.id = $P{id_category}" + 
			
			"GROUP BY fact.registre_commerce, rc.numero_rc, CONCAT(rc.nom,' ',rc.prenom,' ',cc.nom_category) , rc.adresse, cc.lettre")
	public List<Object> req_stat_etat_vente_client(@Param("start") String start, @Param("end") String end, 
			@Param("cat_p") category_produit cat_p);
	*/
	
	@Query(value ="SELECT fact.registre_commerce id_rc, rc.numero_rc, CONCAT(rc.nom,' ',rc.prenom,' ',cc.nom_category) "
			+ "libelle, rc.adresse, cc.lettre, SUM(fact_d.quantite) - COALESCE(	(	SELECT SUM(fact_av_d.quantite) "
			+ "	FROM proforma_cmd_bl_fact.facture_avoir_detail fact_av_d 	JOIN proforma_cmd_bl_fact.facture_avoir fact_av "
			+ "ON fact_av.id = facture_avoir 	JOIN article.article art ON art.id = fact_av_d.article 	"
			+ "JOIN article.produit prd ON prd.id = art.produit 	"
			+ "JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit "
			+ "	JOIN article.category_produit cp ON cp.id = scp.category_produit "
			+ "	JOIN client.registre_commerce rc ON rc.id = fact_av.registre_commerce "
			+ "	JOIN client.category_client cc ON cc.id = rc.category_client "
			+ "	WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) "
			+ "	AND cp.id=:id_category AND fact_av.registre_commerce = fact.registre_commerce 	),0) quantite "
			+ " FROM proforma_cmd_bl_fact.facture_detail fact_d  JOIN proforma_cmd_bl_fact.facture fact ON fact.id = facture "
			+ " JOIN article.article art ON art.id = fact_d.article  JOIN article.produit prd ON prd.id = art.produit  "
			+ "JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit "
			+ " JOIN article.category_produit cp ON cp.id = scp.category_produit "
			+ " JOIN client.registre_commerce rc ON rc.id = fact.registre_commerce "
			+ " JOIN client.category_client cc ON cc.id = rc.category_client "
			+ " WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"
			+ " AND cp.id=:id_category GROUP BY fact.registre_commerce, rc.numero_rc, CONCAT(rc.nom,' ',rc.prenom,' ',cc.nom_category) , rc.adresse, cc.lettre",nativeQuery = true)
	public List<Object> req_stat_etat_vente_client(@Param("start") String start, @Param("end") String end,@Param("id_category") Long id_category);
	
	//---------------------------------------------------------------------
	
@Query( " SELECT fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			
			" SUM(quantite), SUM(montant_ht), SUM(montant_remise), SUM(montant_ht_net), SUM(montant_tva), "+
			
			"SUM(montant_ttc)" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" GROUP BY  nom_category")
		 
	public List<Object[]> get_quantite_sold_val_fact_cat(@Param("start") String start, @Param("end") String end);
	
@Query( " SELECT fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+

		" fct_d.article.produit.sous_category_produit.nom_sous_category, "+
		
			" SUM(quantite), SUM(montant_ht), SUM(montant_remise), SUM(montant_ht_net), SUM(montant_tva), "+
			
			"SUM(montant_ttc)" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" GROUP BY  nom_category,nom_sous_category")
		 
	public List<Object[]> get_quantite_sold_val_fact_sous_cat(@Param("start") String start, @Param("end") String end);
	
}
