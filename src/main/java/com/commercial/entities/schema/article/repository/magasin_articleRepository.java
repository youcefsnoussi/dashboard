package com.commercial.entities.schema.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.magasin_article;

public interface magasin_articleRepository extends JpaRepository<magasin_article,Long>{
	
	@Query( " FROM magasin_article mag_art "
			
			+ " WHERE mag_art.article = :article ")
	
	public List<magasin_article> list_magasin_by_article(@Param("article") article article);
	
	@Query(   " SELECT magasin "
			
			+ " FROM magasin_article mag_art"
			
			+ " WHERE mag_art.article = :article ")
	
	public List<Magasin> get_magasin_by_article(@Param("article") article article);
	
}
