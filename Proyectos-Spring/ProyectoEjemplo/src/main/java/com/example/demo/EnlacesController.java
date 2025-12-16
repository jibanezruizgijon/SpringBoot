package com.example.demo;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnlacesController {
	
	@GetMapping("/Enlaces") 
	public String metodo() {
		return "Enlaces";
	}
	
	 @GetMapping("/enlace_seleccionado")
	    public String viewHomePage(@RequestParam("nenlace") String nenlace, Model model) {
	        model.addAttribute("nenlace", nenlace);
	        return "enlace_seleccionado";
	    }

}
