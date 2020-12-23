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
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.entities.schema.static_data.mode_paiement;;

@Entity

@Table(name="facture" , schema = "proforma_cmd_bl_fact")

public class facture implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client")
	private client client;
	
	@ManyToOne
	@JoinColumn(name = "registre_commerce")
	private registre_commerce registre_commerce;
	
	@ManyToOne
	@JoinColumn(name = "client_registrecommerce")
	private client_registreCommerce client_registrecommerce;
	
	private String date;
	private String time;
	
	@Column(unique = true)
	private String numero;
	
	private double montant_ht;
	
	private double tva;
	
	private String matricule_camion;
	
	private double montant_ttc;
	
	private double montant_tva;
	
	private String link_pdf;
	
	@ManyToOne
	@JoinColumn(name = "bon_livraison")
	private bon_livraison bon_livraison;
	
	@ManyToOne
	@JoinColumn(name = "mode_paiement")
	private mode_paiement mode_paiement;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	private boolean etat_sold;
	
	private double sold_rest = montant_ttc;
	
	private Boolean printed;
	
	private Boolean notification = false;
	
	@Column(columnDefinition = "double precision default 0")
	private double pourcentage_reduction;
	
	public facture() {
		// TODO Auto-generated constructor stub
	}

	public facture(com.commercial.entities.schema.client.client client,
			com.commercial.entities.schema.client.registre_commerce registre_commerce,
			client_registreCommerce client_registrecommerce, String date, String time, String numero, double montant_ht,
			double tva, String matricule_camion, double montant_ttc, double montant_tva, String link_pdf,
			com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison bon_livraison,
			com.commercial.entities.schema.static_data.mode_paiement mode_paiement,
			com.commercial.entities.schema.user_menu.users users, boolean etat_sold, double sold_rest, Boolean printed,
			Boolean notification, double pourcentage_reduction) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.client_registrecommerce = client_registrecommerce;
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.montant_ht = montant_ht;
		this.tva = tva;
		this.matricule_camion = matricule_camion;
		this.montant_ttc = montant_ttc;
		this.montant_tva = montant_tva;
		this.link_pdf = link_pdf;
		this.bon_livraison = bon_livraison;
		this.mode_paiement = mode_paiement;
		this.users = users;
		this.etat_sold = etat_sold;
		this.sold_rest = sold_rest;
		this.printed = printed;
		this.notification = notification;
		this.pourcentage_reduction = pourcentage_reduction;
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

	public client_registreCommerce getClient_registrecommerce() {
		return client_registrecommerce;
	}

	public void setClient_registrecommerce(client_registreCommerce client_registrecommerce) {
		this.client_registrecommerce = client_registrecommerce;
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

	public String getMatricule_camion() {
		return matricule_camion;
	}

	public void setMatricule_camion(String matricule_camion) {
		this.matricule_camion = matricule_camion;
	}

	public double getMontant_ttc() {
		return montant_ttc;
	}

	public void setMontant_ttc(double montant_ttc) {
		this.montant_ttc = montant_ttc;
	}

	public double getMontant_tva() {
		return montant_tva;
	}

	public void setMontant_tva(double montant_tva) {
		this.montant_tva = montant_tva;
	}

	public String getLink_pdf() {
		return link_pdf;
	}

	public void setLink_pdf(String link_pdf) {
		this.link_pdf = link_pdf;
	}

	public bon_livraison getBon_livraison() {
		return bon_livraison;
	}

	public void setBon_livraison(bon_livraison bon_livraison) {
		this.bon_livraison = bon_livraison;
	}

	public mode_paiement getMode_paiement() {
		return mode_paiement;
	}

	public void setMode_paiement(mode_paiement mode_paiement) {
		this.mode_paiement = mode_paiement;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	public boolean isEtat_sold() {
		return etat_sold;
	}

	public void setEtat_sold(boolean etat_sold) {
		this.etat_sold = etat_sold;
	}

	public double getSold_rest() {
		return sold_rest;
	}

	public void setSold_rest(double sold_rest) {
		this.sold_rest = sold_rest;
	}

	public Boolean getPrinted() {
		return printed;
	}

	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}

	public Boolean getNotification() {
		return notification;
	}

	public void setNotification(Boolean notification) {
		this.notification = notification;
	}

	public double getPourcentage_reduction() {
		return pourcentage_reduction;
	}

	public void setPourcentage_reduction(double pourcentage_reduction) {
		this.pourcentage_reduction = pourcentage_reduction;
	}

}
