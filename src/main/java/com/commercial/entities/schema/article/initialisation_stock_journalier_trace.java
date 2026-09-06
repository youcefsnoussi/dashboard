package com.commercial.entities.schema.article;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.commercial.entities.schema.user_menu.users;

@Entity
@Table(name = "initialisation_stock_journalier_trace", schema = "article")
public class initialisation_stock_journalier_trace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "initialisation_id")
    private initialisation_stock_journalier initialisation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private category_produit category;

    @Column(name = "quantite_stock", columnDefinition = "double precision default 0")
    private double quantiteStock;

    @Column(name = "pourcentage_vente", columnDefinition = "double precision default 0")
    private double pourcentageVente;

    private String date;

    private String time;

    @ManyToOne
    @JoinColumn(name = "users")
    private users user;

    public initialisation_stock_journalier_trace() {
        // default constructor
    }

    public initialisation_stock_journalier_trace(initialisation_stock_journalier initialisation) {
        this.initialisation = initialisation;
        this.category = initialisation.getCategory();
        this.quantiteStock = initialisation.getQuantiteStock();
        this.pourcentageVente = initialisation.getPourcentageVente();
        this.date = initialisation.getDate();
        this.time = initialisation.getTime();
        this.user = initialisation.getUser();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public initialisation_stock_journalier getInitialisation() {
        return initialisation;
    }

    public void setInitialisation(initialisation_stock_journalier initialisation) {
        this.initialisation = initialisation;
    }

    public category_produit getCategory() {
        return category;
    }

    public void setCategory(category_produit category) {
        this.category = category;
    }

    public double getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(double quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public double getPourcentageVente() {
        return pourcentageVente;
    }

    public void setPourcentageVente(double pourcentageVente) {
        this.pourcentageVente = pourcentageVente;
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

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }
}
