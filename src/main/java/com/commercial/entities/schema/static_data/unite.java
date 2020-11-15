package com.commercial.entities.schema.static_data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="unite" , schema = "static_data")

public class unite implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom_unite;
	
	private Integer identifiant = 0;
	
	public unite() {
		// TODO Auto-generated constructor stub
	}

	public unite(String nom_unite, int identifiant) {
		super();
		this.nom_unite = nom_unite;
		this.identifiant = identifiant;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdentifiant(Integer identifiant) {
		this.identifiant = identifiant;
	}

	public String getNom_unite() {
		return nom_unite;
	}

	public void setNom_unite(String nom_unite) {
		this.nom_unite = nom_unite;
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	
	
}
