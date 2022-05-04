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

@Table(name="article_consignation_relation" , schema = "article")

public class article_consignation_relation implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	@ManyToOne
	@JoinColumn(name = "article_consignation")
	private article article_consignation;
	
	private double division;
	
	public article_consignation_relation() {
		// TODO Auto-generated constructor stub
	}

	public article_consignation_relation(article article, article article_consignation, double division) {
		super();
		this.article = article;
		this.article_consignation = article_consignation;
		this.division = division;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public article getArticle() {
		return article;
	}

	public void setArticle(article article) {
		this.article = article;
	}

	public article getArticle_consignation() {
		return article_consignation;
	}

	public void setArticle_consignation(article article_consignation) {
		this.article_consignation = article_consignation;
	}

	public double getDivision() {
		return division;
	}

	public void setDivision(double division) {
		this.division = division;
	}
	
}
