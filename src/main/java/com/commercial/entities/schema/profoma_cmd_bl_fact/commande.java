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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.static_data.mode_paiement;
import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="commande" , schema = "proforma_cmd_bl_fact")

public class commande implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String date;
	private String time;
	
	@Column(unique = true)
	private String numero;
	
	private double montant_ht;
	
	private double tva;
	
	private double montant_ttc;
	
	private String link_pdf;
	
	@OneToOne
	@JoinColumn(name = "proforma")
	private proforma proforma;
	
	@ManyToMany
	@JoinColumn(name = "bon_livraison")
	private List <bon_livraison> list_bon_livraison;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	private boolean cloturer = false;
	
	private String matricule;
	
	@ManyToOne
	@JoinColumn(name = "mode_paiement")
	private mode_paiement mode_paiement;
	
	@ManyToOne
	@JoinColumn(name = "client_registrecommerce")
	private client_registreCommerce client_registrecommerce;
	
	public commande() {
		// TODO Auto-generated constructor stub
	}

	public commande(String date, String time, String numero, double montant_ht, double tva, double montant_ttc,
			String link_pdf, com.commercial.entities.schema.profoma_cmd_bl_fact.proforma proforma,
			List<bon_livraison> list_bon_livraison, com.commercial.entities.schema.user_menu.users users,
			boolean cloturer, String matricule, com.commercial.entities.schema.static_data.mode_paiement mode_paiement,
			client_registreCommerce client_registrecommerce) {
		super();
		this.date = date;
		this.time = time;
		this.numero = numero;
		this.montant_ht = montant_ht;
		this.tva = tva;
		this.montant_ttc = montant_ttc;
		this.link_pdf = link_pdf;
		this.proforma = proforma;
		this.list_bon_livraison = list_bon_livraison;
		this.users = users;
		this.cloturer = cloturer;
		this.matricule = matricule;
		this.mode_paiement = mode_paiement;
		this.client_registrecommerce = client_registrecommerce;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public double getMontant_ttc() {
		return montant_ttc;
	}

	public void setMontant_ttc(double montant_ttc) {
		this.montant_ttc = montant_ttc;
	}

	public String getLink_pdf() {
		return link_pdf;
	}

	public void setLink_pdf(String link_pdf) {
		this.link_pdf = link_pdf;
	}

	public proforma getProforma() {
		return proforma;
	}

	public void setProforma(proforma proforma) {
		this.proforma = proforma;
	}

	public List<bon_livraison> getList_bon_livraison() {
		return list_bon_livraison;
	}

	public void setList_bon_livraison(List<bon_livraison> list_bon_livraison) {
		this.list_bon_livraison = list_bon_livraison;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	public boolean isCloturer() {
		return cloturer;
	}

	public void setCloturer(boolean cloturer) {
		this.cloturer = cloturer;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public mode_paiement getMode_paiement() {
		return mode_paiement;
	}

	public void setMode_paiement(mode_paiement mode_paiement) {
		this.mode_paiement = mode_paiement;
	}

	public client_registreCommerce getClient_registrecommerce() {
		return client_registrecommerce;
	}

	public void setClient_registrecommerce(client_registreCommerce client_registrecommerce) {
		this.client_registrecommerce = client_registrecommerce;
	}

	

}
