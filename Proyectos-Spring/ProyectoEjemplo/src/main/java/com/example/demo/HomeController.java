package com.example.demo;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class HomeController {
	@ModelAttribute("personas")
	public List<Persona> getUserList() {
	    return Arrays.asList(
	        new Persona("Juan", "Perez", "jp@gmail.com", "Hombre"),
	        new Persona("Maria", "Lopez", "ml@gmail.com", "Mujer"),
	        new Persona("Beto", "Luna", "bluna@gmail.com", "Hombre")
	    );
	}
    @GetMapping("/home")
    public String viewHomePage(Model model) {
        model.addAttribute("fecha", LocalDate.now());
        return "home";
    }
}
