package com.commercial.entities.schema.ai_forecast;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* One row per category: held-out accuracy (MAE / MAPE) of the forecasting model,
   over the same test window as demand_test. Lets the UI show "how much to trust
   this" next to the chart instead of a naked prediction. */
@Entity

@Table(name = "demand_metrics", schema = "ai_forecast")

public class demand_metrics implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String category;

	private double mae;

	private Double mape;

	private int n_days;

	private double avg_actual;

	private LocalDateTime trained_at;

	public demand_metrics() {
	}

	public Long getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public double getMae() {
		return mae;
	}

	public Double getMape() {
		return mape;
	}

	public int getN_days() {
		return n_days;
	}

	public double getAvg_actual() {
		return avg_actual;
	}

	public LocalDateTime getTrained_at() {
		return trained_at;
	}

}
