package com.commercial.entities.schema.article;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="pesage_produit" , schema = "article")

public class pesage_produit implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double pesage;
	
	private String unite_pesage;
	
	public pesage_produit() {
		// TODO Auto-generated constructor stub
	}

	public pesage_produit(double pesage, String unite_pesage) {
		super();
		this.pesage = pesage;
		this.unite_pesage = unite_pesage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPesage() {
		return pesage;
	}

	public void setPesage(double pesage) {
		this.pesage = pesage;
	}

	public String getUnite_pesage() {
		return unite_pesage;
	}

	public void setUnite_pesage(String unite_pesage) {
		this.unite_pesage = unite_pesage;
	}
	
	
	
}
