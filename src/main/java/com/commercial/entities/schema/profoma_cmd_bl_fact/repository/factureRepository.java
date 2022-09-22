package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.user_menu.users;

public interface factureRepository extends JpaRepository<facture, Long> {
	
	public facture  findFirst1ByOrderByNumeroDesc();
	
	//----------------------------------------------------------
	
	@Query( " SELECT id, numero FROM facture fact "
			
			+ " WHERE numero like %:num_fact ")
	 
	public List<Object[]> get_facts_by_num(@Param("num_fact") String num_fact);
	
	//----------------------------------------------------------
	
	@Query( " FROM facture fact "
			
			+ " WHERE fact.etat_sold = 'false' "
			+ " AND fact.client = :clt")
	 
	public List<facture> client__active_only(@Param("clt") client clt);
	
	//----------------------------------------------------------
	
	@Query( " FROM facture fact "
			
			+ " WHERE fact.etat_sold = 'false' "
			+ " AND fact.registre_commerce = :rc "
			+ " ORDER BY fact.id")
	 
	public List<facture> get_factures_not_solde_by_rc(@Param("rc") registre_commerce rc);
	
	//----------------------------------------------------------
		
	@Query( " FROM facture fact "
			
			+ " WHERE fact.date = :today ")
	 
	public List<facture> today_facture(@Param("today") String today);
		
	//----------------------------------------------------------
	
	@Query( " FROM facture fact "
			
		  + " WHERE CAST(fact.date AS date) BETWEEN (:start) AND (:end)" )
	 
	public List<facture> date_between_facture(@Param("start") Date start, @Param("end") Date end);
		
		
	//----------------------------------------------------------
		
	@Query( " FROM facture fact "
			
		  + " WHERE CAST(fact.date AS date) BETWEEN (:start) AND (:end)"
		  
		  + " AND registre_commerce = :rc " )
	 
	public List<facture> date_between_facture_rc(@Param("start") Date start, @Param("end") Date end, 
			@Param("rc") registre_commerce rc);
		
	//----------------------------------------------------------
		
	@Query( " FROM facture fact "
			
		  + " WHERE fact.notification = 'FALSE' "
		  
		  + " AND fact.users = :user" )
	 
	public List<facture> get_notification_by_user(users user);
		
	//----------------------------------------------------------
	
	@Query( " FROM facture fact "
			
		  + " WHERE fact.notification = 'FALSE' " )
	 
	public List<facture> get_notifications_admin();
		
	//----------------------------------------------------------

	@Query( " SELECT DISTINCT SUBSTR(date, 7, 9) AS dates FROM facture "
			
			+ " WHERE date LIKE '__/__/_%_%_%_%' "
			
			+ " ORDER BY dates ASC " )
	 
	public List<String> get_years_db();	
	
	//----------------------------------------------------------
	
	@Query( " SELECT fct.registre_commerce.code, fct.registre_commerce.numero_rc, fct.registre_commerce.nom, "
			
			+ "fct.registre_commerce.prenom,"
			
			+ " fct.registre_commerce.adresse, fct.registre_commerce.category.nom_category"
			
			+ " FROM facture fct"
			
			+ " WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"
			
			+ " GROUP BY fct.registre_commerce.code, fct.registre_commerce.numero_rc, fct.registre_commerce.nom, "
			
			+ "fct.registre_commerce.prenom," 
			  
			+ " fct.registre_commerce.adresse, fct.registre_commerce.category.nom_category" )
	 
	public List<Object[]> get_rc_buy(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------
	
	@Query( /*" SELECT fct.registre_commerce.code, CONCAT(fct.registre_commerce.nom, ' ', fct.registre_commerce.prenom),"
			
			+ " fct.numero "
			
			+*/ " FROM facture fct"
			
			+ " WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)"
			
			/*+ " ORDER BY registre_commerce.code"*/ )
	 
	public List<facture> get_code_rc_num_date(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------
	
	@Query(   " FROM facture fct"
			
			+ " WHERE id IN (:id_facts) AND facture_avoir IS NULL"
			
			+ " ORDER BY registre_commerce.code" )
	 
	public List<facture> get_facts_by_ids(@Param("id_facts") List<Long> facts);
	
	//----------------------------------------------------------
	
	@Query(   " FROM facture fct"
			
			+ " WHERE facture_avoir = :fact_av"
			
			+ " ORDER BY registre_commerce.code" )
	 
	public List<facture> get_facts_by_fact_avoir(@Param("fact_av") facture_avoir fact_av);
	
	//----------------------------------------------------------
	
	@Query(   " SELECT DISTINCT registre_commerce "
			
			+ " FROM facture fct "
			
			+ " WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)" 
			
			+ " AND fct.registre_commerce.category = :cat_c ")
	 
	public List<registre_commerce> GetClientsByCategory(@Param("start") String start, @Param("end") String end, 
															@Param("cat_c") category_client cat_c);
	
	//----------------------------------------------------------
	
	@Query(   " SELECT DISTINCT registre_commerce "
			
			+ " FROM facture fct "
			
			+ " WHERE CAST(fct.date AS date) BETWEEN CAST(:start AS date) AND CAST(:end AS date)")
	 
	public List<registre_commerce> GetClients(@Param("start") String start, @Param("end") String end);
	
	//----------------------------------------------------------
	
	@Query(   " SELECT MAX(CAST(date as date)) "
			
			+ " FROM facture fct "
			
			+ " WHERE fct.registre_commerce = :rc ")
	 
	public String GetLastDateFactByRc(@Param("rc") registre_commerce rc);
	
	//----------------------------------------------------------
	@Query( " FROM facture fact "
			
			  + " WHERE CAST(fact.date AS date) BETWEEN (:start) AND (:end)"
			  
			  + " AND client = :client " )
		 
		public List<facture> date_between_facture_client(@Param("start") Date start, @Param("end") Date end, 
				@Param("client") client client);
	
	//----------------------------------------------------------
		
	   
	   public List<facture> findByClientIdOrderByDateDesc(Long client, Sort sort);
			
}
