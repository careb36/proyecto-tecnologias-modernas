# Swagger/OpenAPI - Guía de Aprendizaje

## ¿Qué es Swagger/OpenAPI?

OpenAPI (anteriormente Swagger) es una especificación para describir APIs REST de manera estándar. Swagger es el conjunto de herramientas que implementa esta especificación.

## Conceptos Clave

### 1. OpenAPI Specification (OAS)
Estándar para describir APIs REST con:
- Endpoints disponibles
- Métodos HTTP
- Parámetros y respuestas
- Modelos de datos
- Autenticación

### 2. Swagger UI
Interfaz web interactiva que permite:
- Visualizar la documentación
- Probar endpoints directamente
- Generar ejemplos de requests/responses

### 3. Annotations (Anotaciones)
Etiquetas en el código que generan automáticamente la documentación:
- `@Tag`: Agrupa endpoints
- `@Operation`: Describe un endpoint específico
- `@Schema`: Define modelos de datos
- `@Parameter`: Describe parámetros
- `@ApiResponse`: Define respuestas posibles

## Implementación en Microservicios

### 1. Arquitectura de Microservicios con Swagger
```
API Gateway (8080) ← Swagger Agregado
├── Usuario Service (8081) ← Swagger Individual
├── Product Service (8082) ← Swagger Individual
├── Order Service (8083) ← Swagger Individual
└── Notification Service (8084) ← Swagger Individual
```

### 2. Configuración Base
En cada microservicio se incluye:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.9</version>
</dependency>
```

### 3. Configuración en application.yml
```yaml
springdoc:
  api-docs:
    path: /usuario/v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
  info:
    title: Usuario Service API
    description: Microservicio para gestión de usuarios
    version: 1.0.0
```

## Anotaciones Principales

### 1. @Tag - Agrupación de Endpoints
```java
@RestController
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {
    // ...
}
```

### 2. @Operation - Descripción de Operaciones
```java
@GetMapping("/{id}")
@Operation(
    summary = "Obtener usuario por ID",
    description = "Retorna los datos completos de un usuario específico"
)
public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
    // ...
}
```

### 3. @Parameter - Descripción de Parámetros
```java
@GetMapping
public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios(
    @Parameter(description = "Número de página (empezando en 0)")
    @RequestParam(defaultValue = "0") int page,

    @Parameter(description = "Tamaño de página")
    @RequestParam(defaultValue = "10") int size) {
    // ...
}
```

### 4. @ApiResponses - Respuestas Posibles
```java
@PostMapping
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDTO.class))),
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
    @ApiResponse(responseCode = "409", description = "Email ya existe")
})
public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
    // ...
}
```

### 5. @Schema - Modelos de Datos
```java
@Schema(description = "Información completa del usuario")
public class UsuarioDTO {

    @Schema(description = "ID único del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", required = true)
    private String nombre;

    @Schema(description = "Email único del usuario", example = "juan@ejemplo.com", format = "email")
    private String email;
}
```

## Servicios Implementados

### 1. API Gateway (Puerto 8080)
- **URL Principal**: http://localhost:8080/swagger-ui.html
- **Agregación**: Documenta todos los microservicios
- **Routing**: Enruta requests a servicios específicos

### 2. Usuario Service (Puerto 8081)
- **Swagger URL**: http://localhost:8081/swagger-ui.html
- **API Docs**: http://localhost:8081/usuario/v3/api-docs
- **Endpoints**:
  - `GET /api/usuarios` - Listar usuarios
  - `POST /api/usuarios` - Crear usuario
  - `GET /api/usuarios/{id}` - Obtener por ID
  - `PUT /api/usuarios/{id}` - Actualizar usuario
  - `DELETE /api/usuarios/{id}` - Desactivar usuario
  - `GET /api/usuarios/buscar` - Búsqueda avanzada

### 3. Product Service (Puerto 8082)
- **Swagger URL**: http://localhost:8082/swagger-ui.html
- **API Docs**: http://localhost:8082/producto/v3/api-docs
- **Endpoints**:
  - `GET /api/productos` - Catálogo de productos
  - `POST /api/productos` - Crear producto
  - `PUT /api/productos/{id}/stock` - Actualizar stock
  - `GET /api/productos/categorias` - Listar categorías
  - `GET /api/productos/buscar` - Búsqueda de productos

### 4. Order Service (Puerto 8083)
- **Swagger URL**: http://localhost:8083/swagger-ui.html
- **Endpoints**: Procesamiento de pedidos

### 5. Notification Service (Puerto 8084)
- **Swagger URL**: http://localhost:8084/swagger-ui.html
- **Endpoints**: Gestión de notificaciones

## Ejercicios Prácticos

### Ejercicio 1: Explorar Swagger UI
1. Inicia todos los microservicios:
```bash
docker-compose up -d
```

2. Accede a cada Swagger UI:
- API Gateway: http://localhost:8080/swagger-ui.html
- Usuario Service: http://localhost:8081/swagger-ui.html
- Product Service: http://localhost:8082/swagger-ui.html

3. Explora la documentación interactiva
4. Prueba diferentes endpoints directamente desde Swagger UI

### Ejercicio 2: Probar APIs desde Swagger
1. Ve al Usuario Service Swagger UI
2. Expande `POST /api/usuarios`
3. Click en "Try it out"
4. Completa el JSON de ejemplo:
```json
{
  "nombre": "Ana García",
  "email": "ana.garcia@test.com",
  "rol": "USER",
  "activo": true
}
```
5. Ejecuta la request
6. Observa la respuesta y el código HTTP

### Ejercicio 3: Documentar un Nuevo Endpoint
1. Agrega un nuevo método al UsuarioController:
```java
@GetMapping("/activos/count")
@Operation(
    summary = "Contar usuarios activos",
    description = "Retorna el número total de usuarios activos en el sistema"
)
@ApiResponse(responseCode = "200", description = "Conteo realizado exitosamente")
public ResponseEntity<Map<String, Long>> contarUsuariosActivos() {
    return ResponseEntity.ok(Map.of("count", 150L));
}
```

2. Reinicia el servicio
3. Verifica que aparezca en Swagger UI

### Ejercicio 4: Validar Modelos de Datos
1. Ve a la sección "Schemas" en Swagger UI
2. Encuentra `UsuarioDTO` y `UsuarioCreateDTO`
3. Observa las validaciones y ejemplos
4. Prueba crear un usuario con datos inválidos
5. Observa los mensajes de error de validación

## Configuración Avanzada

### 1. Personalizar Información de la API
```java
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Usuario Service API")
                .version("1.0.0")
                .description("Microservicio para gestión de usuarios")
                .contact(new Contact()
                    .name("Equipo de Desarrollo")
                    .email("dev@empresa.com")
                    .url("https://empresa.com"))
                .license(new License()
                    .name("MIT")
                    .url("https://opensource.org/licenses/MIT")));
    }
}
```

### 2. Agrupar Endpoints por Tags
```java
@Tag(name = "Gestión de Usuarios", description = "Operaciones CRUD de usuarios")
@Tag(name = "Búsquedas", description = "Endpoints de búsqueda y filtrado")
@Tag(name = "Estadísticas", description = "Métricas y reportes")
```

### 3. Documentar Autenticación
```java
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
```

### 4. Ejemplos Complejos
```java
@Schema(example = """
{
  "nombre": "María González",
  "email": "maria.gonzalez@empresa.com",
  "rol": "MANAGER",
  "activo": true
}
""")
public class UsuarioCreateDTO {
    // ...
}
```

## Integración con API Gateway

### 1. Agregación de Documentación
El API Gateway reúne toda la documentación de los microservicios:

```java
@Component
public class SwaggerConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("Usuario Service", "/usuario/v3/api-docs", "1.0"));
        resources.add(swaggerResource("Product Service", "/producto/v3/api-docs", "1.0"));
        return resources;
    }
}
```

### 2. Routing de Documentación
```java
@Bean
public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("usuario-swagger", r -> r.path("/usuario/v3/api-docs")
                .uri("lb://usuario-service"))
        .route("product-swagger", r -> r.path("/producto/v3/api-docs")
                .uri("lb://product-service"))
        .build();
}
```

## Mejores Prácticas

### 1. Documentación Clara
- Usar descripciones detalladas
- Incluir ejemplos realistas
- Documentar todos los casos de error
- Especificar formatos de datos

### 2. Consistencia
- Nomenclatura uniforme en toda la API
- Códigos de respuesta estándar
- Estructura similar en todos los servicios

### 3. Mantenimiento
- Mantener documentación actualizada
- Versionar APIs apropiadamente
- Incluir changelog de cambios
- Testing regular de ejemplos

### 4. Seguridad
- No exponer información sensible
- Documentar esquemas de autenticación
- Validar inputs adecuadamente

## Comparación con Alternativas

| Feature | Swagger/OpenAPI | Postman | Insomnia | API Blueprint |
|---------|----------------|---------|-----------|---------------|
| **Standard** | ✅ OpenAPI | ❌ Propio | ❌ Propio | ✅ API Blueprint |
| **Interactive** | ✅ | ✅ | ✅ | ❌ |
| **Code Generation** | ✅ | ✅ | ❌ | ✅ |
| **Auto-generation** | ✅ | ❌ | ❌ | ❌ |
| **Testing** | ✅ | ✅ | ✅ | ❌ |

## 📚 Recursos de Aprendizaje y Referencias

### Especificaciones y Estándares

#### OpenAPI Specification
- **📖 [OpenAPI 3.0 Specification](https://swagger.io/specification/)** - Especificación oficial completa
- **🎓 [OpenAPI Tutorial](https://swagger.io/docs/specification/basic-structure/)** - Guía paso a paso
- **📖 [OpenAPI Examples](https://swagger.io/docs/specification/examples/)** - Ejemplos prácticos

#### Herramientas de Diseño
- **🎨 [Swagger Editor](https://editor.swagger.io/)** - Editor online interactivo
- **📝 [SwaggerHub](https://swaggerhub.com/)** - Plataforma colaborativa
- **🔧 [OpenAPI Generator](https://openapi-generator.tech/)** - Generador de código

### Documentación Técnica

#### SpringDoc OpenAPI
- **📖 [SpringDoc Documentation](https://springdoc.org/)** - Documentación completa
- **🎓 [SpringDoc Guides](https://springdoc.org/#springdoc-openapi-gradle-plugin)** - Guías de configuración
- **📖 [SpringDoc GitHub](https://github.com/springdoc/springdoc-openapi)** - Código fuente y ejemplos

#### Swagger Tools
- **📖 [Swagger UI](https://swagger.io/tools/swagger-ui/)** - Interfaz de documentación
- **🔧 [Swagger Codegen](https://swagger.io/tools/swagger-codegen/)** - Generador de clientes
- **📊 [Swagger Stats](https://swagger.io/tools/swagger-stats/)** - Métricas de APIs

### Libros y Cursos

#### Libros Recomendados
- **"API Design Patterns" - JJ Geewax**
  - **📚 [Comprar](https://www.amazon.com/API-Design-Patterns-JJ-Geewax/dp/1617295856)**
  - **Contenido**: Patrones de diseño para APIs REST

- **"REST API Design Rulebook" - Mark Masse**
  - **📚 [Versión Digital](https://www.oreilly.com/library/view/rest-api-design/9781449317904/)**
  - **Contenido**: Reglas y mejores prácticas para APIs REST

#### Cursos Online
- **API Design with OpenAPI** (Udemy)
- **REST API Design** (Coursera)
- **Swagger/OpenAPI Fundamentals** (Pluralsight)
- **Building APIs with OpenAPI** (LinkedIn Learning)

### Comunidades y Blogs

#### Comunidades
- **OpenAPI Initiative**: [GitHub Organization](https://github.com/OAI)
- **API Evangelist**: [Blog sobre APIs](https://apievangelist.com/)
- **Stack Overflow**: Etiquetas `openapi`, `swagger`, `api-design`
- **Reddit**: r/api_design, r/programming

#### Blogs Técnicos
- **Swagger Blog**: [Actualizaciones y tutoriales](https://swagger.io/blog/)
- **API Design Blog**: [Patrones y mejores prácticas](https://www.apidesignbook.com/)
- **Phil Sturgeon**: [Experto en APIs](https://philsturgeon.uk/)

### Herramientas Complementarias

#### Testing de APIs
- **Postman**: [Plataforma de testing](https://www.postman.com/)
- **Insomnia**: [Cliente REST alternativo](https://insomnia.rest/)
- **REST Client (VS Code)**: Extensión para testing

#### Documentación
- **ReadMe**: [Plataforma de documentación](https://readme.com/)
- **Stoplight**: [Herramientas de API design](https://stoplight.io/)
- **Redoc**: [Alternativa a Swagger UI](https://github.com/Redocly/redoc)

## 🎯 Rutas de Aprendizaje

### Nivel Principiante (1-2 semanas)
1. **Conceptos básicos** de APIs REST
2. **OpenAPI Specification** fundamentals
3. **Swagger UI** básico
4. **Primeros endpoints** documentados

### Nivel Intermedio (2-4 semanas)
1. **Anotaciones avanzadas** de OpenAPI
2. **Validaciones** y esquemas complejos
3. **Autenticación** en APIs
4. **Versionado** de APIs

### Nivel Avanzado (4-6 semanas)
1. **Generación de código** automática
2. **Testing** automatizado de documentación
3. **API Gateways** con documentación agregada
4. **Microservicios** con documentación distribuida

## 🏆 Mejores Prácticas Implementadas

### Documentación
- ✅ **Esquemas completos** para todos los modelos
- ✅ **Ejemplos realistas** en todas las operaciones
- ✅ **Descripciones detalladas** de parámetros y respuestas
- ✅ **Códigos de error** documentados

### Desarrollo
- ✅ **Anotaciones consistentes** en toda la API
- ✅ **Validaciones automáticas** integradas
- ✅ **Versionado** apropiado de endpoints
- ✅ **Nomenclatura** RESTful consistente

### Operaciones
- ✅ **Documentación viva** que se actualiza automáticamente
- ✅ **Testing integrado** desde Swagger UI
- ✅ **Métricas de uso** de la documentación
- ✅ **Acceso público** a la documentación

## 🔧 Configuración Avanzada

### Personalización de UI
```yaml
springdoc:
  swagger-ui:
    path: /swagger-ui.html
    operationsSorter: method
    tagsSorter: alpha
    disable-swagger-default-url: true
    doc-expansion: none
```

### Grupos de APIs
```java
@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("public")
            .pathsToMatch("/api/public/**")
            .build();
    }
}
```

### Seguridad en Documentación
```java
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
```

## 🚀 Próximos Pasos

### Inmediatos
1. **Implementar autenticación** en la documentación
2. **Agregar ejemplos** más completos
3. **Configurar versionado** de APIs
4. **Generar SDKs** automáticamente

### A Largo Plazo
1. **API Management** avanzado
2. **Testing automatizado** de contratos
3. **Monitoreo de APIs** en producción
4. **GraphQL** como alternativa

## 📊 Métricas de Éxito

### Calidad de Documentación
- **Completitud**: Todos los endpoints documentados
- **Accuracy**: Documentación actualizada con código
- **Usabilidad**: Fácil de entender y usar
- **Consistencia**: Estilo uniforme en toda la API

### Métricas Técnicas
- **Coverage**: Porcentaje de endpoints documentados
- **Validation**: Esquemas válidos y completos
- **Performance**: Tiempo de carga de documentación
- **Usage**: Frecuencia de acceso a la documentación

## 🎓 Certificaciones y Credenciales

- **OpenAPI Professional Certification**
  - [📋 Información](https://www.openapis.org/certification/)
- **API Design Certification** (Postman)
  - [📋 Postman Certification](https://www.postman.com/certification/)

## 📞 Comunidad y Soporte

- **💬 [OpenAPI Slack](https://open-api.slack.com/)** - Comunidad oficial
- **🐙 [GitHub OpenAPI](https://github.com/OAI/OpenAPI-Specification)** - Especificación
- **📧 [SpringDoc Issues](https://github.com/springdoc/springdoc-openapi/issues)** - Soporte técnico
- **🎯 [API Specifications Conference](https://apispecs.org/)** - Conferencias

## 🔄 Comparación con Alternativas

| Característica | OpenAPI/Swagger | Postman | Insomnia | API Blueprint |
|----------------|----------------|---------|----------|----------------|
| **Estándar** | ✅ OpenAPI | ❌ Propio | ❌ Propio | ✅ API Blueprint |
| **Documentación** | ✅ Automática | ✅ Manual | ✅ Manual | ✅ Automática |
| **Testing** | ✅ Integrado | ✅ Avanzado | ✅ Avanzado | ❌ Limitado |
| **Code Generation** | ✅ Múltiples lenguajes | ✅ Limitado | ❌ | ✅ Limitado |
| **Colaboración** | ✅ SwaggerHub | ✅ Workspaces | ✅ Teams | ✅ GitHub |
| **Costo** | ✅ Gratuito | ⚠️ Freemium | ✅ Gratuito | ✅ Gratuito |

## 📋 Checklist de Implementación

### Documentación Básica
- [x] Endpoints documentados
- [x] Modelos de datos definidos
- [x] Ejemplos de requests/responses
- [x] Descripciones de parámetros

### Documentación Avanzada
- [x] Autenticación documentada
- [x] Códigos de error detallados
- [ ] Versionado de APIs
- [ ] Ejemplos de uso complejos
- [ ] Callbacks y webhooks

### Operaciones
- [x] Documentación accesible
- [x] Testing desde UI
- [ ] Métricas de uso
- [ ] Feedback de usuarios
- [ ] Versiones históricas

---

*La documentación de APIs es crucial para el éxito de cualquier proyecto de microservicios. OpenAPI/Swagger proporciona un estándar robusto y herramientas poderosas para lograrlo.*
