package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.commande_detail;

public interface commande_detailRepository extends JpaRepository<commande_detail, Long> {
	
	@Query( value=
			"SELECT code, libelle, SUM(quantite) quantite, nom_unite_mesure FROM proforma_cmd_bl_fact.commande_detail cmd_d " + 
			"JOIN proforma_cmd_bl_fact.commande cmd ON cmd.id = commande " + 
			"JOIN article.article art ON art.id  = article " + 
			"JOIN proforma_cmd_bl_fact.bon_livraison bl ON bl.commande = cmd.id " + 
			"JOIN static_data.unite_mesure um ON um.id = unite_mesure " + 
			"WHERE CAST(cmd.date as date) BETWEEN CAST(?2 AS date) AND CAST(?3 AS date) " +
			"AND registre_commerce  = ?1 AND cloturer='t' and cancel='f' " + 
			"GROUP BY libelle, code, nom_unite_mesure " + 
			"ORDER BY code", nativeQuery=true) 
	public List<Object[]> get_commande_details (Long id_rc, String start, String end);
	
	//------------------------------------------------------------------------------
	
	@Query( value=
			"SELECT code, libelle, SUM(quantite) quantite, nom_unite_mesure, cmd.date FROM proforma_cmd_bl_fact.commande_detail cmd_d " + 
			"JOIN proforma_cmd_bl_fact.commande cmd ON cmd.id = commande " + 
			"JOIN article.article art ON art.id  = article " + 
			"JOIN proforma_cmd_bl_fact.bon_livraison bl ON bl.commande = cmd.id " + 
			"JOIN static_data.unite_mesure um ON um.id = unite_mesure " + 
			"WHERE CAST(cmd.date as date) BETWEEN CAST(?2 AS date) AND CAST(?3 AS date) " +
			"AND registre_commerce  = ?1 AND cloturer='t' and cancel='f' " + 
			"GROUP BY cmd.date, libelle, code, nom_unite_mesure " + 
			"ORDER BY cmd.date", nativeQuery=true) 
	public List<Object[]> get_commande_details_dates (Long id_rc, String start, String end);
	
	/*
	@Query( "SELECT article.code, article.libelle, SUM(quantite), unite_mesure.nom_unite_mesure, cmd_d.commande.date "+
			"FROM commande_detail cmd_d, bon_livraison bl " + 
			//"JOIN commande cmd ON cmd.id = commande " + 
			//"JOIN article art ON art.id  = article " + 
			//"JOIN bon_livraison bl ON bon_livraison.commande = cmd_d.commande " + 
			//"JOIN unite_mesure um ON um.id = unite_mesure " + 
			"WHERE CAST(cmd_d.commande.date as date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			//"AND bl.registre_commerce  = :rc "+
			"AND cmd_d.commande.cloturer='true' "+
			//"AND bl.cancel='false' " + 
			"GROUP BY cmd_d.commande.date, article.libelle, article.code, unite_mesure.nom_unite_mesure " + 
			"ORDER BY cmd_d.commande.date") 
	public List<Object[]> get_commande_details_dates (@Param("rc") registre_commerce rc, @Param("start") String start, @Param("end") String end);
	*/
	//--------------------------------------------------------------------------------------------
	/*
	@Query( "SELECT code, libelle, SUM(quantite), nom_unite_mesure FROM commande_detail cmd_d " + 
			"JOIN commande cmd ON cmd.id = commande " + 
			"JOIN article art ON art.id  = article " + 
			"JOIN bon_livraison bl ON bl.commande = cmd.id " + 
			"JOIN unite_mesure um ON um.id = unite_mesure " + 
			"WHERE CAST(cmd.date as date) between :start AND :end "+
			"AND registre_commerce  = :rc AND cloturer='t' AND cancel='f' " + 
			"GROUP BY libelle, code, nom_unite_mesure " + 
			"ORDER BY code")
	public List<Object[]> get_commande_details (@Param("rc") registre_commerce rc, @Param("start") String start, @Param("end") String end);
	*/
}
