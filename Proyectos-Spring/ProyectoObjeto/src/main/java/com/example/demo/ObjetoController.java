package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class ObjetoController {

	@GetMapping("/objeto") 
	public String metodo() {
		return "objeto";
	}
	
}
