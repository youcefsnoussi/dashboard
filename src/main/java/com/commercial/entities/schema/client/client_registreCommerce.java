package com.commercial.entities.schema.client;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity

@Table(name="client_registreCommerce" , schema = "client")

public class client_registreCommerce implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client")
	private client client;
	
	@ManyToOne
	@JoinColumn(name = "registre_commerce")
	private registre_commerce registre_commerce;
	
	private String date_debut;
	
	private String date_fin;
	
	private double montant_actuel;
	
	public client_registreCommerce() {
		// TODO Auto-generated constructor stub
	}

	public client_registreCommerce(com.commercial.entities.schema.client.client client,
			com.commercial.entities.schema.client.registre_commerce registre_commerce, String date_debut,
			String date_fin, double montant_actuel) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.montant_actuel = montant_actuel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public client getClient() {
		return client;
	}

	public void setClient(client client) {
		this.client = client;
	}

	public registre_commerce getRegistre_commerce() {
		return registre_commerce;
	}

	public void setRegistre_commerce(registre_commerce registre_commerce) {
		this.registre_commerce = registre_commerce;
	}

	public String getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public double getMontant_actuel() {
		return montant_actuel;
	}

	public void setMontant_actuel(double montant_actuel) {
		this.montant_actuel = montant_actuel;
	}

	

}
