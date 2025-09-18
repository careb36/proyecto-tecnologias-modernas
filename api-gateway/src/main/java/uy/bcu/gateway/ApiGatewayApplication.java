package uy.bcu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * API Gateway - Punto de entrada único para todos los microservicios
 * Incluye routing, load balancing y agregación de Swagger
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Usuario Service
                .route("usuario-service", r -> r.path("/api/usuarios/**")
                        .uri("lb://usuario-service"))

                // Product Service
                .route("product-service", r -> r.path("/api/productos/**")
                        .uri("lb://product-service"))

                // Order Service
                .route("order-service", r -> r.path("/api/pedidos/**")
                        .uri("lb://order-service"))

                // Notification Service
                .route("notification-service", r -> r.path("/api/notificaciones/**")
                        .uri("lb://notification-service"))

                // Swagger UI routes para cada servicio
                .route("usuario-swagger", r -> r.path("/usuario/v3/api-docs")
                        .uri("lb://usuario-service"))
                .route("product-swagger", r -> r.path("/producto/v3/api-docs")
                        .uri("lb://product-service"))
                .route("order-swagger", r -> r.path("/pedido/v3/api-docs")
                        .uri("lb://order-service"))
                .route("notification-swagger", r -> r.path("/notification/v3/api-docs")
                        .uri("lb://notification-service"))

                .build();
    }
}
