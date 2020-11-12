package com.commercial.entities.schema.user_menu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity

@Table(name="login_track" , schema = "user_menu")

public class login_track implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	private String ip_adresse;
	
	private String date_login;
	
	private String time_login;
	
	private String date_logout;
	
	private String time_logout;
	
	private String login_cookie;
	
	public login_track() {
		// TODO Auto-generated constructor stub
	}

	public login_track(com.commercial.entities.schema.user_menu.users users, String ip_adresse, String date_login,
			String time_login, String date_logout, String time_logout, String login_cookie) {
		super();
		this.users = users;
		this.ip_adresse = ip_adresse;
		this.date_login = date_login;
		this.time_login = time_login;
		this.date_logout = date_logout;
		this.time_logout = time_logout;
		this.login_cookie = login_cookie;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	public String getIp_adresse() {
		return ip_adresse;
	}

	public void setIp_adresse(String ip_adresse) {
		this.ip_adresse = ip_adresse;
	}

	public String getDate_login() {
		return date_login;
	}

	public void setDate_login(String date_login) {
		this.date_login = date_login;
	}

	public String getTime_login() {
		return time_login;
	}

	public void setTime_login(String time_login) {
		this.time_login = time_login;
	}

	public String getDate_logout() {
		return date_logout;
	}

	public void setDate_logout(String date_logout) {
		this.date_logout = date_logout;
	}

	public String getTime_logout() {
		return time_logout;
	}

	public void setTime_logout(String time_logout) {
		this.time_logout = time_logout;
	}

	public String getLogin_cookie() {
		return login_cookie;
	}

	public void setLogin_cookie(String login_cookie) {
		this.login_cookie = login_cookie;
	}

	
}
