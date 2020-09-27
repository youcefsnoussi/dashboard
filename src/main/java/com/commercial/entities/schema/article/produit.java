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

@Table(name="produit" , schema = "article")

public class produit implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long id;
	private String designation;
	
	@ManyToOne
	@JoinColumn(name = "sous_category_produit")
	private sous_category_produit sous_category_produit;
	
	public produit() {
		// TODO Auto-generated constructor stub
	}

	public produit(String designation,
			com.commercial.entities.schema.article.sous_category_produit sous_category_produit) {
		super();
		this.designation = designation;
		this.sous_category_produit = sous_category_produit;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public sous_category_produit getSous_category_produit() {
		return sous_category_produit;
	}

	public void setSous_category_produit(sous_category_produit sous_category_produit) {
		this.sous_category_produit = sous_category_produit;
	}
	
	
	
}
