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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.client.*;
import com.commercial.entities.schema.user_menu.*;

@Entity

@Table(name="proforma" , schema = "proforma_cmd_bl_fact")

public class proforma implements Serializable{
	
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
	
	private double total_ht;
	
	private double total_tva=0;
	
	private double total_ttc;
	
	@OneToOne
	@JoinColumn(name = "commande")
	private commande commande;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	public proforma() {
		// TODO Auto-generated constructor stub
	}

	public proforma(client client, registre_commerce registre_commerce, String date, String time,
			String numero, List<proforma_detail> pf_d, users users) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.total_ht = pf_d.stream().filter(pd -> pd != null).mapToDouble(proforma_detail::getMontant_ht).sum();
		this.total_tva = pf_d.stream().filter(pd -> pd != null).mapToDouble(proforma_detail::getMontant_tva).sum();
		this.total_ttc = pf_d.stream().filter(pd -> pd != null).mapToDouble(proforma_detail::getMontant_ttc).sum();
		this.users = users;
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

	public double getTotal_ht() {
		return total_ht;
	}

	public void setTotal_ht(double total_ht) {
		this.total_ht = total_ht;
	}

	public double getTotal_tva() {
		return total_tva;
	}

	public void setTotal_tva(double total_tva) {
		this.total_tva = total_tva;
	}

	public double getTotal_ttc() {
		return total_ttc;
	}

	public void setTotal_ttc(double total_ttc) {
		this.total_ttc = total_ttc;
	}

	public commande getCommande() {
		return commande;
	}

	public void setCommande(commande commande) {
		this.commande = commande;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}
	
}
