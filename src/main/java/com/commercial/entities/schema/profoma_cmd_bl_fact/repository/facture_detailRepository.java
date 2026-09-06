package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.HashMap;
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
	
	/* Dashboard: one single aggregate query for the whole date range (replaces the
	   per-category/per-period query loop that made the dashboard slow). Grouped by
	   category, sous-category and invoice day; bucketing into periods is done in Java. */
	@Query( value = " SELECT cp.id AS cat_id, cp.nom_category AS cat_nom, " +
			" scp.nom_sous_category AS scat_nom, f.date AS jour, " +
			" SUM(fd.quantite) AS total_qte, SUM(fd.montant_ht) AS total_ht " +
			" FROM proforma_cmd_bl_fact.facture_detail fd " +
			" JOIN proforma_cmd_bl_fact.facture f ON f.id = fd.facture " +
			" JOIN article.article a ON a.id = fd.article " +
			" JOIN article.produit p ON p.id = a.produit " +
			" JOIN article.sous_category_produit scp ON scp.id = p.sous_category_produit " +
			" JOIN article.category_produit cp ON cp.id = scp.category_produit " +
			" WHERE CAST(f.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			" GROUP BY cp.id, cp.nom_category, scp.nom_sous_category, f.date ", nativeQuery = true)
	public List<Object[]> get_dashboard_sums_by_day(@Param("start") String start, @Param("end") String end);

	//----------------------------------------------------------------------

	/* Dashboard: category / sous-category / wilaya breakdown for the whole range.
	   Feeds the flow and hierarchy charts. Additive - nothing else uses it. */
	@Query( value = " SELECT cp.id AS cat_id, cp.nom_category AS cat_nom, " +
			" scp.nom_sous_category AS scat_nom, w.designation AS wilaya, " +
			" SUM(fd.quantite) AS total_qte, SUM(fd.montant_ht) AS total_ht " +
			" FROM proforma_cmd_bl_fact.facture_detail fd " +
			" JOIN proforma_cmd_bl_fact.facture f ON f.id = fd.facture " +
			" JOIN client.registre_commerce rc ON rc.id = f.registre_commerce " +
			" JOIN static_data.wilaya w ON w.id = rc.wilaya " +
			" JOIN article.article a ON a.id = fd.article " +
			" JOIN article.produit p ON p.id = a.produit " +
			" JOIN article.sous_category_produit scp ON scp.id = p.sous_category_produit " +
			" JOIN article.category_produit cp ON cp.id = scp.category_produit " +
			" WHERE CAST(f.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			" GROUP BY cp.id, cp.nom_category, scp.nom_sous_category, w.designation ", nativeQuery = true)
	public List<Object[]> get_dashboard_breakdown(@Param("start") String start, @Param("end") String end);

	//----------------------------------------------------------------------

	/* Dashboard detail drawer: what each ARTICLE (libellé) sold in the range,
	   with its category and sous-category so the page can group them.
	   Additive - nothing else uses it. */
	@Query( value = " SELECT cp.nom_category AS cat_nom, " +
			" scp.nom_sous_category AS scat_nom, " +
			" a.libelle AS libelle, a.code AS code, " +
			" SUM(fd.quantite) AS total_qte, SUM(fd.montant_ht) AS total_ht " +
			" FROM proforma_cmd_bl_fact.facture_detail fd " +
			" JOIN proforma_cmd_bl_fact.facture f ON f.id = fd.facture " +
			" JOIN article.article a ON a.id = fd.article " +
			" JOIN article.produit p ON p.id = a.produit " +
			" JOIN article.sous_category_produit scp ON scp.id = p.sous_category_produit " +
			" JOIN article.category_produit cp ON cp.id = scp.category_produit " +
			" WHERE CAST(f.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			" GROUP BY cp.nom_category, scp.nom_sous_category, a.libelle, a.code ", nativeQuery = true)
	public List<Object[]> get_dashboard_articles(@Param("start") String start, @Param("end") String end);

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
	
	@Query(value="WITH FactureTotals AS (" + 
			"    SELECT" + 
			"        fct.registre_commerce," + 
			"        SUM(fct_d.montant_ht_net) AS total_ht_net," + 
			"        SUM(fct_d.montant_tva) AS total_tva," + 
			"        SUM(fct_d.montant_ttc) AS total_ttc" + 
			"    FROM" + 
			"        proforma_cmd_bl_fact.facture fct" + 
			"    JOIN" + 
			"        proforma_cmd_bl_fact.facture_detail fct_d ON fct.id = fct_d.facture" + 
			"	 JOIN" + 
			"		article.article art ON fct_d.article = art.id" + 
			"	 JOIN" + 
			"		article.produit prd ON art.produit = prd.id" + 
			"	 JOIN" + 
			"		article.sous_category_produit scp ON prd.sous_category_produit = scp.id" + 
			"	 JOIN" + 
			"		article.category_produit cp ON scp.category_produit = cp.id" + 
			"    WHERE" + 
			"		CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			"       AND cp.id IN (:cat_p)" + 
			"    GROUP BY" + 
			"       fct.registre_commerce" + 
			")," + 
			" AvoirTotals AS (" + 
			"    SELECT" + 
			"        fct.registre_commerce," + 
			"        SUM(fct_d.montant_ht_net) AS total_ht_net," + 
			"        SUM(fct_d.montant_tva) AS total_tva," + 
			"        SUM(fct_d.montant_ttc) AS total_ttc" + 
			"    FROM" + 
			"        proforma_cmd_bl_fact.facture_avoir fct" + 
			"    JOIN" + 
			"        proforma_cmd_bl_fact.facture_avoir_detail fct_d ON fct.id = fct_d.facture_avoir" + 
			"	 JOIN" + 
			"		article.article art ON fct_d.article = art.id" + 
			"	 JOIN" + 
			"		article.produit prd ON art.produit = prd.id" + 
			"	 JOIN" + 
			"		article.sous_category_produit scp ON prd.sous_category_produit = scp.id" + 
			"	 JOIN" + 
			"		article.category_produit cp ON scp.category_produit = cp.id" + 
			"    WHERE" + 
			"		CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			"       AND cp.id IN (:cat_p)" + 
			"    GROUP BY" + 
			"       fct.registre_commerce" + 
			")," + 
			" RistourneTotals AS (" + 
			"    SELECT" + 
			"        fct.registre_commerce," + 
			"        SUM(fct_d.montant_ht) AS total_ht_net," + 
			"        SUM(fct_d.montant_tva) AS total_tva," + 
			"        SUM(fct_d.montant_ttc) AS total_ttc" + 
			"    FROM" + 
			"        proforma_cmd_bl_fact.facture_ristourne fct" + 
			"    JOIN" + 
			"        proforma_cmd_bl_fact.facture_ristourne_detail fct_d ON fct.id = fct_d.facture_ristourne" + 
			"	 JOIN" + 
			"		article.article art ON fct_d.article = art.id" + 
			"	 JOIN" + 
			"		article.produit prd ON art.produit = prd.id" + 
			"	 JOIN" + 
			"		article.sous_category_produit scp ON prd.sous_category_produit = scp.id" + 
			"	 JOIN" + 
			"		article.category_produit cp ON scp.category_produit = cp.id" + 
			"    WHERE" + 
			"		CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			"       AND cp.id IN (:cat_p)" + 
			"    GROUP BY" + 
			"       fct.registre_commerce" + 
			")" + 
			" SELECT" + 
			"    registre.code," +
			"    registre.nom," +
			"	 registre.prenom," +
			"	 cc.nom_category," + 
			"    registre.adresse," + 
			"    registre.numero_nif," + 
			"    registre.numero_art," + 
			"    registre.numero_rc," + 
			"    COALESCE(SUM(FactureTotals.total_ht_net), 0) - COALESCE(SUM(AvoirTotals.total_ht_net), 0) - COALESCE(SUM(RistourneTotals.total_ht_net), 0) montant_ht," + 
			"    COALESCE(SUM(FactureTotals.total_tva), 0) - COALESCE(SUM(AvoirTotals.total_tva), 0) - COALESCE(SUM(RistourneTotals.total_tva), 0) montant_tva," + 
			"    COALESCE(SUM(FactureTotals.total_ttc), 0) - COALESCE(SUM(AvoirTotals.total_ttc), 0) - COALESCE(SUM(RistourneTotals.total_ttc), 0) montant_ttc" + 
			" FROM" + 
			"    client.registre_commerce registre" + 
			" INNER JOIN" + 
			"    client.category_client cc ON registre.category_client = cc.id" + 
			" LEFT JOIN" + 
			"    FactureTotals ON registre.id = FactureTotals.registre_commerce" + 
			" LEFT JOIN" + 
			"    AvoirTotals ON registre.id = AvoirTotals.registre_commerce" + 
			" LEFT JOIN" + 
			"    RistourneTotals ON registre.id = RistourneTotals.registre_commerce" + 
			" GROUP BY" + 
			"    registre.code," + 
			"    registre.nom," + 
			"    registre.prenom," + 
			"    cc.nom_category," + 
			"    registre.adresse," + 
			"    registre.numero_nif," + 
			"    registre.numero_art," + 
			"    registre.numero_rc" + 
			" HAVING" + 
			"    COALESCE(SUM(FactureTotals.total_ht_net), 0) - COALESCE(SUM(AvoirTotals.total_ht_net), 0) - COALESCE(SUM(RistourneTotals.total_ht_net), 0) != 0" + 
			"    OR COALESCE(SUM(FactureTotals.total_tva), 0) - COALESCE(SUM(AvoirTotals.total_tva), 0) - COALESCE(SUM(RistourneTotals.total_tva), 0) != 0" + 
			"    OR COALESCE(SUM(FactureTotals.total_ttc), 0) - COALESCE(SUM(AvoirTotals.total_ttc), 0) - COALESCE(SUM(RistourneTotals.total_ttc), 0) != 0" + 
			" ORDER BY" + 
			"    registre.code;", nativeQuery=true)
	
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
	
	
	//---------------------------------------------------------------------
	
@Query( " SELECT fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+

		" fct_d.article.produit.sous_category_produit.nom_sous_category, "+
		
			" SUM(quantite), SUM(montant_ht), SUM(montant_remise), SUM(montant_ht_net), SUM(montant_tva), "+
			
			"SUM(montant_ttc)" + 
			
			" FROM facture_detail fct_d" +
			
			" WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" + 
			
			" GROUP BY  nom_category,nom_sous_category")
		 
	public List<Object[]> get_quantite_sold_val_fact_sous_cat(@Param("start") String start, @Param("end") String end);
	
	
	
	
	/************ FODHIL ********************/
	
	@Query(value ="select prenom,nom,adresse,numero_rc, " + 
			"((select sum(COALESCE(quantite, 0 ))  from proforma_cmd_bl_fact.facture_detail fact_d " + 
			"join article.article art on fact_d.article=art.id " + 
			"join  article.produit prod on art.produit = prod.id " + 
			"join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
			"join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
			"where fact_d.facture in (select id from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  " + 
			"                         and registre_commerce=clt.id) " + 
			"  and cat_prod.nom_category=:cat_p ) " + 
			" - " + 
			"  " + 
			"  (select   "
			+"	 	 		COALESCE(sum((select sum(COALESCE(quantite, 0)) from proforma_cmd_bl_fact.facture_avoir_detail fact_av_d  "
			+"	 			join article.article art on fact_av_d.article=art.id "
			+"	 	 		join  article.produit prod on art.produit = prod.id "
			+"	 	 		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id "
			+"	 	 		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id "
			+"	 	 		where fact_av_d.facture_avoir=fct_av.id and cat_prod.nom_category=:cat_p )  ), 0)"
					
			+"	 	 		from proforma_cmd_bl_fact.facture_avoir fct_av "
					
			+"	 	 		where   cast(fct_av.date as date) between CAST(:start AS date) AND CAST(:end AS date) "
			+"	 	 		                        and fct_av.registre_commerce=clt.id) "+
			"  " + 
			
			") " + 
			"as farine, " + 
			" " + 
			"((select sum(COALESCE(quantite, 0 ))  from proforma_cmd_bl_fact.facture_detail fact_d " + 
			"join article.article art on fact_d.article=art.id " + 
			"join  article.produit prod on art.produit = prod.id " + 
			"join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
			"join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
			"where fact_d.facture in (select id from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  " + 
			"                         and registre_commerce=clt.id) " + 
			"  and cat_prod.nom_category='Semoule' ) " + 
			" - " + 
			"  " + 
			"  (select   "
			+"	 	 		COALESCE(sum((select sum(COALESCE(quantite, 0)) from proforma_cmd_bl_fact.facture_avoir_detail fact_av_d  "
			+"	 			join article.article art on fact_av_d.article=art.id "
			+"	 	 		join  article.produit prod on art.produit = prod.id "
			+"	 	 		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id "
			+"	 	 		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id "
			+"	 	 		where fact_av_d.facture_avoir=fct_av.id and cat_prod.nom_category='Semoule' )  ), 0)"
					
			+"	 	 		from proforma_cmd_bl_fact.facture_avoir fct_av "
					
			+"	 	 		where   cast(fct_av.date as date) between CAST(:start AS date) AND CAST(:end AS date) "
			+"	 	 		                        and fct_av.registre_commerce=clt.id) "+
			") " + 
			"as semoule, " + 
			" " + 
			"((select sum(COALESCE(quantite, 0 ))  from proforma_cmd_bl_fact.facture_detail fact_d " + 
			"join article.article art on fact_d.article=art.id " + 
			"join  article.produit prod on art.produit = prod.id " + 
			"join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
			"join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
			"where fact_d.facture in (select id from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  " + 
			"                         and registre_commerce=clt.id) " + 
			"  and  (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates' ) ) " + 
			" - " + 
			"  " + 
			"  (select   "
			+"	 	 		COALESCE(sum((select sum(COALESCE(quantite, 0)) from proforma_cmd_bl_fact.facture_avoir_detail fact_av_d  "
			+"	 			join article.article art on fact_av_d.article=art.id "
			+"	 	 		join  article.produit prod on art.produit = prod.id "
			+"	 	 		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id "
			+"	 	 		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id "
			+"	 	 		where fact_av_d.facture_avoir=fct_av.id and  (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates' ))  ), 0)"
					
			+"	 	 		from proforma_cmd_bl_fact.facture_avoir fct_av "
					
			+"	 	 		where   cast(fct_av.date as date) between CAST(:start AS date) AND CAST(:end AS date) "
			+"	 	 		                        and fct_av.registre_commerce=clt.id) "+
			"  " + 
			") " + 
			"as Pates " + 
			" " + 
			" " + 
			" " + 
			"from  client.registre_commerce clt  " + 
			" " + 
			"where wilaya='3'  and  id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date))",nativeQuery = true )

		 
	public List<Object[]> get_quantite_client_category_produit_blida(@Param("start") String start, @Param("end") String end);
	
	////////////////////////////////////////////////////////////////////////////
	
	
	@Query(value ="select prenom,nom,adresse,numero_rc, COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte_facrine" + 
			", COALESCE(dd.qte, 0 )-COALESCE(ee.qte_av, 0 ) qte_Semoule, COALESCE(ff.qte, 0 )-COALESCE(gg.qte_av, 0 ) qte_pates" + 
			"		 from client.registre_commerce clt  " + 
			"         " + 
			"		  " + 
			"		 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Farine' " + 
			"		 group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Farine' " + 
			"		 group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce " + 
			"		  " + 
			"		" + 
			"     left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Semoule' " + 
			"		 group by fct.registre_commerce ) dd on clt.id=dd.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Semoule' " + 
			"		 group by fct.registre_commerce ) ee on clt.id=ee.registre_commerce " + 
			"		      " + 
			"        " + 
			"        left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates' )  " + 
			"		 group by fct.registre_commerce ) ff on clt.id=ff.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates')" + 
			"		 group by fct.registre_commerce ) gg on clt.id=gg.registre_commerce " + 
			"		       " + 
			"        " + 
			"        " + 
			"        " + 
			"        where	 wilaya='3'  and " + 
			"				  (id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) or  " + 
			"				 id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) )" + 
			"		        ",nativeQuery = true )

		 
	public List<Object[]> get_quantite_client_category_produit_blida1(@Param("start") String start, @Param("end") String end);
	
	////////////////////////////////////////////////////////////////////////////
	
	@Query(value ="select prenom,nom,adresse,numero_rc, COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte_facrine" + 
			", COALESCE(dd.qte, 0 )-COALESCE(ee.qte_av, 0 ) qte_Semoule, COALESCE(ff.qte, 0 )-COALESCE(gg.qte_av, 0 ) qte_pates" + 
			"		 from client.registre_commerce clt  " + 
			"         " + 
			"		  " + 
			"		 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Farine' " + 
			"		 group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Farine' " + 
			"		 group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce " + 
			"		  " + 
			"		" + 
			"     left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Semoule' " + 
			"		 group by fct.registre_commerce ) dd on clt.id=dd.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Semoule' " + 
			"		 group by fct.registre_commerce ) ee on clt.id=ee.registre_commerce " + 
			"		      " + 
			"        " + 
			"        left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates' )  " + 
			"		 group by fct.registre_commerce ) ff on clt.id=ff.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates')" + 
			"		 group by fct.registre_commerce ) gg on clt.id=gg.registre_commerce " + 
			"		       " + 
			"        " + 
			"        " + 
			"        " + 
			"        where	 wilaya='4'  and " + 
			"				  (id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) or  " + 
			"				 id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) )" + 
			"		        ",nativeQuery = true )

		 
	public List<Object[]> get_quantite_client_category_produit_alger1(@Param("start") String start, @Param("end") String end);
	
	////////////////////////////////////////////////////////////////////////////
	

	@Query(value ="select prenom,nom,adresse,numero_rc, COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte_facrine" + 
			", COALESCE(dd.qte, 0 )-COALESCE(ee.qte_av, 0 ) qte_Semoule, COALESCE(ff.qte, 0 )-COALESCE(gg.qte_av, 0 ) qte_pates" + 
			"		 from client.registre_commerce clt  " + 
			"         " + 
			"		  " + 
			"		 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Farine' " + 
			"		 group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Farine' " + 
			"		 group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce " + 
			"		  " + 
			"		" + 
			"     left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Semoule' " + 
			"		 group by fct.registre_commerce ) dd on clt.id=dd.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Semoule' " + 
			"		 group by fct.registre_commerce ) ee on clt.id=ee.registre_commerce " + 
			"		      " + 
			"        " + 
			"        left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates' )  " + 
			"		 group by fct.registre_commerce ) ff on clt.id=ff.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates')" + 
			"		 group by fct.registre_commerce ) gg on clt.id=gg.registre_commerce " + 
			"		       " + 
			"        " + 
			"        " + 
			"        " + 
			"        where	 clt.wilaya!='3'  and " + 
			"				  (id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) or  " + 
			"				 id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) )" + 
			"		        ",nativeQuery = true )

		 
	public List<Object[]> get_quantite_client_category_produit_hors_blida1(@Param("start") String start, @Param("end") String end);
	
	////////////////////////////////////////////////////////////////////////////
	
	

	@Query(value ="select prenom,nom,adresse,numero_rc, COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte_facrine" + 
			", COALESCE(dd.qte, 0 )-COALESCE(ee.qte_av, 0 ) qte_Semoule, COALESCE(ff.qte, 0 )-COALESCE(gg.qte_av, 0 ) qte_pates" + 
			"		 from client.registre_commerce clt  " + 
			"         " + 
			"		  " + 
			"		 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Farine' " + 
			"		 group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Farine' " + 
			"		 group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce " + 
			"		  " + 
			"		" + 
			"     left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Semoule' " + 
			"		 group by fct.registre_commerce ) dd on clt.id=dd.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category='Semoule' " + 
			"		 group by fct.registre_commerce ) ee on clt.id=ee.registre_commerce " + 
			"		      " + 
			"        " + 
			"        left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht  " + 
			"		 from proforma_cmd_bl_fact.facture fct " + 
			"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates' )  " + 
			"		 group by fct.registre_commerce ) ff on clt.id=ff.registre_commerce " + 
			"		  " + 
			"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av " + 
			"		 from proforma_cmd_bl_fact.facture_avoir fct " + 
			"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir) " + 
			"			join article.article art on fact_d.article=art.id  " + 
			"				join  article.produit prod on art.produit = prod.id  " + 
			"				join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
			"				join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id  " + 
			"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates')" + 
			"		 group by fct.registre_commerce ) gg on clt.id=gg.registre_commerce " + 
			"		       " + 
			"        " + 
			"        " + 
			"        " + 
			"        where	 clt.wilaya!='3'  and clt.wilaya!='4'  and " + 
			"				  (id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) or  " + 
			"				 id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) )" + 
			"		        ",nativeQuery = true )

		 
	public List<Object[]> get_quantite_client_category_produit_hors_blida_alger1(@Param("start") String start, @Param("end") String end);
	
	////////////////////////////////////////////////////////////////////////////
	
	@Query(value ="select prenom,nom,adresse,numero_rc, " + 
			"((select sum(COALESCE(quantite, 0 ))  from proforma_cmd_bl_fact.facture_detail fact_d " + 
			"join article.article art on fact_d.article=art.id " + 
			"join  article.produit prod on art.produit = prod.id " + 
			"join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
			"join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
			"where fact_d.facture in (select id from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  " + 
			"                         and registre_commerce=clt.id) " + 
			"  and cat_prod.nom_category=:cat_p ) " + 
			" - " + 
			"  (select   "
			+"	 	 		COALESCE(sum((select sum(COALESCE(quantite, 0)) from proforma_cmd_bl_fact.facture_avoir_detail fact_av_d  "
			+"	 			join article.article art on fact_av_d.article=art.id "
			+"	 	 		join  article.produit prod on art.produit = prod.id "
			+"	 	 		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id "
			+"	 	 		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id "
			+"	 	 		where fact_av_d.facture_avoir=fct_av.id and cat_prod.nom_category=:cat_p )  ), 0)"
					
			+"	 	 		from proforma_cmd_bl_fact.facture_avoir fct_av "
					
			+"	 	 		where   cast(fct_av.date as date) between CAST(:start AS date) AND CAST(:end AS date) "
			+"	 	 		                        and fct_av.registre_commerce=clt.id) "+
			"  " + 
			
			") " + 
			"as farine, " + 
			" " + 
			"((select sum(COALESCE(quantite, 0 ))  from proforma_cmd_bl_fact.facture_detail fact_d " + 
			"join article.article art on fact_d.article=art.id " + 
			"join  article.produit prod on art.produit = prod.id " + 
			"join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
			"join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
			"where fact_d.facture in (select id from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  " + 
			"                         and registre_commerce=clt.id) " + 
			"  and cat_prod.nom_category='Semoule' ) " + 
			" - " + 
			"  " + 
			"  (select   "
			+"	 	 		COALESCE(sum((select sum(COALESCE(quantite, 0)) from proforma_cmd_bl_fact.facture_avoir_detail fact_av_d  "
			+"	 			join article.article art on fact_av_d.article=art.id "
			+"	 	 		join  article.produit prod on art.produit = prod.id "
			+"	 	 		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id "
			+"	 	 		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id "
			+"	 	 		where fact_av_d.facture_avoir=fct_av.id and cat_prod.nom_category='Semoule' )  ), 0)"
					
			+"	 	 		from proforma_cmd_bl_fact.facture_avoir fct_av "
					
			+"	 	 		where   cast(fct_av.date as date) between CAST(:start AS date) AND CAST(:end AS date) "
			+"	 	 		                        and fct_av.registre_commerce=clt.id) "+
			") " + 
			"as semoule, " + 
			" " + 
			"((select sum(COALESCE(quantite, 0 ))  from proforma_cmd_bl_fact.facture_detail fact_d " + 
			"join article.article art on fact_d.article=art.id " + 
			"join  article.produit prod on art.produit = prod.id " + 
			"join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
			"join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
			"where fact_d.facture in (select id from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  " + 
			"                         and registre_commerce=clt.id) " + 
			"  and  (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates' ) ) " + 
			" - " + 
			"  " + 
			"  (select   "
			+"	 	 		COALESCE(sum((select sum(COALESCE(quantite, 0)) from proforma_cmd_bl_fact.facture_avoir_detail fact_av_d  "
			+"	 			join article.article art on fact_av_d.article=art.id "
			+"	 	 		join  article.produit prod on art.produit = prod.id "
			+"	 	 		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id "
			+"	 	 		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id "
			+"	 	 		where fact_av_d.facture_avoir=fct_av.id and  (cat_prod.nom_category='Couscous' or cat_prod.nom_category='Pates' ))  ), 0)"
					
			+"	 	 		from proforma_cmd_bl_fact.facture_avoir fct_av "
					
			+"	 	 		where   cast(fct_av.date as date) between CAST(:start AS date) AND CAST(:end AS date) "
			+"	 	 		                        and fct_av.registre_commerce=clt.id) "+
			") " + 
			"as Pates " + 
			" " + 
			" " + 
			" " + 
			"from  client.registre_commerce clt  " + 
			" " + 
			"where wilaya!='3'  and  id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) limit 1",nativeQuery = true )

		 
	public List<Object[]> get_quantite_client_category_produit_hors_blida(@Param("start") String start, @Param("end") String end);
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////
	
	
	
	
////////////////////////////////////////////////////////////////////////////
	
@Query(value ="select code,concat(nom,' ',prenom),adresse,id from client.registre_commerce where"
		+ ""
		+ " id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) or "
		+ "id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date))",nativeQuery = true )


public  List<Object[]> get_client_facture_periode(@Param("start") String start, @Param("end") String end);



@Query(value ="select clt.id, " +
		"(COALESCE((select sum(COALESCE(quantite, 0 ))  from proforma_cmd_bl_fact.facture_detail fact_d " + 
		"join article.article art on fact_d.article=art.id " + 
		"join  article.produit prod on art.produit = prod.id " + 
		"join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
		"join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
		"where fact_d.facture in (select id from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  " + 
		"                         and registre_commerce=clt.id) " + 
		"  and cat_prod.nom_category=:cat_p ) , 0 )" + 
		" - " + 
		"  COALESCE((select   "
		+"	 	 		COALESCE(sum((select sum(COALESCE(quantite, 0)) from proforma_cmd_bl_fact.facture_avoir_detail fact_av_d  "
		+"	 			join article.article art on fact_av_d.article=art.id "
		+"	 	 		join  article.produit prod on art.produit = prod.id "
		+"	 	 		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id "
		+"	 	 		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id "
		+"	 	 		where fact_av_d.facture_avoir=fct_av.id and cat_prod.nom_category=:cat_p )  ), 0)"
				
		+"	 	 		from proforma_cmd_bl_fact.facture_avoir fct_av "
				
		+"	 	 		where   cast(fct_av.date as date) between CAST(:start AS date) AND CAST(:end AS date) "
		+"	 	 		                        and fct_av.registre_commerce=clt.id), 0 ) "+
		"  " + 
		
		") " + 
		"as qte ,"
		+"(COALESCE((select sum(COALESCE(montant_ht, 0 ))  from proforma_cmd_bl_fact.facture_detail fact_d " + 
		"join article.article art on fact_d.article=art.id " + 
		"join  article.produit prod on art.produit = prod.id " + 
		"join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
		"join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
		"where fact_d.facture in (select id from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  " + 
		"                         and registre_commerce=clt.id) " + 
		"  and cat_prod.nom_category=:cat_p ) , 0 )" + 
		" - " + 
		"  COALESCE((select   "
		+"	 	 		COALESCE(sum((select sum(COALESCE(montant_ht, 0)) from proforma_cmd_bl_fact.facture_avoir_detail fact_av_d  "
		+"	 			join article.article art on fact_av_d.article=art.id "
		+"	 	 		join  article.produit prod on art.produit = prod.id "
		+"	 	 		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id "
		+"	 	 		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id "
		+"	 	 		where fact_av_d.facture_avoir=fct_av.id and cat_prod.nom_category=:cat_p )  ), 0)"
				
		+"	 	 		from proforma_cmd_bl_fact.facture_avoir fct_av "
				
		+"	 	 		where   cast(fct_av.date as date) between CAST(:start AS date) AND CAST(:end AS date) "
		+"	 	 		                        and fct_av.registre_commerce=clt.id), 0 ) "+
		"  " + 
		
		") " + 
		"as m_ht "
		+ ""
		+ ""
		+ "from client.registre_commerce clt where"
		+ ""
		+ " id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) or "
		+ "id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date))",nativeQuery = true )


public  List<Object[]> get_qte_client_souscategorie(@Param("start") String start, @Param("end") String end, @Param("cat_p") String cat_p);

//////////////////////////////////////////////////////////////////////////////



@Query(value ="select clt.id ,COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte,COALESCE(bb.montant_ht, 0 )-COALESCE(cc.montant_ht_av, 0 ) montant_ht" + 
		" from client.registre_commerce clt " + 
		" " + 
		" left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht " + 
		" from proforma_cmd_bl_fact.facture fct" + 
		" join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)" + 
		"	join article.article art on fact_d.article=art.id " + 
		"		join  article.produit prod on art.produit = prod.id " + 
		"		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
		"		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
		" where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category=:cat_p" + 
		" group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce" + 
		" " + 
		"  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av" + 
		" from proforma_cmd_bl_fact.facture_avoir fct" + 
		" join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)" + 
		"	join article.article art on fact_d.article=art.id " + 
		"		join  article.produit prod on art.produit = prod.id " + 
		"		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id " + 
		"		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
		" where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)   and cat_prod.nom_category=:cat_p" + 
		" group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce" + 
		" " + 
		"	where	 " + 
		"		  id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)) or " + 
		"		 id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date))" + 
		"         order by qte_av",nativeQuery = true )


public  List<Object[]> get_qte_client_souscategorie1(@Param("start") String start, @Param("end") String end, @Param("cat_p") String cat_p);

//////////////////////////////////////////////////////////////////////////////
@Query(value ="select extract(year from cast(a.date as date )) as year ,extract(month from cast(a.date as date )) as month " + 
",(sum(fct_d.quantite)- COALESCE(b.qte_avoir,0)) qte,(sum(fct_d.montant_ht_net)-COALESCE(b.ht_avoir,0)-COALESCE(c.ht_rest,0))  ht " + 
" ,COALESCE(c.ht_rest,0) ht_rest " + 
"from proforma_cmd_bl_fact.facture a " + 
"join proforma_cmd_bl_fact.facture_detail fct_d on fct_d.facture = a.id " + 
" " + 
"left join " + 
"(select extract(year from cast(fct_av.date as date )) as year_avoir,extract(month from cast(fct_av.date as date )) as month_avoir,sum(fct_av_d.quantite) qte_avoir ,sum(fct_av_d.montant_ht_net) ht_avoir  " + 
"          from proforma_cmd_bl_fact.facture_avoir  fct_av " + 
"          join proforma_cmd_bl_fact.facture_avoir_detail fct_av_d ON fct_av_d.facture_avoir = fct_av.id " + 
"           " + 
"           " + 
"          group by year_avoir,month_avoir) b  " + 
"           " + 
"          on (b.year_avoir=extract(year from cast(a.date as date )) and b.month_avoir=extract(month from cast(a.date as date )))  " + 
" " + 
" " + 
" left join " + 
" (select extract(year from cast(fct_rest.date as date )) as year_rest,extract(month from cast(fct_rest.date as date )) as month_rest ,sum(fct_rest.montant_ht) ht_rest  " + 
"          from proforma_cmd_bl_fact.facture_ristourne fct_rest   " + 
"            " + 
"         group by year_rest,month_rest) c  " + 
" " + 
"          on (c.year_rest=extract(year from cast(a.date as date )) and c.month_rest=extract(month from cast(a.date as date )))  " + 
" " + 
" " + 
" where cast(a.date as date ) between CAST(:start AS date) AND CAST(:end AS date) " + 
"                " + 
"                group by year,month,b.qte_avoir, b.ht_avoir,c.ht_rest " ,nativeQuery = true )


public List<Object[]> qte_quantite_val_year_month(@Param("start") String start, @Param("end") String end);

/////////////////////////////////////////////////////////////////////////////////////

@Query(value ="select distinct(cat_prod.nom_category),sum(COALESCE(fact.ht,0))-sum(COALESCE(fact_av.ht_av,0))-sum(COALESCE(fact_rest.ht_rest,0))ht from  " + 
" " + 
"article.article art       " + 
" join  article.produit prod on art.produit = prod.id  " + 
"		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
"		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
"		 " + 
"left join ( " + 
"(select  sum(fct_d.montant_ht_net) ht,fct_d.article  " + 
"          from proforma_cmd_bl_fact.facture  fct " + 
"          join proforma_cmd_bl_fact.facture_detail fct_d ON fct_d.facture = fct.id " + 
" where cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)   group by fct_d.article) " + 
") fact on (fact.article=art.id)  " + 
"  " + 
" left join ( " + 
"(select  sum(fct_av_d.montant_ht_net) ht_av,fct_av_d.article  " + 
"          from proforma_cmd_bl_fact.facture_avoir  fct_av " + 
"          join proforma_cmd_bl_fact.facture_avoir_detail fct_av_d ON fct_av_d.facture_avoir = fct_av.id " + 
" where cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)   group by fct_av_d.article) " + 
") fact_av on (fact_av.article=art.id)  " + 
"  " + 
"  " + 
" left join " + 
"(select  sum(fct_rest_d.montant_ht) ht_rest,fct_rest_d.article " + 
"          from proforma_cmd_bl_fact.facture_ristourne fct_rest   " + 
"           join proforma_cmd_bl_fact.facture_ristourne_detail fct_rest_d on fct_rest.id = fct_rest_d.facture_ristourne " + 
"       where cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)   group by fct_rest_d.article " + 
") fact_rest on (fact_rest.article=art.id) " + 
" group by distinct(cat_prod.nom_category)  order by ht desc" + 
"           " ,nativeQuery = true )


public List<Object[]> ca_par_produit(@Param("start") String start, @Param("end") String end);

/////////////////////////////////////////////////////////////////////////////////////

@Query(value ="select distinct(cat_prod.nom_category),sum(COALESCE(fact.ht,0))-sum(COALESCE(fact_av.ht_av,0))-sum(COALESCE(fact_rest.ht_rest,0))ht from  " + 
" " + 
"article.article art       " + 
" join  article.produit prod on art.produit = prod.id  " + 
"		join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id  " + 
"		join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id " + 
"		 " + 
"left join ( " + 
"(select  sum(fct_d.quantite) ht,fct_d.article  " + 
"          from proforma_cmd_bl_fact.facture  fct " + 
"          join proforma_cmd_bl_fact.facture_detail fct_d ON fct_d.facture = fct.id " + 
" where cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)   group by fct_d.article) " + 
") fact on (fact.article=art.id)  " + 
"  " + 
" left join ( " + 
"(select  sum(fct_av_d.quantite) ht_av,fct_av_d.article  " + 
"          from proforma_cmd_bl_fact.facture_avoir  fct_av " + 
"          join proforma_cmd_bl_fact.facture_avoir_detail fct_av_d ON fct_av_d.facture_avoir = fct_av.id " + 
" where cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)   group by fct_av_d.article) " + 
") fact_av on (fact_av.article=art.id)  " + 
"  " + 
"  " + 
" left join " + 
"(select  sum(fct_rest_d.quantite) ht_rest,fct_rest_d.article " + 
"          from proforma_cmd_bl_fact.facture_ristourne fct_rest   " + 
"           join proforma_cmd_bl_fact.facture_ristourne_detail fct_rest_d on fct_rest.id = fct_rest_d.facture_ristourne " + 
"       where cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)   group by fct_rest_d.article " + 
") fact_rest on (fact_rest.article=art.id) " + 
"where cat_prod.nom_category!='Pain' and cat_prod.nom_category!='Transport'  group by distinct(cat_prod.nom_category)  order by ht desc" + 
"           " ,nativeQuery = true )


public List<Object[]> qte_par_produit(@Param("start") String start, @Param("end") String end);

/////////////////////////////////////////////////////////////////////////////////////
@Query(value ="select clt.nom ,COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte,COALESCE(bb.montant_ht, 0 )-COALESCE(cc.montant_ht_av, 0 )-COALESCE(dd.montant_rist, 0 )montant_ht  " + 
"		 from client.registre_commerce clt " + 
"		" + 
"		 left join ( select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht_net, 0 ))as montant_ht " + 
"		 from proforma_cmd_bl_fact.facture fct" + 
"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)" + 
"		" + 
"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)     " + 
"		 group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce" + 
"		" + 
"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht_net, 0 ))as montant_ht_av" + 
"		 from proforma_cmd_bl_fact.facture_avoir fct" + 
"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)" + 
"			" + 
"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)     " + 
"		 group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce" + 
"	" + 
"		 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_rist" + 
"		 from proforma_cmd_bl_fact.facture_ristourne fct" + 
"		 join proforma_cmd_bl_fact.facture_ristourne_detail fact_d on (fct.id=fact_d.facture_ristourne)" + 
"			" + 
"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)     " + 
"		 group by fct.registre_commerce ) dd on clt.id=dd.registre_commerce" + 
"	" + 
"			where	 " + 
"				  id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  ) or " + 
"				 id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  )" + 
"		         order by montant_ht desc limit 20"  ,nativeQuery = true )


public List<Object[]> get_ca_rc(@Param("start") String start, @Param("end") String end);

/////////////////////////////////////////////////////////////////////////////////////

@Query(value ="select clt.nom ,COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte,COALESCE(bb.montant_ht, 0 )-COALESCE(cc.montant_ht_av, 0 )-COALESCE(dd.montant_rist, 0 )montant_ht  " + 
"		 from client.registre_commerce clt " + 
"		" + 
"		 left join ( select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht_net, 0 ))as montant_ht " + 
"		 from proforma_cmd_bl_fact.facture fct" + 
"		 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)" + 
"		" + 
"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)     " + 
"		 group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce" + 
"		" + 
"		  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht_net, 0 ))as montant_ht_av" + 
"		 from proforma_cmd_bl_fact.facture_avoir fct" + 
"		 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)" + 
"			" + 
"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)     " + 
"		 group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce" + 
"	" + 
"		 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_rist" + 
"		 from proforma_cmd_bl_fact.facture_ristourne fct" + 
"		 join proforma_cmd_bl_fact.facture_ristourne_detail fact_d on (fct.id=fact_d.facture_ristourne)" + 
"			" + 
"		 where  cast(fct.date as date) between CAST(:start AS date) AND CAST(:end AS date)     " + 
"		 group by fct.registre_commerce ) dd on clt.id=dd.registre_commerce" + 
"	" + 
"			where	 " + 
"				  id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  ) or " + 
"				 id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between CAST(:start AS date) AND CAST(:end AS date)  )" + 
"		         order by qte desc limit 20"  ,nativeQuery = true )


public List<Object[]> get_qte_rc(@Param("start") String start, @Param("end") String end);

/////////////////////////////////////////////////////////////////////////////////////
	


@Query(value ="select wilaya.designation wilaya,cat.nom_category, prenom,nom,adresse,numero_rc, COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte_facrine  " + 
		"			, COALESCE(dd.qte, 0 )-COALESCE(ee.qte_av, 0 ) qte_Semoule " + 
		"					 from client.registre_commerce clt    			           " + 
		"					    join client.category_client cat on cat.id = clt.category_client" + 
		"						join static_data.wilaya ON wilaya.id = clt.wilaya" + 
		"					 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht    " + 
		"					 from proforma_cmd_bl_fact.facture fct   " + 
		"					 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start AS date) AND  CAST(:end AS date)   and cat_prod.nom_category='Farine'   " + 
		"					 group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce   					    " + 
		"					  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av   " + 
		"					 from proforma_cmd_bl_fact.facture_avoir fct   " + 
		"					 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start AS date) AND  CAST(:end AS date)   and cat_prod.nom_category='Farine'   " + 
		"					 group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce   " + 
		"		  " + 
		"			     left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht    " + 
		"					 from proforma_cmd_bl_fact.facture fct   " + 
		"					 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start AS date) AND  CAST(:end AS date)   and cat_prod.nom_category='Semoule'   " + 
		"					 group by fct.registre_commerce ) dd on clt.id=dd.registre_commerce   " + 
		"					    " + 
		"					  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av   " + 
		"					 from proforma_cmd_bl_fact.facture_avoir fct   " + 
		"					 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start AS date) AND  CAST(:end AS date)   and cat_prod.nom_category='Semoule'   " + 
		"					 group by fct.registre_commerce ) ee on clt.id=ee.registre_commerce   " + 
		"" + 
		"			        where	  " + 
		"							  (clt.id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between  CAST(:start AS date) AND CAST(:end AS date)) or    " + 
		"							clt. id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between  CAST(:start AS date) AND CAST(:end AS date)) )  " + 
		"					        ",nativeQuery = true )

	 
public List<Object[]> get_quantite_client_category_wilaya_semoule_farine(@Param("start") String start, @Param("end") String end);

////////////////////////////////////////////////////////////////////////////

@Query(value ="select wilaya.designation wilaya,cat.nom_category, prenom,nom,adresse,numero_rc, COALESCE(bb.qte, 0 )-COALESCE(cc.qte_av, 0 ) qte_facrine  " + 
		"			, COALESCE(dd.qte, 0 )-COALESCE(ee.qte_av, 0 ) qte_Semoule ," + 
		"			  COALESCE(far_2025.qte, 0) - COALESCE(far_av_2025.qte_av, 0) AS qte_farine_2025," + 
		"			   COALESCE(sem_2025.qte, 0) - COALESCE(sem_av_2025.qte_av, 0) AS qte_semoule_2025" + 
		"					 from client.registre_commerce clt    			           " + 
		"					    join client.category_client cat on cat.id = clt.category_client" + 
		"						join static_data.wilaya ON wilaya.id = clt.wilaya" + 
		"					 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht    " + 
		"					 from proforma_cmd_bl_fact.facture fct   " + 
		"					 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start AS date) AND  CAST(:end AS date)   and cat_prod.nom_category='Farine'   " + 
		"					 group by fct.registre_commerce ) bb on clt.id=bb.registre_commerce   					    " + 
		"					  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av   " + 
		"					 from proforma_cmd_bl_fact.facture_avoir fct   " + 
		"					 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start AS date) AND  CAST(:end AS date)   and cat_prod.nom_category='Farine'   " + 
		"					 group by fct.registre_commerce ) cc on clt.id=cc.registre_commerce   " + 
		"		  " + 
		"			     left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht    " + 
		"					 from proforma_cmd_bl_fact.facture fct   " + 
		"					 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start AS date) AND  CAST(:end AS date)   and cat_prod.nom_category='Semoule'   " + 
		"					 group by fct.registre_commerce ) dd on clt.id=dd.registre_commerce   " + 
		"					    " + 
		"					  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av   " + 
		"					 from proforma_cmd_bl_fact.facture_avoir fct   " + 
		"					 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start AS date) AND  CAST(:end AS date)   and cat_prod.nom_category='Semoule'   " + 
		"					 group by fct.registre_commerce ) ee on clt.id=ee.registre_commerce   " + 
		"" + 
		"" + 
		"	 left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht    " + 
		"					 from proforma_cmd_bl_fact.facture fct   " + 
		"					 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start1 AS date) AND  CAST(:end1 AS date)   and cat_prod.nom_category='Farine'   " + 
		"					 group by fct.registre_commerce ) far_2025 on clt.id=far_2025.registre_commerce   				 " + 
		"					  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av   " + 
		"					 from proforma_cmd_bl_fact.facture_avoir fct   " + 
		"					 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start1 AS date) AND  CAST(:end1 AS date)   and cat_prod.nom_category='Farine'   " + 
		"					 group by fct.registre_commerce ) far_av_2025 on clt.id=far_av_2025.registre_commerce   " + 
		"" + 
		"			     left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 ))as qte,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht    " + 
		"					 from proforma_cmd_bl_fact.facture fct   " + 
		"					 join proforma_cmd_bl_fact.facture_detail fact_d on (fct.id=fact_d.facture)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start1 AS date) AND  CAST(:end1 AS date)   and cat_prod.nom_category='Semoule'   " + 
		"					 group by fct.registre_commerce ) sem_2025 on clt.id=sem_2025.registre_commerce   " + 
		"					    " + 
		"					  left join (select fct.registre_commerce,sum(COALESCE(quantite, 0 )) as qte_av,sum(COALESCE(fact_d.montant_ht, 0 ))as montant_ht_av   " + 
		"					 from proforma_cmd_bl_fact.facture_avoir fct   " + 
		"					 join proforma_cmd_bl_fact.facture_avoir_detail fact_d on (fct.id=fact_d.facture_avoir)   " + 
		"						join article.article art on fact_d.article=art.id    " + 
		"							join  article.produit prod on art.produit = prod.id    " + 
		"							join article.sous_category_produit sous_prod on prod.sous_category_produit = sous_prod.id    " + 
		"							join article.category_produit cat_prod on sous_prod.category_produit = cat_prod.id    " + 
		"					 where  cast(fct.date as date) between  CAST(:start1 AS date) AND  CAST(:end1 AS date)   and cat_prod.nom_category='Semoule'   " + 
		"					 group by fct.registre_commerce ) sem_av_2025 on clt.id=sem_av_2025.registre_commerce   " + 
		"" + 
		"	 " + 
		"			        where	  " + 
		"							  (clt.id in (select registre_commerce from proforma_cmd_bl_fact.facture where  cast(date as date) between  CAST(:start AS date) AND  CAST(:end1 AS date)) or    " + 
		"							clt. id in (select registre_commerce from proforma_cmd_bl_fact.facture_avoir where  cast(date as date) between  CAST(:start AS date) AND  CAST(:end1 AS date)) )  " + 
		"					        ",nativeQuery = true )

public List<Object[]> get_quantite_client_category_wilaya_semoule_farine1(@Param("start") String start, @Param("end") String end,@Param("start1") String start1, @Param("end1") String end1);

////////////////////////////////////////////////////////////////////////////



	/************ FODHIL ********************/
	
}
