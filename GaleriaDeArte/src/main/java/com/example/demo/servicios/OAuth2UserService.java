
package com.example.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.clases.Usuario;
import com.example.demo.repository.UsuarioRepository;

/**
 * Servicio personalizado para la gestión de la autenticación mediante OAuth2 (Login con Google).
 * <p>
 * Esta clase extiende {@link DefaultOAuth2UserService} para interceptar el momento en que un usuario
 * inicia sesión exitosamente con un proveedor externo (Google). Su función principal es sincronizar
 * el usuario de Google con la base de datos local (Auto-Registro).
 *
 * @author Jonathan Ibáñez Piñero
 * @see org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
 */
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

	/** Repositorio para buscar o guardar usuarios que vienen de Google. */
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /** * Codificador de contraseñas.
     * Se usa la anotación {@code @Lazy} para evitar una dependencia circular ("Circular Dependency Injection"),
     * ya que el SecurityConfig suele depender de los servicios de usuario, y este servicio depende
     * del encoder definido en SecurityConfig.
     */
    @Autowired
    @Lazy 
    private PasswordEncoder passwordEncoder;

    /**
     * Método principal que carga los datos del usuario tras una autenticación exitosa en el proveedor OAuth2.
     * <p>
     * Este método realiza las siguientes acciones:
     * <ol>
     * <li>Invoca al método padre para obtener los datos crudos del usuario desde Google.</li>
     * <li>Extrae el email y el nombre del usuario.</li>
     * <li>Verifica si el usuario ya existe en la base de datos local {@code usuarioRepository}.</li>
     * <li><b>Si el usuario NO existe (primera vez):</b> Lo crea automáticamente, asignándole el rol "ROLE_USER"
     * y una contraseña interna protegida.</li>
     * </ol>
     *
     * @param userRequest Objeto que contiene la solicitud de acceso y el token del usuario.
     * @return Un objeto {@link OAuth2User} con los detalles del usuario autenticado.
     * @throws OAuth2AuthenticationException Si ocurre un error al conectar con el proveedor de identidad.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Carga el usuario de Google utilizando la implementación por defecto de Spring Security
        OAuth2User user = super.loadUser(userRequest);

        // Extrae los datos necesarios del perfil de Google
        String email = user.getAttribute("email");
        String nombreGoogle = user.getAttribute("name");

        // Comprueba si el usuario ya existe en nuestra base de datos local
        Usuario usuarioExistente = usuarioRepository.findByEmail(email);

        if (usuarioExistente == null) {
            // Lógica de Auto-Registro: Si no existe, creamos el usuario en nuestra BBDD
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setNombre(nombreGoogle);
            
            // Asigna una contraseña interna codificada para cumplir con la estructura de la entidad Usuario.
            nuevoUsuario.setPassword(passwordEncoder.encode("GOOGLE_AUTH_HIDDEN"));
            
            nuevoUsuario.setRol("ROLE_USER");
            
            usuarioRepository.save(nuevoUsuario);
        }

        return user;
    }
}