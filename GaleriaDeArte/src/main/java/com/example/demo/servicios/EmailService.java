package com.example.demo.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del envío de correos electrónicos.
 * Utiliza la configuración SMTP definida en application.properties.
 */
@Service
public class EmailService {

    // Logger para registrar eventos y errores de forma profesional
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    // Leemos el email del remitente desde application.properties
    @Value("${spring.mail.username}")
    private String remitente;

    /**
     * Envía un correo electrónico simple a un destinatario específico.
     * Gestiona internamente las excepciones para no detener la ejecución del programa si el envío falla.
     *
     * @param destinatario La dirección de correo electrónico del receptor.
     * @param asunto       El asunto del correo.
     * @param cuerpo       El contenido o cuerpo del mensaje.
     */
    public void enviarCorreoMasivo(String destinatario, String asunto, String cuerpo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remitente);
            message.setTo(destinatario);
            message.setSubject(asunto);
            message.setText(cuerpo);

            javaMailSender.send(message);
            
            // Registro de éxito en los logs
            logger.info("Correo enviado correctamente a: {}", destinatario);
            
        } catch (Exception e) {
            // Registro del error con traza completa en los logs
            logger.error("Error crítico enviando correo a: {}. Causa: {}", destinatario, e.getMessage());
        }
    }
}