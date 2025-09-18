package uy.bcu.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Usuario Service - Microservicio para gestión de usuarios
 * Incluye documentación Swagger/OpenAPI completa
 */
@SpringBootApplication
@EnableEurekaClient
public class UsuarioServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsuarioServiceApplication.class, args);
    }
}
