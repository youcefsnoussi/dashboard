package com.commercial.entities.schema.dynamic_data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.dynamic_data.track_operation_user;

public interface track_operation_userRepository extends JpaRepository<track_operation_user, Long>{
	
	@Query( " SELECT date  "
			
		  +	" FROM track_operation_user track "
			
		  + " WHERE designation = :des AND id_operation = :id_rc ")
	
	public String get_date_creation_rc(@Param("id_rc") Long id_rc, @Param("des") String designation);
	
}
