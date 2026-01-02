package com.example.demo.repository;

import com.example.demo.clases.Voto; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    // Busca si existe un voto que coincida con el id del usuario Y el id del cuadro
    boolean existsByUsuarioIdAndCuadroId(Long usuarioId, Long cuadroId);

    
    // Calcula la media con avg de las puntuaciones de un cuadro según su id
    @Query("SELECT AVG(v.puntuacion) FROM Voto v WHERE v.cuadro.id = :cuadroId")
    Double obtenerMediaPorCuadro(@RequestParam("cuadroId") Long cuadroId);
    
}