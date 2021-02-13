package com.commercial.entities.schema.profoma_cmd_bl_fact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.user_menu.users;

@Entity

@Table(name="bon_transfert_interne" , schema = "proforma_cmd_bl_fact")

public class bon_transfert_interne  implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String numero;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users users;
	
	@Column(columnDefinition="boolean default false")
	private boolean cancel = false;
	
	private String date; 
	
	private String time; 
	
	private String destination;
	
	public bon_transfert_interne() {
		// TODO Auto-generated constructor stub
	}

	public bon_transfert_interne(String numero, com.commercial.entities.schema.user_menu.users users, boolean cancel,
			String date, String time, String destination) {
		super();
		this.numero = numero;
		this.users = users;
		this.cancel = cancel;
		this.date = date;
		this.time = time;
		this.destination = destination;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public users getUsers() {
		return users;
	}

	public void setUsers(users users) {
		this.users = users;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
