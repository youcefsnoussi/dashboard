package com.commercial.entities.schema.user_menu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
//import javax.persistence.SecondaryTables;
import javax.persistence.Table;

@Entity

@Table(name="sub_menu" , schema = "user_menu")

public class sub_menu implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom_submenu;
	private String url_submenu;
	
	@Column(columnDefinition = "integer default 0")
	private int ord = 0; //----------- order
	
	@ManyToOne
    @JoinColumn(name = "menu")
	private menu menu;
	
	private String ids_banned;
	private String icone;
	
	public sub_menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public sub_menu(String nom_submenu, String url_submenu, int ord, menu menu, String ids_banned, String icone) {
		super();
		this.nom_submenu = nom_submenu;
		this.url_submenu = url_submenu;
		this.ord = ord;
		this.menu = menu;
		this.ids_banned = ids_banned;
		this.icone = icone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom_submenu() {
		return nom_submenu;
	}

	public void setNom_submenu(String nom_submenu) {
		this.nom_submenu = nom_submenu;
	}

	public String getUrl_submenu() {
		return url_submenu;
	}

	public void setUrl_submenu(String url_submenu) {
		this.url_submenu = url_submenu;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public menu getMenu() {
		return menu;
	}

	public void setMenu(menu menu) {
		this.menu = menu;
	}

	public String getIds_banned() {
		return ids_banned;
	}

	public void setIds_banned(String ids_banned) {
		this.ids_banned = ids_banned;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}
	
	
	
}
