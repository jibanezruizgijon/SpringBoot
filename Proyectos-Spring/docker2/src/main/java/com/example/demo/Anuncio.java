package com.example.demo;


// Mejora introducida: validar entradas de formulario (https://spring.io/guides/gs/validating-form-input/)
public class Anuncio {
	// Atributos
    private String nombre;

    private String asunto;

   private String descripcion;
    
    // Getters y setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
