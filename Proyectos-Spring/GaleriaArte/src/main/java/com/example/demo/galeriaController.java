package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class galeriaController {

    @GetMapping("/galeria")
    public String mostrarGaleria(HttpSession session, Model model) {
    	// En caso de no estar registrado te devuelve a inicio con un mensaje de alerta
        if (session.getAttribute("nombre") == null || session.getAttribute("contraseña") == null) {
        	model.addAttribute("mensajeError", "Debes registrarte antes de acceder.");
            return "inicio";
        }

        List<Cuadro> galeria = (List<Cuadro>) session.getAttribute("galeria");
        if (galeria == null) {
            return "redirect:/acceso";
        }

        model.addAttribute("galeria", galeria);
        return "galeria";
    }

    @PostMapping("/votar")
    public String añadirVoto(@RequestParam String puntuacion,
                             @RequestParam String nombreCuadro,
                             HttpSession session
                            ) { 	
        List<Cuadro> galeria = (List<Cuadro>) session.getAttribute("galeria");
      // En caso de votar sin elegir puntuación no hace nada, recarga la página
        if (galeria == null || puntuacion == null || nombreCuadro == null) {
            return "redirect:/galeria";
        }

        int voto = Integer.parseInt(puntuacion);
        boolean cuadroEncontrado = false;

        for (Cuadro g : galeria) {
            if (g.getNombre().equals(nombreCuadro)) {
                g.añadirVoto(voto); 
                cuadroEncontrado = true;
            }
        }
        
        // Comprobaba si no entraba en el if si al votar mme llevaba al cuadroAleatorio
        if (!cuadroEncontrado) {
        	return "cuadroAleatorio";
        }
        session.setAttribute("galeria", galeria);
        return "redirect:/galeria"; 
    }

    @GetMapping("/Ranking")
    public String mostrarRanking(HttpSession session, Model model) {
    	// En caso de no estar registrado te devuelve a inicio con un mensaje de alerta
        if (session.getAttribute("nombre") == null || session.getAttribute("contraseña") == null) {
        	model.addAttribute("mensajeError", "Debes registrarte antes de acceder.");
            return "inicio";
        }

        // Compara los cuadros para ordenarlos de mayor media a menor 
        List<Cuadro> galeria = (List<Cuadro>) session.getAttribute("galeria");
        galeria.sort((a, b) -> Double.compare(b.getMedia(), a.getMedia()));

        model.addAttribute("galeria", galeria);
        return "Ranking";
    }

    @GetMapping("/CuadroAleatorio")
    public String cuadroAleatorio(HttpSession session, Model model) {
    	// En caso de no estar registrado te devuelve a inicio con un mensaje de alerta
        if (session.getAttribute("nombre") == null || session.getAttribute("contraseña") == null) {
        	model.addAttribute("mensajeError", "Debes registrarte antes de acceder.");
            return "inicio";
        }

        // Guarda un número aleatorio que sea del rango de la cantidad de cuadros creados en la galeria
        // Muestra el cuadro que esté en el array con el índice aleatorio
        List<Cuadro> galeria = (List<Cuadro>) session.getAttribute("galeria");
        int indice = (int) (Math.random() * galeria.size());
        Cuadro cuadro = galeria.get(indice);

        model.addAttribute("cuadro", cuadro);
        return "cuadroAleatorio";
    }
}
