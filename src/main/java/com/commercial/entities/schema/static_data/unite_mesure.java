package com.commercial.entities.schema.static_data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="unite_mesure" , schema = "static_data")

public class unite_mesure implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom_unite_mesure;
	
	public unite_mesure() {
		// TODO Auto-generated constructor stub
	}

	public unite_mesure(String nom_unite_mesure) {
		super();
		this.nom_unite_mesure = nom_unite_mesure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom_unite_mesure() {
		return nom_unite_mesure;
	}

	public void setNom_unite_mesure(String nom_unite_mesure) {
		this.nom_unite_mesure = nom_unite_mesure;
	}
	
	
	
}
