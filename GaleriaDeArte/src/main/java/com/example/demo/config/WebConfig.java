package com.example.demo.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.demo.controladores.AdminController;
import com.example.demo.repository.CuadroRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.servicios.CloudinaryService;
import com.example.demo.servicios.EmailService;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuración de la capa Web MVC para la gestión de la Internacionalización (i18n).
 * <p>
 * Esta clase define cómo la aplicación resuelve y mantiene el idioma seleccionado por el usuario.
 * Utiliza un mecanismo basado en sesiones ({@link SessionLocaleResolver}), lo que permite que
 * la preferencia de idioma persista durante toda la navegación del usuario hasta que cierre el navegador
 * o expire su sesión.
 *
 * @author Jonathan Ibáñez Piñero
 * @see WebMvcConfigurer
 */
@Configuration
@Data
@NoArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    /**
     * Define el mecanismo de resolución de la configuración regional (Locale).
     * <p>
     * Se configura un {@link SessionLocaleResolver} para almacenar la elección del idioma
     * en la sesión HTTP del usuario.
     * @return El bean {@code LocaleResolver} configurado con el español ("es") como idioma predeterminado.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // Establece el Español como idioma por defecto si el usuario no elige ninguno.
        slr.setDefaultLocale(Locale.of("es"));
        return slr;
    }
    
    /**
     * Crea un interceptor que detecta cambios de idioma en las solicitudes HTTP.
     * <p>
     * Este interceptor busca un parámetro específico en la URL (definido como "lang")
     * para cambiar el idioma actual.
     * <br>
     * Ejemplo de uso: {@code localhost:8080/inicio?lang=en} cambiará el idioma a inglés.
     *
     * @return El interceptor {@link LocaleChangeInterceptor} configurado para escuchar el parámetro "lang".
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    /**
     * Registra los interceptores personalizados en el ciclo de vida de las peticiones de Spring MVC.
     * <p>
     * Es necesario sobrescribir este método para que el {@code LocaleChangeInterceptor} definido
     * anteriormente sea efectivo y se ejecute en cada petición entrante.
     *
     * @param intercepto El registro de interceptores donde añadimos nuestra configuración.
     */
    @Override
    public void addInterceptors(InterceptorRegistry intercepto) {
        intercepto.addInterceptor(localeChangeInterceptor());
    }
}