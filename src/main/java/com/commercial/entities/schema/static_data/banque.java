package com.commercial.entities.schema.static_data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="banque" , schema = "static_data")

public class banque implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	private String nom_banque;
	private String numero_compte_entreprise;
	
	@Column(columnDefinition="boolean default true")
	private boolean display;
	
	public banque() {
		// TODO Auto-generated constructor stub
	}

	public banque(String code, String nom_banque, String numero_compte_entreprise) {
		super();
		this.code = code;
		this.nom_banque = nom_banque;
		this.numero_compte_entreprise = numero_compte_entreprise;
		this.display = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom_banque() {
		return nom_banque;
	}

	public void setNom_banque(String nom_banque) {
		this.nom_banque = nom_banque;
	}

	public String getNumero_compte_entreprise() {
		return numero_compte_entreprise;
	}

	public void setNumero_compte_entreprise(String numero_compte_entreprise) {
		this.numero_compte_entreprise = numero_compte_entreprise;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	
	
}
