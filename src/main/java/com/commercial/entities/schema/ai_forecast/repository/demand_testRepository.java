package com.commercial.entities.schema.ai_forecast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.commercial.entities.schema.ai_forecast.demand_test;

public interface demand_testRepository extends JpaRepository<demand_test, Long> {

	@Query("FROM demand_test dt ORDER BY dt.category ASC, dt.date ASC")
	public List<demand_test> get_all_ordered();

}
