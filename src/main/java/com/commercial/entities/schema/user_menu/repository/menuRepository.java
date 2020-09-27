package com.commercial.entities.schema.user_menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercial.entities.schema.user_menu.menu;

public interface menuRepository extends JpaRepository<menu, Long>{
	
}
