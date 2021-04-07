package com.commercial.entities.schema.article.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.category_produit;

public interface category_produitRepository extends JpaRepository<category_produit, Long>{
	
	@Query(   " FROM category_produit cat_prod "
			
			+ " WHERE cat_prod.nom_category = :nom_cat_prod ")
	
	public category_produit  get_cat_prod_by_name(@Param("nom_cat_prod") String nom_cat_prod);
	
}
