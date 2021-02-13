package com.commercial.entities.schema.dynamic_data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.client.registre_commerce;

@Entity

@Table(name="rc_bl_cumulative" , schema = "dynamic_data")

public class rc_bl_cumulative implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "registre_commerce")
	private registre_commerce registre_commerce;
	
	public rc_bl_cumulative() {
		// TODO Auto-generated constructor stub
	}

	public rc_bl_cumulative(com.commercial.entities.schema.client.registre_commerce registre_commerce) {
		super();
		this.registre_commerce = registre_commerce;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public registre_commerce getRegistre_commerce() {
		return registre_commerce;
	}

	public void setRegistre_commerce(registre_commerce registre_commerce) {
		this.registre_commerce = registre_commerce;
	}
	
}
