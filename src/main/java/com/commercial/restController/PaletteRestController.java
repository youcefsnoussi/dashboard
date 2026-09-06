package com.commercial.restController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.commercial.entities.schema.client.gestion_palette;
import com.commercial.entities.schema.user_menu.users;
import com.commercial.services.PaletteService;

@CrossOrigin()
@RestController
@SessionAttributes("admin")
public class PaletteRestController {
	
	@Autowired
	PaletteService ps;
	
	public PaletteRestController() {
	}
	
	/**
	 * Returns list of clients with palette balance data (since 2026-01-01)
	 */
	@GetMapping(value="/getClientsPaletteData")
	public List<Map<String, Object>> getClientsPaletteData() {
		return ps.getClientsPaletteData();
	}
	
	/**
	 * Returns palette history for a specific RC (outgoing + returns)
	 */
	@GetMapping(value="/getPaletteHistory")
	public List<Map<String, Object>> getPaletteHistory(@RequestParam("numero_rc") String numeroRc) {
		return ps.getPaletteHistory(numeroRc);
	}
	
	/**
	 * Record a palette return
	 */
	@PostMapping(value="/retourPalette")
	public gestion_palette retourPalette(
			@RequestParam("client_rc") String clientRc,
			@RequestParam("client_name") String clientName,
			@RequestParam("quantity") double quantity,
			@SessionAttribute("user") users user) {
		
		return ps.recordReturn(clientRc, clientName, quantity, user.getId());
	}
	
}
