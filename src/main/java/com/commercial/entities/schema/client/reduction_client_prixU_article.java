package com.commercial.entities.schema.client;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.*;

@Entity

@Table(name="reduction_client_prixu_article" , schema = "client")

public class reduction_client_prixU_article implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "client")
	private client client;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	private double nouveau_prix;
	
	private String date_debut;
	
	private String date_fin;
	
	public reduction_client_prixU_article() {
		// TODO Auto-generated constructor stub
	}

	public reduction_client_prixU_article(client client,article article, double nouveau_prix, String date_debut,String date_fin) {
		super();
		this.client = client;
		this.article = article;
		this.nouveau_prix = nouveau_prix;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public client getClient() {
		return client;
	}

	public void setClient(client client) {
		this.client = client;
	}

	public article getArticle() {
		return article;
	}

	public void setArticle(article article) {
		this.article = article;
	}

	public double getNouveau_prix() {
		return nouveau_prix;
	}

	public void setNouveau_prix(double nouveau_prix) {
		this.nouveau_prix = nouveau_prix;
	}

	public String getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}
	
	
	
}
