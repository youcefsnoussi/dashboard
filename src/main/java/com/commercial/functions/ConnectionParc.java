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
public class ConnectionParc {

	public ConnectionParc() {
		
	}
	
public static Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("org.postgresql.Driver"); // oracle.jdbc.driver.OracleDriver
			
//			String url = "jdbc:postgresql://localhost:5443/Parc"; 
//			String url = "jdbc:postgresql://192.168.1.231:5432/Parc"; //AIN ROMANA
			
			String url = "jdbc:postgresql://10.15.15.151:5432/Parc"; //Ain Defla

			
			String username = "postgres";
			String password = "Admin125478";
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

public static List< Map<String,String> > get_vehicules(){
	
	List <Map<String,String>> list_vehicules = new ArrayList <Map<String,String>> ();
	
	//Connection_RH db = new Connection_RH();
	Connection con = getconnection();

		Statement state = null;
	try {
		state = con.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	String sql = "select matricule_n,type from sch.vehicule where type='1' or  type='2' or  type='6'" ; 
	
	try {
		
		ResultSet res = state.executeQuery(sql);
		
		while (res.next()) {
			
			Map<String,String> vhl = new HashMap<String, String> ();
			
			vhl.put("matricule", res.getString("matricule_n"));
			vhl.put("type", res.getString("type"));
			
			list_vehicules.add(vhl);
			
		}
		
		con.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return list_vehicules;
	
}

public Double getPriceByTypeAndWilaya(Integer type, String code,String comune) {
	
	Double price = 0.0;
	boolean withCommune = false;
	
	//Connection_RH db = new Connection_RH();
	Connection con = getconnection();

		Statement state = null;
	try {
		state = con.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
//	String sql1 = "select montant FROM sch.wilaya_transport "+
//			"WHERE id_wilaya='"+code+"' AND id_type='"+type+"' ";
	Integer codeInt = Integer.valueOf(code);
	
	String sql = null;//"select montant FROM sch.wilaya_transport "+"WHERE id_wilaya='"+code+"' AND id_type='"+type+"' ";
	
	if(comune==null || comune.equals("") || comune.equals(" ")) {
		 sql = "select montant FROM sch.wilaya_transport "+
				"WHERE id_wilaya='"+codeInt+"' AND id_type='"+type+"' AND comune IS NULL";
		
	}else {
		withCommune= true;
		 sql = "select montant FROM sch.wilaya_transport "+
				"WHERE id_wilaya='"+codeInt+"' AND id_type='"+type+"' AND comune='"+comune+"' "; 
	}
	
	
	
	
	try {
		
		ResultSet res = state.executeQuery(sql);
		while (res.next()) {
			System.out.println(" res");
			price =  res.getDouble("montant");
			System.out.println(" res.getDouble(\"montant\")>>>>>>>>>>>>>> " + res.getDouble("montant"));
		}
		
		con.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(price==0.0 && withCommune) {
		return getPriceByTypeAndWilaya(type, code, "");
	}
	return price;
	
}

//public double test() {
//	
//	double price = 0;
//	
//	Connection con = getconnection();
//
//		Statement state = null;
//	try {
//		state = con.createStatement();
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	
//	
//	try {
//		
//		
//		String sql = "select montant  FROM sch.wilaya_transport WHERE id_wilaya='1' AND id_type='2' AND comune='REGGANE'";
//		
//		ResultSet res = state.executeQuery(sql);
//		System.out.println("before "+sql);
//		
//		if(res.next()) {
//			System.out.println("res.getDouble(\"montant\") "+ res.getDouble("montant"));
//			price = res.getDouble("montant");
//			
//		}
//		
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	
//	try {
//		
//		con.close();
//		
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	return price;
//	
//}
	

}
