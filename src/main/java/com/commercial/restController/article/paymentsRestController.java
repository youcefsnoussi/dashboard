package com.commercial.restController.article;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.client.repository.client_registreCommerceRepository;
import com.commercial.entities.schema.client.repository.registre_commerceRepository;
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiement_factureRepository;
import com.commercial.functions.get_time_date;

@RestController

public class paymentsRestController {

	public paymentsRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	registre_commerceRepository rcRepo;
	
	@Autowired
	clientRepository clientRepo;
	
	@Autowired
	client_registreCommerceRepository client_rcRepo;
	
	@Autowired
	paiementRepository payRepo;
	
	@Autowired
	paiement_factureRepository pay_factRepo;
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	mouvementRepository mvmRepo;
	
	//----------------------------------------------------------------------------
	
	@RequestMapping(value="/get_rc_by_client")
	public List<registre_commerce> get_rc_by_client(
		@RequestParam("id_client") long id_client) throws IOException, ParseException{
		
		client clt = clientRepo.getOne(id_client);
		
		List <registre_commerce> rc_list = client_rcRepo.rc_by_client(clt);
		
		return rc_list;
	}
	
	//___________________________________________________________________________________________________
	
	@RequestMapping(value="/cancel_payment")
	public String cancel_payment(
		@RequestParam("id_payment") long id_payment) throws IOException, ParseException{
		
		get_time_date gtd = new get_time_date();
		
		String ret = "ok";
		
		paiement pay = payRepo.getOne(id_payment);
		
		pay.setCancel(true);
		
		payRepo.save(pay);payRepo.flush();
		
		//-------------- update sold client inverse ----------
		
		client clt = pay.getClient();
		
		double sold_encours_clt = clt.getSold_encours();
		
		double new_sold_clt = sold_encours_clt + pay.getMontant();
		
		clt.setSold_encours(new_sold_clt);
		
		clientRepo.save(clt);clientRepo.flush();
		
		//-------------- update sold RC
		
		registre_commerce rc = pay.getRegistre_commerce();
		
		double sold_encours_rc = rc.getSold_encours();
		
		double new_sold_rc = sold_encours_rc + pay.getMontant();
		
		rc.setSold_encours(new_sold_rc);
		
		rcRepo.save(rc);rcRepo.flush();
		
		//-------------- insert to mouvement 
		
		//mouvement mvm = new mouvement(clt, rc, montant, "paiement", pay.getId(), date, gtd.get_time(), banque.getNom_banque());
		
		mouvement mvm = new mouvement(clt, rc, pay.getMontant(), "Annulation Paiement", pay.getId(), gtd.get_date(), gtd.get_time(), "", sold_encours_clt, sold_encours_rc,
				new_sold_clt, new_sold_rc);
		
		mvmRepo.save(mvm);mvmRepo.flush();
		
		//-------------- update factures and paiement factures ------------------------
		
		List<paiement_facture> list_pay_fact = pay_factRepo.get_payfact_by_pay(pay);
		
		for(int i =0; i<list_pay_fact.size();i++) {
			
			paiement_facture pay_fact = list_pay_fact.get(i);
			
			double montant_paye = pay_fact.getMontant_paye_facture();
			
			pay_fact.setMontant_paye_facture(0);
			
			pay_factRepo.save(pay_fact);pay_factRepo.flush();
			
			facture fact = pay_fact.getFacture();
			
			double sold_fact_rest = fact.getSold_rest();
			
			double new_sold_fact_rest = sold_fact_rest + montant_paye;
			
			fact.setSold_rest(new_sold_fact_rest);
			
			factRepo.save(fact);factRepo.flush();
			
		}
		
		return ret;
		
	}
	
}
