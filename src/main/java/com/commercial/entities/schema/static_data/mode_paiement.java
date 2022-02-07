package com.commercial.entities.schema.static_data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name="mode_paiement" , schema = "static_data")

public class mode_paiement  implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String designation;
	
	@Column(columnDefinition="boolean default true")
	private boolean display ;
	
	public mode_paiement() {
		// TODO Auto-generated constructor stub
	}
	
	public mode_paiement(String designation) {
		super();
		this.designation = designation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}
	
}
