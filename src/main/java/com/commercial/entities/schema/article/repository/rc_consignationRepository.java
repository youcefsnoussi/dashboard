package com.commercial.entities.schema.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.rc_consignation;
import com.commercial.entities.schema.client.registre_commerce;

public interface rc_consignationRepository extends JpaRepository<rc_consignation, Long>{
	
	@Query("FROM rc_consignation "
			+ "WHERE registre_commerce = :rc AND article_consignation = :art_cons")
	public rc_consignation getRcConsignation( @Param("rc") registre_commerce rc, @Param("art_cons") article art_cons);
	
}
