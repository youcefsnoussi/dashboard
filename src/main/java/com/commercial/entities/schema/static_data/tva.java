package com.commercial.entities.schema.static_data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="tva" , schema = "static_data")

public class tva implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double taux_tva;
	
	public tva() {
		// TODO Auto-generated constructor stub
	}

	public tva(double taux_tva) {
		super();
		this.taux_tva = taux_tva;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getTaux_tva() {
		return taux_tva;
	}

	public void setTaux_tva(double taux_tva) {
		this.taux_tva = taux_tva;
	}
	
	
	
}
