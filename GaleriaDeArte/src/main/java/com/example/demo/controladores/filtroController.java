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

@Controller
public class filtroController {

    @Autowired
    private CuadroRepository cuadroRepository;

    // Muestra la página vacía de cuadros con las opciones para filtrar
    @GetMapping("/filtrar")
    public String mostrarPaginaFiltrar(Model model) {
        model.addAttribute("epocas", EpocaPintura.values());
        model.addAttribute("cuadros", null); 
        return "filtraCuadros"; 
    }

    // Procesa la busqueda según el filtro
    @PostMapping("/procesarBusqueda")
    public String procesarBusqueda(@RequestParam("tipoBusqueda") String tipo,
                                   @RequestParam(value = "autor", required = false) String autor,
                                   @RequestParam(value = "epoca", required = false) EpocaPintura epoca,
                                   Model model) {
        
        List<Cuadro> resultados = null;

        if ("autor".equals(tipo) && autor != null && !autor.isBlank()) {
            resultados = cuadroRepository.findByAutorContainingIgnoreCase(autor.trim());
        } else if ("epoca".equals(tipo) && epoca != null) {
            resultados = cuadroRepository.findByEpocaPintura(epoca);
        }
        // Envia los cuadros que cumple con el filtro
        model.addAttribute("resultados", resultados); 
        // Vuelve a enviar las épocas para la siguiente busqueda
        model.addAttribute("epocas", EpocaPintura.values()); 
        return "filtraCuadros";
    }
}