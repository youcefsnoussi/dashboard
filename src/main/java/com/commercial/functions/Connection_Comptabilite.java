package com.commercial.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.commercial.entities.schema.client.registre_commerce;

public class Connection_Comptabilite {

	public Connection_Comptabilite() {
		// TODO Auto-generated constructor stub
	}
	
	public Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("org.postgresql.Driver"); // oracle.jdbc.driver.OracleDriver
			
			String url = "jdbc:postgresql://localhost:5432/comptabilite"; //  Ain Romana
			//String url = "jdbc:postgresql://192.168.1.231:5432/comptabilite"; //  Ain Defla
			
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
			System.out.println("No Connection to Comptability DB \nError Code : "+e.getErrorCode()+"\nMessage : "+e.getMessage());
			appcon = null;
		}
		
		return appcon;
	
	}
		
	public void InsertClientToComptaDB(registre_commerce rc){
		
		Connection_Comptabilite db = new Connection_Comptabilite();
		Connection con = db.getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String adresse = rc.getAdresse().replaceAll("'", "''");
		
		try {
			
			String sql = "INSERT INTO comptable.client_compta( code, lib, adr_cl, c_wil, rc, nif, ai, id_com) " + 
							" VALUES ( '"+rc.getCode()+"', '"+rc.getNom()+" "+rc.getPrenom()+"', '"+adresse+"', " +
							" '"+rc.getWilaya().getDesignation()+"', '"+rc.getNumero_rc()+"', '"+rc.getNumero_nif()+"', " +
							" '"+rc.getNumero_art()+"', '"+rc.getId()+"')";
			
			state.execute(sql);
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void EditClientToComptaDB(registre_commerce rc){
		
		Connection_Comptabilite db = new Connection_Comptabilite();
		Connection con = db.getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String adresse = rc.getAdresse().replaceAll("'", "''");
		
		try {
			
			String sql = "UPDATE comptable.client_compta SET lib='"+rc.getNom()+" "+rc.getPrenom()+"', adr_cl='"+adresse+"', " +
							" c_wil='"+rc.getWilaya().getDesignation()+"', rc='"+rc.getNumero_rc()+"', nif='"+rc.getNumero_nif()+"', " +
							" ai='"+rc.getNumero_art()+"' WHERE id = '"+rc.getId()+"' ";
			
			state.execute(sql);
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
