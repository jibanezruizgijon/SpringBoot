package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
	// Mostrar página, leyendo el nombre de la sesión si existe
	@GetMapping("/mipagina")
	public String mostrarPagina(HttpSession session, Model model) {
		String nombre = (String) session.getAttribute("miNombre");
		if (nombre == null) {
			nombre = "Invitado";
		}
		model.addAttribute("nombreUsuario", nombre);
		return "mipagina";
	}

	@GetMapping("/guardar-nombre")
	public String mostrarFormulario() {
		return "guardar-nombre";
	}

	@PostMapping("/guardar-nombre")
	public String guardarNombre(@RequestParam("miNombre") String miNombre, HttpSession session) {
		session.setAttribute("miNombre", miNombre);
		return "redirect:/mipagina";
	}
}
