package com.commercial.entities.schema.dynamic_data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;

@Entity

@Table(name="mouvement_consignation" , schema = "dynamic_data")

public class mouvement_consignation implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "registre_commerce")
	private registre_commerce registre_commerce;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article_consignation;
	
	private String date;
	
	private String time;
	
	private double montant_physic; 
	
	private double montant_valorise;
	
	private double old_sold_physic; 
	
	private double old_sold_valorise;
	
	private double new_sold_physic; 
	
	private double new_sold_valorise;
	
	private String type_operation;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	private facture facture;
	
	public mouvement_consignation() {
		// TODO Auto-generated constructor stub
	}

	public mouvement_consignation(registre_commerce registre_commerce, article article_consignation, String date, String time, 
			double montant_physic, double montant_valorise, double old_sold_physic, double old_sold_valorise, double new_sold_physic, 
			double new_sold_valorise, String type_operation, facture facture) {
		super();
		this.registre_commerce = registre_commerce;
		this.article_consignation = article_consignation;
		this.date = date;
		this.time = time;
		this.montant_physic = montant_physic;
		this.montant_valorise = montant_valorise;
		this.old_sold_physic = old_sold_physic;
		this.old_sold_valorise = old_sold_valorise;
		this.new_sold_physic = new_sold_physic;
		this.new_sold_valorise = new_sold_valorise;
		this.type_operation = type_operation;
		this.facture = facture;
	}
	
	public mouvement_consignation(registre_commerce registre_commerce, article article_consignation, String date, String time, 
			double montant_physic, double montant_valorise, double old_sold_physic, double old_sold_valorise, String type_operation,
			facture facture) {
		super();
		this.registre_commerce = registre_commerce;
		this.article_consignation = article_consignation;
		this.date = date;
		this.time = time;
		this.montant_physic = montant_physic;
		this.montant_valorise = montant_valorise;
		this.old_sold_physic = old_sold_physic;
		this.old_sold_valorise = old_sold_valorise;
		this.new_sold_physic = old_sold_physic + montant_physic;
		this.new_sold_valorise = old_sold_valorise + montant_valorise;
		this.type_operation = type_operation;
		this.facture = facture;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getMontant_physic() {
		return montant_physic;
	}

	public void setMontant_physic(double montant_physic) {
		this.montant_physic = montant_physic;
	}

	public double getMontant_valorise() {
		return montant_valorise;
	}

	public void setMontant_valorise(double montant_valorise) {
		this.montant_valorise = montant_valorise;
	}

	public double getOld_sold_physic() {
		return old_sold_physic;
	}

	public void setOld_sold_physic(double old_sold_physic) {
		this.old_sold_physic = old_sold_physic;
	}

	public double getOld_sold_valorise() {
		return old_sold_valorise;
	}

	public void setOld_sold_valorise(double old_sold_valorise) {
		this.old_sold_valorise = old_sold_valorise;
	}

	public double getNew_sold_physic() {
		return new_sold_physic;
	}

	public void setNew_sold_physic(double new_sold_physic) {
		this.new_sold_physic = new_sold_physic;
	}

	public double getNew_sold_valorise() {
		return new_sold_valorise;
	}

	public void setNew_sold_valorise(double new_sold_valorise) {
		this.new_sold_valorise = new_sold_valorise;
	}

	public String getType_operation() {
		return type_operation;
	}

	public void setType_operation(String type_operation) {
		this.type_operation = type_operation;
	}
	
}
