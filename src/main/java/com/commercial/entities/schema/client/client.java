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

import com.commercial.entities.schema.static_data.*;

@Entity

@Table(name="client" , schema = "client")

public class client implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	private String prenom;
	
	private String adresse;
	
	@ManyToOne
	@JoinColumn(name = "wilaya")
	wilaya wilaya;
	
	private String code_postal;
	private String email;
	
	private String telephone;
	
	private String fax;
	
	@Column(unique = true)
	private String code;
	
	private String date_creation;
	
	@ManyToOne
	@JoinColumn(name = "category_client")
	private category_client category;
	
	@ManyToOne
	@JoinColumn(name = "banque")
	private banque banque;
	
	@ManyToOne
	@JoinColumn(name = "type_regelement_client")
	private type_reglement type_reglement;
	
	@ManyToOne
	@JoinColumn(name = "unite")
	private unite unite;
	
	private double sold_encours;
	
	private double plafond;
	
	private boolean etat_blockage = false;
	
	private boolean remise = false;
	
	private String img;
	
	public client() {
		// TODO Auto-generated constructor stub
	}

	public client(String nom, String prenom, String adresse, wilaya wilaya, String code_postal, String email,
			String telephone, String fax, String code, String date_creation, category_client category,
			com.commercial.entities.schema.static_data.banque banque,
			com.commercial.entities.schema.static_data.type_reglement type_reglement,
			com.commercial.entities.schema.static_data.unite unite, double sold_encours, double plafond,
			boolean etat_blockage, boolean remise, String img) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.wilaya = wilaya;
		this.code_postal = code_postal;
		this.email = email;
		this.telephone = telephone;
		this.fax = fax;
		this.code = code;
		this.date_creation = date_creation;
		this.category = category;
		this.banque = banque;
		this.type_reglement = type_reglement;
		this.unite = unite;
		this.sold_encours = sold_encours;
		this.plafond = plafond;
		this.etat_blockage = etat_blockage;
		this.remise = remise;
		this.img = img;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public wilaya getWilaya() {
		return wilaya;
	}

	public void setWilaya(wilaya wilaya) {
		this.wilaya = wilaya;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(String date_creation) {
		this.date_creation = date_creation;
	}

	public category_client getCategory() {
		return category;
	}

	public void setCategory(category_client category) {
		this.category = category;
	}

	public banque getBanque() {
		return banque;
	}

	public void setBanque(banque banque) {
		this.banque = banque;
	}

	public type_reglement getType_reglement() {
		return type_reglement;
	}

	public void setType_reglement(type_reglement type_reglement) {
		this.type_reglement = type_reglement;
	}

	public unite getUnite() {
		return unite;
	}

	public void setUnite(unite unite) {
		this.unite = unite;
	}

	public double getSold_encours() {
		return sold_encours;
	}

	public void setSold_encours(double sold_encours) {
		this.sold_encours = sold_encours;
	}

	public double getPlafond() {
		return plafond;
	}

	public void setPlafond(double plafond) {
		this.plafond = plafond;
	}

	public boolean isEtat_blockage() {
		return etat_blockage;
	}

	public void setEtat_blockage(boolean etat_blockage) {
		this.etat_blockage = etat_blockage;
	}

	public boolean isRemise() {
		return remise;
	}

	public void setRemise(boolean remise) {
		this.remise = remise;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
