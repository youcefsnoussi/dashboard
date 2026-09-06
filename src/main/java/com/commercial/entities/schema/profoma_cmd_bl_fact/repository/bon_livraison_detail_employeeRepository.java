package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;

public interface bon_livraison_detail_employeeRepository extends JpaRepository<bon_livraison_detail_employee, Long>{
	
	@Query( " FROM bon_livraison_detail_employee ble_det "+
			
			" WHERE ble_det.bon_livraison_employee = :ble")
		 
	public List<bon_livraison_detail_employee> get_ble_detail(@Param("ble") bon_livraison_employee ble);
	
	//---------------------------------------------------------------------
	
	@Query( " SELECT ble_det.prix_u_ht, ble_det.tva, ble_det.article.id, ble_det.magasin.id, ble_det.unite_mesure.id, " +
			
			" SUM(quantite), SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc) " +
			
			" FROM bon_livraison_detail_employee ble_det " + 
			
			" WHERE CAST( ble_det.bon_livraison_employee.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			
			" AND ble_det.bon_livraison_employee.cancel = 'false' AND ble_det.bon_livraison_employee.factured = 'false' " +
			" AND ble_det.bon_livraison_employee.type_rc = :type_rc " +
			
			" GROUP BY ble_det.article.id, ble_det.prix_u_ht, ble_det.tva, ble_det.magasin.id, ble_det.unite_mesure.id")
		 
	public List<Object []> get_cumule_facture_ble(@Param("start") String start, @Param("end") String end, @Param("type_rc") String type_rc);
	
	//---------------------------------------------------------------------
	
	@Query( " SELECT SUM(montant_ht) AS mnt_ht, SUM(montant_tva) AS mnt_tva, SUM(montant_ttc) AS mnt_ttc " +
			
			" FROM bon_livraison_detail_employee ble_det " + 
			
			" WHERE CAST( ble_det.bon_livraison_employee.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date) " +
			
			" AND ble_det.bon_livraison_employee.cancel = 'false' AND ble_det.bon_livraison_employee.factured = 'false' " +
			" AND ble_det.bon_livraison_employee.type_rc = :type_rc")
		 
	public List<Object []> get_total_facture_ble(@Param("start") String start, @Param("end") String end, @Param("type_rc") String type_rc);
	
	//----------------------------------------------------------------------
	
	@Query( " SELECT SUM(montant_ht), SUM(montant_tva), SUM(montant_ttc) " +
			
			" FROM bon_livraison_detail_employee ble_det " +
			
		    " WHERE ble_det.bon_livraison_employee = :ble")
	
	public List<Double[]> get_sum_for_ble(@Param("ble") bon_livraison_employee ble);
	
	//----------------------------------------------------------------------
	
		@Query( " FROM bon_livraison_detail_employee ble_det " +
				
			    " WHERE ble_det.bon_livraison_employee.cardNumber = :cardNumber " +
			    
				" AND ble_det.bon_livraison_employee.cancel = 'false' " +
			    
				" AND ble_det.validation = 'false' ")
		
		public List<bon_livraison_detail_employee> get_ble_details_by_card_number
					(@Param("cardNumber") String cardNumber);
		
		//----------------------------------------------------------------------
		
		@Query( " FROM bon_livraison_detail_employee ble_det " +
				
			    " WHERE ble_det.bon_livraison_employee = :ble " +
			    
				" AND ble_det.validation = 'true' ")
		
		public List<bon_livraison_detail_employee> check_ble_detail_validation
					(@Param("ble") bon_livraison_employee ble);
		
	}
