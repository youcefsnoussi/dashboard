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
import com.commercial.functions.get_time_date;

@Entity
@Table(name = "initialisation_stock_journalier", schema = "article")
public class initialisation_stock_journalier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private category_produit category;

    @Column(name = "quantite_stock", columnDefinition = "double precision default 0")
    private double quantiteStock = 0;

    @Column(name = "pourcentage_vente", columnDefinition = "double precision default 0")
    private double pourcentageVente = 0;

    private String date;

    private String time;

    @ManyToOne
    @JoinColumn(name = "users")
    private users user;

    public initialisation_stock_journalier() {
        // default constructor
    }

    public initialisation_stock_journalier(category_produit category, double quantiteStock, double pourcentageVente, users user) {
        this.category = category;
        this.quantiteStock = quantiteStock;
        this.pourcentageVente = pourcentageVente;
        this.user = user;

        get_time_date gtd = new get_time_date();
        this.date = gtd.get_date();
        this.time = gtd.get_time();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
