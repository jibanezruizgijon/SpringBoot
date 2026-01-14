package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@Autowired
	UsuarioRepository usuariorepositorio;

	@GetMapping({ "/login", "/" })
	public String inicioSesion(Model modelo) {
		return "login";
	}

	@PostMapping("/acceso")
	public String comprobar(@RequestParam("user") String user, @RequestParam("pass") String pass, Model modelo) {

		if (usuariorepositorio.existsByUserAndPass(user, pass)) {
			modelo.addAttribute("usuarios", usuariorepositorio.findAll());
			return "usuarios";
		}
		return "denegado";
	}
}
