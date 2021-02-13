package com.commercial.entities.schema.profoma_cmd_bl_fact;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.static_data.unite_mesure;

@Entity

@Table(name="bon_transfert_interne_detail" , schema = "proforma_cmd_bl_fact")

public class bon_transfert_interne_detail implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "bon_transfert_interne")
	private bon_transfert_interne bon_transfert_interne;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	private double quantite;
	
	@ManyToOne
	@JoinColumn(name = "unite_mesure")
	private unite_mesure unite_mesure;
	
	@ManyToOne
	@JoinColumn(name = "magasin")
	private Magasin magasin;
	
	public bon_transfert_interne_detail() {
		// TODO Auto-generated constructor stub
	}

	public bon_transfert_interne_detail(
			com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_interne bon_transfert_interne,
			com.commercial.entities.schema.article.article article, double quantite,
			com.commercial.entities.schema.static_data.unite_mesure unite_mesure, Magasin magasin) {
		super();
		this.bon_transfert_interne = bon_transfert_interne;
		this.article = article;
		this.quantite = quantite;
		this.unite_mesure = unite_mesure;
		this.magasin = magasin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public bon_transfert_interne getBon_transfert_interne() {
		return bon_transfert_interne;
	}

	public void setBon_transfert_interne(bon_transfert_interne bon_transfert_interne) {
		this.bon_transfert_interne = bon_transfert_interne;
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

	public Magasin getMagasin() {
		return magasin;
	}

	public void setMagasin(Magasin magasin) {
		this.magasin = magasin;
	}

	
	
}
