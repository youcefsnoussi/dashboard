package com.commercial.restController;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.article.Magasin;
import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.article_consignation_relation;
import com.commercial.entities.schema.article.initialisation_stock_journalier;
import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.prixUnitaire_article_categoryClient;
import com.commercial.entities.schema.article.rc_consignation;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.article.repository.article_consignation_relationRepository;
import com.commercial.entities.schema.article.repository.initialisation_stock_journalierRepository;
import com.commercial.entities.schema.article.repository.magasin_articleRepository;
import com.commercial.entities.schema.article.repository.prixUnitaire_article_categoryClient_Repository;
import com.commercial.entities.schema.article.repository.rc_consignationRepository;
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.client_registreCommerce;
import com.commercial.entities.schema.client.reduction_client_prixU_article;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.reduction_client_prixU_articleRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiement_factureRepository;
import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.ConnectionParc;
import com.commercial.functions.Connection_peseur;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;
import com.commercial.services.PaletteService;
import com.commercial.services.track_operations;

@RestController
@SessionAttributes("user")

public class factureRestController {

	@Autowired
	registre_commerceRepository rcRepo;

	@Autowired
	clientRepository clientRepo;

	@Autowired
	client_registreCommerceRepository clt_rcRepo;

	@Autowired
	paiementRepository payRepo;

	@Autowired
	paiement_factureRepository pay_factRepo;

	@Autowired
	factureRepository factRepo;

	@Autowired
	articleRepository artRepo;

	@Autowired
	prixUnitaire_article_categoryClient_Repository pu_a_ctRepo;

	@Autowired
	reduction_client_prixU_articleRepository reduxRepo;

	@Autowired
	magasin_articleRepository magRepo;

	@Autowired
	bon_livraisonRepository bon_lRepo;

	@Autowired
	bon_livraison_factureRepository blfRepo;

	@Autowired
	article_consignation_relationRepository art_cons_relRepo;

	@Autowired
	initialisation_stock_journalierRepository stockJournalRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	PaletteService ps;

	@Autowired
	ConnectionParc con_parc;

	@Autowired
	facture_detailRepository fact_detRepo;

	public factureRestController() {
		// TODO Auto-generated constructor stub
	}

	// ----------------------------------------------------------------

	@RequestMapping(value = "/get_matricules")
	public List<String> get_matricules_from_peuseurDB(
			@SessionAttribute("user") users user) {

		Connection_peseur con = new Connection_peseur();

		List<String> lm = new ArrayList<>();

		if (con.getconnection() != null) {

			lm = con.get_matricule_from_peseur(user.getUnite().getId());

		}

		return lm;

	}

	// ----------------------------------------------------------------
	/*
	 * @RequestMapping(value="/ajax_get_client_by_code")
	 * public client get_client_by_code(
	 * 
	 * @RequestParam("code") String code) throws IOException, ParseException{
	 * 
	 * client clt = clientRepo.get_client_by_code(code);
	 * 
	 * return clt;
	 * }
	 */
	// ----------------------------------------------------------------

	@RequestMapping(value = "/ajax_get_rc_by_client_fact")
	public List<client_registreCommerce> get_rc_by_client_fact(
			@RequestParam("id_client") long id_client) throws IOException, ParseException {

		get_time_date gtd = new get_time_date();

		client clt = clientRepo.getOne(id_client);

		List<client_registreCommerce> list_rc = clt_rcRepo.get_list_rc_by_client_for_facture(clt, gtd.get_date());

		return list_rc;
	}

	// ------------------------------------------------------------------

	@RequestMapping(value = "/ajax_get_art_by_client_cat")
	public List<prixUnitaire_article_categoryClient> get_art_by_client_cat(
			@RequestParam("id_client") long id_client) throws IOException, ParseException {

		client clt = clientRepo.getOne(id_client);

		get_time_date gtd = new get_time_date();

		List<prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo.get_articles_by_CatClient(clt.getCategory());

		// ------------------------- get reduction if existe -----------------------

		for (int i = 0; i < list_art.size(); i++) {

			prixUnitaire_article_categoryClient pu = list_art.get(i);

			reduction_client_prixU_article red = reduxRepo.get_reduction_by_clt_art(clt, pu.getArticle(),
					gtd.get_date());

			if (red != null) {

				pu.setPrix(red.getNouveau_prix()); // naba3to b - bach ndetecti article li fih reduction o f client side
													// nredo normal

				pu.setId((long) -1);

				list_art.set(i, pu);

			}

		}

		// --------------------------------------------------------------------------

		return list_art;

	}

	// ------------------------------------------------------------------

	@Autowired
	tva_Repository tvaRepo;

	@Autowired
	rc_consignationRepository rccRepo;

	@RequestMapping(value = "/ajax_get_art_by_rc_cat")
	public List<prixUnitaire_article_categoryClient> get_art_by_rc_cat(
			@RequestParam("id_rc_clt") long id_rc_clt) throws IOException, ParseException {

		client_registreCommerce clt_rc = clt_rcRepo.getOne(id_rc_clt);

		List<prixUnitaire_article_categoryClient> list_art = pu_a_ctRepo
				.get_articles_by_CatClient(clt_rc.getRegistre_commerce().getCategory());

		// ------------------------- get reduction if existe -----------------------

		list_art.stream().forEach(pu -> {

			double prix = pu.getPrix();

			pu.setPrix(prix * pu.getArticle().getMultiplicator());

			if (clt_rc.getRegistre_commerce().getTva() == 0) {

				// System.out.println("-----<>-- "+tvaRepo.findAll().stream().filter(tva ->
				// tva.getTaux_tva()==0).collect(Collectors.toList()));

				pu.setTva((tva) tvaRepo.findAll().stream().filter(tva -> tva.getTaux_tva() == 0)
						.collect(Collectors.toList()).get(0));

			}

			if (pu.getArticle().isConsignation()) { // ---------> article consignation display price consignation

				Optional<rc_consignation> rcc = Optional
						.ofNullable(rccRepo.getRcConsignation(clt_rc.getRegistre_commerce(), pu.getArticle()));

				rcc.ifPresent(r -> {

					pu.setPrix(r.getPrix_u_ht());

				});

			}

		});

		// for(int i=0;i<list_art.size();i++) {

		// prixUnitaire_article_categoryClient pu = list_art.get(i);
		/*
		 * reduction_client_prixU_article red =
		 * reduxRepo.get_reduction_by_clt_art(clt_rc.getClient(), pu.getArticle(),
		 * gtd.get_date());
		 * 
		 * if(red != null) {
		 * 
		 * pu.setPrix(red.getNouveau_prix()); // naba3to b - bach ndetecti article li
		 * fih reduction o f client side nredo normal
		 * 
		 * pu.setId((long) -1);
		 * 
		 * list_art.set(i, pu);
		 * 
		 * }
		 */
		// if(clt_rc.getRegistre_commerce().getTva()==0) {

		// System.out.println("-----<>-- "+tvaRepo.findAll().stream().filter(tva ->
		// tva.getTaux_tva()==0).collect(Collectors.toList()));

		// pu.setTva((tva) tvaRepo.findAll().stream().filter(tva ->
		// tva.getTaux_tva()==0).collect(Collectors.toList()).get(0) );

		// }

		// }

		// --------------------------------------------------------------------------

		return list_art;

	}

	// ----------------------------------------------------------------

	@RequestMapping(value = "/ajax_get_magasin_by_art")
	public List<Magasin> get_magasin_by_article(
			@RequestParam("id_article") long id_article) throws IOException, ParseException {

		// System.out.println("ajax_get_magasin_by_art ---> id article sent by client
		// -->"+id_article);

		List<Magasin> list_mag = magRepo.get_magasin_by_article(artRepo.getOne(id_article));

		return list_mag;
	}

	// ----------------------------------------------------------------

	@RequestMapping(value = "/ajax_get_art_consign")
	public List<article_consignation_relation> ajax_get_art_consign(
			@RequestParam("id_article") long id_article,
			@RequestParam("id_rc_clt") long id_rc_clt) throws IOException, ParseException {

		// System.out.println("ajax_get_magasin_by_art ---> id article sent by client
		// -->"+id_article);

		// List<article_consignation_relation> list_art_consign =
		// art_cons_relRepo.findByArticle(artRepo.getOne(id_article));

		List<article_consignation_relation> list_art_consign = art_cons_relRepo.getArticleConsignationWithRc(
				artRepo.getOne(id_article),
				clt_rcRepo.getOne(id_rc_clt).getRegistre_commerce());

		return list_art_consign;
	}

	// ------------------------------------------------------------------

	@RequestMapping(value = "/ajax_get_stock_journalier")
	public Map<String, Object> ajax_get_stock_journalier(
			@RequestParam("id_article") long id_article) throws IOException {


		get_time_date gtd = new get_time_date();
		String today = gtd.get_date();

		article article = artRepo.getOne(id_article);
		category_produit category = article.getProduit().getSous_category_produit().getCategory_produit();

		initialisation_stock_journalier stock = stockJournalRepo
				.findTopByCategoryAndDateOrderByIdDesc(category, today);

		Map<String, Object> payload = new HashMap<>();
		payload.put("article_id", id_article);
		payload.put("category_id", category.getId());
		payload.put("vente_base_stock", category.isVenteBaseStock());
		if (stock != null) {
			payload.put("quantite_stock", stock.getQuantiteStock());
			payload.put("pourcentage_vente", stock.getPourcentageVente());
			payload.put("date", stock.getDate());
			payload.put("time", stock.getTime());
		} else {
			payload.put("quantite_stock", 0d);
			payload.put("pourcentage_vente", category.getPourcentageVente());
		}
		return payload;
	}

	// ------------------------------------------------------------------

	@RequestMapping(value = "/ajax_get_article_sold_quantity")
	public Map<String, Object> ajax_get_article_sold_quantity(
			@RequestParam(value = "id_category", required = false) Long id_category,
			@RequestParam(value = "id_article", required = false) Long id_article,
			@RequestParam("date") String date) {

		LocalDate saleDate = LocalDate.parse(date);

		Long categoryId = id_category;
		if (categoryId == null && id_article != null) {
			Optional<article> articleOpt = artRepo.findById(id_article);
			if (articleOpt.isPresent() && articleOpt.get().getProduit() != null
					&& articleOpt.get().getProduit().getSous_category_produit() != null
					&& articleOpt.get().getProduit().getSous_category_produit().getCategory_produit() != null) {
				category_produit category = articleOpt.get().getProduit().getSous_category_produit()
						.getCategory_produit();
				categoryId = (category != null) ? category.getId() : null;
			}
		}

		Map<String, Object> payload = new HashMap<>();
		payload.put("article_id", id_article);
		payload.put("category_id", categoryId);
		payload.put("date", saleDate.toString());

		if (categoryId == null) {
			payload.put("quantite_totale", 0d);
			return payload;
		}

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("COALESCE(( ");
		sql.append("    SELECT SUM(blf_d.quantite) ");
		sql.append("    FROM proforma_cmd_bl_fact.bon_livraison_facture_detail blf_d ");
		sql.append("    JOIN proforma_cmd_bl_fact.bon_livraison_facture blf ");
		sql.append("        ON blf_d.bon_livraison_facture = blf.id ");
		sql.append("    JOIN client.registre_commerce reg ");
		sql.append("        ON blf.registre_commerce = reg.id ");
		sql.append("       AND reg.category_client IN ('1','5') ");
		sql.append("    JOIN article.article art ON art.id = blf_d.article ");
		sql.append("    JOIN article.produit prd ON prd.id = art.produit ");
		sql.append("    JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit ");
		sql.append("    JOIN article.category_produit cp ON cp.id = scp.category_produit ");
		sql.append("    WHERE CAST(blf.date AS date) = :saleDate ");
		sql.append("      AND blf.cancel = false ");
		sql.append("      AND cp.id = :categoryId ");
		sql.append("), 0) ");
		sql.append(" + COALESCE(( ");
		sql.append("    SELECT SUM(fact_d.quantite) ");
		sql.append("    FROM proforma_cmd_bl_fact.facture_detail fact_d ");
		sql.append("    JOIN proforma_cmd_bl_fact.facture fact ");
		sql.append("        ON fact_d.facture = fact.id ");
		sql.append("    JOIN client.registre_commerce reg ");
		sql.append("        ON fact.registre_commerce = reg.id ");
		sql.append("       AND reg.category_client IN ('1','5') ");
		sql.append("    JOIN article.article art ON art.id = fact_d.article ");
		sql.append("    JOIN article.produit prd ON prd.id = art.produit ");
		sql.append("    JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit ");
		sql.append("    JOIN article.category_produit cp ON cp.id = scp.category_produit ");
		sql.append("    WHERE reg.multiple_bon_livraison = false ");
		sql.append("      AND CAST(fact.date AS date) = :saleDate ");
		sql.append("      AND cp.id = :categoryId ");
		sql.append("), 0) ");
		sql.append(" + COALESCE(( ");
		sql.append("    SELECT SUM(bl_d.quantite) ");
		sql.append("    FROM proforma_cmd_bl_fact.bon_livraison_detail bl_d ");
		sql.append("    JOIN proforma_cmd_bl_fact.bon_livraison bl ");
		sql.append("        ON bl_d.bon_livraison = bl.id ");
		sql.append("    JOIN client.registre_commerce reg ");
		sql.append("        ON bl.registre_commerce = reg.id ");
		sql.append("       AND reg.category_client IN ('1','5') ");
		sql.append("    JOIN article.article art ON art.id = bl_d.article ");
		sql.append("    JOIN article.produit prd ON prd.id = art.produit ");
		sql.append("    JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit ");
		sql.append("    JOIN article.category_produit cp ON cp.id = scp.category_produit ");
		sql.append("    WHERE CAST(bl.date AS date) = :saleDate ");
		sql.append("      AND bl.cancel = false ");
		sql.append("      AND bl.etat_livraison = '0' ");
		sql.append("      AND cp.id = :categoryId ");
		sql.append("), 0) ");
		sql.append(" - COALESCE(( ");
		sql.append("    SELECT SUM(fact_a_d.quantite) ");
		sql.append("    FROM proforma_cmd_bl_fact.facture_avoir_detail fact_a_d ");
		sql.append("    JOIN proforma_cmd_bl_fact.facture_avoir fact_a ");
		sql.append("        ON fact_a_d.facture_avoir = fact_a.id ");
		sql.append("    JOIN client.registre_commerce reg ");
		sql.append("        ON fact_a.registre_commerce = reg.id ");
		sql.append("       AND reg.category_client IN ('1','5') ");
		sql.append("    JOIN article.article art ON art.id = fact_a_d.article ");
		sql.append("    JOIN article.produit prd ON prd.id = art.produit ");
		sql.append("    JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit ");
		sql.append("    JOIN article.category_produit cp ON cp.id = scp.category_produit ");
		sql.append("    WHERE reg.multiple_bon_livraison = false ");
		sql.append("      AND CAST(fact_a.date AS date) = :saleDate ");
		sql.append("      AND cp.id = :categoryId ");
		sql.append("), 0) AS quantite_totale");

		Number result = (Number) entityManager
			.createNativeQuery(sql.toString())
			.setParameter("saleDate", Date.valueOf(saleDate))
			.setParameter("categoryId", categoryId)
			.getSingleResult();

		double qty = (result != null) ? result.doubleValue() : 0d;
		payload.put("quantite_totale", qty);

		return payload;
	}

	// ------------------------------------------------------------------

	@RequestMapping(value = "/ajax_test_plafond")
	public Map<String, Integer> test_plafond(
			// @RequestParam("id_client") long id_client,
			@RequestParam("id_rc_clt") long id_relation_rc_client,
			// @RequestParam("id_bl") long id_bl,
			@RequestParam(value = "nbr_palette", defaultValue = "0") double nbr_palette,
			@RequestParam("montant_ttc") double montant_ttc) throws IOException, ParseException {

		// JSONArray arr_obj = new JSONArray();

		// System.out.println("******---------------- ENTER TEST PLAFOND
		// -----------------***");

		HashMap<String, Integer> map = new HashMap<>();

		client_registreCommerce rc_clt = clt_rcRepo.getOne(id_relation_rc_client);
		/*
		 * client clt = rc_clt.getClient();
		 * 
		 * List <bon_livraison> list_bl = bon_lRepo.get_bl_encours_by_clt(clt);
		 * 
		 * double montant = 0;
		 * 
		 * for(int j=0;j<list_bl.size();j++) {
		 * 
		 * montant = montant + list_bl.get(j).getMontant_ttc();
		 * 
		 * }
		 * 
		 * double sold_encours = clt.getSold_encours();
		 * 
		 * sold_encours = sold_encours + montant + montant_ttc;
		 */
		// clt_rc.get(i).getRegistre_commerce().setSold_encours(sold_encours);

		// double balance_clt = clt.getSold_encours();

		// System.out.println("SOLD CLT -> "+sold_encours+"/ plafond CLT ->
		// "+clt.getPlafond());

		/************** TEST CLIENT FATHER **********/
		/*
		 * if(sold_encours>clt.getPlafond()) {
		 * 
		 * map.put("plafond_client", 1);
		 * 
		 * }
		 * else {
		 * 
		 * map.put("plafond_client", 0);
		 * 
		 * }
		 */
		map.put("plafond_client", 0);

		/************** TEST CLIENT RC **********/

		registre_commerce rc = rc_clt.getRegistre_commerce();

		List<bon_livraison> list_bl = bon_lRepo.get_bl_encours_by_rc(rc);

		List<bon_livraison_facture> list_blf = blfRepo.get_blf_non_factured_rc(rc);

		double montant = 0;

		for (int j = 0; j < list_bl.size(); j++)
			montant += list_bl.get(j).getMontant_ttc();

		for (int j = 0; j < list_blf.size(); j++)
			montant += list_blf.get(j).getMontant_ttc();

		double sold_encours = rc.getSold_encours();

		// ------------------------ TEST Palette -----------------------------
		/*
		 * double pricePalette = 0;
		 * 
		 * if(clt.isVentePalette()==true) {
		 * 
		 * pricePalette =
		 * pu_a_ctRepo.get_prix_articles_by_CatClient(rc_clt.getRegistre_commerce().
		 * getCategory(),
		 * artRepo.findByLibelle("Palette")) * nbr_palette;
		 * 
		 * if(ps.testPalettePlafond(list_bl, clt, nbr_palette)) {
		 * 
		 * map.put("plafond_palette", 1);
		 * 
		 * }
		 * else {
		 * 
		 * map.put("plafond_palette", 0);
		 * 
		 * }
		 * 
		 * }
		 * else {
		 * 
		 * map.put("plafond_palette", 0);
		 * 
		 * }
		 */
		// -------------------------------------------------------------------

		sold_encours = sold_encours + montant + montant_ttc; // --------> pricepalette prix total palette + pricePalette

		// System.out.println("SOLD RC -> "+sold_encours+"/ plafond RC ->
		// "+rc.getPlafond());

		// double balance_rc = rc.getSold_encours();

		// System.out.println("("+(rc.getSold_encours()+montant)+") = ("+sold_encours+")
		// ==>> "+rc.getPlafond());

		if (sold_encours > rc.getPlafond()) {

			map.put("plafond_rc", 1);

		} else {

			map.put("plafond_rc", 0);

		}

		// map.put("plafond_rc", 0);

		return map;

	}

	// ------------------------------------------------------------------

	@RequestMapping(value = "/get_notification")
	public List<facture> notification_facture_ready(
			@SessionAttribute("user") users user) throws IOException, ParseException {

		// System.out.println("user entred -> "+user.getUsername());

		List<facture> list_fct = new ArrayList<facture>();

		List<facture> ret = new ArrayList<facture>();

		if (user.getRole().getNom_role().equals("Admin")) {

			list_fct = factRepo.get_notifications_admin();

			ret = list_fct;

		}

		else if (user.getRole().getNom_role().equals("A.C.imprimer")) {

			list_fct = factRepo.get_notifications_admin();

			for (int i = 0; i < list_fct.size(); i++) {

				facture fct = list_fct.get(i);

				fct.setNotification(true);

				factRepo.save(fct);
				factRepo.flush();

			}

			ret = list_fct;

		}

		else {

			list_fct = factRepo.get_notification_by_user(user);

			for (int i = 0; i < list_fct.size(); i++) {

				facture fct = list_fct.get(i);

				fct.setNotification(true);

				factRepo.save(fct);
				factRepo.flush();

			}

			ret = list_fct;

		}

		return ret;

	}

	// _____________________________________ hedi ta3 REST Controller article mabid
	// dertha hna _____________

	@RequestMapping(value = "/get_prix_by_client")
	public Double get_prix_article_by_client(
			@RequestParam("id_client") long id_client,
			@RequestParam("id_article") long id_article) throws IOException, ParseException {

		category_client cat_client = clientRepo.getOne(id_client).getCategory();

		article art = artRepo.getOne(id_article);

		prixUnitaire_article_categoryClient prix = pu_a_ctRepo.get_prix_articles_by_CatClient_Object(cat_client, art);

		// Map<String, prixUnitaire_article_categoryClient> ret = new HashMap<>();
		//
		// ret.put("prix", prix);
		double ret = prix.getPrix();

		return ret;

	}

	// ------------------------------------------------- Edit matricule Controller
	// --------------

	@RequestMapping(value = "/update_matricule_facture")
	public String update_matricule_facture(
			@RequestParam("id_facture") long id_fact,
			@RequestParam("matricule") String matricule,
			@SessionAttribute("user") users user) throws IOException, ParseException {

		facture fact = factRepo.getOne(id_fact);

		fact.setMatricule_camion(matricule);

		factRepo.save(fact);
		factRepo.flush();

		track_operations trk = new track_operations();

		trk.add_track("facture", "Changement matricule Facture", id_fact, user);

		return "OK";

	}

	@RequestMapping(value = "/ajax_get_price_by_type")
	public Double getPriceByType(
			@RequestParam("idWilaya") String idWilaya, @RequestParam("type") Integer type,
			@RequestParam("comune") String comune) throws IOException, ParseException {

		// System.out.println("ajax_get_magasin_by_art ---> id article sent by client
		// -->"+id_article);

		Double price = con_parc.getPriceByTypeAndWilaya(type, idWilaya, comune);
		// Double price = con_parc.test(

		return price;
	}

	// ------------------------------------------TEST CONNECTION
	// ------------------------>
	@RequestMapping(value = "/TestCon")
	public void testConnection() {

	}

	/****************************** FODHIL *******************************/

	@RequestMapping(value = "/ajax_stat_qte_ca_month")
	public List<Object[]> stat_qte_ca_month(
			@RequestParam("start") String date_debut,
			@RequestParam("end") String date_fin) throws IOException, ParseException {

		System.out.println(date_debut + "-->" + date_fin);

		convert_string_to_date_util conv = new convert_string_to_date_util();

		List<Object[]> list = new ArrayList<Object[]>();

		// list =
		// fact_detRepo.qte_quantite_val_year_month(conv.convertion_InputDate_to_MyDate(date_debut),
		// conv.convertion_InputDate_to_MyDate(date_fin));
		list = fact_detRepo.qte_quantite_val_year_month(conv.convertion_InputDate_to_MyDate(date_debut),
				conv.convertion_InputDate_to_MyDate(date_fin));

		return list;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/ajax_stat_ca_produit")
	public List<Object[]> stat_ca_produit(
			@RequestParam("start") String date_debut,
			@RequestParam("end") String date_fin) throws IOException, ParseException {

		// System.out.println("ajax_get_magasin_by_art ---> id article sent by client
		// -->"+id_article);

		convert_string_to_date_util conv = new convert_string_to_date_util();

		List<Object[]> list = new ArrayList<Object[]>();

		// list =
		// fact_detRepo.qte_quantite_val_year_month(conv.convertion_InputDate_to_MyDate(date_debut),
		// conv.convertion_InputDate_to_MyDate(date_fin));
		list = fact_detRepo.ca_par_produit(conv.convertion_InputDate_to_MyDate(date_debut),
				conv.convertion_InputDate_to_MyDate(date_fin));

		return list;
	}

	////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/ajax_stat_qte_produit")
	public List<Object[]> stat_qte_produit(
			@RequestParam("start") String date_debut,
			@RequestParam("end") String date_fin) throws IOException, ParseException {

		// System.out.println("ajax_get_magasin_by_art ---> id article sent by client
		// -->"+id_article);

		convert_string_to_date_util conv = new convert_string_to_date_util();

		List<Object[]> list = new ArrayList<Object[]>();

		// list =
		// fact_detRepo.qte_quantite_val_year_month(conv.convertion_InputDate_to_MyDate(date_debut),
		// conv.convertion_InputDate_to_MyDate(date_fin));
		list = fact_detRepo.qte_par_produit(conv.convertion_InputDate_to_MyDate(date_debut),
				conv.convertion_InputDate_to_MyDate(date_fin));

		return list;
	}
	////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/ajax_stat_get_ca_rc")
	public List<Object[]> stat_get_ca_rc(
			@RequestParam("start") String date_debut,
			@RequestParam("end") String date_fin) throws IOException, ParseException {

		// System.out.println("ajax_get_magasin_by_art ---> id article sent by client
		// -->"+id_article);

		convert_string_to_date_util conv = new convert_string_to_date_util();

		List<Object[]> list = new ArrayList<Object[]>();

		// list =
		// fact_detRepo.qte_quantite_val_year_month(conv.convertion_InputDate_to_MyDate(date_debut),
		// conv.convertion_InputDate_to_MyDate(date_fin));
		list = fact_detRepo.get_ca_rc(conv.convertion_InputDate_to_MyDate(date_debut),
				conv.convertion_InputDate_to_MyDate(date_fin));

		return list;
	}
	////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "/ajax_stat_get_qte_rc")
	public List<Object[]> stat_get_qte_rc(
			@RequestParam("start") String date_debut,
			@RequestParam("end") String date_fin) throws IOException, ParseException {

		// System.out.println("ajax_get_magasin_by_art ---> id article sent by client
		// -->"+id_article);

		convert_string_to_date_util conv = new convert_string_to_date_util();

		List<Object[]> list = new ArrayList<Object[]>();

		// list =
		// fact_detRepo.qte_quantite_val_year_month(conv.convertion_InputDate_to_MyDate(date_debut),
		// conv.convertion_InputDate_to_MyDate(date_fin));
		list = fact_detRepo.get_qte_rc(conv.convertion_InputDate_to_MyDate(date_debut),
				conv.convertion_InputDate_to_MyDate(date_fin));

		return list;
	}
	////////////////////////////////////////////////////////////////////////////////
	/****************************** FODHIL *******************************/

}
