package com.commercial.functions;

public class doc_app {

	public doc_app() {
		// TODO Auto-generated constructor stub
	}
	
	/*				
	 * 						ids_banned hia les roles li yakadro yakhedmo bihom 				
	 * 											ghlat dertha ids_banned
	 * 				
	 * 
	 * 				------------------------------  Article  ---------------------------------
					- add_reduction => yakder yajouter reduction de prix
					- add_article => yakder yajouter article
					- edit_article => yakder imodifier aricle
					- view_detail_article => yakder ichof detail ta3 article
					------------------------------  RC  -------------------------------------
					- add_rc => mayakderch yajouti rc (makach button + page inaccessible)
					- edit_rc => edit client previlege
					- rc_client =>yakder yarbet bin rc + client
					
					------------------------------ Client -------------------------------------
					- add_client => manafichich button ta3 add client ma3endoch plafond (=10da) + blocké
					- add_client_admin => add client normal b plafond
					- edit_client => yakder edition client
					
					------------------------------  Payment -------------------------------------
					- add_payment =>  yajouti payement
					- cancel_payment => anuuler payment
					- add_remboursement => yakder irembourssi
	 * 
	 * 				____________________________________________________________________________________________
	 * 
	 * 				------------------------------  RC -------------------------------------
	 *				- add_rc => mayakderch yajouti rc (makach button + page inaccessible)
	 *				- edit_rc => edit client previlege
	 *				- rc_client =>yakder yarbet bin rc + client
	 *				
	 *				------------------------------  Client -------------------------------------
	 *				- add_client => manafichich button ta3 add client ma3endoch plafond (=10da) + blocké
	 *				- add_client_admin => add client normal b plafond
	 *				- edit_client => yakder edition client
	 *				
	 *				------------------------------  Payment -------------------------------------
	 *				- add_payment =>  yajouti payement
	 *				- cancel_payment => anuuler payment
	 *				- add_remboursement => yakder idir remboursement
	 *				- edit_payment => edit payement
	 *
	 *				------------------------------  VENTE -------------------------------------
	 *				- change_user_cmd =>  ibedel user li khdem commande 
	 * 				- cancel_bl => yakder yanuli BL
	 * 				- cancel_ble => yakder yanuli BL emlpoyée
	 * 				- facture_av => yakder idir facture avoir
	 * 				--------------------------------------------------------------------------------
	 * 				- cancel_blq => annuler bon_livraison_facture
	 * 				- edit_blq => edit bon_livraison_facture
	 * 				- cancel_bt => annuler bon_transfert
	 * 				- fact_blfs => facturé les bls
	 * 				--------------------------------------------------------------------------------
	 * 				PS : -> PRIX ARTICLE PAR CATEGORY  = -1 ma3netha manbi3oloch hedek ARTICLE
	 * 					 
	 * 					 -> Table registre_commerce column multiple_bon_livraison if true => client yedi b BL omba3ed ifacturiw kemel les bl li dahom
	 * 																					  table ( bon_livraison_facture )
	 * 																			  false => kol bl yetvalida tokhredj alih facture 
	 * 

	 * 				////////////////////////////////----------------------------------
	 * 
	 * 								th:value="${bl_det.article.produit.sous_category_produit.category_produit.nom_category}+' '+
									  ${#strings.replace(bl_det.article.produit.sous_category_produit.nom_sous_category, bl_det.article.produit.sous_category_produit.category_produit.nom_category, '')}+' '+
									  ${#strings.replace(#strings.replace(bl_det.article.produit.designation, bl_det.article.produit.sous_category_produit.category_produit.nom_category, ''), bl_det.article.produit.sous_category_produit.nom_sous_category, '')}+' '+
									  ${#strings.replace(bl_det.article.emballage_produit.nom_emballage, bl_det.article.produit.sous_category_produit.category_produit.nom_category, '') }+' '+
									  ${bl_det.article.pesage_produit.pesage}+' '+${bl_det.article.pesage_produit.unite_pesage}" 
	 * 
	 * 
	 * 
	 */
	
}
