package com.commercial.entities.schema.backup_edit;

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
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.commande;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;


@Entity

@Table(name="bon_livraison_backup" , schema = "backup_edit")

public class bon_livraison_backup implements Serializable{
	
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
	
	private Double montant_ht;
	
	private Double tva;
	
	private Double montant_ttc;
	
	private long id_bl;
	
	private String date_edit;
	
	private String time_edit;
	
	@ManyToOne
	@JoinColumn(name = "user_edit")
	private users user_edit;
	 
	public bon_livraison_backup() {
		// TODO Auto-generated constructor stub
		super();
	}

	public bon_livraison_backup(com.commercial.entities.schema.client.client client,
			com.commercial.entities.schema.client.registre_commerce registre_commerce, String date, String time,
			String numero, String matricule, String link_pdf,
			com.commercial.entities.schema.profoma_cmd_bl_fact.commande commande,
			com.commercial.entities.schema.profoma_cmd_bl_fact.facture facture,
			com.commercial.entities.schema.user_menu.users users, int etat_livraison, Double montant_ht, Double tva,
			Double montant_ttc, long id_bl, users user) {
		super();
		
		get_time_date gtd = new get_time_date();
		
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
		this.tva = tva;
		this.montant_ttc = montant_ttc;
		this.id_bl = id_bl;
		this.date_edit = gtd.get_date();
		this.time_edit = gtd.get_time();
		this.user_edit = user;
		
	}
	
	public bon_livraison_backup(bon_livraison bl, users user) {
		
		super();
		
		get_time_date gtd = new get_time_date();
		
		this.client = bl.getClient();
		this.registre_commerce = bl.getRegistre_commerce();
		this.date = bl.getDate();
		this.time = bl.getTime();
		this.numero = bl.getNumero();
		this.matricule = bl.getMatricule();
		this.link_pdf = bl.getLink_pdf();
		this.commande = bl.getCommande();
		this.facture = bl.getFacture();
		this.users = bl.getUsers();
		this.etat_livraison = bl.getEtat_livraison();
		this.montant_ht = bl.getMontant_ht();
		this.tva = bl.getTva();
		this.montant_ttc = bl.getMontant_ttc();
		this.id_bl = bl.getId();
		this.date_edit = gtd.get_date();
		this.time_edit = gtd.get_time();
		this.user_edit = user;
		
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

	public Double getMontant_ht() {
		return montant_ht;
	}

	public void setMontant_ht(Double montant_ht) {
		this.montant_ht = montant_ht;
	}

	public Double getTva() {
		return tva;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}

	public Double getMontant_ttc() {
		return montant_ttc;
	}

	public void setMontant_ttc(Double montant_ttc) {
		this.montant_ttc = montant_ttc;
	}

	public long getId_bl() {
		return id_bl;
	}

	public void setId_bl(long id_bl) {
		this.id_bl = id_bl;
	}

	public String getDate_edit() {
		return date_edit;
	}

	public void setDate_edit(String date_edit) {
		this.date_edit = date_edit;
	}

	public String getTime_edit() {
		return time_edit;
	}

	public void setTime_edit(String time_edit) {
		this.time_edit = time_edit;
	}

	public users getUser_edit() {
		return user_edit;
	}

	public void setUser_edit(users user_edit) {
		this.user_edit = user_edit;
	}
	
	
	
}
