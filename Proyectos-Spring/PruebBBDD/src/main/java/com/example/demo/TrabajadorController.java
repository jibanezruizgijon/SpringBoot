package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TrabajadorController {

    @Autowired
    private TrabajadorServicio servicio;

    @GetMapping({ "/trabajadores", "/" })
    public String listarTrabajadores(Model modelo) {
        modelo.addAttribute("trabajadores", servicio.listarTrabajador());
        return "trabajador"; // nos retorna al archivo trabajadores
    }

    @GetMapping("/trabajadores/crear")
    public String formulario(Model modelo) {
        Trabajador trabajador = new Trabajador();
        modelo.addAttribute("trabajador", trabajador);
        return "crear";
    }

    @PostMapping("/trabajadores")
    public String guardarTrabajador(@ModelAttribute("trabajador") Trabajador trabajador , BindingResult result) {
    	if (servicio.existeEmail(trabajador.getEmail())) {
    
            result.rejectValue("email", "error.user", "Este email ya está registrado en la base de datos.");
        }

       
        if (result.hasErrors()) {
            return "crear"; 
        }
        servicio.guardarTrabajador(trabajador);
        return "redirect:/trabajadores";
    }

    @GetMapping("/trabajadores/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("trabajador", servicio.obtenerTrabajador(id));
        return "editar";
    }

    @PostMapping("/trabajadores/{id}")
    public String actualizar(@PathVariable Integer id, @ModelAttribute("trabajador") Trabajador trabajador,
            Model modelo) {
        Trabajador actuarlizarTrabajador = servicio.obtenerTrabajador(id);
        actuarlizarTrabajador.setId(id);
        actuarlizarTrabajador.setNombre(trabajador.getNombre());
        actuarlizarTrabajador.setApellidos(trabajador.getApellidos());
        actuarlizarTrabajador.setTelefono(trabajador.getTelefono());
        actuarlizarTrabajador.setEmail(trabajador.getEmail());
        servicio.actualizarTrabajador(actuarlizarTrabajador);
        return "redirect:/trabajadores";
    }

    @GetMapping("/trabajadores/{id}")
    public String eliminar(@PathVariable Integer id) {
        servicio.borrarTrabajador(id);
        return "redirect:/trabajadores";
    }

    @GetMapping("/trabajadores/detalles/{id}")
    public String findById(Model model, @PathVariable Integer id) {
        // Recuperamos todos los anuncios.
        model.addAttribute("trabajador", servicio.obtenerTrabajador(id));
        return "detalles";
    }

}
