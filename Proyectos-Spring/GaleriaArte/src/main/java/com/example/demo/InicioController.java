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
public class InicioController {

	//Comprueba si están las sesiones o cookies creadas para no volver a registrarse 
	//Redirige a la página acceso si ya estaba registrado
	@GetMapping("/")
	public String mostrarInicio(HttpSession session,
	        @CookieValue(value = "nombre", defaultValue = "") String nombreCookie,
	        @CookieValue(value = "contrasena", defaultValue = "") String contraseñaCookie,
	        Model model) {

	    // Si ya hay sesión, redirigir a la página acceso
	    if (session.getAttribute("nombre") != null && session.getAttribute("contraseña") != null) {
	        model.addAttribute("mensajeBienvenida", "Bienvenido a la galería de arte, " + session.getAttribute("nombre"));
	        
	        //En caso de que la galeria no esté creada se inicializa
	        if (session.getAttribute("galeria") == null) {
	            inicializarGaleria(session);
	        }
	        // Guarda las sesión en el modelo 
	        model.addAttribute("galeria", session.getAttribute("galeria"));
	        return "acceso";
	    }

	    // Si hay cookies creadas guarda los datos en la sesión 
	    if (!nombreCookie.isEmpty() && !contraseñaCookie.isEmpty()) {
	        session.setAttribute("nombre", nombreCookie);
	        session.setAttribute("contraseña", contraseñaCookie);
	        
	      //En caso de que la galeria no esté creada se inicializa
	        if (session.getAttribute("galeria") == null) {
	            inicializarGaleria(session);
	        } 
	        
	        model.addAttribute("galeria", session.getAttribute("galeria"));
	        model.addAttribute("mensajeBienvenida", "Bienvenido a la galería de arte, " + nombreCookie);
	        return "acceso";
	    }

	    return "inicio";
	}
	

	// Recibe el formulario POST
	@PostMapping("/acceso")
	public String guardarRegistro(@RequestParam("nombre") String nombre, @RequestParam("contraseña") String contraseña,
			@RequestParam(value = "recuerda", required = false) String recuerda, HttpSession session,
			HttpServletResponse response, Model model) {
		session.setAttribute("nombre", nombre);
		session.setAttribute("contraseña", contraseña);

		// Comprueba si el checkbox se ha pulsado
		if (recuerda != null) {
			// Se crea la cookie para el nombre
			Cookie cookieNombre = new Cookie("nombre", nombre);
			// Se introduce el tiempo de la Cookie
			cookieNombre.setMaxAge(60 * 60);
			// Añadir la cookie a la respuesta
			response.addCookie(cookieNombre);

			// Se crea la cookie para la contraseña
			Cookie cookieContraseña = new Cookie("contrasena", contraseña);
			cookieContraseña.setMaxAge(60 * 60);
			response.addCookie(cookieContraseña);
		}
		//Mete el valor de la sesion nombre en un String para enviarlo a la página en el mensaje de bienvenida
		String nombreUsuario = (String) session.getAttribute("nombre");
		model.addAttribute("mensajeBienvenida", "Bienvenido a la galería de arte, " + nombreUsuario.trim());

		return "redirect:/acceso";
	}

	// Mostrar página de acceso
	@GetMapping("/acceso")
    public String mostrarAcceso(HttpSession session, Model model) {
		// En caso de no estar registrado te devuelve a inicio con un mensaje de alerta
        if (session.getAttribute("nombre") == null || session.getAttribute("contraseña") == null) {
            model.addAttribute("mensajeError", "Debes registrarte antes de acceder.");
            return "inicio";
        }

        //En caso de no estar creada la sesión de galeria se llama al método para crearla
        if (session.getAttribute("galeria") == null) {
        inicializarGaleria(session);
        }
        
        model.addAttribute("galeria", session.getAttribute("galeria"));
        model.addAttribute("mensajeBienvenida", "Bienvenido a la galería de arte, " + session.getAttribute("nombre"));
        return "acceso";
    }
	
	
	// Método para crear la galería en sesión
    private void inicializarGaleria(HttpSession session) {
        if (session.getAttribute("galeria") == null) {
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
            session.setAttribute("galeria", galeria);
        }
    }
}
