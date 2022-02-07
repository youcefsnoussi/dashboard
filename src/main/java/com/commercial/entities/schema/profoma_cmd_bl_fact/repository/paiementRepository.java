package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;
import com.commercial.entities.schema.static_data.banque;

public interface paiementRepository extends JpaRepository<paiement, Long> {
	
	public paiement  findFirst1ByOrderByIdDesc();
	
	//------------------------------------------------------------------
	
	@Query( " FROM paiement pay "
			
			+ " WHERE pay.cancel = 'false' ")
	
	public List<paiement> get_payments_no_cancled();
	
	//------------------------------------------------------------------
	
	@Query( " FROM paiement pay "
			
			+ " WHERE pay.cancel = 'false' "
			+ " AND CAST(pay.date_saisie AS date) BETWEEN (:start) AND (:end)")
	
	public List<paiement> get_payments_no_cancled_interval(@Param("start") Date date_d, @Param("end") Date date_f);
	
	//------------------------------------------------------------------
	
	@Query( " FROM paiement pay "
			
			+ " WHERE pay.cancel = 'false' "
			
			+ " AND pay.banque = :bank "
			
			+ " AND LOWER(pay.numero_piece) = LOWER(:num_piece) "
			
			+ " AND pay.date = :date "
			
			//+ " AND pay.montant = :montant " 
			
			+ " AND pay.registre_commerce = :rc ")  /* // , @Param("rc") registre_commerce rc "*/
	
	public List<paiement> if_payment_already_exist(@Param("bank") banque bank, @Param("num_piece") String num_piece, @Param("date") String date, 
			/*@Param("montant") double montant,*/ @Param("rc") registre_commerce rc);
	
	//------------------------------------------------------------------
	
	@Query(   " FROM paiement pay "
			
			+ " WHERE pay.cancel = 'false' "
			
			+ " AND date_saisie = :date AND registre_commerce = :rc "
			
			+ " ORDER BY id ASC")
	
	public List<paiement> get_payments_date_rc(@Param("date") String date, @Param("rc") registre_commerce rc); 
	
	//----------------------------------------------------------
	
	@Query( " FROM paiement pai "
			
				+ " WHERE pai.etat_sold = 'false' "
				
				+ " AND pai.registre_commerce = :rc "
				
				+ " ORDER BY pai.id")
		 
	public List<paiement> get_paiements_not_solde_by_rc(@Param("rc") registre_commerce rc);
	
	//----------------------------------------------------------
	
	@Query( " FROM paiement pai "
			
				+ " WHERE pai.cancel = 'false' "
				
				+ " AND pai.registre_commerce = :rc "
				
				+ " ORDER BY pai.id DESC")
		 
	public List<paiement> get_paiements_not_canceled_by_rc(@Param("rc") registre_commerce rc);
	
	//----------------------------------------------------------
	
	@Query( " FROM paiement pai "
			
				+ " WHERE pai.cancel = 'false' "
				
				+ " AND pai.verification = 'true' AND pai.validation = 'false' "
				
				+ " ORDER BY pai.id DESC")
		 
	public List<paiement> get_paiements_impaye();
	
	//----------------------------------------------------------
	
	@Query( 	"SELECT COUNT(*) "
			
				+ " FROM paiement pai "
			
				+ " WHERE pai.cancel = 'false' "
				
				+ " AND pai.verification = 'true' AND pai.validation = 'false' ")
		 
	public Integer get_count_paiements_impaye();
	
	//---------------------------------------------------------
	
	@Query( 	value=  "SELECT * FROM proforma_cmd_bl_fact.paiement pai "
			
				+ " WHERE pai.cancel = 'false' AND registre_commerce = :id_rc"
				
				+ " ORDER BY CAST(date_saisie AS date), id DESC LIMIT 1", nativeQuery=true)
		 
	public paiement get_last_payement_not_canceled_by_rc(@Param("id_rc") long id_rc);
	
	//---------------------------------------------------------
	
	@Query(   " SELECT MAX(CAST(date_saisie as date)) "
			
			+ " FROM paiement pai "
			
			+ " WHERE pai.registre_commerce = :rc AND pai.cancel = 'false' ")
	 
	public String GetLastDateFactByRc(@Param("rc") registre_commerce rc);
	
}
