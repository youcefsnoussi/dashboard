package com.commercial.functions;

import java.util.Calendar;

public class convert_calander_string {
	
	
	
	public convert_calander_string() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String date_tostring( Calendar c){
		
		//Calendar c = Calendar.getInstance();
		
		
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
	
	
	public String time_tostring(Calendar c){
		
		//Calendar c = Calendar.getInstance();
		
		
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
