package com.commercial.restController.article;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.entities.schema.article.repository.articleRepository;
import com.commercial.entities.schema.client.client;
import com.commercial.entities.schema.client.repository.clientRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.static_data.repository.uniteRepository;


@RestController

public class DanonTreatmentRestController {
	
	@Autowired
	articleRepository artRepo;
	
	@Autowired
	uniteRepository umRepo;
	
	@Autowired
	bon_livraisonRepository blRepo;
	
	@Autowired
	bon_livraison_detailRepository bldRepo;
	
	@Autowired
	clientRepository cltRepo;
	
	public DanonTreatmentRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/ajax_generate_facture_danon")
	public String ajax_generate_facture_danon(@RequestParam("bls") String bls) throws IOException{
		
		String ret = "";
		
    	String [] id_bls = bls.split("/");
    	
    	ArrayList<bon_livraison> list_bls = new ArrayList<bon_livraison>();
    	
    	double total_ht = 0;
    	double total_tva = 0;
    	double total_ttc = 0;
    	
    	for(int i = 0; i<id_bls.length;i++) {
    		
    		bon_livraison bl = blRepo.getOne( Long.parseLong(id_bls[i]) );
    		
    		list_bls.add( bl );
    		
    		total_ht = total_ht + bl.getMontant_ht();
    		
    		total_tva = total_tva + bl.getTva();
    		
    		total_ttc = total_ttc + bl.getMontant_ttc(); 
    		
    	}
    	
    	List< Map<String, Object> > mapped_detail_facture = new ArrayList< Map<String, Object> >();
    	
    	List<Object[]> detail_fact = bldRepo.get_bl_bls_danon_by_id(list_bls);
    	
    	
    	for(int i=0;i<detail_fact.size();i++) {
    		
    		Object[] m =  detail_fact.get(i);
    		
    		Map <String, Object> det = new HashMap <String, Object> ();
    		
    		System.out.println("-->"+m[0]);
    		det.put("montant_ht", m[0]);
    		System.out.println("-->"+m[1]);
    		det.put("montant_tva", m[1]);
    		System.out.println("-->"+m[2]);
    		det.put("prix_u_ht", m[2]);
    		System.out.println("-->"+m[3]);
    		det.put("quantite", m[3]);
    		System.out.println("-->"+m[4]);
    		det.put("tva", m[4]);
    		System.out.println("-->"+m[5]);
    		det.put("article", artRepo.getOne( ((BigInteger) m[5]).longValue() ) );
    		System.out.println("-->"+m[6]);
    		det.put("unite_mesure", umRepo.getOne( ((BigInteger) m[6]).longValue() ) );
    		
    		System.out.println("______________________");
    		
    		mapped_detail_facture.add(det);
    		
    	}
    	
    	/*
    	double total_ht = 0;
    	double total_tva = 0;
    	double total_ttc = 0;
    	
    	ArrayList< Map<String, Object> > detail_fact = new ArrayList< Map<String, Object> >();
    	
    	
    	for(int i = 0; i < list_bls.size(); i++) {
    		
    		total_ht = total_ht + list_bls.get(i).getMontant_ht();
    		
    		total_tva = total_tva + list_bls.get(i).getTva();
    		
    		total_ttc = total_ttc + list_bls.get(i).getMontant_ttc(); 
    		
    		List <bon_livraison_detail> bl_details = bldRepo.get_bl_detail( list_bls.get(i) );
    		
    		Map<String, Object> detail_fct = new HashMap<String, Object>();
    		
    		for(int j=0; j < bl_details.size(); j++) {
    			
    			bon_livraison_detail bld = bl_details.get(j);
    			
    			if(detail_fct.isEmpty()) {
    				
    				detail_fct.put("montant_ht", bld.getMontant_ht());
    				detail_fct.put("montant_tva", bld.getMontant_tva());
    				detail_fct.put("prix_u_ht", bld.getPrix_u_ht());
    				detail_fct.put("quantite", bld.getQuantite());
    				detail_fct.put("tva", bld.getTva());
    				detail_fct.put("article", bld.getArticle());
    				
    			}
    			else {
    				
    				detail_fct.put("montant_ht", (double) detail_fct.get("montant_ht") + bld.getMontant_ht());
    				detail_fct.put("montant_tva", (double) detail_fct.get("montant_tva") + bld.getMontant_tva());
    				//detail_fct.put("prix_u_ht", bld.getPrix_u_ht());
    				detail_fct.put("quantite",  (double) detail_fct.get("quantite") + bld.getQuantite());
    				//detail_fct.put("tva", bld.getTva());
    				//detail_fct.put("article", bld.getArticle());
    				
    			}
    			
    		}
    		
    	}
    	*/
		return ret;
		
	}
	
}
