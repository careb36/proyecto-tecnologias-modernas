# 🎓 Guía de Aprendizaje Paso a Paso

Esta guía está diseñada para llevarte desde **principiante absoluto** hasta **desarrollador moderno** con microservicios, Docker y DevOps.

## 📚 Metodología de Aprendizaje

### 🎯 **Enfoque Práctico**
- **Aprende haciendo** - Cada concepto incluye ejemplos prácticos
- **Progreso gradual** - De lo simple a lo complejo
- **Proyecto real** - Todo se aplica a este mismo proyecto
- **Mejores prácticas** - Implementadas desde el inicio

### 📊 **Niveles de Aprendizaje**
- **🚀 Principiante** (4-6 semanas) - Fundamentos básicos
- **⚡ Intermedio** (6-8 semanas) - Arquitecturas distribuidas
- **🎯 Avanzado** (8-12 semanas) - DevOps y producción

---

## 📚 Fase 0: Preparación (Si eres completamente nuevo)

**⏱️ Tiempo estimado**: 1 semana
**🎯 Objetivo**: Tener tu entorno listo para el desarrollo moderno

### Día 1-2: Java y Spring Boot
**Meta**: Ejecutar una aplicación Spring Boot en tu máquina

#### 📖 **Teoría**
- ¿Qué es Java y JVM?
- Introducción a Spring Framework
- Conceptos básicos de Spring Boot

#### 🛠️ **Práctica**
```bash
# 1. Instalar Java 11+
java -version

# 2. Instalar Maven
mvn -version

# 3. Crear primera app Spring Boot
# Sigue: https://spring.io/guides/gs/spring-boot/
```

#### 📚 **Recursos**
- [Java para Principiantes](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Spring Boot Getting Started](https://spring.io/guides/gs/spring-boot/)
- [Maven Documentation](https://maven.apache.org/guides/)

### Día 3-4: Docker Básico
**Meta**: `docker run hello-world` funcionando

#### 📖 **Teoría**
- ¿Qué es la containerización?
- Diferencia entre VM y contenedores
- Comandos básicos de Docker

#### 🛠️ **Práctica**
```bash
# 1. Instalar Docker Desktop
# Descarga: https://www.docker.com/products/docker-desktop

# 2. Ejecutar tu primer contenedor
docker run hello-world

# 3. Explorar comandos básicos
docker ps
docker images
docker --version
```

#### 📚 **Recursos**
- [Docker para Principiantes](https://docker-curriculum.com/)
- [Play with Docker](https://labs.play-with-docker.com/)
- [Docker Documentation](https://docs.docker.com/get-started/)

### Día 5-7: Proyecto Completo
**Meta**: Todo el stack funcionando en http://localhost:8080

#### 📖 **Teoría**
- ¿Qué es un stack de aplicaciones?
- Arquitectura de microservicios básica
- Orquestación con Docker Compose

#### 🛠️ **Práctica**
```bash
# 1. Clonar el repositorio
git clone <tu-repositorio>
cd Proyecto_Tecnologias

# 2. Levantar todo el stack
docker-compose up -d

# 3. Verificar que funciona
docker-compose ps

# 4. Acceder a la aplicación
# http://localhost:8080
```

#### 📚 **Recursos**
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [README del Proyecto](./README.md)
- [Guía de Inicio Rápido](./README.md#inicio-rápido)

---

## 🏗️ Fase 1: Arquitectura y Servicios

**⏱️ Tiempo estimado**: 2-3 semanas
**🎯 Objetivo**: Entender cómo funciona una arquitectura de microservicios

### Semana 1: Microservicios
**Meta**: Crear un nuevo endpoint en un servicio existente

#### 📖 **Conceptos Teóricos**
- **Principio de Responsabilidad Única** (Single Responsibility)
- **Comunicación entre servicios** (REST, gRPC)
- **Base de datos por servicio** (Database per Service)
- **Service Discovery** y registro automático

#### 🛠️ **Práctica con el Proyecto**

##### **1. Explorar Servicios Existentes**
```bash
# Ver todos los servicios ejecutándose
docker-compose ps

# Acceder a Swagger UI
# http://localhost:8080/swagger-ui.html

# Ver Eureka Dashboard
# http://localhost:8761
```

##### **2. Entender la Comunicación**
```bash
# Probar API Gateway
curl http://localhost:8080/api/usuarios

# Probar servicio directo
curl http://localhost:8081/api/usuarios

# Ver logs de comunicación
docker-compose logs api-gateway
```

##### **3. Agregar Nuevo Endpoint**
```java
// En UsuarioController.java
@PostMapping("/buscar")
public ResponseEntity<List<UsuarioDTO>> buscarUsuarios(@RequestParam String nombre) {
    // Implementar búsqueda
}
```

#### 📚 **Recursos**
- [Microservicios con Spring Boot](docs/microservices.md)
- [API Gateway Documentation](docs/swagger.md)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)

### Semana 2: APIs y Documentación
**Meta**: Documentar completamente una nueva funcionalidad

#### 📖 **Conceptos Teóricos**
- **OpenAPI 3** y especificaciones REST
- **Swagger UI** para documentación interactiva
- **Versionado de APIs**
- **Contratos de API** y documentación viva

#### 🛠️ **Práctica con el Proyecto**

##### **1. Explorar Documentación Existente**
```bash
# Swagger UI principal
# http://localhost:8080/swagger-ui.html

# Documentación por servicio
# http://localhost:8081/swagger-ui.html (Usuario)
# http://localhost:8082/swagger-ui.html (Product)
# http://localhost:8083/swagger-ui.html (Order)
# http://localhost:8084/swagger-ui.html (Notification)
```

##### **2. Agregar Documentación a Nuevo Endpoint**
```java
@PostMapping("/buscar")
@Operation(
    summary = "Buscar usuarios por nombre",
    description = "Retorna lista de usuarios que coinciden con el criterio de búsqueda"
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa"),
    @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
})
public ResponseEntity<List<UsuarioDTO>> buscarUsuarios(
    @Parameter(description = "Nombre a buscar")
    @RequestParam String nombre
) {
    // Implementación
}
```

#### 📚 **Recursos**
- [Documentación de APIs](docs/swagger.md)
- [OpenAPI Specification](https://swagger.io/specification/)
- [SpringDoc OpenAPI](https://springdoc.org/)

### Semana 3: Bases de Datos
**Meta**: Agregar un nuevo campo a una entidad

#### 📖 **Conceptos Teóricos**
- **JPA/Hibernate** para mapeo objeto-relacional
- **Migraciones de base de datos**
- **Relaciones entre entidades**
- **Consultas JPQL** y Criteria API

#### 🛠️ **Práctica con el Proyecto**

##### **1. Explorar Base de Datos**
```bash
# Conectar a Oracle
# Host: localhost
# Port: 1521
# SID: xe
# User: system
# Password: oracle

# Ver tablas existentes
SELECT table_name FROM user_tables;
```

##### **2. Agregar Nuevo Campo**
```java
// En Usuario.java
@Column(name = "telefono")
private String telefono;

// En UsuarioDTO.java
@Schema(description = "Teléfono del usuario", example = "+598 99 123 456")
private String telefono;

// En UsuarioCreateDTO.java
@Schema(description = "Teléfono del usuario", example = "+598 99 123 456")
private String telefono;
```

##### **3. Actualizar Controller**
```java
@PostMapping
public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
    UsuarioDTO usuario = new UsuarioDTO(
        999L,
        dto.getNombre(),
        dto.getEmail(),
        dto.getTelefono(), // Nuevo campo
        LocalDateTime.now(),
        true
    );
    return ResponseEntity.ok(usuario);
}
```

#### 📚 **Recursos**
- [Oracle Database Guide](docs/oracle.md)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [JPA Documentation](https://jakarta.ee/specifications/persistence/3.0/)

---

## 🐳 Fase 2: Containerización y Orquestación

**⏱️ Tiempo estimado**: 2 semanas
**🎯 Objetivo**: Dominar Docker y la gestión de contenedores

### Semana 1: Docker Avanzado
**Meta**: Crear imagen Docker para tu propia aplicación

#### 📖 **Conceptos Teóricos**
- **Dockerfile** y mejores prácticas
- **Capas de imagen** y optimización
- **Multi-stage builds**
- **Docker networking** y volúmenes

#### 🛠️ **Práctica con el Proyecto**

##### **1. Analizar Dockerfile Existente**
```dockerfile
# Ejemplo del proyecto
FROM openjdk:11-jre-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

##### **2. Crear Dockerfile Optimizado**
```dockerfile
# Multi-stage build
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

##### **3. Construir y Ejecutar**
```bash
# Construir imagen
docker build -t mi-servicio .

# Ejecutar contenedor
docker run -p 8080:8080 mi-servicio

# Ver logs
docker logs <container-id>
```

#### 📚 **Recursos**
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)
- [Multi-stage Builds](https://docs.docker.com/develop/buildx/multi-stage/)
- [Dockerfile Reference](https://docs.docker.com/engine/reference/builder/)

### Semana 2: Gestión y Orquestación
**Meta**: Desplegar una aplicación completa en Swarm

#### 📖 **Conceptos Teóricos**
- **Docker Compose** para desarrollo
- **Docker Swarm** para producción
- **Load balancing** y escalado
- **Service discovery** en cluster

#### 🛠️ **Práctica con el Proyecto**

##### **1. Usar Docker Compose (Desarrollo)**
```bash
# Ver configuración actual
cat docker-compose.yml

# Escalar un servicio
docker-compose up -d --scale usuario-service=3

# Ver servicios escalados
docker-compose ps
```

##### **2. Introducción a Docker Swarm**
```bash
# Inicializar Swarm
docker swarm init

# Desplegar stack
docker stack deploy -c docker-compose.swarm.yml tech-stack

# Ver servicios en Swarm
docker stack services tech-stack

# Escalar servicio
docker service scale tech-stack_usuario-service=3
```

##### **3. Monitoreo con Portainer**
```bash
# Portainer está incluido en el stack
# URL: http://localhost:9001

# Funcionalidades:
# - Gestión visual de contenedores
# - Monitoreo de recursos
# - Logs en tiempo real
# - Redes y volúmenes
```

#### 📚 **Recursos**
- [Docker Swarm Documentation](docs/docker-swarm.md)
- [Portainer Guide](docs/portainer.md)
- [Docker Compose Scaling](https://docs.docker.com/compose/scaling/)

---

## 📊 Fase 3: Observabilidad y Monitoreo

**⏱️ Tiempo estimado**: 2 semanas
**🎯 Objetivo**: Implementar monitoreo completo de aplicaciones

### Semana 1: Métricas y Dashboards
**Meta**: Dashboard mostrando métricas de tu aplicación

#### 📖 **Conceptos Teóricos**
- **Métricas de aplicación** (JVM, HTTP, DB)
- **Micrometer** para instrumentación
- **Prometheus** para recolección
- **Grafana** para visualización

#### 🛠️ **Práctica con el Proyecto**

##### **1. Explorar Métricas Existentes**
```bash
# Prometheus
# http://localhost:9090

# Grafana (admin/admin)
# http://localhost:3000

# Endpoints de métricas
curl http://localhost:8081/actuator/metrics
curl http://localhost:8081/actuator/prometheus
```

##### **2. Crear Dashboard Personalizado**
```bash
# En Grafana:
# 1. Crear nuevo dashboard
# 2. Agregar panel de métricas JVM
# 3. Configurar consultas PromQL
# 4. Agregar alertas
```

##### **3. Métricas Personalizadas**
```java
// En tu servicio
@Autowired
private MeterRegistry meterRegistry;

public void procesarPedido() {
    Timer.Sample sample = Timer.start(meterRegistry);
    try {
        // Lógica de negocio
        meterRegistry.counter("pedidos.procesados").increment();
    } finally {
        sample.stop(Timer.builder("pedido.procesamiento.tiempo")
            .register(meterRegistry));
    }
}
```

#### 📚 **Recursos**
- [Grafana Guide](docs/grafana.md)
- [Prometheus Documentation](https://prometheus.io/docs/)
- [Micrometer Documentation](https://micrometer.io/)

### Semana 2: Logs y Troubleshooting
**Meta**: Encontrar y solucionar un problema usando logs

#### 📖 **Conceptos Teóricos**
- **Niveles de logging** (DEBUG, INFO, WARN, ERROR)
- **Logging estructurado** con SLF4J
- **Agregación centralizada** con Loki
- **Correlación de logs** con trace IDs

#### 🛠️ **Práctica con el Proyecto**

##### **1. Explorar Sistema de Logs**
```bash
# Loki (agregador de logs)
# http://localhost:3100

# Ver logs de un servicio
docker-compose logs usuario-service

# Buscar en logs con Loki
# http://localhost:3100
```

##### **2. Implementar Logging Estructurado**
```java
// En tu código
private static final Logger logger = LoggerFactory.getLogger(MiClase.class);

public void procesarUsuario(Long usuarioId) {
    logger.info("Procesando usuario", KeyValue.of("usuarioId", usuarioId),
        KeyValue.of("operacion", "procesamiento"));

    try {
        // Lógica de negocio
        logger.debug("Usuario procesado exitosamente", KeyValue.of("usuarioId", usuarioId));
    } catch (Exception e) {
        logger.error("Error procesando usuario", e,
            KeyValue.of("usuarioId", usuarioId),
            KeyValue.of("error", e.getMessage()));
    }
}
```

##### **3. Troubleshooting con Logs**
```bash
# Buscar errores en logs
docker-compose logs | grep ERROR

# Ver logs en tiempo real
docker-compose logs -f usuario-service

# Buscar por patrón en Loki
# Usar filtros: {service="usuario-service", level="ERROR"}
```

#### 📚 **Recursos**
- [Loki Guide](docs/loki.md)
- [SLF4J Documentation](https://www.slf4j.org/)
- [Logback Configuration](https://logback.qos.ch/manual/)

---

## 🔄 Fase 4: CI/CD y Calidad

**⏱️ Tiempo estimado**: 2 semanas
**🎯 Objetivo**: Automatizar el desarrollo y asegurar calidad

### Semana 1: Pipelines CI/CD
**Meta**: Pipeline que se ejecuta en cada commit

#### 📖 **Conceptos Teóricos**
- **Integración Continua** (CI)
- **Despliegue Continuo** (CD)
- **GitLab CI/CD** con pipelines
- **Quality Gates** y gates de calidad

#### 🛠️ **Práctica con el Proyecto**

##### **1. Explorar Pipeline Existente**
```yaml
# .gitlab-ci.yml
stages:
  - build
  - test
  - quality
  - deploy

build:
  script:
    - mvn clean compile

test:
  script:
    - mvn test

quality:
  script:
    - mvn sonar:sonar

deploy:
  script:
    - docker-compose up -d
```

##### **2. Ejecutar Pipeline Local**
```bash
# Instalar GitLab Runner local
# O usar docker para testing

# Ejecutar stages manualmente
mvn clean compile
mvn test
mvn sonar:sonar
```

##### **3. Agregar Nuevos Stages**
```yaml
# Agregar stage de performance
performance:
  stage: test
  script:
    - mvn jmeter:jmeter

# Agregar stage de security
security:
  stage: quality
  script:
    - mvn dependency-check:check
```

#### 📚 **Recursos**
- [GitLab CI/CD Guide](docs/gitlab-ci.md)
- [GitLab CI/CD Documentation](https://docs.gitlab.com/ee/ci/)
- [Maven Plugins](https://maven.apache.org/plugins/)

### Semana 2: Calidad y Performance
**Meta**: Quality gate funcionando y pruebas de carga documentadas

#### 📖 **Conceptos Teóricos**
- **Análisis estático** con SonarQube
- **Cobertura de código** con JaCoCo
- **Pruebas de carga** con JMeter
- **Security scanning** y análisis de vulnerabilidades

#### 🛠️ **Práctica con el Proyecto**

##### **1. Explorar SonarQube**
```bash
# SonarQube (admin/admin)
# http://localhost:9000

# Ver métricas de calidad
# - Cobertura de código
# - Code smells
# - Duplicaciones
# - Vulnerabilidades
```

##### **2. Ejecutar Análisis Local**
```bash
# Análisis con SonarQube
mvn clean verify sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=admin \
  -Dsonar.password=admin
```

##### **3. Pruebas de Carga con JMeter**
```bash
# JMeter incluido en el proyecto
# Crear plan de pruebas:
# 1. Thread Group (usuarios virtuales)
# 2. HTTP Request (endpoint a probar)
# 3. Results Tree (visualizar resultados)
# 4. Summary Report (estadísticas)

# Ejecutar pruebas
jmeter -n -t test-plan.jmx -l results.jtl
```

#### 📚 **Recursos**
- [SonarQube Guide](docs/sonarqube.md)
- [JMeter Guide](docs/jmeter.md)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/)

---

## 🚀 Fase 5: Proyecto Final

**⏱️ Tiempo estimado**: 2-4 semanas
**🎯 Objetivo**: Aplicar todo lo aprendido en un proyecto real

### Planificación
**Meta**: Diseña tu propio microservicio

#### 📋 **Checklist de Planificación**
- [ ] **Definir dominio** - ¿Qué problema resuelve?
- [ ] **Identificar bounded contexts** - Límites del servicio
- [ ] **Diseñar API** - Endpoints REST
- [ ] **Modelar datos** - Entidades y relaciones
- [ ] **Definir contratos** - DTOs y validaciones

#### 🛠️ **Herramientas para Diseño**
```bash
# Crear diagrama de arquitectura
# Usar: draw.io, plantuml, structurizr

# Diseñar API
# Usar: Swagger Editor, Postman

# Modelar base de datos
# Usar: MySQL Workbench, DBeaver
```

### Desarrollo
**Meta**: Implementa usando las mejores prácticas

#### 📋 **Checklist de Desarrollo**
- [ ] **Configurar proyecto** - Maven, Spring Boot
- [ ] **Implementar entidades** - JPA con validaciones
- [ ] **Crear repositorios** - Spring Data JPA
- [ ] **Desarrollar servicios** - Lógica de negocio
- [ ] **Construir controladores** - REST API con documentación
- [ ] **Agregar tests** - Unitarios e integración
- [ ] **Configurar métricas** - Micrometer
- [ ] **Implementar logging** - SLF4J estructurado

#### 🛠️ **Estructura Recomendada**
```
mi-servicio/
├── Dockerfile
├── pom.xml
├── src/main/java/.../mi/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── dto/
│   └── config/
├── src/main/resources/
│   ├── application.yml
│   └── logback-spring.xml
└── src/test/
    ├── java/
    └── resources/
```

### Testing
**Meta**: Pruebas unitarias, integración y carga

#### 📋 **Estrategia de Testing**
- **Unit Tests**: 70-80% cobertura
- **Integration Tests**: Con Testcontainers
- **API Tests**: Con REST Assured
- **Performance Tests**: Con JMeter
- **E2E Tests**: Con Selenium/Cypress

#### 🛠️ **Ejemplo de Test**
```java
@SpringBootTest
@Testcontainers
class MiServicioIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");

    @Test
    void deberiaCrearUsuario() {
        // Given
        UsuarioCreateDTO dto = new UsuarioCreateDTO("Juan", "juan@email.com");

        // When
        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity("/api/usuarios", dto, UsuarioDTO.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getNombre()).isEqualTo("Juan");
    }
}
```

### Despliegue
**Meta**: CI/CD completo con monitoreo

#### 📋 **Pipeline Completo**
```yaml
stages:
  - build
  - test
  - quality
  - security
  - performance
  - deploy
  - monitor

# Incluir todos los stages aprendidos
```

#### 🛠️ **Despliegue en Producción**
```bash
# Usar Docker Swarm
docker stack deploy -c docker-compose.prod.yml mi-stack

# Configurar monitoreo
# - Grafana dashboards
# - Prometheus alertas
# - Loki para logs

# Configurar backup
# - Base de datos
# - Configuraciones
# - Volúmenes
```

### Documentación
**Meta**: APIs, arquitectura y guías de uso

#### 📋 **Documentación Requerida**
- [ ] **README.md** - Guía de instalación y uso
- [ ] **API Documentation** - Swagger/OpenAPI completa
- [ ] **Architecture Diagrams** - Diagramas de componentes
- [ ] **Deployment Guide** - Guía de despliegue
- [ ] **Troubleshooting Guide** - Solución de problemas comunes
- [ ] **Contributing Guide** - Cómo contribuir al proyecto

---

## 🏆 Certificaciones Recomendadas

### 🚀 Nivel Básico
- **Oracle Certified Java Programmer (OCJP)**
  - [📚 Preparación](https://education.oracle.com/oracle-certified-professional-java-se-11-programmer/trackp_OCPJSE11)

### ⚡ Nivel Intermedio
- **Docker Certified Associate (DCA)**
  - [🎓 Certificación](https://docker.com/certification)
- **Spring Professional Certification**
  - [🎓 Pivotal Certification](https://pivotal.io/training)

### 🎯 Nivel Avanzado
- **Certified Kubernetes Administrator (CKA)**
  - [🎓 CNCF Certification](https://www.cncf.io/certification/cka/)
- **AWS Certified DevOps Engineer**
  - [🎓 AWS Certification](https://aws.amazon.com/certification/)

---

## 📚 Recursos Adicionales

### Comunidades y Blogs
- **Spring Blog**: https://spring.io/blog
- **Baeldung**: https://www.baeldung.com/
- **Reddit**: r/java, r/microservices, r/docker
- **Stack Overflow**: Etiquetas específicas

### Cursos Especializados
- **Udemy**: "Master Microservices with Spring Boot and Spring Cloud"
- **Coursera**: "Microservices Architecture" (University of Alberta)
- **LinkedIn Learning**: "Building Microservices with Spring Boot"

### Libros Avanzados
- **"Domain-Driven Design"** - Eric Evans
- **"Clean Architecture"** - Robert C. Martin
- **"Building Evolutionary Architectures"** - Neal Ford

---

**¡Felicidades! Has completado el camino hacia el desarrollo moderno. 🎓✨**

Esta guía te ha llevado desde los fundamentos hasta la implementación de sistemas complejos. El conocimiento adquirido aquí es aplicable a cualquier proyecto moderno de software.

**¿Qué sigue?**
- Contribuye a proyectos open source
- Únete a comunidades técnicas
- Considera certificaciones avanzadas
- Mantén tu aprendizaje continuo

**¡El viaje nunca termina! 🚀**
