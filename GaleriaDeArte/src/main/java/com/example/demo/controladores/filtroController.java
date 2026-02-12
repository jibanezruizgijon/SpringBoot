package com.example.demo.controladores;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.clases.Cuadro;
import com.example.demo.clases.EpocaPintura;
import com.example.demo.repository.CuadroRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.servicios.CloudinaryService;
import com.example.demo.servicios.EmailService;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Controlador encargado de gestionar las búsquedas y filtrado de cuadros.
 * <p>
 * Permite al usuario buscar obras de arte por diferentes criterios (Autor o Época)
 * y muestra los resultados en una vista dedicada.
 *
 * @author Jonathan Ibáñez Piñero
 */
@Controller
@Data
@NoArgsConstructor
public class filtroController {

	/** Repositorio para realizar búsquedas de cuadros. */
    @Autowired
    private CuadroRepository cuadroRepository;

    /**
     * Muestra la página de búsqueda inicial.
     * <p>
     * Carga las opciones de filtrado (como las épocas disponibles) pero no muestra ningún resultado
     * hasta que el usuario realice una acción.
     *
     * @param model Modelo de datos para pasar la lista de épocas a la vista.
     * @return La vista {@code filtraCuadros.html}.
     */
    @GetMapping("/filtrar")
    public String mostrarPaginaFiltrar(Model model) {
        model.addAttribute("epocas", EpocaPintura.values());
        model.addAttribute("cuadros", null); 
        return "filtraCuadros"; 
    }

    /**
     * Procesa la solicitud de búsqueda enviada por el formulario.
     * <p>
     * Dependiendo del "tipoBusqueda" seleccionado, delega la consulta al repositorio:
     * <ul>
     * <li><b>Autor:</b> Búsqueda parcial e insensible a mayúsculas.</li>
     * <li><b>Época:</b> Búsqueda exacta por el enum {@link EpocaPintura}.</li>
     * </ul>
     *
     * @param tipo  El tipo de búsqueda seleccionado ("autor" o "epoca").
     * @param autor El autor introducido por el que se filtrarán los cuadros.
     * @param epoca La época seleccionada en el desplegable.
     * @param model Modelo para devolver los resultados y recargar el formulario.
     * @return La misma vista {@code filtraCuadros.html} pero ahora con la lista de resultados poblada.
     */
    @PostMapping("/procesarBusqueda")
    public String procesarBusqueda(@RequestParam("tipoBusqueda") String tipo,
                                   @RequestParam(value = "autor", required = false) String autor,
                                   @RequestParam(value = "epoca", required = false) EpocaPintura epoca,
                                   Model model) {
        
        List<Cuadro> resultados = null;

        // Lógica de selección de estrategia de búsqueda
        if ("autor".equals(tipo) && autor != null && !autor.isBlank()) {
            resultados = cuadroRepository.findByAutorContainingIgnoreCase(autor.trim());
        } else if ("epoca".equals(tipo) && epoca != null) {
            resultados = cuadroRepository.findByEpocaPintura(epoca);
        }
        
        // Envía los resultados en la vista
        model.addAttribute("resultados", resultados); 
        // Es necesario volver a enviar las épocas para que el desplegable se vuelva a llenar después de recargar
        model.addAttribute("epocas", EpocaPintura.values()); 
        
        return "filtraCuadros";
    }
}