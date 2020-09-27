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

@Table(name="sous_category_produit" , schema = "article")

public class sous_category_produit  implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nom_sous_category;
	
	@ManyToOne
	@JoinColumn(name = "category_produit")
	private category_produit category_produit;
	
	
	public sous_category_produit() {
		// TODO Auto-generated constructor stub
	}


	public sous_category_produit(String nom_sous_category,
			com.commercial.entities.schema.article.category_produit category_produit) {
		super();
		this.nom_sous_category = nom_sous_category;
		this.category_produit = category_produit;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNom_sous_category() {
		return nom_sous_category;
	}


	public void setNom_sous_category(String nom_sous_category) {
		this.nom_sous_category = nom_sous_category;
	}


	public category_produit getCategory_produit() {
		return category_produit;
	}


	public void setCategory_produit(category_produit category_produit) {
		this.category_produit = category_produit;
	}
	
	
	
}
