package com.commercial.functions;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class CombinePdf {

	public CombinePdf() {
		// TODO Auto-generated constructor stub
	}
	
	public static void combine(ArrayList<String> datas,String destination){
		
		PDFMergerUtility ut = new PDFMergerUtility();
		for(int i=0 ; i<datas.size() ; i++)
			
			try {
				ut.addSource(datas.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
			try {
				ut.setDestinationFileName(destination);
				ut.mergeDocuments();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
}
