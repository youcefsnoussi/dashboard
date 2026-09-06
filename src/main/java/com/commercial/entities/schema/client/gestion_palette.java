package com.commercial.entities.schema.client;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gestion_palette" , schema = "client")
public class gestion_palette implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String client_rc;

	private String client_name;

	@Column(columnDefinition = "bigint default 0")
	private Long article_id;

	@Column(columnDefinition = "double precision default 0")
	private double quantity;

	private Long user_session_id;

	private String created_at;

	public gestion_palette() {
	}

	public gestion_palette(String client_rc, String client_name, Long article_id, double quantity, Long user_session_id, String created_at) {
		this.client_rc = client_rc;
		this.client_name = client_name;
		this.article_id = article_id;
		this.quantity = quantity;
		this.user_session_id = user_session_id;
		this.created_at = created_at;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClient_rc() {
		return client_rc;
	}

	public void setClient_rc(String client_rc) {
		this.client_rc = client_rc;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public Long getArticle_id() {
		return article_id;
	}

	public void setArticle_id(Long article_id) {
		this.article_id = article_id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Long getUser_session_id() {
		return user_session_id;
	}

	public void setUser_session_id(Long user_session_id) {
		this.user_session_id = user_session_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
