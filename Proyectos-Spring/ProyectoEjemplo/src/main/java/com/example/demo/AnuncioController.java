package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnuncioController {
	
	@GetMapping("/nuevoAnuncio") 
	public String metodo() {
		return "nuevoAnuncio";
	}
	
	@GetMapping("/anuncio") // petición de dirección web
	public String metodo(
			@RequestParam("nombre") String nombre,
			@RequestParam("asunto") String asunto,
			@RequestParam("comentario") String comentario,
			Model model) {
		model.addAttribute("nombre", nombre);
		model.addAttribute("asunto", asunto);
		model.addAttribute("comentario", comentario);
		return "anuncio"; // A que HTML me voy??
	}
}
