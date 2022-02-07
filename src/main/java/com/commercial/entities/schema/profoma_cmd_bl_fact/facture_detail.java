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

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.static_data.unite_mesure;

@Entity

@Table(name="facture_detail" , schema = "proforma_cmd_bl_fact")

public class facture_detail implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	private facture facture;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	private double quantite;
	
	private double prix_u_ht;
	
	private double montant_ht;
	
	private double tva;
	
	private double montant_tva;
	
	private double montant_ttc;
	
	@Column(columnDefinition="double precision default 0")
	private double pourcentage_remise;
	
	@Column(columnDefinition="double precision default 0")
	private double montant_remise;
	
	@Column(columnDefinition="double precision default 0")
	private double montant_ht_net;
	
	@ManyToOne
	@JoinColumn(name = "unite_mesure")
	private unite_mesure unite_mesure;
	
	public facture_detail() {
		// TODO Auto-generated constructor stub
	}

	public facture_detail(facture facture, article article, double quantite, double prix_u_ht, double montant_ht, double tva, 
			double montant_tva, double montant_ttc, double pourcentage_remise, double montant_remise, double montant_ht_net, 
			unite_mesure unite_mesure) {
		super();
		this.facture = facture;
		this.article = article;
		this.quantite = quantite;
		this.prix_u_ht = prix_u_ht;
		this.montant_ht = montant_ht;
		this.tva = tva;
		this.montant_tva = montant_tva;
		this.montant_ttc = montant_ttc;
		this.pourcentage_remise = pourcentage_remise;
		this.montant_remise = montant_remise;
		this.montant_ht_net = montant_ht_net;
		this.unite_mesure = unite_mesure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public facture getFacture() {
		return facture;
	}

	public void setFacture(facture facture) {
		this.facture = facture;
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

	public double getPourcentage_remise() {
		return pourcentage_remise;
	}

	public void setPourcentage_remise(double pourcentage_remise) {
		this.pourcentage_remise = pourcentage_remise;
	}

	public double getMontant_remise() {
		return montant_remise;
	}

	public void setMontant_remise(double montant_remise) {
		this.montant_remise = montant_remise;
	}

	public double getMontant_ht_net() {
		return montant_ht_net;
	}

	public void setMontant_ht_net(double montant_ht_net) {
		this.montant_ht_net = montant_ht_net;
	}

	public unite_mesure getUnite_mesure() {
		return unite_mesure;
	}

	public void setUnite_mesure(unite_mesure unite_mesure) {
		this.unite_mesure = unite_mesure;
	}

	
	
}
