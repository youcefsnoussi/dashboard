package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;

public interface bon_livraison_employeeRepository extends JpaRepository<bon_livraison_employee, Long>{
	
	public bon_livraison_employee findFirst1ByOrderByNumeroDesc();
	
	//---------------------------------------------------------------
	
	@Query( " FROM bon_livraison_employee ble " +
			
  			" WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
  			
			" AND cancel = 'false'  " ) /*OR (factured = 'false' AND cancel = 'false')*/

	public List<bon_livraison_employee> date_between_ble(@Param("start") String start, @Param("end") String end);
	
	//---------------------------------------------------------------
	
	@Query( " FROM bon_livraison_employee ble " +
			
  			" WHERE CAST(date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" +
  			
			" AND cancel = 'false' AND factured = 'false' " )

	public List<bon_livraison_employee> get_ble_factured(@Param("start") String start, @Param("end") String end);	
	
	//-----------------------------------------------------------------------------------
	
	@Query( " FROM bon_livraison_employee ble " +
			
  			" WHERE matricule_employee = :code_emp AND cancel = 'false' AND factured = 'false' " )

	public List<bon_livraison_employee> test_if_it_got_bl(@Param("code_emp") String matricule_employee);	
	
}
