package com.commercial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.entities.schema.dynamic_data.track_operation_user;
import com.commercial.entities.schema.dynamic_data.repository.track_operation_userRepository;
import com.commercial.entities.schema.user_menu.users;


@Service
public class track_operations {
	
	@Autowired
	track_operation_userRepository trackRepo;
	
	public track_operations() {
		// TODO Auto-generated constructor stub
	}
	
	public void add_track(String entity_operation, String designation_operation, long id_operation, users user) {
		
		//System.out.println("=>class =>"+id_operation+" / user =>"+user.getUsername());
		
		track_operation_user trk =  new track_operation_user(entity_operation, designation_operation, id_operation, user);
		
		//System.out.println(trackRepo.findAll());
		
		trackRepo.save(trk);trackRepo.flush();
		
	}
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		track_operations to = new track_operations();
		
		users user = new users();
		
		to.add_track("sdqsd", "azezae", 2, user);
		
	}
	*/
}
