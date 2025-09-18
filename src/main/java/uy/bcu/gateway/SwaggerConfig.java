package uy.bcu.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuración para agregar documentación Swagger de todos los microservicios
 * en el API Gateway
 */
@Component
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        // Agregar documentación de cada microservicio
        resources.add(swaggerResource("Usuario Service", "/usuario/v3/api-docs", "1.0"));
        resources.add(swaggerResource("Product Service", "/producto/v3/api-docs", "1.0"));
        resources.add(swaggerResource("Order Service", "/pedido/v3/api-docs", "1.0"));
        resources.add(swaggerResource("Notification Service", "/notification/v3/api-docs", "1.0"));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
