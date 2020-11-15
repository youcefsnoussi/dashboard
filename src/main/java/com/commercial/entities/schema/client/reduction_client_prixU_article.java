package com.commercial.entities.schema.client;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.*;
import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="reduction_client_prixu_article" , schema = "client")

public class reduction_client_prixU_article implements Serializable{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client")
	private client client;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	private double ancien_prix;
	
	private double nouveau_prix;
	
	private String date_debut;
	
	private String date_fin;
	
	private String date_etablissement;
	
	private String observation;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	private boolean active = false;
	
	public reduction_client_prixU_article() {
		// TODO Auto-generated constructor stub
	}

	public reduction_client_prixU_article(com.commercial.entities.schema.client.client client,
			com.commercial.entities.schema.article.article article, double ancien_prix, double nouveau_prix,
			String date_debut, String date_fin, String date_etablissement, String observation,
			com.commercial.entities.schema.user_menu.users users, boolean active) {
		super();
		this.client = client;
		this.article = article;
		this.ancien_prix = ancien_prix;
		this.nouveau_prix = nouveau_prix;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.date_etablissement = date_etablissement;
		this.observation = observation;
		this.users = users;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public double getAncien_prix() {
		return ancien_prix;
	}

	public void setAncien_prix(double ancien_prix) {
		this.ancien_prix = ancien_prix;
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

	public String getDate_etablissement() {
		return date_etablissement;
	}

	public void setDate_etablissement(String date_etablissement) {
		this.date_etablissement = date_etablissement;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	

}
