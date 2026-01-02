package com.example.demo.clases;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 	Necesario tener otra clase de la tabla intermedia para que pueda guardar 
 * más variables además de los id de usuario y de cuadros. como la media de los votos
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "votos", 
    uniqueConstraints = {
        // Para que un usuario solo pueda votar una vez el mismo cuadro
        @UniqueConstraint(columnNames = {"usuario_id", "cuadro_id"})
    }
)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private int puntuacion;
    
    @Column(name = "fechaVoto")
	private LocalDateTime fechaVoto = LocalDateTime.now();

    // Relación con Usuario: Muchos votos pueden ser de un usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación con Cuadro: Muchos votos pueden ser para un cuadro
    @ManyToOne
    @JoinColumn(name = "cuadro_id", nullable = false)
    private Cuadro cuadro;
}