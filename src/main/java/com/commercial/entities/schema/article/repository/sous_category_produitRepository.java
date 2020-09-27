package com.commercial.entities.schema.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.sous_category_produit;

public interface sous_category_produitRepository extends JpaRepository<sous_category_produit, Long>{
	
	@Query( " FROM sous_category_produit scp "
			
			+ " WHERE scp.category_produit = :cat ")
	
	public List<sous_category_produit>  get_sousCat_by_cat(@Param("cat") category_produit cat);
	
}
