package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.clases.*;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CuadroRepository cuadroRepository;

	public void registrarVoto(Long usuarioId, Long cuadroId, int puntuacion) {

		// Comprueba que el usuario no haya votado ya el cuadro al que ha puntuado
		boolean comprobarVoto = votoRepository.existsByUsuarioIdAndCuadroId(usuarioId, cuadroId);

		if (comprobarVoto) {
			// En caso de ya haber votado se envía una excepción
			throw new RuntimeException("Este usuario ya ha votado por este cuadro.");
		}

		// Recoge el Usuario que ha votado y el Cuadro que se ha votado en la Base de
		// Datos
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

	public double obtenerMedia(Long cuadroId) {
		Double media = votoRepository.obtenerMediaPorCuadro(cuadroId);
		// Si no hay votos, la media devuelve null, así que se devuelve 0

		if (media != null) {
			return media;
		} else {
			return 0;
		}
	}
}
