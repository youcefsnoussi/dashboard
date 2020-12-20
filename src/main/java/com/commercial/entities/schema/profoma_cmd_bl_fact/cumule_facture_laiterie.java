package com.commercial.entities.schema.profoma_cmd_bl_fact;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity

@Table(name="cumule_facture_laiterie" , schema = "proforma_cmd_bl_fact")

public class cumule_facture_laiterie implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "combo")
	prof_cmd_bl_fact_client_rc_avoir combo;
	
	@ManyToOne
	@JoinColumn(name = "facture")
	facture facture;
	
	public cumule_facture_laiterie() {
		// TODO Auto-generated constructor stub
	}

	public cumule_facture_laiterie(prof_cmd_bl_fact_client_rc_avoir combo,
			com.commercial.entities.schema.profoma_cmd_bl_fact.facture facture) {
		super();
		this.combo = combo;
		this.facture = facture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public prof_cmd_bl_fact_client_rc_avoir getCombo() {
		return combo;
	}

	public void setCombo(prof_cmd_bl_fact_client_rc_avoir combo) {
		this.combo = combo;
	}

	public facture getFacture() {
		return facture;
	}

	public void setFacture(facture facture) {
		this.facture = facture;
	}
	
	
	
}
