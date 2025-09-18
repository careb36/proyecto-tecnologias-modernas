# Proyecto de Aprendizaje de Tecnologías Modernas

Este proyecto es una implementación completa de arquitectura de microservicios usando Spring Boot, diseñada específicamente para el aprendizaje práctico de tecnologías modernas de desarrollo, DevOps y observabilidad.

## 🎯 ¿Para quién es este proyecto?

Este proyecto está diseñado especialmente para **desarrolladores que nunca se han modernizado** y quieren aprender las tecnologías actuales del desarrollo de software. Si vienes de:

- **Programación tradicional** (Java puro, .NET Framework, PHP procedural)
- **Desarrollo monolítico** (una sola aplicación grande)
- **Despliegue manual** (subir archivos por FTP)
- **Sin experiencia en cloud** o contenedores

¡Este proyecto es perfecto para ti! Te guiaremos paso a paso desde lo básico hasta tecnologías avanzadas.

## 🎯 Objetivos de Aprendizaje

### Nivel Básico (Semanas 1-4)
- ✅ **Java moderno** con Spring Boot
- ✅ **APIs REST** y servicios web
- ✅ **Bases de datos** con JPA/Hibernate
- ✅ **Testing** automatizado
- ✅ **Primeros pasos** con Docker

### Nivel Intermedio (Semanas 5-8)
- ✅ **Microservicios** y arquitectura distribuida
- ✅ **Containerización** completa con Docker
- ✅ **Orquestación** con Docker Swarm
- ✅ **Documentación** de APIs con Swagger
- ✅ **Monitoreo básico** con Grafana

### Nivel Avanzado (Semanas 9-12)
- ✅ **CI/CD** con GitLab pipelines
- ✅ **Observabilidad** completa (logs, métricas, tracing)
- ✅ **Calidad de código** con SonarQube
- ✅ **Pruebas de carga** con JMeter
- ✅ **Despliegue** en producción

## 🛠️ Tecnologías Incluidas

### Backend & Desarrollo
- **☕ Java 11+** con Spring Boot 2.7
- **🏗️ Arquitectura de Microservicios** con Spring Cloud
- **📋 APIs REST** documentadas con OpenAPI/Swagger
- **🗄️ Bases de datos** Oracle y H2
- **🧪 Testing** con JUnit 5 y Testcontainers

### DevOps & Infraestructura
- **🐳 Docker** para containerización
- **🎛️ Portainer** para gestión visual
- **🐙 Docker Swarm** para orquestación
- **🔄 GitLab CI/CD** para pipelines automatizados
- **📊 Grafana** para dashboards y métricas

### Observabilidad & Calidad
- **📝 Loki** para agregación de logs
- **🔍 SonarQube** para análisis de calidad
- **⚡ JMeter** para pruebas de carga
- **📈 Prometheus** para métricas
- **🔒 Spring Security** para autenticación

## 🚀 Tecnologías Incluidas

### Backend & APIs
- **Spring Boot**: Framework principal para microservicios
- **Spring Cloud Gateway**: API Gateway con routing inteligente
- **Netflix Eureka**: Service Discovery y registro
- **Spring Cloud Config**: Configuración centralizada
- **OpenAPI 3**: Documentación de APIs

### Bases de Datos
- **Oracle Database**: Base de datos relacional empresarial
- **H2 Database**: Base de datos en memoria para testing
- **Testcontainers**: Contenedores de base de datos para pruebas

### DevOps & Infraestructura
- **Docker**: Containerización de aplicaciones
- **Docker Compose**: Orquestación local
- **Docker Swarm**: Orquestación en producción
- **GitLab CI/CD**: Pipelines automatizados
- **Portainer**: Gestión visual de Docker

### Observabilidad
- **Grafana**: Dashboards y visualización
- **Prometheus**: Métricas y alertas
- **Loki**: Agregación de logs
- **Micrometer**: Métricas de aplicación

### Calidad de Código
- **SonarQube**: Análisis de calidad
- **JaCoCo**: Cobertura de pruebas
- **JUnit 5**: Framework de testing
- **Maven**: Gestión de dependencias y build

## 🏗️ Arquitectura de Microservicios

### Diagrama de Arquitectura

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   API Gateway   │────│  Config Server   │────│ Service Registry│
│   (Port 8080)   │    │   (Port 8888)    │    │   (Port 8761)   │
│                 │    │                  │    │                 │
│ • Routing       │    │ • Config Central│    │ • Eureka Server │
│ • Load Balance  │    │ • Profiles       │    │ • Service Disc. │
│ • Swagger Agg.  │    │ • Refresh        │    │ • Health Check  │
└─────────────────┘    └──────────────────┘    └─────────────────┘
          │
     ┌────┴────┐
     │         │
┌───▼───┐ ┌──▼────┐ ┌────────┐ ┌──────────┐
│Usuario│ │Product│ │ Order  │ │Notification│
│Service│ │Service│ │Service │ │  Service   │
│ 8081  │ │ 8082  │ │  8083  │ │    8084    │
│        │ │        │ │        │ │           │
│ • CRUD │ │ • Cat. │ │ • Proc.│ │ • Email   │
│ • Auth │ │ • Inv. │ │ • Int. │ │ • SMS     │
│ • Valid│ │ • API  │ │ • Event│ │ • Push    │
└───────┘ └───────┘ └────────┘ └──────────┘
          │
    ┌─────┴─────┐
    │           │
┌───▼───┐ ┌───▼───┐ ┌───▼───┐
│ Oracle│ │Promethe│ │  Loki │
│   DB   │ │   us   │ │       │
│ 1521  │ │ 9090  │ │ 3100  │
│        │ │        │ │       │
│ • Data │ │ • Mtrc │ │ • Logs│
│ • JPA  │ │ • Alert│ │ • Aggr│
│ • Trans│ │ • Query│ │ • Filter│
└───────┘ └───────┘ └───────┘
```

### Servicios Implementados

#### 🔐 **API Gateway** (Puerto 8080)
- **Tecnología**: Spring Cloud Gateway
- **Funciones**:
  - Enrutamiento inteligente de requests
  - Load balancing automático
  - Agregación de documentación Swagger
  - Rate limiting y seguridad
- **Endpoints**: `/api/usuarios/**`, `/api/productos/**`, etc.

#### ⚙️ **Config Server** (Puerto 8888)
- **Tecnología**: Spring Cloud Config
- **Funciones**:
  - Configuración centralizada
  - Perfiles por ambiente (dev, prod, docker)
  - Actualización automática sin reinicio
- **Backend**: Git repository

#### 🏷️ **Service Registry** (Puerto 8761)
- **Tecnología**: Netflix Eureka
- **Funciones**:
  - Registro automático de servicios
  - Service discovery
  - Health monitoring
  - Load balancing

#### 👥 **Usuario Service** (Puerto 8081)
- **Tecnología**: Spring Boot + JPA
- **Funciones**:
  - Gestión completa de usuarios (CRUD)
  - Validaciones y autenticación
  - Documentación Swagger completa
  - Métricas y logs integrados

#### 📦 **Product Service** (Puerto 8082)
- **Tecnología**: Spring Boot + In-memory
- **Funciones**:
  - Catálogo de productos
  - Gestión de inventario
  - Búsqueda y filtrado
  - API REST documentada

#### 📋 **Order Service** (Puerto 8083)
- **Tecnología**: Spring Boot
- **Funciones**:
  - Procesamiento de pedidos
  - Integración con otros servicios
  - Arquitectura event-driven

#### 📢 **Notification Service** (Puerto 8084)
- **Tecnología**: Spring Boot
- **Funciones**:
  - Envío de emails
  - Notificaciones SMS
  - Push notifications
  - Templates de mensajes

## 📁 Estructura del Proyecto

```
├── 📚 docs/                          # Documentación completa
│   ├── microservices.md             # Arquitectura de microservicios
│   ├── docker.md                    # Guía de Docker
│   ├── swagger.md                   # Documentación de APIs
│   ├── gitlab-ci.md                 # CI/CD con GitLab
│   ├── grafana.md                   # Dashboards y métricas
│   └── ...
├── 🐳 docker/                        # Configuración de Docker
│   ├── docker-compose.yml           # Stack completo local
│   ├── docker-compose.swarm.yml     # Despliegue en Swarm
│   └── Dockerfile                   # Imagen de la aplicación
├── 📊 monitoring/                    # Stack de observabilidad
│   ├── grafana/                     # Dashboards preconfigurados
│   ├── prometheus/                  # Métricas y alertas
│   └── loki/                        # Agregación de logs
├── 🔍 quality/                       # Calidad de código
│   └── sonar-project.properties     # Configuración de SonarQube
├── 🔄 .gitlab-ci.yml                 # Pipeline de CI/CD
├── 🗄️ oracle/                        # Base de datos
│   └── init/                        # Scripts de inicialización
├── 📝 src/main/java/                 # Código fuente
│   └── uy/bcu/                      # Paquete base
│       ├── config/                  # Config Server
│       ├── gateway/                 # API Gateway
│       ├── registry/                # Service Registry
│       ├── usuario/                 # Usuario Service
│       ├── product/                 # Product Service
│       ├── notification/            # Notification Service
│       └── order/                   # Order Service
├── 🧪 src/test/                      # Tests automatizados
├── 📋 pom.xml                        # Configuración Maven
├── 🐧 start.sh                       # Script de inicio Linux
├── 🪟 start.bat                      # Script de inicio Windows
└── ⚙️ .env.example                   # Variables de entorno
```

## 🚀 Inicio Rápido

### 🎯 Si eres COMPLETAMENTE nuevo en estas tecnologías

**¡No te preocupes!** Hemos creado guías específicas para principiantes. Sigue este orden:

1. **📖 Lee primero**: [Java con Spring Boot](docs/java-spring.md)
2. **🐳 Instala**: Docker Desktop (es gratuito)
3. **▶️ Ejecuta**: El comando más simple del mundo:
   ```bash
   docker-compose up -d
   ```
4. **🌐 Abre**: http://localhost:8080 en tu navegador
5. **📚 Explora**: Las APIs en http://localhost:8080/swagger-ui.html

### ⚡ Si ya tienes experiencia básica

1. **Clonar el repositorio**
    ```bash
    git clone <tu-repositorio>
    cd Proyecto_Tecnologias
    ```

2. **Levantar todo el stack**
    ```bash
    # Levantar todos los servicios
    docker-compose up -d

    # Ver logs en tiempo real
    docker-compose logs -f
    ```

3. **Acceder a los servicios principales**
    - **🏠 Aplicación principal**: http://localhost:8080
    - **📋 APIs documentadas**: http://localhost:8080/swagger-ui.html
    - **🎛️ Portainer (gestión Docker)**: http://localhost:9090
    - **📊 Grafana (métricas)**: http://localhost:3000 (admin/admin)
    - **🔍 SonarQube (calidad)**: http://localhost:9000 (admin/admin)

4. **Verificar que todo funciona**
    ```bash
    # Ver estado de todos los servicios
    docker-compose ps

    # Probar una API
    curl http://localhost:8080/api/usuarios
    ```

### 🔧 Servicios individuales (para desarrollo)

Si quieres trabajar en un servicio específico:

```bash
# Solo API Gateway
docker-compose up api-gateway

# Solo base de datos
docker-compose up oracle

# Servicios de monitoreo
docker-compose up grafana loki prometheus
```

## 📚 Guías de Aprendizaje y Recursos

### Documentación del Proyecto
Cada tecnología tiene su propia guía detallada en la carpeta `docs/`:

- [☕ Java con Spring Boot](docs/java-spring.md) - *¡Comienza aquí si eres principiante!*
- [🏗️ Arquitectura de Microservicios](docs/microservices.md)
- [🐳 Docker y Containerización](docs/docker.md)
- [🎛️ Portainer - Gestión de Docker](docs/portainer.md)
- [🐙 Docker Swarm - Orquestación](docs/docker-swarm.md)
- [📋 OpenAPI/Swagger - Documentación de APIs](docs/swagger.md)
- [🔄 GitLab CI/CD - Pipelines Automatizados](docs/gitlab-ci.md)
- [📊 Grafana - Dashboards y Métricas](docs/grafana.md)
- [📝 Loki - Agregación de Logs](docs/loki.md)
- [🔍 SonarQube - Análisis de Calidad](docs/sonarqube.md)
- [⚡ JMeter - Pruebas de Carga](docs/jmeter.md)
- [🗄️ Oracle Database](docs/oracle.md)

### 📖 Materiales de Referencia y Teoría

#### Arquitectura de Microservicios
- **"Building Microservices"** - Sam Newman
  - [📚 Libro en Amazon](https://www.amazon.com/Building-Microservices-Designing-Fine-Grained-Systems/dp/1491950358)
  - [🌐 Versión Digital](https://www.oreilly.com/library/view/building-microservices/9781491950340/)
- **"Microservices Patterns"** - Chris Richardson
  - [📚 Libro](https://microservices.io/book)
  - [🎯 Patrones de Microservicios](https://microservices.io/patterns/)
- **Spring Cloud Documentation**
  - [📖 Documentación Oficial](https://spring.io/projects/spring-cloud)
  - [🎓 Spring Cloud Guides](https://spring.io/guides)

#### Docker y Containerización
- **Docker Documentation**
  - [📚 Docker Docs](https://docs.docker.com/)
  - [🎓 Docker Labs](https://labs.play-with-docker.com/)
- **"Docker Deep Dive"** - Nigel Poulton
  - [📚 Libro](https://www.amazon.com/Docker-Deep-Dive-Nigel-Poulton/dp/1521822808)
- **Docker Swarm y Portainer**
  - [🐳 Docker Swarm](https://docs.docker.com/engine/swarm/)
  - [🎛️ Portainer](https://docs.portainer.io/)

#### DevOps y CI/CD
- **GitLab CI/CD**
  - [📖 Documentación](https://docs.gitlab.com/ee/ci/)
  - [🎯 GitLab Learning](https://about.gitlab.com/learn/)
- **"The DevOps Handbook"** - Gene Kim, Jez Humble, Patrick Debois
  - [📚 Libro](https://www.amazon.com/DevOps-Handbook-World-Class-Reliability-Organizations/dp/1942788002)
- **12-Factor App Methodology**
  - [📋 12factor.net](https://12factor.net/)

#### Observabilidad
- **Grafana University**
  - [🎓 Cursos Gratuitos](https://university.grafana.com/)
  - [📊 Dashboard Examples](https://grafana.com/grafana/dashboards/)
- **Prometheus Documentation**
  - [📖 Prometheus Docs](https://prometheus.io/docs/)
  - [🔍 PromQL Tutorial](https://prometheus.io/docs/prometheus/latest/querying/basics/)
- **"Observability Engineering"** - Charity Majors, Liz Fong-Jones, George Miranda
  - [📚 Libro](https://www.oreilly.com/library/view/observability-engineering/9781492076438/)

#### Calidad de Código
- **SonarQube Documentation**
  - [📖 SonarQube Docs](https://docs.sonarsource.com/sonarqube/)
  - [🎯 SonarQube Academy](https://academy.sonarsource.com/)
- **Clean Code - Robert C. Martin**
  - [📚 Libro](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)
- **Test-Driven Development**
  - [📋 TDD Resources](https://martinfowler.com/bliki/TestDrivenDevelopment.html)

### 🎯 Rutas de Aprendizaje Recomendadas

#### 🚀 Nivel Principiante (4 semanas)
**Si nunca has usado estas tecnologías, comienza aquí:**

1. **📚 Semana 1: Java y Spring Boot**
   - Lee: [Java con Spring Boot](docs/java-spring.md)
   - Crea tu primera aplicación web
   - Aprende sobre REST APIs

2. **🐳 Semana 2: Docker Básico**
   - Lee: [Docker y Containerización](docs/docker.md)
   - Instala Docker en tu máquina
   - Crea y ejecuta tu primer contenedor

3. **📋 Semana 3: APIs y Documentación**
   - Lee: [OpenAPI/Swagger](docs/swagger.md)
   - Explora las APIs del proyecto
   - Prueba endpoints desde Swagger UI

4. **🗄️ Semana 4: Bases de Datos**
   - Lee: [Oracle Database](docs/oracle.md)
   - Entiende JPA y Hibernate
   - Practica con consultas y relaciones

#### ⚡ Nivel Intermedio (4 semanas)
**Una vez que dominas lo básico:**

1. **🏗️ Arquitectura de Microservicios**
   - Lee: [Microservicios](docs/microservices.md)
   - Entiende servicios independientes
   - Aprende sobre comunicación entre servicios

2. **🎛️ Gestión con Portainer**
   - Lee: [Portainer](docs/portainer.md)
   - Gestiona contenedores visualmente
   - Configura stacks y redes

3. **🐙 Orquestación con Docker Swarm**
   - Lee: [Docker Swarm](docs/docker-swarm.md)
   - Despliega aplicaciones distribuidas
   - Aprende sobre clustering

4. **📊 Monitoreo Básico**
   - Lee: [Grafana](docs/grafana.md)
   - Crea tus primeros dashboards
   - Visualiza métricas de aplicación

#### 🎯 Nivel Avanzado (4 semanas)
**Para dominar el desarrollo moderno:**

1. **🔄 CI/CD con GitLab**
   - Lee: [GitLab CI/CD](docs/gitlab-ci.md)
   - Configura pipelines automatizados
   - Implementa despliegue continuo

2. **📝 Logs y Observabilidad**
   - Lee: [Loki](docs/loki.md)
   - Centraliza logs de todos los servicios
   - Implementa tracing distribuido

3. **🔍 Calidad de Código**
   - Lee: [SonarQube](docs/sonarqube.md)
   - Analiza calidad automáticamente
   - Implementa quality gates

4. **⚡ Pruebas de Performance**
   - Lee: [JMeter](docs/jmeter.md)
   - Crea pruebas de carga
   - Identifica cuellos de botella

### 🏆 Certificaciones Recomendadas

- **Docker Certified Associate**
  - [🎓 Docker Certification](https://docker.com/certification)
- **Certified Kubernetes Administrator (CKA)**
  - [🎓 CNCF Certification](https://www.cncf.io/certification/cka/) - *Para aprendizaje avanzado*
- **AWS Certified DevOps Engineer**
  - [🎓 AWS Certification](https://aws.amazon.com/certification/)
- **Spring Professional Certification**
  - [🎓 Pivotal Certification](https://pivotal.io/training)

## 🛠️ Configuración del Entorno

### Prerrequisitos
- Docker y Docker Compose
- Java 11+
- Git
- Maven

### Variables de Entorno
Copia el archivo `.env.example` a `.env` y ajusta las variables según tu entorno.

## 📈 Monitoreo y Logs

El proyecto incluye una stack completa de monitoreo:
- **Grafana**: Dashboards para métricas de aplicación y sistema
- **Loki**: Centralización de logs
- **Prometheus**: Recolección de métricas (integrado con Grafana)

## 🔍 Calidad de Código

- **SonarQube**: Análisis estático de código
- **GitLab CI**: Análisis automático en cada push
- **Quality Gates**: Bloqueo de merge si no se cumplen estándares

## 🚢 Despliegue

### Desarrollo Local
```bash
docker-compose -f docker-compose.dev.yml up -d
```

### Producción con Docker Swarm
```bash
docker stack deploy -c docker-compose.swarm.yml tech-stack
```

## 🎓 Guía de Aprendizaje Paso a Paso

### 📚 Fase 0: Preparación (Si eres completamente nuevo)

**Tiempo estimado**: 1 semana
**Objetivo**: Tener tu entorno listo para el desarrollo moderno

1. **Día 1-2: Java y Spring Boot**
   - Lee: [Java con Spring Boot](docs/java-spring.md)
   - Instala Java 11+ y Maven
   - Crea tu primera aplicación "Hola Mundo"
   - **Meta**: Ejecutar una app Spring Boot en tu máquina

2. **Día 3-4: Docker Básico**
   - Lee: [Docker y Containerización](docs/docker.md)
   - Instala Docker Desktop
   - Ejecuta tu primer contenedor
   - **Meta**: `docker run hello-world` funcionando

3. **Día 5-7: Proyecto Completo**
   - Clona este repositorio
   - Ejecuta `docker-compose up -d`
   - Explora las APIs con Swagger UI
   - **Meta**: Todo el stack funcionando en http://localhost:8080

### 🏗️ Fase 1: Arquitectura y Servicios (2-3 semanas)

**Tiempo estimado**: 2-3 semanas
**Objetivo**: Entender cómo funciona una arquitectura de microservicios

1. **Semana 1: Microservicios**
   - Lee: [Arquitectura de Microservicios](docs/microservices.md)
   - Explora cada servicio individual
   - Entiende la comunicación entre servicios
   - **Meta**: Crear un nuevo endpoint en un servicio existente

2. **Semana 2: APIs y Documentación**
   - Lee: [OpenAPI/Swagger](docs/swagger.md)
   - Prueba todas las APIs desde Swagger UI
   - Crea documentación para un nuevo endpoint
   - **Meta**: Documentar completamente una nueva funcionalidad

3. **Semana 3: Bases de Datos**
   - Lee: [Oracle Database](docs/oracle.md)
   - Explora las entidades JPA
   - Crea consultas personalizadas
   - **Meta**: Agregar un nuevo campo a una entidad

### 🐳 Fase 2: Containerización y Orquestación (2 semanas)

**Tiempo estimado**: 2 semanas
**Objetivo**: Dominar Docker y la gestión de contenedores

1. **Semana 1: Docker Avanzado**
   - Lee: [Docker y Containerización](docs/docker.md)
   - Crea tus propios Dockerfiles
   - Optimiza imágenes para producción
   - **Meta**: Crear imagen Docker para tu propia aplicación

2. **Semana 2: Gestión y Orquestación**
   - Lee: [Portainer](docs/portainer.md)
   - Lee: [Docker Swarm](docs/docker-swarm.md)
   - Gestiona contenedores visualmente
   - Despliega stacks completos
   - **Meta**: Desplegar una aplicación completa en Swarm

### 📊 Fase 3: Observabilidad y Monitoreo (2 semanas)

**Tiempo estimado**: 2 semanas
**Objetivo**: Implementar monitoreo completo de aplicaciones

1. **Semana 1: Métricas y Dashboards**
   - Lee: [Grafana](docs/grafana.md)
   - Crea dashboards personalizados
   - Configura alertas básicas
   - **Meta**: Dashboard mostrando métricas de tu aplicación

2. **Semana 2: Logs y Troubleshooting**
   - Lee: [Loki](docs/loki.md)
   - Centraliza logs de todos los servicios
   - Implementa búsqueda y filtrado de logs
   - **Meta**: Encontrar y solucionar un problema usando logs

### 🔄 Fase 4: CI/CD y Calidad (2 semanas)

**Tiempo estimado**: 2 semanas
**Objetivo**: Automatizar el desarrollo y asegurar calidad

1. **Semana 1: Pipelines CI/CD**
   - Lee: [GitLab CI/CD](docs/gitlab-ci.md)
   - Configura un pipeline básico
   - Implementa build y test automáticos
   - **Meta**: Pipeline que se ejecuta en cada commit

2. **Semana 2: Calidad y Performance**
   - Lee: [SonarQube](docs/sonarqube.md)
   - Lee: [JMeter](docs/jmeter.md)
   - Analiza calidad de código
   - Crea pruebas de carga
   - **Meta**: Quality gate funcionando y pruebas de carga documentadas

### 🚀 Fase 5: Proyecto Final (2-4 semanas)

**Tiempo estimado**: 2-4 semanas
**Objetivo**: Aplicar todo lo aprendido en un proyecto real

1. **Planificación**: Diseña tu propio microservicio
2. **Desarrollo**: Implementa usando las mejores prácticas
3. **Testing**: Pruebas unitarias, integración y carga
4. **Despliegue**: CI/CD completo con monitoreo
5. **Documentación**: APIs, arquitectura y guías de uso

## 🏆 Mejores Prácticas Implementadas

### Arquitectura
- ✅ **Separación de responsabilidades** por microservicio
- ✅ **API Gateway** para enrutamiento centralizado
- ✅ **Service Discovery** automático
- ✅ **Configuración centralizada** con Spring Cloud Config
- ✅ **Documentación OpenAPI** completa

### Desarrollo
- ✅ **Principio SOLID** en el diseño de clases
- ✅ **Inyección de dependencias** con Spring
- ✅ **Validaciones** automáticas con Bean Validation
- ✅ **Manejo de errores** consistente
- ✅ **Logging estructurado** con SLF4J

### Testing
- ✅ **Tests unitarios** con JUnit 5
- ✅ **Tests de integración** con Testcontainers
- ✅ **Cobertura de código** con JaCoCo
- ✅ **Quality Gates** con SonarQube

### DevOps
- ✅ **Containerización** completa con Docker
- ✅ **CI/CD** automatizado con GitLab
- ✅ **Multi-entorno** (dev, staging, prod)
- ✅ **Infrastructure as Code** con Docker Compose
- ✅ **Orquestación** con Docker Swarm

### Observabilidad
- ✅ **Métricas** con Micrometer/Prometheus
- ✅ **Logs centralizados** con Loki
- ✅ **Dashboards** con Grafana
- ✅ **Health checks** automáticos
- ✅ **Alerting** configurado

## 🚀 Próximos Pasos y Mejoras

### Funcionalidades Pendientes
- [ ] Implementar autenticación OAuth2/JWT
- [ ] Agregar cache con Redis
- [ ] Implementar mensajería con RabbitMQ/Kafka
- [ ] Crear service mesh con Istio
- [ ] Migrar a Kubernetes
- [ ] Implementar API Gateway avanzado con Kong
- [ ] Agregar contratos con OpenAPI/Swagger
- [ ] Implementar circuit breakers con Resilience4j

### Mejoras Técnicas
- [ ] Agregar tests de performance con JMeter
- [ ] Implementar feature flags
- [ ] Configurar canary deployments
- [ ] Agregar distributed tracing con Zipkin
- [ ] Implementar API versioning
- [ ] Configurar rate limiting avanzado
- [ ] Agregar compresión de respuestas
- [ ] Implementar graceful shutdown

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Este proyecto está diseñado para el aprendizaje colaborativo.

### Cómo Contribuir
1. **Fork** del proyecto
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. **Commit** cambios (`git commit -m 'Agrega nueva funcionalidad'`)
4. **Push** a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abrir **Pull Request** con descripción detallada

### Guías de Contribución
- Seguir los estándares de código existentes
- Agregar tests para nuevas funcionalidades
- Actualizar documentación cuando corresponda
- Usar commits convencionales
- Mantener compatibilidad con versiones anteriores

## 📞 Soporte y Comunidad

- **📧 Email**: Para consultas técnicas
- **💬 Issues**: Para reportar bugs o solicitar features
- **📚 Wiki**: Documentación adicional del proyecto
- **🎯 Roadmap**: Plan de desarrollo futuro

## 📄 Licencia

Este proyecto está bajo la **Licencia MIT** - ver el archivo [LICENSE](LICENSE) para detalles.

---

## 🙏 Agradecimientos

Este proyecto se inspira en las mejores prácticas de la industria y las contribuciones de la comunidad open source:

- **Spring Framework** y **Spring Cloud** por proporcionar herramientas robustas
- **Docker** y la comunidad de containerización
- **CNCF** por estándares de cloud native
- **OpenAPI Initiative** por especificaciones de APIs
- La comunidad de **GitLab** por herramientas de DevOps

**¡Feliz aprendizaje! 🎓**
