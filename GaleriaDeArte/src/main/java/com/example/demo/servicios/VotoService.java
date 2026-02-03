package com.example.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.clases.*;
import com.example.demo.repository.CuadroRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.VotoRepository;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los votos.
 * Permite registrar nuevos votos y calcular la media de puntuación de los cuadros.
 */
@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuadroRepository cuadroRepository;

    /**
     * Registra un voto de un usuario para un cuadro específico.
     * Verifica que el usuario no haya votado previamente por el mismo cuadro.
     *
     * @param usuarioId  El identificador único del usuario que realiza el voto.
     * @param cuadroId   El identificador único del cuadro que recibe el voto.
     * @param puntuacion La puntuación otorgada al cuadro (valor entero).
     * @throws RuntimeException Si el usuario ya ha votado por este cuadro o si no se encuentra el usuario/cuadro.
     */
    public void registrarVoto(Long usuarioId, Long cuadroId, int puntuacion) {

        // Comprueba que el usuario no haya votado ya el cuadro al que ha puntuado
        boolean comprobarVoto = votoRepository.existsByUsuarioIdAndCuadroId(usuarioId, cuadroId);

        if (comprobarVoto) {
            // En caso de ya haber votado se envía una excepción
            throw new RuntimeException("Este usuario ya ha votado por este cuadro.");
        }

        // Recoge el Usuario que ha votado y el Cuadro que se ha votado en la Base de Datos
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + usuarioId));

        Cuadro cuadro = cuadroRepository.findById(cuadroId)
                .orElseThrow(() -> new RuntimeException("No se encontró el cuadro con id: " + cuadroId));
        
        Voto nuevoVoto = new Voto();
        nuevoVoto.setUsuario(usuario);
        nuevoVoto.setCuadro(cuadro);
        nuevoVoto.setPuntuacion(puntuacion);

        // Guarda el voto
        votoRepository.save(nuevoVoto);
    }

    /**
     * Obtiene la puntuación media de un cuadro basándose en todos los votos registrados.
     *
     * @param cuadroId El identificador del cuadro del que se quiere obtener la media.
     * @return La media de las puntuaciones como un valor double. Devuelve 0 si no hay votos.
     */
    public double obtenerMedia(Long cuadroId) {
        Double media = votoRepository.obtenerMedia(cuadroId);
        // Si no hay votos, la media devuelve null, así que se devuelve 0

        if (media != null) {
            return media;
        } else {
            return 0;
        }
    }
}