package com.commercial.functions;

public class mille_separator {

	/**
	 * @param args
	 */
	
	public mille_separator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public static String seperate (int x){
		
		String s = Integer.toString(x);
		
		int leng = s.length();
		int ling = leng;
		
		//System.out.println(" leng == "+leng);
		
		int k = leng/3;
		
		float p = (float)leng/3;
		
		float test = p%1;
		
		//System.out.println(" p == "+p%1);
		String res="";
		
		if(test==0){
			
			for(int i=0;i<k;i++){
				
				if(i==k-1){
					
					res = s.substring(leng-3, leng)+res;
					leng = leng-3;
					
				}
				else{
					
					res = " "+s.substring(leng-3, leng)+res;
					leng = leng-3;
					
				}
				
				
			}
			
		}
		
		else{
			
			
				for(int i=0;i<k;i++){
					
					if(i==k-1){
						
						res = " "+s.substring(leng-3, leng)+res;
						leng = leng-3;
						
					}
					else{
						
						res = " "+s.substring(leng-3, leng)+res;
						leng = leng-3;
						
					}
					
					
				}
			
		}
		
		
		
		String res1 = s.substring(0, (ling-(3*k)))+res;
		res1 = res1+",00";
		return res1;
		
	}

	
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("'"+seperate(1000)+"'");

	}
	*/
}
