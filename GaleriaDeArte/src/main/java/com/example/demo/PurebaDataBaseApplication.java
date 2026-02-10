package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Clase principal de entrada para la aplicación Spring Boot "GaleriaArteBBDD".
 * <p>
 * Esta clase es responsable de arrancar el contexto de Spring, la configuración automática
 * y el servidor web embebido (Tomcat).
 * <p>
 * Además de la configuración estándar de {@link SpringBootApplication}, se habilita explícitamente
 * la ejecución asíncrona mediante {@link EnableAsync}. Esto permite que métodos anotados con {@code @Async}
 * (como el envío masivo de correos) se ejecuten en hilos separados para no bloquear la interfaz de usuario.

 *
 * @author Jonathan Ibáñez Piñero
 * @see org.springframework.scheduling.annotation.EnableAsync
 */
@SpringBootApplication
@EnableAsync
public class PurebaDataBaseApplication {

    /**
     * Método principal (Main) que inicia la ejecución de la aplicación.
     * <p>
     * Delega el control a {@link SpringApplication#run} para inicializar el contenedor de Spring.
     *
     * @param args Argumentos de línea de comandos pasados al arrancar el programa.
     */
    public static void main(String[] args) {
        SpringApplication.run(PurebaDataBaseApplication.class, args);
    }

}