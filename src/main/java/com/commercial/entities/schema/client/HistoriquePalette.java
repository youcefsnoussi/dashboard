package com.commercial.entities.schema.client;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;

@Entity

@Table(name="HistoriquePalette" , schema = "client")

public class HistoriquePalette implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String date;
	
	@ManyToOne
	@JoinColumn(name = "client")
	private client client;
	
	private String typeOperation;
	
	private long oldSold;
	
	private long newSold;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	private facture facture;
	
	public HistoriquePalette() {
		// TODO Auto-generated constructor stub
	}

	public HistoriquePalette(String date, client client, String typeOperation, long oldSold, long newSold, facture facture) {
		super();
		this.date = date;
		this.client = client;
		this.typeOperation = typeOperation;
		this.oldSold = oldSold;
		this.newSold = newSold;
		this.facture = facture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public client getClient() {
		return client;
	}

	public void setClient(client client) {
		this.client = client;
	}

	public String getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}

	public long getOldSold() {
		return oldSold;
	}

	public void setOldSold(long oldSold) {
		this.oldSold = oldSold;
	}

	public long getNewSold() {
		return newSold;
	}

	public void setNewSold(long newSold) {
		this.newSold = newSold;
	}

	public facture getFacture() {
		return facture;
	}

	public void setFacture(facture facture) {
		this.facture = facture;
	}
	
}
