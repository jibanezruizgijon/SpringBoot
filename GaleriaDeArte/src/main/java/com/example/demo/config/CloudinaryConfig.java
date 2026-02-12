package com.example.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.controladores.AdminController;
import com.example.demo.repository.CuadroRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.servicios.CloudinaryService;
import com.example.demo.servicios.EmailService;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
/**
 * Clase de configuración para la integración del servicio Cloudinary.
 * <p>
 * Esta clase inicializa el cliente de Cloudinary utilizando las credenciales (API Key, Secret, Cloud Name)
 * definidas en el archivo de propiedades {@code application.properties}.
 * Permite que la aplicación web suba y gestione imágenes en Cloudinary.
 * @author Jonathan Ibáñez Piñero
 * @see com.example.demo.servicios.CloudinaryService
 */
@Configuration
public class CloudinaryConfig {

    /** Nombre de cloudinary añadido como variable de entorno */
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    /** Clave pública de la API  añadida como variable de entorno. */
    @Value("${cloudinary.api_key}")
    private String apiKey;

    /** Clave secreta de la API añadida como variable de entorno. */
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    /**
     * Crea y configura el bean cliente de Cloudinary.
     * <p>
     * Este objeto será inyectado posteriormente en los servicios (como {@code CloudinaryService})
     * para realizar las operaciones de subida de archivos.
     *
     * @return Una instancia de {@link Cloudinary} configurada con las credenciales de la cuenta.
     */
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret));
    }
}