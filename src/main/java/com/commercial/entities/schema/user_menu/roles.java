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

public class roles  implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom_role;
	
	@Column(length=5000)
	private String ids_banned;
	
	@Column(columnDefinition = "boolean default false")
	private boolean one_interface = false;
	
	private String page_to_display;
	
	public roles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public roles(String nom_role, String ids_banned, boolean one_interface, String page_to_display) {
		super();
		this.nom_role = nom_role;
		this.ids_banned = ids_banned;
		this.one_interface = one_interface;
		this.page_to_display = page_to_display;
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

	public boolean isOne_interface() {
		return one_interface;
	}

	public void setOne_interface(boolean one_interface) {
		this.one_interface = one_interface;
	}

	public String getPage_to_display() {
		return page_to_display;
	}

	public void setPage_to_display(String page_to_display) {
		this.page_to_display = page_to_display;
	}

}
