package com.commercial.entities.schema.profoma_cmd_bl_fact;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.static_data.causes_facture_avoir;
import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="facture_avoir" , schema = "proforma_cmd_bl_fact")

public class facture_avoir implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client")
	private client client;
	
	@ManyToOne
	@JoinColumn(name = "registre_commerce")
	private registre_commerce registre_commerce;
	
	private String date;
	private String time;
	
	@Column(unique = true)
	private String numero;
	
	private double montant_ht;
	
	private double tva;
	
	private double montant_ttc;
	
	private String link_pdf;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	private facture facture;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	private String etat_sold = "non_solde";
	
	private double sold_rest = montant_ttc;
	
	@ManyToOne
	@JoinColumn(name = "cause")
	private causes_facture_avoir cause;
	
	public facture_avoir() {
		// TODO Auto-generated constructor stub
	}

	public facture_avoir(com.commercial.entities.schema.client.client client,
			com.commercial.entities.schema.client.registre_commerce registre_commerce, String date, String time,
			String numero, double montant_ht, double tva, double montant_ttc, String link_pdf,
			com.commercial.entities.schema.profoma_cmd_bl_fact.facture facture,
			com.commercial.entities.schema.user_menu.users users, String etat_sold, double sold_rest,
			causes_facture_avoir cause) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.montant_ht = montant_ht;
		this.tva = tva;
		this.montant_ttc = montant_ttc;
		this.link_pdf = link_pdf;
		this.facture = facture;
		this.users = users;
		this.etat_sold = etat_sold;
		this.sold_rest = sold_rest;
		this.cause = cause;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public client getClient() {
		return client;
	}

	public void setClient(client client) {
		this.client = client;
	}

	public registre_commerce getRegistre_commerce() {
		return registre_commerce;
	}

	public void setRegistre_commerce(registre_commerce registre_commerce) {
		this.registre_commerce = registre_commerce;
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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

	public double getMontant_ttc() {
		return montant_ttc;
	}

	public void setMontant_ttc(double montant_ttc) {
		this.montant_ttc = montant_ttc;
	}

	public String getLink_pdf() {
		return link_pdf;
	}

	public void setLink_pdf(String link_pdf) {
		this.link_pdf = link_pdf;
	}

	public facture getFacture() {
		return facture;
	}

	public void setFacture(facture facture) {
		this.facture = facture;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	public String getEtat_sold() {
		return etat_sold;
	}

	public void setEtat_sold(String etat_sold) {
		this.etat_sold = etat_sold;
	}

	public double getSold_rest() {
		return sold_rest;
	}

	public void setSold_rest(double sold_rest) {
		this.sold_rest = sold_rest;
	}

	public causes_facture_avoir getCause() {
		return cause;
	}

	public void setCause(causes_facture_avoir cause) {
		this.cause = cause;
	}
	
}
