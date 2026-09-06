package com.commercial.webController.statistic;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.ai_forecast.demand_metrics;
import com.commercial.entities.schema.ai_forecast.demand_test;
import com.commercial.entities.schema.ai_forecast.repository.demand_metricsRepository;
import com.commercial.entities.schema.ai_forecast.repository.demand_testRepository;
import com.commercial.entities.schema.user_menu.users;

/* "Prevision IA" -- demand forecast vs. real held-out sales, per product category.
   The data itself is precomputed (scripts/train_forecast.py: same lag/rolling +
   time-based-holdout Random Forest technique as the Kaggle demand-forecasting
   prototype, trained on this app's own facture_detail history) and just read here --
   this page has no write path and never touches the operational tables. */
@Controller
@SessionAttributes("user")

public class previsionIaController {

	@Autowired
	demand_testRepository testRepo;

	@Autowired
	demand_metricsRepository metricsRepo;

	public previsionIaController() {
	}

	@RequestMapping(value = "/prevision_ia")
	public String prevision_ia(@SessionAttribute("user") users user, Model model) {

		List<demand_metrics> metrics = metricsRepo.get_all_ordered();

		Map<String, Map<String, Object>> chart_data = new LinkedHashMap<String, Map<String, Object>>();

		DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE;

		for (demand_metrics m : metrics) {

			List<demand_test> rows = testRepo.get_all_ordered().stream()
					.filter(r -> r.getCategory().equals(m.getCategory()))
					.collect(java.util.stream.Collectors.toList());

			List<String> dates = new ArrayList<String>();
			List<Double> actual = new ArrayList<Double>();
			List<Double> predicted = new ArrayList<Double>();

			for (demand_test r : rows) {
				dates.add(r.getDate().format(iso));
				actual.add(r.getActual());
				predicted.add(r.getPredicted());
			}

			Map<String, Object> series = new LinkedHashMap<String, Object>();
			series.put("dates", dates);
			series.put("actual", actual);
			series.put("predicted", predicted);
			chart_data.put(m.getCategory(), series);

		}

		model.addAttribute("metrics", metrics);
		model.addAttribute("chart_data", chart_data);

		return "statistic/prevision_ia";

	}

}
