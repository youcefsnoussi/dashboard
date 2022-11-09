package com.commercial.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;

@Service
public class ConnectionSimAgro {

	public ConnectionSimAgro() {
		
	}
	
public static Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("org.postgresql.Driver"); // oracle.jdbc.driver.OracleDriver
			
//			String url = "jdbc:postgresql://localhost:5443/Agro_Sim"; 
			String url = "jdbc:postgresql://192.168.1.231:5432/Agro_Sim"; 
			
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
			System.out.println("No Connection to SimAgro DB \nError Code : "+e.getErrorCode()+"\nMessage : "+e.getMessage());
			appcon = null;
		}
		
		return appcon;
	
	}
	
public static void editPayementToSimAgro(paiement payment){
	
	Connection con = getconnection();


		Statement state = null;
	try {
		state = con.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	try {
		System.out.println("n_doc : "+ payment.getNumero_piece());
		System.out.println("banque : "+ payment.getBanque().getNom_banque());
		System.out.println("c_reg : "+ payment.getMode_paiement().getId());
		System.out.println("c_reg  designation : "+payment.getMode_paiement().getDesignation());
		System.out.println("n_reg : "+ payment.getId());
		
		String sql="update simagro.commerciale set n_doc='"+payment.getNumero_piece()+"',banque='"+
	payment.getBanque().getNom_banque()+"',"+
				"c_reg='"+
	payment.getMode_paiement().getId()+"',"+"designation='"+payment.getMode_paiement().getDesignation()+"' where n_reg='"+
				payment.getId()+"' ";
		
		state.execute(sql);
		
		con.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

	

}
