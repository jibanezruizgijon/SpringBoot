package com.example.demo.clases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa un usuario.
 * <p>
 * Esta clase mapea la tabla "usuarios" en la base de datos y contiene la información
 * de credenciales (nombre, email y contraseña)  y el rol de acceso
 * para la gestión de seguridad (Spring Security).
 * Usa Lombok (@Data) para generar automáticamente getters, setters, toString, etc.
 * También usa (@AllArgsConstructor) para que cree un contructor con todas las variables
 * y (@NoArgsConstructor) para que cree el constructor por defecto.
 *
 * @author Jonathan Ibáñez Piñero
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    /**
     * Identificador único del usuario (Clave Primaria).
     * <p>
     * Se genera automáticamente por la base de datos (Auto-increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nombre completo o alias del usuario.
     */
    private String nombre;
    
    /**
     * Correo electrónico del usuario.
     * <p>
     * Se utiliza como nombre de usuario (username) para el inicio de sesión.
     * La restricción en la base de datos asegura que sea único para evitar cuentas duplicadas.
     */
    @Column(unique = true)
    private String email;

    /**
     * Contraseña del usuario.
     * <p>
     * Se guarda en la base de datos encriptada (mediante BCrypt).
     */
    private String password;
    
    /**
     * Rol de autorización del usuario.
     * <p>
     * Define los permisos de navegación y acciones dentro de la aplicación.
     * Los usuarios nuevos reciben el rol "ROLE_USER" por defecto, mientras que
     * las cuentas de gestión operan con "ROLE_ADMIN".
     */
    private String rol;

    /**
     * Constructor personalizado para registrar nuevos usuarios.
     * <p>
     * Se utiliza para instanciar un objeto {@code Usuario} antes de persistirlo
     * en la base de datos. No requiere el ID, ya que se autogenera.
     *
     * @param nombre   El nombre del usuario.
     * @param email    El correo electrónico de contacto y acceso.
     * @param password La contraseña (previamente cifrada).
     * @param rol      El rol asignado (Por defecto USER)
     */
    public Usuario(String nombre, String email, String password, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.password  = password;
        this.rol = rol;
    }
}