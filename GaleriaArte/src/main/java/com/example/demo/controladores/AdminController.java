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
 * Controlador encargado de las funcionalidades de administración.
 * Gestiona las operaciones CRUD (Crear, Leer, Actualizar, Borrar) para la entidad {@link Cuadro}.
 * Solo los usuarios con rol de administrador deberían tener acceso a estas rutas.
 */
@Controller
public class AdminController {

	/**
     * Servicio para la gestión de subida de imágenes a la nube (Cloudinary).
     */
	@Autowired
	private CloudinaryService cloudinaryService;

	/**
     * Repositorio para realizar operaciones de base de datos sobre la entidad Cuadro.
     */
	@Autowired
	private CuadroRepository cuadroRepository;
	
	/**
     * Repositorio para realizar operaciones de base de datos sobre la entidad Usuario.
     */
	@Autowired
    private UsuarioRepository usuarioRepository;
	
    @Autowired
    private EmailService emailService;

    /**
     * Muestra el formulario para crear un nuevo cuadro.
     * Carga las épocas de pintura disponibles en el modelo para mostrarlas en un desplegable.
     * * @param model Modelo de Spring para pasar datos a la vista.
     * @return El nombre de la vista (plantilla HTML) "nuevoCuadro".
     */
	@GetMapping("/nuevoCuadro")
	public String formularioCuadro(Model model) {
		model.addAttribute("epocas", EpocaPintura.values());
		return "nuevoCuadro";
	}

	/**
     * Procesa el formulario de creación de un cuadro.
     * Sube la imagen proporcionada a Cloudinary, crea una nueva instancia de {@link Cuadro}
     * con los datos recibidos y la guarda en la base de datos.
     * * @param nombre Nombre del cuadro.
     * @param autor Autor del cuadro.
     * @param epoca Época a la que pertenece el cuadro (enum {@link EpocaPintura}).
     * @param imagen Archivo de imagen subido por el usuario.
     * @return Redirección a la galería si todo sale bien, o redirección con error si falla la subida.
     */
	@PostMapping("/guardarCuadro")
	public String guardarCuadro(@RequestParam("nombre") String nombre, @RequestParam("autor") String autor,
			@RequestParam("epoca") EpocaPintura epoca, @RequestParam("imagen") MultipartFile imagen, RedirectAttributes redirectAttrs) {

		String urlImagen = null;
		// Para subir la imagen a cloudinary 
		try {
			urlImagen = cloudinaryService.subirImagen(imagen);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/nuevo-cuadro?error=fallo_subida";
		}

		Cuadro nuevoCuadro = new Cuadro(nombre, autor, epoca, urlImagen);

		cuadroRepository.save(nuevoCuadro);

		return "redirect:/prepararCorreo/" + nuevoCuadro.getId();
	}
	
	// Para redactar el correo que se enviarán a los usuarios
    @GetMapping("/prepararCorreo/{id}")
    public String prepararCorreo(@PathVariable Long id, Model model) {
        // Buscamos el cuadro recién creado para mostrar sus datos
        Cuadro cuadro = cuadroRepository.findById(id).orElse(null);
        
        
        // Si no existe te redirige a la galería
        if (cuadro == null) {
            return "redirect:/galeria"; 
        }
        
        model.addAttribute("cuadro", cuadro);
        return "enviarCorreo"; // Nombre de tu archivo HTML del correo
    }

    // Envía los correos masivos y termina el proceso
    @PostMapping("/enviarAviso")
    public String enviarAviso(@RequestParam Long idCuadro,
                              @RequestParam String asunto,
                              @RequestParam String cuerpo) {
        
        // Buscamos usuarios normales (ROLE_USER)
        List<Usuario> usuarios = usuarioRepository.findByRol("ROLE_USER");
        
        for (Usuario u : usuarios) {
            if (u.getEmail() != null && !u.getEmail().isEmpty()) {
                // Usamos el servicio para enviar
                emailService.enviarCorreoMasivo(u.getEmail(), asunto, cuerpo);
            }
        }
        
        return "redirect:/galeria";
    }

	// Elimina el Cuadro -------------------------
	@GetMapping("/eliminarCuadro/{id}")
	public String eliminarCuadro(@PathVariable("id") Long id) {
		cuadroRepository.deleteById(id);
		return "redirect:/galeria";
	}

	/**
     * Prepara y muestra el formulario para modificar un cuadro existente.
     * Busca el cuadro por su ID y lo añade al modelo para que los campos del formulario aparezcan rellenos.
     * * @param id Identificador del cuadro a editar.
     * @param model Modelo de Spring para pasar el cuadro y las épocas a la vista.
     * @return El nombre de la vista "modificarCuadro".
     */
	@GetMapping("/modificarCuadro/{id}")
	public String modificarCuadro(@PathVariable("id") Long id, Model model) {

		Cuadro cuadroModificar = cuadroRepository.findById(id).orElse(null);

		model.addAttribute("cuadro", cuadroModificar);
		model.addAttribute("epocas", EpocaPintura.values());
		return "modificarCuadro";
	}

	/**
     * Procesa los cambios realizados en el formulario de modificación.
     * Actualiza los datos del cuadro (nombre, autor, época) manteniendo la imagen y el ID originales.
     * * @param cuadro Objeto Cuadro con los datos modificados provenientes del formulario.
     * @param model Modelo de Spring.
     * @return Redirección a la galería con los datos actualizados.
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