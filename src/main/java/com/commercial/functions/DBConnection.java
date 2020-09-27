package com.commercial.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

	public DBConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("org.postgresql.Driver"); // oracle.jdbc.driver.OracleDriver
			
						
			String url = "jdbc:postgresql://localhost:5432/GestionBle";
			//String url = "jdbc:postgresql://192.168.1.231:5432/Parc";
		
			String username = "postgres";
			String password = "Admin125478";
			appcon = DriverManager.getConnection(url, username, password);
			System.out.println("DATABASE CONNECT !! (y)");
			
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		DBConnection db = new DBConnection();
		Connection con = db.getconnection();

	}
	
}
