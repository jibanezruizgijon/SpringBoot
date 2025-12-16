package com.example.demo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public interface SesionFinalizable {

	public void cerrarSesion(HttpSession sesion, HttpServletResponse response);
}
