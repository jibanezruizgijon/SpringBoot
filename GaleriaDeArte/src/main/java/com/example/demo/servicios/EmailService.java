package com.example.demo.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import java.util.List;
import com.example.demo.clases.Usuario;

/**
 * Servicio de infraestructura encargado de la gestión y envío de notificaciones por correo electrónico.
 * <p>
 * Este servicio utiliza la implementación {@link JavaMailSender} de Spring Boot.
 * Su diseño incluye manejo de excepciones interno y soporte para ejecución asíncrona,
 * garantizando que los envíos masivos no bloqueen la interfaz de usuario.
 *
 * @author Jonathan Ibáñez Piñero
 * @see org.springframework.mail.javamail.JavaMailSender
 */
@Service
public class EmailService {

    /** Logger notificar errores. */
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    /** Inyección de la dependencia encargada del envío real mediante protocolo SMTP. */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Dirección de correo electrónico del remitente.
     * Se inyecta automáticamente desde la propiedad {@code app.email.remitente}.
     */
    @Value("${app.email.remitente}")
    private String remitente;

    /**
     * Procesa el envío de correos a una lista de usuarios en segundo plano.
     * <p>
     * Al estar anotado con {@code @Async}, este método se ejecuta en un hilo independiente.
     * Esto permite liberar el hilo principal del Controlador inmediatamente, mejorando
     * drásticamente la experiencia de usuario en la aplicación web.
     *
     * @param usuarios Lista de objetos {@link Usuario} destinatarios.
     * @param asunto   Asunto del correo.
     * @param cuerpo   Contenido del mensaje.
     */
    @Async
    public void enviarNotificacionAsync(List<Usuario> usuarios, String asunto, String cuerpo) {
        
        // Mensaje inicial para saber que ha empezado a enviar correos
        logger.info("Inicio del envío en segundo plano a {} usuarios.", usuarios.size());
        
        int contador = 0;

        for (Usuario u : usuarios) {
            // Comprueba que tenga email
            if (u.getEmail() != null && !u.getEmail().isEmpty()) {
                
                // Envía el correo
                this.enviarCorreoMasivo(u.getEmail(), asunto, cuerpo);
                
                contador++;
            }
        }
        
        logger.info("Fin del envío. Se han enviado {} correos.", contador);
    }

    /**
     * Envía un correo electrónico de texto plano a los usuarios.
     * <p>
     * <b>Nota sobre el manejo de errores:</b> Este método encapsula el envío en un bloque {@code try-catch}.
     * Si ocurre un error, se registra en el log pero <b>NO</b> se lanza la excepción,
     * permitiendo que el bucle de envío masivo continúe con el siguiente usuario.
     *
     * @param destinatario La dirección de correo electrónico del receptor.
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
            
        } catch (Exception e) {
            // Registro de fallo en consola
            logger.error("Fallo crítico enviando correo a: {}. Causa: {}", destinatario, e.getMessage());
        }
    }
}