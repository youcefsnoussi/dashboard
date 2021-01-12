package com.commercial.functions;

import java.io.File;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
@Transactional

public class generate_Doc {
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fct_detRepo;
	
	@Autowired
	bon_livraisonRepository blRepo;
	
	@Autowired
	bon_livraison_detailRepository bl_dRepo;
	
	public generate_Doc() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String generate_BL(long id_bl, String numero, String matricule, String qr_code, Connection con) {
		 
		 String destination = "D:/Commercial/Doc/BL/BL.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\Bon Livraison\\BL.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				Map<String, Object> mp = new HashMap<String, Object>();
				
				bon_livraison bl = blRepo.getOne(id_bl);
				
				List <bon_livraison_detail> list_bld = bl_dRepo.get_bl_detail(bl);
					
				List <Long> magasins = new ArrayList <Long>();
				
				for(int i=0; i<list_bld.size(); i++) {
					
					if(!magasins.contains(list_bld.get(i).getMagasin().getId())) {
						
						magasins.add(list_bld.get(i).getMagasin().getId());
						
					}
					
				}
				
				ArrayList<String> pdfs = new ArrayList <String>();
				
				for(int i=0;i<magasins.size();i++) {
					
					mp.put("id_bon_livraison",id_bl);
					mp.put("num",numero);
					mp.put("qr_code", qr_code);
					mp.put("Matricule", matricule);
					mp.put("client", bl.getRegistre_commerce().getNom()+" "+
							bl.getRegistre_commerce().getPrenom());
					
					mp.put("magasin", magasins.get(i));
					
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\BL");
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BL\\BL"+i+".pdf");
					
					pdfs.add("D:\\Commercial\\Doc\\BL\\BL"+i+".pdf");
					
				}
				
				CombinePdf.combine(pdfs, destination);
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return destination;
	
	}		
	
	//------------------------------ PRINT FACT ------------------------------------
	
	public String generate_Fact(facture fact, String qr_code, Connection con) {
		
		 JasperDesign jdesign; 
			try {
				
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("num_facture", fact.getNumero());
				mp.put("id_facture", fact.getId());
				mp.put("num_client", fact.getRegistre_commerce().getCode());
				mp.put("matricule", fact.getMatricule_camion());
				mp.put("nom_client", fact.getRegistre_commerce().getNom()+" "+fact.getRegistre_commerce().getPrenom());
				mp.put("adresse", fact.getRegistre_commerce().getAdresse());
				mp.put("rc", fact.getRegistre_commerce().getNumero_rc());
				mp.put("nif", fact.getRegistre_commerce().getNumero_nif());
				mp.put("nis", fact.getRegistre_commerce().getNumero_art());
				mp.put("user", fact.getUsers().getMatricule());
				mp.put("cat_rc", fact.getRegistre_commerce().getCategory().getNom_category());
				
				DecimalFormat df = new DecimalFormat("# ###,##0.00");
				
				mp.put("total_ht", df.format(fact.getMontant_ht()) );
				mp.put("total_tva", df.format(fact.getMontant_tva()) );
				mp.put("total_ttc", df.format(fact.getMontant_ttc()) );
				mp.put("total_ttc_lettre", FrenchNumberToWords.convert(fact.getMontant_ttc())+" Dinars Algérien");
				
				//----------------------- calcule Cumule TVA --------------------------
				
				String cumule_tva = "";
				
				List <facture_detail> list_fct_det = fct_detRepo.get_facture_detail(fact);
				
				Map<String, Double> cuml = new HashMap<String, Double>();
				
				for(int i=0;i<list_fct_det.size();i++) {
					
					facture_detail fct_de = list_fct_det.get(i);
					
					if(cuml.containsKey(""+fct_de.getTva())) {
						
						double val = cuml.get(""+fct_de.getTva());
						
						cuml.put(""+fct_de.getTva(), val + ( fct_de.getMontant_ht() * (fct_de.getTva()/100) ) );
						
					}
					else {
						
						cuml.putIfAbsent(""+fct_de.getTva(), fct_de.getMontant_ht() * (fct_de.getTva()/100));
						
					}
					
				}
				
				for (Map.Entry<String, Double> entry : cuml.entrySet()) {
				    String key = entry.getKey();
				    Double value = entry.getValue();
				    
				    cumule_tva = cumule_tva + key + "% \t"+ value+"\n";
				    
				}
				
				//----------------------- +++++++++++++++++++ --------------------------
				
				mp.put("cumule_tva", cumule_tva);
				
				if(fact.getPrinted()==false) {
					
					jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture.jrxml");
					
					fact.setPrinted(true);
					
					factRepo.save(fact);factRepo.flush();
					
				}
				else {
					
					jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture_dup.jrxml");
					
				}
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
				
				File dir = new File("D:\\Commercial\\Doc\\FCT");
				
			    if (!dir.exists()) dir.mkdirs();
				
				JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\FCT\\FCT.pdf");
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/FCT/FCT.pdf";
	
	}	
	
}
