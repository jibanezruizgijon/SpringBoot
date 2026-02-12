package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.clases.Cuadro;
import com.example.demo.clases.EpocaPintura;
import com.example.demo.controladores.AdminController;
import com.example.demo.servicios.CloudinaryService;
import com.example.demo.servicios.EmailService;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Repositorio de acceso a datos para la entidad {@link Cuadro}.
 * <p>
 * Añade la funcionalidad completa de CRUD 
 * gracias a la herencia de {@link JpaRepository}.
 * Incluye consultas personalizadas derivadas del nombre del método para búsquedas específicas.
 * @author Jonathan Ibáñez Piñero
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository

public interface CuadroRepository extends JpaRepository<Cuadro, Long> {

    /**
     * Obtiene una lista de todos los cuadros ordenados por su puntuación media.
     * <p>
     * Utiliza una consulta que calcula el promedio (AVG) de las puntuaciones
     * en la tabla de votos asociada a cada cuadro y ordena los resultados de mayor a menor (DESC).
     * @return Lista de cuadros ordenados por votos de mayor a menor puntuación media.
     */
    @Query("SELECT c FROM Cuadro c ORDER BY (SELECT AVG(v.puntuacion) FROM Voto v WHERE v.cuadro = c) DESC")
    List<Cuadro> obtenerRanking();

    /**
     * Busca cuadros creados por el autor especificado 
     * <p>
     * La búsqueda es "case-insensitive" (ignora mayúsculas y minúsculas) y parcial
     * (el texto buscado puede estar en cualquier parte del nombre del autor).
     * @param autor Fragmento del nombre del autor a buscar.
     * @return Una lista de cuadros cuyos autores coinciden con el criterio de búsqueda.
     */
    List<Cuadro> findByAutorContainingIgnoreCase(String autor);

    /**
     * Filtra los cuadros que pertenecen a una época o estilo artístico específico.
     * <p>
     * Realiza una búsqueda exacta basada en el enumerado {@link EpocaPintura}.
     * @param epocaPintura La época o estilo artístico por el que se desea filtrar.
     * @return Lista de cuadros pertenecientes a esa época.
     */
    List<Cuadro> findByEpocaPintura(EpocaPintura epocaPintura);
}