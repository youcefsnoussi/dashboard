package com.commercial.functions;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;




public class numerotation_by_year {

	public numerotation_by_year() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	public String return_num (String num){ //assm la rebrique numero 
		
		
		String numero ="",resnum="",year_encours="",month_encours="";
		
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String y = df.format(Calendar.getInstance().getTime());
		
		DateFormat dff = new SimpleDateFormat("MM"); // Just the year, with 2 digits
		String m = dff.format(Calendar.getInstance().getTime());
		
		
		
			if(!num.equals("")){
				
					resnum= num;
					year_encours = num.substring(0, 2);
					
					month_encours= num.substring(3, 5);
					
					System.out.println("year_encours == "+year_encours);
					
					System.out.println("month_encours == "+month_encours);
					
					if(year_encours.equals(y) && month_encours.equals(m)){
						
						String n = resnum.substring(6);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	public String return_num_proforma (String num){ //assm la rebrique numero 
		
		
		String numero ="",resnum="",year_encours="",month_encours="";
		
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String y = df.format(Calendar.getInstance().getTime());
		
		DateFormat dff = new SimpleDateFormat("MM"); // Just the year, with 2 digits
		String m = dff.format(Calendar.getInstance().getTime());
		
		
		
			if(!num.equals("")){
				
					resnum= num;
					year_encours = num.substring(4, 6);
					
					month_encours= num.substring(7, 9);
					
					System.out.println("year_encours == "+year_encours);
					
					System.out.println("month_encours == "+month_encours);
					
					if(year_encours.equals(y) && month_encours.equals(m)){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = "PRF_"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = "PRF_"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = "PRF_"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = "PRF_"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = "PRF_"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = "PRF_"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = "PRF_"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = "PRF_"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = "PRF_"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	public String return_num_commande (String num){ //assm la rebrique numero 
		
		
		String numero ="",resnum="",year_encours="",month_encours="";
		
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String y = df.format(Calendar.getInstance().getTime());
		
		DateFormat dff = new SimpleDateFormat("MM"); // Just the year, with 2 digits
		String m = dff.format(Calendar.getInstance().getTime());
		
		
		
			if(!num.equals("")){
				
					resnum= num;
					year_encours = num.substring(4, 6);
					
					month_encours= num.substring(7, 9);
					
					System.out.println("year_encours == "+year_encours);
					
					System.out.println("month_encours == "+month_encours);
					
					if(year_encours.equals(y) && month_encours.equals(m)){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = "CMD_"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = "CMD_"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = "CMD_"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = "CMD_"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = "CMD_"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = "CMD_"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = "CMD_"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = "CMD_"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = "CMD_"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	//------------------------------------------------------------------------------------------------------------
	
	public String return_num_BonLivraison (String num){ //assm la rebrique numero 
		
		
		String numero ="",resnum="",year_encours="",month_encours="";
		
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String y = df.format(Calendar.getInstance().getTime());
		
		DateFormat dff = new SimpleDateFormat("MM"); // Just the year, with 2 digits
		String m = dff.format(Calendar.getInstance().getTime());
		
		
		
			if(!num.equals("")){
				
					resnum= num;
					year_encours = num.substring(3, 5);
					
					month_encours= num.substring(6, 8);
					
					System.out.println("year_encours == "+year_encours);
					
					System.out.println("month_encours == "+month_encours);
					
					if(year_encours.equals(y) && month_encours.equals(m)){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = "BL_"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = "BL_"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = "BL_"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = "BL_"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = "BL_"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = "BL_"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = "BL_"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = "BL_"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = "BL_"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	//------------------------------------------------------------------------------------------------------------
	
		public String return_num_BonCommande (String num){ //assm la rebrique numero 
			
			
			String numero ="",resnum="",year_encours="",month_encours="";
			
			DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
			String y = df.format(Calendar.getInstance().getTime());
			
			DateFormat dff = new SimpleDateFormat("MM"); // Just the year, with 2 digits
			String m = dff.format(Calendar.getInstance().getTime());
			
			
			
				if(!num.equals("")){
					
						resnum= num;
						year_encours = num.substring(3, 5);
						
						month_encours= num.substring(6, 8);
						
						System.out.println("year_encours == "+year_encours);
						
						System.out.println("month_encours == "+month_encours);
						
						if(year_encours.equals(y) && month_encours.equals(m)){
							
							String n = resnum.substring(10);
							System.out.println(n);
							int nn = Integer.parseInt(n);
							System.out.println(nn);
							nn++;
								if( nn < 10 ){ numero = "BC_"+y+"/"+m+"/0000000"+nn;} else
								if( nn < 100 ){ numero = "BC_"+y+"/"+m+"/000000"+nn;} else	
								if( nn < 1000 ){ numero = "BC_"+y+"/"+m+"/00000"+nn;} else
								if( nn < 10000 ){ numero = "BC_"+y+"/"+m+"/0000"+nn;} else
								if( nn < 100000 ){ numero = "BC_"+y+"/"+m+"/000"+nn;} else
								if( nn < 1000000 ){ numero = "BC_"+y+"/"+m+"/00"+nn;}else
								if( nn < 10000000 ){ numero = "BC_"+y+"/"+m+"/0"+nn;}
						}
						
						else{
							
							numero = "BL_"+y+"/"+m+"/00000001"; 
							
						}
				}
				
				else{
					
					numero = "BL_"+y+"/"+m+"/00000001";
					
				}
				
			
			return numero;
		}
	
	//-------------------------------------------------------------------------------------	
		
	public String return_num_facture (String num,Long long1){ //assm la rebrique numero 
		
		
		String numero ="",resnum="",year_encours="",month_encours="";
		
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String y = df.format(Calendar.getInstance().getTime());
		
		DateFormat dff = new SimpleDateFormat("MM"); // Just the year, with 2 digits
		String m = dff.format(Calendar.getInstance().getTime());
		
		
		
			if(!num.equals("")){
				
					resnum= num;
					year_encours = num.substring(5, 7);
					
					month_encours= num.substring(8, 10);
					
					System.out.println("year_encours == "+year_encours);
					
					System.out.println("month_encours == "+month_encours);
					
					if(year_encours.equals(y) && month_encours.equals(m)){
						
						String n = resnum.substring(11);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = "FCT_"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = "FCT_"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = "FCT_"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = "FCT_"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = "FCT_"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = "FCT_"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = "FCT_"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = long1+"FCT_"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = long1+"FCT_"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	//--------------------------------
	
	public String return_num_facture_avoir (String num){ //assm la rebrique numero 
		
		
		String numero ="",resnum="",year_encours="",month_encours="";
		
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String y = df.format(Calendar.getInstance().getTime());
		
		DateFormat dff = new SimpleDateFormat("MM"); // Just the year, with 2 digits
		String m = dff.format(Calendar.getInstance().getTime());
		
		
		
			if(!num.equals("")){
				
					resnum= num;
					year_encours = num.substring(6, 8);
					
					month_encours= num.substring(9, 11);
					
					System.out.println("year_encours == "+year_encours);
					
					System.out.println("month_encours == "+month_encours);
					
					if(year_encours.equals(y) && month_encours.equals(m)){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = "FCTAV_"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = "FCTAV_"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = "FCTAV_"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = "FCTAV_"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = "FCTAV_"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = "FCTAV_"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = "FCTAV_"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = "FCTAV_"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = "FCTAV_"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	// static void main
	
	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		numerotation_by_year numero = new numerotation_by_year();
		
		System.out.println(numero.return_num_commande("FCT_19/06/00024174"));

	}
	*/
	
}


