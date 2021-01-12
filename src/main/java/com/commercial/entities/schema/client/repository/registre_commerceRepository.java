package com.commercial.entities.schema.client.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.registre_commerce;



public interface registre_commerceRepository extends JpaRepository<registre_commerce, Long> {
	
	public registre_commerce  findFirst1ByOrderByIdDesc();
	
	@Query( " FROM registre_commerce rc "
			
			+ " WHERE rc.numero_rc = :num_rc "
			
			+ " OR rc.numero_nif = :num_nif "
			
			+ " OR rc.numero_art = :num_art "
			
			+ " OR (nom = :nom AND prenom = :prenom)")
	
	public registre_commerce  if_rc_exist_db(@Param("num_rc") String num_rc, @Param("num_nif") String num_nif, @Param("num_art") String num_art, 
												@Param("nom") String nom, @Param("prenom") String prenom);
	
	@Query( " FROM registre_commerce rc "
			
			+ " WHERE rc.etat_blockage = 'active' ")
	
	public List<registre_commerce>  rc_active_only();
	
	//----------------------
	
	@Query( " FROM registre_commerce rc "
			
			+ " WHERE rc.category = :cat ")
	
	public List<registre_commerce>  last_rc_by_category(@Param("cat") category_client category);
	
}
