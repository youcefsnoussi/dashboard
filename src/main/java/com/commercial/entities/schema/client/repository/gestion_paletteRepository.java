package com.commercial.entities.schema.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.client.gestion_palette;

public interface gestion_paletteRepository extends JpaRepository<gestion_palette, Long> {

	@Query(value = "SELECT COALESCE(SUM(gp.quantity), 0) FROM client.gestion_palette gp WHERE gp.client_rc = :clientRc", nativeQuery = true)
	public double getTotalReturnedByRc(@Param("clientRc") String clientRc);

	@Query(value = "SELECT * FROM client.gestion_palette gp WHERE gp.client_rc = :clientRc ORDER BY gp.created_at DESC", nativeQuery = true)
	public List<gestion_palette> getHistoryByRc(@Param("clientRc") String clientRc);

}
