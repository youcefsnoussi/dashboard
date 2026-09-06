package com.commercial.webController.statistic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.sous_category_produit;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.article.repository.sous_category_produitRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;

@Controller
@SessionAttributes("user")

public class dashboardController {

	@Autowired
	factureRepository factRepo;

	@Autowired
	facture_detailRepository fact_dRepo;

	@Autowired
	facture_avoirRepository fact_avRepo;

	@Autowired
	facture_avoir_detailRepository fact_av_dRepo;

	@Autowired
	registre_commerceRepository rcRepo;

	@Autowired
	articleRepository artRepo;

	@Autowired
	category_produitRepository cat_pRepo;

	@Autowired
	sous_category_produitRepository scat_pRepo;

	public dashboardController() {
	}

	/*
	 * Fast dashboard: instead of 2 SQL queries per category per period (which meant
	 * hundreds of scans of facture_detail), the whole range is fetched in ONE
	 * aggregate query per table (facture_detail + facture_avoir_detail), grouped by
	 * category / sous-category / day, and bucketed into periods here in Java.
	 * Same URL, same request parameters, same model attributes as before.
	 */
	@RequestMapping(value="/dashboard")
	public String dashboard(HttpServletRequest request,
						 @RequestParam("nbr_days") String intervalle,
						 @RequestParam(name = "type_val", defaultValue = "1") int type_val,
						 @RequestParam(name = "list_category", defaultValue = "") long[] list_category,
						 @RequestParam(name = "test_category", defaultValue = "") String test_category,
						 @RequestParam("start") String start,
						 @RequestParam("end") String end,
						 @SessionAttribute("user") users user,
						 Model model){

		List<category_produit> all_categories = cat_pRepo.findAll();

		if(list_category.length==0) {

			long[] tmp = new long [all_categories.size()];

			for(int i=0; i< all_categories.size();i++) {

				tmp[i] = all_categories.get(i).getId();

			}

			list_category = tmp;

		}

		Map<Long, category_produit> cat_by_id = new HashMap<Long, category_produit>();

		for(category_produit cp : all_categories) {

			cat_by_id.put(cp.getId(), cp);

		}

		convert_string_to_date_util conv = new convert_string_to_date_util();

		LocalDate s, e;

		if(start.equals("0")) {

			s = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

			e = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

			// the dashboard is always month-by-month
			intervalle = "m";

		}
		else {

			s = LocalDate.parse(start);

			e = LocalDate.parse(end);

		}

		// A single month cannot draw a trend line - there is only one point. In
		// that one case fall back to weekly buckets so the curve still has a
		// shape. Any wider range stays monthly.
		if(intervalle.equals("m") && s.getYear() == e.getYear() && s.getMonth() == e.getMonth()) {

			intervalle = "6";

		}

		// ---- build the periods (same boundaries as the old implementation) ----

		List<LocalDate> period_start = new ArrayList<LocalDate>();

		List<LocalDate> period_end = new ArrayList<LocalDate>();

		List<String> weeks = new ArrayList<String>();

		LocalDate tmp = s;

		int k = 1;

		while(tmp.isBefore(e)){

			LocalDate firstDayOfMonth = tmp;

			LocalDate lastDayOfMonth;

			if(intervalle.equals("m")) {

				lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

			}
			else {

				lastDayOfMonth = firstDayOfMonth.plusDays(Long.parseLong(intervalle)).isBefore(
						firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth()).minusDays(3))
						? firstDayOfMonth.plusDays(Long.parseLong(intervalle))
						: firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

			}

			period_start.add(firstDayOfMonth);

			period_end.add(lastDayOfMonth);

			weeks.add("Période "+k+" = "+conv.convertion_InputDate_to_MyDate(firstDayOfMonth.toString())+" "+
							conv.convertion_InputDate_to_MyDate(lastDayOfMonth.toString()));

			tmp = lastDayOfMonth.plusDays(1);

			k++;

		}

		// ---- series definitions, in selection order ----
		// one series per category; "Pates" split by sous-category

		List<String> series_names = new ArrayList<String>();

		Map<String, Integer> series_index = new HashMap<String, Integer>();

		for(long id : list_category) {

			category_produit cat_p = cat_by_id.get(id);

			if(cat_p == null) {

				continue;

			}

			if(cat_p.getNom_category().equals("Pates")) {

				List<sous_category_produit> l_scat = scat_pRepo.get_sousCat_by_cat(cat_p);

				for(sous_category_produit scat : l_scat) {

					series_index.put(id + "|" + scat.getNom_sous_category(), series_names.size());

					series_names.add(cat_p.getNom_category() + " " + scat.getNom_sous_category());

				}

			}
			else {

				series_index.put(id + "|", series_names.size());

				series_names.add(cat_p.getNom_category());

			}

		}

		// ---- fetch everything in 2 queries and bucket into periods ----

		double[][] sums = new double[period_start.size()][series_names.size()];

		if(!period_start.isEmpty() && !series_names.isEmpty()) {

			String range_start = period_start.get(0).toString();

			String range_end = period_end.get(period_end.size()-1).toString();

			List<Object[]> rows = new ArrayList<Object[]>();

			rows.addAll(fact_dRepo.get_dashboard_sums_by_day(range_start, range_end));

			rows.addAll(fact_av_dRepo.get_dashboard_sums_by_day(range_start, range_end));

			DateTimeFormatter day_fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			for(Object[] row : rows) {

				long cat_id = ((Number) row[0]).longValue();

				String sous_name = (String) row[2];

				Integer idx = series_index.get(cat_id + "|" + sous_name);

				if(idx == null) {

					idx = series_index.get(cat_id + "|");

				}

				if(idx == null) {

					continue; // category not selected

				}

				LocalDate day;

				try {

					day = LocalDate.parse((String) row[3], day_fmt);

				} catch (Exception ex) {

					continue; // unparseable date value, ignore the row

				}

				double value = (type_val == 2) ? ((Number) row[5]).doubleValue()
											   : ((Number) row[4]).doubleValue();

				for(int p = 0; p < period_start.size(); p++) {

					if(!day.isBefore(period_start.get(p)) && !day.isAfter(period_end.get(p))) {

						sums[p][idx] += value;

						break;

					}

				}

			}

		}

		// ---- model, same attribute names/shapes as before ----

		List<List<Object[]>> list = new ArrayList<List<Object[]>>();

		for(int p = 0; p < period_start.size(); p++) {

			List<Object[]> list_temp = new ArrayList<Object[]>();

			for(int i = 0; i < series_names.size(); i++) {

				Object[] obj = new Object[2];

				obj[0] = weeks.get(p);

				obj[1] = sums[p][i];

				list_temp.add(obj);

			}

			list.add(list_temp);

		}

		model.addAttribute("start", s.toString());

		model.addAttribute("end", e.toString());

		model.addAttribute("nbr_days", intervalle);

		model.addAttribute("type_val", type_val);

		model.addAttribute("list", list);

		model.addAttribute("weeks", weeks);

		model.addAttribute("cats", series_names);

		model.addAttribute("cat_selected", list_category);

		model.addAttribute("cat_prod", all_categories.stream()
				.filter(cat -> !cat.getNom_category().equals("Ristourne"))
				.collect(Collectors.toList()) );

		// ---- breakdown by sous-category and wilaya (flow + hierarchy charts) ----

		List<Object[]> breakdown = new ArrayList<Object[]>();

		if(!period_start.isEmpty() && !series_names.isEmpty()) {

			List<Object[]> rows_b = fact_dRepo.get_dashboard_breakdown(period_start.get(0).toString(),
										period_end.get(period_end.size()-1).toString());

			for(Object[] r : rows_b) {

				long cat_id = ((Number) r[0]).longValue();

				String sous_name = (String) r[2];

				// keep only what the current selection shows, using the same series mapping
				if(series_index.get(cat_id + "|" + sous_name) == null
						&& series_index.get(cat_id + "|") == null) {

					continue;

				}

				Object[] o = new Object[4];

				o[0] = r[1];		// category
				o[1] = sous_name;	// sous-category
				o[2] = r[3];		// wilaya
				o[3] = (type_val == 2) ? ((Number) r[5]).doubleValue() : ((Number) r[4]).doubleValue();

				breakdown.add(o);

			}

		}

		model.addAttribute("breakdown", breakdown);

		// ---- per-article (libellé) sales, for the product detail drawer ----

		List<Object[]> articles = new ArrayList<Object[]>();

		if(!period_start.isEmpty() && !series_names.isEmpty()) {

			List<Object[]> rows_a = fact_dRepo.get_dashboard_articles(period_start.get(0).toString(),
										period_end.get(period_end.size()-1).toString());

			for(Object[] r : rows_a) {

				String cat_name  = (String) r[0];
				String sous_name = (String) r[1];

				Object[] o = new Object[5];

				o[0] = cat_name;	// category
				o[1] = sous_name;	// sous-category
				o[2] = r[2];		// libellé
				o[3] = r[3];		// code
				o[4] = (type_val == 2) ? ((Number) r[5]).doubleValue() : ((Number) r[4]).doubleValue();

				articles.add(o);

			}

		}

		model.addAttribute("articles", articles);

		return "statistic/dashboard_new";
	}

}
