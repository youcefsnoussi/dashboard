package com.commercial.webController.stock;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.user_menu.users;

@Controller
@SessionAttributes("user")
public class gestionPaletteController {

	public gestionPaletteController() {
	}
	
	@RequestMapping(value="/gestion_palette")
	public String gestionPalette(HttpServletRequest request,
			@SessionAttribute("user") users user,
			Model model) {
		
		return "stock/gestion_palette";
	}

}
