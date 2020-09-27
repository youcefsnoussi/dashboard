package com.commercial.entities.schema.profoma_cmd_bl_fact;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;

@Entity

@Table(name="prof_cmd_bl_fact_client_rc_avoir" , schema = "proforma_cmd_bl_fact")

public class prof_cmd_bl_fact_client_rc_avoir implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "client_registrecommerce")
	private client_registreCommerce client_registrecommerce;
	
	@ManyToOne
	@JoinColumn(name = "profrma")
	private proforma profrma;
	
	@ManyToOne
	@JoinColumn(name = "commande")
	private commande commande;
	
	@ManyToOne
	@JoinColumn(name = "bon_livraison")
	private bon_livraison bon_livraison;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	private facture facture;
	
	@ManyToOne
	@JoinColumn(name = "facture_avoir")
	private facture_avoir facture_avoir;
	
	public prof_cmd_bl_fact_client_rc_avoir() {
		// TODO Auto-generated constructor stub
	}

	public prof_cmd_bl_fact_client_rc_avoir(client_registreCommerce client_registrecommerce, proforma profrma,
			com.commercial.entities.schema.profoma_cmd_bl_fact.commande commande,
			com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison bon_livraison,
			com.commercial.entities.schema.profoma_cmd_bl_fact.facture facture,
			com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir facture_avoir) {
		super();
		this.client_registrecommerce = client_registrecommerce;
		this.profrma = profrma;
		this.commande = commande;
		this.bon_livraison = bon_livraison;
		this.facture = facture;
		this.facture_avoir = facture_avoir;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public client_registreCommerce getClient_registrecommerce() {
		return client_registrecommerce;
	}

	public void setClient_registrecommerce(client_registreCommerce client_registrecommerce) {
		this.client_registrecommerce = client_registrecommerce;
	}

	public proforma getProfrma() {
		return profrma;
	}

	public void setProfrma(proforma profrma) {
		this.profrma = profrma;
	}

	public commande getCommande() {
		return commande;
	}

	public void setCommande(commande commande) {
		this.commande = commande;
	}

	public bon_livraison getBon_livraison() {
		return bon_livraison;
	}

	public void setBon_livraison(bon_livraison bon_livraison) {
		this.bon_livraison = bon_livraison;
	}

	public facture getFacture() {
		return facture;
	}

	public void setFacture(facture facture) {
		this.facture = facture;
	}

	public facture_avoir getFacture_avoir() {
		return facture_avoir;
	}

	public void setFacture_avoir(facture_avoir facture_avoir) {
		this.facture_avoir = facture_avoir;
	}

}
