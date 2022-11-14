package com.commercial.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ConnectionAinDefla {

	public ConnectionAinDefla() {
		
	}
	
public static Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("org.postgresql.Driver"); 

			String url = "jdbc:postgresql://10.15.15.151:5432/commercial"; 
			
			String username = "postgres";
			String password = "***REMOVED***";
			appcon = DriverManager.getConnection(url, username, password);
			//System.out.println("DATABASE CONNECT !! (y)");
			
			//appcon.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("non");
			System.out.println(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("No Connection to Parc DB \nError Code : "+e.getErrorCode()+"\nMessage : "+e.getMessage());
			appcon = null;
		}
		
		return appcon;
	
	}

public static List< Map<String,Object> > getQuantiteSoldValFact(){
	
	List <Map<String,Object>> list = new ArrayList <Map<String,Object>> ();
	
	//Connection_RH db = new Connection_RH();
	Connection con = getconnection();

		Statement state = null;
	try {
		state = con.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
//	String sql1 = "select matricule_n,type from sch.vehicule where type='1' or  type='2' or  type='6'" ; 
	
	String sql = "SELECT art.code,categ.nom_category, scateg.nom_sous_category,art.libelle,  " + 
			"			 SUM(fct_d.quantite) quantite, prix_u_ht, SUM(fct_d.montant_ht) montant_ht, SUM(fct_d.montant_remise) montant_remise, " + 
			"            SUM (fct_d.montant_ht_net) montant_ht_net, SUM(fct_d.montant_tva) montant_tva, " + 
			"			 SUM(fct_d.montant_ttc) montant_ttc " + 
			"				FROM proforma_cmd_bl_fact.facture_detail fct_d JOIN article.article art ON fct_d.article=art.id " + 
			"                JOIN article.produit prod ON prod.id=art.produit " + 
			"                JOIN article.sous_category_produit scateg ON scateg.id=prod.sous_category_produit  " + 
			"                JOIN proforma_cmd_bl_fact.facture fact ON fact.id=fct_d.facture " + 
			"                JOIN article.category_produit categ ON categ.id=scateg.category_produit " + 
			"			 WHERE CAST(fact.date AS date) BETWEEN CAST('01/01/2022' AS date) AND CAST('01/11/2022' AS date) " + 
			"				 GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht" ; 
	
	try {
		
		ResultSet res = state.executeQuery(sql);
		
		while (res.next()) {
//			Object obj = new Object();
//			obj.
			
			Map<String,Object> factH = new HashMap<String, Object> ();
			
			factH.put("code", res.getString("code"));
			factH.put("nom_category", res.getString("nom_category"));
			
			factH.put("nom_sous_category", res.getString("nom_sous_category"));
			factH.put("libelle", res.getString("libelle"));
			factH.put("quantite", res.getDouble("quantite"));
			factH.put("montant_ht", res.getDouble("montant_ht"));
			factH.put("montant_remise", res.getDouble("montant_remise"));
			factH.put("montant_ht_net", res.getDouble("montant_ht_net"));
			factH.put("montant_tva", res.getDouble("montant_tva"));
			factH.put("montant_ttc", res.getDouble("montant_ttc"));
			
			list.add(factH);
			
		}
		
		con.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return list;
	
}


public static List< Map<String,Object> > getQuantiteSoldValFactAvoir(){
	
	List <Map<String,Object>> list = new ArrayList <Map<String,Object>> ();
	
	//Connection_RH db = new Connection_RH();
	Connection con = getconnection();

		Statement state = null;
	try {
		state = con.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
//	String sql1 = "select matricule_n,type from sch.vehicule where type='1' or  type='2' or  type='6'" ; 
	
	String sql = "SELECT art.code,categ.nom_category, scateg.nom_sous_category,art.libelle,  " + 
			"			 SUM(fct_d.quantite) quantite, prix_u_ht, SUM(fct_d.montant_ht) montant_ht, SUM(fct_d.montant_remise) montant_remise, " + 
			"            SUM (fct_d.montant_ht_net) montant_ht_net, SUM(fct_d.montant_tva) montant_tva, " + 
			"			 SUM(fct_d.montant_ttc) montant_ttc " + 
			"				FROM proforma_cmd_bl_fact.facture_avoir_detail fct_d JOIN article.article art ON fct_d.article=art.id " + 
			"                JOIN article.produit prod ON prod.id=art.produit " + 
			"                JOIN article.sous_category_produit scateg ON scateg.id=prod.sous_category_produit  " + 
			"                JOIN proforma_cmd_bl_fact.facture_avoir fact ON fact.id=fct_d.facture_avoir " + 
			"                JOIN article.category_produit categ ON categ.id=scateg.category_produit " + 
			"			 WHERE CAST(fact.date AS date) BETWEEN CAST('01/01/2022' AS date) AND CAST('01/11/2022' AS date) " + 
			"				 GROUP BY code, nom_category, nom_sous_category, libelle, prix_u_ht";
	
	try {
		
		ResultSet res = state.executeQuery(sql);
		
		while (res.next()) {
//			Object obj = new Object();
//			obj.
			
			Map<String,Object> factH = new HashMap<String, Object> ();
			
			factH.put("code", res.getString("code"));
			factH.put("nom_category", res.getString("nom_category"));
			
			factH.put("nom_sous_category", res.getString("nom_sous_category"));
			factH.put("libelle", res.getString("libelle"));
			factH.put("quantite", res.getDouble("quantite"));
			factH.put("montant_ht", res.getDouble("montant_ht"));
			factH.put("montant_remise", res.getDouble("montant_remise"));
			factH.put("montant_ht_net", res.getDouble("montant_ht_net"));
			factH.put("montant_tva", res.getDouble("montant_tva"));
			factH.put("montant_ttc", res.getDouble("montant_ttc"));
			
			list.add(factH);
			
		}
		
		con.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return list;
	
}



	

}
