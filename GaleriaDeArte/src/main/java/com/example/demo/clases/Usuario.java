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
 * descriptiva de la obra, su autoría, clasificación histórica y la referencia a su imagen digital.
 * Usa Lombok (@Data) para generar automáticamente getters, setters, toString, etc.
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
     * Se genera automáticamente por la base de datos (Auto-increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nombre del usuario
     */
    private String nombre;
    
    /**
     * Correo electrónico que en la base de datos es único
     */
    @Column(unique = true)
    private String email;

    /**
     * Contraseña del usuario
     */
    private String password;
    /**
     * Rol del usuario.
     * 
     * El usuario no lo elige, se asigna de forma predeterminada el rol USER
     * 
     * Solo existe un rol diferente que es ADMIN 
     */
    private String rol;

    
    /**
     * Constructor personalizado para registrar nuevos usuarios.
     * <p>
     * Se utiliza al crear un objeto {@code Usuario} antes de guardarlo en la base de datos,
     * por lo que no requiere el ID (que se autogenera) ni la media (que se calcula después).
     *
     * @param nombre       El nombre del usuario.
     * @param email        El email del usuario.
     * @param password 	   La contraseña del usuario.
     * @param rol          Asigna el rol automáticamente (siempre USER)
     */
    public Usuario(String nombre, String email, String password, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.password  = password;
        this.rol = rol;
    }
}