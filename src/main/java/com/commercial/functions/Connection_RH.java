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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;
import com.commercial.entities.schema.static_data.unite;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;

@Service

public class Connection_RH {
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	unite_mesureRepository umRepo;
	
	public Connection_RH() {
		// TODO Auto-generated constructor stub
	}
	
	public static Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("org.postgresql.Driver"); // oracle.jdbc.driver.OracleDriver
			
			 String url = "jdbc:postgresql://192.168.1.231:5432/grhDB";  //--> AIN ROMANA
			// String url = "jdbc:postgresql://192.168.51.15:5433/grhDB"; 
	
		  //  String url = "jdbc:postgresql://localhost:5443/grhDB2";
			
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
	
	//-------------------------------------------------
	
	public  List< bon_livraison_employee > get_ble_grh(String start, String end){
		
		List <bon_livraison_employee> list_ble_emp = new ArrayList <bon_livraison_employee> ();
		
		Connection con = getconnection();

		if(con != null) {
		
	  		Statement state = null;
			try {
				state = con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			String sql = " SELECT * FROM commercial.bon_livraison_employee ble " + 
							"WHERE traiter = 'false' AND annul = 'false' " +
							"AND date_saisie BETWEEN CAST('"+start+"' AS date) AND CAST('"+end+"' AS date) "; 
			
			try {
				String type_rc = "Agro";
				ResultSet res = state.executeQuery(sql);
				
				while (res.next()) {
					
					bon_livraison_employee ble = new bon_livraison_employee(res.getString("matricule_employee"), 
							res.getString("nom_employee"), res.getString("prenom_employee"), res.getString("date_saisie"), 
							res.getString("heure_saisie"), "", "", null, null, 0, res.getDouble("montant_ht"), 
							res.getDouble("montant_tva"), res.getDouble("montant_ttc"), false, 
							res.getString("card_number"), type_rc);
					
					ble.setId(res.getLong("id"));
					
					list_ble_emp.add(ble);
					
				}
				
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return list_ble_emp;
		
	}
	
	//---------------------------------------------------------------------
	
	public  bon_livraison_employee get_ble_grh_id(long id_ble){
		
		bon_livraison_employee ble_emp = new bon_livraison_employee();
		
		Connection con = getconnection();

  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = " SELECT * FROM commercial.bon_livraison_employee ble " + 
						"WHERE id='"+id_ble+"' AND traiter = 'false' "; 
		
		try {
			String type_rc = "Agro";

			ResultSet res = state.executeQuery(sql);
			
			while (res.next()) {
				
				ble_emp = new bon_livraison_employee(res.getString("matricule_employee"), 
						res.getString("nom_employee"), res.getString("prenom_employee"), res.getString("date_saisie"), 
						res.getString("heure_saisie"), "", "", null, null, 0, res.getDouble("montant_ht"), 
						res.getDouble("montant_tva"), res.getDouble("montant_ttc"), false, 
						res.getString("card_number"), type_rc
					);
				
				ble_emp.setId(res.getLong("id"));
				
			}
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ble_emp;
		
	}
	
	//-------------------------------------------------
	
	public  List<bon_livraison_detail_employee> get_detail_ble_grh(long id_ble){
		
		List <bon_livraison_detail_employee> list_ble_detail_emp = new ArrayList <bon_livraison_detail_employee> ();
		
		Connection con = getconnection();
		
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = " SELECT * FROM commercial.bon_livraison_detail_employee ble_d " +
						"JOIN commercial.bon_livraison_employee ble ON ble.id = bon_livraison_employee " + 
						"WHERE bon_livraison_employee = '"+id_ble+"' AND traiter = 'false' AND annul = 'false' "; 
		
		try {
			
			ResultSet res = state.executeQuery(sql);
			
			while (res.next()) {
				
				bon_livraison_detail_employee ble_d = new bon_livraison_detail_employee(null, 
						artRepo.getOne(res.getLong("article")), res.getDouble("quantite"), res.getDouble("prix_u_ht"),
						res.getDouble("tva"), null, umRepo.getOne(res.getLong("unite_mesure")), null);
				
				list_ble_detail_emp.add(ble_d);
				
			}
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list_ble_detail_emp;
		
	}
	
	//-------------------------------------------------
	
	public void update_ble_grh(bon_livraison_employee ble, long id_ble_grh){
		
		Connection con = getconnection();
		
		get_time_date gtd = new get_time_date();
		
  		Statement state = null;
		try {
			state = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = " UPDATE commercial.bon_livraison_employee " + 
             " SET  traiter='true', "
           + " user_traiter='" + ble.getUsers().getUsername() + "', "
           + " date_traiter='" + gtd.get_date() + "', "
           + " heure_traiter='" + gtd.get_time() + "', "
           + " id_bon_commercial='" + ble.getId() + "' "
           + " WHERE id = '" + id_ble_grh + "' ";

		
		
		try {
			
			state.executeUpdate(sql);
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
