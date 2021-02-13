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

import com.commercial.entities.schema.client.unite_interne;
import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="bon_transfert" , schema = "proforma_cmd_bl_fact")

public class bon_transfert implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "unite_interne")
	private unite_interne unite_interne;
	
	@Column(unique = true)
	private String numero;
	
	private String matricule_vehicule;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	@Column(columnDefinition="boolean default false")
	private boolean cancel = false;
	
	private String chauffeur;
	
	private String matricule_chauffeur;
	
	private String date; 
	
	private String time; 
	
	public bon_transfert() {
		// TODO Auto-generated constructor stub
	}

	public bon_transfert(com.commercial.entities.schema.client.unite_interne unite_interne, String numero,
			String matricule_vehicule, com.commercial.entities.schema.user_menu.users users, boolean cancel,
			String chauffeur, String matricule_chauffeur, String date, String time) {
		super();
		this.unite_interne = unite_interne;
		this.numero = numero;
		this.matricule_vehicule = matricule_vehicule;
		this.users = users;
		this.cancel = cancel;
		this.chauffeur = chauffeur;
		this.matricule_chauffeur = matricule_chauffeur;
		this.date = date;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public unite_interne getUnite_interne() {
		return unite_interne;
	}

	public void setUnite_interne(unite_interne unite_interne) {
		this.unite_interne = unite_interne;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMatricule_vehicule() {
		return matricule_vehicule;
	}

	public void setMatricule_vehicule(String matricule_vehicule) {
		this.matricule_vehicule = matricule_vehicule;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public String getChauffeur() {
		return chauffeur;
	}

	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}

	public String getMatricule_chauffeur() {
		return matricule_chauffeur;
	}

	public void setMatricule_chauffeur(String matricule_chauffeur) {
		this.matricule_chauffeur = matricule_chauffeur;
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

	
	
}
