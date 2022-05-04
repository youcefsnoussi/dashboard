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

import com.commercial.entities.schema.static_data.unite;

public class Connection_RH {

	public Connection_RH() {
		// TODO Auto-generated constructor stub
	}
	
	public static Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("org.postgresql.Driver"); // oracle.jdbc.driver.OracleDriver
			
			String url = "jdbc:postgresql://192.168.1.231:5432/grhDB";  //--> AIN ROMANA
			
			//String url = "jdbc:postgresql://192.168.1.231:5432/grhDB";
			
			String username = "postgres";
			String password = "***REMOVED***";
			appcon = DriverManager.getConnection(url, username, password);
			//System.out.println("DATABASE CONNECT !! (y)");
			
			//appcon.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Class de Connection Introuvable \nMessage : "+e.getMessage());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Connection to GRH DB Impossible \nMessage : "+e.getMessage());
			//System.out.println("nooooooooOoooooooon");
		}

		return appcon;
	
	}
	
	//-------------------------------------------------
	
	public static List< Map<String,String> > get_employee(unite unite){
		
		List <Map<String,String>> list_emp = new ArrayList <Map<String,String>> ();
		
		//Connection_RH db = new Connection_RH();
		Connection con = getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String filiale = "";
		
		if(unite.getId()==1) {
			
			filiale = "SIM MOUZAIA";
			
		}
		
		else {
			
			filiale = "SIM AINDEFLA";
			
		}
		
		String sql = " SELECT matricule, nom, prenom FROM employee.employee " + 
						"WHERE etat = 'true' AND apprenti IS NULL AND filiale = '"+filiale+"' AND vente = 'true'"; 
		
		try {
			
			ResultSet res = state.executeQuery(sql);
			
			while (res.next()) {
				
				Map<String,String> emp = new HashMap<String, String> ();
				
				emp.put("matricule", res.getString("matricule"));
				emp.put("nom", res.getString("nom"));
				emp.put("prenom", res.getString("prenom"));
				
				list_emp.add(emp);
				
			}
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list_emp;
		
	}
	
}
