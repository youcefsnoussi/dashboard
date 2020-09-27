package com.commercial.entities.schema.static_data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="type_reglement" , schema = "static_data")

public class type_reglement implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String designation;
	
	public type_reglement() {
		// TODO Auto-generated constructor stub
	}

	public type_reglement(String designation) {
		super();
		this.designation = designation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
	
}
