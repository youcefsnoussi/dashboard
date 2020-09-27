package com.commercial.entities.schema.client;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="category_client" , schema = "client")

public class category_client implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nom_category;
	
	private String lettre;
	
	public category_client() {
		// TODO Auto-generated constructor stub
	}

	public category_client(String nom_category, String lettre) {
		super();
		this.nom_category = nom_category;
		this.lettre = lettre;
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

	public String getLettre() {
		return lettre;
	}

	public void setLettre(String lettre) {
		this.lettre = lettre;
	}

	
}
