package com.commercial.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.functions.get_time_date;

@Service
public class ConnectionGRHLogistique {

	@Autowired
	articleRepository artRepo;

	@Autowired
	unite_mesureRepository umRepo;

	public ConnectionGRHLogistique() {
		
	}
	
public static Connection getconnection() {
		
		Connection appcon = null;
		try {
			Class.forName("org.postgresql.Driver"); // oracle.jdbc.driver.OracleDriver
			
		String url = "jdbc:postgresql://192.168.100.84:5432/grhDB"; 
		//	String url = "jdbc:postgresql://192.168.1.231:5432/Agro_Sim"; 
			
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

// -----------------------------------------------------------------

	public List<bon_livraison_employee> get_ble_logistique(String start, String end){

		List<bon_livraison_employee> list_ble_emp = new ArrayList<bon_livraison_employee>();

		Connection con = getconnection();

		if(con != null) {

			try (Statement state = con.createStatement()) {
				String sql = " SELECT * FROM commercial.bon_livraison_employee ble "
						+ "WHERE traiter = 'false' AND annul = 'false' "
						+ "AND date_saisie BETWEEN CAST('"+start+"' AS date) AND CAST('"+end+"' AS date) ";

				String type_rc = "Logistique";
				ResultSet res = state.executeQuery(sql);

				while (res.next()) {
					bon_livraison_employee ble = new bon_livraison_employee(
						res.getString("matricule_employee"),
						res.getString("nom_employee"),
						res.getString("prenom_employee"),
						res.getString("date_saisie"),
						res.getString("heure_saisie"),
						"",
						"",
						null,
						null,
						0,
						res.getDouble("montant_ht"),
						res.getDouble("montant_tva"),
						res.getDouble("montant_ttc"),
						false,
						res.getString("card_number"),
						type_rc);

					ble.setId(res.getLong("id"));
					//ble.setNumero(res.getString("numero"));
					list_ble_emp.add(ble);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}

		return list_ble_emp;
	}

	// -----------------------------------------------------------------

	public bon_livraison_employee get_ble_logistique_id(long id_ble){

		bon_livraison_employee ble_emp = new bon_livraison_employee();

		Connection con = getconnection();

		if(con != null) {
			try (Statement state = con.createStatement()) {
				String sql = " SELECT * FROM commercial.bon_livraison_employee ble "
						+ "WHERE id='"+id_ble+"' AND traiter = 'false' ";

				ResultSet res = state.executeQuery(sql);
				String type_rc = "Logistique";

				while (res.next()) {
					ble_emp = new bon_livraison_employee(
						res.getString("matricule_employee"),
						res.getString("nom_employee"),
						res.getString("prenom_employee"),
						res.getString("date_saisie"),
						res.getString("heure_saisie"),
						"",
						"",
						null,
						null,
						0,
						res.getDouble("montant_ht"),
						res.getDouble("montant_tva"),
						res.getDouble("montant_ttc"),
						false,
						res.getString("card_number"),
						type_rc
						);

					ble_emp.setId(res.getLong("id"));
					//ble_emp.setNumero(res.getString("numero"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}

		return ble_emp;
	}

	// -----------------------------------------------------------------

	public List<bon_livraison_detail_employee> get_detail_ble_logistique(long id_ble){

		List <bon_livraison_detail_employee> list_ble_detail_emp = new ArrayList<bon_livraison_detail_employee>();

		Connection con = getconnection();

		if(con != null) {
			try (Statement state = con.createStatement()) {
				String sql = " SELECT * FROM commercial.bon_livraison_detail_employee ble_d "
						+ "JOIN commercial.bon_livraison_employee ble ON ble.id = bon_livraison_employee "
						+ "WHERE bon_livraison_employee = '"+id_ble+"' AND traiter = 'false' AND annul = 'false' ";

				ResultSet res = state.executeQuery(sql);

				while (res.next()) {
					bon_livraison_detail_employee ble_d = new bon_livraison_detail_employee(
						null,
						artRepo.getOne(res.getLong("article")),
						res.getDouble("quantite"),
						res.getDouble("prix_u_ht"),
						res.getDouble("tva"),
						null,
						umRepo.getOne(res.getLong("unite_mesure")),
						null);

					list_ble_detail_emp.add(ble_d);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}

		return list_ble_detail_emp;
	}

	//-------------------------------------------------

	public void update_ble_logistique(bon_livraison_employee ble, long id_ble_logistique){

		Connection con = getconnection();

		get_time_date gtd = new get_time_date();

		if(con != null) {
			try (Statement state = con.createStatement()) {
				String sql = " UPDATE commercial.bon_livraison_employee "
        + " SET  traiter='true', "
        + " user_traiter='" + ble.getUsers().getUsername() + "', "
        + " date_traiter='" + gtd.get_date() + "', "
        + " heure_traiter='" + gtd.get_time() + "', "
        + " id_bon_commercial='" + ble.getId() + "', "
        + " type_rc='Logistique' "
        + " WHERE id = '" + id_ble_logistique + "' ";


				state.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
		}

	}

	

}
