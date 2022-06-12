package com.commercial.entities.schema.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.article_consignation_relation;
import com.commercial.entities.schema.client.registre_commerce;

public interface article_consignation_relationRepository extends JpaRepository<article_consignation_relation, Long>{
	
	List<article_consignation_relation> findByArticle(article art);
	
	@Query("SELECT division FROM article_consignation_relation "+
			"WHERE article = :art AND article_consignation = :art_cons ")
	public Double getDivisioner(@Param("art") article art, @Param("art_cons") article art_cons);
	
	@Query("FROM article_consignation_relation "+
			"WHERE article = :art ")
	public List<article_consignation_relation> if_art_consigned(@Param("art") article art);
	
	@Query("FROM article_consignation_relation art_rel " +
			"JOIN rc_consignation rcc ON rcc.article_consignation = art_rel.article_consignation " +
			"WHERE art_rel.article = :art AND rcc.registre_commerce = :rc AND rcc.registre_commerce.consignation = 'true' ")
	public List<article_consignation_relation> getArticleConsignationWithRc(@Param("art") article art, 
			@Param("rc") registre_commerce rc);
	
}
