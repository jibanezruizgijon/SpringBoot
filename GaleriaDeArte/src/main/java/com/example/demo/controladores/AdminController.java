package com.example.demo.controladores;

import com.example.demo.clases.Cuadro;
import com.example.demo.clases.EpocaPintura;
import com.example.demo.clases.Usuario;
import com.example.demo.repository.CuadroRepository;
import com.example.demo.servicios.CloudinaryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.servicios.EmailService; 
import com.example.demo.repository.UsuarioRepository; 

/**
 * Controlador de Administración (Back-office).
 * <p>
 * Gestiona las operaciones privilegiadas que solo el rol ADMIN puede realizar:
 * <ul>
 * <li>Alta, Baja y Modificación de Cuadros (CRUD).</li>
 * <li>Gestión de imágenes en la nube (Cloudinary).</li>
 * <li>Envío de notificaciones masivas por correo electrónico (Newsletter).</li>
 * </ul>
 * </p>
 *
 * @author Jonathan Ibáñez Piñero
 * @version 1.0
 */
@Controller
public class AdminController {

    /** Servicio para la gestión de almacenamiento de imágenes externas. */
    @Autowired
    private CloudinaryService cloudinaryService;

    /** Acceso a datos de los cuadros. */
    @Autowired
    private CuadroRepository cuadroRepository;
    
    /** Acceso a datos de usuarios (necesario para obtener emails para el newsletter). */
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /** Servicio de envío de correos electrónicos. */
    @Autowired
    private EmailService emailService;

    /**
     * Muestra el formulario de alta de un nuevo cuadro.
     * @param model Modelo de datos.
     * @return La vista {@code nuevoCuadro.html}.
     */
    @GetMapping("/nuevoCuadro")
    public String formularioCuadro(Model model) {
        model.addAttribute("epocas", EpocaPintura.values());
        return "nuevoCuadro";
    }

    /**
     * Procesa la creación de un nuevo cuadro y gestiona la subida de su imagen.
     * <p>
     * Flujo de ejecución:
     * <ol>
     * <li>Sube la imagen recibida a Cloudinary y obtiene su URL pública.</li>
     * <li>Crea el objeto {@code Cuadro} con los metadatos y la URL.</li>
     * <li>Guarda el cuadro en la base de datos.</li>
     * <li>Redirige a la pantalla de redacción de correo para notificar la novedad.</li>
     * </ol>
     * </p>
     *
     * @param nombre        Título de la obra.
     * @param autor         Nombre del artista.
     * @param epoca         Categoría histórica/artística.
     * @param imagen        Archivo binario de la imagen.
     * @param redirectAttrs Atributos para pasar mensajes entre redirecciones.
     * @return Redirección a {@code /prepararCorreo/{id}}.
     */
    @PostMapping("/guardarCuadro")
    public String guardarCuadro(@RequestParam("nombre") String nombre, @RequestParam("autor") String autor,
            @RequestParam("epoca") EpocaPintura epoca, @RequestParam("imagen") MultipartFile imagen, RedirectAttributes redirectAttrs) {

        String urlImagen = null;
        
        try {
            // Delegamos la subida al servicio externo
            urlImagen = cloudinaryService.subirImagen(imagen);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/nuevo-cuadro?error=fallo_subida";
        }

        Cuadro nuevoCuadro = new Cuadro(nombre, autor, epoca, urlImagen);
        cuadroRepository.save(nuevoCuadro);

        // Pasamos al siguiente paso del flujo: Notificar a los usuarios
        return "redirect:/prepararCorreo/" + nuevoCuadro.getId();
    }
    
    /**
     * Muestra la vista de previsualización para enviar un correo masivo sobre un cuadro nuevo.
     * @param id    Identificador del cuadro recién creado.
     * @param model Modelo de datos.
     * @return La vista {@code enviarCorreo.html}.
     */
    @GetMapping("/prepararCorreo/{id}")
    public String prepararCorreo(@PathVariable Long id, Model model) {
        Cuadro cuadro = cuadroRepository.findById(id).orElse(null);
        
        if (cuadro == null) {
            return "redirect:/galeria"; 
        }
        
        model.addAttribute("cuadro", cuadro);
        return "enviarCorreo"; 
    }

    /**
     * Ejecuta el envío masivo de correos electrónicos a todos los usuarios registrados.
     * <p>
     * Recupera todos los usuarios con rol 'ROLE_USER' e itera sobre ellos para enviar
     * la notificación utilizando {@link EmailService}.
     * </p>
     *
     * @param idCuadro ID del cuadro (referencia).
     * @param asunto   Asunto del correo electrónico.
     * @param cuerpo   Cuerpo del mensaje.
     * @return Redirección final a la galería.
     */
    @PostMapping("/enviarAviso")
    public String enviarAviso(@RequestParam Long idCuadro,
                              @RequestParam String asunto,
                              @RequestParam String cuerpo) {
        
        // Obtenemos la lista de destinatarios (Usuarios normales)
        List<Usuario> usuarios = usuarioRepository.findByRol("ROLE_USER");
        
        // Envío iterativo (Nota: En producción, esto debería ser asíncrono o por lotes)
        for (Usuario u : usuarios) {
            if (u.getEmail() != null && !u.getEmail().isEmpty()) {
                emailService.enviarCorreoMasivo(u.getEmail(), asunto, cuerpo);
            }
        }
        
        return "redirect:/galeria";
    }

    /**
     * Elimina un cuadro del sistema por su ID.
     * @param id Identificador del cuadro a borrar.
     * @return Redirección a la galería actualizada.
     */
    @GetMapping("/eliminarCuadro/{id}")
    public String eliminarCuadro(@PathVariable("id") Long id) {
        cuadroRepository.deleteById(id);
        return "redirect:/galeria";
    }

    /**
     * Carga el formulario de edición con los datos actuales de un cuadro.
     * @param id    Identificador del cuadro.
     * @param model Modelo de datos.
     * @return La vista {@code modificarCuadro.html}.
     */
    @GetMapping("/modificarCuadro/{id}")
    public String modificarCuadro(@PathVariable("id") Long id, Model model) {
        Cuadro cuadroModificar = cuadroRepository.findById(id).orElse(null);
        model.addAttribute("cuadro", cuadroModificar);
        model.addAttribute("epocas", EpocaPintura.values());
        return "modificarCuadro";
    }

    /**
     * Aplica los cambios realizados a un cuadro existente.
     * <p>
     * <b>Nota:</b> Este método solo actualiza los metadatos (texto). 
     * La imagen no se modifica en esta operación.
     * </p>
     * @param cuadro Objeto con los nuevos datos (binding automático del formulario).
     * @param model  Modelo de datos.
     * @return Redirección a la galería.
     */
    @PostMapping("/cuadroModificado")
    public String cuadroModificado(@ModelAttribute("cuadro") Cuadro cuadro, Model model) {
        Cuadro cuadroModificar = cuadroRepository.findById(cuadro.getId()).orElse(null);

        if (cuadroModificar != null) {
            cuadroModificar.setNombre(cuadro.getNombre());
            cuadroModificar.setAutor(cuadro.getAutor());
            cuadroModificar.setEpocaPintura(cuadro.getEpocaPintura());
            cuadroRepository.save(cuadroModificar);
        }
        return "redirect:/galeria";
    }
}