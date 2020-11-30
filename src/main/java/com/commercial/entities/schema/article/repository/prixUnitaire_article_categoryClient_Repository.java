package com.commercial.entities.schema.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.client.category_client;

public interface prixUnitaire_article_categoryClient_Repository extends JpaRepository<prixUnitaire_article_categoryClient, Long>{
	
	@Query( " FROM prixUnitaire_article_categoryClient prix_u_c "
			
			+ " WHERE prix_u_c.article = :art ")
	
	public List<prixUnitaire_article_categoryClient>  get_prices_by_CatClient(@Param("art") article art);
	
	//-------------------------------------------------------------------
	
	@Query( " FROM prixUnitaire_article_categoryClient prix_u_c "
			
			+ " WHERE category_client = :cat_client AND prix !=(-1) ")
	
	public List<prixUnitaire_article_categoryClient>  get_articles_by_CatClient(@Param("cat_client") category_client cat_client);
	
	//-------------------------------------------------------------------
	
	@Query( " SELECT prix"
			
			+ " FROM prixUnitaire_article_categoryClient prix_u_c "
			
			+ " WHERE category_client = :cat_client "
			
			+ " AND article = :article")
	
	public double  get_prix_articles_by_CatClient(@Param("cat_client") category_client cat_client, @Param("article") article article);
	
}
