package com.commercial.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.static_data.unite_mesure;
import com.commercial.entities.schema.static_data.repository.tva_Repository;
import com.commercial.entities.schema.static_data.repository.unite_mesureRepository;
import com.commercial.functions.convert_string_to_date_util;

@Service
public class FactureAvoirFromMultipleFactService {
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository factDetRepo;
	
	@Autowired
	facture_avoirRepository factAvRepo;
	
	@Autowired
	facture_avoir_detailRepository factAvDetRepo;
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	unite_mesureRepository umRepo;
	
	@Autowired
	tva_Repository tvaRepo;
	
	public FactureAvoirFromMultipleFactService() {
		// TODO Auto-generated constructor stub
	}
	
	public List< Map<String, Object> > getDetailFactAvFromMultipleFact( List<facture> facts ){
		
		List< Map<String, Object> > result = new ArrayList<>();
		
		List<Object[]> detailsObj = factDetRepo.get_cumule_detail_facture(facts);
		
		for (Object[] obj : detailsObj) {
			
			//article art = (article) obj[0];
			article art = artRepo.getOne((long)obj[0]);
			
			//unite_mesure um = (unite_mesure) obj[1];
			
			unite_mesure um = umRepo.getOne((long)obj[1]);
			
			double prix_u_ht = (double) obj[2];
			
			double quant = (double) obj[3];
			
			double montant_ht = (double) obj[4];
			
			double montant_tva = (double) obj[5];
			
			double montant_ttc = (double) obj[6];
			
			double tva = (double) obj[7];
			
			double montant_remise = (double) obj[8];
			
			double montant_ht_net = (double) obj[9];
			/*
			System.out.println( art.getCode()+" | "+um.getNom_unite_mesure()+" | "+quant+" | "+prix_u_ht+" | "+tva+" | "+montant_ht+" | "+montant_tva+" | "+
					montant_ttc);
			*/
			
			Map<String, Object> tmp = new HashMap<String, Object>();
			
			tmp.put("article", art);
			tmp.put("unite_mesure", um);
			tmp.put("prix_u_ht", prix_u_ht);
			tmp.put("quantite", quant);
			tmp.put("montant_ht", montant_ht);
			tmp.put("montant_tva", montant_tva);
			tmp.put("montant_ttc", montant_ttc);
			tmp.put("tva", tva);
			tmp.put("montant_remise", montant_remise);
			tmp.put("montant_ht_net", montant_ht_net);
			tmp.put("pourcentage_remise", (montant_ht==0) ? 0 : (montant_remise * 100)/montant_ht);
			
			result.add(tmp);
			
		}
		
		return result;
		
	}
	
	
	public Map<String, Double> get_totals_montant (List< Map<String, Object> > input){
		
		Map<String, Double> result = new HashMap<>();
		
		double montant_ht = 0, montant_ht_net = 0, montant_remise = 0, montant_tva = 0, montant_ttc = 0;
		
		for (Map<String, Object> map : input) {
			
			montant_ht += (double) map.get("montant_ht");
			montant_ht_net += (double) map.get("montant_ht_net");
			montant_remise += (double) map.get("montant_remise");
			montant_tva += (double) map.get("montant_tva");
			montant_ttc += (double) map.get("montant_ttc");
			
		}
		
		result.put("montant_ht", montant_ht);
		
		result.put("montant_ht_net", montant_ht_net);
		
		result.put("montant_remise", montant_remise);
		
		result.put("pourcentage_reduction", (montant_remise * 100) / montant_ht);
		
		result.put("montant_tva", montant_tva);
		
		result.put("montant_ttc", montant_ttc);
		
		return result;
		
	}
	
	public List<Map<String, Object>> get_fact_avoir_list (String start, String end) throws ParseException{
		
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		List<facture_avoir> lst_fact_av = factAvRepo.date_between_facture_avoir(conv.convertion_from_my_date(start), 
				conv.convertion_from_my_date(end));
		
		List<Map<String, Object>> ret = new ArrayList<>();
		
		for (facture_avoir fact_av : lst_fact_av) {
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("facture_avoir", fact_av);
			map.put("lst_facture", factRepo.get_facts_by_fact_avoir(fact_av));
			
			ret.add(map);
			
		}
		
		return ret;
		
	}
	
public List<Map<String, Object>> get_fact_avoir_list_client (long idClient) throws ParseException{
		
		
		List<facture_avoir> lst_fact_av = factAvRepo.findByClientId(idClient, 
				Sort.by(Sort.Direction.DESC, "date"));
		
		List<Map<String, Object>> ret = new ArrayList<>();
		
		for (facture_avoir fact_av : lst_fact_av) {
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("facture_avoir", fact_av);
			map.put("lst_facture", factRepo.get_facts_by_fact_avoir(fact_av));
			
			ret.add(map);
			
		}
		
		return ret;
		
	}
	
}
