package com.commercial.functions;

import java.util.ArrayList;
import java.util.Calendar;


public class time_between {
	
	
	
	public time_between() {
		super();
		// TODO Auto-generated constructor stub
	}

//___________________________________________________________________________________________________________________________
	
	public boolean t_between (String a, String b, String x){
		
		boolean resp = false ;
		
		String[] parts = a.split(":");
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		cal1.set(Calendar.MINUTE, Integer.parseInt(parts[1]));

		parts = b.split(":");
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		cal2.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
		
		parts = x.split(":");
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		cal3.set(Calendar.MINUTE, Integer.parseInt(parts[1]));

		// Add 1 day because you mean 00:16:23 the next day
		//cal2.add(Calendar.DATE, 1);

		if (cal1.before(cal3) && cal2.after(cal3)) {
		    //System.out.println("felwast");
		    resp = true ;
		}
		
		
		
		return resp;
	}
	
//________________________________________________________________________________________________________________________
	
	
	public boolean t_between_new_day (String a, String b, String x){
		
		boolean resp = false ;
		
		String[] parts = a.split(":");
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		cal1.set(Calendar.MINUTE, Integer.parseInt(parts[1]));

		parts = b.split(":");
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		cal2.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
		cal2.add(Calendar.DATE, 1);
		
		parts = x.split(":");
		Calendar cal3 = Calendar.getInstance();
		cal3.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		cal3.set(Calendar.MINUTE, Integer.parseInt(parts[1]));

		// Add 1 day because you mean 00:16:23 the next day
		//cal2.add(Calendar.DATE, 1);

		if (cal1.before(cal3) && cal2.after(cal3)) {
		    //System.out.println("felwast");
		    resp = true ;
		}
		
		
		
		return resp;
	}
	
//________________________________________________________________________________________________________________________
		
	
	public String get_time_before (String now){
		
		String result = now;
		int i=0;
		while (i<=22){
			
			if(i<10){
				
				if(t_between("0"+i+":00", "0"+(i+2)+":00", now) == true){
					result = "0"+i+":00";
				}
				
			}
			else{
				
				if(t_between(i+":00", (i+2)+":00", now) == true){
					result = i+":00";
				}
				
			}
			
			i=i+2;
			
		}
		
		
		return result;
	}
	

//________________________________________________________________________________________________________________________
	
public boolean if_grise(String now){
		boolean result = false;
		
		String heure = get_time_before(now);
		
		String[] parts = heure.split(":");
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		cal1.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
		
		cal1.add(Calendar.HOUR, 1);
		
		
				
		String one = cal1.get(Calendar.HOUR_OF_DAY)+":"+cal1.get(Calendar.MINUTE);
		if(cal1.get(Calendar.MINUTE)<10){
			
			if(cal1.get(Calendar.HOUR_OF_DAY)<10){
				one = "0"+cal1.get(Calendar.HOUR_OF_DAY)+":0"+cal1.get(Calendar.MINUTE);
			}
			
			else{
				one = cal1.get(Calendar.HOUR_OF_DAY)+":0"+cal1.get(Calendar.MINUTE);
			}
			
			
		}
		else{
			
			if(cal1.get(Calendar.HOUR_OF_DAY)<10){
				one = "0"+cal1.get(Calendar.HOUR_OF_DAY)+":"+cal1.get(Calendar.MINUTE);
			}
			else{
				one = cal1.get(Calendar.HOUR_OF_DAY)+":"+cal1.get(Calendar.MINUTE);
			}
			
			
		}
		
		
		if(t_between(heure, one, now)==true){
			
			result = false;
			
		}
		
		else{
			
			result = true;
			
		}
		
		
		return result;
	}
	
//________________________________________________________________________________________________________________________

//___________________________________________________________________________________________________________________________

	public boolean if_after_24(String heure,String date){
		
		boolean ret;
		
		convert_calander_string convert = new convert_calander_string();
		
		Calendar cal = Calendar.getInstance();
		
		//System.out.println("---- "+date);
		
		String [] date_s = date.split("/");  
		String heure_s [] = heure.split(":");
		
		//System.out.println("-***** "+Integer.parseInt(date_s[1]));
		
		//System.out.println(" || "+date_s[2]+" || "+date_s[1]+" || "+date_s[0]+" ||// "+heure_s[0]+" || "+heure_s[1]+" || ");
		cal.set(Integer.parseInt(date_s[2]), Integer.parseInt(date_s[1])-1, Integer.parseInt(date_s[0]), Integer.parseInt(heure_s[0]), Integer.parseInt(heure_s[1]));
		//String date_old = cal.toString();
		
		//System.out.println("cal before = "+convert.date_tostring(cal)+" || "+convert.time_tostring(cal));
		
		Calendar cale = Calendar.getInstance();;
		cale.set(Integer.parseInt(date_s[2]), Integer.parseInt(date_s[1])-1, Integer.parseInt(date_s[0]), Integer.parseInt(heure_s[0]), Integer.parseInt(heure_s[1]));
		
		//System.out.println("cal = "+convert.date_tostring(cal)+" || "+convert.time_tostring(cal));
		//System.out.println("cale = "+convert.date_tostring(cale)+" || "+convert.time_tostring(cale));
		
		Calendar today = Calendar.getInstance();
		
		cale.add(Calendar.DATE, 1);
		
		//System.out.println("___________________________________________________");		
		//System.out.println("today = "+convert.date_tostring(today)+" || "+convert.time_tostring(today));
		//System.out.println("cal = "+convert.date_tostring(cal)+" || "+convert.time_tostring(cal));
		//System.out.println("cale = "+convert.date_tostring(cale)+" || "+convert.time_tostring(cale));
		
		
		if(today.before(cale) && today.after(cal)){
			
			ret = false;
			
		}
		else{
			
			ret = true;
			
		}
		
		return ret;
		
		
	}

//___________________________________________________________________________________________________________________________
	public String get_day_before_today(String today){
		
		String ret="";
		
		convert_calander_string convert = new convert_calander_string();
		
		Calendar cal = Calendar.getInstance();
		
		String [] date_s = today.split("/"); 
		
		cal.set(Integer.parseInt(date_s[2]), Integer.parseInt(date_s[1])-1, Integer.parseInt(date_s[0])-1);
		
		ret = convert.date_tostring(cal);
		
		return ret;
		
	}

//___________________________________________________________________________________________________________________________
	
	public String get_day_after_today(String today){
		
		String ret="";
		
		convert_calander_string convert = new convert_calander_string();
		
		Calendar cal = Calendar.getInstance();
		
		String [] date_s = today.split("/"); 
		
		cal.set(Integer.parseInt(date_s[2]), Integer.parseInt(date_s[1])-1, Integer.parseInt(date_s[0])+1);
		
		ret = convert.date_tostring(cal);
		
		return ret;
		
	}

//___________________________________________________________________________________________________________________________
	
	
	public ArrayList<String> get_dates_between_2_dates(String start,String end){
		
		convert_calander_string conv = new convert_calander_string();
		
		ArrayList<String> dates = new ArrayList<String>();
		
		String[] parts = start.split("/");
		
		Calendar cal_start = Calendar.getInstance();
		cal_start.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
		cal_start.set(Calendar.MONTH, Integer.parseInt(parts[1])-1);
		cal_start.set(Calendar.YEAR, Integer.parseInt(parts[2]));
		
		String[] parts1 = end.split("/");
		
		Calendar cal_end = Calendar.getInstance();
		cal_end.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts1[0]));
		cal_end.set(Calendar.MONTH, Integer.parseInt(parts1[1])-1);
		cal_end.set(Calendar.YEAR, Integer.parseInt(parts1[2]));
		
		while(!cal_start.equals(cal_end)){
			
			dates.add(conv.date_tostring(cal_start));
			
			cal_start.add(Calendar.DAY_OF_MONTH, 1);
			
		}
		
		dates.add(conv.date_tostring(cal_end));
		
		
		return dates;
	}

//___________________________________________________________________________________________________________________________
	
	public int get_current_year(){
		
		convert_calander_string convert = new convert_calander_string();
		
		String ret = "";
		
		Calendar cal = Calendar.getInstance();
		Calendar sep = Calendar.getInstance();
		
		get_time_date dd = new get_time_date();
		String date = dd.get_date();
		String [] date_s = date.split("/");
		int year = Integer.parseInt(date_s[2]);
		
		//sep.set(year, 6, 1);
		
		//System.out.println("today ==> "+convert.date_tostring(cal));
		/*
		System.out.println("september ==> "+convert.date_tostring(sep));
		
		if(cal.after(sep)){
			
			ret = year+"/"+(year+1);
			
		}
		else{
			
			ret = (year-1)+"/"+year;
			
		}
		*/
		return year;
		
		
	}
//___________________________________________________________________________________________________________________________

	public ArrayList<String> get_times_10min_before(){
		
		convert_calander_string convert = new convert_calander_string();
		
		ArrayList<String> times = new ArrayList<String>();
		
		Calendar now = Calendar.getInstance();
		
		for(int i=0;i<10;i++) {
			
			now.add(Calendar.MINUTE, -1);
			times.add(convert.time_tostring(now));
			
		}
		
		return times;
		
		
	}
//___________________________________________________________________________________________________________________________
	

	public boolean if_before_2(String date1, String date2){
		
		boolean ret = true;
		
		String[] parts = date1.split("/");
		
		Calendar date_1 = Calendar.getInstance();
		date_1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
		date_1.set(Calendar.MONTH, Integer.parseInt(parts[1])-1);
		date_1.set(Calendar.YEAR, Integer.parseInt(parts[2]));
		
		String[] parts1 = date2.split("/");
		
		Calendar date_2 = Calendar.getInstance();
		date_2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts1[0]));
		date_2.set(Calendar.MONTH, Integer.parseInt(parts1[1])-1);
		date_2.set(Calendar.YEAR, Integer.parseInt(parts1[2]));
		
		if(date_1.after(date_2)) {
			
			ret = false;
			
		}
		
		return ret;
		
		
	}
//___________________________________________________________________________________________________________________________
	
	public boolean if_after_2(String date1, String date2){
		
		boolean ret = true;
		
		String[] parts = date1.split("/");
		
		Calendar date_1 = Calendar.getInstance();
		date_1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[0]));
		date_1.set(Calendar.MONTH, Integer.parseInt(parts[1])-1);
		date_1.set(Calendar.YEAR, Integer.parseInt(parts[2]));
		
		String[] parts1 = date2.split("/");
		
		Calendar date_2 = Calendar.getInstance();
		date_2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts1[0]));
		date_2.set(Calendar.MONTH, Integer.parseInt(parts1[1])-1);
		date_2.set(Calendar.YEAR, Integer.parseInt(parts1[2]));
		
		if(date_1.before(date_2)) {
			
			ret = false;
			
		}
		
		return ret;
		
		
	}
//___________________________________________________________________________________________________________________________
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		time_between t = new time_between();
		//boolean r = t.t_between("10:00", "14:00", "09:00");
		
		//String res = t.get_time_before("15:00");
		//System.out.println(r);
		//System.out.println(res);
		/*
		boolean result = t.if_grise("01:30");
		
		if(result ==true){
			System.out.println("egrizi el 7ala");
		}
		else{ System.out.println("mategrizich el 7ala"); }
		*/
		
		
		//System.out.println(t.get_day_after_today("01/06/2018"));
		/*
		ArrayList<String> l = t.get_dates_between_2_dates("01/07/2018", "10/08/2018");
		
		for(int i=0;i<l.size();i++){
			
			System.out.println(l.get(i));
			
		}
		*/
		//System.out.println(t.get_times_10min_before());
		//System.out.println("year == "+t.get_current_year());
		
		System.out.println(t.if_after_2("12/09/2019", "17/09/2019"));
		
	}
	
	

}
