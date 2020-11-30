package com.commercial.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Connection_peseur {

	public Connection_peseur() {
		// TODO Auto-generated constructor stub
	}
	
	public Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // oracle.jdbc.driver.OracleDriver
			
						
			String url = "jdbc:mysql://192.168.1.231:3306/peseur";
			//String url = "jdbc:postgresql://192.168.1.231:5432/process";
			
			//System.out.println("URL ============>"+url);
			
			String username = "root";
			String password = "simcs";
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
			e.printStackTrace();
			System.out.println("nooooooooooooooooon");
		}

		return appcon;
	
	
	}
	
	public List<String> get_matricule_from_peseur(){
		
		List <String> lm = new ArrayList <String> ();
		
		Connection_peseur db = new Connection_peseur();
		Connection con = db.getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		get_time_date gtd = new get_time_date();
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		String today = conv.convertion_MyDate_to_InputDate(gtd.get_date());
		
		String sql = " SELECT p.camion_idcamion FROM peser p " + 
						"JOIN charger c ON p.idpeser=c.produitfini_idproduitfini "+
						"WHERE date_peser = '"+today+"' AND poid2 IS NULL ORDER BY p.camion_idcamion";
		
		try {
			
			ResultSet res = state.executeQuery(sql);
			
			while (res.next()) {
				
				lm.add(res.getString("Camion_idCamion"));
				
			}
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return lm;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Connection_peseur cc = new Connection_peseur();
		
		List <String> lm = cc.get_matricule_from_peseur();
		
		for(int i=0;i<lm.size();i++) {
			
			System.out.println("->"+lm.get(i));
			
		}
		*/
	}

}
