package com.example.demo.controladores;

import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.clases.Cuadro;
import com.example.demo.clases.Usuario;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class galeriaController {

	@Autowired
	CuadroRepository cuadroRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	VotoRepository votoRepository;

	@Autowired
	VotoService votoService;

	@GetMapping("/galeria")
	public String mostrarGaleria(HttpSession session, Model model) {
		// En caso de no estar registrado te devuelve a inicio con un mensaje de alerta
		if (session.getAttribute("nombre") == null || session.getAttribute("email") == null) {
			model.addAttribute("mensajeError", "Debes registrarte antes de acceder.");
			return "inicio";
		}

		List<Cuadro> galeria = cuadroRepository.findAll();
		;
		if (galeria == null) {
			return "redirect:/acceso";
		}

		model.addAttribute("galeria", galeria);
		return "galeria";
	}

	@PostMapping("/votar")
	public String añadirVoto(@RequestParam String puntuacion, @RequestParam String CuadroId, HttpSession session,
			Model model) {
		List<Cuadro> galeria = cuadroRepository.findAll();
		// En caso de votar sin elegir puntuación no hace nada, recarga la página
		if (galeria == null || puntuacion == null || CuadroId == null) {
			return "redirect:/galeria";
		}

		try {

			String emailUsuario = (String) session.getAttribute("email");

			// Busca al usuario en BBDD para sacar su ID 
			Usuario usuario = usuarioRepository.findByEmail(emailUsuario);
			
			Long idUsuario = usuario.getId();
			Long idCuadro = Long.parseLong(CuadroId);
			int puntos = Integer.parseInt(puntuacion);

			// Se usa el método del servicio para registrar el voto
			votoService.registrarVoto(idUsuario, idCuadro, puntos);

		} catch (Exception e) {
			// Si el usuario ya votó o hay error, podrías mandar un mensaje de error
			// Por ahora redirigimos para que no rompa
			System.out.println("Error al votar: " + e.getMessage());
		}
		session.setAttribute("galeria", galeria);
		return "redirect:/galeria";
	}

	@GetMapping("/Ranking")
	public String mostrarRanking(HttpSession session, Model model) {
		// En caso de no estar registrado te devuelve a inicio con un mensaje de alerta
		if (session.getAttribute("nombre") == null || session.getAttribute("email") == null) {
			model.addAttribute("mensajeError", "Debes registrarte antes de acceder.");
			return "inicio";
		}

		// Recoge la lista ordenada
		List<Cuadro> galeriaOrdenada = cuadroRepository.obtenerRanking();
		
		// Se rellena la variable media de cada cuadro
		for (Cuadro cuadro : galeriaOrdenada) {
			double mediaCalculada = votoService.obtenerMedia(cuadro.getId());
			cuadro.setMedia(mediaCalculada);
		}

		model.addAttribute("galeria", galeriaOrdenada);
		return "Ranking";
	}

	@GetMapping("/CuadroAleatorio")
    public String cuadroAleatorio(HttpSession session, Model model) {
    	// En caso de no estar registrado te devuelve a inicio con un mensaje de alerta
        if (session.getAttribute("nombre") == null || session.getAttribute("email") == null) {
        	model.addAttribute("mensajeError", "Debes registrarte antes de acceder.");
            return "inicio";
        }

        // Guarda un número aleatorio que sea del rango de la cantidad de cuadros creados en la galeria
        // Muestra el cuadro que esté en el array con el índice aleatorio
        List<Cuadro> galeria = cuadroRepository.findAll();
        int indice = (int) (Math.random() * galeria.size());
        Cuadro cuadro = galeria.get(indice);

        model.addAttribute("cuadro", cuadro);
        return "CuadroAleatorio";
    }
}
