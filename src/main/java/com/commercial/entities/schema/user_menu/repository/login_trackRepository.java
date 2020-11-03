package com.commercial.entities.schema.user_menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.user_menu.login_track;
import com.commercial.entities.schema.user_menu.users;

public interface login_trackRepository extends JpaRepository<login_track, Long>{
	/*
	@Query(value = " SELECT * FROM static_data.login_track lt "
					+ "WHERE lt.users = (:user) AND date_login = (:date) AND date_logout = '' "
					+ "ORDER BY lt.id DESC LIMIT 1", nativeQuery = true)
	
	@Query( value =   " SELECT * FROM dynamic_data.palette pal "
			+ " WHERE id_cond = (:id_cond) "
			+ " AND id_unite = (:id_unite) "
			+ " ORDER BY pal.id DESC LIMIT 1", nativeQuery = true)*/
	
	//public login_track if_login_exist(@Param("user") users user, @Param("date") String date);
	
	@Query("FROM login_track lt "
			+ "WHERE login_cookie = :cookie ")
	
	public login_track if_login_exist(@Param("cookie") String cookie);
	
}
