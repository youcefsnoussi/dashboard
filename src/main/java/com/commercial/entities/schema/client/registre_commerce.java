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

import com.commercial.entities.schema.static_data.banque;
import com.commercial.entities.schema.static_data.mode_paiement;
import com.commercial.entities.schema.static_data.type_reglement;
import com.commercial.entities.schema.static_data.unite;
import com.commercial.entities.schema.static_data.wilaya;

@Entity

@Table(name="registre_commerce" , schema = "client")

public class registre_commerce implements Serializable{
	
	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 private String nom;
	 private String prenom;
	 
	 @Column(unique = true)
	 private String code;
	 
	 @ManyToOne
	 @JoinColumn(name = "category_client")
	 private category_client category;
	 
	 @Column(unique = true)
	 private String numero_rc;
	 
	 @Column(unique = true)
	 private String numero_art;
	 
	 @Column(unique = true)
	 private String numero_nif;
	 
	 private String date_emission;
	 
	 private String date_fin;
	 
	 private String adresse;
	 private String comune;
	 
	 @ManyToOne
	 @JoinColumn(name = "wilaya")
	 private wilaya wilaya;
	 
	 private String etat; //------------------> etat tweli desactivé ki ifout la date
	 
	 private double tva; //-------------------> 0 matetebakch alih tva / 1 3akss
	 
	 private double plafond;
	 
	 private double sold_encours;
	 
	 private String activite;
	 
	 private String etat_blockage = "active";
	 
	 @ManyToOne
	 @JoinColumn(name = "banque")
	 private banque banque;
	
	 @ManyToOne
	 @JoinColumn(name = "type_reglement")
	 private type_reglement type_reglement;
	 
	 @ManyToOne
	 @JoinColumn(name = "mode_paiement")
	 private mode_paiement mode_paiement;
	 
	 @ManyToOne
	 @JoinColumn(name = "unite")
	 private unite unite;
	 
	 private String date_last_facture;
	 
	 @Column(columnDefinition="boolean default false")
	 private boolean multiple_bon_livraison;
	 
	 private String ArticleLoieExoneration;
	 
	public registre_commerce() {
		super();
		// TODO Auto-generated constructor stub
	}

	public registre_commerce(String nom, String prenom, String code, category_client category, String numero_rc,
			String numero_art, String numero_nif, String date_emission, String date_fin, String adresse, String comune,
			com.commercial.entities.schema.static_data.wilaya wilaya, String etat, double tva, double plafond,
			double sold_encours, String activite, String etat_blockage,
			com.commercial.entities.schema.static_data.banque banque,
			com.commercial.entities.schema.static_data.type_reglement type_reglement,
			com.commercial.entities.schema.static_data.mode_paiement mode_paiement,
			com.commercial.entities.schema.static_data.unite unite) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.code = code;
		this.category = category;
		this.numero_rc = numero_rc;
		this.numero_art = numero_art;
		this.numero_nif = numero_nif;
		this.date_emission = date_emission;
		this.date_fin = date_fin;
		this.adresse = adresse;
		this.comune = comune;
		this.wilaya = wilaya;
		this.etat = etat;
		this.tva = tva;
		this.plafond = plafond;
		this.sold_encours = sold_encours;
		this.activite = activite;
		this.etat_blockage = etat_blockage;
		this.banque = banque;
		this.type_reglement = type_reglement;
		this.mode_paiement = mode_paiement;
		this.unite = unite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public category_client getCategory() {
		return category;
	}

	public void setCategory(category_client category) {
		this.category = category;
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

	public String getDate_emission() {
		return date_emission;
	}

	public void setDate_emission(String date_emission) {
		this.date_emission = date_emission;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
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

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public double getTva() {
		return tva;
	}
	
	public void setTva(double tva) {
		this.tva = tva;
	}

	public double getPlafond() {
		return plafond;
	}

	public void setPlafond(double plafond) {
		this.plafond = plafond;
	}

	public double getSold_encours() {
		return sold_encours;
	}

	public void setSold_encours(double sold_encours) {
		this.sold_encours = sold_encours;
	}

	public String getActivite() {
		return activite;
	}

	public void setActivite(String activite) {
		this.activite = activite;
	}

	public String getEtat_blockage() {
		return etat_blockage;
	}

	public void setEtat_blockage(String etat_blockage) {
		this.etat_blockage = etat_blockage;
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

	public mode_paiement getMode_paiement() {
		return mode_paiement;
	}

	public void setMode_paiement(mode_paiement mode_paiement) {
		this.mode_paiement = mode_paiement;
	}

	public unite getUnite() {
		return unite;
	}

	public void setUnite(unite unite) {
		this.unite = unite;
	}

	public String getDate_last_facture() {
		return date_last_facture;
	}

	public void setDate_last_facture(String date_last_facture) {
		this.date_last_facture = date_last_facture;
	}

	public boolean isMultiple_bon_livraison() {
		return multiple_bon_livraison;
	}

	public void setMultiple_bon_livraison(boolean multiple_bon_livraison) {
		this.multiple_bon_livraison = multiple_bon_livraison;
	}

	public String getArticleLoieExoneration() {
		return ArticleLoieExoneration;
	}

	public void setArticleLoieExoneration(String articleLoieExoneration) {
		ArticleLoieExoneration = articleLoieExoneration;
	}
	 
	

}
