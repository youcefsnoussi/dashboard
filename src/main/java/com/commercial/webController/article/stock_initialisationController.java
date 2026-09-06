package com.commercial.webController.article;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.initialisation_stock_journalier;
import com.commercial.entities.schema.article.initialisation_stock_journalier_trace;
import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.repository.initialisation_stock_journalierRepository;
import com.commercial.entities.schema.article.repository.initialisation_stock_journalier_traceRepository;
import com.commercial.entities.schema.article.repository.category_produitRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.get_time_date;

@Controller
@SessionAttributes("user")
public class stock_initialisationController {

    @Autowired
    private category_produitRepository categoryRepo;

    @Autowired
    private initialisation_stock_journalierRepository stockInitRepo;

    @Autowired
    private initialisation_stock_journalier_traceRepository stockTraceRepo;

    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    public String stockInterface(HttpServletRequest request,
                                 @SessionAttribute("user") users user,
                                 Model model) {

        List<category_produit> categories = categoryRepo.findVenteBaseStockOrderByNomCategory();
        get_time_date gtd = new get_time_date();
        String today = gtd.get_date();

        Map<Long, initialisation_stock_journalier> todayEntries = new HashMap<Long, initialisation_stock_journalier>();
        List<initialisation_stock_journalier> entries = stockInitRepo.findByDateOrderByIdDesc(today);
        for (initialisation_stock_journalier entry : entries) {
            Long categoryId = entry.getCategory().getId();
            if (!todayEntries.containsKey(categoryId)) {
                todayEntries.put(categoryId, entry);
            }
        }

        boolean locked = !entries.isEmpty() && !user.isCanEditStockJournalier();

        model.addAttribute("categories", categories);
        model.addAttribute("entries", todayEntries);
        model.addAttribute("today", today);
        model.addAttribute("ret", request.getParameter("ret"));
        model.addAttribute("locked", locked);

        return "stock/stock_initialisation";
    }

    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    public String saveStockInitialisation(@RequestParam("category_id") long[] categoryIds,
                                          @RequestParam("stock_value") double[] stockValues,
                                          @RequestParam("vente_pourcentage") double[] pourcentages,
                                          @RequestParam(value = "vente_base_stock_category", required = false) List<Long> venteBaseStockCategory,
                                          @SessionAttribute("user") users user) {

        get_time_date gtd = new get_time_date();
        String today = gtd.get_date();

        List<initialisation_stock_journalier> entriesToday = stockInitRepo.findByDateOrderByIdDesc(today);
        if (!entriesToday.isEmpty() && !user.isCanEditStockJournalier()) {
            return "redirect:/stock?ret=locked";
        }

        Map<Long, initialisation_stock_journalier> todayEntriesByCategory = new HashMap<Long, initialisation_stock_journalier>();
        for (initialisation_stock_journalier entry : entriesToday) {
            Long categoryId = entry.getCategory().getId();
            if (!todayEntriesByCategory.containsKey(categoryId)) {
                todayEntriesByCategory.put(categoryId, entry);
            }
        }

        Set<Long> venteBaseStockIds = (venteBaseStockCategory == null)
            ? Collections.emptySet()
            : new HashSet<Long>(venteBaseStockCategory);

        if (categoryIds != null) {
            for (int i = 0; i < categoryIds.length; i++) {
                category_produit category = categoryRepo.getOne(categoryIds[i]);

            boolean shouldBeVenteBaseStock = venteBaseStockIds.contains(category.getId());
            if (category.isVenteBaseStock() != shouldBeVenteBaseStock) {
                category.setVenteBaseStock(shouldBeVenteBaseStock);
                categoryRepo.save(category);
            }

                double qty = stockValues[i] < 0 ? 0 : stockValues[i];
                double perc = pourcentages[i] < 0 ? 0 : pourcentages[i];

                initialisation_stock_journalier existing = todayEntriesByCategory.get(category.getId());

                if (existing != null) {
                    existing.setQuantiteStock(qty);
                    existing.setPourcentageVente(perc);
                    existing.setUser(user);
                    existing.setDate(today);
                    existing.setTime(gtd.get_time());
                    stockInitRepo.save(existing);
                    stockInitRepo.flush();

                    initialisation_stock_journalier_trace trace = new initialisation_stock_journalier_trace(existing);
                    stockTraceRepo.save(trace);
                    stockTraceRepo.flush();
                } else {
                    initialisation_stock_journalier init = new initialisation_stock_journalier(category, qty, perc, user);
                    stockInitRepo.save(init);
                    stockInitRepo.flush();

                    initialisation_stock_journalier_trace trace = new initialisation_stock_journalier_trace(init);
                    stockTraceRepo.save(trace);
                    stockTraceRepo.flush();
                }
            }
        }

        return "redirect:/stock?ret=succes";
    }
}
