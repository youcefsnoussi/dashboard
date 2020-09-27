package com.commercial.entities.schema.dynamic_data;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.commercial.entities.schema.article.article;

@Entity

@Table(name="magasin" , schema = "dynamic_data")

public class Magasin implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	String name;

	@OneToMany(mappedBy="magasin")
    private Set<article> articles;
    
    public Magasin() {
		// TODO Auto-generated constructor stub
	}
    
	public Magasin(String name, Set<article> articles) {
		super();
		this.name = name;
		this.articles = articles;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<article> getArticles() {
		return articles;
	}

	public void setArticles(Set<article> articles) {
		this.articles = articles;
	}

}
