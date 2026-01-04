package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.clases.Cuadro;


public interface CuadroRepository extends JpaRepository<Cuadro, Long> {
	// Devuelve los cuadros ordenados según su media de votos
	@Query("SELECT c FROM Cuadro c ORDER BY (SELECT AVG(v.puntuacion) FROM Voto v WHERE v.cuadro = c) DESC")
	List<Cuadro> obtenerRanking();
}
