package com.commercial.entities.schema.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.reduction_client_prixU_article;

public interface reduction_client_prixU_articleRepository extends JpaRepository<reduction_client_prixU_article, Long> {
	
@Query( 	"FROM reduction_client_prixU_article red "
			
			+ "WHERE active = 'TRUE' "
			
			+ "AND article = :art "
			
			+ "AND client = :clt "
			
			+ "AND ( CAST(:today AS date) BETWEEN CAST(date_debut AS date) AND CAST(date_fin AS date) ) ")
	
	public reduction_client_prixU_article get_reduction_by_clt_art(@Param("clt") client clt, @Param("art") article art, @Param("today") String today);
	
}
