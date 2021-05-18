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

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="bon_livraison_facture" , schema = "proforma_cmd_bl_fact")

public class bon_livraison_facture implements Serializable{
	
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
	
	private String matricule;
	
	@ManyToOne
	@JoinColumn(name = "commande")
	private commande commande;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	private facture facture;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	private int etat_livraison;
	
	private Double montant_ht;
	
	private Double montant_tva;
	
	private Double montant_ttc;
	
	@Column(columnDefinition="double precision default 0")
	private double montant_remise;
	
	@Column(columnDefinition="double precision default 0")
	private double montant_ht_net;
	
	@Column(columnDefinition="boolean default false")
	private boolean cancel = false;
	
	@Column(columnDefinition="boolean default false")
	private boolean factured = false;
	
	private String chauffeur;
	
	public bon_livraison_facture() {
		// TODO Auto-generated constructor stub
	}

	

	public bon_livraison_facture(com.commercial.entities.schema.client.client client,
			com.commercial.entities.schema.client.registre_commerce registre_commerce, String date, String time,
			String numero, String matricule, com.commercial.entities.schema.profoma_cmd_bl_fact.commande commande,
			com.commercial.entities.schema.user_menu.users users, int etat_livraison, Double montant_ht,
			Double montant_tva, Double montant_ttc, double montant_remise, double montant_ht_net, String chauffeur) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.matricule = matricule;
		this.commande = commande;
		this.users = users;
		this.etat_livraison = etat_livraison;
		this.montant_ht = montant_ht;
		this.montant_tva = montant_tva;
		this.montant_ttc = montant_ttc;
		this.montant_remise = montant_remise;
		this.montant_ht_net = montant_ht_net;
		this.chauffeur = chauffeur;
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



	public String getChauffeur() {
		return chauffeur;
	}



	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public commande getCommande() {
		return commande;
	}

	public void setCommande(commande commande) {
		this.commande = commande;
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

	public int getEtat_livraison() {
		return etat_livraison;
	}

	public void setEtat_livraison(int etat_livraison) {
		this.etat_livraison = etat_livraison;
	}

	public Double getMontant_ht() {
		return montant_ht;
	}

	public void setMontant_ht(Double montant_ht) {
		this.montant_ht = montant_ht;
	}

	public Double getMontant_tva() {
		return montant_tva;
	}

	public void setMontant_tva(Double montant_tva) {
		this.montant_tva = montant_tva;
	}

	public Double getMontant_ttc() {
		return montant_ttc;
	}

	public void setMontant_ttc(Double montant_ttc) {
		this.montant_ttc = montant_ttc;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public boolean isFactured() {
		return factured;
	}

	public void setFactured(boolean factured) {
		this.factured = factured;
	}
	
	

	
	
}
