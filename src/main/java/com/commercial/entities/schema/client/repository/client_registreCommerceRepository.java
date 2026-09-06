package com.commercial.entities.schema.client.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.client;

public interface client_registreCommerceRepository extends JpaRepository<client_registreCommerce, Long> {
	
	@Query( " FROM client_registreCommerce crc "
			
			+ " WHERE crc.client = :clt "
			+ " AND crc.registre_commerce = :rc"
			+ " ORDER BY crc.id ASC")
	
	public List<client_registreCommerce>  if_relation_existe(@Param("clt") client clt, @Param("rc") registre_commerce rc);
	
	//---------------------------------------------
	
	/* Same rows as findAll(), but with both @ManyToOne sides fetched in ONE
	   query. Those relations default to EAGER, so a plain findAll() over ~930
	   rows fired ~1900 round trips to the remote DB and made the "Nouveau
	   Paiement" screen take seconds to open. Additive - findAll() is untouched. */
	@Query( " SELECT crc FROM client_registreCommerce crc "
			
			+ " JOIN FETCH crc.registre_commerce "
			+ " JOIN FETCH crc.client "
			+ " ORDER BY crc.id ASC ")
	
	public List<client_registreCommerce>  find_all_with_client_and_rc();
	
	//---------------------------------------------
	
	@Query( " SELECT DISTINCT registre_commerce "
			
		  +	" FROM client_registreCommerce crc "
			
		  + " WHERE crc.client = :clt ")
	
	public List<registre_commerce>  rc_by_client(@Param("clt") client clt);
	
	//----------------------------------------------
	
	@Query( " SELECT registre_commerce "
			
		  +	" FROM client_registreCommerce crc "
			
		  + " WHERE crc.client = :clt AND CAST(:today AS date) BETWEEN CAST(crc.date_fin AS date)-7 "
		  
		  + " AND CAST(crc.date_fin AS date)")
	
	public List<registre_commerce>  rc_by_client_intervall(@Param("clt") client clt, String today);
	
	//----------------------------------------------
	
	@Query( " SELECT client "
			
		  +	" FROM client_registreCommerce crc "
			
		  + " WHERE crc.registre_commerce = :rc ")
	
	public List<client>  client_by_rc(@Param("rc") registre_commerce rc);
	
	@Query( " FROM client_registreCommerce crc "
			
		  + " WHERE crc.client = :clt ")
	
	public List<client_registreCommerce>  rc_by_client_all(@Param("clt") client clt);
	
	@Query( " FROM client_registreCommerce crc "
			
		  + " WHERE crc.registre_commerce = :rc ")
	
	public List<client_registreCommerce>  client_by_rc_all(@Param("rc") registre_commerce rc);
	
	//-----------------------------------------------------
	
	@Query( " SELECT COUNT (*) "
			
			+" FROM client_registreCommerce crc "
			
		  	+ " WHERE CAST(:today AS date) BETWEEN CAST(date_fin AS date)-7 AND CAST(date_fin AS date)")
	
	public int  get_number_notification(String today);
	
	//-----------------------------------------------------
	
	@Query( " FROM client_registreCommerce crc "
			
		  	+ " WHERE CAST(:today AS date) BETWEEN CAST(date_fin AS date)-7 AND CAST(date_fin AS date)")
	
	public List<client_registreCommerce>  get_list_notification(String today);
	
	//-----------------------------------------------------
	
	@Query(  /* " SELECT registre_commerce "
			
			+*/ " FROM client_registreCommerce crc "
			
		  	//+ " WHERE crc.client = :clt AND CAST(:today AS date) >= CAST(crc.date_fin AS date)")
			
			+ " WHERE crc.client = :clt AND CAST(:today AS date) BETWEEN CAST(date_debut AS date) AND CAST(date_fin AS date)"
			
			+ " AND crc.registre_commerce.etat_blockage = 'active'")
	
	public List<client_registreCommerce>  get_list_rc_by_client_for_facture(@Param("clt") client clt, String today);
	
	//-----------------------------------------------------
	
	@Query( "FROM client_registreCommerce crc " + 
	
			"WHERE registre_commerce = :rc " + 
			
			"AND (  ( CAST(:date_d AS date) BETWEEN CAST(date_debut AS date) AND CAST(date_fin AS date) " + 
			
			"OR CAST(:date_f AS date) BETWEEN CAST(date_debut AS date) AND CAST(date_fin AS date) ) " + 
			
			"OR " + 
			
			"( CAST(date_debut AS date) BETWEEN CAST(:date_d AS date) AND CAST(:date_f AS date) " + 
			
			"AND CAST(date_fin AS date) BETWEEN CAST(:date_d AS date) AND CAST(:date_f AS date)  ) " + 
			
			")" )
	
	public List<client_registreCommerce>  if_rc_is_already_inRelation(@Param("rc") registre_commerce rc, @Param("date_d") String date_debut
			, @Param("date_f") String date_fin);
	
	//-----------------------------------------------------
	
	@Query( "FROM client_registreCommerce crc " + 
	
			"WHERE crc.registre_commerce.etat_blockage = 'active' " +
			
			"AND CAST(:today AS date) BETWEEN CAST(crc.registre_commerce.date_emission AS date) AND CAST(crc.registre_commerce.date_fin AS date) " +
			
			"AND CAST(:today AS date) BETWEEN CAST(crc.date_debut AS date) AND CAST(crc.date_fin AS date) " )
	
	public List<client_registreCommerce>  ListRCwithCLIENT_active(@Param("today") String today);
	
	//------------------------------------------------------
	
	@Query( "FROM client_registreCommerce crc " + 
			
			"WHERE registre_commerce = :rc  ")
	
	public List<client_registreCommerce>  if_rc_existe_in_relation(@Param("rc") registre_commerce rc);
	
	//-----------------------------------------------------
	
	@Query( "FROM client_registreCommerce crc " + 
	
			"WHERE crc.registre_commerce.category.nom_category = 'Personnel' ")
	
	public List<client_registreCommerce>  RC_employee();
	
	//-----------------------------------------------------
	
	@Query( "FROM client_registreCommerce crc " + 
	
			"WHERE crc.registre_commerce.consignation = 'TRUE' ")
	
	public List<client_registreCommerce>  RC_consignation();
	
	//-----------------------------------------------------
	
	@Query( "FROM client_registreCommerce crc " + 
	
			"WHERE crc.registre_commerce.multiple_bon_livraison = 'TRUE' ")
	
	public List<client_registreCommerce>  RC_bls_facture();
	
	//------------------------------------------------------
	/*
	@Query( "FROM client_registreCommerce crc " + 
			
			"WHERE crc.registre_commerce.etat_blockage = 'active' " +
			
			"AND CAST(:today AS date) BETWEEN CAST(crc.date_debut AS date) AND CAST(crc.date_fin AS date) " )
	
	public List<client_registreCommerce>  ListAllRCs(@Param("today") String today);
	*/
}
