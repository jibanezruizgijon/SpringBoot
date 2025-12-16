package com.example.demo;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class ControladorTablon {
	private ArrayList<Anuncio> anuncios = new ArrayList<>();

	@GetMapping("/paginaPrincipal")
	public String paginaPrincipal(Model model, HttpSession session) {
		model.addAttribute("anuncios", anuncios);

		// Para comprobar si el anuncio se ha mostrado una vez
		if (session.getAttribute("bienvenidaMostrada") == null) {
			model.addAttribute("mensajeBienvenida", "Bienvenido!");
			session.setAttribute("bienvenidaMostrada", true);
		}
		return "paginaPrincipal";
	}

	@GetMapping("/añadirAnuncio")
	public String formulario(Model model, HttpSession session) {
		Anuncio anuncio = new Anuncio();
		String nombreGuardado = (String) session.getAttribute("nombreUsuario");
		if (nombreGuardado != null) {
			anuncio.setNombre(nombreGuardado);
		}
		model.addAttribute("anuncio", anuncio);

		return "añadirAnuncio";
	}

	@PostMapping("/anuncioGuardado") // petición de dirección web
	public String metodo(@ModelAttribute Anuncio anuncio, HttpSession session, Model model) {
	
		anuncios.add(anuncio);
		session.setAttribute("nombreUsuario", anuncio.getNombre());
		model.addAttribute("anuncio", anuncio);
		return "anuncioGuardado"; // A que HTML me voy??
	}
	
	
	@GetMapping("/verAnuncio/{index}")
	public String VerAnuncio(@PathVariable int index, Model model) {
		 model.addAttribute("anuncio", anuncios.get(index));
		return "verAnuncio";
	}
}
