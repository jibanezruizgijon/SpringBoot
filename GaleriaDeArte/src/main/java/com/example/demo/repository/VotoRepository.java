package com.example.demo.repository;

import com.example.demo.clases.Voto; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Repositorio de acceso a datos para la entidad {@link Voto}.
 * <p>
 * Gestiona la persistencia de las valoraciones realizadas por los usuarios.
 * Además de las operaciones CRUD básicas, incluye métodos específicos para
 * el control de duplicados (reglas de negocio) y cálculos estadísticos.
 * </p>
 *
 * @see JpaRepository
 * @see Voto
 */
public interface VotoRepository extends JpaRepository<Voto, Long> {

    /**
     * Verifica si un usuario concreto ya ha votado un cuadro específico.
     * <p>
     * Este método derivado se utiliza para imponer la regla de negocio de
     * "un solo voto por usuario y cuadro", evitando que un usuario puntúe
     * múltiples veces la misma obra.
     * </p>
     * * @param usuarioId El ID del usuario que intenta votar.
     * @param cuadroId  El ID del cuadro que recibe el voto.
     * @return {@code true} si ya existe un registro con esa combinación, {@code false} si es el primer voto.
     */
    boolean existsByUsuarioIdAndCuadroId(Long usuarioId, Long cuadroId);

    
    /**
     * Calcula la puntuación media de un cuadro basándose en todos sus votos registrados.
     * <p>
     * Utiliza una consulta JPQL personalizada con la función de agregación {@code AVG}
     * para procesar el cálculo eficientemente en la base de datos, en lugar de traer
     * todos los votos a la memoria de la aplicación.
     * </p>
     * * @param cuadroId Identificador del cuadro del que se quiere obtener la media.
     * @return El promedio de puntuación como {@code Double}. Puede ser {@code null} si el cuadro aún no tiene votos.
     */
    @Query("SELECT AVG(v.puntuacion) FROM Voto v WHERE v.cuadro.id = :cuadroId")
    Double obtenerMedia(@RequestParam("cuadroId") Long cuadroId);
    
}