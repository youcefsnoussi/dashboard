package com.commercial.entities.schema.article;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="emballage_produit" , schema = "article")

public class emballage_produit implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom_emballage;
	
	public emballage_produit() {
		// TODO Auto-generated constructor stub
	}

	public emballage_produit(String nom_emballage) {
		super();
		this.nom_emballage = nom_emballage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom_emballage() {
		return nom_emballage;
	}

	public void setNom_emballage(String nom_emballage) {
		this.nom_emballage = nom_emballage;
	}
	
	
	
}
