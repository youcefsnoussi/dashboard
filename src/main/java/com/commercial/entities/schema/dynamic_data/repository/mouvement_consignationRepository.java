package com.commercial.entities.schema.dynamic_data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.dynamic_data.mouvement_consignation;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;

public interface mouvement_consignationRepository extends JpaRepository<mouvement_consignation, Long>{
	
	
	
	//----------------------------------------------------------------
	
	@Query("FROM mouvement_consignation "
			+ "WHERE registre_commerce = :rc AND article_consignation = :art_cons "
			+ "ORDER BY CAST(date as date), id ")
	public List<mouvement_consignation> recalculeSoldArtConsignationRc( @Param("rc") registre_commerce rc, 
			@Param("art_cons") article art_cons);
	
	//-------------------------------------------------------------------------------------------------------
	
	@Query("FROM mouvement_consignation " +
		   "WHERE facture = :fact AND article_consignation = :art_cons " +
		   "ORDER BY id DESC")
	public List<mouvement_consignation> get_mvm_by_fact_artC( @Param("fact") facture fact, 
			@Param("art_cons") article art_cons);
	
	//-------------------------------------------------------------------------------------------------------
	
	@Query("FROM mouvement_consignation " +
		   "WHERE facture = :fact")
	public List<mouvement_consignation> list_all_mvmC_by_fact( @Param("fact") facture fact);
	
}
