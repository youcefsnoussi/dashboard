package com.commercial.entities.schema.user_menu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="menu" , schema = "user_menu")

public class menu implements Serializable{
	
	@Id @GeneratedValue
	
	private long id;
	private String nom_menu;
	
	@Column(columnDefinition = "integer default 0")
	private int ord = 0; //----------- order
	
	public menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public menu(String nom_menu, int ord) {
		super();
		this.nom_menu = nom_menu;
		this.ord = ord;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom_menu() {
		return nom_menu;
	}

	public void setNom_menu(String nom_menu) {
		this.nom_menu = nom_menu;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}
	
	

}
