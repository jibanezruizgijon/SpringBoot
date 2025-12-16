package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class Controlador {

	
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
		
		
		
		/*
		 // Función para implementar el método burbuja
public static void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                // Intercambiar arr[j] y arr[j+1]
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

   HashMap<String, Integer> edades = new HashMap<>();

        // Insertar valores
        edades.put("Ana", 25);
       

        // Obtener un valor por su clave
        int edadAna = edades.get("Ana");
        System.out.println("Edad de Ana: " + edadAna);

        // Comprobar si existe una clave
        if (edades.containsKey("Luis")) {
            System.out.println("Luis está en el mapa");
        }

        // Modificar un valor
        edades.put("Marta", 23);

        // Eliminar una entrada
        edades.remove("Luis");

        // Recorrer el HashMap
        for (String nombre : edades.keySet()) {
            System.out.println(nombre + " -> " + edades.get(nombre));
        }
		  */
		 
}
