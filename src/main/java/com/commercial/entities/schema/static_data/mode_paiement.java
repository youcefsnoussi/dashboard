package com.commercial.entities.schema.static_data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="mode_paiement" , schema = "static_data")

public class mode_paiement  implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String designation;
	
	public mode_paiement() {
		// TODO Auto-generated constructor stub
	}
	
	public mode_paiement(String designation) {
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
