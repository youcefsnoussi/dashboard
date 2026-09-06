package com.commercial.entities.schema.ai_forecast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.entities.schema.ai_forecast.demand_metrics;

public interface demand_metricsRepository extends JpaRepository<demand_metrics, Long> {

	@Query("FROM demand_metrics dm ORDER BY dm.mape ASC NULLS LAST")
	public List<demand_metrics> get_all_ordered();

}
