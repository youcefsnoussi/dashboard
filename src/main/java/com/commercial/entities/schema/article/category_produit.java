package com.commercial.entities.schema.article;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.springframework.data.annotation.Id;

@Entity

@Table(name="category_produit" , schema = "article")

public class category_produit implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom_category;
	
	
	public category_produit() {
		// TODO Auto-generated constructor stub
	}


	public category_produit(String nom_category) {
		super();
		this.nom_category = nom_category;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNom_category() {
		return nom_category;
	}


	public void setNom_category(String nom_category) {
		this.nom_category = nom_category;
	}
	
	
	
}
