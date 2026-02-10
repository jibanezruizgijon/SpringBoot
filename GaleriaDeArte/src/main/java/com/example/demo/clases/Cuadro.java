package com.example.demo.clases;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa una obra de arte (Cuadro).
 * <p>
 * Esta clase mapea la tabla "cuadros" en la base de datos y contiene la información
 * descriptiva de la obra, su autoría, clasificación histórica y la referencia a su imagen digital.
 * Usa Lombok (@Data) para generar automáticamente getters, setters, toString, etc.
 * @author Jonathan Ibáñez Piñero
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuadros")
public class Cuadro {

    /**
     * Identificador único del cuadro (Clave Primaria).s
     * Se genera automáticamente por la base de datos (Auto-increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Puntuación media de los votos recibidos por el cuadro.
     * <p>
     * Este campo está marcado como {@code @Transient}, lo que significa que 
     * <b>NO se guarda en la base de datos</b>. Se calcula en tiempo de ejecución
     * obteniendo la media de la tabla de votos asociada.
     */
    @Transient 
    private Double media;

    /**
     * Título o nombre de la obra de arte.
     */
    private String nombre;

    /**
     * Nombre del artista o pintor que creó la obra.
     */
    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    /**
     * Clasificación histórica o estilo artístico al que pertenece el cuadro.
     * <p>
     * Se almacena en la base de datos como un {@code STRING} (texto literal) 
     * en lugar de un número ordinal, para mayor legibilidad en la BBDD.
     * @see EpocaPintura
     */
    @Enumerated(EnumType.STRING)
    private EpocaPintura epocaPintura;

    /**
     * URL pública donde se encuentra alojada la imagen del cuadro.
     * Generalmente apunta a un servicio de almacenamiento externo (ej. Cloudinary).
     */
    private String urlImg;

    /**
     * Constructor personalizado para registrar nuevos cuadros.
     * <p>
     * Se utiliza al crear un objeto {@code Cuadro} antes de guardarlo en la base de datos,
     * por lo que no requiere el ID (que se autogenera) ni la media (que se calcula después).
     *
     * @param nombre       El título de la obra.
     * @param autor        El nombre del artista.
     * @param epocaPintura La época o estilo (enum).
     * @param urlImg       La URL de la imagen ya subida al servidor.
     */
    public Cuadro(String nombre, String autor, EpocaPintura epocaPintura, String urlImg) {
        this.nombre = nombre;
        this.autor = autor;
        this.epocaPintura = epocaPintura;
        this.urlImg = urlImg;
    }
}