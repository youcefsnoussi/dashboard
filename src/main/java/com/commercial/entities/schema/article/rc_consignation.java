package com.commercial.entities.schema.article;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.client.registre_commerce;

@Entity

@Table(name="rc_consignation" , schema = "article")

public class rc_consignation implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "registre_commerce")
	private registre_commerce registre_commerce;
	
	@ManyToOne
	@JoinColumn(name = "article_consignation")
	private article article_consignation;
	
	private double sold_physique;
	
	private double sold_valorise;
	
	private double prix_u_ht;
	
	public rc_consignation() {
		// TODO Auto-generated constructor stub
	}

	public rc_consignation(registre_commerce registre_commerce, article article_consignation, double prix_u_ht) {
		super();
		this.registre_commerce = registre_commerce;
		this.article_consignation = article_consignation;
		this.sold_physique = 0;
		this.sold_valorise = 0;
		this.prix_u_ht = prix_u_ht;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public registre_commerce getRegistre_commerce() {
		return registre_commerce;
	}

	public void setRegistre_commerce(registre_commerce registre_commerce) {
		this.registre_commerce = registre_commerce;
	}

	public article getArticle_consignation() {
		return article_consignation;
	}

	public void setArticle_consignation(article article_consignation) {
		this.article_consignation = article_consignation;
	}
	
	public double getSold_physique() {
		return sold_physique;
	}

	public void setSold_physique(double sold_physique) {
		this.sold_physique = sold_physique;
	}

	public double getSold_valorise() {
		return sold_valorise;
	}

	public void setSold_valorise(double sold_valorise) {
		this.sold_valorise = sold_valorise;
	}

	public double getPrix_u_ht() {
		return prix_u_ht;
	}

	public void setPrix_u_ht(double prix_u_ht) {
		this.prix_u_ht = prix_u_ht;
	}
	
}
