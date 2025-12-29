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

		// En caso de que la galeria no esté creada se inicializa
		inicializarGaleria();

		// Si ya hay sesión, redirigir a la página acceso
		if (session.getAttribute("nombre") != null && session.getAttribute("email") != null) {
			model.addAttribute("mensajeBienvenida",
					"Bienvenido a la galería de arte, " + session.getAttribute("nombre"));
			// En caso de que la galeria no esté creada se inicializa
			inicializarGaleria();
			return "acceso";
		}

		// Si hay cookies creadas guarda los datos en la sesión
		if (!nombreCookie.isEmpty() && !emailCookie.isEmpty()) {
			session.setAttribute("nombre", nombreCookie);
			session.setAttribute("email", emailCookie);
			// En caso de que la galeria no esté creada se inicializa
			inicializarGaleria();
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

	// Método para crear la galería en sesión
	private void inicializarGaleria() {
		if (cuadroRepository.count() == 0) {
			List<Cuadro> galeria = new ArrayList<>();
			galeria.add(new Cuadro("Impresión, sol naciente", "Claude Monet", EpocaPintura.Impresionismo,
					"/imagenesLogos/img1.jpg"));
			galeria.add(
					new Cuadro("Las Meninas", "Diego de Velázquez", EpocaPintura.Barroco, "imagenesLogos/img2.jpg"));
			galeria.add(new Cuadro("La Capilla Sixtina", "Miguel Ángel", EpocaPintura.Renacimiento,
					"imagenesLogos/img3.jpg"));
			galeria.add(new Cuadro("El Guernica", "Pablo Picasso", EpocaPintura.Cubismo, "imagenesLogos/img4.jpg"));
			galeria.add(new Cuadro("La noche estrellada", "Vincent van Gogh", EpocaPintura.Postimpresionismo,
					"imagenesLogos/img5.jpg"));
			galeria.add(new Cuadro("El nacimiento de Venus", "Botticelli", EpocaPintura.Renacimiento,
					"imagenesLogos/img6.jpg"));
			galeria.add(new Cuadro("El Jardín de las delicias", "El Bosco", EpocaPintura.Renacimiento,
					"imagenesLogos/img7.jpg"));
			galeria.add(new Cuadro("La joven de la perla", "Johannes Vermeer", EpocaPintura.Barroco,
					"imagenesLogos/img8.jpg"));
			galeria.add(new Cuadro("Composición en rojo, amarillo, azul, blanco y negro", "Piet Mondrian",
					EpocaPintura.Neoplasticismo, "imagenesLogos/img9.jpg"));
			galeria.add(new Cuadro("El grito", "De Munch", EpocaPintura.Expresionismo, "imagenesLogos/img10.png"));
			galeria.add(new Cuadro("El entierro del conde de Orgaz", "El Greco", EpocaPintura.Manierismo,
					"imagenesLogos/img11.jpg"));
			galeria.add(
					new Cuadro("El gran siglo", "René Magritte", EpocaPintura.Surrealismo, "imagenesLogos/img12.jpg"));
			galeria.add(new Cuadro("La libertad guiando al pueblo", "Eugène Delacroix", EpocaPintura.Romanticismo,
					"imagenesLogos/img13.jpg"));
			galeria.add(new Cuadro("La Gran ola de Kanagaza", "Katsushika Hokusai", EpocaPintura.Arte_oriental,
					"imagenesLogos/img14.jpg"));
			galeria.add(new Cuadro("La persistencia de la memoria", "Salvador Dalí", EpocaPintura.Surrealismo,
					"imagenesLogos/img15.jpg"));
			galeria.add(new Cuadro("La última cena", "Leonardo da Vinci", EpocaPintura.Renacimiento,
					"imagenesLogos/img16.jpg"));
			galeria.add(new Cuadro("Los girasoles", "Vincent Van Gogh", EpocaPintura.Postimpresionismo,
					"imagenesLogos/img17.jpg"));
			galeria.add(new Cuadro("Saturno devorando a su hijo", "Goya", EpocaPintura.Romanticismo,
					"imagenesLogos/img18.jpg"));
			galeria.add(new Cuadro("Autorretrato", "Vincent Van Gogh", EpocaPintura.Postimpresionismo,
					"imagenesLogos/img19.jpg"));
			galeria.add(new Cuadro("La lechera", "Vermeer", EpocaPintura.Barroco, "imagenesLogos/img20.jpg"));
			galeria.add(new Cuadro("La ciudad", "de Fernand Léger", EpocaPintura.Cubismo, "imagenesLogos/img22.jpg"));
			galeria.add(new Cuadro("El columpio", "Fragonard", EpocaPintura.Rococo, "imagenesLogos/img23.jpg"));
			galeria.add(new Cuadro("Paseo a orillas del mar", "de Joaquín Sorolla", EpocaPintura.Impresionismo,
					"imagenesLogos/img24.jpg"));
			cuadroRepository.saveAll(galeria);
		}
	}
}
