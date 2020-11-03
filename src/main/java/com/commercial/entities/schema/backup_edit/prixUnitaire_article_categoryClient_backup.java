package com.commercial.entities.schema.backup_edit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;

@Entity

@Table(name="prix_unitaire_article_category_client_backup" , schema = "backup_edit")

public class prixUnitaire_article_categoryClient_backup implements Serializable{
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	@ManyToOne
	@JoinColumn(name = "category_client")
	private category_client category_client;
	
	private double prix;
	
	@ManyToOne
	@JoinColumn(name = "tva")
	private tva tva;
	
	private long id_prix_category;
	
	private String date;
	
	private String time;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users user;
	
	public prixUnitaire_article_categoryClient_backup() {
		// TODO Auto-generated constructor stub
		super();
	}

	public prixUnitaire_article_categoryClient_backup(com.commercial.entities.schema.article.article article,
			com.commercial.entities.schema.client.category_client category_client, double prix,
			com.commercial.entities.schema.static_data.tva tva, long id_prix_category, String date, String time,
			users user) {
		super();
		this.article = article;
		this.category_client = category_client;
		this.prix = prix;
		this.tva = tva;
		this.id_prix_category = id_prix_category;
		this.date = date;
		this.time = time;
		this.user = user;
	}

	public prixUnitaire_article_categoryClient_backup(prixUnitaire_article_categoryClient p, users user) {
		
		super();
		
		get_time_date gtd = new get_time_date();
		
		this.article = p.getArticle();
		this.category_client = p.getCategory_client();
		this.prix = p.getPrix();
		this.tva = p.getTva();
		this.id_prix_category = p.getId();
		this.date = gtd.get_date();
		this.time = gtd.get_time();
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public article getArticle() {
		return article;
	}

	public void setArticle(article article) {
		this.article = article;
	}

	public category_client getCategory_client() {
		return category_client;
	}

	public void setCategory_client(category_client category_client) {
		this.category_client = category_client;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public tva getTva() {
		return tva;
	}

	public void setTva(tva tva) {
		this.tva = tva;
	}

	public long getId_prix_category() {
		return id_prix_category;
	}

	public void setId_prix_category(long id_prix_category) {
		this.id_prix_category = id_prix_category;
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

	public users getUser() {
		return user;
	}

	public void setUser(users user) {
		this.user = user;
	}
	
}
