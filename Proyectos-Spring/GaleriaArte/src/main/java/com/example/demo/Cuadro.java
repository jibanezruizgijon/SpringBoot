package com.example.demo;

import lombok.Data;

// Clase cuadro con el comparable implementado

@Data
public class Cuadro implements Comparable<Cuadro> {
    
	// Atributos de la clase
    private final String nombre;
    private final String autor;
    private final EpocaPintura epocaPintura;
    private final String urlImg;
    private double media;
    private int numVotos;  
    
    /*
    // Contructor con los datos necesarios
    public Cuadro(String nombre, String autor, EpocaPintura epocaPintura, String urlImg) {
        this.nombre = nombre;
        this.autor = autor;
        this.epocaPintura = epocaPintura;
        this.urlImg = urlImg;
        //Todos los cuadros empezarán con una votacion y número de votos de 0
        this.media = 0;
        this.numVotos = 0;
        
    }
    
    // Getters y Setters
    public EpocaPintura getEpocaPintura() {
        return epocaPintura;
    }

    public void setEpocaPintura(EpocaPintura epocaPintura) {
        this.epocaPintura = epocaPintura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}
	
	public int getNumVotos() {
		return numVotos;
	}

	public void setNumVotos(int numVotos) {
		this.numVotos = numVotos;
	}
	*/
    

	// Método que añade el voto del cuadro y calcula la media con el número de votos  
	public void añadirVoto(int puntuacion) {
		this.media = (this.media * this.numVotos + puntuacion) / (this.numVotos + 1);
        this.numVotos++;
    }
	
	//Método que ordena los cuadros de mayor a menor según los votos
	@Override
    public int compareTo(Cuadro otro) {
        return Double.compare(otro.getMedia(), this.media);
    }
    
    
}
