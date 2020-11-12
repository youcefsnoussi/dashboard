package com.commercial.functions;

import java.io.File;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;



public class generate_Doc {
	
	@Autowired
    private static DataSource localDataSource;
	
	public generate_Doc() {
		// TODO Auto-generated constructor stub
	}
	
	public static String generate_BL(long id_bl, String numero, String matricule, String qr_code, Connection con) {
		
		 JasperDesign jdesign; 
			try {
				
				jdesign = JRXmlLoader.load("D:\\Commercial\\report\\Bon Livraison\\BL.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(jdesign);
				Map<String, Object> mp = new HashMap<String, Object>();
				
				mp.put("id_bon_livraison",id_bl);
				mp.put("num",numero);
				mp.put("qr_code", qr_code);
				mp.put("Matricule", matricule);
				
				
				    
				JasperPrint jprint=JasperFillManager.fillReport(jreport,  mp, con);
				
				File dir = new File("D:\\Commercial\\Doc\\BL");
			    if (!dir.exists()) dir.mkdirs();
				
				JasperExportManager.exportReportToPdfFile(jprint,"D:\\Commercial\\Doc\\BL\\BL.pdf");
				
				//JasperExportManager.exportReportToHtmlFile(jprint, "D:\\Commercial\\Doc\\BL\\BL.html");
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "D:/Commercial/Doc/BL/BL.pdf";
	
	}		
			
}
