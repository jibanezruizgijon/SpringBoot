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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.repository.UsuarioRepository;

/**
 * Controlador de Administrador
 * <p>
 * Gestiona las acciones que solo el rol ADMIN puede realizar:
 * <ul>
 * <li>Crear, Eliminar y Modificar Cuadros.</li>
 * <li>Envío de notificaciones por correo electrónico.</li>
 * </ul>
 *
 * @author Jonathan Ibáñez Piñero
 */
@Controller

public class AdminController {
	
	/** Servicio para la gestión de almacenamiento de imágenes en la nube. */
	@Autowired
	private CloudinaryService cloudinaryService;

	/** Acceso a datos de los cuadros. */
	@Autowired
	private CuadroRepository cuadroRepository;

	/** Acceso a datos de usuario */
	@Autowired
	private UsuarioRepository usuarioRepository;

	/** Servicio de envío de correos. */
	@Autowired
	private EmailService emailService;

	/**
	 * Muestra el formulario para crear un nuevo cuadro.
	 * 
	 * @param model Modelo de datos.
	 * @return La vista {@code nuevoCuadro.html}.
	 */
	@GetMapping("/nuevoCuadro")
	public String formularioCuadro(Model model) {
		model.addAttribute("epocas", EpocaPintura.values());
		return "nuevoCuadro";
	}

	/**
	 * Procesa la creación de un nuevo cuadro y gestiona la subida de su imagen a Cloudinary.
	 * <ol>
	 * <li>Sube la imagen recibida a Cloudinary y obtiene su URL pública.</li>
	 * <li>Crea el objeto {@code Cuadro} con los datos y la URL.</li>
	 * <li>Guarda la ruta del cuadro en la base de datos.</li>
	 * <li>Redirige a la pantalla de redacción de correo para notificar.</li>
	 * </ol>
	 *
	 * @param nombre        Título de la obra.
	 * @param autor         Nombre del artista.
	 * @param epoca         Categoría histórica/artística.
	 * @param imagen        Archivo de la imagen.
	 * @param redirectAttrs Atributos para pasar mensajes entre redirecciones.
	 * @return Redirección a {@code /prepararCorreo/{id}}.
	 */
	@PostMapping("/guardarCuadro")
	public String guardarCuadro(@RequestParam("nombre") String nombre, @RequestParam("autor") String autor,
			@RequestParam("epoca") EpocaPintura epoca, @RequestParam("imagen") MultipartFile imagen,
			RedirectAttributes redirectAttrs) {

		String urlImagen = null;

		try {
			// Delega la subida al servicio externo
			urlImagen = cloudinaryService.subirImagen(imagen);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/nuevo-cuadro?error=fallo_subida";
		}

		Cuadro nuevoCuadro = new Cuadro(nombre, autor, epoca, urlImagen);
		cuadroRepository.save(nuevoCuadro);

		// Redirección para notificar a los usuarios del nuevo cuadro
		return "redirect:/prepararCorreo/" + nuevoCuadro.getId();
	}

	/**
	 * Muestra la vista de previsualización para enviar un correo a todos los usuarios sobre un
	 * cuadro nuevo.
	 * 
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
	 * Ejecuta el envío de correos electrónicos a todos los usuarios
	 * registrados.
	 * <p>
	 * Recupera todos los usuarios con rol 'ROLE_USER' 
	 * Envía la notificación utilizando {@link EmailService}.
	 *
	 * @param idCuadro ID del cuadro .
	 * @param asunto   Asunto del correo electrónico.
	 * @param cuerpo   Cuerpo del mensaje.
	 * @return Redirección a la galería.
	 */
	@PostMapping("/enviarAviso")
	public String enviarAviso(
			@RequestParam Long idCuadro,
			@RequestParam String asunto, 
			@RequestParam String cuerpo) {

		// Obtengo la lista de todos los usuarios con rol USER
		List<Usuario> usuarios = usuarioRepository.findByRol("ROLE_USER");

		emailService.enviarNotificacionAsync(usuarios, asunto, cuerpo);

		return "redirect:/galeria";
	}

	/**
	 * Elimina un cuadro de la base de datos por su ID.
	 * 
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
	 * 
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
	 * <b>Nota:</b> Este método solo actualiza los datos de texto. La imagen no
	 * se modifica en esta operación.
	 * 
	 * @param cuadro Objeto con los nuevos datos
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