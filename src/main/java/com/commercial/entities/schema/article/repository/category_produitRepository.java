package com.commercial.entities.schema.article.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.category_produit;

public interface category_produitRepository extends JpaRepository<category_produit, Long>{
	
	@Query(   " FROM category_produit cat_prod "
			
			+ " WHERE cat_prod.nom_category = :nom_cat_prod ")
	
	public category_produit  get_cat_prod_by_name(@Param("nom_cat_prod") String nom_cat_prod);
	
	//-------------------------------------------------------------------------------------------
	
	@Query( " SELECT fct_d.facture.registre_commerce.wilaya.designation, " +
			" fct_d.article.produit.sous_category_produit.category_produit.nom_category, " +
			" fct_d.article.subvension, " + 
			"" + 
			"	SUM(fct_d.quantite) - " + 
			"	COALESCE ((" + 
			"" + 
			"		SELECT SUM(fct_av_d.quantite) from facture_avoir_detail fct_av_d " + 
			"" + 
			"		WHERE CAST(fct_av_d.facture_avoir.date AS date) BETWEEN CAST(:start AS date) "+
			"		AND CAST(:end AS date) " + 
			"		AND fct_av_d.article.produit.sous_category_produit.category_produit = :cat " +
			"		AND fct_av_d.facture_avoir.registre_commerce.wilaya = fct_d.facture.registre_commerce.wilaya " + 
			"			" + 
			"	),0) " + 
			"" + 
			"FROM facture_detail fct_d " + 
			"" + 
			"WHERE CAST(fct_d.facture.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"AND fct_d.article.produit.sous_category_produit.category_produit = :cat " +   
			/*"AND fct_d.article.subvension = :sub " +   cp.id = '1' => farine / cp.id = '2' => semoule / p.id = '5' => son */
			""+
			"GROUP BY fct_d.facture.registre_commerce.wilaya.designation, "+
			"fct_d.article.produit.sous_category_produit.category_produit.nom_category, "+
			"fct_d.facture.registre_commerce.wilaya, "+
			"fct_d.article.subvension " +
			""+
			"ORDER BY fct_d.article.produit.sous_category_produit.category_produit.nom_category")
	
	public List<Object[]> get_quantite_vendu_by_wilaya_fact(@Param("start") String start, @Param("end") String end,
								@Param("cat") category_produit cat_prod); 
	
	//-----------------------------------------------------------------------------------------------------------------------
	
	@Query(value=
			"SELECT CONCAT(rc.nom, ' ',rc.prenom ) ,adresse, w.designation wilaya, cp.nom_category, scp.nom_sous_category, subvension, " + 
			"" + 
			"	SUM(quantite) - " + 
			"	COALESCE (( " + 
			"" + 
			"		SELECT SUM(quantite) from proforma_cmd_bl_fact.facture_avoir_detail fct_av_d " + 
			"		JOIN proforma_cmd_bl_fact.facture_avoir fct_av ON fct_av.id = facture_avoir " + 
			"		JOIN client.registre_commerce rc ON rc.id = registre_commerce" + 
			"		JOIN static_data.wilaya w_av ON w_av.id = rc.wilaya " + 
			"" + 
			"		JOIN article.article art ON art.id = fct_av_d.article " + 
			"		JOIN article.produit prd ON prd.id = art.produit " + 
			"		JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit " + 
			"		JOIN article.category_produit cp ON cp.id = scp.category_produit " + 
			"" + 
			"		where CAST(fct_av.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"		AND cp.id = :id_cat_prod " + 
			"			" + 
			"	),0) quantite " + 
			"" + 
			"FROM proforma_cmd_bl_fact.facture_detail fct_d " + 
			"" + 
			"JOIN proforma_cmd_bl_fact.facture fct ON fct.id = facture " + 
			"JOIN client.registre_commerce rc ON rc.id = registre_commerce " + 
			"JOIN static_data.wilaya w ON w.id = rc.wilaya " + 
			"" + 
			"JOIN article.article art ON art.id = fct_d.article " + 
			"JOIN article.produit prd ON prd.id = art.produit " + 
			"JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit " + 
			"JOIN article.category_produit cp ON cp.id = scp.category_produit " + 
			"" + 
			"WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"AND cp.id = :id_cat_prod  " + 
			"" + 
			"GROUP BY CONCAT(rc.nom, ' ',rc.prenom ) ,adresse, w.designation , cp.nom_category, scp.nom_sous_category, subvension " + 
			"" + 
			"UNION " + 
			"" + 
			"SELECT CONCAT(rc.nom, ' ',rc.prenom ) ,adresse, w.designation wilaya, cp.nom_category, scp.nom_sous_category, subvension, " + 
			"" + 
			"	SUM(quantite)  quantite " + 
			"" + 
			"FROM proforma_cmd_bl_fact.bon_livraison_facture_detail fct_d " + 
			"" + 
			"JOIN proforma_cmd_bl_fact.bon_livraison_facture fct ON fct.id = bon_livraison_facture " + 
			"JOIN client.registre_commerce rc ON rc.id = registre_commerce " + 
			"JOIN static_data.wilaya w ON w.id = rc.wilaya " + 
			"" + 
			"JOIN article.article art ON art.id = fct_d.article " + 
			"JOIN article.produit pro on art.produit=pro.id " + 
			"" + 
			"JOIN article.sous_category_produit sous_cat ON pro.sous_category_produit=sous_cat.id " + 
			"JOIN article.sous_category_produit scp ON scp.id =pro.sous_category_produit " + 
			"JOIN article.category_produit cp ON cp.id = scp.category_produit " + 
			"" + 
			"WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " + 
			"AND cp.id = :id_cat_prod AND  fct.cancel='false' " + 
			"" + 
			"GROUP BY CONCAT(rc.nom, ' ',rc.prenom ) ,adresse, w.designation, cp.nom_category, scp.nom_sous_category, subvension",
			nativeQuery=true)
	public List<Object[]> get_quantite_vendu_by_wilaya_fact_detail(String start, String end, long id_cat_prod); 
	
}
