package com.commercial.entities.schema.article;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.static_data.unite_mesure;

@Entity
@Table(name="article" , schema = "article")

public class article implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "produit")
	private produit produit;
	
	@ManyToOne
	@JoinColumn(name = "emballage_produit")
	private emballage_produit emballage_produit;
	
	@ManyToOne
	@JoinColumn(name = "pesage_produit")
	private pesage_produit pesage_produit;
	
	private String image_article;
	
	@Column(name="vendu",columnDefinition = "numeric default 0")
	private double vendu = 0; //-------------- unite
	
	private String date_creation;
	
	@ManyToOne
	@JoinColumn(name = "unite_mesure_vente")
	private unite_mesure unite_mesure_vente;
	
	@Column(columnDefinition="boolean default false")
	private boolean subvension = false;
	
	private String libelle;
	
	@Column(columnDefinition="boolean default true")
	private boolean display_listing = true;
	
	@Column(columnDefinition="double precision default 0")
	private double pesagePalette;
	
	@Column(columnDefinition="boolean default false")
	private boolean consignation = false;
	
	public article() {
		// TODO Auto-generated constructor stub
	}

	public article(String code, produit produit, emballage_produit emballage_produit, pesage_produit pesage_produit, 
			String image_article, double vendu, String date_creation, unite_mesure unite_mesure_vente, boolean subvension, 
			String libelle, boolean consignation) {
		super();
		this.code = code;
		this.produit = produit;
		this.emballage_produit = emballage_produit;
		this.pesage_produit = pesage_produit;
		this.image_article = image_article;
		this.vendu = vendu;
		this.date_creation = date_creation;
		this.unite_mesure_vente = unite_mesure_vente;
		this.subvension = subvension;
		this.libelle = libelle;
		this.consignation = consignation;
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

	public produit getProduit() {
		return produit;
	}

	public void setProduit(produit produit) {
		this.produit = produit;
	}

	public emballage_produit getEmballage_produit() {
		return emballage_produit;
	}

	public void setEmballage_produit(emballage_produit emballage_produit) {
		this.emballage_produit = emballage_produit;
	}

	public pesage_produit getPesage_produit() {
		return pesage_produit;
	}

	public void setPesage_produit(pesage_produit pesage_produit) {
		this.pesage_produit = pesage_produit;
	}

	public String getImage_article() {
		return image_article;
	}

	public void setImage_article(String image_article) {
		this.image_article = image_article;
	}

	public double getVendu() {
		return vendu;
	}

	public void setVendu(double vendu) {
		this.vendu = vendu;
	}

	public String getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(String date_creation) {
		this.date_creation = date_creation;
	}

	public unite_mesure getUnite_mesure_vente() {
		return unite_mesure_vente;
	}

	public void setUnite_mesure_vente(unite_mesure unite_mesure_vente) {
		this.unite_mesure_vente = unite_mesure_vente;
	}

	public boolean isSubvension() {
		return subvension;
	}

	public void setSubvension(boolean subvension) {
		this.subvension = subvension;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public boolean isDisplay_listing() {
		return display_listing;
	}

	public void setDisplay_listing(boolean diplay_listing) {
		this.display_listing = diplay_listing;
	}

	public double getPesagePalette() {
		return pesagePalette;
	}

	public void setPesagePalette(double pesagePalette) {
		this.pesagePalette = pesagePalette;
	}

	public boolean isConsignation() {
		return consignation;
	}

	public void setConsignation(boolean consignation) {
		this.consignation = consignation;
	}
	
}
