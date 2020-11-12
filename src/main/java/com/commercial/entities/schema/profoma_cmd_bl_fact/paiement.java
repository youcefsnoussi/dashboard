package com.commercial.entities.schema.profoma_cmd_bl_fact;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.user_menu.users;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.static_data.banque;
import com.commercial.entities.schema.static_data.mode_paiement;

@Entity

@Table(name="paiement" , schema = "proforma_cmd_bl_fact")

public class paiement implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "client")
	private client client;
	
	@ManyToOne
	@JoinColumn(name = "registre_commerce")
	private registre_commerce registre_commerce;
	
	private double montant;
	
	private String date;
	private String date_saisie;
	private String time_saisie;
	
	@ManyToOne
	@JoinColumn(name="mode_paiement")
	private mode_paiement mode_paiement;
	
	@ManyToOne
	@JoinColumn(name="banque")
	private banque banque;
	
	@ManyToOne
	@JoinColumn(name="users")
	private users users;
	
	private String numero_piece;
	
	private String path_img;
	
	private boolean cancel;
	
	public paiement() {
		// TODO Auto-generated constructor stub
	}

	public paiement(com.commercial.entities.schema.client.client client,
			com.commercial.entities.schema.client.registre_commerce registre_commerce, double montant, String date,
			String date_saisie, String time_saisie,
			com.commercial.entities.schema.static_data.mode_paiement mode_paiement,
			com.commercial.entities.schema.static_data.banque banque,
			com.commercial.entities.schema.user_menu.users users, String numero_piece, String path_img,
			boolean cancel) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.montant = montant;
		this.date = date;
		this.date_saisie = date_saisie;
		this.time_saisie = time_saisie;
		this.mode_paiement = mode_paiement;
		this.banque = banque;
		this.users = users;
		this.numero_piece = numero_piece;
		this.path_img = path_img;
		this.cancel = cancel;
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

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate_saisie() {
		return date_saisie;
	}

	public void setDate_saisie(String date_saisie) {
		this.date_saisie = date_saisie;
	}

	public String getTime_saisie() {
		return time_saisie;
	}

	public void setTime_saisie(String time_saisie) {
		this.time_saisie = time_saisie;
	}

	public mode_paiement getMode_paiement() {
		return mode_paiement;
	}

	public void setMode_paiement(mode_paiement mode_paiement) {
		this.mode_paiement = mode_paiement;
	}

	public banque getBanque() {
		return banque;
	}

	public void setBanque(banque banque) {
		this.banque = banque;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	public String getNumero_piece() {
		return numero_piece;
	}

	public void setNumero_piece(String numero_piece) {
		this.numero_piece = numero_piece;
	}

	public String getPath_img() {
		return path_img;
	}

	public void setPath_img(String path_img) {
		this.path_img = path_img;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	
	
	
}
