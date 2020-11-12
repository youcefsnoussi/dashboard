package com.commercial.entities.schema.dynamic_data;

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
import com.commercial.functions.get_time_date;

@Entity

@Table(name="track_operation_user" , schema = "dynamic_data")

public class track_operation_user implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String entity_operation;
	
	private String designation;
	
	private long id_operation;
	
	private String date;
	
	private String time;
	
	@ManyToOne
	@JoinColumn(name = "users")
	private users user;
	
	public track_operation_user() {
		// TODO Auto-generated constructor stub
		super();
	}

	public track_operation_user(String entity_operation, String designation, long id_operation, users user) {
		
		super();
		
		get_time_date gtd = new get_time_date();
		
		this.entity_operation = entity_operation;
		this.designation = designation;
		this.id_operation = id_operation;
		this.user = user;
		this.date = gtd.get_date();
		this.time = gtd.get_time();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEntity_operation() {
		return entity_operation;
	}

	public void setEntity_operation(String entity_operation) {
		this.entity_operation = entity_operation;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public long getId_operation() {
		return id_operation;
	}

	public void setId_operation(long id_operation) {
		this.id_operation = id_operation;
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
