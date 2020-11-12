package com.commercial.entities.schema.user_menu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.commercial.entities.schema.static_data.unite;

@Entity

@Table(name="users" , schema = "user_menu")


public class users implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String password;
	private String nom;
	private String prenom;
	private String image = "resources/images/welcome/no_image.png";
	private boolean active = true;
	
	@ManyToOne
    @JoinColumn(name = "role")
	private roles role;

	@ManyToOne
	@JoinColumn(name = "unite")
	private unite unite;
	

	public users() {
		super();
		// TODO Auto-generated constructor stub
	}


	public users(String username, String password, String nom, String prenom, String image, boolean active, roles role,
			com.commercial.entities.schema.static_data.unite unite) {
		super();
		this.username = username;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.image = image;
		this.active = active;
		this.role = role;
		this.unite = unite;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public roles getRole() {
		return role;
	}


	public void setRole(roles role) {
		this.role = role;
	}


	public unite getUnite() {
		return unite;
	}


	public void setUnite(unite unite) {
		this.unite = unite;
	}

	
	
}
