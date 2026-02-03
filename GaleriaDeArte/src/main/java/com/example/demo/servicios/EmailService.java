package com.example.demo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // Leemos el email del remitente desde application.properties
    @Value("${spring.mail.username}")
    private String remitente;

    public void enviarCorreoMasivo(String destinatario, String asunto, String cuerpo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remitente);
            message.setTo(destinatario);
            message.setSubject(asunto);
            message.setText(cuerpo);

            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error enviando correo a: " + destinatario);
            e.printStackTrace();
        }
    }
}