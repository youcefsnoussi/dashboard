package com.commercial.restController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.profoma_cmd_bl_fact.repository.facture_detailRepository;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.functions.convert_string_to_date_util;
import com.commercial.functions.get_time_date;

@RestController
@SessionAttributes("user")

public class StatistiqueRestController {

	public StatistiqueRestController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	facture_detailRepository fact_dRepo;
	
	@RequestMapping(value="/ajax_rapport_ultra_detailler")
	public List<Object[]> rapport_ultra_detailler(HttpServletRequest request,
						 @RequestParam(value="start", defaultValue="0") String start,
						 @RequestParam(value="end", defaultValue="0") String end,
						 @SessionAttribute("user") users user,
						 Model model){
		/*
		LocalTime start_time = LocalTime.now();
		
		System.out.println("start ------->"+start_time);
		*/
		convert_string_to_date_util conv = new convert_string_to_date_util();
		
		get_time_date gtd = new get_time_date();
		
		List <Object[]> list = new ArrayList<Object[]>();
		
		String ret_start = (start.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : start;
		
		String ret_end = (end.equals("0")) ? conv.convertion_MyDate_to_InputDate(gtd.get_date()) : end;
		
		list = fact_dRepo.RapoortUltraDetailler(ret_start, ret_end) ;	
		
		/*
		LocalTime end_time = LocalTime.now();
		
		System.out.println("end ------->"+end_time+" | diff ---> "+ChronoUnit.MINUTES.between(start_time, end_time)+":"+
					ChronoUnit.SECONDS.between(start_time, end_time));
		*/
		return list;		
	}
	
}
