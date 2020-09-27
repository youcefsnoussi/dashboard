package com.commercial.entities.schema.article;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.static_data.tva;

@Entity

@Table(name="prix_unitaire_article_category_client" , schema = "article")

public class prixUnitaire_article_categoryClient implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	@ManyToOne
	@JoinColumn(name = "category_client")
	private category_client category_client;
	
	private double prix;
	
	@ManyToOne
	@JoinColumn(name = "tva")
	private tva tva;
	
	public prixUnitaire_article_categoryClient() {
		// TODO Auto-generated constructor stub
	}

	public prixUnitaire_article_categoryClient(com.commercial.entities.schema.article.article article,
			com.commercial.entities.schema.client.category_client category_client, double prix,
			com.commercial.entities.schema.static_data.tva tva) {
		super();
		this.article = article;
		this.category_client = category_client;
		this.prix = prix;
		this.tva = tva;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public article getArticle() {
		return article;
	}

	public void setArticle(article article) {
		this.article = article;
	}

	public category_client getCategory_client() {
		return category_client;
	}

	public void setCategory_client(category_client category_client) {
		this.category_client = category_client;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public tva getTva() {
		return tva;
	}

	public void setTva(tva tva) {
		this.tva = tva;
	}

	
	
}
