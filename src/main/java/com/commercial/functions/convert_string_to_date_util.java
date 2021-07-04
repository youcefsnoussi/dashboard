package com.commercial.functions;

import java.text.ParseException;
import java.util.Date;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

public class convert_string_to_date_util {

	public convert_string_to_date_util() {
		// TODO Auto-generated constructor stub
	}
	
	public Date convertion_from_InputDate(String date) throws ParseException {
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date result = formatter.parse(date);
		
		return result;
		
	}
	
	public Date convertion_from_my_date(String date) throws ParseException {
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date result = formatter.parse(date);
		
		return result;
		
	}
	
	public String convertion_InputDate_to_MyDate(String date){
		
		String [] sp = date.split("-");
		
		String result = sp[2]+"/"+sp[1]+"/"+sp[0];
		
		return result;
		
	}
	
	public String convertion_MyDate_to_InputDate(String date) {
		
		String result = "";
		
		if(date!=null) {
		
			String [] sp = date.split("/");
			
			result = sp[2]+"-"+sp[1]+"-"+sp[0];
		
		}
		
		return result;
		
	}
	
}
