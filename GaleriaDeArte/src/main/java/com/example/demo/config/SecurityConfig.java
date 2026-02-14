package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.example.demo.controladores.AdminController;
import com.example.demo.repository.CuadroRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.servicios.CloudinaryService;
import com.example.demo.servicios.EmailService;
import com.example.demo.servicios.OAuth2UserService;
import com.example.demo.servicios.UserDetailsServiceImp;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Clase principal de configuración de Seguridad (Spring Security).
 * <p>
 * Define la cadena de filtros de seguridad, las reglas de autorización por roles,
 * la configuración del formulario de login y la integración con OAuth2 (Google).
 *
 * @author Jonathan Ibáñez Piñero
 * @see org.springframework.security.web.SecurityFilterChain
 */
@Configuration
@EnableWebSecurity
@Data
@NoArgsConstructor
public class SecurityConfig {
    
	/** Servicio personalizado para gestionar el login con Google. */
    @Autowired
    private OAuth2UserService oauthServicio;

    /** Servicio para cargar detalles de usuarios desde la base de datos. */
    @Autowired
    private UserDetailsServiceImp userDetailsService;
    
    /**
     * Define la cadena de filtros de seguridad (Security Filter Chain).
     * <p>
     *  Políticas de acceso HTTP:
     * <ul>
     * <li>Desactivación de CSRF para endpoints de la API REST.</li>
     * <li>Rutas de URL públicas (css, js, inicio, registro).</li>
     * <li>Restricciones de acceso basadas en roles (ADMIN vs USER) para Swagger y la API.</li>
     * <li>Configuración del inicio de sesión dual (Formulario clásico + OAuth2 Google).</li>
     * <li>Configuración del cierre de sesión (Logout).</li>
     * </ul>
     *
     * @param http El objeto {@link HttpSecurity} que permite configurar la seguridad web.
     * @return La cadena de filtros construida y lista para ser gestionada por Spring.
     * @throws Exception Si ocurre algún error durante la configuración de las reglas.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        // Desactivar CSRF para rutas de la API (/api/**) permite que herramientas externas (Postman, Swagger)
        // puedan realizar peticiones POST/PUT/DELETE sin necesidad de tokens CSRF.
        .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

        .authorizeHttpRequests((requests) -> requests
            // Ruta a la que pueden acceder todos los roles
            // Permitimos acceso total a recursos estáticos (CSS, JS, Imágenes) y páginas de aterrizaje.
            .requestMatchers("/", "/inicio", "/registro", "/css/**", "/js/**" , "/imagenesLogos/**", "/filtrar", "/procesarBusqueda").permitAll()

            //Ruta para swagger
            // La documentación de la API es accesible para usuarios autenticados
            .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").authenticated()

            // API Rest
            // Los métodos de lectura (GET) son públicos para usuarios autenticados.
            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/**").authenticated()
            
            // Los métodos de escritura (POST, PUT, DELETE) son exclusivos del ADMIN.
            .requestMatchers("/api/**").hasRole("ADMIN")

            //  ZONA ADMINISTRATIVA WEB
            // Rutas de controladores MVC que modifican datos sensibles.
            .requestMatchers("/nuevoCuadro", "/guardarCuadro", "/eliminarCuadro", "/modificarCuadro").hasRole("ADMIN")
            
            //  REGLA POR DEFECTO
            // Cualquier otra petición no listada arriba requiere autenticación.
            .anyRequest().authenticated())
            
            // CONFIGURACIÓN DE LOGIN (Formulario) 
            .formLogin((form) -> form
                .loginPage("/inicio") 
                .loginProcessingUrl("/login") 
                .usernameParameter("email") 
                .defaultSuccessUrl("/acceso", true) 
                .permitAll())
            
            // CONFIGURACIÓN DE OAUTH2 (Login con Google) 
            .oauth2Login(oauth2 -> oauth2
                    .loginPage("/inicio") 
                    .defaultSuccessUrl("/acceso", true)
                    .userInfoEndpoint(userInfo -> userInfo
                        .userService(oauthServicio) // Servicio personalizado para procesar el usuario de Google
                    )
                )
            
            //  CONFIGURACIÓN DE LOGOUT 
            .logout((logout) -> logout
                // permite logout mediante petición GET (útil para enlaces simples en HTML)
                .logoutRequestMatcher(new RegexRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/inicio?logout") 
                .permitAll());

        return http.build();
    }

    /**
     * Define el algoritmo de encriptación de contraseñas.
     * <p>
     * Se utiliza {@link BCryptPasswordEncoder}, es el estándar actual recomendado por Spring Security
     * por ser un algoritmo de hash adaptativo y seguro.
     *
     * @return Una instancia del codificador BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el proveedor de autenticación DAO.
     * <p>
     * Este componente conecta el servicio de usuarios (UserDetailsService) con el sistema de seguridad,
     * indicándole cómo buscar usuarios en la base de datos y cómo verificar sus contraseñas encriptadas.
     *
     * @param userDetailsService El servicio que carga los datos del usuario desde la BBDD.
     * @param passwordEncoder    El codificador para verificar que la contraseña ingresada coincide con la hash guardada.
     * @return El proveedor de autenticación configurado.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}