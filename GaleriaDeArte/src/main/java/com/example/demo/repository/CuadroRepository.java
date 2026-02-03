package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.clases.Cuadro;
import com.example.demo.clases.EpocaPintura;

/**
 * Repositorio de acceso a datos para la entidad {@link Cuadro}.
 * <p>
 * Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD estándar
 * (Create, Read, Update, Delete) sin necesidad de implementación manual.
 * Además, incluye consultas personalizadas para el ranking y filtrado de obras.
 * </p>
 *
 * @see JpaRepository
 * @see Cuadro
 */
public interface CuadroRepository extends JpaRepository<Cuadro, Long> {

    /**
     * Obtiene una lista de todos los cuadros ordenados por su puntuación media.
     * <p>
     * Utiliza una consulta JPQL personalizada que calcula el promedio (AVG) de las puntuaciones
     * en la tabla de votos asociada a cada cuadro y ordena los resultados de mayor a menor (DESC).
     * </p>
     * * @return Lista de cuadros ordenados por popularidad (el mejor valorado primero).
     */
    @Query("SELECT c FROM Cuadro c ORDER BY (SELECT AVG(v.puntuacion) FROM Voto v WHERE v.cuadro = c) DESC")
    List<Cuadro> obtenerRanking();

    /**
     * Busca cuadros cuyo nombre de autor contenga el texto proporcionado.
     * <p>
     * La búsqueda es "case-insensitive" (ignora mayúsculas y minúsculas) y parcial
     * (el texto buscado puede estar en cualquier parte del nombre del autor).
     * </p>
     * * @param autor Fragmento del nombre del autor a buscar (ej: "Picasso", "vinci").
     * @return Una lista de cuadros cuyos autores coinciden con el criterio de búsqueda.
     */
    List<Cuadro> findByAutorContainingIgnoreCase(String autor);

    /**
     * Filtra los cuadros que pertenecen a una época pictórica específica.
     * <p>
     * Realiza una búsqueda exacta basada en el enumerado {@link EpocaPintura}.
     * </p>
     * * @param epocaPintura La época o estilo artístico por el que se desea filtrar.
     * @return Lista de cuadros pertenecientes a esa época.
     */
    List<Cuadro> findByEpocaPintura(EpocaPintura epocaPintura);
}