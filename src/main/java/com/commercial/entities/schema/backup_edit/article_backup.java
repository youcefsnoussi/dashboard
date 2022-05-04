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

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.emballage_produit;
import com.commercial.entities.schema.article.pesage_produit;
import com.commercial.entities.schema.article.produit;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;


@Entity

@Table(name="article_backup" , schema = "backup_edit")

public class article_backup implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "produit")
	private produit produit;
	
	@ManyToOne
	@JoinColumn(name = "emballage_produit")
	private emballage_produit emballage_produit;
	
	@ManyToOne
	@JoinColumn(name = "pesage_produit")
	private pesage_produit pesage_produit;
	
	private String image_article;
	
	@Column(name="vendu",columnDefinition = "numeric default 0")
	private double vendu = 0; //-------------- unite
	
	private String date_creation;
	
	@ManyToOne
	@JoinColumn(name = "unite_mesure_vente")
	private unite_mesure unite_mesure_vente;
	
	private long id_article;
	
	private String date;
	
	private String time;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	@Column(columnDefinition="boolean default false")
	private boolean subvension = false;
	
	public article_backup() {
		super();
		// TODO Auto-generated constructor stub
	}

	public article_backup(String code, com.commercial.entities.schema.article.produit produit,
			com.commercial.entities.schema.article.emballage_produit emballage_produit,
			com.commercial.entities.schema.article.pesage_produit pesage_produit, String image_article, double vendu,
			String date_creation, String code_comptable, unite_mesure unite_mesure_vente, long id_article, users user, boolean sub) {
		
		super();
		
		get_time_date gtd = new get_time_date();
		
		this.code = code;
		this.produit = produit;
		this.emballage_produit = emballage_produit;
		this.pesage_produit = pesage_produit;
		this.image_article = image_article;
		this.vendu = vendu;
		this.date_creation = date_creation;
		this.unite_mesure_vente = unite_mesure_vente;
		this.id_article = id_article;
		this.date = gtd.get_date();
		this.time = gtd.get_time();
		this.users = user;
		this.subvension = sub;
	}

	public article_backup (article art, users user) {
		
		super();
		
		get_time_date gtd = new get_time_date();
		
		this.code = art.getCode();
		this.produit = art.getProduit();
		this.emballage_produit = art.getEmballage_produit();
		this.pesage_produit = art.getPesage_produit();
		this.image_article = art.getImage_article();
		this.vendu = art.getVendu();
		this.date_creation = art.getDate_creation();
		this.unite_mesure_vente = art.getUnite_mesure_vente();
		this.id_article = art.getId();
		this.date = gtd.get_date();
		this.time = gtd.get_time();
		this.users = user;
		this.subvension = art.isSubvension();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public produit getProduit() {
		return produit;
	}

	public void setProduit(produit produit) {
		this.produit = produit;
	}

	public emballage_produit getEmballage_produit() {
		return emballage_produit;
	}

	public void setEmballage_produit(emballage_produit emballage_produit) {
		this.emballage_produit = emballage_produit;
	}

	public pesage_produit getPesage_produit() {
		return pesage_produit;
	}

	public void setPesage_produit(pesage_produit pesage_produit) {
		this.pesage_produit = pesage_produit;
	}

	public String getImage_article() {
		return image_article;
	}

	public void setImage_article(String image_article) {
		this.image_article = image_article;
	}

	public double getVendu() {
		return vendu;
	}

	public void setVendu(double vendu) {
		this.vendu = vendu;
	}

	public String getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(String date_creation) {
		this.date_creation = date_creation;
	}

	public unite_mesure getUnite_mesure_vente() {
		return unite_mesure_vente;
	}

	public void setUnite_mesure_vente(unite_mesure unite_mesure_vente) {
		this.unite_mesure_vente = unite_mesure_vente;
	}

	public long getId_article() {
		return id_article;
	}

	public void setId_article(long id_article) {
		this.id_article = id_article;
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

	public boolean isSubvension() {
		return subvension;
	}

	public void setSubvension(boolean subvension) {
		this.subvension = subvension;
	}

	
	
}
