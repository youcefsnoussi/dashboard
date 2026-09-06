package com.commercial.entities.schema.ai_forecast;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* Held-out actual vs. predicted daily quantity per category, produced by the
   Random Forest forecasting model (same technique as the Kaggle demand-forecasting
   prototype) trained on this database's own facture_detail history.
   Written by scripts/train_forecast.py, read-only from the app's side. */
@Entity

@Table(name = "demand_test", schema = "ai_forecast")

public class demand_test implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String category;

	private LocalDate date;

	private double actual;

	private double predicted;

	public demand_test() {
	}

	public Long getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getActual() {
		return actual;
	}

	public double getPredicted() {
		return predicted;
	}

}
