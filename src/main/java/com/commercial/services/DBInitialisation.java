package com.commercial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.repository.regionRepository;
import com.commercial.entities.schema.article.repository.wilayaRepository;
import com.commercial.entities.schema.client.category_client;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.static_data.banque;
import com.commercial.entities.schema.static_data.causes_facture_avoir;
import com.commercial.entities.schema.static_data.mode_paiement;
import com.commercial.entities.schema.static_data.region;
import com.commercial.entities.schema.static_data.tva;
import com.commercial.entities.schema.static_data.type_reglement;
import com.commercial.entities.schema.static_data.unite;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.static_data.wilaya;
import com.commercial.entities.schema.static_data.repository.banqueRepository;
import com.commercial.entities.schema.static_data.repository.causes_facture_avoirRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.type_reglementRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.entities.schema.user_menu.menu;
import com.commercial.entities.schema.user_menu.roles;
import com.commercial.entities.schema.user_menu.roles_menu;
import com.commercial.entities.schema.user_menu.sub_menu;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.entities.schema.user_menu.repository.menuRepository;
import com.commercial.entities.schema.user_menu.repository.rolesRepository;
import com.commercial.entities.schema.user_menu.repository.roles_menuRepository;
import com.commercial.entities.schema.user_menu.repository.sub_menuRepository;
import com.commercial.entities.schema.user_menu.repository.userRepository;

@Service

public class DBInitialisation {

	public DBInitialisation() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	menuRepository menuRepo;
	
	@Autowired
	sub_menuRepository subMenuRepo;
	
	@Autowired
	rolesRepository rolesRepo;
	
	@Autowired
	roles_menuRepository rolesMenuRepo;
	
	@Autowired
	userRepository userRepo;
	
	@Autowired
	regionRepository regRepo;
	
	@Autowired
	wilayaRepository wilayaRepo;
	
	@Autowired
	unite_mesureRepository umRepo;
	
	@Autowired
	uniteRepository unRepo;
	
	@Autowired
	type_reglementRepository tpRepo;
	
	@Autowired
	tva_Repository tvaRepo;
	
	@Autowired
	mode_paiementRepository mdRepo;
	
	@Autowired
	causes_facture_avoirRepository causeRepo;
	
	@Autowired
	banqueRepository bankRepo;
	
	@Autowired
	category_clientRepository catCRepo;
	
	//---------------------------------------------------------------
	
	//-------------------- user_menu Schema -------------------------
	
	public void initMenu () {
		
		if( menuRepo.findAll().size() == 0 ) {
			
			menuRepo.save( new menu("Client", 1) );
			menuRepo.save( new menu("Article", 2) );
			menuRepo.save( new menu("Vente", 3) );
			menuRepo.save( new menu("Parametre", 7) );
			menuRepo.save( new menu("Operation", 4) );
			menuRepo.save( new menu("Statistique", 6) );
			menuRepo.save( new menu("Transfert", 5) );
			
			menuRepo.flush();
			
		}
		
	}
	
	public void initSubMenu () {
		
		if( subMenuRepo.findAll().size() == 0 ) {
			
			subMenuRepo.save( new sub_menu("Information de l'entreprise", "info_ent", 1, menuRepo.getOne((long) 4),"", "align-justify") );
			subMenuRepo.save( new sub_menu("Liste des articles", "list_art", 1, menuRepo.getOne((long) 2),"", "th-list") );
			subMenuRepo.save( new sub_menu("Listes des RC", "list_rc?id_c=0", 2, menuRepo.getOne((long) 1),"", "list-alt") );
			subMenuRepo.save( new sub_menu("Liste des clients", "list_client?id_rc=0", 1, menuRepo.getOne((long) 1),"", "address-book") );
			subMenuRepo.save( new sub_menu("List des banques", "list_banq", 2, menuRepo.getOne((long) 4),"", "university") );
			subMenuRepo.save( new sub_menu("modes de paiements", "list_mode_p", 3, menuRepo.getOne((long) 4),"", "cash-register") );
			subMenuRepo.save( new sub_menu("TVA", "tva", 4, menuRepo.getOne((long) 4),"", "percentage") );
			subMenuRepo.save( new sub_menu("Unité", "unite", 5, menuRepo.getOne((long) 4),"", "industry-alt") );
			subMenuRepo.save( new sub_menu("Liste des types règlements", "list_type_reg", 6, menuRepo.getOne((long) 4),"", "money-check") );
			subMenuRepo.save( new sub_menu("Facture", "fact", 0, menuRepo.getOne((long) 3),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("liste des paiements", "list_payments?date_debut=0&date_fin=0", 1, menuRepo.getOne((long) 5),"", "cash-register") );
			subMenuRepo.save( new sub_menu("Liste des factures", "list_fact?date_debut=0&date_fin=0", 4, menuRepo.getOne((long) 3),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Relevé client", "historic_clt?clt=0&start=0&end=0", 1, menuRepo.getOne((long) 6),"", "history") );
			subMenuRepo.save( new sub_menu("Relevé RC", "historic_rc?rc=0&start=0&end=0", 2, menuRepo.getOne((long) 6),"", "history") );
			subMenuRepo.save( new sub_menu("Liste des factures avoirs", "list_fact_avoir?date_debut=0&date_fin=0", 5, menuRepo.getOne((long) 3),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Commande", "commande", 1, menuRepo.getOne((long) 3),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("List BL encours EXP", "list_bl_encours", 3, menuRepo.getOne((long) 3),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("List BL encours", "list_bl", 2, menuRepo.getOne((long) 3),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Bon Commande", "bon_cmd", 6, menuRepo.getOne((long) 3),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("Listing des prix", "listing_prix", 2, menuRepo.getOne((long) 2),"", "clipboard-list") );
			subMenuRepo.save( new sub_menu("Vente par Article", "historic_ventes?start=0&end=0", 3, menuRepo.getOne((long) 6),"", "clipboard-list") );
			subMenuRepo.save( new sub_menu("List RC achteurs", "historic_ventes_rc?start=0&end=0", 4, menuRepo.getOne((long) 6),"", "clipboard-list") );
			subMenuRepo.save( new sub_menu("Vente par Article Val", "historic_ventes_val?start=0&end=0", 5, menuRepo.getOne((long) 6),"", "clipboard-list") );
			subMenuRepo.save( new sub_menu("Vente Employee", "vente_employee", 7, menuRepo.getOne((long) 3),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("List Bon Employee", "bl_encours_employee?start=0&end=0", 8, menuRepo.getOne((long) 3),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Bon Transfert", "bon_transfert", 1, menuRepo.getOne((long) 7),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("List Bon Transfert", "list_bon_transfert?start=0&end=0", 2, menuRepo.getOne((long) 7),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Bon Transfert Interne", "bon_transfert_interne", 3, menuRepo.getOne((long) 7),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("List Bon Transfert I", "list_bon_transfert_interne?start=0&end=0", 4, menuRepo.getOne((long) 7),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Vente Par Client", "vente_client?start=0&end=0&id_rc=0", 6, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("Ventes Par Produit", "vente_produit?start=0&end=0&id_art=0", 7, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("Ventes Par Produit Val", "vente_produit_val?start=0&end=0&id_art=0", 8, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("Ventes Par Produit/Client", "vente_produit_client?start=0&end=0&id_rc=0", 9, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("Facture Ristourne", "facture_ristourne", 9, menuRepo.getOne((long) 3),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("Déclaration TVA", "declaration_tva?start=0&end=0", 13, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("Etat des ventes par client", "etat_vente_client", 10, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("Etat sortie Article", "etat_sortie", 11, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("Etat sortie (Quantitatif)", "etat_sortie_category", 12, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("List BL Non Facture", "list_blf_no_f", 10, menuRepo.getOne((long) 3),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Facturation de BL", "list_blf_by_rc", 11, menuRepo.getOne((long) 3),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("List BL Facturé", "list_blf_f", 12, menuRepo.getOne((long) 3),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Récap Clients", "recap_clients", 3, menuRepo.getOne((long) 1),"", "address-book") );
			subMenuRepo.save( new sub_menu("Etat des Blés SUB", "etat_sortie_subvension", 13, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("List des Clients Achteurs", "GetClientsByCategory", 4, menuRepo.getOne((long) 1),"", "clipboard-list") );
			subMenuRepo.save( new sub_menu("Proforma", "proforma", 13, menuRepo.getOne((long) 3),"", "file-invoice") );
			subMenuRepo.save( new sub_menu("List Proformas", "list_proforma", 14, menuRepo.getOne((long) 3),"", "clipboard-list-check") );
			subMenuRepo.save( new sub_menu("Etat 104", "etat_104", 14, menuRepo.getOne((long) 6),"", "file-pdf") );
			subMenuRepo.save( new sub_menu("Rapport par Wilaya", "rapport_wilaya", 16, menuRepo.getOne((long) 6),"", "clipboard-list") );
			subMenuRepo.save( new sub_menu("Rapport par Wilaya Detail", "rapport_wilaya_detail", 17, menuRepo.getOne((long) 6),"", "clipboard-list") );
			subMenuRepo.save( new sub_menu("Sold par date", "sold_rc_date", 5, menuRepo.getOne((long) 1),"", "balance-scale-right") );
			subMenuRepo.save( new sub_menu("List des Ristournes", "list_ristourne", 10, menuRepo.getOne((long) 3),"", "clipboard-list") );
			subMenuRepo.save( new sub_menu("Relier Artciel Consignation", "list_art_cons", 3, menuRepo.getOne((long) 2),"", "repeat") );
			subMenuRepo.save( new sub_menu("Relier RC Consignation", "list_rc_cons", 4, menuRepo.getOne((long) 2),"", "repeat") );
			subMenuRepo.save( new sub_menu("Rapport Ultra Detailler", "rapport_ultra_detailler", 15, menuRepo.getOne((long) 6),"", "clipboard-list") );
			
			subMenuRepo.flush();
			
		}
		
	}
	
	public void initRoles () {
		
		if( rolesRepo.findAll().size() == 0 ) {
			
			rolesRepo.save( new roles("Admin", "/", false, "") );
			rolesRepo.save( new roles("Responsable", 
					"/add_article/edit_article/add_client/edit_client/rc_client/edit_rc/add_rc/rc_client/add_payment" +
					"/add_remboursement/cancel_payment/cancel_bl/facture_av/cancel_ble/cancel_blq/edit_blq/fact_blfs" +
					"/edit_payment/edit_date_relation/dashboard/edit_matricule_fact/manipulate_plafond", false, "") );
			rolesRepo.save( new roles("Expédition", "/", true, "list_bl_encours") );
			rolesRepo.save( new roles("Commercial", "/add_rc/add_payment/facture_av", false, "") );
			rolesRepo.save( new roles("Gérant", "/", false, "") );
			rolesRepo.save( new roles("Comptabilité", "/", false, "") );

			
			rolesRepo.flush();
			
		}
		
	}
	
	public void initRolesMenu () {
		
		if( rolesMenuRepo.findAll().size() == 0 ) {
			
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 1)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 2)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 3)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 4)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 5)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 6)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 7)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 8)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 9)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 11)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 12)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 13)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 14)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 15)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 16)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 17)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 18)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 19)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 20)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 21)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 22)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 23)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 24)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 25)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 26)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 27)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 28)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 29)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 30)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 31)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 32)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 33)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 34)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 35)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 36)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 37)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 38)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 39)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 40)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 41)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 42)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 43)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 44)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 45)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 46)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 47)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 48)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 49)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 50)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 51)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 52)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 53)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 1), subMenuRepo.getOne((long) 54)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 2)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 3)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 4)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 5)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 6)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 7)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 9)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 11)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 12)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 14)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 15)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 16)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 17)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 18)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 20)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 22)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 23)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 24)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 25)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 28)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 29)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 30)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 31)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 32)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 33)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 36)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 37)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 38)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 39)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 40)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 41)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 43)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 44)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 45)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 46)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 48)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 49)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 50)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 2), subMenuRepo.getOne((long) 54)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 3), subMenuRepo.getOne((long) 17)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 2)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 3)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 11)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 12)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 15)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 16)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 17)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 18)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 20)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 23)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 24)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 25)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 28)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 4), subMenuRepo.getOne((long) 29)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 2)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 11)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 12)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 14)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 15)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 20)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 22)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 23)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 25)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 27)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 29)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 30)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 31)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 32)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 33)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 42)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 44)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 5), subMenuRepo.getOne((long) 54)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 2)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 3)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 11)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 12)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 14)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 15)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 20)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 21)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 22)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 23)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 25)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 30)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 31)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 32)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 33)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 35)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 39)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 41)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 42)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 43)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 44)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 47)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 48)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 49)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 50)) );
			rolesMenuRepo.save( new roles_menu(rolesRepo.getOne((long) 6), subMenuRepo.getOne((long) 54)) );

			rolesMenuRepo.flush();
			
		}
		
	}
	
	public void initUsers () {
		
		if( userRepo.findAll().size() == 0 ) {
			
			userRepo.save( new users("admin","root", "Admin", "Admin", "D:/Commercial/no_image.png", 
					true, rolesRepo.getOne((long) 1), null, "", null) );

			userRepo.flush();
			
		}
		
	}
	
	public void initRegion () {
		
		if( regRepo.findAll().size() == 0 ) {
			
			regRepo.save( new region("Est") );
			regRepo.save( new region("Centre") );
			regRepo.save( new region("Ouest") );
			regRepo.save( new region("Sud") );

			regRepo.flush();
			
		}
		
	}
	
	public void initWilaya () {
		
		if( wilayaRepo.findAll().size() == 0 ) {
			
			wilayaRepo.save( new wilaya("ADRAR", regRepo.getOne((long) 4), "01") );
			wilayaRepo.save( new wilaya("CHELEF", regRepo.getOne((long) 2), "02") );
			wilayaRepo.save( new wilaya("BLIDA", regRepo.getOne((long) 2), "09") );
			wilayaRepo.save( new wilaya("ALGER", regRepo.getOne((long) 2), "16") );
			wilayaRepo.save( new wilaya("MEDEA", regRepo.getOne((long) 2), "26") );
			wilayaRepo.save( new wilaya("TLEMCEN", regRepo.getOne((long) 3), "13") );
			wilayaRepo.save( new wilaya("ORAN", regRepo.getOne((long) 3), "31") );
			wilayaRepo.save( new wilaya("BOUMERDES", regRepo.getOne((long) 2), "35") );
			wilayaRepo.save( new wilaya("TAMENRASSET", regRepo.getOne((long) 4), "11") );
			wilayaRepo.save( new wilaya("LAGHOUAT", regRepo.getOne((long) 4), "03") );
			wilayaRepo.save( new wilaya("OUM-EL BOUAGHI", regRepo.getOne((long) 1), "04") );
			wilayaRepo.save( new wilaya("BATNA", regRepo.getOne((long) 1), "05") );
			wilayaRepo.save( new wilaya("BEJAIA", regRepo.getOne((long) 1), "06") );
			wilayaRepo.save( new wilaya("BISKRA", regRepo.getOne((long) 4), "07") );
			wilayaRepo.save( new wilaya("BECHAR", regRepo.getOne((long) 4), "08") );
			wilayaRepo.save( new wilaya("BOUIRA", regRepo.getOne((long) 2), "10") );
			wilayaRepo.save( new wilaya("TEBESSA", regRepo.getOne((long) 1), "12") );
			wilayaRepo.save( new wilaya("DJELFA", regRepo.getOne((long) 4), "17") );
			wilayaRepo.save( new wilaya("DJIJEL", regRepo.getOne((long) 1), "18") );
			wilayaRepo.save( new wilaya("SETIF", regRepo.getOne((long) 1), "19") );
			wilayaRepo.save( new wilaya("SAIDA", regRepo.getOne((long) 3), "20") );
			wilayaRepo.save( new wilaya("SKIKDA", regRepo.getOne((long) 1), "21") );
			wilayaRepo.save( new wilaya("SIDI BELABBES", regRepo.getOne((long) 3), "22") );
			wilayaRepo.save( new wilaya("ANNABA", regRepo.getOne((long) 1), "23") );
			wilayaRepo.save( new wilaya("GUELMA", regRepo.getOne((long) 1), "24") );
			wilayaRepo.save( new wilaya("CONSTANTINE", regRepo.getOne((long) 1), "25") );
			wilayaRepo.save( new wilaya("MOSTAGANEM", regRepo.getOne((long) 3), "27") );
			wilayaRepo.save( new wilaya("M SILA", regRepo.getOne((long) 1), "28") );
			wilayaRepo.save( new wilaya("MASCARA", regRepo.getOne((long) 3), "29") );
			wilayaRepo.save( new wilaya("OUARGLA", regRepo.getOne((long) 4), "30") );
			wilayaRepo.save( new wilaya("TIPAZA", regRepo.getOne((long) 2), "42") );
			wilayaRepo.save( new wilaya("MILA", regRepo.getOne((long) 1), "43") );
			wilayaRepo.save( new wilaya("AIN DEFLA", regRepo.getOne((long) 2), "44") );
			wilayaRepo.save( new wilaya("NAAMA", regRepo.getOne((long) 4), "45") );
			wilayaRepo.save( new wilaya("AIN TIMOUCHENT", regRepo.getOne((long) 3), "46") );
			wilayaRepo.save( new wilaya("GHARDAIA", regRepo.getOne((long) 4), "47") );
			wilayaRepo.save( new wilaya("RELIZANE", regRepo.getOne((long) 3), "48") );
			wilayaRepo.save( new wilaya("TISSEMSILT", regRepo.getOne((long) 3), "38") );
			wilayaRepo.save( new wilaya("EL OUED", regRepo.getOne((long) 4), "39") );
			wilayaRepo.save( new wilaya("KHENCHLA", regRepo.getOne((long) 1), "40") );
			wilayaRepo.save( new wilaya("SOUK AHRAS", regRepo.getOne((long) 1), "41") );
			wilayaRepo.save( new wilaya("EL-BAYED", regRepo.getOne((long) 4), "32") );
			wilayaRepo.save( new wilaya("ILLIZI", regRepo.getOne((long) 4), "33") );
			wilayaRepo.save( new wilaya("B.BOU ARRERIDJ", regRepo.getOne((long) 1), "34") );
			wilayaRepo.save( new wilaya("EL TAREF", regRepo.getOne((long) 1), "36") );
			wilayaRepo.save( new wilaya("TINDOUF", regRepo.getOne((long) 4), "37") );
			wilayaRepo.save( new wilaya("TIARET", regRepo.getOne((long) 3), "14") );
			wilayaRepo.save( new wilaya("TIZI-OUZOU", regRepo.getOne((long) 2), "15") );


			wilayaRepo.flush();
			
		}
		
	}
	
	public void initUniteMesure () {
		
		if( umRepo.findAll().size() == 0 ) {
			
			umRepo.save( new unite_mesure("G") );
			umRepo.save( new unite_mesure("KG") );
			umRepo.save( new unite_mesure("Q") );
			umRepo.save( new unite_mesure("T") );
			umRepo.save( new unite_mesure("Carton") );
			umRepo.save( new unite_mesure("Unité") );
			umRepo.save( new unite_mesure("CL") );
			umRepo.save( new unite_mesure("L") );
			umRepo.save( new unite_mesure("ML") );

			umRepo.flush();
			
		}
		
	}
	
	public void initUnite () {
		
		if( unRepo.findAll().size() == 0 ) {
			
			unRepo.save( new unite("Unité Principale", 1) );

			unRepo.flush();
			
			users admin = userRepo.getOne((long) 1);
			
			admin.setUnite(unRepo.getOne((long)1));
			
			userRepo.save(admin); userRepo.flush();
			
		}
		
	}
	
	public void initTypeReglement () {
		
		if( tpRepo.findAll().size() == 0 ) {
			
			tpRepo.save( new type_reglement("A Terme") );
			tpRepo.save( new type_reglement("A Compte") );

			tpRepo.flush();
			
		}
		
	}
	
	public void initTva () {
		
		if( tvaRepo.findAll().size() == 0 ) {
			
			tvaRepo.save( new tva(0) );
			tvaRepo.save( new tva(9) );
			tvaRepo.save( new tva(19) );

			tvaRepo.flush();
			
		}
		
	}
	
	public void initModePaiement () {
		
		if( mdRepo.findAll().size() == 0 ) {
			
			mdRepo.save( new mode_paiement("Chèque") );
			mdRepo.save( new mode_paiement("Virement") );
			mdRepo.save( new mode_paiement("Versement") );
			mdRepo.save( new mode_paiement("Retenu sur paie") );

			mdRepo.flush();
			
		}
		
	}
	
	public void initCauseFactureAvoir () {
		
		if( causeRepo.findAll().size() == 0 ) {
			
			causeRepo.save( new causes_facture_avoir("Annulation facture") );
			causeRepo.save( new causes_facture_avoir("Avarie") );
			causeRepo.save( new causes_facture_avoir("Retour Marchandise") );

			causeRepo.flush();
			
		}
		
	}
	
	public void initBanque () {
		
		if( bankRepo.findAll().size() == 0 ) {
			
			bankRepo.save( new banque("", "BNP", "") );
			bankRepo.save( new banque("", "BDL", "") );
			bankRepo.save( new banque("", "BNA", "") );
			bankRepo.save( new banque("", "BADR", "") );
			bankRepo.save( new banque("", "CPA", "") );
			bankRepo.save( new banque("", "NATIXIS", "") );
			bankRepo.save( new banque("", "TRUST", "") );
			bankRepo.save( new banque("", "SGA", "") );
			bankRepo.save( new banque("", "AL SALAM BANK", "") );
			bankRepo.save( new banque("", "ALGERIE POSTE", "") );
			bankRepo.save( new banque("", "AGB", "") );
			
			bankRepo.flush();
			
		}
		
	}
	
	public void initCategoryClient () {
		
		if( catCRepo.findAll().size() == 0 ) {
			
			catCRepo.save( new category_client("Grossiste", "G", "") );
			catCRepo.save( new category_client("Boulangerie", "B", "") );
			catCRepo.save( new category_client("Personnel", "P", "") );
			catCRepo.save( new category_client("Société", "S", "") );
			catCRepo.save( new category_client("MDN", "M", "") );
			catCRepo.save( new category_client("Distributeur", "D", "") );
			catCRepo.save( new category_client("Export", "EX", "") );
			catCRepo.save( new category_client("Artisant", "A", "") );
			catCRepo.save( new category_client("Détaillant", "D", "") );
			catCRepo.save( new category_client("Consomateur", "C", "") );
			catCRepo.save( new category_client("Cooperative", "CO", "") );
			catCRepo.save( new category_client("Catering", "CA", "") );

			catCRepo.flush();
			
		}
		
	}
	
}
