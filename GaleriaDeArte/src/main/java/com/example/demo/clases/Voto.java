package com.example.demo.clases;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa un voto o valoración realizada por un usuario sobre una obra de arte.
 * <p>
 * Esta clase mapea la tabla "votos" en la base de datos.
 * </p>
 * 
 * <b>Reglas de Negocio:</b>
 * <ul>
 * <li>Un usuario solo puede votar una vez por cada cuadro (garantizado por {@code @UniqueConstraint}).</li>
 * <li>La fecha del voto se registra automáticamente al instanciar la clase.</li>
 * </ul>
 * 
 * * @author Jonathan Ibáñez Piñero
 * @version 1.0
 * @see Usuario
 * @see Cuadro
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "votos", 
    uniqueConstraints = {
        // Restricción compuesta: la combinación de usuario_id y cuadro_id debe ser única
        @UniqueConstraint(columnNames = {"usuario_id", "cuadro_id"})
    }
)
public class Voto {

    /**
     * Identificador único del registro de voto (Clave Primaria).
     * <p>
     * Se genera automáticamente mediante la estrategia de auto-incremento de la base de datos.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Valor numérico de la puntuación otorgada al cuadro.
     * Generalmente representa una escala (ej. de 1 a 5 estrellas).
     */
    @Column(nullable = false)
    private int puntuacion;
    
    /**
     * Fecha y hora exacta en la que se realizó la votación.
     * <p>
     * Se inicializa automáticamente con {@code LocalDateTime.now()} al crear el objeto,
     * por lo que no es necesario establecerla manualmente salvo para corregir datos.
     * </p>
     */
    @Column(name = "fechaVoto")
    private LocalDateTime fechaVoto = LocalDateTime.now();

    /**
     * El usuario que emite el voto.
     * <p>
     * Relación Muchos-a-Uno: Un usuario puede realizar múltiples votos (a distintos cuadros).
     * Se almacena como clave foránea {@code usuario_id}.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * El cuadro que recibe el voto.
     * <p>
     * Relación Muchos-a-Uno: Un cuadro puede recibir múltiples votos (de distintos usuarios).
     * Se almacena como clave foránea {@code cuadro_id}.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "cuadro_id", nullable = false)
    private Cuadro cuadro;
}