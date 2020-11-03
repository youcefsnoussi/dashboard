package com.commercial.entities.schema.backup_edit;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;

@Entity

@Table(name="registre_commerce_backup" , schema = "backup_edit")

public class registre_commerce_backup implements Serializable{
	
	@Id @GeneratedValue
	 private long id;
	 
	 private String nom;
	 private String prenom;
	 
	 private String numero_rc;
	 
	 private String numero_art;
	 
	 private String numero_nif;
	 
	 private String date_emission;
	 
	 private String date_fin;
	 
	 private String adresse;
	 private String comune;
	 private String wilaya;
	 
	 private String etat; //------------------> etat tweli desactivé ki ifout la date
	 
	 private double tva; //-------------------> 0 matetebakch alih tva / 1 3akss
	 
	 private double plafond;
	 
	 private double sold_encours;
	 
	 private String activite;
	 
	 private String etat_blockage = "active";
	 
	 private long id_rc;
	 
	 private String date;
		
	 private String time;
	
	 @ManyToOne
	 @JoinColumn(name = "users")
	 private users users;
	 
	public registre_commerce_backup() {
		// TODO Auto-generated constructor stub
		super();
	}

	public registre_commerce_backup(String nom, String prenom, String numero_rc, String numero_art, String numero_nif,
			String date_emission, String date_fin, String adresse, String comune, String wilaya, String etat,
			double tva, double plafond, double sold_encours, String activite, String etat_blockage, long id_rc, users user) {
		super();
		
		get_time_date gtd = new get_time_date();
		
		this.nom = nom;
		this.prenom = prenom;
		this.numero_rc = numero_rc;
		this.numero_art = numero_art;
		this.numero_nif = numero_nif;
		this.date_emission = date_emission;
		this.date_fin = date_fin;
		this.adresse = adresse;
		this.comune = comune;
		this.wilaya = wilaya;
		this.etat = etat;
		this.tva = tva;
		this.plafond = plafond;
		this.sold_encours = sold_encours;
		this.activite = activite;
		this.etat_blockage = etat_blockage;
		this.id_rc = id_rc;
		this.date = gtd.get_date();
		this.time = gtd.get_time();
		this.users = user;
		
	}

	public registre_commerce_backup(registre_commerce rc, users user) {
		
		super();
		
		get_time_date gtd = new get_time_date();
		
		this.nom = rc.getNom();
		this.prenom = rc.getPrenom();
		this.numero_rc = rc.getNumero_rc();
		this.numero_art = rc.getNumero_art();
		this.numero_nif = rc.getNumero_nif();
		this.date_emission = rc.getDate_emission();
		this.date_fin = rc.getDate_fin();
		this.adresse = rc.getAdresse();
		this.comune = rc.getComune();
		this.wilaya = rc.getWilaya();
		this.etat = rc.getEtat();
		this.tva = rc.getTva();
		this.plafond = rc.getPlafond();
		this.sold_encours = rc.getSold_encours();
		this.activite = rc.getActivite();
		this.etat_blockage = rc.getEtat_blockage();
		this.id_rc = rc.getId();
		this.date = gtd.get_date();
		this.time = gtd.get_time();
		this.users = user;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNumero_rc() {
		return numero_rc;
	}

	public void setNumero_rc(String numero_rc) {
		this.numero_rc = numero_rc;
	}

	public String getNumero_art() {
		return numero_art;
	}

	public void setNumero_art(String numero_art) {
		this.numero_art = numero_art;
	}

	public String getNumero_nif() {
		return numero_nif;
	}

	public void setNumero_nif(String numero_nif) {
		this.numero_nif = numero_nif;
	}

	public String getDate_emission() {
		return date_emission;
	}

	public void setDate_emission(String date_emission) {
		this.date_emission = date_emission;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getWilaya() {
		return wilaya;
	}

	public void setWilaya(String wilaya) {
		this.wilaya = wilaya;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public double getTva() {
		return tva;
	}

	public void setTva(double tva) {
		this.tva = tva;
	}

	public double getPlafond() {
		return plafond;
	}

	public void setPlafond(double plafond) {
		this.plafond = plafond;
	}

	public double getSold_encours() {
		return sold_encours;
	}

	public void setSold_encours(double sold_encours) {
		this.sold_encours = sold_encours;
	}

	public String getActivite() {
		return activite;
	}

	public void setActivite(String activite) {
		this.activite = activite;
	}

	public String getEtat_blockage() {
		return etat_blockage;
	}

	public void setEtat_blockage(String etat_blockage) {
		this.etat_blockage = etat_blockage;
	}

	public long getId_rc() {
		return id_rc;
	}

	public void setId_rc(long id_rc) {
		this.id_rc = id_rc;
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

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}
	
}
