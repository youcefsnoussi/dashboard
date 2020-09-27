package com.commercial.functions;

public class String_to_float {

	public String_to_float() {
		// TODO Auto-generated constructor stub
	}
	
	public float str_float (String s){
		
		float result = 0;
		
		if(s.equals("") || s.equals(" ")){
			
			result = 0;
			
		}
		else{
			
			result = Float.parseFloat(s.replaceAll(",", "."));
			
		}
		
		return result;
		
	}

}
