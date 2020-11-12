package com.commercial.entities.schema.static_data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity

@Table(name="wilaya" , schema = "static_data")

public class wilaya {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String designation;
	
	@ManyToOne
	@JoinColumn(name = "region")
	private region region;
	
	private String code;
	
	public wilaya() {
		// TODO Auto-generated constructor stub
	}

	public wilaya(String designation, com.commercial.entities.schema.static_data.region region, String code) {
		super();
		this.designation = designation;
		this.region = region;
		this.code = code;
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

	public region getRegion() {
		return region;
	}

	public void setRegion(region region) {
		this.region = region;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
