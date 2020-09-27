package com.commercial.entities.schema.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.produit;
import com.commercial.entities.schema.article.sous_category_produit;

public interface produitRepository extends JpaRepository<produit, Long>{
	
	@Query( " FROM produit prod "
			
			+ " WHERE prod.sous_category_produit = :sous_cat ")
	
	public List<produit>  get_prod_by_sousCat(@Param("sous_cat") sous_category_produit sous_cat);
	
}
