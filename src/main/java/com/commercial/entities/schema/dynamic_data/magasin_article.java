package com.commercial.entities.schema.dynamic_data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.article.article;;

@Entity

@Table(name="magasin_article" , schema = "dynamic_data")

public class magasin_article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "article")
	private article article;
	
	@ManyToOne
	@JoinColumn(name = "magasin")
	private Magasin magasin;

	public magasin_article(com.commercial.entities.schema.article.article article, Magasin magasin) {
		super();
		this.article = article;
		this.magasin = magasin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public article getArticle() {
		return article;
	}

	public void setArticle(article article) {
		this.article = article;
	}

	public Magasin getMagasin() {
		return magasin;
	}

	public void setMagasin(Magasin magasin) {
		this.magasin = magasin;
	}
	
	
	
}
