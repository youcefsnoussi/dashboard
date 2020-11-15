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

import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="BonCommande" , schema = "proforma_cmd_bl_fact")

public class BonCommande implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String date_insert;
	private String time;
	
	private String date_debut;
	
	private String date_fin;
	
	@Column(unique = true)
	private String numero;
	
	@ManyToOne
	@JoinColumn(name = "client_registrecommerce")
	private client_registreCommerce client_registrecommerce;
	
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	public BonCommande() {
		// TODO Auto-generated constructor stub
	}

	public BonCommande(String date_insert, String time, String date_debut, String date_fin, String numero,
			client_registreCommerce client_registrecommerce, String image,
			com.commercial.entities.schema.user_menu.users users) {
		super();
		this.date_insert = date_insert;
		this.time = time;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.numero = numero;
		this.client_registrecommerce = client_registrecommerce;
		this.image = image;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate_insert() {
		return date_insert;
	}

	public void setDate_insert(String date_insert) {
		this.date_insert = date_insert;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public client_registreCommerce getClient_registrecommerce() {
		return client_registrecommerce;
	}

	public void setClient_registrecommerce(client_registreCommerce client_registrecommerce) {
		this.client_registrecommerce = client_registrecommerce;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	
	
	
}
