package uy.bcu.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal del proyecto de aprendizaje de tecnologías
 * Incluye integración con Docker, Oracle, Grafana, Loki y SonarQube
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}