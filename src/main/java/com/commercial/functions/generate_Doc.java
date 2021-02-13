package com.commercial.functions;

import java.io.File;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commercial.entities.schema.article.repository.MagasinRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_interne;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_employeeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_interneRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_interne_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;

/*
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;*/
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRXlsDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
//@Transactional

public class generate_Doc {
	
	@Autowired
	factureRepository factRepo;
	
	@Autowired
	facture_detailRepository fct_detRepo;
	
	@Autowired
	facture_avoirRepository fact_avRepo;
	
	@Autowired
	facture_avoir_detailRepository fct_av_detRepo;
	
	@Autowired
	bon_livraisonRepository blRepo;
	
	@Autowired
	bon_livraison_detailRepository bl_dRepo;
	
	@Autowired
	MagasinRepository magRepo;
	
	@Autowired
    DataSource localDataSource;
	
	@Autowired
	bon_livraison_employeeRepository bleRepo;
	
	@Autowired
	mode_paiementRepository mode_pRepo;
	
	@Autowired
	bon_transfert_interneRepository btiRepo;
	
	@Autowired
	bon_transfert_interne_detailRepository bti_dRepo;
	
	public generate_Doc() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String generate_BL(long id_bl, String numero, String matricule, String qr_code) {
		 
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
					
					try {
						
						Connection con  = localDataSource.getConnection();
					
						JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
						
						File dir = new File("D:\\Commercial\\Doc\\BL");
					    if (!dir.exists()) dir.mkdirs();
						
						JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BL\\BL"+i+".pdf");
						
						pdfs.add("D:\\Commercial\\Doc\\BL\\BL"+i+".pdf");
						
						con.close();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				CombinePdf.combine(pdfs, destination);
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return destination;
	
	}		
	
	//------------------------------------------------------------------------------
	
	public String generate_BLE(long id_bl, String qr_code) {
		 
		 String destination = "D:/Commercial/Doc/BL/BLE.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\Bon Livraison\\BLE.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				Map<String, Object> mp = new HashMap<String, Object>();
				
				bon_livraison_employee ble = bleRepo.getOne(id_bl);
					
				DecimalFormat df = new DecimalFormat("#,##0.00");
				
					mp.put("id_bon_livraison",ble.getId());
					mp.put("num",ble.getNumero());
					mp.put("qr_code", qr_code);
					mp.put("Matricule", ble.getMatricule_employee());
					mp.put("client", ble.getNom_employee()+" "+ble.getPrenom_employee());
					mp.put("montant_ht", df.format(ble.getMontant_ht()) );
					mp.put("montant_tva", df.format(ble.getTva()) );
					mp.put("montant_ttc", df.format(ble.getMontant_ttc()) );
					
					try {
						
						Connection con  = localDataSource.getConnection();
					
						JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
						
						File dir = new File("D:\\Commercial\\Doc\\BL");
						
					    if (!dir.exists()) dir.mkdirs();
						
						JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BL\\BLE.pdf");
						
						con.close();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return destination;
	
	}
	
	//------------------------------ PRINT FACT ------------------------------------
	
	public String generate_Fact(facture fact, String qr_code) {
		
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
				mp.put("date", fact.getDate());
				
				DecimalFormat df = new DecimalFormat("# ###,##0.00");
				
				mp.put("total_ht", df.format(fact.getMontant_ht()) );
				mp.put("total_tva", df.format(fact.getMontant_tva()) );
				mp.put("total_ttc", df.format(fact.getMontant_ttc()) );
				
				//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
				
				String m_ttc1 = df.format(fact.getMontant_ttc());
				
				System.out.println("---TTC1 -->"+m_ttc1);
				
				String m_ttc = m_ttc1.replaceAll(" ", "");
				
				System.out.println("---TTC -->"+m_ttc);
				
				String m [] = m_ttc.split(",");
				
				char  chkoupi [] = m[1].toCharArray();
				
				String virgule = "";
				
				if(chkoupi[0]=='0' && chkoupi[1]!='0') {
					
					String chk = ""+chkoupi[0], chk1 = ""+chkoupi[1]; 
					
					virgule = FrenchNumberToWords.convert(Double.parseDouble(chk))+" "+FrenchNumberToWords.convert(Double.parseDouble(chk1));
					
				}
				else {
					
					virgule = FrenchNumberToWords.convert(Double.parseDouble(m[1]));
					
				}
				
				mp.put("total_ttc_lettre", FrenchNumberToWords.convert(Double.parseDouble(m[0]))+" Virgule "+
				virgule+" Dinars Algérien");
				
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
					//------------------------------------------- hedi li tred facture Duplicata
					factRepo.save(fact);factRepo.flush();
					
				}
				else {
					
					//jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture_dup.jrxml"); //--- JRXML duplicata
					
					jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture.jrxml");
					
				}
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				try {
					
					Connection con = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\FCT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\FCT\\FCT.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/FCT/FCT.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_Fact_avoir(facture_avoir fact_av) {
		
	 JasperDesign jdesign; 
		try {
			
			Map<String, Object> mp = new HashMap<String, Object>();
			
			mp.put("num_fact_av", fact_av.getNumero());
			mp.put("id_fact_av", fact_av.getId());
			mp.put("num_fact", fact_av.getFacture().getNumero());
			mp.put("code", fact_av.getRegistre_commerce().getCode());
			mp.put("client_category", fact_av.getRegistre_commerce().getNom()+" "+fact_av.getRegistre_commerce().getPrenom()+" "+
										fact_av.getRegistre_commerce().getCategory().getNom_category());
			mp.put("adresse", fact_av.getRegistre_commerce().getAdresse());
			mp.put("date", fact_av.getDate());
			mp.put("user_matricule", fact_av.getUsers().getMatricule());
			
			DecimalFormat df = new DecimalFormat("# ###,##0.00");
			
			mp.put("montant_ht", df.format(fact_av.getMontant_ht()) );
			mp.put("montant_tva", df.format(fact_av.getTva()) );
			mp.put("montant_ttc", df.format(fact_av.getMontant_ttc()) );
			
			//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
			
			String m_ttc1 = df.format(fact_av.getMontant_ttc());
			
			System.out.println("---TTC1 -->"+m_ttc1);
			
			String m_ttc = m_ttc1.replaceAll(" ", "");
			
			System.out.println("---TTC -->"+m_ttc);
			
			String m [] = m_ttc.split(",");
			
			char  chkoupi [] = m[1].toCharArray();
			
			String virgule = "";
			
			if(chkoupi[0]=='0' && chkoupi[1]!='0') {
				
				String chk = ""+chkoupi[0], chk1 = ""+chkoupi[1]; 
				
				virgule = FrenchNumberToWords.convert(Double.parseDouble(chk))+" "+FrenchNumberToWords.convert(Double.parseDouble(chk1));
				
			}
			else {
				
				virgule = FrenchNumberToWords.convert(Double.parseDouble(m[1]));
				
			}
			
			mp.put("montant_ttc_lettre", FrenchNumberToWords.convert(Double.parseDouble(m[0]))+" Virgule "+
					virgule+" Dinars Algérien");
			
			//----------------------- calcule Cumule TVA --------------------------
			
			String cumule_tva = "TVA \t Montant \n";
			
			List <facture_avoir_detail> list_fct_det = fct_av_detRepo.get_facture_avoir_detail(fact_av);
			
			Map<String, Double> cuml = new HashMap<String, Double>();
			
			for(int i=0;i<list_fct_det.size();i++) {
				
				facture_avoir_detail fct_de = list_fct_det.get(i);
				
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
				
			jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture_avoir.jrxml");
			
			JasperReport jreport = JasperCompileManager.compileReport(jdesign);
			
			try {
				
				Connection con = localDataSource.getConnection();
			
				JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
				
				File dir = new File("D:\\Commercial\\Doc\\FCT");
				
			    if (!dir.exists()) dir.mkdirs();
				
				JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\FCT\\FCTAV.pdf");
				
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "D:/Commercial/Doc/FCT/FCTAV.pdf";

}	

//------------------------------------------------------------------------------
	
	public String generate_bordereau_pay(String start, String end, long [] mode_pay) {
		 
		 String destination = "D:/Commercial/Doc/PAI/BORD.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\paiement\\bordereau_remise_paiements.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				Map<String, Object> mp = new HashMap<String, Object>();
				
				System.out.println("start->"+start+" end->"+end);
				
				System.out.println("mode p size ->"+mode_pay.length);
				
				String req_p = " (";
				
				String mode_paye = "";
				
				for(int i=0;i<mode_pay.length;i++) {
					
					if(i!=(mode_pay.length-1)) {
						
						req_p += "p.mode_paiement = '"+mode_pay[i]+"' OR ";
						
						mode_paye += mode_pRepo.getOne(mode_pay[i]).getDesignation()+", ";
						
					}
					else {
						
						req_p += "p.mode_paiement = '"+mode_pay[i]+"'";
						
						mode_paye += mode_pRepo.getOne(mode_pay[i]).getDesignation();
						
					}
					
				}
				
				req_p += ")";
				
				System.out.println("req -> "+req_p);
				
				mp.put("start",start);
				mp.put("end",end);
				mp.put("types_paiements", mode_paye);
				mp.put("req_pay", req_p);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\PAI");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\PAI\\BORD.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return destination;
	
	}
	
	//------------------------------------------------------------------------------
	
	public String generate_BonTransfertInterne(long id_bti, String qr_code) {
		 
		 String destination = "D:/Commercial/Doc/BT/BTI.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\Bon Livraison\\BTI.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				Map<String, Object> mp = new HashMap<String, Object>();
				
				bon_transfert_interne bti = btiRepo.getOne(id_bti);
				
					mp.put("id_bti",bti.getId());
					mp.put("num",bti.getNumero());
					mp.put("client", bti.getDestination());
					
					try {
						
						Connection con  = localDataSource.getConnection();
					
						JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
						
						File dir = new File("D:\\Commercial\\Doc\\BL");
						
					    if (!dir.exists()) dir.mkdirs();
						
						JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BT\\BTI.pdf");
						
						con.close();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return destination;
	
	}
	
	/*
	 private static JRXlsDataSource getDataSource_bl() throws JRException
	  {
	    JRXlsDataSource ds;
	    try
	    {
	  
	      String[] columnNames1 = new String[]{"code","name","quantite","nom_unite_mesure","lib","pesage","unite_mesage"};
	      int[] columnIndexes1 = new int[]{ 0,1,2,3,4,5,6};
	      
	      //ds = new JRXlsDataSource();
	      ds = new JRXlsDataSource("D:\\Commercial\\report\\excel_transfert\\bl.xls");
	      ds.setColumnNames(columnNames1);
	      ds.getNumberFormat();

	    }
	    catch (IOException e)
	    {
	      throw new JRException(e);
	    }
	    return ds;

	    
	  

	  }
	*/
}
