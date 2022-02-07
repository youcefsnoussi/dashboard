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
			
						
			String url = "jdbc:mysql://localhost:3306/peseur";
			//String url = "jdbc:postgresql://192.168.1.231:5432/process";
			
			//System.out.println("URL ============>"+url);
			
			String username = "root";
			String password = "simcs";
			appcon = DriverManager.getConnection(url, username, password);
			//System.out.println("DATABASE CONNECT !! (y)");
			
			//appcon.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("===> Class JDBC MYSQL Not Found !!!");
			System.out.println(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("No Connection Serveur MYSQL PESEUR");
		}

		return appcon;
	
	
	}
	
	//----------------------------------------------------------------
	
	public List<String> get_matricule_from_peseur(long unite){
		
		List <String> lm = new ArrayList <String> ();
		
		if(unite==1) {
		
			Connection_peseur db = new Connection_peseur();
			Connection con = db.getconnection();
	
		
	  		Statement state = null;
			try {
				state = con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("===> NO Connection to Server MYSQL !!!");
			}
			
			get_time_date gtd = new get_time_date();
			convert_string_to_date_util conv = new convert_string_to_date_util();
			
			String today = conv.convertion_MyDate_to_InputDate(gtd.get_date());
			
			String sql = " SELECT p.camion_idcamion FROM peser p " + 
							//"JOIN charger c ON p.idpeser=c.produitfini_idproduitfini "+
							"WHERE date_peser = '"+today+"'  ORDER BY p.camion_idcamion"; //AND poid2 IS NULL
			
			try {
				
				ResultSet res = state.executeQuery(sql);
				
				while (res.next()) {
					
					lm.add(res.getString("Camion_idCamion"));
					
				}
				
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("===> NO matricules CAUSE No Connection to MYSQL DB SERVER !!!");
			}
		
		}
		
		return lm;
		
	}
	
	//---------------------------------------------------------------------------------------
	
	public void insert_fct_to_peseur(String numero, String date) {
		
		
		Connection_peseur db = new Connection_peseur();
		Connection con = db.getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("===> NO Connection to Server MYSQL !!!");
		}
		
		
		
		try {
			
			String [] d = date.split("/");
			
			String datee = d[2]+"/"+d[1]+"/"+d[0];
			
			String sql = "INSERT INTO factures (idfacture, date_facture) VALUES ('"+numero+"', '"+datee+"')";
			
			state.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("===> Can't insert TO DB DUE to NO Connection to Server MYSQL !!!");
		}
		
		
		try {
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("===> No Close for NO Connection to Server MYSQL !!!");
		}
		
	}
	
	//---------------------------------------------------------------------------------------
	
	public void function_son_bl(String numero_bl, String date) {
		
		
		Connection_peseur db = new Connection_peseur();
		Connection con = db.getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			
			String [] d = date.split("/");
			
			String datee = d[2]+"/"+d[1]+"/"+d[0];
			
			String sql = "INSERT INTO chargrments (idchargrments, date_chargement) VALUES ('"+numero_bl+"', '"+datee+"')";
			
			state.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//---------------------------------------------------------------------------------------
	
	public double function_get_quant_son_bl(String numero_bl) {
		
		double quant_ret = 0;
		
		Connection_peseur db = new Connection_peseur();
		Connection con = db.getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			
			
			String sql = "SELECT * FROM charger WHERE num_fact = '"+numero_bl+"' ";
			
			ResultSet res = state.executeQuery(sql);
			
			if(res.next()) {
				
				quant_ret = res.getDouble("poidnetproduitfini");
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quant_ret;
		
	}
	
	//---------------------------------------------------------------------------------------
	
	public void update_bl_fact_son(String numero_bl, String numero_fact) {
		
		
		Connection_peseur db = new Connection_peseur();
		Connection con = db.getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			
			
			String sql = "UPDATE charger SET num_fact = '"+numero_fact+"' WHERE num_fact = '"+numero_bl+"' ";
			
			state.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//---------------------------------------------------------------------------------------
	
	public void update_bl_when_edit(String numero_bl, String date) {
		
		
		Connection_peseur db = new Connection_peseur();
		Connection con = db.getconnection();

	
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			
			String [] d = date.split("/");
			
			String datee = d[2]+"/"+d[1]+"/"+d[0];
			
			String sql = "UPDATE factures SET date_facture = '"+datee+"' WHERE idfacture = '"+numero_bl+"' ";
			
			state.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//---------------------------------------------------------------------------------------
	public boolean IfConxToDB() throws SQLException {
		
		
		Connection_peseur db = new Connection_peseur();
		Connection con = db.getconnection();

		return con.isValid(0);
  		
		
	}
	
}
