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
import com.commercial.entities.schema.static_data.unite_mesure;

@Entity

@Table(name="proforma_detail" , schema = "proforma_cmd_bl_fact")

public class proforma_detail implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "proforma")
	proforma proformat;
	
	@ManyToOne
	@JoinColumn(name = "article")
	article article;
	
	private double quantite;
	
	@ManyToOne
	@JoinColumn(name = "unite_mesure")
	unite_mesure unite_mesure;
	
	private double prix_u_ht;
	
	private double montant_ht;
	
	private double tva;
	
	private double montant_tva;
	
	private double montant_ttc;
	
	public proforma_detail() {
		// TODO Auto-generated constructor stub
	}

	public proforma_detail(proforma proformat, article article, double quantite, double prix_u_ht, unite_mesure unite_mesure, double tva) {
		super();
		this.proformat = proformat;
		this.article = article;
		this.quantite = quantite;
		this.unite_mesure = unite_mesure;
		this.prix_u_ht = prix_u_ht;
		this.montant_ht = quantite * prix_u_ht;
		this.tva = tva;
		this.montant_tva = this.montant_ht * (tva/100);
		this.montant_ttc = this.montant_ht + this.montant_tva;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public proforma getProformat() {
		return proformat;
	}

	public void setProformat(proforma proformat) {
		this.proformat = proformat;
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
	
	public unite_mesure getUnite_mesure() {
		return unite_mesure;
	}

	public void setUnite_mesure(unite_mesure unite_mesure) {
		this.unite_mesure = unite_mesure;
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
