package com.commercial.entities.schema.static_data;

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

@Table(name="information_entreprise" , schema = "static_data")

public class information_entreprise implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom_entreprise;
	
	private String adresse_facturation; 
	
	private String activite_entreprise;
	
	private String commune;
	
	private String wilaya;
	
	private String code_postal;
	
	private String telephone;
	
	private String fax;
	
	private String email;
	
	private String num_rc;
	
	private String num_nif;
	
	private String num_art;
	
	private String num_nis;
	
	private double capitale;
	
	@ManyToOne
    @JoinColumn(name = "banque")
	private banque banque;
	
	private String chemain_logo = "";
	
	public information_entreprise() {
		// TODO Auto-generated constructor stub
	}

	public information_entreprise(String nom_entreprise, String adresse_facturation, String activite_entreprise,
			String commune, String wilaya, String code_postal, String telephone, String fax, String email,
			String num_rc, String num_nif, String num_art, String num_nis, double capitale,
			com.commercial.entities.schema.static_data.banque banque, String chemain_logo) {
		super();
		this.nom_entreprise = nom_entreprise;
		this.adresse_facturation = adresse_facturation;
		this.activite_entreprise = activite_entreprise;
		this.commune = commune;
		this.wilaya = wilaya;
		this.code_postal = code_postal;
		this.telephone = telephone;
		this.fax = fax;
		this.email = email;
		this.num_rc = num_rc;
		this.num_nif = num_nif;
		this.num_art = num_art;
		this.num_nis = num_nis;
		this.capitale = capitale;
		this.banque = banque;
		this.chemain_logo = chemain_logo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom_entreprise() {
		return nom_entreprise;
	}

	public void setNom_entreprise(String nom_entreprise) {
		this.nom_entreprise = nom_entreprise;
	}

	public String getAdresse_facturation() {
		return adresse_facturation;
	}

	public void setAdresse_facturation(String adresse_facturation) {
		this.adresse_facturation = adresse_facturation;
	}

	public String getActivite_entreprise() {
		return activite_entreprise;
	}

	public void setActivite_entreprise(String activite_entreprise) {
		this.activite_entreprise = activite_entreprise;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getWilaya() {
		return wilaya;
	}

	public void setWilaya(String wilaya) {
		this.wilaya = wilaya;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNum_rc() {
		return num_rc;
	}

	public void setNum_rc(String num_rc) {
		this.num_rc = num_rc;
	}

	public String getNum_nif() {
		return num_nif;
	}

	public void setNum_nif(String num_nif) {
		this.num_nif = num_nif;
	}

	public String getNum_art() {
		return num_art;
	}

	public void setNum_art(String num_art) {
		this.num_art = num_art;
	}

	public String getNum_nis() {
		return num_nis;
	}

	public void setNum_nis(String num_nis) {
		this.num_nis = num_nis;
	}

	public double getCapitale() {
		return capitale;
	}

	public void setCapitale(double capitale) {
		this.capitale = capitale;
	}

	public banque getBanque() {
		return banque;
	}

	public void setBanque(banque banque) {
		this.banque = banque;
	}

	public String getChemain_logo() {
		return chemain_logo;
	}

	public void setChemain_logo(String chemain_logo) {
		this.chemain_logo = chemain_logo;
	}

	

}
