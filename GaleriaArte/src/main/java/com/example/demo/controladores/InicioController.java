package com.example.demo.controladores;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class InicioController {

	@Autowired
	CuadroRepository cuadroRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	VotoRepository votoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Comprueba si están las sesiones o cookies creadas para no volver a
	// registrarse
	// Redirige a la página acceso si ya estaba registrado
	@GetMapping({ "/inicio", "/" })
	public String mostrarInicio() {
		return "inicio";
	}

	// Mostrar página de acceso
	@GetMapping("/acceso")
	public String mostrarAcceso(Model model, Principal principal) {
	    String nombreMostrar = "Invitado";

	    if (principal != null) {
	       
	        String email = principal.getName();
	        
	        
	        Usuario usuario = usuarioRepository.findByEmail(email);
	        
	        
	        if (usuario != null) {
	            nombreMostrar = usuario.getNombre();
	        }
	    }

	    model.addAttribute("mensajeBienvenida", nombreMostrar);
	    return "acceso";
	}

	@GetMapping("/registro")
	public String mostrarRegistro() {
		return "registro";
	}


	@PostMapping("/registro")
	public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
		 
		 if(usuarioRepository.existsByEmail(usuario.getEmail())) {
			 
			 model.addAttribute("cuentaCreada", "Ya existe una cuenta con ese correo ");
			 return "/registro";
		 }
		
	   // Se le asigna el rol user por defecto
	    usuario.setRol("ROLE_USER"); 

	    // Se cifra la contraseña encriptándola
	    String passCifrada = passwordEncoder.encode(usuario.getPassword());
	    usuario.setPassword(passCifrada);

	    
	    usuarioRepository.save(usuario);

	    return "redirect:/inicio"; 
	}

// Método para crear los cuadros la primera vez
	@PostConstruct
	public void inicializarGaleria() {
		
		if (usuarioRepository.findByEmail("admin@admin.com") == null) {
	        Usuario admin = new Usuario();
	        admin.setNombre("Administrador");
	        admin.setEmail("admin@admin.com");
	        admin.setPassword(passwordEncoder.encode("admin123")); 
	        admin.setRol("ROLE_ADMIN");
	        usuarioRepository.save(admin);
	    }
		
		// Solo se crean si está vacía
		if (cuadroRepository.count() == 0) {
			List<Cuadro> galeria = new ArrayList<>();

			galeria.add(new Cuadro("Impresión, sol naciente", "Claude Monet", EpocaPintura.Impresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img1_sldarh.jpg"));
			galeria.add(new Cuadro("Las Meninas", "Diego de Velázquez", EpocaPintura.Barroco,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img2_ctbgrz.jpg"));
			galeria.add(new Cuadro("La Capilla Sixtina", "Miguel Ángel", EpocaPintura.Renacimiento,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img3_vadv5g.png"));
			galeria.add(new Cuadro("El Guernica", "Pablo Picasso", EpocaPintura.Cubismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428753/img4_r8gams.jpg"));
			galeria.add(new Cuadro("La noche estrellada", "Vincent van Gogh", EpocaPintura.Postimpresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428753/img5_q4eiwf.jpg"));
			galeria.add(new Cuadro("El nacimiento de Venus", "Botticelli", EpocaPintura.Renacimiento,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img6_gh0pdd.jpg"));
			galeria.add(new Cuadro("El Jardín de las delicias", "El Bosco", EpocaPintura.Renacimiento,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428754/img7_vpzij7.jpg"));
			galeria.add(new Cuadro("La joven de la perla", "Johannes Vermeer", EpocaPintura.Barroco,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img8_karbbc.jpg"));
			galeria.add(new Cuadro("Composición en rojo, amarillo, azul, blanco y negro", "Piet Mondrian",
					EpocaPintura.Neoplasticismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img9_s9vmyr.jpg"));
			galeria.add(new Cuadro("El grito", "De Munch", EpocaPintura.Expresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img10_diqhjn.png"));
			galeria.add(new Cuadro("El entierro del conde de Orgaz", "El Greco", EpocaPintura.Manierismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768471103/img11_vhhsam.jpg"));
			galeria.add(new Cuadro("El gran siglo", "René Magritte", EpocaPintura.Surrealismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428753/img12_d6nefi.jpg"));
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
			galeria.add(new Cuadro("La lechera", "Vermeer", EpocaPintura.Barroco,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768471338/img20_qiejt9.jpg"));
			galeria.add(new Cuadro("La ciudad", "de Fernand Léger", EpocaPintura.Cubismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428755/img22_vwvplc.jpg"));
			galeria.add(new Cuadro("El columpio", "Fragonard", EpocaPintura.Rococo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428756/img23_rmvtgn.jpg"));
			galeria.add(new Cuadro("Paseo a orillas del mar", "de Joaquín Sorolla", EpocaPintura.Impresionismo,
					"https://res.cloudinary.com/dzjb4fkau/image/upload/v1768428759/img24_monwdu.jpg"));

			cuadroRepository.saveAll(galeria);
		}
	}

}
