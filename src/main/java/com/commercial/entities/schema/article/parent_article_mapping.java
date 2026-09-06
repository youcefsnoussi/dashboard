package com.commercial.entities.schema.article;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="parent_article_mapping" , schema = "article")
public class parent_article_mapping implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "parent_article")
	private article parent_article;

	@ManyToOne
	@JoinColumn(name = "child_article")
	private article child_article;

	public parent_article_mapping() {
	}

	public parent_article_mapping(article parent_article, article child_article) {
		this.parent_article = parent_article;
		this.child_article = child_article;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public article getParent_article() {
		return parent_article;
	}

	public void setParent_article(article parent_article) {
		this.parent_article = parent_article;
	}

	public article getChild_article() {
		return child_article;
	}

	public void setChild_article(article child_article) {
		this.child_article = child_article;
	}

}
