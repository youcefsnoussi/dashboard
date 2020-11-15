package com.commercial.entities.schema.static_data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="peseur" , schema = "static_data")

public class peseur implements Serializable{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String matricule;
	
	private String date;
	
	private float poids_entre;
	
	private float poids_sortie;
	
	private float net;

	public peseur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getPoids_entre() {
		return poids_entre;
	}

	public void setPoids_entre(float poids_entre) {
		this.poids_entre = poids_entre;
	}

	public float getPoids_sortie() {
		return poids_sortie;
	}

	public void setPoids_sortie(float poids_sortie) {
		this.poids_sortie = poids_sortie;
	}

	public float getNet() {
		return net;
	}

	public void setNet(float net) {
		this.net = net;
	}
	
	
}
