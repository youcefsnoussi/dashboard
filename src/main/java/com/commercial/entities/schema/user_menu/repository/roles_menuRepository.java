package com.commercial.entities.schema.user_menu.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercial.entities.schema.user_menu.menu;
import com.commercial.entities.schema.user_menu.roles;
import com.commercial.entities.schema.user_menu.roles_menu;
import com.commercial.entities.schema.user_menu.sub_menu;



public interface roles_menuRepository extends JpaRepository<roles_menu, Long>{
	/*
	@Query("SELECT me.nom_menu, sub.nom_submenu, sub.url_submenu, sub.ids_banned "
			+ " FROM roles_menu us_m, menu me, sub_menu sub "
			+ " WHERE us_m.id_user = (:id_user) "
			+ " AND us_m.id_menu = me.id "
			+ " AND us_m.id_submenu = sub.id "
			+ " ORDER BY us_m.id_menu ASC ")
	public List<Object>  findbyid_user(@Param("id_user") long string);
	*/
	
	@Query("SELECT men.id, men.nom_menu "
			
			+ " FROM menu men, sub_menu sub, roles_menu rl_m "
			
			+ " WHERE men.id = sub.menu "
			+ " AND sub.id = rl_m.submenu "
			
			+ " AND rl_m.role = :id_role "
			
			+ "GROUP BY men.nom_menu, men.id"
			+ " ORDER BY men.ord ASC ")
	
	public List<Object[]>  get_menu_by_role(@Param("id_role") roles role);
	
	/*
	@Query("SELECT men.nom_menu, men.id "
			
			+ " FROM menu men, roles_menu rl_m, sub_menu sub "
			
			+ " WHERE sub.menu = men.id "
			+ " AND rl_m.submenu = sub.id "
			
			+ " AND rl_m.role = :id_role "
			
			+ " ORDER BY men.id ASC ")
	
	public List<menu>  get_menu_by_role(@Param("id_role") roles role);
	 */
	@Query("SELECT sub.nom_submenu, sub.url_submenu, sub.ids_banned, men.id, sub.icone "
			
			+ " FROM sub_menu sub, roles_menu rl_m, menu men "
			
			+ " WHERE rl_m.submenu = sub.id "
			+ " AND sub.menu = men.id "
			
			+ " AND rl_m.role = (:id_role) "
			+ " AND sub.menu = (:id_menu) "
			
			+ " ORDER BY sub.ord ASC ")
	
	public List<Object[]>  get_submenu_by_user_role(@Param("id_role") roles id_role,@Param("id_menu") menu id_menu);
	
}
