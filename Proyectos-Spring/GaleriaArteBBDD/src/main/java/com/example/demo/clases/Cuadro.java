package com.example.demo.clases;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase cuadro 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuadros")
public class Cuadro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private String nombre;
	private String autor;
	// Se modifica el tipo de enumeracion a String 
	// Para que lea literalmente la época y no un número en la base de datos
	@Enumerated(EnumType.STRING)
	private EpocaPintura epocaPintura;

	private String urlImg;

	// Constructor sin el id para crear cuadros nuevos
	public Cuadro(String nombre, String autor, EpocaPintura epocaPintura, String urlImg) {
		this.nombre = nombre;
		this.autor = autor;
		this.epocaPintura = epocaPintura;
		this.urlImg = urlImg;
	}
}
