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
@Table(name = "edit_article_tracability", schema = "article")
public class edit_article_tracability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private article article;

    @Column(name = "old_vente_base_stock")
    private boolean oldVenteBaseStock;

    @Column(name = "new_vente_base_stock")
    private boolean newVenteBaseStock;

    @Column(name = "old_pourcentage_vente", columnDefinition = "double precision")
    private double oldPourcentageVente;

    @Column(name = "new_pourcentage_vente", columnDefinition = "double precision")
    private double newPourcentageVente;

    private String date;

    private String time;

    @ManyToOne
    @JoinColumn(name = "users")
    private users user;

    @Column(name = "username_snapshot")
    private String usernameSnapshot;

    public edit_article_tracability() {
        // default constructor
    }

    public edit_article_tracability(article article, users user, boolean oldVenteBaseStock, boolean newVenteBaseStock,
            double oldPourcentageVente, double newPourcentageVente) {
        this.article = article;
        this.user = user;
        this.oldVenteBaseStock = oldVenteBaseStock;
        this.newVenteBaseStock = newVenteBaseStock;
        this.oldPourcentageVente = oldPourcentageVente;
        this.newPourcentageVente = newPourcentageVente;
        this.usernameSnapshot = (user != null) ? user.getUsername() : "";

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

    public article getArticle() {
        return article;
    }

    public void setArticle(article article) {
        this.article = article;
    }

    public boolean isOldVenteBaseStock() {
        return oldVenteBaseStock;
    }

    public void setOldVenteBaseStock(boolean oldVenteBaseStock) {
        this.oldVenteBaseStock = oldVenteBaseStock;
    }

    public boolean isNewVenteBaseStock() {
        return newVenteBaseStock;
    }

    public void setNewVenteBaseStock(boolean newVenteBaseStock) {
        this.newVenteBaseStock = newVenteBaseStock;
    }

    public double getOldPourcentageVente() {
        return oldPourcentageVente;
    }

    public void setOldPourcentageVente(double oldPourcentageVente) {
        this.oldPourcentageVente = oldPourcentageVente;
    }

    public double getNewPourcentageVente() {
        return newPourcentageVente;
    }

    public void setNewPourcentageVente(double newPourcentageVente) {
        this.newPourcentageVente = newPourcentageVente;
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

    public String getUsernameSnapshot() {
        return usernameSnapshot;
    }

    public void setUsernameSnapshot(String usernameSnapshot) {
        this.usernameSnapshot = usernameSnapshot;
    }
}
