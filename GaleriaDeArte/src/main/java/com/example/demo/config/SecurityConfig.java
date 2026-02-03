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
import com.example.demo.servicios.UserDetailsServiceImp;


/*
 * 
 * Configuración
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
    private UserDetailsServiceImp userDetailsService;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		// Desactivar CSRF para la API hace que si un usuario entra en swagger no puede usar POST/PUT/DELETE
		.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

        .authorizeHttpRequests((requests) -> requests
            // --- RUTAS PÚBLICAS  ---
            .requestMatchers("/", "/inicio", "/registro", "/css/**", "/js/**" , "/imagenesLogos/**", "/filtrar", "/procesarBusqueda").permitAll()

            // --- ACCESO A SWAGGER  ---
            // Permitimos que ADMIN y USER entren a ver la página de Swagger
            .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").hasAnyRole("ADMIN", "USER")

            // --- REGLAS DE LA API REST ---
            // Métodos GET (Buscar, Filtrar, Ver): Permitido a ADMIN y USER
            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")
            
            //  Resto de métodos (Crear, Borrar, Modificar): Solo ADMIN
            .requestMatchers("/api/**").hasRole("ADMIN")

            // --- Rutas al controlador donde solo tiene acceso el admin ---
            .requestMatchers("/nuevoCuadro", "/guardarCuadro", "/eliminarCuadro", "/modificarCuadro").hasRole("ADMIN")
            
            // --- RESTO ---
            .anyRequest().authenticated())
            .formLogin((form) -> form
                .loginPage("/inicio")
                .loginProcessingUrl("/login")
                .usernameParameter("email") 
                .defaultSuccessUrl("/acceso", true)
                .permitAll())
            
            //Para cerrar Sesión
            .logout((logout) -> logout
                .logoutRequestMatcher(new RegexRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/inicio?logout")
                .permitAll());

        return http.build();
    }

	// Encripta la contraseña
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
	    //  Se usa el contructor vacío
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    
	    // Se asigna el servicio con el setter
	    authProvider.setUserDetailsService(userDetailsService);
	    
	    // Se encripta la contraseña
	    authProvider.setPasswordEncoder(passwordEncoder);
	    
	    return authProvider;
	}
}
