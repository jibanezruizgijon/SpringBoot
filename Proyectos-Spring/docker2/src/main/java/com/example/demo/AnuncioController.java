package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

@Controller
public class AnuncioController {

    @GetMapping("/")
    public String tablon(Model model, HttpSession session) {
        if (session.isNew()) {
            model.addAttribute("mensajeBienvenida", "¡Bienvenido/a al tablón de anuncios!");
            session.setAttribute("usuario", new Usuario());
        }

        ArrayList<Anuncio> anuncios = obtenerAnuncios(session);
        model.addAttribute("anuncios", anuncios);

        return "tablon";
    }

    @GetMapping("/anuncio")
    public String muestraAnuncio(@RequestParam String nombre, Model model, HttpSession session) {
        ArrayList<Anuncio> anuncios = obtenerAnuncios(session);

        Anuncio anuncioEncontrado = null;
        for (Anuncio anuncio : anuncios) {
            if (anuncio.getNombre().equals(nombre)) {
                anuncioEncontrado = anuncio;
                break;
            }
        }

        if (anuncioEncontrado != null) {
            model.addAttribute("anuncio", anuncioEncontrado);
            return "muestraAnuncio";
        } else {
            return "anuncioNoEncontrado";
        }
    }

    @GetMapping("/nuevo")
    public String nuevoAnuncio(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null && usuario.getNombre() != null) {
            model.addAttribute("nombreUsuario", usuario.getNombre());
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncio", new Anuncio());

        return "nuevoAnuncio";
    }

    // Mejora introducida: validar entradas de formulario (https://spring.io/guides/gs/validating-form-input/)
    @PostMapping("/nuevo")
    public String guardaAnuncio(@ModelAttribute("anuncio") Anuncio anuncio, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            // Si hay errores de validación, regresa a la página para insertar un anuncio nuevo
            return "nuevoAnuncio";
        }
        ArrayList<Anuncio> anuncios = obtenerAnuncios(session);
        anuncios.add(anuncio);
        session.setAttribute("anuncios", anuncios);

        return "anuncioGuardado";
    }

    private ArrayList<Anuncio> obtenerAnuncios(HttpSession session) {
    	ArrayList<Anuncio> anuncios = (ArrayList<Anuncio>) session.getAttribute("anuncios");
        if (anuncios == null) {
            anuncios = new ArrayList<>();
            session.setAttribute("anuncios", anuncios);
        }
        return anuncios;
    }
}

