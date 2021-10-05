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
import com.commercial.entities.schema.static_data.unite_mesure;;

@Entity

@Table(name="BonCommandeDetail" , schema = "proforma_cmd_bl_fact")

public class BonCommandeDetail implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "bon_commande")
	private BonCommande bon_commande;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	private double quantite;
	
	@ManyToOne
	@JoinColumn(name = "unite_mesure")
	private unite_mesure unite_mesure;
	
	public BonCommandeDetail() {
		// TODO Auto-generated constructor stub
	}

	public BonCommandeDetail(BonCommande bon_commande,article article, double quantite, unite_mesure unite_mesure) {
		super();
		this.bon_commande = bon_commande;
		this.article = article;
		this.quantite = quantite;
		this.unite_mesure = unite_mesure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BonCommande getBon_commande() {
		return bon_commande;
	}

	public void setBon_commande(BonCommande bon_commande) {
		this.bon_commande = bon_commande;
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
	
	
	
}
