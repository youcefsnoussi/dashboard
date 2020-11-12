package com.commercial.entities.schema.profoma_cmd_bl_fact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.*;

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
	
	private double prix_u_ht;
	
	private double montant_ht;
	
	public proforma_detail() {
		// TODO Auto-generated constructor stub
	}

	public proforma_detail(com.commercial.entities.schema.profoma_cmd_bl_fact.proforma proformat,
			com.commercial.entities.schema.article.article article, double quantite, double prix_u_ht,
			double montant_ht) {
		super();
		this.proformat = proformat;
		this.article = article;
		this.quantite = quantite;
		this.prix_u_ht = prix_u_ht;
		this.montant_ht = montant_ht;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	
}
