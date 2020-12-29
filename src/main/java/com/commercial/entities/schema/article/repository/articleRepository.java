package com.commercial.entities.schema.article.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.emballage_produit;
import com.commercial.entities.schema.article.pesage_produit;
import com.commercial.entities.schema.article.produit;

public interface articleRepository extends JpaRepository<article, Long>{
	
	@Query( " FROM article art "
			
			+ " WHERE art.code = :code ")
	
	public article  if_code_art_exist(@Param("code") String code_art);
	
	@Query( " FROM article art "
			
			+ " WHERE art.produit = :produit "
			+ " AND art.emballage_produit = :emb_produit "
			+ " AND art.pesage_produit = :pes_produit"
			+ " AND art.code_comptable = :code_comptable"
			+ " AND art.subvension = :sub")
	
	public article  if_art_same_spec_exist(@Param("produit") produit produit, @Param("emb_produit") emballage_produit emb_produit,
											@Param("pes_produit") pesage_produit pes_produit, @Param("code_comptable") String code_comptable,
											@Param("sub") boolean subvension);
	
	@Query( " FROM article art "
			
			+ " ORDER BY art.produit.designation ASC ")
	
	public List<article>  select_articles_ordered();
	
}
