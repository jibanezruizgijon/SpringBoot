package com.example.demo.servicios;

import com.example.demo.clases.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación personalizada del servicio de seguridad de Spring Security.
 * Se encarga de cargar los datos del usuario desde la base de datos para la autenticación.
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga un usuario basándose en su dirección de correo electrónico (usada como username).
     *
     * @param email El correo electrónico del usuario que intenta iniciar sesión.
     * @return Un objeto UserDetails compatible con Spring Security con los datos y roles del usuario.
     * @throws UsernameNotFoundException Si no se encuentra ningún usuario con el email proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepository.findByEmail(email);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        // Construimos el usuario de Spring Security.
        // Nota: Spring espera que los roles no tengan el prefijo "ROLE_" dentro del método .roles(),
        // por eso lo reemplazamos si viene guardado así en la BBDD.
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword()) 
                .roles(usuario.getRol().replace("ROLE_", "")) 
                .build();
    }
}