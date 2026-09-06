package com.commercial.entities.schema.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.initialisation_stock_journalier;
import com.commercial.entities.schema.article.category_produit;

public interface initialisation_stock_journalierRepository extends JpaRepository<initialisation_stock_journalier, Long> {

    @Query(" FROM initialisation_stock_journalier st WHERE st.date = :date ORDER BY st.id DESC ")
    List<initialisation_stock_journalier> findByDateOrderByIdDesc(@Param("date") String date);

    initialisation_stock_journalier findTopByCategoryAndDateOrderByIdDesc(category_produit category, String date);
}
