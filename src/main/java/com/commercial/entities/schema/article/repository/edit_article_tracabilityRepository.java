package com.commercial.entities.schema.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commercial.entities.schema.article.edit_article_tracability;

@Repository
public interface edit_article_tracabilityRepository extends JpaRepository<edit_article_tracability, Long> {

}
