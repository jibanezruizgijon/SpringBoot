package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LibroController {

	// Inicio
	@GetMapping("/login")
	public String mostrarInicio() {

		return "login";
	}

	// Cuando entra en la página para crear un nuevo libro
	@GetMapping("/nuevo")
	public String nuevoLibro() {
		return "nuevo";
	}

	// Recibe el formulario de los datos del libro
	@PostMapping("/nuevo")
	public String crearLibro(@RequestParam("titulo") String titulo, @RequestParam("autor") String autor,
			@RequestParam("precio") int precio, @RequestParam("isbn") int isbn, HttpSession session) {

		// En caso de que no se introduzca ningun autor se rellenará con el del autor
		// registrado
		String nombreUsuario = "";
		if (autor == null) {
			nombreUsuario = (String) session.getAttribute("autor");
		} else {
			nombreUsuario = autor;
		}

		// Mete la sesión en el arrayLsit
		List<Libro> libros = (List<Libro>) session.getAttribute("libros");
		libros.add(new Libro(titulo, nombreUsuario, precio, isbn));
		session.setAttribute("libros", libros);

		return "libros";
	}

	@GetMapping("/logout")
	public String cerrarSesion(HttpSession session) {
		session.setAttribute("autor", "");
		session.setAttribute("libros", "");

		return "login";
	}

	@GetMapping("/libros")
	public String volver() {

		return "libros";
	}

	// Recibe el formulario POST
	@PostMapping("/registro")
	public String guardarRegistro(@RequestParam("autor") String autor, HttpSession session,
			HttpServletResponse response, Model model) {
		String nombreAutor = (String) session.getAttribute("autor");
		session.setAttribute("autor", autor);

		// Llama al método para crear los libros y guardarlos en sesion
		inicializarLibros(session);

		// Mete la sesión en el arrayLsit
		List<Libro> libros = (List<Libro>) session.getAttribute("libros");
		model.addAttribute("libros", libros);
		/*
		 * for (Libro libro: libros ) { if (nombreAutor == libro.getAutor()) {
		 * 
		 * 
		 * return "libro"; } }
		 */
		Integer nLibros = 0;

		nLibros = libros.size();

		model.addAttribute("nLibros", nLibros);
		// model.addAttribute("mensajeError", "Autor no registrado");
		return "libros";
	}

	private void inicializarLibros(HttpSession session) {
		if (session.getAttribute("libros") == null) {
			List<Libro> libros = new ArrayList<>();
			libros.add(new Libro("La sombra del viento", "Zafón", 16, 1111));
			libros.add(new Libro("Don Quijote de la Mancha", "Cervantes", 18, 1112));
			libros.add(new Libro("El juego del ángel", "Lorca", 16, 1113));
			session.setAttribute("libros", libros);
		}
	}
}
