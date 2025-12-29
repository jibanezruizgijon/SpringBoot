package com.example.demo.controladores;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.clases.Cuadro;
import com.example.demo.clases.EpocaPintura;
import com.example.demo.clases.Usuario;
import com.example.demo.repository.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class InicioController {

	@Autowired
	CuadroRepository cuadroRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	VotoRepository votoRepository;

	// Comprueba si están las sesiones o cookies creadas para no volver a
	// registrarse
	// Redirige a la página acceso si ya estaba registrado
	@GetMapping({ "/inicio", "/" })
	public String mostrarInicio(HttpSession session,
			@CookieValue(value = "nombre", defaultValue = "") String nombreCookie,
			@CookieValue(value = "email", defaultValue = "") String emailCookie, Model model) {


		// Si ya hay sesión, redirigir a la página acceso
		if (session.getAttribute("nombre") != null && session.getAttribute("email") != null) {
			model.addAttribute("mensajeBienvenida",
					"Bienvenido a la galería de arte, " + session.getAttribute("nombre"));
			
			return "acceso";
		}

		// Si hay cookies creadas guarda los datos en la sesión
		if (!nombreCookie.isEmpty() && !emailCookie.isEmpty()) {
			session.setAttribute("nombre", nombreCookie);
			session.setAttribute("email", emailCookie);
		
			model.addAttribute("mensajeBienvenida", "Bienvenido a la galería de arte, " + nombreCookie);
			return "acceso";
		}

		return "inicio";
	}

	// Recibe el formulario POST
	@PostMapping("/acceso")
	public String guardarRegistro(@RequestParam("nombre") String nombre, @RequestParam("email") String email,
			@RequestParam(value = "recuerda", required = false) String recuerda, HttpSession session,
			HttpServletResponse response, Model model) {

		if (!usuarioRepository.existsByEmail(email)) {
			model.addAttribute("mensajeError", "El Correo introducido no existe");
			return "inicio";
		}

		session.setAttribute("nombre", nombre);
		session.setAttribute("email", email);

		// Comprueba si el checkbox se ha pulsado
		if (recuerda != null) {
			// Se crea la cookie para el nombre
			Cookie cookieNombre = new Cookie("nombre", nombre);
			// Se introduce el tiempo de la Cookie
			cookieNombre.setMaxAge(60 * 60);
			// Añadir la cookie a la respuesta
			response.addCookie(cookieNombre);

			// Se crea la cookie para el email
			Cookie cookieEmail = new Cookie("email", email);
			cookieEmail.setMaxAge(60 * 60);
			response.addCookie(cookieEmail);
		}

		model.addAttribute("mensajeBienvenida", "Bienvenido a la galería de arte, " + session.getAttribute("nombre"));
		return "redirect:/acceso";
	}

	// Mostrar página de acceso
	@GetMapping("/acceso")
	public String mostrarAcceso(HttpSession session, Model model) {
		// En caso de no estar registrado te devuelve a inicio con un mensaje de alerta
		if (session.getAttribute("nombre") == null || session.getAttribute("email") == null) {
			model.addAttribute("mensajeError", "Debes Iniciar Sesión antes de acceder.");
			return "inicio";
		}
		model.addAttribute("mensajeBienvenida", "Bienvenido a la galería de arte, " + session.getAttribute("nombre"));
		return "acceso";
	}

	@GetMapping("/registro")
	public String mostrarRegistro() {
		return "registro";
	}

	@PostMapping("/registro")
	public String registrarUsuario(@RequestParam("nombre") String nombre, @RequestParam("email") String email,
			Model model) {

		if (usuarioRepository.existsByEmail(email)) {
			model.addAttribute("mensajeError", "El Correo introducido ya existe");
			return "registro";
		} else {
			Usuario usuarionuevo = new Usuario(nombre, email);
			usuarioRepository.save(usuarionuevo);
		}
		return "redirect:/inicio";
	}

	@GetMapping("/cierre")
	public String cierreSesion(HttpSession session, HttpServletResponse response) {
		session.removeAttribute("nombre");
		session.removeAttribute("email");

		Cookie cookieNombre = new Cookie("nombre", null);
		cookieNombre.setMaxAge(0);
		response.addCookie(cookieNombre);

		Cookie cookieEmail = new Cookie("email", null);
		cookieEmail.setMaxAge(0);
		response.addCookie(cookieEmail);

		return "inicio";
	}

}
