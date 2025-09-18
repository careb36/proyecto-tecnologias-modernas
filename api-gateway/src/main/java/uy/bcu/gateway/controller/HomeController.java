package uy.bcu.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador principal del API Gateway
 * Proporciona una página de bienvenida con enlaces a todos los servicios
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        return ResponseEntity.ok(Map.of(
            "message", "🚀 API Gateway - Proyecto de Tecnologías Modernas",
            "version", "1.0.0",
            "status", "UP",
            "description", "Gateway principal para arquitectura de microservicios",
            "services", Map.of(
                "usuario-service", Map.of(
                    "description", "Gestión de usuarios",
                    "endpoint", "/api/usuarios",
                    "swagger", "/usuario/v3/api-docs"
                ),
                "product-service", Map.of(
                    "description", "Catálogo de productos",
                    "endpoint", "/api/productos",
                    "swagger", "/producto/v3/api-docs"
                ),
                "order-service", Map.of(
                    "description", "Procesamiento de pedidos",
                    "endpoint", "/api/pedidos",
                    "swagger", "/pedido/v3/api-docs"
                ),
                "notification-service", Map.of(
                    "description", "Sistema de notificaciones",
                    "endpoint", "/api/notificaciones",
                    "swagger", "/notification/v3/api-docs"
                )
            ),
            "monitoring", Map.of(
                "grafana", "http://localhost:3000 (admin/admin)",
                "prometheus", "http://localhost:9090",
                "portainer", "http://localhost:9001",
                "sonarqube", "http://localhost:9000 (admin/admin)"
            ),
            "documentation", Map.of(
                "swagger-ui", "/swagger-ui.html",
                "api-docs", "/v3/api-docs",
                "eureka-dashboard", "http://localhost:8761",
                "config-server", "http://localhost:8888"
            )
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "api-gateway",
            "timestamp", java.time.LocalDateTime.now().toString()
        ));
    }
}
