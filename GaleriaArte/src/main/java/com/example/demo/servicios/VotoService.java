package com.example.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.clases.*;
import com.example.demo.repository.CuadroRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.VotoRepository;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los votos de la aplicación.
 * <p>
 * Esta clase actúa como intermediaria entre los controladores y la capa de persistencia,
 * gestionando las reglas de validación para emitir votos y el cálculo de estadísticas.
 * </p>
 *
 * @author TuNombre (o Nombre de la Organización)
 * @version 1.0
 * @see com.example.demo.clases.Voto
 * @see com.example.demo.clases.Cuadro
 */
@Service
public class VotoService {

    /** Repositorio para operaciones CRUD sobre la entidad Voto. */
    @Autowired
    private VotoRepository votoRepository;

    /** Repositorio para validar y recuperar entidades de Usuario. */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /** Repositorio para validar y recuperar entidades de Cuadro. */
    @Autowired
    private CuadroRepository cuadroRepository;

    /**
     * Registra un voto de un usuario para un cuadro específico.
     * <p>
     * Este método realiza las siguientes validaciones antes de guardar:
     * <ol>
     * <li>Verifica si el usuario ya ha votado por el cuadro (regla de voto único).</li>
     * <li>Verifica la existencia del usuario en la base de datos.</li>
     * <li>Verifica la existencia del cuadro en la base de datos.</li>
     * </ol>
     *
     * @param usuarioId  El identificador único (ID) del usuario que realiza el voto.
     * @param cuadroId   El identificador único (ID) del cuadro que recibe el voto.
     * @param puntuacion La puntuación numérica otorgada al cuadro.
     * @throws RuntimeException Si se cumple alguna de las siguientes condiciones:
     * <ul>
     * <li>El usuario ya ha realizado un voto para este cuadro.</li>
     * <li>No existe un usuario con el {@code usuarioId} proporcionado.</li>
     * <li>No existe un cuadro con el {@code cuadroId} proporcionado.</li>
     * </ul>
     */
    public void registrarVoto(Long usuarioId, Long cuadroId, int puntuacion) {

        // 1. Validación de regla de negocio: Un usuario no puede votar dos veces el mismo cuadro
        boolean existeVotoPrevio = votoRepository.existsByUsuarioIdAndCuadroId(usuarioId, cuadroId);

        if (existeVotoPrevio) {
            throw new RuntimeException("Error: El usuario con ID " + usuarioId + " ya ha votado por el cuadro " + cuadroId + ".");
        }

        // 2. Recuperación de entidades (Usuario y Cuadro)
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + usuarioId));

        Cuadro cuadro = cuadroRepository.findById(cuadroId)
                .orElseThrow(() -> new RuntimeException("No se encontró el cuadro con id: " + cuadroId));
        
        // 3. Creación y persistencia del voto
        Voto nuevoVoto = new Voto();
        nuevoVoto.setUsuario(usuario);
        nuevoVoto.setCuadro(cuadro);
        nuevoVoto.setPuntuacion(puntuacion);

        votoRepository.save(nuevoVoto);
    }

    /**
     * Calcula y obtiene la puntuación media de un cuadro.
     * <p>
     * Utiliza una consulta agregada en el repositorio para obtener el promedio.
     * </p>
     *
     * @param cuadroId El identificador del cuadro del que se quiere obtener la media.
     * @return Un valor {@code double} que representa la media aritmética de los votos.
     * Devuelve {@code 0.0} si el cuadro no tiene votos registrados o no existe.
     */
    public double obtenerMedia(Long cuadroId) {
        Double media = votoRepository.obtenerMedia(cuadroId);

        // Control de nulos: si la base de datos devuelve null (sin votos), retornamos 0.
        return (media != null) ? media : 0.0;
    }
}