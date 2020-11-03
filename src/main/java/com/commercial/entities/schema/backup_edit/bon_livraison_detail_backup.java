package com.commercial.entities.schema.backup_edit;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.magasin_article;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="bon_livraison_detail_backup" , schema = "backup_edit")

public class bon_livraison_detail_backup implements Serializable{
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "bon_livraison_backup")
	private bon_livraison_backup bon_livraison_backup;
	
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
	private magasin_article magasin;
	
	public bon_livraison_detail_backup() {
		// TODO Auto-generated constructor stub
		super();
	}

	public bon_livraison_detail_backup(
			com.commercial.entities.schema.backup_edit.bon_livraison_backup bon_livraison_backup,
			com.commercial.entities.schema.article.article article, double quantite, double prix_u_ht,
			double montant_ht, double montant_tva, double tva, users user_magasin_validate,
			com.commercial.entities.schema.static_data.unite_mesure unite_mesure, boolean validation, magasin_article magasin) {
		super();
		this.bon_livraison_backup = bon_livraison_backup;
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
	
	public bon_livraison_detail_backup(bon_livraison_detail bl_d, bon_livraison_backup bl_b) {
		
		super();
		this.bon_livraison_backup = bl_b;
		this.article = bl_d.getArticle();
		this.quantite = bl_d.getQuantite();
		this.prix_u_ht = bl_d.getPrix_u_ht();
		this.montant_ht = bl_d.getMontant_ht();
		this.montant_tva = bl_d.getMontant_tva();
		this.tva = bl_d.getTva();
		this.user_magasin_validate = bl_d.getUser_magasin_validate();
		this.unite_mesure = bl_d.getUnite_mesure();
		this.validation = bl_d.isValidation();
		this.magasin = bl_d.getMagasin();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public bon_livraison_backup getBon_livraison_backup() {
		return bon_livraison_backup;
	}

	public void setBon_livraison_backup(bon_livraison_backup bon_livraison_backup) {
		this.bon_livraison_backup = bon_livraison_backup;
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
	
}
