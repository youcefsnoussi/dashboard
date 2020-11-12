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

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;

@Entity

@Table(name="mouvement" , schema = "dynamic_data")

public class mouvement implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client")
	private client client;
	
	@ManyToOne
	@JoinColumn(name = "registre_commerce")
	private registre_commerce registre_commerce;
	
	private double montant_operation;
	
	private String type_operation;
	
	private long id_operation;
	
	private String date;
	
	private String time;
	
	private String observation;
	
	private double old_sold_client;
	
	private double old_sold_rc;
	
	private double new_sold_client;
	
	private double new_sold_rc;
	
	public mouvement() {
		// TODO Auto-generated constructor stub
	}

	public mouvement(com.commercial.entities.schema.client.client client,
			com.commercial.entities.schema.client.registre_commerce registre_commerce, double montant_operation,
			String type_operation, long id_operation, String date, String time, String observation,
			double old_sold_client, double old_sold_rc, double new_sold_client, double new_sold_rc) {
		super();
		this.client = client;
		this.registre_commerce = registre_commerce;
		this.montant_operation = montant_operation;
		this.type_operation = type_operation;
		this.id_operation = id_operation;
		this.date = date;
		this.time = time;
		this.observation = observation;
		this.old_sold_client = old_sold_client;
		this.old_sold_rc = old_sold_rc;
		this.new_sold_client = new_sold_client;
		this.new_sold_rc = new_sold_rc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public client getClient() {
		return client;
	}

	public void setClient(client client) {
		this.client = client;
	}

	public registre_commerce getRegistre_commerce() {
		return registre_commerce;
	}

	public void setRegistre_commerce(registre_commerce registre_commerce) {
		this.registre_commerce = registre_commerce;
	}

	public double getMontant_operation() {
		return montant_operation;
	}

	public void setMontant_operation(double montant_operation) {
		this.montant_operation = montant_operation;
	}

	public String getType_operation() {
		return type_operation;
	}

	public void setType_operation(String type_operation) {
		this.type_operation = type_operation;
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

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public double getOld_sold_client() {
		return old_sold_client;
	}

	public void setOld_sold_client(double old_sold_client) {
		this.old_sold_client = old_sold_client;
	}

	public double getOld_sold_rc() {
		return old_sold_rc;
	}

	public void setOld_sold_rc(double old_sold_rc) {
		this.old_sold_rc = old_sold_rc;
	}

	public double getNew_sold_client() {
		return new_sold_client;
	}

	public void setNew_sold_client(double new_sold_client) {
		this.new_sold_client = new_sold_client;
	}

	public double getNew_sold_rc() {
		return new_sold_rc;
	}

	public void setNew_sold_rc(double new_sold_rc) {
		this.new_sold_rc = new_sold_rc;
	}

}
