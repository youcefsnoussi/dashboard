package com.commercial.entities.schema.profoma_cmd_bl_fact;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.article;

@Entity

@Table(name="facture_avoir_detail" , schema = "proforma_cmd_bl_fact")

public class facture_avoir_detail implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "facture_avoir")
	private facture_avoir facture_avoir;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	private double quantite;
	
	private double prix_u_ht;
	
	private double montant_ht;
	
	private double tva;
	
	private double montant_tva;
	
	private double montant_ttc;
	
	public facture_avoir_detail() {
		// TODO Auto-generated constructor stub
	}

	public facture_avoir_detail(com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir facture_avoir,
			com.commercial.entities.schema.article.article article, double quantite, double prix_u_ht,
			double montant_ht, double tva, double montant_tva, double montant_ttc) {
		super();
		this.facture_avoir = facture_avoir;
		this.article = article;
		this.quantite = quantite;
		this.prix_u_ht = prix_u_ht;
		this.montant_ht = montant_ht;
		this.tva = tva;
		this.montant_tva = montant_tva;
		this.montant_ttc = montant_ttc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public facture_avoir getFacture_avoir() {
		return facture_avoir;
	}

	public void setFacture_avoir(facture_avoir facture_avoir) {
		this.facture_avoir = facture_avoir;
	}

	public article getArticle() {
		return article;
	}

	public void setArticle(article article) {
		this.article = article;
	}

	public double getQuantite() {
		return quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public double getPrix_u_ht() {
		return prix_u_ht;
	}

	public void setPrix_u_ht(double prix_u_ht) {
		this.prix_u_ht = prix_u_ht;
	}

	public double getMontant_ht() {
		return montant_ht;
	}

	public void setMontant_ht(double montant_ht) {
		this.montant_ht = montant_ht;
	}

	public double getTva() {
		return tva;
	}

	public void setTva(double tva) {
		this.tva = tva;
	}

	public double getMontant_tva() {
		return montant_tva;
	}

	public void setMontant_tva(double montant_tva) {
		this.montant_tva = montant_tva;
	}

	public double getMontant_ttc() {
		return montant_ttc;
	}

	public void setMontant_ttc(double montant_ttc) {
		this.montant_ttc = montant_ttc;
	}
	
	
	
}
