package com.commercial.entities.schema.profoma_cmd_bl_fact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.profoma_cmd_bl_fact.BonCommande;

public interface BonCommandeRepository  extends JpaRepository<BonCommande, Long> {
	
	public BonCommande  findFirst1ByOrderByNumeroDesc();
	
	//------------------------------------------------------
	
	@Query("FROM BonCommande bc "+
		   "WHERE ( CAST(date_debut AS date) BETWEEN :start AND :end ) "+
		   "OR ( CAST(date_fin AS date) BETWEEN :start AND :end ) ")
	public List<BonCommande> GetBonCommandeBetweenDates(@Param("start") String start, @Param("end") String end);
	
}
