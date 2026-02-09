package com.example.demo.repository;

import com.example.demo.clases.Voto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Repositorio de acceso a datos para la entidad {@link Voto}.
 * <p>
 * Gestiona la persistencia de las valoraciones realizadas por los usuarios.
 * Además de las operaciones CRUD básicas heredadas de {@link JpaRepository}, incluye:
 * <ul>
 * <li>Consultas derivadas para validación de reglas de negocio (evitar duplicados).</li>
 * <li>Consultas JPQL personalizadas para cálculos estadísticos (medias).</li>
 * <li>Proyecciones de datos para optimizar el rendimiento en listados.</li>
 * </ul>
 * </p>
 *
 * @author Jonathan Ibáñez Piñero
 * @see JpaRepository
 * @see Voto
 */
@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    /**
     * Verifica si un usuario concreto ya ha votado un cuadro específico.
     * <p>
     * Este método derivado se utiliza para imponer la regla de negocio de
     * "un solo voto por usuario y cuadro", evitando que un usuario puntúe
     * múltiples veces la misma obra.
     * </p>
     *
     * @param usuarioId El ID del usuario que intenta votar.
     * @param cuadroId  El ID del cuadro que recibe el voto.
     * @return {@code true} si ya existe un registro con esa combinación, {@code false} si es el primer voto.
     */
    boolean existsByUsuarioIdAndCuadroId(Long usuarioId, Long cuadroId);

    /**
     * Calcula la puntuación media de un cuadro basándose en todos sus votos registrados.
     * <p>
     * Utiliza una consulta JPQL personalizada con la función de agregación {@code AVG}
     * para procesar el cálculo eficientemente en la base de datos, descargando a la aplicación
     * de la tarea de iterar sobre todos los registros.
     * </p>
     *
     * @param cuadroId Identificador del cuadro del que se quiere obtener la media.
     * @return El promedio de puntuación como {@code Double}. Devuelve {@code null} si el cuadro aún no tiene votos.
     */
    @Query("SELECT AVG(v.puntuacion) FROM Voto v WHERE v.cuadro.id = :cuadroId")
    Double obtenerMedia(@Param("cuadroId") Long cuadroId);
    
    /**
     * Recupera una lista únicamente con los IDs de los cuadros que un usuario ha votado.
     * <p>
     * <b>Optimización (Proyección):</b> Esta consulta no recupera las entidades completas de {@code Voto} ni de {@code Cuadro}.
     * Solo extrae los identificadores (Long). Esto es muy eficiente para la interfaz de usuario,
     * permitiendo saber rápidamente qué cuadros deben mostrarse como "ya votados" o deshabilitados
     * en una galería grande sin cargar datos innecesarios en memoria.
     * </p>
     * * @param usuarioId El identificador del usuario del que queremos consultar su historial de votos.
     * @return Una lista de {@code Long} que representa los IDs de los cuadros ya votados por dicho usuario.
     */
    @Query("SELECT v.cuadro.id FROM Voto v WHERE v.usuario.id = :usuarioId")
    List<Long> obtenerIdsCuadrosVotadosPorUsuario(@Param("usuarioId") Long usuarioId);
    
}