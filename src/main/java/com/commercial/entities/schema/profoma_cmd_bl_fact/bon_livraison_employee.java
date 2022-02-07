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

import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="bon_livraison_employee" , schema = "proforma_cmd_bl_fact")

public class bon_livraison_employee  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String matricule_employee; 
	
	private String nom_employee; 
	
	private String prenom_employee;
	
	private String date;
	private String time;
	
	@Column(unique = true)
	private String numero;
	
	private String link_pdf;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	private facture facture;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	private int etat_livraison;
	
	@Column(columnDefinition = "double precision default 0")
	private double montant_ht;
	
	@Column(columnDefinition = "double precision default 0")
	private double montant_tva;
	
	@Column(columnDefinition = "double precision default 0")
	private double montant_ttc;
	
	@Column(columnDefinition="boolean default false")
	boolean cancel = false;
	
	@Column(columnDefinition="boolean default false")
	boolean factured = false;
	
	public bon_livraison_employee() {
		// TODO Auto-generated constructor stub
	}

	public bon_livraison_employee(String matricule_employee, String nom_employee, String prenom_employee, String date,
			String time, String numero, String link_pdf, facture facture, users users, int etat_livraison, double montant_ht, 
			double montant_tva, double montant_ttc, boolean cancel) {
		super();
		this.matricule_employee = matricule_employee;
		this.nom_employee = nom_employee;
		this.prenom_employee = prenom_employee;
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.link_pdf = link_pdf;
		this.facture = facture;
		this.users = users;
		this.etat_livraison = etat_livraison;
		this.montant_ht = montant_ht;
		this.montant_tva = montant_tva;
		this.montant_ttc = montant_ttc;
		this.cancel = cancel;
	}
	
	public bon_livraison_employee(String matricule_employee, String nom_employee, String prenom_employee, String date,
			String time, String numero, users users) {
		super();
		this.matricule_employee = matricule_employee;
		this.nom_employee = nom_employee;
		this.prenom_employee = prenom_employee;
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.link_pdf = "";
		this.facture = null;
		this.users = users;
		this.etat_livraison = 0;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricule_employee() {
		return matricule_employee;
	}

	public void setMatricule_employee(String matricule_employee) {
		this.matricule_employee = matricule_employee;
	}

	public String getNom_employee() {
		return nom_employee;
	}

	public void setNom_employee(String nom_employee) {
		this.nom_employee = nom_employee;
	}

	public String getPrenom_employee() {
		return prenom_employee;
	}

	public void setPrenom_employee(String prenom_employee) {
		this.prenom_employee = prenom_employee;
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

	public int getEtat_livraison() {
		return etat_livraison;
	}

	public void setEtat_livraison(int etat_livraison) {
		this.etat_livraison = etat_livraison;
	}

	public Double getMontant_ht() {
		return montant_ht;
	}

	public void setMontant_ht(double montant_ht) {
		this.montant_ht = montant_ht;
	}

	public Double getMontant_tva() {
		return montant_tva;
	}

	public void setMontant_tva(double montant_tva) {
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
