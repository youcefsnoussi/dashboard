package com.commercial.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection_oracle {

	public DBConnection_oracle() {
		// TODO Auto-generated constructor stub
	
	}	
		
		public Connection getconnection(long unite) {
			
			Connection appcon = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver"); // 
					
				String url = (unite==1) ? "jdbc:oracle:thin:@192.168.0.201:1521:info" : "jdbc:oracle:thin:@10.15.15.151:1521:info";
				
				String username = "sdba";
				String password = "kv32md";
				DriverManager.setLoginTimeout(10);
				appcon = DriverManager.getConnection(url, username, password);
				System.out.println("DATABASE ORACLE CONNECT !! (y)");
				
				//appcon.close();
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Oracle Driver Class Not Found !!!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("No SQL Connection to Oracle DB");
			}
			finally {
				
				System.out.println("No Connection to Oracle DB !!!");
				
			}

			return appcon;
		
	}
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DBConnection_oracle connexion = new DBConnection_oracle();
		Connection con = connexion.getconnection();
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 	*/
}
