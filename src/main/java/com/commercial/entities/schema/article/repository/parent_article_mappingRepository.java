package com.commercial.entities.schema.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.parent_article_mapping;

public interface parent_article_mappingRepository extends JpaRepository<parent_article_mapping, Long> {

	@Query("FROM parent_article_mapping m WHERE m.child_article = :child")
	public parent_article_mapping findByChildArticle(@Param("child") article child);

	@Query("FROM parent_article_mapping m WHERE m.parent_article = :parent")
	public List<parent_article_mapping> findByParentArticle(@Param("parent") article parent);

	@Query("SELECT m.child_article FROM parent_article_mapping m WHERE m.parent_article = :parent")
	public List<article> findChildrenByParent(@Param("parent") article parent);

	@Query("SELECT m.parent_article FROM parent_article_mapping m WHERE m.child_article = :child")
	public article findParentByChild(@Param("child") article child);

	@Modifying
	@Query("DELETE FROM parent_article_mapping m WHERE m.child_article = :child")
	public void deleteByChildArticle(@Param("child") article child);

}
