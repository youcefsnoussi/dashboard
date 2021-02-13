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

import com.commercial.entities.schema.static_data.wilaya;

@Entity

@Table(name="unite_interne" , schema = "client")

public class unite_interne implements Serializable{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String designation;
	
	@Column(unique = true)
	private String numero_rc;
	 
	@Column(unique = true)
	private String numero_art;
	 
	@Column(unique = true)
	private String numero_nif;
	
	@Column(unique = true)
	private String code;
	
	private String adresse;
	private String comune;
	 
	@ManyToOne
	@JoinColumn(name = "wilaya")
	private wilaya wilaya;
	
	private String etat_blockage = "active";
	
	public unite_interne() {
		// TODO Auto-generated constructor stub
	}

	public unite_interne(String designation, String numero_rc, String numero_art, String numero_nif, String code,
			String adresse, String comune, com.commercial.entities.schema.static_data.wilaya wilaya,
			String etat_blockage) {
		super();
		this.designation = designation;
		this.numero_rc = numero_rc;
		this.numero_art = numero_art;
		this.numero_nif = numero_nif;
		this.code = code;
		this.adresse = adresse;
		this.comune = comune;
		this.wilaya = wilaya;
		this.etat_blockage = etat_blockage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getNumero_rc() {
		return numero_rc;
	}

	public void setNumero_rc(String numero_rc) {
		this.numero_rc = numero_rc;
	}

	public String getNumero_art() {
		return numero_art;
	}

	public void setNumero_art(String numero_art) {
		this.numero_art = numero_art;
	}

	public String getNumero_nif() {
		return numero_nif;
	}

	public void setNumero_nif(String numero_nif) {
		this.numero_nif = numero_nif;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public wilaya getWilaya() {
		return wilaya;
	}

	public void setWilaya(wilaya wilaya) {
		this.wilaya = wilaya;
	}

	public String getEtat_blockage() {
		return etat_blockage;
	}

	public void setEtat_blockage(String etat_blockage) {
		this.etat_blockage = etat_blockage;
	}
	
}
