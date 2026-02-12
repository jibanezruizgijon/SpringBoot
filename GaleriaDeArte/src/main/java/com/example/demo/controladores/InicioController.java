package com.example.demo.controladores;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.clases.Cuadro;
import com.example.demo.clases.EpocaPintura;
import com.example.demo.clases.Usuario;
import com.example.demo.repository.*;
import com.example.demo.servicios.CloudinaryService;
import com.example.demo.servicios.EmailService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Controlador principal encargado de la gestión de accesos y la página de inicio.
 * <p>
 * Maneja las rutas públicas de la aplicación, el proceso de registro de nuevos usuarios
 * y la redirección post-login. Además, incluye un método de inicialización de datos
 * ({@code @PostConstruct}) que carga la galería con cuadros por defecto si la base de datos está vacía.
 *
 * @author Jonathan Ibáñez Piñero
 */
@Controller
public class InicioController {

	/** Acceso a datos de los cuadros. */
    @Autowired
    CuadroRepository cuadroRepository;

    /** Acceso a datos de usuario */
    @Autowired
    UsuarioRepository usuarioRepository;

    /** Repositorio de votos (necesario para algunas validaciones). */
    @Autowired
    VotoRepository votoRepository;
    
    /** Encriptador de contraseñas para el registro de usuarios. */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Muestra la página de portada.
     * <p>Ruta: {@code /} o {@code /inicio}
     * @return El nombre de la vista {@code inicio.html}.
     */
    @GetMapping({ "/inicio", "/" })
    public String mostrarInicio() {
        return "inicio";
    }

    /**
     * Gestiona la vista de bienvenida tras un inicio de sesión exitoso.
     * <p>
     * Este método detecta automáticamente el tipo de autenticación utilizada (Formulario local o Google OAuth2)
     * para extraer el nombre del usuario y mostrar un mensaje personalizado.
     *
     * @param model     Modelo de datos para pasar atributos a la vista.
     * @param principal Objeto de seguridad que contiene la información del usuario autenticado.
     * @return El nombre de la vista {@code acceso.html}.
     */
    @GetMapping("/acceso")
    public String mostrarAcceso(Model model, Principal principal) {
        String nombreMostrar = "Invitado";
        String email = null; 

        if (principal != null) {
            // Lógica para extraer el email según el proveedor de identidad
            if (principal instanceof OAuth2AuthenticationToken) {
                // Login con Google: El email viene en los atributos del token OIDC
                email = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
            } 
            else if (principal instanceof UsernamePasswordAuthenticationToken) {
                // Login Local: El principal.getName() devuelve el email (username)
                email = principal.getName();
            }
            
            // Recupera el nombre real del usuario desde la BBDD para mostrarlo en la bienvenida
            if (email != null) {
                Usuario usuario = usuarioRepository.findByEmail(email);
                if (usuario != null) {
                    nombreMostrar = usuario.getNombre();
                }
            }
        }

        model.addAttribute("mensajeBienvenida", nombreMostrar);
        return "acceso";
    }

    /**
     * Muestra el formulario de registro para nuevos usuarios.
     * @return El nombre de la vista {@code registro.html}.
     */
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    /**
     * Procesa el formulario de registro de un nuevo usuario local.
     * <p>
     * Realiza las siguientes acciones:
     * <ul>
     * <li>Verifica si el correo electrónico ya está registrado.</li>
     * <li>Asigna el rol por defecto "ROLE_USER".</li>
     * <li>Encripta la contraseña utilizando {@code PasswordEncoder} antes de guardar.</li>
     * </ul>
     *
     * @param usuario Objeto {@link Usuario} con los datos del formulario.
     * @param model   Modelo para enviar mensajes de error si la validación falla.
     * @return Redirección a {@code /inicio} si es exitoso, o vuelta al formulario si hay error.
     */
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
         
         // Validación de duplicados
         if(usuarioRepository.existsByEmail(usuario.getEmail())) {
             model.addAttribute("cuentaCreada", "Error: Ya existe una cuenta asociada a ese correo electrónico.");
             return "/registro";
         }
        
         // Configuración de valores por defecto y seguridad
         usuario.setRol("ROLE_USER"); 
         String passCifrada = passwordEncoder.encode(usuario.getPassword());
         usuario.setPassword(passCifrada);
        
         usuarioRepository.save(usuario);

         return "redirect:/inicio"; 
    }

    /**
     * Inicializa la base de datos con datos de prueba al arrancar la aplicación.
     * <p>
     * Este método se ejecuta automáticamente tras la inyección de dependencias ({@code @PostConstruct}).
     * <ul>
     * <li>Crea un usuario administrador por defecto.</li>
     * <li>Crea una colección inicial de cuadros si la tabla está vacía.</li>
     * </ul>
     */
    @PostConstruct
    public void inicializarGaleria() {
        
        // Crear Administrador por defecto si no existe
        if (usuarioRepository.findByEmail("admin@admin.com") == null) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123")); 
            admin.setRol("ROLE_ADMIN");
            usuarioRepository.save(admin);
        }
        
        // Carga unos cuadros en caso de que la galería esté vacía
        if (cuadroRepository.count() == 0) {
            List<Cuadro> galeria = new ArrayList<>();
            // Carga de cuadros iniciales con imágenes de Cloudinary
            galeria.add(new Cuadro("Impresión, sol naciente", "Claude Monet", EpocaPintura.Impresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img1_sldarh.jpg"));
			galeria.add(
					new Cuadro("Las Meninas", "Diego de Velázquez", EpocaPintura.Barroco, "https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img2_ctbgrz.jpg"));
			galeria.add(new Cuadro("La Capilla Sixtina", "Miguel Ángel", EpocaPintura.Renacimiento,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img3_vadv5g.png"));
			galeria.add(new Cuadro("El Guernica", "Pablo Picasso", EpocaPintura.Cubismo, "https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428753/img4_r8gams.jpg"));
			galeria.add(new Cuadro("La noche estrellada", "Vincent van Gogh", EpocaPintura.Postimpresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428753/img5_q4eiwf.jpg"));
			galeria.add(new Cuadro("El nacimiento de Venus", "Botticelli", EpocaPintura.Renacimiento,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img6_gh0pdd.jpg"));
			galeria.add(new Cuadro("El Jardín de las delicias", "El Bosco", EpocaPintura.Renacimiento,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img7_vpzij7.jpg"));
			galeria.add(new Cuadro("La joven de la perla", "Johannes Vermeer", EpocaPintura.Barroco,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img8_karbbc.jpg"));
			galeria.add(new Cuadro("Composición en rojo, amarillo, azul, blanco y negro", "Piet Mondrian",
					EpocaPintura.Neoplasticismo, "https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img9_s9vmyr.jpg"));
			galeria.add(new Cuadro("El grito", "De Munch", EpocaPintura.Expresionismo, "https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img10_diqhjn.png"));
			galeria.add(new Cuadro("El entierro del conde de Orgaz", "El Greco", EpocaPintura.Manierismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768471103/img11_vhhsam.jpg"));
			galeria.add(
					new Cuadro("El gran siglo", "René Magritte", EpocaPintura.Surrealismo, "https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428753/img12_d6nefi.jpg"));
			galeria.add(new Cuadro("La libertad guiando al pueblo", "Eugène Delacroix", EpocaPintura.Romanticismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img13_wdrwxk.jpg"));
			galeria.add(new Cuadro("La Gran ola de Kanagaza", "Katsushika Hokusai", EpocaPintura.Arte_oriental,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768471606/img14_rewg0r.jpg"));
			galeria.add(new Cuadro("La persistencia de la memoria", "Salvador Dalí", EpocaPintura.Surrealismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img15_g26gwq.jpg"));
			galeria.add(new Cuadro("La última cena", "Leonardo da Vinci", EpocaPintura.Renacimiento,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img16_pgmsnm.jpg"));
			galeria.add(new Cuadro("Los girasoles", "Vincent Van Gogh", EpocaPintura.Postimpresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428756/img17_aa0hdt.jpg"));
			galeria.add(new Cuadro("Saturno devorando a su hijo", "Goya", EpocaPintura.Romanticismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img18_saijax.jpg"));
			galeria.add(new Cuadro("Autorretrato", "Vincent Van Gogh", EpocaPintura.Postimpresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768471263/img19_zjdonz.jpg"));
			galeria.add(new Cuadro("La lechera", "Vermeer", EpocaPintura.Barroco, "https://res.cloudinary.com/dzjb4fkau/image/upload/v1768471338/img20_qiejt9.jpg"));
			galeria.add(new Cuadro("La ciudad", "de Fernand Léger", EpocaPintura.Cubismo, "https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img22_vwvplc.jpg"));
			galeria.add(new Cuadro("El columpio", "Fragonard", EpocaPintura.Rococo, "https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428756/img23_rmvtgn.jpg"));
			galeria.add(new Cuadro("Paseo a orillas del mar", "de Joaquín Sorolla", EpocaPintura.Impresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428759/img24_monwdu.jpg"));
            cuadroRepository.saveAll(galeria);
        }
    }
}
