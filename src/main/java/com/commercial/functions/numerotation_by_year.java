package com.commercial.functions;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	public String return_num_BonLivraisonFacture (String num, Long unite){ //assm la rebrique numero 
		
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
					
					if(year_encours.equals(y) /*&& month_encours.equals(m)*/){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = unite+"BL"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = unite+"BL"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = unite+"BL"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = unite+"BL"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = unite+"BL"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = unite+"BL"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = unite+"BL"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = unite+"BL"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = unite+"BL"+y+"/"+m+"/00000001";
				
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
					
					if(year_encours.equals(y) /*&& month_encours.equals(m)*/){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = "BC"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = "BC"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = "BC"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = "BC"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = "BC"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = "BC"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = "BC"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = "BC"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = "BC"+y+"/"+m+"/00000001";
				
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
					year_encours = num.substring(4, 6);
					
					month_encours= num.substring(7, 9);
					
					System.out.println("year_encours == "+year_encours);
					
					System.out.println("month_encours == "+month_encours);
					
					if(year_encours.equals(y) /*&& month_encours.equals(m)*/){
						
						String n = resnum.substring(11);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = long1+"FCT"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = long1+"FCT"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = long1+"FCT"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = long1+"FCT"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = long1+"FCT"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = long1+"FCT"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = long1+"FCT"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = long1+"FCT"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = long1+"FCT"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	//--------------------------------
	
	public String return_num_facture_avoir (String num, Long long1){ //assm la rebrique numero 
		
		
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
					
					if(year_encours.equals(y) /*&& month_encours.equals(m)*/){
						
						String n = resnum.substring(13);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = long1+"FCTAV"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = long1+"FCTAV"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = long1+"FCTAV"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = long1+"FCTAV"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = long1+"FCTAV"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = long1+"FCTAV"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = long1+"FCTAV"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = long1+"FCTAV"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = long1+"FCTAV"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	//--------------------------------
	
	public String return_num_facture_ristourne (String num, Long long1){ //assm la rebrique numero 
		
		
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
					
					if(year_encours.equals(y) /*&& month_encours.equals(m)*/){
						
						String n = resnum.substring(13);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = long1+"FCTRS"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = long1+"FCTRS"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = long1+"FCTRS"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = long1+"FCTRS"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = long1+"FCTRS"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = long1+"FCTRS"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = long1+"FCTRS"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = long1+"FCTRS"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = long1+"FCTRS"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	//------------------------------------------------------------------------------------------------------------
	
	public String return_num_BonTransfert (String num, Long unite){ //assm la rebrique numero 
		
		
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
					
					if(year_encours.equals(y) /*&& month_encours.equals(m)*/){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = unite+"BT"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = unite+"BT"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = unite+"BT"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = unite+"BT"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = unite+"BT"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = unite+"BT"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = unite+"BT"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = unite+"BT"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = unite+"BT"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	//------------------------------------------------------------------------------------------------------------
	
	//------------------------------------------------------------------------------------------------------------
	
	public String return_num_BonTransfertInterne (String num, Long unite){ //assm la rebrique numero 
		
		
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
					
					if(year_encours.equals(y) /*&& month_encours.equals(m)*/){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = unite+"BTI"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = unite+"BTI"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = unite+"BTI"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = unite+"BTI"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = unite+"BTI"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = unite+"BTI"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = unite+"BTI"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = unite+"BTI"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = unite+"BTI"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	//------------------------------------------------------------------------------------------------------------
	
	public String return_num_BonSortie (String num, Long unite){ //assm la rebrique numero 
		
		
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
					
					if(year_encours.equals(y) /*&& month_encours.equals(m)*/){
						
						String n = resnum.substring(10);
						System.out.println(n);
						int nn = Integer.parseInt(n);
						System.out.println(nn);
						nn++;
							if( nn < 10 ){ numero = unite+"BS"+y+"/"+m+"/0000000"+nn;} else
							if( nn < 100 ){ numero = unite+"BS"+y+"/"+m+"/000000"+nn;} else	
							if( nn < 1000 ){ numero = unite+"BS"+y+"/"+m+"/00000"+nn;} else
							if( nn < 10000 ){ numero = unite+"BS"+y+"/"+m+"/0000"+nn;} else
							if( nn < 100000 ){ numero = unite+"BS"+y+"/"+m+"/000"+nn;} else
							if( nn < 1000000 ){ numero = unite+"BS"+y+"/"+m+"/00"+nn;}else
							if( nn < 10000000 ){ numero = unite+"BS"+y+"/"+m+"/0"+nn;}
					}
					
					else{
						
						numero = unite+"BS"+y+"/"+m+"/00000001"; 
						
					}
			}
			
			else{
				
				numero = unite+"BS"+y+"/"+m+"/00000001";
				
			}
			
		
		return numero;
	}
	
	
	
	// static void main
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		numerotation_by_year numero = new numerotation_by_year();
		/*
		System.out.println("Result BLF ->"+numero.return_num_BonLivraisonFacture("1BL21/04/00000001", (long) 3));
		System.out.println("--------------------");
		
		System.out.println("Result BL ->"+numero.return_num_BonLivraison("BL_21/04/00000001"));
		System.out.println("--------------------");
		
		System.out.println("Result FACT ->"+numero.return_num_facture("1FCT21/04/00000001", (long) 3));
		System.out.println("--------------------");
		*/
		
		System.out.println("Result BS ->"+numero.return_num_BonSortie("1BS21/04/00000001", (long) 1));
		System.out.println("--------------------");
		
	}
	
	
}


