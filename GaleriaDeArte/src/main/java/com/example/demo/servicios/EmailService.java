package com.example.demo.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio de infraestructura encargado de la gestión y envío de notificaciones por correo electrónico.
 * <p>
 * Este servicio utiliza la implementación {@link JavaMailSender} de Spring Boot y recupera la configuración SMTP
 * (host, puerto, credenciales) directamente del archivo {@code application.properties}.
 * </p>
 * <p>
 * Su diseño incluye manejo de excepciones interno para garantizar que los fallos en el envío de correos
 * no interrumpan el flujo principal de la aplicación.
 * </p>
 *
 * @author TuNombre
 * @version 1.0
 * @see org.springframework.mail.javamail.JavaMailSender
 */
@Service
public class EmailService {

    /** Logger para el registro de auditoría y trazabilidad de errores. */
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    /** Inyección de la dependencia encargada del envío real mediante protocolo SMTP. */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Dirección de correo electrónico del remitente.
     * Se inyecta automáticamente desde la propiedad {@code spring.mail.username} definida en la configuración.
     */
    @Value("${spring.mail.username}")
    private String remitente;

    /**
     * Envía un correo electrónico de texto plano (Simple Mail) a un destinatario específico.
     * <p>
     * <b>Nota sobre el manejo de errores:</b> Este método encapsula el envío en un bloque {@code try-catch}.
     * Si ocurre un error (problemas de red, autenticación SMTP, etc.), la excepción <b>NO</b> se propaga
     * hacia arriba; en su lugar, se registra en el log como error (`logger.error`).
     * Esto asegura que procesos masivos (como notificar a muchos usuarios) no se detengan por un fallo individual.
     * </p>
     *
     * @param destinatario La dirección de correo electrónico del receptor (ej. "usuario@ejemplo.com").
     * @param asunto       El título o asunto del mensaje.
     * @param cuerpo       El contenido del mensaje en formato de texto plano.
     */
    public void enviarCorreoMasivo(String destinatario, String asunto, String cuerpo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remitente);
            message.setTo(destinatario);
            message.setSubject(asunto);
            message.setText(cuerpo);

            javaMailSender.send(message);
            
            // Registro de éxito (nivel INFO)
            logger.info("Notificación enviada correctamente a: {}", destinatario);
            
        } catch (Exception e) {
            // Registro de fallo (nivel ERROR) - No lanzamos la excepción para no romper el flujo de ejecución
            logger.error("Fallo crítico enviando correo a: {}. Causa: {}", destinatario, e.getMessage());
        }
    }
}