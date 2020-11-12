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

@Entity

@Table(name="paiement_facture" , schema = "proforma_cmd_bl_fact")

public class paiement_facture implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "paiement")
	paiement paiement;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	facture facture;
	
	private double montant_paye_facture;
	
	
	public paiement_facture() {
		// TODO Auto-generated constructor stub
	}


	public paiement_facture(com.commercial.entities.schema.profoma_cmd_bl_fact.paiement paiement,
			com.commercial.entities.schema.profoma_cmd_bl_fact.facture facture, double montant_paye_facture) {
		super();
		this.paiement = paiement;
		this.facture = facture;
		this.montant_paye_facture = montant_paye_facture;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public paiement getPaiement() {
		return paiement;
	}


	public void setPaiement(paiement paiement) {
		this.paiement = paiement;
	}


	public facture getFacture() {
		return facture;
	}


	public void setFacture(facture facture) {
		this.facture = facture;
	}


	public double getMontant_paye_facture() {
		return montant_paye_facture;
	}


	public void setMontant_paye_facture(double montant_paye_facture) {
		this.montant_paye_facture = montant_paye_facture;
	}
	
	
}
