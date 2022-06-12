package com.commercial.functions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import import_client.DBConnection_oracle;
//import import_client.DBConnection_pg;

@Service

public class RecalculeSold {
	
	@Autowired
    DataSource localDataSource;
	
	public RecalculeSold() {
		// TODO Auto-generated constructor stub
	}
	
	public void recalcule_sold_rc_client(String codes, long unite) {
		
		DBConnection_oracle db_oracle = new DBConnection_oracle();
		Connection con_oracle = db_oracle.getconnection(unite);
		
		Statement state_oracle = null;
		
		try {
			
			state_oracle = (con_oracle != null) ? con_oracle.createStatement() : null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Oracle DB not connected (IN FUNCTION RecalculeSold) ");
		}
		finally {}
		
		//-------------------------------------------------
		
		
		//-------------------------------------------------
		
		try {
			
			//DBConnection_pg db_pg = new DBConnection_pg();
			Connection con_pg  = localDataSource.getConnection();
			
			Statement state_pg = con_pg.createStatement();
			
			Statement state_pg1 = con_pg.createStatement();
			
			Statement state_pg2 = con_pg.createStatement();
			
			Statement state_pg3 = con_pg.createStatement();
			
			//----------------------------- SELECT ALL RCs ---------------------------
			
			System.out.println("---->"+codes);
			
			String sql_pg = "SELECT * FROM client.registre_commerce WHERE code IN ("+codes+")";
			
			//System.out.println("->"+sql_pg);
			
			ResultSet res_pg = state_pg.executeQuery(sql_pg);
			
			String code_no_in_db_orcl = "";
			
			while(res_pg.next()) { //-----------------------------> BROWS RCs 1 by 1
				
				long id_rc = res_pg.getLong("id");
				
				String code = res_pg.getString("code");
				
				String nom_prenom = res_pg.getString("nom")+" "+res_pg.getString("prenom");
				
				//-----------------------------------> Getting SOLD From OLD DB with las date '31/01/2021'
				
				String sql_orcale = (unite==1) ? 
						"SELECT SOLDE FROM AN_SOLDE_EDIT_2021 WHERE C_CL = '"+code+"' AND ANNEE = '2021' " :  //AN_SOLDE_EDIT_2021 -> ain romana edit
						"SELECT SOLDE FROM AN_SOLDE WHERE C_CL = '"+code+"' AND ANNEE = '2021' "; // ----> Ain Defla
				
				ResultSet res_orcl = (state_oracle != null) ? state_oracle.executeQuery(sql_orcale) : null;
				
				double sold_orcl=0;
				
				if(res_orcl != null && res_orcl.next()) { //___________________________________________ IF EXIST IN DB ORACLE 
					
					sold_orcl = res_orcl.getDouble("SOLDE");
					
				}
				
				System.out.println(code+" / old = "+sold_orcl+" / new = "+res_pg.getString("sold_encours"));
				
				double old_sold_pg = res_pg.getDouble("sold_encours");
				
				//double sold_orcl = res_orcl.getDouble("SOLDE");
				
				System.out.println("____________________________");
				
				//-------------------------------------------> Gettings operations From table mouvement From Start date = '01/02/2021'
				
				String sql_pg1 = "SELECT * FROM dynamic_data.mouvement "
								+ "	WHERE registre_commerce = '"+id_rc+"' AND CAST(date AS date) >= '01/01/2021' "
								//+ "	WHERE registre_commerce = '"+id_rc+"' AND CAST(date AS date) >= '01/01/2021' AND CAST(date AS date) <= '31/01/2021' "
									+ " ORDER BY CAST(date AS date), id ASC";
				
				ResultSet res_pg1 = state_pg1.executeQuery(sql_pg1);
				
				System.out.println("===================> BEGIN VERIFICATION ALL TABLES WITH TABLE mouvement AND UPDATE IT <=======================");
				
				//-------------------------------------------- MAKCH mouvement like JANVIER 2021 ----------------------------------
				
				
				//-----------------------------------------------------------------------------------------------------------------
				
				while(res_pg1.next()) { //-----------------------------------------> correct vals of table movement if all other tables have been edited
					
					long id_mvm = res_pg1.getLong("id");
					
					long id_opration = res_pg1.getLong("id_operation");
					
					String type_operation = res_pg1.getString("type_operation");
					
					String date_op = res_pg1.getString("date");
					
					double montant_op = res_pg1.getDouble("montant_operation");
					
					//------------------------------> Correct mvm 1 by 1 depending on mvm type
					
					switch (type_operation) {
					
					case "Paiement":{
								
								String sql_pg2 = "SELECT * FROM proforma_cmd_bl_fact.paiement WHERE id = '"+id_opration+"' ";
								
								ResultSet res_pg2 = state_pg2.executeQuery(sql_pg2);
								
								if(res_pg2.next()) {
								
									double montant_p = res_pg2.getDouble("montant");
									
									String date_saisie = res_pg2.getString("date_saisie");
									
									if(montant_op != montant_p) { System.out.println("-->edit paiement montant");
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET montant_operation='"+montant_p+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
									}
									
									if(!date_saisie.equals(date_op)) { System.out.println("-->edit paiement date");
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET date='"+date_saisie+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
									}
								
								}
								
							}break;
					
					case "Remboursement":{ 
						
								String sql_pg2 = "SELECT * FROM proforma_cmd_bl_fact.remboursement WHERE id = '"+id_opration+"' ";
								
								ResultSet res_pg2 = state_pg2.executeQuery(sql_pg2);
								
								if(res_pg2.next()) {
								
									double montant_p = res_pg2.getDouble("montant");
									
									String date_saisie = res_pg2.getString("date_saisie");
									
									if(montant_op != montant_p) { System.out.println("-->edit remboursement montant");
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET montant_operation='"+montant_p+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
									}
									
									if(!date_saisie.equals(date_op)) { System.out.println("-->edit remboursement date");
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET date='"+date_saisie+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
									}
								
								}
								
							}break;
					
					case "Facture":{
						
								String sql_pg2 = "SELECT * FROM proforma_cmd_bl_fact.facture WHERE id = '"+id_opration+"' ";
								
								ResultSet res_pg2 = state_pg2.executeQuery(sql_pg2);
								
								if(res_pg2.next()) {
								
									double montant_p = res_pg2.getDouble("montant_ttc");
									
									String date_saisie = res_pg2.getString("date");
									
									if(montant_op != montant_p) { System.out.println("-->edit facture montant");
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET montant_operation='"+montant_p+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
									}
									
									if(!date_saisie.equals(date_op)) { System.out.println("-->edit facture date");
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET date='"+date_saisie+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
									}
								
								}
								
							}break;		
						
					case "Facture Avoire":{ 
						
								String sql_pg2 = "SELECT * FROM proforma_cmd_bl_fact.facture_avoir WHERE id = '"+id_opration+"' ";
								
								ResultSet res_pg2 = state_pg2.executeQuery(sql_pg2);
								
								if(res_pg2.next()) {
								
									double montant_p = res_pg2.getDouble("montant_ttc");
									
									String date_saisie = res_pg2.getString("date");
									
									if(montant_op != montant_p) { System.out.println("-->edit facture avoir montant");
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET montant_operation='"+montant_p+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
									}
									
									if(!date_saisie.equals(date_op)) { System.out.println("-->edit facture avoir date");
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET date='"+date_saisie+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
									}
								
								}
								
							}break;		
							
					default:
						break;
					}
					
				} //-----------------------------------------> ****** END ****** correct vals of table movement if all other tables have been edited
				
				System.out.println("===================> END VERIFICATION ALL TABLES WITH TABLE mouvement AND UPDATE IT <==========================");
				
				System.out.println("===================> BEGIN RECALCULE AND UPDATE TABLE mouvement <==========================");
				
				//-------------------------------------------> Gettings operations From table mouvement From Start date = '01/02/2021'
				
				sql_pg1 = "SELECT * FROM dynamic_data.mouvement "
								+ "	WHERE registre_commerce = '"+id_rc+"' AND CAST(date AS date) >= '01/01/2021' "
									+ " ORDER BY CAST(date AS date), id ASC";
				
				res_pg1 = state_pg1.executeQuery(sql_pg1);
				
				int wd=0;
				
				//System.out.println("===================> BEGIN VERIFICATION ALL TABLES WITH TABLE mouvement AND UPDATE IT<==========================");
				
				double old_sold_dyn = sold_orcl; //---------------------------------->>>>>>>> heda li ndiro f old sold mor kol operation li hoa new sold ta3 li klbelha
				
				double total_op_tzid_sold = 0, total_op_tnakass_sold = 0;
				
				while(res_pg1.next()) {
					
					long id_mvm = res_pg1.getLong("id");
					
					//long id_opration = res_pg1.getLong("id_operation");
					
					String type_operation = res_pg1.getString("type_operation");
					
					//String date_op = res_pg1.getString("date");
					
					double montant_op = res_pg1.getDouble("montant_operation");
					
					if(wd==0) {
						
						switch (type_operation) {
						
							case "Paiement":{
										
										double old_sold = old_sold_dyn;
										
										double new_sold = old_sold - montant_op;
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET new_sold_rc='"+new_sold+"', "
															+ "old_sold_rc='"+old_sold+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
										old_sold_dyn = new_sold;
										
										total_op_tnakass_sold += montant_op;
										
									}break;
							
							case "Remboursement":{
								
										double old_sold = old_sold_dyn;
										
										double new_sold = old_sold + montant_op;
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET new_sold_rc='"+new_sold+"', "
															+ "old_sold_rc='"+old_sold+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
										old_sold_dyn = new_sold;
										
										total_op_tzid_sold += montant_op;
										
									}break;
							
							case "Facture":{
								
										double old_sold = old_sold_dyn;
										
										double new_sold = old_sold + montant_op;
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET new_sold_rc='"+new_sold+"', "
															+ "old_sold_rc='"+old_sold+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
										old_sold_dyn = new_sold;
										
										total_op_tzid_sold += montant_op;
										
									}break;		
							
							case "Annulation Paiement":{
								
										double old_sold = old_sold_dyn;
										
										double new_sold = old_sold + montant_op;
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET new_sold_rc='"+new_sold+"', "
															+ "old_sold_rc='"+old_sold+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
										old_sold_dyn = new_sold;
										
										total_op_tzid_sold += montant_op;
										
									}break;			
									
							case "Facture Avoire":{
								
										double old_sold = old_sold_dyn;
										
										double new_sold = old_sold - montant_op;
										
										String sql_pg3 = "UPDATE dynamic_data.mouvement SET new_sold_rc='"+new_sold+"', "
															+ "old_sold_rc='"+old_sold+"' WHERE id = '"+id_mvm+"' ";
										
										state_pg3.executeUpdate(sql_pg3);
										
										old_sold_dyn = new_sold;
										
										total_op_tnakass_sold += montant_op;
										
									}break;	
									
							case "Facture Ristourne":{
								
								double old_sold = old_sold_dyn;
								
								double new_sold = old_sold - montant_op;
								
								String sql_pg3 = "UPDATE dynamic_data.mouvement SET new_sold_rc='"+new_sold+"', "
													+ "old_sold_rc='"+old_sold+"' WHERE id = '"+id_mvm+"' ";
								
								state_pg3.executeUpdate(sql_pg3);
								
								old_sold_dyn = new_sold;
								
								total_op_tnakass_sold += montant_op;
								
							}break;			
							default:
								break;
						}
						
					}
					
				}
				
				System.out.println("===================> END RECALCULE AND UPDATE TABLE mouvement <==========================");
				
				//------------------------------------------------------- UPDATE SOLD RC in table registre_commerce
				
				System.out.println("client = "+nom_prenom+" / "+code);
				
				System.out.println("old SOLD pg = "+Double.toString(old_sold_pg));
				
				System.out.println("BEGIN SOLD (orcl) = "+Double.toString(sold_orcl)+" <--------------------->");
				
				System.out.println("total moontant op tzid sold = "+Double.toString(total_op_tzid_sold));
				
				System.out.println("total moontant op tnakas sold = "+ Double.toString( (total_op_tnakass_sold*(-1))) );
				
				System.out.println("FINAL SOLD = "+Double.toString(old_sold_dyn)+" <--------------------->");
				
				System.out.println("<____________________________><_____________________________><_______________________>");
				
				//////------------------------ UPDATE SOLD ENCOURS table registre_commer ------------------/////
				
				String sql_pg3 = "UPDATE client.registre_commerce SET  sold_encours='"+old_sold_dyn+"' WHERE id = '"+id_rc+"' ";
				
				state_pg3.executeUpdate(sql_pg3);
				
				//----------------------------------------------------------------------- END -------------------------------
				
				/*} //___________________________________________ END IF EXIST IN DB ORACLE
				else { //___________________________________________ ELSE nzid code ta3o f madjmo3a
					
					code_no_in_db_orcl += " / "+code;
					
				}*/
				
				if(sold_orcl==0){
					
					System.out.println("_________________ CODE RC inexistant DANS ORACLE ___________________");
					System.out.println(code_no_in_db_orcl);
				}
				
			}
			
			con_pg.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
