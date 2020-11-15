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

import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.magasin_article;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="bon_livraison_detail" , schema = "proforma_cmd_bl_fact")

public class bon_livraison_detail implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "bon_livraison")
	private bon_livraison bon_livraison;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	private double quantite;
	
	private double prix_u_ht;
	
	private double montant_ht;
	
	private double montant_tva;
	
	private double tva;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users user_magasin_validate;
	
	@ManyToOne
	@JoinColumn(name = "unite_mesure")
	private unite_mesure unite_mesure;
	
	@Column(columnDefinition = "boolean default false")
	private boolean validation = false;
	
	@ManyToOne
	@JoinColumn(name = "magasin")
	private Magasin magasin;
	
	public bon_livraison_detail() {
		// TODO Auto-generated constructor stub
	}

	public bon_livraison_detail(com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison bon_livraison,
			com.commercial.entities.schema.article.article article, double quantite, double prix_u_ht,
			double montant_ht, double montant_tva, double tva, users user_magasin_validate,
			com.commercial.entities.schema.static_data.unite_mesure unite_mesure, boolean validation,
			Magasin magasin) {
		super();
		this.bon_livraison = bon_livraison;
		this.article = article;
		this.quantite = quantite;
		this.prix_u_ht = prix_u_ht;
		this.montant_ht = montant_ht;
		this.montant_tva = montant_tva;
		this.tva = tva;
		this.user_magasin_validate = user_magasin_validate;
		this.unite_mesure = unite_mesure;
		this.validation = validation;
		this.magasin = magasin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public bon_livraison getBon_livraison() {
		return bon_livraison;
	}

	public void setBon_livraison(bon_livraison bon_livraison) {
		this.bon_livraison = bon_livraison;
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

	public double getMontant_tva() {
		return montant_tva;
	}

	public void setMontant_tva(double montant_tva) {
		this.montant_tva = montant_tva;
	}

	public double getTva() {
		return tva;
	}

	public void setTva(double tva) {
		this.tva = tva;
	}

	public users getUser_magasin_validate() {
		return user_magasin_validate;
	}

	public void setUser_magasin_validate(users user_magasin_validate) {
		this.user_magasin_validate = user_magasin_validate;
	}

	public unite_mesure getUnite_mesure() {
		return unite_mesure;
	}

	public void setUnite_mesure(unite_mesure unite_mesure) {
		this.unite_mesure = unite_mesure;
	}

	public boolean isValidation() {
		return validation;
	}

	public void setValidation(boolean validation) {
		this.validation = validation;
	}

	public Magasin getMagasin() {
		return magasin;
	}

	public void setMagasin(Magasin magasin) {
		this.magasin = magasin;
	}

}
