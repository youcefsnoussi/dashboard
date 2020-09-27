package com.commercial.functions;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class get_time_date {
	
	
	
	
	public get_time_date() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String get_time(){
		
		Calendar c = Calendar.getInstance();
		
		
		String t;
		 
		t = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
		if(c.get(Calendar.MINUTE)<10){
			
			if(c.get(Calendar.HOUR_OF_DAY)<10){
				t = "0"+c.get(Calendar.HOUR_OF_DAY)+":0"+c.get(Calendar.MINUTE);
			}
			
			else{
				t = c.get(Calendar.HOUR_OF_DAY)+":0"+c.get(Calendar.MINUTE);
			}
			
			//t = c.get(Calendar.HOUR_OF_DAY)+":0"+c.get(Calendar.MINUTE);
		}
		else{
			
			if(c.get(Calendar.HOUR_OF_DAY)<10){
				t = "0"+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
			}
			else{
				t = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
			}
			
			//t = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
		}
		
		return t;
		
	} 
	
	
	public String get_date(){
			
			Calendar c = Calendar.getInstance();
			
			
			String d;
			if((c.get(Calendar.MONTH)+1) <10 ){
				
				if(c.get(Calendar.DAY_OF_MONTH)<10){
					d = "0"+c.get(Calendar.DAY_OF_MONTH)+"/0"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
				}
				else{
					d = c.get(Calendar.DAY_OF_MONTH)+"/0"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
					}
					
										 
			}
			else{
				
				if(c.get(Calendar.DAY_OF_MONTH)<10){
					d = "0"+c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
				}
				else{d = c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);}
			}
			
			return d;
			
		}
	
	
		public String get_time_second(){
		
		Calendar c = Calendar.getInstance();
		
		
		String t;
		 
		t = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
		if(c.get(Calendar.MINUTE)<10){
			
			if(c.get(Calendar.HOUR_OF_DAY)<10){
				t = "0"+c.get(Calendar.HOUR_OF_DAY)+":0"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
			}
			
			else{
				t = c.get(Calendar.HOUR_OF_DAY)+":0"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
			}
			
			//t = c.get(Calendar.HOUR_OF_DAY)+":0"+c.get(Calendar.MINUTE);
		}
		else{
			
			if(c.get(Calendar.HOUR_OF_DAY)<10){
				t = "0"+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
			}
			else{
				t = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
			}
			
			//t = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
		}
		
		return t;
		
	}
		
		
	public String get_date_before(int nbr, String date){
		
		//System.out.println("init date == "+date);
				
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			System.out.println("date == "+date);
			
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.add(Calendar.DATE, (nbr*(-1)));
		
		convert_calander_string conv = new convert_calander_string();
		
		String d = conv.date_tostring(c); 
		
		c.clear();
		
		return d;
		
	}
	
	public ArrayList<String> get_dates_before(int nbr, String date){
		
		//System.out.println("init date == "+date);
		
		ArrayList<String> list = new ArrayList<String>();
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			System.out.println("date == "+date);
			c.setTime(sdf.parse(date));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < nbr;i++) {
			
			c.add(Calendar.DATE, (-1));
			
			convert_calander_string conv = new convert_calander_string();
			
			String d = conv.date_tostring(c); 
			
			list.add(d);
			
		}
		
		
		//c.clear();
		
		return list;
		
	}
	
	//--------------------------------------------------------------------------------
	
	public String get_date_input_date(){
		
		String today = get_date();
		
		String [] str = today.split("/");
		
		today = str[2]+"-"+str[1]+"-"+str[0];
		
		return today;
		
	}
	
	//--------------------------------------------------------------------------------
	
	public Date parse_input_date_sql_date(String date){
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		
		java.util.Date date1 = null;
		try {
			date1 = sdf1.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		java.sql.Date sql_date = new java.sql.Date(date1.getTime());
		
		return sql_date;
		
	}
	
	//--------------------------------------------------------------------------------
	
		public String convert_input_date_date(String date){
			
			//String today = get_date();
			
			String [] str = date.split("-");
			
			String today = str[2]+"/"+str[1]+"/"+str[0];
			
			return today;
			
		}
		
	//-----------------------------------------------------------------------------------	
	
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			get_time_date tt  = new get_time_date();
			
			System.out.println(tt.convert_input_date_date("2019-04-07"));

		}

}


