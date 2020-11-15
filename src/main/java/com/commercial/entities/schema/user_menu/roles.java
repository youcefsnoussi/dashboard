package com.commercial.entities.schema.user_menu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="roles" , schema = "user_menu")

public class roles   implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom_role;
	private String ids_banned;
	
	public roles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public roles(String nom_role, String ids_banned) {
		super();
		this.nom_role = nom_role;
		this.ids_banned = ids_banned;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom_role() {
		return nom_role;
	}

	public void setNom_role(String nom_role) {
		this.nom_role = nom_role;
	}

	public String getIds_banned() {
		return ids_banned;
	}

	public void setIds_banned(String ids_banned) {
		this.ids_banned = ids_banned;
	}
	
	
	
}
