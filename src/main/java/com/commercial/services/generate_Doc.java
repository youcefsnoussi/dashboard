package com.commercial.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.article.article;
import com.commercial.entities.schema.article.category_produit;
import com.commercial.entities.schema.article.repository.MagasinRepository;
import com.commercial.entities.schema.client.registre_commerce;
import com.commercial.entities.schema.client.repository.category_clientRepository;
import com.commercial.entities.schema.dynamic_data.mouvement;
import com.commercial.entities.schema.dynamic_data.repository.mouvementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_employee;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_livraison_facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert;
import com.commercial.entities.schema.profoma_cmd_bl_fact.bon_transfert_interne;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_avoir_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_ristourne;
import com.commercial.entities.schema.profoma_cmd_bl_fact.facture_ristourne_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.paiement;
import com.commercial.entities.schema.profoma_cmd_bl_fact.proforma;
import com.commercial.entities.schema.profoma_cmd_bl_fact.proforma_detail;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraisonRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_employeeRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_livraison_facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_interneRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.bon_transfert_interne_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.factureRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoirRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_avoir_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_ristourne_detailRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.paiementRepository;
import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.proforma_detailRepository;
import com.commercial.entities.schema.static_data.repository.information_entrepriseRepository;
import com.commercial.entities.schema.static_data.repository.mode_paiementRepository;
import com.commercial.functions.CombinePdf;
import com.commercial.functions.FrenchNumberToWords;
import com.commercial.functions.generateQRcode;

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
	
	@Autowired
	mouvementRepository mvmRepo;
	
	@Autowired
	facture_ristourne_detailRepository fct_ris_dRepo;
	
	@Autowired
	paiementRepository paiRepo;
	
	@Autowired
	information_entrepriseRepository infoeRepo;
	
	@Autowired
	bon_livraison_factureRepository blfRepo;
	
	@Autowired
	bon_livraison_facture_detailRepository blfdRepo;
	
	@Autowired
	category_clientRepository cat_cRepo;
	
	@Autowired
	proforma_detailRepository prof_detRepo;
	
	public generate_Doc() {
		// TODO Auto-generated constructor stub
	}
	
	DecimalFormat df = new DecimalFormat("#,##0.00",  new DecimalFormatSymbols(Locale.FRENCH));
	
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
					
					if(!magasins.contains(list_bld.get(i).getMagasin().getId()) 
							&& !list_bld.get(i).getMagasin().getName().equals("Dépot Palette")) {
						
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
					
					mp.put("date",bl.getDate());
					
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
				
					mp.put("id_bon_livraison",ble.getId());
					mp.put("num",ble.getNumero());
					mp.put("qr_code", qr_code);
					mp.put("Matricule", ble.getMatricule_employee());
					mp.put("client", ble.getNom_employee()+" "+ble.getPrenom_employee());
					mp.put("montant_ht", df.format(ble.getMontant_ht()) );
					mp.put("montant_tva", df.format(ble.getMontant_tva()) );
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
				mp.put("nom_client", fact.getRegistre_commerce().getNom()+" "+fact.getRegistre_commerce().getPrenom()
						);
				mp.put("adresse", fact.getRegistre_commerce().getAdresse());
				mp.put("rc", fact.getRegistre_commerce().getNumero_rc());
				mp.put("nif", fact.getRegistre_commerce().getNumero_nif());
				mp.put("nis", fact.getRegistre_commerce().getNumero_art());
				mp.put("user", fact.getUsers().getMatricule());
				mp.put("cat_rc", fact.getRegistre_commerce().getCategory().getNom_category());
				mp.put("date", fact.getDate());
				mp.put("ArticleLoieExoneration", fact.getRegistre_commerce().getArticleLoieExoneration());
				
				//mp.put("total_remise", df.format(fact.getBon_livraison().getCommande().getValeur_reduction()) );
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
				
				//---------------- reglement 
				
				String reglem = "";
				
				if(fact.getRegistre_commerce().getType_reglement().getDesignation().equals("A Terme")) {
					
					reglem = "A Terme";
					
				}
				else {
					
					List<paiement> lst_pay = paiRepo.get_payments_date_rc(fact.getDate(), fact.getRegistre_commerce());
					
					if(lst_pay.size()==0) {
						
						reglem = "A Compte";
						
					}
					else {
						
						paiement pay = lst_pay.get(lst_pay.size()-1);
						
						String info_supp = "";
						
						if(pay.getInfo_supp_banque()!=null) {info_supp = pay.getInfo_supp_banque();}
						
						reglem = pay.getBanque().getNom_banque()+" "+info_supp+" "+pay.getNumero_piece()+" "+df.format(pay.getMontant())+" DA";
						
					}
					
				}
				
				//mp.put("mode_reg", fact.getMode_paiement().getDesignation());
				
				String mode_pay = "";
				
				List<paiement> lst_pay = paiRepo.get_paiements_not_canceled_by_rc(fact.getRegistre_commerce());
				
				if(lst_pay.size()==0) {
					
					mode_pay = fact.getRegistre_commerce().getMode_paiement().getDesignation();
					
				}
				else {
					
					mode_pay = lst_pay.get(0).getMode_paiement().getDesignation();
					
				}
				
				mp.put("mode_reg", mode_pay);
				
				mp.put("reglem", reglem);
				
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
				    
				    cumule_tva = cumule_tva + key + "% \t"+ df.format(value)+"\n";
				    
				}
				
				//----------------------- +++++++++++++++++++ --------------------------
				
				mp.put("cumule_tva", cumule_tva);
				
				if(fact.getPrinted()==false) {
					
					fact.setPrinted(true);
					//------------------------------------------- hedi li tred facture Duplicata
					factRepo.save(fact);factRepo.flush();
					
				}
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture.jrxml");
				
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
	
	public String generate_Fact_A4(facture fact, String qr_code) {
		
		 JasperDesign jdesign; 
			try {
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("num_fact", fact.getNumero());
				mp.put("id_fact", fact.getId());
				mp.put("num_client", fact.getRegistre_commerce().getCode());
				mp.put("matricule", fact.getMatricule_camion());
				mp.put("nom_client",fact.getRegistre_commerce().getNom()+" "+fact.getRegistre_commerce().getPrenom()+" "+
						fact.getRegistre_commerce().getCategory().getNom_category());
				mp.put("adresse_client", fact.getRegistre_commerce().getAdresse());
				mp.put("rc", fact.getRegistre_commerce().getNumero_rc());
				mp.put("nif", fact.getRegistre_commerce().getNumero_nif());
				mp.put("nis", fact.getRegistre_commerce().getNumero_art());
				mp.put("user", (fact.getUsers()!=null) ? fact.getUsers().getMatricule() : "");
				mp.put("cat_rc", fact.getRegistre_commerce().getCategory().getNom_category());
				mp.put("date", fact.getDate());
				mp.put("ArticleLoieExoneration", fact.getRegistre_commerce().getArticleLoieExoneration());
				mp.put("time", fact.getTime());
				//mp.put("user", fact.getUsers().getMatricule());
				
				//mp.put("total_remise", df.format(fact.getBon_livraison().getCommande().getValeur_reduction()) );
				mp.put("total_ht", df.format(fact.getMontant_ht()) );
				mp.put("total_tva", df.format(fact.getMontant_tva()) );
				mp.put("total_ttc", df.format(fact.getMontant_ttc()) );
				mp.put("timbre", df.format(0) );
				mp.put("total_ht_net", df.format(fact.getMontant_ht_net()) );
				mp.put("total_remise", df.format(fact.getMontant_remise()) );
				
				mp.put("qr_path", qr_code );
				
				//---------------------- Entreprise INFO -------------------------------
				
				mp.put("Nom_entreprise", infoeRepo.getOne((long)1 ).getNom_entreprise() );
				mp.put("capitale", df.format(infoeRepo.getOne((long)1 ).getCapitale()) );
				mp.put("adresse", infoeRepo.getOne((long)1 ).getAdresse_facturation() );
				mp.put("tel", infoeRepo.getOne((long)1 ).getTelephone() );
				mp.put("fax", infoeRepo.getOne((long)1 ).getFax() );
				mp.put("num_rc", infoeRepo.getOne((long)1 ).getNum_rc() );
				mp.put("num_art", infoeRepo.getOne((long)1 ).getNum_art() );
				mp.put("num_nif", infoeRepo.getOne((long)1 ).getNum_nif() );
				mp.put("num_nis", infoeRepo.getOne((long)1 ).getNum_nis() );
				mp.put("BankAccounts", infoeRepo.getOne((long)1 ).getBankAccounts() );
				
				mp.put("logo_path", infoeRepo.getOne((long)1 ).getChemain_logo() );
				
				//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
				
				String m_ttc1 = df.format(fact.getMontant_ttc());
				
				//System.out.println("---TTC1 -->"+m_ttc1);
				
				String m_ttc = m_ttc1.replaceAll(" ", "");
				
				//System.out.println("---TTC -->"+m_ttc);
				
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
				
				//---------------- reglement 
				
				String reglem = "";
				
				if(fact.getRegistre_commerce().getType_reglement().getDesignation().equals("A Terme")) {
					
					reglem = "A Terme";
					
				}
				else {
					
					//List<paiement> lst_pay = paiRepo.get_payments_date_rc(fact.getDate(), fact.getRegistre_commerce());
					
					List<paiement> lst_pay = paiRepo.get_paiements_not_canceled_by_rc( fact.getRegistre_commerce());
					
					System.out.println("---------------------->"+lst_pay.size());
					
					if(lst_pay.size()==0) {
						
						reglem = "A Compte";
						
					}
					else {
						
						//paiement pay = lst_pay.get(lst_pay.size()-1);
						
						paiement pay = lst_pay.get(0);
						
						String info_supp = "";
						
						if(pay.getInfo_supp_banque()!=null) {info_supp = pay.getInfo_supp_banque();}
						
						reglem += " "+pay.getBanque().getNom_banque()+" "+info_supp+" "+pay.getNumero_piece()+" "+df.format(pay.getMontant())+" DA";
						
					}
					
				}
				
				//mp.put("mode_reg", fact.getMode_paiement().getDesignation());
				
				String mode_pay = "";
				
				List<paiement> lst_pay = paiRepo.get_paiements_not_canceled_by_rc(fact.getRegistre_commerce());
				
				if(lst_pay.size()==0) {
					
					mode_pay = fact.getRegistre_commerce().getMode_paiement().getDesignation();
					
				}
				else {
					
					mode_pay = lst_pay.get(0).getMode_paiement().getDesignation();
					
				}
				
				mp.put("mode_reg", mode_pay);
				
				mp.put("reglem", reglem);
				
				//--------------------------------------------
				
				String cumule_tva = "";
				
				List <facture_detail> list_fact_det = fct_detRepo.get_facture_detail(fact);
				
				Map<String, Double> cuml = new HashMap<String, Double>();
				
				for(int i=0;i<list_fact_det.size();i++) {
					
					facture_detail det_fact = list_fact_det.get(i);
					
					if(cuml.containsKey(""+det_fact.getTva())) {
						
						double val = cuml.get(""+det_fact.getTva());
						
						cuml.put(""+det_fact.getTva(), val + det_fact.getMontant_tva() );
						
					}
					else {
						
						cuml.putIfAbsent(""+det_fact.getTva(), det_fact.getMontant_tva() );
						
					}
					
				}
				
				for (Map.Entry<String, Double> entry : cuml.entrySet()) {
				    String key = entry.getKey();
				    Double value = entry.getValue();
				    
				    cumule_tva = cumule_tva + key + "% \t"+ df.format(value)+"\n";
				    
				}
				
				//----------------------- +++++++++++++++++++ --------------------------
				
				mp.put("cumule_tva", cumule_tva);
				
				if(fact.getPrinted()==false) {
					
					fact.setPrinted(true);
					//------------------------------------------- hedi li tred facture Duplicata
					factRepo.save(fact);factRepo.flush();
					
				}
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture_A4.jrxml");
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				try {
					
					Connection con = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\FCT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\FCT\\FCT_A4.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/FCT/FCT_A4.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_Fact_A4_blfs(facture fact, String qr_code) {
		 
		 ArrayList<String> allPdfs = new ArrayList<String>();
		 
		 String path_fct = generate_Fact_A4(fact, qr_code);
		 
		 allPdfs.add(path_fct.replaceAll("/", "\\/"));
		 
		 JasperDesign jdesign; 
			try {
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				//---------------------- Entreprise INFO -------------------------------------
				
				mp.put("Nom_entreprise", infoeRepo.getOne((long)1 ).getNom_entreprise() );
				mp.put("capitale", df.format(infoeRepo.getOne((long)1 ).getCapitale()) );
				mp.put("adresse", infoeRepo.getOne((long)1 ).getAdresse_facturation() );
				mp.put("tel", infoeRepo.getOne((long)1 ).getTelephone() );
				mp.put("num_rc", infoeRepo.getOne((long)1 ).getNum_rc() );
				mp.put("num_art", infoeRepo.getOne((long)1 ).getNum_art() );
				mp.put("num_nif", infoeRepo.getOne((long)1 ).getNum_nif() );
				
				mp.put("logo_path", infoeRepo.getOne((long)1 ).getChemain_logo() );
				
				//----------------------------------------------------------------------------
				
				mp.put("id_fact", fact.getId());
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture_A4_bls.jrxml");
					
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				try {
					
					Connection con = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\FCT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\FCT\\FCT_A4_bls.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			allPdfs.add("D:\\Commercial\\Doc\\FCT\\FCT_A4_bls.pdf");
			
			CombinePdf.combine(allPdfs, "D:\\Commercial\\Doc\\FCT\\Combine_FCT_A4_BLFS.pdf");
			
			return "D:/Commercial/Doc/FCT/Combine_FCT_A4_BLFS.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_Proforma(proforma prof, String qr_code) {
		
		 JasperDesign jdesign; 
			try {
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("num_fact", prof.getNumero());
				mp.put("id_proforma", prof.getId());
				mp.put("num_client", prof.getRegistre_commerce().getCode());
				mp.put("nom_client",prof.getRegistre_commerce().getNom()+" "+prof.getRegistre_commerce().getPrenom()+" "+
						prof.getRegistre_commerce().getCategory().getNom_category());
				mp.put("adresse_client", prof.getRegistre_commerce().getAdresse());
				mp.put("rc", prof.getRegistre_commerce().getNumero_rc());
				mp.put("nif", prof.getRegistre_commerce().getNumero_nif());
				mp.put("nis", prof.getRegistre_commerce().getNumero_art());
				mp.put("user", prof.getUsers().getMatricule());
				mp.put("cat_rc", prof.getRegistre_commerce().getCategory().getNom_category());
				mp.put("date", prof.getDate());
				mp.put("time", prof.getTime());
				mp.put("user", prof.getUsers().getMatricule());
				
				//mp.put("total_remise", df.format(fact.getBon_livraison().getCommande().getValeur_reduction()) );
				mp.put("total_ht", df.format(prof.getTotal_ht()) );
				mp.put("total_tva", df.format(prof.getTotal_tva()) );
				mp.put("total_ttc", df.format(prof.getTotal_ttc()) );
				mp.put("timbre", df.format(0) );
				
				mp.put("qr_path", qr_code );
				
				//---------------------- Entreprise INFO -------------------------------
				
				mp.put("Nom_entreprise", infoeRepo.getOne((long)1 ).getNom_entreprise() );
				mp.put("capitale", df.format(infoeRepo.getOne((long)1 ).getCapitale()) );
				mp.put("adresse", infoeRepo.getOne((long)1 ).getAdresse_facturation() );
				mp.put("tel", infoeRepo.getOne((long)1 ).getTelephone() );
				mp.put("fax", infoeRepo.getOne((long)1 ).getFax() );
				mp.put("num_rc", infoeRepo.getOne((long)1 ).getNum_rc() );
				mp.put("num_art", infoeRepo.getOne((long)1 ).getNum_art() );
				mp.put("num_nif", infoeRepo.getOne((long)1 ).getNum_nif() );
				mp.put("num_nis", infoeRepo.getOne((long)1 ).getNum_nis() );
				mp.put("BankAccounts", infoeRepo.getOne((long)1 ).getBankAccounts() );
				
				mp.put("logo_path", infoeRepo.getOne((long)1 ).getChemain_logo() );
				
				//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
				
				String m_ttc1 = df.format(prof.getTotal_ttc());
				
				//System.out.println("---TTC1 -->"+m_ttc1);
				
				String m_ttc = m_ttc1.replaceAll(" ", "");
				
				//System.out.println("---TTC -->"+m_ttc);
				
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
				
				//--------------------------------------------
				
				String cumule_tva = "";
				
				List <proforma_detail> list_prof_det = prof_detRepo.get_proforma_detail(prof);
				
				Map<String, Double> cuml = new HashMap<String, Double>();
				
				for(int i=0;i<list_prof_det.size();i++) {
					
					proforma_detail det_fact = list_prof_det.get(i);
					
					if(cuml.containsKey(""+det_fact.getTva())) {
						
						double val = cuml.get(""+det_fact.getTva());
						
						cuml.put(""+det_fact.getTva(), val + ( det_fact.getMontant_ht() * (det_fact.getTva()/100) ) );
						
					}
					else {
						
						cuml.putIfAbsent(""+det_fact.getTva(), det_fact.getMontant_ht() * (det_fact.getTva()/100));
						
					}
					
				}
				
				for (Map.Entry<String, Double> entry : cuml.entrySet()) {
				    String key = entry.getKey();
				    Double value = entry.getValue();
				    
				    cumule_tva = cumule_tva + key + "% \t"+ df.format(value)+"\n";
				    
				}
				
				//----------------------- +++++++++++++++++++ --------------------------
				
				mp.put("cumule_tva", cumule_tva);
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Proforma.jrxml");
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				try {
					
					Connection con = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\FCT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\FCT\\PROF.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/FCT/PROF.pdf";
	
	}
	
	//------------------------------------------------------------------------------
	
	public String generate_Fact_avoir(facture_avoir fact_av, String facts) {
		
	 JasperDesign jdesign; 
		try {
			
			Map<String, Object> mp = new HashMap<String, Object>();
			
			//---------------------- Entreprise INFO -------------------------------
			
			mp.put("Nom_entreprise", infoeRepo.getOne((long)1 ).getNom_entreprise() );
			mp.put("capitale", df.format(infoeRepo.getOne((long)1 ).getCapitale()) );
			mp.put("adresse", infoeRepo.getOne((long)1 ).getAdresse_facturation() );
			mp.put("tel", infoeRepo.getOne((long)1 ).getTelephone() );
			mp.put("fax", infoeRepo.getOne((long)1 ).getFax() );
			mp.put("num_rc", infoeRepo.getOne((long)1 ).getNum_rc() );
			mp.put("num_art", infoeRepo.getOne((long)1 ).getNum_art() );
			mp.put("num_nif", infoeRepo.getOne((long)1 ).getNum_nif() );
			mp.put("num_nis", infoeRepo.getOne((long)1 ).getNum_nis() );
			mp.put("BankAccounts", infoeRepo.getOne((long)1 ).getBankAccounts() );
			
			mp.put("logo_path", infoeRepo.getOne((long)1 ).getChemain_logo() );
			
			//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
			
			mp.put("num_fact_av", fact_av.getNumero());
			mp.put("id_fact_av", fact_av.getId());
			mp.put("num_client", fact_av.getRegistre_commerce().getCode());
			mp.put("nom_client",fact_av.getRegistre_commerce().getNom()+" "+fact_av.getRegistre_commerce().getPrenom()+" "+
					fact_av.getRegistre_commerce().getCategory().getNom_category());
			mp.put("adresse_client", fact_av.getRegistre_commerce().getAdresse());
			mp.put("rc", fact_av.getRegistre_commerce().getNumero_rc());
			mp.put("nif", fact_av.getRegistre_commerce().getNumero_nif());
			mp.put("nis", fact_av.getRegistre_commerce().getNumero_art());
			mp.put("user", fact_av.getUsers().getMatricule());
			mp.put("cat_rc", fact_av.getRegistre_commerce().getCategory().getNom_category());
			mp.put("date", fact_av.getDate());
			//mp.put("ArticleLoieExoneration", fact_av.getRegistre_commerce().getArticleLoieExoneration());
			mp.put("time", fact_av.getTime());
			mp.put("user", fact_av.getUsers().getMatricule());
			
			//mp.put("total_remise", df.format(fact.getBon_livraison().getCommande().getValeur_reduction()) );
			mp.put("total_ht", df.format(fact_av.getMontant_ht()) );
			mp.put("total_tva", df.format(fact_av.getTva()) );
			mp.put("total_ttc", df.format(fact_av.getMontant_ttc()) );
			mp.put("timbre", df.format(0) );
			mp.put("total_ht_net", df.format(fact_av.getMontant_ht_net()) );
			mp.put("total_remise", df.format(fact_av.getMontant_remise()) );
			
			mp.put("num_fact", facts);
			
			//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
			
			String m_ttc1 = df.format(fact_av.getMontant_ttc());
			
			String m_ttc = m_ttc1.replaceAll(" ", "");
			
			String m [] = m_ttc.split(",");
			
			char  chkoupi [] = m[1].toCharArray();
			
			String virgule = "";
			
			if(chkoupi[0]=='0' && chkoupi[1]!='0') {
				
				String chk = ""+chkoupi[0], chk1 = ""+chkoupi[1]; 
				
				virgule = FrenchNumberToWords.convert(Double.parseDouble(chk))+" "+
				FrenchNumberToWords.convert(Double.parseDouble(chk1));
				
			}
			else {
				
				virgule = FrenchNumberToWords.convert(Double.parseDouble(m[1]));
				
			}
			
			mp.put("total_ttc_lettre", FrenchNumberToWords.convert(Double.parseDouble(m[0]))+" Virgule "+
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
			    
			    cumule_tva = cumule_tva + key + "% \t"+ df.format(value)+"\n";
			    
			}
			
			//----------------------- +++++++++++++++++++ --------------------------
			
			mp.put("cumule_tva", cumule_tva);
				
			jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture_avoir_A4.jrxml");
			
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
	
	public String generate_Fact_ristourne(facture_ristourne fact_ris) {
		
	 JasperDesign jdesign; 
		try {
			
			Map<String, Object> mp = new HashMap<String, Object>();
			
			mp.put("num_fact_ris", fact_ris.getNumero());
			mp.put("id_fact_ris", fact_ris.getId());
			mp.put("code", fact_ris.getRegistre_commerce().getCode());
			mp.put("client_category", fact_ris.getRegistre_commerce().getNom()+" "+fact_ris.getRegistre_commerce().getPrenom()+" "+
					fact_ris.getRegistre_commerce().getCategory().getNom_category());
			mp.put("adresse", fact_ris.getRegistre_commerce().getAdresse());
			mp.put("date",fact_ris.getDate());
			mp.put("user_matricule", fact_ris.getUsers().getMatricule());
			mp.put("rc", fact_ris.getRegistre_commerce().getNumero_rc());
			mp.put("nif", fact_ris.getRegistre_commerce().getNumero_nif());
			mp.put("art", fact_ris.getRegistre_commerce().getNumero_art());
			
			mp.put("montant_ht", df.format(fact_ris.getMontant_ht()) );
			mp.put("montant_tva", df.format(fact_ris.getTva()) );
			mp.put("montant_ttc", df.format(fact_ris.getMontant_ttc()) );
			
			//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
			
			String m_ttc1 = df.format(fact_ris.getMontant_ttc());
			
			String m_ttc = m_ttc1.replaceAll(" ", "");
			
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
			
			List <facture_ristourne_detail> list_fct_det = fct_ris_dRepo.get_facture_ristourne_detail(fact_ris);
			
			Map<String, Double> cuml = new HashMap<String, Double>();
			
			for(int i=0;i<list_fct_det.size();i++) {
				
				facture_ristourne_detail fct_ris_d = list_fct_det.get(i);
				
				if(cuml.containsKey(""+fct_ris_d.getTva())) {
					
					double val = cuml.get(""+fct_ris_d.getTva());
					
					cuml.put(""+fct_ris_d.getTva(), val + ( fct_ris_d.getMontant_ht() * (fct_ris_d.getTva()/100) ) );
					
				}
				else {
					
					cuml.putIfAbsent(""+fct_ris_d.getTva(), fct_ris_d.getMontant_ht() * (fct_ris_d.getTva()/100));
					
				}
				
			}
			
			for (Map.Entry<String, Double> entry : cuml.entrySet()) {
			    String key = entry.getKey();
			    Double value = entry.getValue();
			    
			    cumule_tva = cumule_tva + key + "% \t"+ df.format(value)+"\n";
			    
			}
			
			//----------------------- +++++++++++++++++++ --------------------------
			
			mp.put("cumule_tva", cumule_tva);
			
			System.out.println("id_ris -> "+fact_ris.getId());
			
			
			
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\facture\\Facture_ristourne.jrxml");
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Connection con = localDataSource.getConnection();
			
				JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
				
				System.out.println(jprint.getName());
				
				File dir = new File("D:\\Commercial\\Doc\\FCT");
				
			    if (!dir.exists()) dir.mkdirs();
				
				JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\FCT\\FCTRIS.pdf");
				
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "D:/Commercial/Doc/FCT/FCTRIS.pdf";

	}	

	//--------------------------------- STATISTIC -------------------------------------------------------
	
	public String generate_bordereau_pay(String start, String end, String unite, long [] mode_pay) {
		 
		 String destination = "D:/Commercial/Doc/PAI/BORD.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\paiement\\bordereau_remise_paiements.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				Map<String, Object> mp = new HashMap<String, Object>();
				
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
				
				mp.put("start",start);
				mp.put("end",end);
				mp.put("types_paiements", mode_paye);
				mp.put("req_pay", req_p);
				mp.put("unite", unite);
				
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
	
	//--------------------------------- Impaye -------------------------------------------------------
	
		public String generate_impaye() {
			 
			 String destination = "D:/Commercial/Doc/PAI/IMP.pdf";
			 
			 JasperDesign jdesign; 
				try {
					
					jdesign = JRXmlLoader.load("D:\\Commercial\\report\\paiement\\impaye.jrxml");
					JasperReport jreport = JasperCompileManager.compileReport(jdesign);
					Map<String, Object> mp = new HashMap<String, Object>();
					
					String req_p = " (";
					
					String mode_paye = "";
					
					mp.put("types_paiements", mode_paye);
					mp.put("req_pay", req_p);
					
					try {
						
						Connection con  = localDataSource.getConnection();
					
						JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
						
						File dir = new File("D:\\Commercial\\Doc\\PAI");
						
					    if (!dir.exists()) dir.mkdirs();
						
						JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\PAI\\IMP.pdf");
						
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
	
	//------------------------------------------------------------------------------
	
	public String generate_vente_client(String start, String end, registre_commerce rc) {
		 
		 String destination = "D:/Commercial/Doc/STAT/VC.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\vente_client.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				String pren = rc.getPrenom();
				
				if(pren==null) { pren=""; }
				
				String clt = rc.getCode()+" "+rc.getNom()+" "+pren+" "+rc.getCategory().getNom_category();
				
				mp.put("start",start);
				mp.put("end",end);
				mp.put("code_nom_client", clt);/*
				mp.put("total_quantite", total_quant);
				mp.put("total_ht", total_ht);
				mp.put("total_ttc", total_ttc);*/
				mp.put("id_rc",rc.getId());
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\VC.pdf");
					
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
	
	public String generate_vente_produit(String start, String end, article art) {
		 
		 String destination = "D:/Commercial/Doc/STAT/VP.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\vente_produit.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				String code_libelle = art.getCode()+" - "+art.getLibelle();
				
				
				
				mp.put("start",start);
				mp.put("end",end);
				mp.put("code_libelle", code_libelle);
				mp.put("id_art",art.getId());
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\VP.pdf");
					
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
	
	public String generate_vente_produit_val(String start, String end, article art) {
		 
		 String destination = "D:/Commercial/Doc/STAT/VPV.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\vente_produit_val.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				String code_libelle = art.getCode()+" - "+art.getLibelle();
				
				
				
				mp.put("start",start);
				mp.put("end",end);
				mp.put("code_libelle", code_libelle);
				mp.put("id_art",art.getId());
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\VPV.pdf");
					
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
	
	public String generate_etat_client(String etat) {
		 
		 String destination = "D:/Commercial/Doc/STAT/EC.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\etat_client.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				Map<String, Object> mp = new HashMap<String, Object>();
					
				String condition = "", etat_string = "";
				
					switch (etat) {
					
						case "debit": {
							
							etat_string = "Débiteur";
							condition  = " sold_encours > '0' ";
							
						}break;
						
						case "credit": {
							
							etat_string = "Créditeur";
							condition  = " sold_encours < '0' ";
							
						}break;
						
						case "sold": {
							
							etat_string = "Soldé";
							condition  = " sold_encours = '0' ";
							
						}break;	
							
					}
				
					mp.put("etat_string", etat_string);
					
					mp.put("condition", condition);
					
					try {
						
						Connection con  = localDataSource.getConnection();
					
						JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
						
						File dir = new File("D:\\Commercial\\Doc\\BL");
						
					    if (!dir.exists()) dir.mkdirs();
						
						JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\STAT\\EC.pdf");
						
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
	
	//---------------------------------------------------------------------------------------------
	
	public String generate_vente_produit_global(String start, String end) {
		 
		 String destination = "D:/Commercial/Doc/STAT/VPG.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\vente_produit_global.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("start",start);
				mp.put("end",end);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\VPG.pdf");
					
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
	
	public String generate_vente_employee(String start, String end) {
		 
		 String destination = "D:/Commercial/Doc/STAT/VEMP.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\vente_personnel.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("start",start);
				mp.put("end",end);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\VEMP.pdf");
					
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
	
	public String generate_releve_client(String start, String end, registre_commerce rc) {
		 
		 String destination = "D:/Commercial/Doc/STAT/RC.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\releve_client.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("code_nom_client", rc.getCode()+" - "+rc.getNom()+" "+rc.getPrenom());
				mp.put("start", start);
				mp.put("end", end);
				mp.put("id_rc", rc.getId());
				
				if( mvmRepo.sold_debut_periode(rc, start, end).size()==0) {
					
					mp.put("sold_start", df.format(rc.getSold_encours()) );
					mp.put("sold_end", df.format(rc.getSold_encours()) );
					
				}
				else {
					
					//sold_fin_clt = mvml.get(mvml.size()-1).getNew_sold_client();
					/*
					mp.put("sold_start", df.format( mvmRepo.sold_debut_periode(rc, start, end).get(0).getOld_sold_rc() ));
					mp.put("sold_end", df.format( mvmRepo.sold_fin_periode(rc, start, end).get(0).getNew_sold_rc() ));
					*/
					
					List<mouvement> mvml = mvmRepo.mouvement_by_rc_intervall(rc, start, end);
					
					mp.put("sold_start", df.format( mvml.get(0).getOld_sold_rc() ));
					mp.put("sold_end", df.format( mvml.get(mvml.size()-1).getNew_sold_rc() ));
					
				}
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\RC.pdf");
					
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
	
	public String generate_vente_produit_client(String start, String end, registre_commerce rc) {
		 
		 String destination = "D:/Commercial/Doc/STAT/VPC.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\vente_produit_client_subreport1.jrxml");
				
				JasperReport JSubReport = JasperCompileManager.compileReport(jdesign);
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\vente_produit_client.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				String pren = rc.getPrenom();
				
				if(pren==null) { pren=""; }
				
				mp.put("code_nom_client", rc.getCode()+" - "+rc.getNom()+" "+pren+" "+rc.getCategory().getNom_category());
				mp.put("start", start);
				mp.put("end", end);
				mp.put("id_rc", rc.getId());
				mp.put("SubReportParam", JSubReport);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint = JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\VPC.pdf");
					
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
	
	public String generate_declaration_tva(String start, String end) {
		 
		 String destination = "D:/Commercial/Doc/STAT/DTVA.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\declaration_tva.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("start", start);
				mp.put("end", end);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint = JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\DTVA.pdf");
					
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
	
	public String generate_etat_vente_client(String start, String end, category_produit cp, String unite) {
		 
		 String destination = "D:/Commercial/Doc/STAT/EVC.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\etat_ventes_client.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("start", start);
				mp.put("end", end);
				mp.put("id_category", cp.getId());
				mp.put("category_produit", cp.getNom_category());
				mp.put("unite", unite);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint = JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\EVC.pdf");
					
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
	
	public String generate_etat_104(String start, String end, category_produit cp, String unite) {
		 
		 String destination = "D:/Commercial/Doc/STAT/ETAT104.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\Etat 104.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				String cond_cat = (cp.getId()==0) ? "true" : 
					
					"registre_commerce IN "+
					"(SELECT registre_commerce FROM proforma_cmd_bl_fact.facture fact "+
					 "JOIN proforma_cmd_bl_fact.facture_detail fact_d on fact_d.facture = fact.id "+

					 "JOIN article.article art ON art.id = fact_d.article "+
					 "JOIN article.produit prd ON prd.id = art.produit "+
					 "JOIN article.sous_category_produit scp ON scp.id = prd.sous_category_produit "+
					 "JOIN article.category_produit cp ON cp.id = scp.category_produit "+

					 "WHERE CAST(date AS date) BETWEEN CAST('"+start+"' AS date) AND CAST('"+end+"' AS date) and cp.id = '"+cp.getId()+"' "+

					 "Group by registre_commerce) ";
				
				String produit = (cp.getId()==0) ? "" : "Produit : "+cp.getNom_category();
				
				mp.put("start", start);
				mp.put("end", end);
				mp.put("condition_cat", cond_cat);
				mp.put("category_produit", produit);
				mp.put("unite", unite);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint = JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\ETAT104.pdf");
					
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
	
	public String generate_etat_sortie() {
		 
		 String destination = "D:/Commercial/Doc/STAT/ES.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\etat_stock.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				List <String> col = new ArrayList<String>();
				
				List <String> row = new ArrayList<String>();
				
				col.add("aaa");col.add("bbb");
				
				row.add("aaa");row.add("bbb");
				
				mp.put("ColumnsHeader", col );
				mp.put("Rows", row);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint = JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\ES.pdf");
					
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
	
	public String generate_rc_buyer(String start, String end, Long id_cat) {
		 
		 String destination = "D:/Commercial/Doc/STAT/RcBuyer.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\RcBuyer.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("start", start );
				mp.put("end", end);
				
				if(id_cat==0) {
					
					mp.put("condition_category", " true " );
					mp.put("category", "Tout");
					
				}
				else {
					
					mp.put("condition_category", " category_client = '"+cat_cRepo.getOne(id_cat).getId()+"' " );
					mp.put("category", cat_cRepo.getOne(id_cat).getNom_category());
					
				}
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint = JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\RcBuyer.pdf");
					
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
	
	public String generate_quantite_sub_vendu(String start, String end) {
		 
		 String destination = "D:/Commercial/Doc/STAT/QNTSUB.pdf";
		 
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\statistique\\etat_vente_son_subvension.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("start", start);
				mp.put("end", end);
				
				try {
					
					Connection con  = localDataSource.getConnection();
				
					JasperPrint jprint = JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\STAT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint, "D:\\Commercial\\Doc\\STAT\\QNTSUB.pdf");
					
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
	
	//------------------------------ PRINT BLF ------------------------------------
	
	public String generate_blf(bon_livraison_facture blf, String qr_code) {
		
		 JasperDesign jdesign; 
			try {
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("num_blf", blf.getNumero());
				mp.put("id_blf", blf.getId());
				mp.put("num_client", blf.getRegistre_commerce().getCode());
				mp.put("matricule", blf.getMatricule());
				mp.put("nom_client", blf.getRegistre_commerce().getNom()+" "+blf.getRegistre_commerce().getPrenom()+" "+
						blf.getRegistre_commerce().getCategory().getNom_category());
				mp.put("adresse_client", blf.getRegistre_commerce().getAdresse());
				mp.put("rc", blf.getRegistre_commerce().getNumero_rc());
				mp.put("nif", blf.getRegistre_commerce().getNumero_nif());
				mp.put("nis", blf.getRegistre_commerce().getNumero_art());
				mp.put("user", blf.getUsers().getMatricule());
				mp.put("cat_rc", blf.getRegistre_commerce().getCategory().getNom_category());
				mp.put("date", blf.getDate());
				mp.put("mode_pay", blf.getCommande().getMode_paiement().getDesignation()); //--->  null pointer exception
				mp.put("chauffeur", blf.getChauffeur());
				mp.put("user", blf.getUsers().getMatricule());
				mp.put("time", blf.getTime());
				
				
				
				//mp.put("total_remise", df.format(fact.getBon_livraison().getCommande().getValeur_reduction()) );
				mp.put("total_ht", df.format(blf.getMontant_ht()) );
				mp.put("total_tva", df.format(blf.getMontant_tva()) );
				mp.put("total_ttc", df.format(blf.getMontant_ttc()) );
				mp.put("timbre", df.format(0) );
				mp.put("total_ht_net", df.format(blf.getMontant_ht_net()) );
				mp.put("total_remise", df.format(blf.getMontant_remise()) );
				
				mp.put("qr_path", qr_code );
				
				//---------------------- Entreprise INFO -------------------------------
				
				mp.put("Nom_entreprise", infoeRepo.getOne((long)1 ).getNom_entreprise() );
				mp.put("capitale", df.format(infoeRepo.getOne((long)1 ).getCapitale()) );
				mp.put("adresse", infoeRepo.getOne((long)1 ).getAdresse_facturation() );
				mp.put("tel", infoeRepo.getOne((long)1 ).getTelephone() );
				mp.put("num_rc", infoeRepo.getOne((long)1 ).getNum_rc() );
				mp.put("num_art", infoeRepo.getOne((long)1 ).getNum_art() );
				mp.put("num_nif", infoeRepo.getOne((long)1 ).getNum_nif() );
				
				mp.put("logo_path", infoeRepo.getOne((long)1 ).getChemain_logo() );
				
				//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
				
				String m_ttc1 = df.format(blf.getMontant_ttc());
				
				String m_ttc = m_ttc1.replaceAll(" ", "");
				
				String m [] = m_ttc.split(",");
				
				char  chkoupi [] = m[1].toCharArray();
				
				String virgule = "";
				
				if(chkoupi[0]=='0' && chkoupi[1]!='0') {
					
					String chk = ""+chkoupi[0], chk1 = ""+chkoupi[1]; 
					
					virgule = FrenchNumberToWords.convert(Double.parseDouble(chk))+" "+
								FrenchNumberToWords.convert(Double.parseDouble(chk1));
					
				}
				else {
					
					virgule = FrenchNumberToWords.convert(Double.parseDouble(m[1]));
					
				}
				
				mp.put("total_ttc_lettre", FrenchNumberToWords.convert(Double.parseDouble(m[0]))+" Virgule "+
				virgule+" Dinars Algérien");
				
				String cumule_tva = "";
				
				List <bon_livraison_facture_detail> list_blf_det = blfdRepo.get_detail_by_blf(blf);
				
				Map<String, Double> cuml = new HashMap<String, Double>();
				
				for(int i=0;i<list_blf_det.size();i++) {
					
					bon_livraison_facture_detail blfd = list_blf_det.get(i);
					
					if(cuml.containsKey(""+blfd.getTva())) {
						
						double val = cuml.get(""+blfd.getTva());
						
						cuml.put(""+blfd.getTva(), val + ( blfd.getMontant_ht() * (blfd.getTva()/100) ) );
						
					}
					else {
						
						cuml.putIfAbsent(""+blfd.getTva(), blfd.getMontant_ht() * (blfd.getTva()/100));
						
					}
					
				}
				
				for (Map.Entry<String, Double> entry : cuml.entrySet()) {
				    String key = entry.getKey();
				    Double value = entry.getValue();
				    
				    cumule_tva = cumule_tva + key + "% \t"+ df.format(value)+"\n";
				    
				}
				
				//----------------------- +++++++++++++++++++ --------------------------
				
				mp.put("cumule_tva", cumule_tva);
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\Bon Livraison\\BLF.jrxml");
					
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				try {
					
					Connection con = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\BL");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BL\\BLF.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/BL/BLF.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_blfs(List<bon_livraison_facture> blfs) throws IOException {
		
		ArrayList<String> allPdfs = new ArrayList<String>();
		
		for(int i=0; i<blfs.size(); i++) {
			
			String qr_code = generateQRcode.createQRcode(blfs.get(i).getNumero(), "BL");
			
			String pdf = generate_blq(blfs.get(i), qr_code);
			
		    Path source = Paths.get(pdf);

		    Path newDir = Paths.get("D:\\Commercial\\Doc\\Combination");

		    //create the target directories, if directory exits, no effect
		    Files.createDirectories(newDir);
		    
		    String fp = source.getFileName().toString();
		    
		    fp = fp.replaceAll(".pdf", i+".pdf");
		    
		    Files.move(source, newDir.resolve(fp), StandardCopyOption.REPLACE_EXISTING);
		    
		    String newPath = "D:\\Commercial\\Doc\\Combination\\"+fp;
		    
		    allPdfs.add(newPath);
		    
		}
		
		CombinePdf.combine(allPdfs, "D:\\Commercial\\Doc\\BL\\BLFS.pdf");
		
		return "D:/Commercial/Doc/BL/BLFS.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_facts(List<facture> facts) throws IOException {
		
		ArrayList<String> allPdfs = new ArrayList<String>();
		
		for(int i=0; i<facts.size(); i++) {
			
			String qr_code = generateQRcode.createQRcode(facts.get(i).getNumero(), "FCT");
			
			String pdf = generate_Fact_A4(facts.get(i), qr_code);
			
		    Path source = Paths.get(pdf);

		    Path newDir = Paths.get("D:\\Commercial\\Doc\\Combination");

		    //create the target directories, if directory exits, no effect
		    Files.createDirectories(newDir);
		    
		    String fp = source.getFileName().toString();
		    
		    fp = fp.replaceAll(".pdf", i+".pdf");
		    
		    Files.move(source, newDir.resolve(fp), StandardCopyOption.REPLACE_EXISTING);
		    
		    String newPath = "D:\\Commercial\\Doc\\Combination\\"+fp;
		    
		    allPdfs.add(newPath);
		    
		}
		
		CombinePdf.combine(allPdfs, "D:\\Commercial\\Doc\\BL\\BLFS.pdf");
		
		return "D:/Commercial/Doc/BL/BLFS.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_blfs_valorise(List<bon_livraison_facture> blfs) throws IOException {
		
		ArrayList<String> allPdfs = new ArrayList<String>();
		
		for(int i=0; i<blfs.size(); i++) {
			
			String qr_code = generateQRcode.createQRcode(blfs.get(i).getNumero(), "BL");
			
			String pdf = generate_blf(blfs.get(i), qr_code);
			
		    Path source = Paths.get(pdf);

		    Path newDir = Paths.get("D:\\Commercial\\Doc\\Combination");

		    //create the target directories, if directory exits, no effect
		    Files.createDirectories(newDir);
		    
		    String fp = source.getFileName().toString();
		    
		    fp = fp.replaceAll(".pdf", i+".pdf");
		    
		    Files.move(source, newDir.resolve(fp), StandardCopyOption.REPLACE_EXISTING);
		    
		    String newPath = "D:\\Commercial\\Doc\\Combination\\"+fp;
		    
		    allPdfs.add(newPath);
		    
		}
		
		CombinePdf.combine(allPdfs, "D:\\Commercial\\Doc\\BL\\BLFS.pdf");
		
		return "D:/Commercial/Doc/BL/BLFS.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_bts(List<bon_transfert> bts) throws IOException {
		
		ArrayList<String> allPdfs = new ArrayList<String>();
		
		for(int i=0; i<bts.size(); i++) {
			
			String pdf = generate_bt(bts.get(i));
			
		    Path source = Paths.get(pdf);

		    Path newDir = Paths.get("D:\\Commercial\\Doc\\Combination");

		    //create the target directories, if directory exits, no effect
		    Files.createDirectories(newDir);
		    
		    String fp = source.getFileName().toString();
		    
		    fp = fp.replaceAll(".pdf", i+".pdf");
		    
		    Files.move(source, newDir.resolve(fp), StandardCopyOption.REPLACE_EXISTING);
		    
		    String newPath = "D:\\Commercial\\Doc\\Combination\\"+fp;
		    
		    allPdfs.add(newPath);
		    
		}
		
		CombinePdf.combine(allPdfs, "D:\\Commercial\\Doc\\BL\\BTS.pdf");
		
		return "D:/Commercial/Doc/BL/BTS.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	//------------------------------ PRINT BLF BT------------------------------------
	
	public String generate_blf_bt(bon_livraison_facture blf, String qr_code) {
		
		 JasperDesign jdesign; 
			try {
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("num_blf", blf.getNumero().replaceAll("L", "T"));
				mp.put("id_blf", blf.getId());
				mp.put("num_client", blf.getRegistre_commerce().getCode());
				mp.put("matricule", blf.getMatricule());
				mp.put("nom_client", blf.getRegistre_commerce().getNom()+" "+blf.getRegistre_commerce().getPrenom()+" "+
						blf.getRegistre_commerce().getCategory().getNom_category());
				mp.put("adresse_client", blf.getRegistre_commerce().getAdresse());
				mp.put("rc", blf.getRegistre_commerce().getNumero_rc());
				mp.put("nif", blf.getRegistre_commerce().getNumero_nif());
				mp.put("nis", blf.getRegistre_commerce().getNumero_art());
				mp.put("user", blf.getUsers().getMatricule());
				mp.put("cat_rc", blf.getRegistre_commerce().getCategory().getNom_category());
				mp.put("date", blf.getDate());
				mp.put("mode_pay", blf.getCommande().getMode_paiement().getDesignation());
				mp.put("chauffeur", blf.getChauffeur());
				
				//mp.put("total_remise", df.format(fact.getBon_livraison().getCommande().getValeur_reduction()) );
				mp.put("total_ht", df.format(blf.getMontant_ht()) );
				mp.put("total_tva", df.format(blf.getMontant_tva()) );
				mp.put("total_ttc", df.format(blf.getMontant_ttc()) );
				mp.put("timbre", df.format(0) );
				mp.put("total_ht_net", df.format(blf.getMontant_ht_net()) );
				mp.put("total_remise", df.format(blf.getMontant_remise()) );
				
				mp.put("qr_path", qr_code );
				
				//---------------------- Entreprise INFO -------------------------------
				
				mp.put("Nom_entreprise", infoeRepo.getOne((long)1 ).getNom_entreprise() );
				mp.put("capitale", df.format(infoeRepo.getOne((long)1 ).getCapitale()) );
				mp.put("adresse", infoeRepo.getOne((long)1 ).getAdresse_facturation() );
				mp.put("tel", infoeRepo.getOne((long)1 ).getTelephone() );
				mp.put("num_rc", infoeRepo.getOne((long)1 ).getNum_rc() );
				mp.put("num_art", infoeRepo.getOne((long)1 ).getNum_art() );
				mp.put("num_nif", infoeRepo.getOne((long)1 ).getNum_nif() );
				
				mp.put("logo_path", infoeRepo.getOne((long)1 ).getChemain_logo() );
				
				//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
				
				String m_ttc1 = df.format(blf.getMontant_ttc());
				
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
				
				//---------------- reglement 
				/*
				String reglem = "";
				
				if(fact.getRegistre_commerce().getType_reglement().getDesignation().equals("A Terme")) {
					
					reglem = "A Terme";
					
				}
				else {
					
					List<paiement> lst_pay = paiRepo.get_payments_date_rc(fact.getDate(), fact.getRegistre_commerce());
					
					if(lst_pay.size()==0) {
						
						reglem = "A Compte";
						
					}
					else {
						
						paiement pay = lst_pay.get(lst_pay.size()-1);
						
						String info_supp = "";
						
						if(pay.getInfo_supp_banque()!=null) {info_supp = pay.getInfo_supp_banque();}
						
						reglem = pay.getBanque().getNom_banque()+" "+info_supp+" "+pay.getNumero_piece();
						
					}
					
				}
				
				
				mp.put("reglem", reglem);
				*/
				//----------------------- calcule Cumule TVA --------------------------
				
				String cumule_tva = "";
				
				List <bon_livraison_facture_detail> list_blf_det = blfdRepo.get_detail_by_blf(blf);
				
				Map<String, Double> cuml = new HashMap<String, Double>();
				
				for(int i=0;i<list_blf_det.size();i++) {
					
					bon_livraison_facture_detail blfd = list_blf_det.get(i);
					
					if(cuml.containsKey(""+blfd.getTva())) {
						
						double val = cuml.get(""+blfd.getTva());
						
						cuml.put(""+blfd.getTva(), val + ( blfd.getMontant_ht() * (blfd.getTva()/100) ) );
						
					}
					else {
						
						cuml.putIfAbsent(""+blfd.getTva(), blfd.getMontant_ht() * (blfd.getTva()/100));
						
					}
					
				}
				
				for (Map.Entry<String, Double> entry : cuml.entrySet()) {
				    String key = entry.getKey();
				    Double value = entry.getValue();
				    
				    cumule_tva = cumule_tva + key + "% \t"+ df.format(value)+"\n";
				    
				}
				
				//----------------------- +++++++++++++++++++ --------------------------
				
				mp.put("cumule_tva", cumule_tva);
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\Bon Livraison\\BT.jrxml");
					
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				try {
					
					Connection con = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\BL");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BL\\BT.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/BL/BT.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_bt(bon_transfert bt) {
		
		 JasperDesign jdesign; 
			try {
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("num_bt", bt.getNumero());
				mp.put("id_bt", bt.getId());
				mp.put("num_client", bt.getUnite_interne().getCode());
				mp.put("matricule", bt.getMatricule_vehicule());
				mp.put("nom_client", bt.getUnite_interne().getDesignation());
				mp.put("adresse_client", bt.getUnite_interne().getAdresse());
				mp.put("rc",  bt.getUnite_interne().getNumero_rc());
				mp.put("nif",  bt.getUnite_interne().getNumero_nif());
				mp.put("nis",  bt.getUnite_interne().getNumero_art());
				mp.put("user", bt.getUsers().getMatricule());
				mp.put("date", bt.getDate());
				mp.put("chauffeur", bt.getChauffeur());
				
				//---------------------- Entreprise INFO -------------------------------
				
				mp.put("Nom_entreprise", infoeRepo.getOne((long)1 ).getNom_entreprise() );
				mp.put("capitale", df.format(infoeRepo.getOne((long)1 ).getCapitale()) );
				mp.put("adresse", infoeRepo.getOne((long)1 ).getAdresse_facturation() );
				mp.put("tel", infoeRepo.getOne((long)1 ).getTelephone() );
				mp.put("num_rc", infoeRepo.getOne((long)1 ).getNum_rc() );
				mp.put("num_art", infoeRepo.getOne((long)1 ).getNum_art() );
				mp.put("num_nif", infoeRepo.getOne((long)1 ).getNum_nif() );
				
				mp.put("logo_path", infoeRepo.getOne((long)1 ).getChemain_logo() );
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\Bon Livraison\\BonTransfert.jrxml");
					
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				try {
					
					Connection con = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\BT");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BT\\BonTransfert.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/BT/BonTransfert.pdf";
	
	}	
	
	//------------------------------------------------------------------------------
	
	public String generate_blq(bon_livraison_facture blf, String qr_code) {
		
		 JasperDesign jdesign; 
			try {
				
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("num_blf", blf.getNumero());
				mp.put("id_blf", blf.getId());
				mp.put("num_client", blf.getRegistre_commerce().getCode());
				mp.put("matricule", blf.getMatricule());
				mp.put("nom_client", blf.getRegistre_commerce().getNom()+" "+blf.getRegistre_commerce().getPrenom()+" "+
						blf.getRegistre_commerce().getCategory().getNom_category());
				mp.put("adresse_client", blf.getRegistre_commerce().getAdresse());
				mp.put("rc", blf.getRegistre_commerce().getNumero_rc());
				mp.put("nif", blf.getRegistre_commerce().getNumero_nif());
				mp.put("nis", blf.getRegistre_commerce().getNumero_art());
				mp.put("user", blf.getUsers().getMatricule());
				mp.put("cat_rc", blf.getRegistre_commerce().getCategory().getNom_category());
				mp.put("date", blf.getDate());
				mp.put("mode_pay", blf.getCommande().getMode_paiement().getDesignation());
				mp.put("chauffeur", blf.getChauffeur());
				
				//mp.put("total_remise", df.format(fact.getBon_livraison().getCommande().getValeur_reduction()) );
				mp.put("total_ht", df.format(blf.getMontant_ht()) );
				mp.put("total_tva", df.format(blf.getMontant_tva()) );
				mp.put("total_ttc", df.format(blf.getMontant_ttc()) );
				mp.put("timbre", df.format(0) );
				mp.put("total_ht_net", df.format(blf.getMontant_ht_net()) );
				mp.put("total_remise", df.format(blf.getMontant_remise()) );
				
				mp.put("qr_path", qr_code );
				
				//---------------------- Entreprise INFO -------------------------------
				
				mp.put("Nom_entreprise", infoeRepo.getOne((long)1 ).getNom_entreprise() );
				mp.put("capitale", df.format(infoeRepo.getOne((long)1 ).getCapitale()) );
				mp.put("adresse", infoeRepo.getOne((long)1 ).getAdresse_facturation() );
				mp.put("tel", infoeRepo.getOne((long)1 ).getTelephone() );
				mp.put("num_rc", infoeRepo.getOne((long)1 ).getNum_rc() );
				mp.put("num_art", infoeRepo.getOne((long)1 ).getNum_art() );
				mp.put("num_nif", infoeRepo.getOne((long)1 ).getNum_nif() );
				
				mp.put("logo_path", infoeRepo.getOne((long)1 ).getChemain_logo() );
				
				//----------------------- ajout virgule f lettre ta3 shkoupi ------------------
				
				String m_ttc1 = df.format(blf.getMontant_ttc());
				
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
				
				//---------------- reglement 
				/*
				String reglem = "";
				
				if(fact.getRegistre_commerce().getType_reglement().getDesignation().equals("A Terme")) {
					
					reglem = "A Terme";
					
				}
				else {
					
					List<paiement> lst_pay = paiRepo.get_payments_date_rc(fact.getDate(), fact.getRegistre_commerce());
					
					if(lst_pay.size()==0) {
						
						reglem = "A Compte";
						
					}
					else {
						
						paiement pay = lst_pay.get(lst_pay.size()-1);
						
						String info_supp = "";
						
						if(pay.getInfo_supp_banque()!=null) {info_supp = pay.getInfo_supp_banque();}
						
						reglem = pay.getBanque().getNom_banque()+" "+info_supp+" "+pay.getNumero_piece();
						
					}
					
				}
				
				
				mp.put("reglem", reglem);
				*/
				//----------------------- calcule Cumule TVA --------------------------
				
				String cumule_tva = "";
				
				List <bon_livraison_facture_detail> list_blf_det = blfdRepo.get_detail_by_blf(blf);
				
				Map<String, Double> cuml = new HashMap<String, Double>();
				
				for(int i=0;i<list_blf_det.size();i++) {
					
					bon_livraison_facture_detail blfd = list_blf_det.get(i);
					
					if(cuml.containsKey(""+blfd.getTva())) {
						
						double val = cuml.get(""+blfd.getTva());
						
						cuml.put(""+blfd.getTva(), val + ( blfd.getMontant_ht() * (blfd.getTva()/100) ) );
						
					}
					else {
						
						cuml.putIfAbsent(""+blfd.getTva(), blfd.getMontant_ht() * (blfd.getTva()/100));
						
					}
					
				}
				
				for (Map.Entry<String, Double> entry : cuml.entrySet()) {
				    String key = entry.getKey();
				    Double value = entry.getValue();
				    
				    cumule_tva = cumule_tva + key + "% \t"+ df.format(value)+"\n";
				    
				}
				
				//----------------------- +++++++++++++++++++ --------------------------
				
				mp.put("cumule_tva", cumule_tva);
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\Bon Livraison\\BLQ.jrxml");
					
				
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				
				try {
					
					Connection con = localDataSource.getConnection();
				
					JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
					
					File dir = new File("D:\\Commercial\\Doc\\BL");
					
				    if (!dir.exists()) dir.mkdirs();
					
					JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BL\\BLQ.pdf");
					
					con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/BL/BLQ.pdf";
	
	}	
	
	//_____________________________________________________________ EXPORT EXCEL =========================
	/*
	 private static JRXlsDataSource getDataSource_vente_client() throws JRException
	  {
	    JRXlsDataSource ds;
	    try
	    {
	  
	      String[] columnNames1 = new String[]{"code","libelle","quantite","total_ht","total_ttc"};
	      int[] columnIndexes1 = new int[]{ 0,1,2,3,4};
	      
	      //ds = new JRXlsDataSource();
	      ds = new JRXlsDataSource("D:\\Commercial\\report\\excel_transfert\\vc.xls");
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
	
	//-------------------------------------------------------------------------------
	
	//________________________________________________________________________
	/*
	double total_ht = 0;
	double total_ttc = 0;
	double total_quant = 0;
	
	try {
			
		// creation du xls
		WritableWorkbook workbook;
		workbook = Workbook.createWorkbook(new File("D:\\Commercial\\report\\excel_transfert\\vc.xls"));
		
		WritableSheet sheet = workbook.createSheet("Premier classeur", 0);
	
	
		List<Object[]> list_fct = fct_detRepo.get_quantite_sold_val_by_rc(start, end, rc);
		
		List<Object[]> list_fct_av = fct_av_detRepo.get_quantite_avoir_val_by_rc(start, end, rc);
		
		int ligne=0;
		
		for(int i=0;i<list_fct.size();i++) {
			
			sheet.addCell(new Label(0, ligne,(String) list_fct.get(i)[0] ));
			sheet.addCell(new Label(1, ligne,(String) list_fct.get(i)[1] ));
			sheet.addCell(new Label(2, ligne,Double.toString((double) list_fct.get(i)[2]) ));
			sheet.addCell(new Label(3, ligne,Double.toString((double) list_fct.get(i)[3]) ));
			sheet.addCell(new Label(4, ligne,Double.toString((double) list_fct.get(i)[5]) ));
			
			total_quant = total_quant + (double) list_fct.get(i)[2];
			total_ht = total_ht + (double) list_fct.get(i)[3];
			total_ttc = total_ttc + (double) list_fct.get(i)[5];
			
			ligne++;
			
		}
		
		for(int i=0;i<list_fct_av.size();i++) {
			
			sheet.addCell(new Label(0, ligne,(String) list_fct_av.get(i)[0] ));
			sheet.addCell(new Label(1, ligne,(String) list_fct_av.get(i)[1] ));
			sheet.addCell(new Label(2, ligne,Double.toString( ((double) list_fct_av.get(i)[2]) *(-1) ) ));
			sheet.addCell(new Label(3, ligne,Double.toString( ((double) list_fct_av.get(i)[3]) *(-1) ) ));
			sheet.addCell(new Label(4, ligne,Double.toString( ((double) list_fct_av.get(i)[5]) *(-1) ) ));
			
			total_quant = total_quant - (double) list_fct.get(i)[2];
			total_ht = total_ht - (double) list_fct.get(i)[3];
			total_ttc = total_ttc - (double) list_fct.get(i)[5];
			
			ligne++;
			
		}
		
		workbook.write();
	
		workbook.close();
		
	 } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (WriteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 */
	
	//______________________________________________________________________________
	
}
