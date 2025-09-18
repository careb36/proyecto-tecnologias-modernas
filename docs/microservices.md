# Microservicios con Spring Boot

Este proyecto implementa una arquitectura de microservicios usando Spring Boot, Spring Cloud y Docker.

## Arquitectura de Microservicios

### 🎯 **Diagrama de Arquitectura Completa**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           🌐 CAPA DE PRESENTACIÓN                           │
│                                                                             │
│  ┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐         │
│  │   API Gateway   │────│  Config Server   │────│ Service Registry│         │
│  │   (Port 8080)   │    │   (Port 8888)    │    │   (Port 8761)   │         │
│  │                 │    │                  │    │                 │         │
│  │ • Routing       │    │ • Config Central│    │ • Eureka Server │         │
│  │ • Load Balance  │    │ • Profiles       │    │ • Service Disc. │         │
│  │ • Swagger Agg.  │    │ • Refresh        │    │ • Health Check  │         │
│  └─────────────────┘    └──────────────────┘    └─────────────────┘         │
│               │                                                             │
│          ┌────┴────┐                                                        │
│          │         │                                                        │
│   ┌──────▼────┐ ┌──▼──────┐ ┌─────────┐ ┌────────────┐                      │
│   │ Usuario   │ │ Product │ │  Order  │ │Notification│                      │
│   │ Service   │ │ Service │ │ Service │ │  Service   │                      │
│   │  (8081)   │ │ (8082)  │ │ (8083)  │ │  (8084)    │                      │
│   │           │ │         │ │         │ │            │                      │
│   │ • CRUD    │ │ • Cat.  │ │ • Proc. │ │ • Email    │                      │
│   │ • Auth    │ │ • Inv.  │ │ • Int.  │ │ • SMS      │                      │
│   │ • Valid   │ │ • API   │ │ • Event │ │ • Push     │                      │
│   └───────────┘ └─────────┘ └─────────┘ └────────────┘                      │
└─────────────────────────────────────────────────────────────────────────────┘
               │
          ┌────┴────┐
          │         │
   ┌──────▼─────┐ ┌──▼──────┐ ┌─────────┐ ┌────────────┐
   │   Oracle   │ │Promethe │ │  Loki   │ │ SonarQube  │
   │     DB     │ │   us    │ │         │ │            │
   │   (1521)   │ │ (9090)  │ │ (3100)  │ │  (9000)    │
   │            │ │         │ │         │ │            │
   │ • Data     │ │ • Mtrc  │ │ • Logs  │ │ • Quality  │
   │ • JPA      │ │ • Alert │ │ • Aggr  │ │ • Code     │
   │ • Trans    │ │ • Query │ │ • Filter│ │ • Analysis │
   └────────────┘ └─────────┘ └─────────┘ └────────────┘
          │
     ┌────┴────┐
     │         │
┌────▼────┐ ┌──▼──────┐ ┌─────────┐
│ Grafana │ │Portainer│ │PostgreSQL│
│ (3000)  │ │ (9001)  │ │  (5432)  │
│         │ │         │ │          │
│ • Dash  │ │ • Mgmt  │ │ • Sonar  │
│ • Visual│ │ • Docker│ │ • DB     │
│ • Alert │ │ • Swarm │ │ • Data   │
└─────────┘ └─────────┘ └──────────┘
```

### 🏗️ **Componentes de la Arquitectura**

#### **🏠 Capa de Infraestructura**
- **API Gateway**: Punto de entrada único y enrutamiento inteligente
- **Config Server**: Configuración centralizada y gestión de perfiles
- **Service Registry**: Service Discovery y registro automático

#### **🎯 Capa de Servicios de Negocio**
- **Usuario Service**: Gestión completa de usuarios y autenticación
- **Product Service**: Catálogo de productos e inventario
- **Order Service**: Procesamiento de pedidos y transacciones
- **Notification Service**: Sistema de notificaciones multi-canal

#### **📊 Capa de Monitoreo y Observabilidad**
- **Prometheus**: Recolección de métricas
- **Grafana**: Visualización y dashboards
- **Loki**: Agregación de logs centralizada
- **Node Exporter**: Métricas del sistema operativo

#### **🔍 Capa de Calidad y DevOps**
- **SonarQube**: Análisis de calidad de código
- **Portainer**: Gestión visual de contenedores
- **PostgreSQL**: Base de datos para herramientas

#### **🗄️ Capa de Datos**
- **Oracle Database**: Base de datos principal de negocio
- **H2 Database**: Base de datos de desarrollo/testing

## Servicios Implementados

### 🔧 **Infraestructura de Microservicios**

#### 1. **Config Server** (Puerto 8888)
- **Tecnología**: Spring Cloud Config Server
- **Funciones principales**:
  - Configuración centralizada de todos los microservicios
  - Perfiles por ambiente (dev, prod, docker)
  - Actualización automática sin reinicio de servicios
  - Backend: Git repository para versionado de configuración
- **Endpoints importantes**:
  - `GET /{application}/{profile}` - Obtener configuración
  - `POST /actuator/refresh` - Refresh de configuración
  - `GET /actuator/health` - Health check

#### 2. **Service Registry** (Puerto 8761)
- **Tecnología**: Netflix Eureka Server
- **Funciones principales**:
  - Registro automático de microservicios
  - Service Discovery dinámico
  - Health monitoring continuo
  - Load balancing automático
- **Dashboard**: http://localhost:8761 (Eureka Dashboard)

#### 3. **API Gateway** (Puerto 8080)
- **Tecnología**: Spring Cloud Gateway
- **Funciones principales**:
  - Punto de entrada único para todos los microservicios
  - Enrutamiento inteligente basado en paths
  - Load balancing automático
  - Agregación de documentación Swagger
  - Rate limiting y circuit breakers
  - Autenticación y autorización centralizada
- **Endpoints principales**:
  - `/api/usuarios/**` → Usuario Service
  - `/api/productos/**` → Product Service
  - `/api/pedidos/**` → Order Service
  - `/api/notificaciones/**` → Notification Service

### 🎯 **Microservicios de Negocio**

#### 4. **Usuario Service** (Puerto 8081)
- **Tecnología**: Spring Boot + Spring Data JPA + Oracle
- **Funciones principales**:
  - Gestión completa de usuarios (CRUD)
  - Validaciones automáticas con Bean Validation
  - Autenticación y autorización
  - Documentación Swagger completa
  - Métricas y logs integrados
  - Health checks automáticos
- **Endpoints principales**:
  - `GET /api/usuarios` - Listar usuarios
  - `POST /api/usuarios` - Crear usuario
  - `GET /api/usuarios/{id}` - Obtener usuario específico
  - `PUT /api/usuarios/{id}` - Actualizar usuario
  - `DELETE /api/usuarios/{id}` - Desactivar usuario

#### 5. **Product Service** (Puerto 8082)
- **Tecnología**: Spring Boot + Spring Data JPA + Oracle
- **Funciones principales**:
  - Catálogo completo de productos
  - Gestión de inventario en tiempo real
  - Búsqueda y filtrado avanzado
  - API REST documentada con OpenAPI
  - Validaciones de negocio
  - Integración con métricas
- **Endpoints principales**:
  - `GET /api/productos` - Catálogo de productos
  - `POST /api/productos` - Crear producto
  - `GET /api/productos/{id}` - Obtener producto específico
  - `PUT /api/productos/{id}` - Actualizar producto
  - `PUT /api/productos/{id}/stock` - Actualizar stock
  - `GET /api/productos/categorias` - Listar categorías

#### 6. **Order Service** (Puerto 8083)
- **Tecnología**: Spring Boot + Spring Data JPA + Oracle
- **Funciones principales**:
  - Procesamiento completo de pedidos
  - Integración con Usuario y Product Services
  - Arquitectura event-driven
  - Validaciones de negocio complejas
  - Gestión de estados de pedido
  - Notificaciones automáticas
- **Estados de pedido**: PENDIENTE → PROCESANDO → COMPLETADO/CANCELADO
- **Endpoints principales**:
  - `GET /api/pedidos` - Historial de pedidos
  - `POST /api/pedidos` - Crear nuevo pedido
  - `GET /api/pedidos/{id}` - Detalles de pedido específico
  - `PUT /api/pedidos/{id}/estado` - Actualizar estado
  - `GET /api/pedidos/usuario/{usuarioId}` - Pedidos por usuario
  - `GET /api/pedidos/estadisticas` - Estadísticas de pedidos

#### 7. **Notification Service** (Puerto 8084)
- **Tecnología**: Spring Boot + JavaMail + SMS Gateway
- **Funciones principales**:
  - Envío de notificaciones por email
  - Notificaciones SMS
  - Push notifications (preparado)
  - Templates de mensajes configurables
  - Cola de notificaciones
  - Logging de envíos
- **Tipos de notificación**:
  - EMAIL: Confirmaciones, recordatorios, alertas
  - SMS: Códigos de verificación, urgentes
  - PUSH: Notificaciones en tiempo real
- **Endpoints principales**:
  - `POST /api/notificaciones/email` - Enviar email
  - `POST /api/notificaciones/sms` - Enviar SMS
  - `GET /api/notificaciones/historial` - Historial de notificaciones
  - `GET /api/notificaciones/estadisticas` - Estadísticas de envíos

### 📊 **Stack de Monitoreo Integrado**

#### **Grafana** (Puerto 3000)
- Dashboards preconfigurados para microservicios
- Métricas de aplicación (JVM, requests, errores)
- Métricas de sistema (CPU, memoria, disco)
- Alertas configuradas
- **Credenciales**: `admin` / `admin`

#### **Prometheus** (Puerto 9090)
- Recolección automática de métricas
- Micrometer integration
- Service discovery automático
- Queries avanzadas (PromQL)

#### **Loki** (Puerto 3100)
- Agregación centralizada de logs
- Búsqueda por servicio y nivel de log
- Integración con Grafana
- Retención configurable

#### **Node Exporter** (Puerto 9100)
- Métricas del sistema operativo
- CPU, memoria, disco, red
- Integración con Prometheus

### 🔍 **Calidad de Código**

#### **SonarQube** (Puerto 9000)
- Análisis estático de código
- Cobertura de pruebas (JaCoCo)
- Detección de code smells
- Quality Gates
- **Credenciales**: `admin` / `admin`

#### **PostgreSQL** (Puerto 5432)
- Base de datos para SonarQube
- **Usuario**: `sonar`
- **Contraseña**: `sonar`
- **Base de datos**: `sonar`

## 📚 Fundamentos Teóricos de Microservicios

### ¿Qué son los Microservicios?

Los microservicios son un **estilo arquitectónico** que estructura una aplicación como una colección de servicios pequeños, independientes y desplegables que se comunican entre sí a través de APIs ligeras.

### Principios Fundamentales

#### 1. **Responsabilidad Única** (Single Responsibility)
Cada microservicio debe tener una única razón para cambiar.
- **Principio SOLID**: S - Single Responsibility Principle
- **Beneficio**: Mantenibilidad y escalabilidad

#### 2. **Despliegue Independiente** (Independent Deployment)
Cada servicio puede desplegarse sin afectar a otros.
- **Ventaja**: Liberación más frecuente
- **Desafío**: Coordinación de versiones

#### 3. **Comunicación Ligera** (Lightweight Communication)
Uso de protocolos ligeros como HTTP/REST, gRPC, o mensajería asíncrona.
- **REST**: Sincrónico, simple
- **gRPC**: Alta performance, tipado fuerte
- **Mensajería**: Asíncrona, desacoplada

#### 4. **Base de Datos por Servicio** (Database per Service)
Cada servicio tiene su propia base de datos.
- **Ventaja**: Independencia de esquema
- **Desafío**: Consistencia eventual

### Patrones Arquitectónicos Implementados

#### Patrón API Gateway
```java
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("usuario-service", r -> r.path("/api/usuarios/**")
                .uri("lb://usuario-service"))
            .build();
    }
}
```

#### Patrón Service Discovery
```java
@SpringBootApplication
@EnableEurekaClient
public class UsuarioServiceApplication {
    // Registro automático en Eureka
}
```

#### Patrón Configuración Centralizada
```yaml
# application.yml
spring:
  cloud:
    config:
      uri: http://config-server:8888
```

## 🛠️ Tecnologías Utilizadas

### Framework Principal
- **Spring Boot 2.7.0**: Framework para desarrollo rápido de aplicaciones Java
  - [📖 Documentación](https://spring.io/projects/spring-boot)
  - [🎓 Guías](https://spring.io/guides)

### Spring Cloud Ecosystem
- **Spring Cloud 2021.0.3**: Suite de herramientas para microservicios
  - [📖 Spring Cloud](https://spring.io/projects/spring-cloud)
  - [🎯 Microservices Patterns](https://microservices.io/)

### Componentes de Comunicación
- **Spring Cloud Gateway**: API Gateway moderno y reactivo
  - [📖 Gateway Docs](https://spring.io/projects/spring-cloud-gateway)
- **Netflix Eureka**: Service Discovery y registro
  - [📖 Eureka Docs](https://github.com/Netflix/eureka)
- **Spring Cloud Config**: Configuración centralizada
  - [📖 Config Docs](https://spring.io/projects/spring-cloud-config)

### Documentación y APIs
- **OpenAPI 3 / Swagger**: Especificación estándar para APIs REST
  - [📖 OpenAPI Spec](https://swagger.io/specification/)
  - [🎓 Swagger Editor](https://editor.swagger.io/)
- **SpringDoc OpenAPI**: Implementación de OpenAPI para Spring Boot
  - [📖 SpringDoc](https://springdoc.org/)

### Persistencia de Datos
- **Spring Data JPA**: Abstracción de acceso a datos
  - [📖 Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- **Oracle Database**: Base de datos relacional empresarial
  - [📖 Oracle Docs](https://docs.oracle.com/en/database/)
- **H2 Database**: Base de datos en memoria para desarrollo
  - [📖 H2 Database](https://h2database.com/)

### Containerización
- **Docker**: Plataforma de containerización
  - [📖 Docker Docs](https://docs.docker.com/)
  - [🎓 Docker Labs](https://labs.play-with-docker.com/)
- **Docker Compose**: Orquestación de aplicaciones multi-contenedor
  - [📖 Compose Docs](https://docs.docker.com/compose/)

## 📖 Recursos de Aprendizaje y Referencias

### Libros Fundamentales

#### 1. **"Building Microservices" - Sam Newman**
- **Enfoque**: Diseño y arquitectura de microservicios
- **Contenido**: Patrones, principios, migración de monolitos
- **Nivel**: Intermedio
- **📚 [Comprar en Amazon](https://www.amazon.com/Building-Microservices-Designing-Fine-Grained-Systems/dp/1491950358)**
- **🌐 [Versión Digital](https://www.oreilly.com/library/view/building-microservices/9781491950340/)**

#### 2. **"Microservices Patterns" - Chris Richardson**
- **Enfoque**: Patrones específicos para microservicios
- **Contenido**: 44 patrones con ejemplos prácticos
- **Nivel**: Intermedio/Avanzado
- **📚 [Libro Completo](https://microservices.io/book)**
- **🎯 [Patrones Interactivos](https://microservices.io/patterns/)**

#### 3. **"Spring Microservices in Action" - John Carnell**
- **Enfoque**: Implementación práctica con Spring
- **Contenido**: Spring Cloud, configuración, deployment
- **Nivel**: Intermedio
- **📚 [Comprar en Amazon](https://www.amazon.com/Spring-Microservices-Action-John-Carnell/dp/1617293989)**

### Documentación Oficial

#### Spring Ecosystem
- **📖 [Spring Boot Documentation](https://spring.io/projects/spring-boot)**
- **📖 [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)**
- **🎓 [Spring Guides](https://spring.io/guides)** - Tutoriales prácticos
- **📖 [Spring Cloud Config](https://spring.io/projects/spring-cloud-config)**
- **📖 [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)**

#### Tecnologías de Comunicación
- **📖 [OpenAPI Specification](https://swagger.io/specification/)**
- **🎓 [Swagger Editor](https://editor.swagger.io/)**
- **📖 [SpringDoc OpenAPI](https://springdoc.org/)**
- **📖 [Netflix Eureka](https://github.com/Netflix/eureka/wiki)**

### Cursos y Tutoriales

#### Plataformas de Aprendizaje
- **Udemy**: "Master Microservices with Spring Boot and Spring Cloud"
- **Coursera**: "Microservices Architecture" (diversos cursos)
- **Pluralsight**: "Spring Cloud Fundamentals"
- **LinkedIn Learning**: "Building Microservices with Spring Boot"

#### Canales YouTube
- **SpringDeveloper**: Tutoriales de Spring Boot
- **TechPrimers**: Microservicios con Spring Boot
- **Java Brains**: Arquitectura de microservicios
- **Amigoscode**: Spring Boot para principiantes

### Comunidades y Blogs

#### Blogs Técnicos
- **Martin Fowler**: [Microservices](https://martinfowler.com/articles/microservices.html)
- **Chris Richardson**: [Microservices Blog](https://microservices.io/)
- **Spring Blog**: [Spring Framework](https://spring.io/blog)
- **Baeldung**: [Spring Tutorials](https://www.baeldung.com/)

#### Comunidades
- **Stack Overflow**: Etiquetas `microservices`, `spring-cloud`
- **Reddit**: r/microservices, r/java, r/springboot
- **Discord/Slack**: Comunidades de Spring y Java

### Herramientas de Diseño

#### Modelado Arquitectónico
- **Structurizr**: [Herramienta de diagramas C4](https://structurizr.com/)
- **Draw.io**: Diagramas de arquitectura gratuitos
- **PlantUML**: Diagramas como código

#### Prototipado de APIs
- **Swagger Editor**: Diseño de APIs interactivo
- **Postman**: Testing y documentación de APIs
- **Insomnia**: Cliente REST alternativo

## 🎯 Rutas de Aprendizaje Recomendadas

### Nivel Principiante (4-6 semanas)
1. **Fundamentos de Java y Spring Boot** (1 semana)
2. **Conceptos básicos de microservicios** (1 semana)
3. **Primer microservicio simple** (1 semana)
4. **API Gateway básico** (1 semana)

### Nivel Intermedio (6-8 semanas)
1. **Service Discovery con Eureka** (1 semana)
2. **Configuración centralizada** (1 semana)
3. **Documentación con Swagger** (1 semana)
4. **Testing de microservicios** (1 semana)
5. **Containerización con Docker** (2 semanas)

### Nivel Avanzado (8-12 semanas)
1. **Patrones avanzados** (Saga, CQRS, Event Sourcing)
2. **Observabilidad** (logs, métricas, tracing)
3. **Seguridad** (OAuth2, JWT, API Gateway security)
4. **Despliegue en producción** (Kubernetes, service mesh)
5. **Optimización de performance**

## 🏆 Mejores Prácticas Implementadas

### Diseño Arquitectónico
- ✅ **Principio de responsabilidad única**
- ✅ **Comunicación síncrona con REST**
- ✅ **Base de datos por servicio**
- ✅ **API Gateway para enrutamiento**
- ✅ **Service Discovery automático**

### Desarrollo
- ✅ **Documentación OpenAPI completa**
- ✅ **Validaciones automáticas**
- ✅ **Manejo de errores consistente**
- ✅ **Logging estructurado**
- ✅ **Tests automatizados**

### Operaciones
- ✅ **Containerización completa**
- ✅ **Configuración por ambiente**
- ✅ **Health checks automáticos**
- ✅ **Métricas integradas**
- ✅ **CI/CD automatizado**

## 🔍 Casos de Uso y Ejemplos

### E-commerce Platform
- **Usuario Service**: Gestión de cuentas de usuario
- **Product Service**: Catálogo de productos
- **Order Service**: Procesamiento de pedidos
- **Notification Service**: Confirmaciones y alertas

### Sistema Bancario
- **Account Service**: Cuentas bancarias
- **Transaction Service**: Procesamiento de transacciones
- **Notification Service**: Alertas de seguridad
- **Audit Service**: Registro de auditoría

### Plataforma de Streaming
- **User Service**: Perfiles de usuario
- **Content Service**: Catálogo de contenido
- **Recommendation Service**: Sistema de recomendaciones
- **Analytics Service**: Métricas de uso

## 🚀 Próximos Pasos

### Expansión del Sistema
1. **Agregar más servicios** (Payment, Analytics, Audit)
2. **Implementar mensajería** (RabbitMQ/Kafka)
3. **Agregar cache** (Redis)
4. **Implementar seguridad** (OAuth2, JWT)
5. **Migrar a Kubernetes**

### Mejoras Técnicas
1. **Service Mesh** (Istio/Linkerd)
2. **Distributed Tracing** (Zipkin/Jaeger)
3. **Circuit Breakers** (Resilience4j)
4. **API Gateway avanzado** (Kong, Zuul)
5. **Database sharding**

## 📋 Checklist de Implementación

### Arquitectura
- [x] API Gateway implementado
- [x] Service Discovery configurado
- [x] Configuración centralizada
- [x] Servicios básicos creados
- [ ] Seguridad implementada
- [ ] Mensajería asíncrona
- [ ] Cache distribuido

### Desarrollo
- [x] Documentación Swagger
- [x] Tests unitarios
- [x] Validaciones
- [ ] Tests de integración
- [ ] Tests de contrato
- [ ] Tests de performance

### Operaciones
- [x] Docker configurado
- [x] CI/CD pipeline
- [x] Monitoreo básico
- [ ] Alerting avanzado
- [ ] Auto-scaling
- [ ] Backup y recovery

## 📞 Soporte y Comunidad

Para preguntas sobre microservicios:
- **📧 Email**: Consultas técnicas específicas
- **💬 GitHub Issues**: Reportes de bugs
- **📚 Documentación**: Wiki del proyecto
- **🎯 Roadmap**: Funcionalidades planificadas

---

*Esta documentación se mantiene actualizada con las mejores prácticas de la industria y las últimas versiones de las tecnologías utilizadas.*

## 🔗 Documentación de APIs

Cada microservicio expone su documentación Swagger/OpenAPI completa:

### 📋 **Documentación por Servicio**

#### **API Gateway** (Puerto 8080)
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs JSON**: http://localhost:8080/v3/api-docs
- **Función**: Agregación de toda la documentación de microservicios

#### **Usuario Service** (Puerto 8081)
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **API Docs JSON**: http://localhost:8081/v3/api-docs
- **Health Check**: http://localhost:8081/actuator/health

#### **Product Service** (Puerto 8082)
- **Swagger UI**: http://localhost:8082/swagger-ui.html
- **API Docs JSON**: http://localhost:8082/v3/api-docs
- **Health Check**: http://localhost:8082/actuator/health

#### **Order Service** (Puerto 8083)
- **Swagger UI**: http://localhost:8083/swagger-ui.html
- **API Docs JSON**: http://localhost:8083/v3/api-docs
- **Health Check**: http://localhost:8083/actuator/health

#### **Notification Service** (Puerto 8084)
- **Swagger UI**: http://localhost:8084/swagger-ui.html
- **API Docs JSON**: http://localhost:8084/v3/api-docs
- **Health Check**: http://localhost:8084/actuator/health

### 🎯 **Endpoints Principales**

#### **Usuario Service** (`/api/usuarios`)
```bash
GET    /api/usuarios           # Listar todos los usuarios
POST   /api/usuarios           # Crear nuevo usuario
GET    /api/usuarios/{id}      # Obtener usuario por ID
PUT    /api/usuarios/{id}      # Actualizar usuario
DELETE /api/usuarios/{id}      # Desactivar usuario
GET    /api/usuarios/buscar    # Búsqueda avanzada
```

#### **Product Service** (`/api/productos`)
```bash
GET    /api/productos              # Catálogo completo
POST   /api/productos              # Crear producto
GET    /api/productos/{id}         # Obtener producto específico
PUT    /api/productos/{id}         # Actualizar producto
PUT    /api/productos/{id}/stock   # Actualizar stock
DELETE /api/productos/{id}         # Desactivar producto
GET    /api/productos/categorias   # Listar categorías
GET    /api/productos/buscar       # Búsqueda avanzada
```

#### **Order Service** (`/api/pedidos`)
```bash
GET    /api/pedidos                    # Historial de pedidos
POST   /api/pedidos                    # Crear nuevo pedido
GET    /api/pedidos/{id}               # Detalles de pedido
PUT    /api/pedidos/{id}/estado        # Actualizar estado
GET    /api/pedidos/usuario/{usuarioId} # Pedidos por usuario
GET    /api/pedidos/estadisticas       # Estadísticas
```

#### **Notification Service** (`/api/notificaciones`)
```bash
POST   /api/notificaciones/email       # Enviar email
POST   /api/notificaciones/sms         # Enviar SMS
GET    /api/notificaciones/historial   # Historial de envíos
GET    /api/notificaciones/estadisticas # Estadísticas
```

### 🔍 **Health Checks y Métricas**

Cada microservicio expone endpoints de monitoreo:

#### **Actuator Endpoints** (por servicio)
```bash
GET  /actuator/health      # Estado del servicio
GET  /actuator/info        # Información del servicio
GET  /actuator/metrics     # Métricas de aplicación
GET  /actuator/prometheus  # Métricas en formato Prometheus
GET  /actuator/loggers     # Configuración de logs
```

#### **Eureka Dashboard**
- **URL**: http://localhost:8761
- **Función**: Visualizar servicios registrados y su estado
- **Información**: Instancias activas, health checks, metadata

### 📊 **Monitoreo Integrado**

#### **Grafana Dashboards**
- **URL**: http://localhost:3000 (admin/admin)
- **Dashboards disponibles**:
  - JVM Metrics (memoria, CPU, GC)
  - HTTP Requests (latencia, errores, throughput)
  - Database Connections
  - Custom Business Metrics

#### **Prometheus Metrics**
- **URL**: http://localhost:9090
- **Métricas principales**:
  - `jvm_memory_used_bytes`
  - `http_server_requests_seconds`
  - `hikaricp_connections_active`
  - `system_cpu_usage`

#### **Loki Logs**
- **URL**: http://localhost:3100
- **Labels disponibles**:
  - `service`: Nombre del microservicio
  - `level`: Nivel de log (INFO, WARN, ERROR)
  - `trace_id`: ID de traza distribuida
