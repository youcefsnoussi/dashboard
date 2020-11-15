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
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity

@Table(name="roles_menu" , schema = "user_menu")


public class roles_menu  implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "role")
	private roles role;
	
	@ManyToOne
    @JoinColumn(name = "submenu")
	private sub_menu submenu;
	
	public roles_menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public roles_menu(roles role, sub_menu submenu) {
		super();
		this.role = role;
		this.submenu = submenu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public roles getRole() {
		return role;
	}

	public void setRole(roles role) {
		this.role = role;
	}

	public sub_menu getSubmenu() {
		return submenu;
	}

	public void setSubmenu(sub_menu submenu) {
		this.submenu = submenu;
	}
	
	

}
