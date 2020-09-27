package com.commercial.entities.schema.user_menu.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.user_menu.users;



public interface userRepository extends JpaRepository<users, Long>{
	
	@Query("SELECT u.id FROM users u WHERE u.username = (:username)")
	public long  find_user_id_byusername(@Param("username") String username);
	
	@Query(" FROM users u WHERE u.username = (:username)")
	public users  find_user_byusername(@Param("username") String username);
	
	@Query("SELECT u.image FROM users u WHERE u.id = (:id_user)")
	public String  find_user_image_byid(@Param("id_user") long id_user);
	
}
