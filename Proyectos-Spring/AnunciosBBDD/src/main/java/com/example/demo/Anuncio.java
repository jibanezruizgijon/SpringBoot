package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "anuncios")
public class Anuncio {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "asunto")
	private String asunto;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "fechaCreacion")
	private LocalDateTime fechaCreacion = LocalDateTime.now();

	public Anuncio() {
	}
	
	public Anuncio(Long id,String nombre, String asunto, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.asunto = asunto;
		this.descripcion = descripcion;
	}

	public Anuncio(String nombre, String asunto, String descripcion) {
		this.nombre = nombre;
		this.asunto = asunto;
		this.descripcion = descripcion;
	}
	
	

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
