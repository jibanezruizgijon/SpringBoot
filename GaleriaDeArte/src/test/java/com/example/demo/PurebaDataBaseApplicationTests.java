package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Clase de pruebas de integración para la aplicación.
 * <p>
 * Verifica que el contexto de Spring Boot se inicia correctamente y que
 * los componentes principales están disponibles.
 */
@SpringBootTest
class PurebaDataBaseApplicationTests {

    /**
     * Constructor por defecto para la clase de pruebas.
     */
    public PurebaDataBaseApplicationTests() {
        super();
    }

    /**
     * Prueba básica de carga de contexto ("Smoke Test").
     * <p>
     * Este método fallará si la aplicación no puede arrancar (por ejemplo,
     * si falta alguna configuración en application.properties o falla una inyección).
     */
    @Test
    void contextLoads() {
    }

}
