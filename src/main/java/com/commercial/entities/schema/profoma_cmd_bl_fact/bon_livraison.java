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

@Table(name="bon_livraison" , schema = "proforma_cmd_bl_fact")

public class bon_livraison implements Serializable{
	
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
	
	private String link_pdf;
	
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
	
	@Column(columnDefinition = "double precision default 0")
	private Double montant_ht;
	
	@Column(columnDefinition = "double precision default 0")
	private double pourcentage_reduction;
	
	@Column(columnDefinition = "double precision default 0")
	private double valeur_reduction;
	
	@Column(columnDefinition = "double precision default 0")
	private double montant_ht_net;
	
	@Column(columnDefinition = "double precision default 0")
	private Double tva;
	
	@Column(columnDefinition = "double precision default 0")
	private Double montant_ttc;
	
	@Column(columnDefinition="boolean default false")
	boolean cancel = false;
	
	public bon_livraison() {
		// TODO Auto-generated constructor stub
	}

	public bon_livraison(client client, registre_commerce registre_commerce, String date, String time,
			String numero, String matricule, String link_pdf, commande commande, facture facture, users users, int etat_livraison, 
			Double montant_ht, double pourcentage_reduction, double valeur_reduction, double montant_ht_net, Double tva,
			Double montant_ttc, boolean cancel) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.matricule = matricule;
		this.link_pdf = link_pdf;
		this.commande = commande;
		this.facture = facture;
		this.users = users;
		this.etat_livraison = etat_livraison;
		this.montant_ht = montant_ht;
		this.pourcentage_reduction = pourcentage_reduction;
		this.valeur_reduction = valeur_reduction;
		this.montant_ht_net = montant_ht_net;
		this.tva = tva;
		this.montant_ttc = montant_ttc;
		this.cancel = cancel;
	}
	
	public bon_livraison(client client, registre_commerce registre_commerce, String date, String time,
			String numero, String matricule, commande commande, users users) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.matricule = matricule;
		this.link_pdf = "";
		this.commande = commande;
		this.users = users;
		this.etat_livraison = 0;
		this.cancel = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMontant_ht(Double montant_ht) {
		this.montant_ht = montant_ht;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}

	public void setMontant_ttc(Double montant_ttc) {
		this.montant_ttc = montant_ttc;
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

	public String getLink_pdf() {
		return link_pdf;
	}

	public void setLink_pdf(String link_pdf) {
		this.link_pdf = link_pdf;
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

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public double getPourcentage_reduction() {
		return pourcentage_reduction;
	}

	public void setPourcentage_reduction(double pourcentage_reduction) {
		this.pourcentage_reduction = pourcentage_reduction;
	}

	public double getValeur_reduction() {
		return valeur_reduction;
	}

	public void setValeur_reduction(double valeur_reduction) {
		this.valeur_reduction = valeur_reduction;
	}

	public double getMontant_ht_net() {
		return montant_ht_net;
	}

	public void setMontant_ht_net(double montant_ht_net) {
		this.montant_ht_net = montant_ht_net;
	}
	
}
