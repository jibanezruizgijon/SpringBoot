package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.clases.Usuario;

/**
 * Repositorio de acceso a datos para la entidad {@link Usuario}.
 * <p>
 * Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD estándar
 * (Create, Read, Update, Delete) sin necesidad de implementación manual.
 * </p>
 * <p>
 * Incluye consultas personalizadas para:
 * </p>
 * <ul>
 * <li>Encontrar un usuario por su correo electrónico.</li>
 * <li>Verificar la existencia de un usuario en la base de datos.</li>
 * <li>Recuperar una lista de usuarios filtrada por su rol.</li>
 * </ul>
 *
 * @see JpaRepository
 * @see Usuario
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca y recupera un usuario que coincida con el email especificado.
     * <p>
     * Se utiliza principalmente durante el proceso de login para validar credenciales
     * o para evitar duplicados en el registro.
     * </p>
     * * @param email El correo electrónico único del usuario a buscar.
     * @return El objeto {@link Usuario} si existe, o {@code null} si no se encuentra.
     */
    Usuario findByEmail(String email);
    
    /**
     * Verifica si existe algún usuario registrado con el email proporcionado.
     * <p>
     * Este método es más eficiente que recuperar todo el objeto usuario si solo
     * queremos saber si el correo ya está en uso (útil para validaciones en formularios).
     * </p>
     * * @param email El correo electrónico a comprobar.
     * @return {@code true} si el email ya existe en la base de datos, {@code false} en caso contrario.
     */
    boolean existsByEmail(String email); 
    
    /**
     * Recupera una lista de todos los usuarios que tienen un rol específico.
     * <p>
     * Realiza una búsqueda exacta sobre el campo 'rol' de la entidad {@link Usuario}.
     * </p>
     * * @param rol El nombre del rol a buscar (ej: "ROLE_ADMIN", "ROLE_USER").
     * @return Una lista de usuarios que poseen dicho rol. Si no hay ninguno, devuelve una lista vacía.
     */
    List<Usuario> findByRol(String rol);
    
}