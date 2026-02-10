package com.example.demo.controladores;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.clases.Cuadro;
import com.example.demo.clases.Usuario;
import com.example.demo.repository.*;
import com.example.demo.servicios.CloudinaryService;
import com.example.demo.servicios.EmailService;
import com.example.demo.servicios.VotoService;

import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Controlador encargado de la visualización e interacción con la galería de arte.
 * <p>
 * Gestiona las funcionalidades de:
 * <ul>
 * <li>Listado general de cuadros.</li>
 * <li>Sistema de votación (comprobando permisos y duplicados).</li>
 * <li>Visualización del Ranking de mejores cuadros.</li>
 * <li>Funcionalidad de "Cuadro Aleatorio".</li>
 * </ul>
 *
 * @author Jonathan Ibáñez Piñero
 */
@Controller
public class galeriaController {

	/** Acceso a datos de los cuadros. */
    @Autowired
    CuadroRepository cuadroRepository;

    /** Acceso a datos de usuario */
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    VotoRepository votoRepository;

    @Autowired
    VotoService votoService;
    
    /**
     * Muestra la galería principal de cuadros.
     * <p>
     * Además de cargar los cuadros, este método comprueba qué cuadros ha votado ya el usuario autenticado
     * para deshabilitar el botón de voto en la vista (mejora de UX).
     * </p>
     *
     * @param model     Modelo de datos para la vista.
     * @param principal Usuario autenticado (puede ser null si es anónimo, aunque SecurityConfig lo restringe).
     * @return La vista {@code galeria.html}.
     */
    @GetMapping("/galeria")
    public String mostrarGaleria(Model model, Principal principal) {
        
        // 1. Cargar todos los cuadros
        List<Cuadro> galeria = cuadroRepository.findAll();
        if (galeria == null) {
            return "redirect:/acceso";
        }
        model.addAttribute("galeria", galeria);

        // 2. Lógica de UI: Identificar votos previos del usuario
        List<Long> cuadrosVotados = new ArrayList<>();

        if (principal != null) {
            String email = "";
            // Detección de tipo de login (OAuth2 vs Local)
            if (principal instanceof OAuth2AuthenticationToken) {
                email = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
            } else {
                email = principal.getName();
            }

            Usuario usuario = usuarioRepository.findByEmail(email);
            if (usuario != null) {
                // Recuperamos solo los IDs para optimizar el rendimiento
                cuadrosVotados = votoRepository.obtenerIdsCuadrosVotadosPorUsuario(usuario.getId());
            }
        }

        // Enviamos la lista de IDs votados al frontend para gestionar el estado de los botones
        model.addAttribute("cuadrosVotados", cuadrosVotados);

        return "galeria";
    }
    
    /**
     * Procesa la petición de voto para un cuadro.
     * <p>
     * Utiliza el servicio {@link VotoService} para aplicar las reglas de negocio (un voto por usuario/cuadro).
     * Si el voto es válido, se registra; si hay error (ej. voto duplicado), se captura y muestra en pantalla.
     *
     * @param puntuacion Valor numérico del voto.
     * @param CuadroId   Identificador del cuadro votado.
     * @param session    Sesión HTTP (usada para persistencia temporal si fuera necesario).
     * @param model      Modelo para reportar errores a la vista.
     * @param principal  Usuario que realiza la acción.
     * @return Redirección a la galería o recarga de la misma con mensaje de error.
     */
    @PostMapping("/votar")
    public String añadirVoto(@RequestParam String puntuacion, @RequestParam String CuadroId, HttpSession session,
            Model model, Principal principal) {
        
        List<Cuadro> galeria = cuadroRepository.findAll();
        
        // Validación de parámetros de entrada
        if (galeria == null || puntuacion == null || CuadroId == null) {
            return "redirect:/galeria";
        }

        try {
            // Identificación robusta del usuario (compatible con Google y Local)
            String emailUsuario = null;
            if (principal instanceof OAuth2AuthenticationToken) {
                emailUsuario = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
            } else {
                emailUsuario = principal.getName();
            }

            Usuario usuario = usuarioRepository.findByEmail(emailUsuario);
            if (usuario == null) {
                throw new Exception("Error de seguridad: No se ha podido identificar al usuario en la sesión.");
            }

            Long idUsuario = usuario.getId();
            Long idCuadro = Long.parseLong(CuadroId);
            int puntos = Integer.parseInt(puntuacion);

            // Delegamos la lógica transaccional al servicio
            votoService.registrarVoto(idUsuario, idCuadro, puntos);

        } catch (Exception e) {
            // Gestión de errores: Se muestra el mensaje al usuario sin caer la aplicación
            model.addAttribute("mensajeError", e.getMessage());
            
            // Pasamos el ID del cuadro conflictivo para resaltar el error en la tarjeta correcta
            if (CuadroId != null && !CuadroId.isEmpty()) {
                model.addAttribute("idCuadroError", Long.parseLong(CuadroId));
            }
            model.addAttribute("galeria", galeria);
            // IMPORTANTE: Recargamos los votos del usuario para que la vista no pierda el estado de los botones
            // (Nota: Aquí podrías refactorizar para no repetir la lógica de 'mostrarGaleria')
            return "galeria";
        }
        
        return "redirect:/galeria";
    }

    /**
     * Muestra el Ranking de cuadros ordenados por puntuación.
     * <p>
     * Calcula dinámicamente la media de cada cuadro antes de enviarlo a la vista.
     * @param session Sesión actual.
     * @param model   Modelo de datos.
     * @return La vista {@code Ranking.html}.
     */
    @GetMapping("/Ranking")
    public String mostrarRanking(HttpSession session, Model model) {
        // Obtenemos cuadros ya ordenados desde el repositorio (si la query lo soporta) o los ordenamos aquí
        List<Cuadro> galeriaOrdenada = cuadroRepository.obtenerRanking();

        // Calculamos la media actual para mostrarla en tiempo real
        for (Cuadro cuadro : galeriaOrdenada) {
            double mediaCalculada = votoService.obtenerMedia(cuadro.getId());
            cuadro.setMedia(mediaCalculada);
        }

        model.addAttribute("galeria", galeriaOrdenada);
        return "Ranking";
    }

    /**
     * Selecciona y muestra un cuadro al azar de la galería.
     * @param session Sesión actual.
     * @param model   Modelo de datos.
     * @return La vista {@code CuadroAleatorio.html}.
     */
    @GetMapping("/CuadroAleatorio")
    public String cuadroAleatorio(HttpSession session, Model model) {
        List<Cuadro> galeria = cuadroRepository.findAll();
        
        if (!galeria.isEmpty()) {
            int indice = (int) (Math.random() * galeria.size());
            Cuadro cuadro = galeria.get(indice);
            model.addAttribute("cuadro", cuadro);
        }
        
        return "CuadroAleatorio";
    }
}
