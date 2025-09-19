# 🎓 Proyecto de Aprendizaje de Tecnologías Modernas

> **Un sistema completo de aprendizaje práctico para desarrolladores modernos**

---

## 📖 **¿Qué es este documento?**

Este README está estructurado en **dos secciones principales** para maximizar tu experiencia de aprendizaje:

### 🎯 **Sección 1: Sobre el Proyecto** (Páginas 1-4)
**Para entender QUÉ incluye este proyecto**
- Arquitectura técnica completa
- Tecnologías implementadas
- Cómo ejecutar y usar el sistema
- Servicios disponibles y URLs de acceso

### 📚 **Sección 2: Ruta de Aprendizaje** (Página 5+)
**Para aprender CÓMO usar estas tecnologías**
- Guía paso a paso desde principiante
- Ejemplos prácticos y código
- Recursos de aprendizaje
- Certificaciones recomendadas

**👉 Comienza por la Sección 1 si quieres entender el proyecto, o ve directo a la Sección 2 si ya sabes ejecutar aplicaciones.**

---

## 🎯 **Sección 1: Sobre el Proyecto**

### 🌟 **¿Qué encontrarás aquí?**

Este proyecto es una **implementación completa y profesional** de arquitectura de microservicios que incluye:

- **🏗️ Arquitectura Empresarial**: 7 microservicios + infraestructura completa
- **🐳 DevOps Moderno**: Containerización, orquestación y CI/CD
- **📊 Observabilidad Completa**: Monitoreo, logging y métricas
- **🔍 Calidad de Código**: Testing automatizado y análisis estático
- **📚 Aprendizaje Práctico**: Desde conceptos básicos hasta producción

### 👥 **¿Para quién está diseñado?**

**Perfecto para ti si eres:**
- **Desarrollador tradicional** buscando modernizarse
- **Estudiante** aprendiendo desarrollo web moderno
- **Profesional** necesitando actualizar sus habilidades
- **Equipo técnico** buscando mejores prácticas

**Si vienes de:**
- Programación monolítica (Java puro, .NET, PHP)
- Despliegue manual por FTP
- Sin experiencia en contenedores
- Desarrollo sin DevOps

**¡Este proyecto es tu puente al desarrollo moderno!** 🚀

### 🎯 **¿Qué aprenderás?**

#### **Nivel Básico** (Semanas 1-4)
- ✅ Java moderno con Spring Boot
- ✅ APIs REST y servicios web
- ✅ Bases de datos con JPA/Hibernate
- ✅ Testing automatizado
- ✅ Primeros pasos con Docker

#### **Nivel Intermedio** (Semanas 5-8)
- ✅ Arquitectura de microservicios
- ✅ Containerización completa
- ✅ Orquestación con Docker Swarm
- ✅ Documentación de APIs
- ✅ Monitoreo básico

#### **Nivel Avanzado** (Semanas 9-12)
- ✅ CI/CD con pipelines
- ✅ Observabilidad completa
- ✅ Calidad de código con SonarQube
- ✅ Pruebas de carga
- ✅ Despliegue en producción

## 🛠️ **Stack Tecnológico Completo**

### ☕ **Backend & APIs**
- **Java 11+** con Spring Boot 2.7
- **Spring Cloud** - Arquitectura de microservicios completa
- **Spring Cloud Gateway** - API Gateway inteligente
- **Netflix Eureka** - Service Discovery automático
- **Spring Cloud Config** - Configuración centralizada
- **Spring Data JPA** - Persistencia de datos
- **OpenAPI 3 / Swagger** - Documentación de APIs

### 🗄️ **Bases de Datos**
- **Oracle Database** - Base de datos relacional empresarial
- **PostgreSQL** - Base de datos para herramientas
- **H2 Database** - Base de datos en memoria para testing
- **Testcontainers** - Contenedores de BD para pruebas

### 🐳 **Containerización & Orquestación**
- **Docker** - Containerización de aplicaciones
- **Docker Compose** - Orquestación local
- **Docker Swarm** - Orquestación distribuida
- **Portainer** - Gestión visual de contenedores

### 📊 **Monitoreo & Observabilidad**
- **Grafana** - Dashboards y visualización
- **Prometheus** - Recolección de métricas
- **Loki** - Agregación centralizada de logs
- **Node Exporter** - Métricas del sistema
- **Micrometer** - Métricas de aplicación
- **Spring Boot Actuator** - Health checks

### 🔍 **Calidad & Testing**
- **SonarQube** - Análisis de calidad de código
- **JaCoCo** - Cobertura de pruebas
- **JUnit 5** - Framework de testing moderno
- **Mockito** - Framework de mocking para tests unitarios
- **Testcontainers** - Tests de integración
- **Maven** - Gestión de dependencias

### 🔄 **CI/CD & DevOps**
- **GitLab CI/CD** - Pipelines automatizados
- **Git** - Control de versiones
- **Infrastructure as Code** - Docker Compose
- **Quality Gates** - Control de calidad automático
- **Multi-entorno** - Dev, staging, producción

### 🧪 **Herramientas de Desarrollo**
- **IntelliJ IDEA / VS Code** - IDEs recomendados
- **Postman** - Testing de APIs
- **Git Bash / Terminal** - Línea de comandos
- **Docker Desktop** - Runtime de Docker (requerido para ejecutar contenedores)
- **Portainer** - Interfaz web incluida en el proyecto para gestión visual de contenedores

## 🏗️ **Arquitectura del Sistema**

### 🎯 **¿Por qué esta arquitectura?**

Este proyecto implementa una **arquitectura de microservicios empresarial** siguiendo las mejores prácticas de la industria. Cada componente tiene un propósito específico y está diseñado para el aprendizaje práctico.

### 🏛️ **Componentes Principales**

#### **🏠 Infraestructura Core**
- **API Gateway** - Punto de entrada único con routing inteligente
- **Config Server** - Configuración centralizada de todos los servicios
- **Service Registry** - Service Discovery automático con Eureka

#### **🎯 Microservicios de Negocio**
- **👥 Usuario Service** - Gestión completa de usuarios y autenticación
- **📦 Product Service** - Catálogo de productos e inventario
- **📋 Order Service** - Procesamiento de pedidos y transacciones
- **📢 Notification Service** - Sistema de notificaciones multi-canal

#### **📊 Stack de Observabilidad**
- **Grafana** - Dashboards y visualización de métricas
- **Prometheus** - Recolección y almacenamiento de métricas
- **Loki** - Agregación centralizada de logs

#### **🔍 Calidad y DevOps**
- **SonarQube** - Análisis de calidad de código
- **Portainer** - Gestión visual de contenedores

### 📁 **Estructura de Directorios**

Cada servicio de microservicio es un proyecto independiente basado en Maven, con su propio `pom.xml`, configuración Spring Boot completa y estructura de directorios estándar. Esto permite el desarrollo, testing y despliegue independiente de cada componente.

```
├── 🐳 api-gateway/          # API Gateway Service
├── ⚙️ config-server/        # Configuración centralizada
├── 🏷️ service-registry/     # Service Discovery
├── 👥 usuario-service/      # Gestión de usuarios
├── 📦 product-service/      # Catálogo de productos
├── 📋 order-service/        # Procesamiento de pedidos
├── 📢 notification-service/ # Notificaciones
├── 📊 monitoring/           # Stack de observabilidad
├── 🗄️ oracle/              # Base de datos Oracle
└── 🐳 docker-compose.yml    # Orquestación completa
```

### ✅ **Características Técnicas**

Cada servicio incluye:
- **Proyecto Maven independiente** con `pom.xml`
- **Dockerfile** para containerización
- **Configuración Spring Boot** completa
- **Documentación OpenAPI/Swagger**
- **Tests automatizados** y health checks
- **Métricas integradas** con Micrometer

### 🔄 **Cómo Funciona el Ecosistema**

Este proyecto implementa un **sistema completo de e-commerce** donde todas las tecnologías trabajan juntas para proporcionar una experiencia de desarrollo y operación empresarial moderna:

#### **🏪 Flujo de Negocio**
1. **👥 Gestión de Usuarios**: El servicio de usuarios permite registrar y autenticar clientes
2. **📦 Catálogo de Productos**: Los productos se gestionan con inventario y precios
3. **📋 Procesamiento de Pedidos**: Los usuarios pueden crear pedidos que combinan productos del catálogo
4. **📢 Notificaciones**: El sistema envía confirmaciones y actualizaciones de pedidos

#### **⚙️ Infraestructura Técnica**
- **API Gateway** actúa como punto de entrada único, enrutando las requests a los microservicios apropiados
- **Config Server** centraliza la configuración de todos los servicios, permitiendo cambios sin reinicios
- **Service Registry** mantiene un directorio dinámico de servicios para comunicación automática
- **Oracle Database** almacena todos los datos de negocio de forma persistente y escalable

#### **📊 Observabilidad Completa**
- **Prometheus** recolecta métricas de rendimiento de todas las aplicaciones
- **Grafana** visualiza estas métricas en dashboards personalizables
- **Loki** centraliza logs de todos los contenedores para troubleshooting
- **Spring Boot Actuator** proporciona health checks y métricas de aplicación

#### **🔍 Calidad y DevOps**
- **SonarQube** analiza la calidad del código y previene deuda técnica
- **Portainer** permite gestión visual de contenedores Docker
- **GitLab CI/CD** automatiza testing, building y despliegue
- **Docker Compose/Swarm** orquesta el despliegue completo del sistema

#### **🎯 Casos de Uso Prácticos**
- **Desarrollo Local**: Ejecutar todo el stack con un solo comando
- **Testing de Integración**: Verificar que todos los servicios funcionen juntos
- **Monitoreo en Tiempo Real**: Observar métricas de performance y salud del sistema
- **Desarrollo de Features**: Agregar nuevas funcionalidades siguiendo patrones establecidos
- **Aprendizaje Tecnológico**: Explorar cada tecnología en un contexto real

---

## 🚀 **Inicio Rápido**

### ⚡ **Ejecutar Todo el Sistema**

```bash
# Levantar todos los servicios del sistema (microservicios, base de datos, monitoreo, etc.)
docker-compose up -d

# Ver estado de servicios
docker-compose ps

# Ver logs en tiempo real
docker-compose logs -f
```

### 🌐 **URLs de Acceso**

#### **🏗️ Arquitectura Principal**
- **API Gateway**: http://localhost:8080
- **Config Server**: http://localhost:8888
- **Service Registry**: http://localhost:8761

#### **🎯 Servicios de Negocio**
- **Usuario Service**: http://localhost:8081
- **Product Service**: http://localhost:8082
- **Order Service**: http://localhost:8083
- **Notification Service**: http://localhost:8084

#### **📊 Monitoreo**
- **Grafana**: http://localhost:3000 (`admin`/`admin`)
- **Prometheus**: http://localhost:9090
- **SonarQube**: http://localhost:9000 (`admin`/`admin`)
- **Portainer**: http://localhost:9001 (`admin` / configurar contraseña en primer acceso)
- **Loki**: http://localhost:3100

#### **🗄️ Bases de Datos**
- **Oracle Database Web**: http://localhost:5500 (`system`/`oracle`)
- **PostgreSQL**: No expuesto directamente (usado por SonarQube)

### 📋 **Documentación**
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Documentación Técnica**: `./docs/`

---

## 📚 **Sección 2: Ruta de Aprendizaje**

> **¿Ya entendiste qué incluye el proyecto? Ahora vamos a aprender CÓMO usarlo...**

### 🎯 **¿Qué encontrarás en esta sección?**

La **Sección 2** está dedicada completamente al **aprendizaje práctico**:

- **📚 Guía paso a paso** desde principiante absoluto
- **🛠️ Ejemplos de código** y comandos prácticos
- **🎯 Metas claras** en cada fase de aprendizaje
- **📖 Recursos adicionales** y lecturas recomendadas
- **🏆 Certificaciones** para validar tus conocimientos

### 📖 **Guía Completa de Aprendizaje**

**[📚 Ver Guía de Aprendizaje Paso a Paso](docs/learning-path.md)**

Esta guía te lleva desde **"nunca he usado Docker"** hasta **"despliegue en producción"** con:

- **5 fases de aprendizaje** progresivas
- **Ejemplos prácticos** con este mismo proyecto
- **Código real** que puedes ejecutar
- **Recursos de estudio** por nivel
- **Certificaciones recomendadas**

### 🎓 **¿Por dónde empezar?**

#### **🚀 Si eres COMPLETAMENTE principiante:**
1. Lee la [Fase 0: Preparación](docs/learning-path.md#fase-0-preparación)
2. Instala Docker y Java
3. Ejecuta `docker-compose up -d`
4. Explora las APIs con Swagger

#### **⚡ Si tienes experiencia básica:**
1. Ve directo a la [Fase 1: Arquitectura](docs/learning-path.md#fase-1-arquitectura-y-servicios)
2. Aprende sobre microservicios
3. Modifica un servicio existente
4. Implementa nuevas funcionalidades

#### **🎯 Si eres avanzado:**
1. Revisa la [Fase 5: Proyecto Final](docs/learning-path.md#fase-5-proyecto-final)
2. Estudia los pipelines CI/CD
3. Aprende sobre observabilidad
4. Implementa mejoras al sistema

---

## 🔗 **Navegación Rápida**

| Sección | Contenido | Destino |
|---------|-----------|---------|
| **🏗️ Arquitectura** | Componentes técnicos del sistema | [Ver arriba](#arquitectura-del-sistema) |
| **🚀 Inicio Rápido** | Cómo ejecutar el proyecto | [Ver arriba](#inicio-rápido) |
| **📚 Aprendizaje** | Guía paso a paso de aprendizaje | [docs/learning-path.md](docs/learning-path.md) |
| **📖 Documentación** | Guías técnicas detalladas | [./docs/](./docs/) |
| **🧪 APIs** | Documentación interactiva | http://localhost:8080/swagger-ui.html |
| **📊 Monitoreo** | Dashboards y métricas | http://localhost:3000 |

---

## 🎯 **¿Qué sigue después de leer esto?**

1. **Si quieres entender el proyecto** → Lee la Sección 1 completa
2. **Si quieres aprender las tecnologías** → Ve a la [Guía de Aprendizaje](docs/learning-path.md)
3. **Si quieres ejecutar el proyecto** → Sigue el [Inicio Rápido](#inicio-rápido)
4. **Si quieres documentación técnica** → Explora la carpeta [./docs/](./docs/)

**¡Tu viaje hacia el desarrollo moderno comienza aquí!** 🚀✨



## 📚 **Recursos de Aprendizaje**

### 🎓 **Guía de Aprendizaje Completa**
**[📚 Ver Guía de Aprendizaje Paso a Paso](docs/learning-path.md)**

Esta guía te lleva desde **"nunca he usado Docker"** hasta **"despliegue en producción"** con:
- **5 fases de aprendizaje** progresivas
- **Ejemplos prácticos** con este mismo proyecto
- **Código real** que puedes ejecutar
- **Recursos de estudio** por nivel
- **Certificaciones recomendadas**

### 📖 **Documentación Técnica**
Cada tecnología tiene su propia guía detallada en la carpeta `docs/`:
- [☕ Java con Spring Boot](docs/java-spring.md)
- [🏗️ Arquitectura de Microservicios](docs/microservices.md)
- [🐳 Docker y Containerización](docs/docker.md)
- [📋 OpenAPI/Swagger](docs/swagger.md)
- [📊 Grafana](docs/grafana.md)
- [🔍 SonarQube](docs/sonarqube.md)
- [🗄️ Oracle Database](docs/oracle.md)

---

## 🤝 **Contribución**

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

---

## 📄 **Licencia**

Este proyecto está bajo la **Licencia MIT** - ver el archivo [LICENSE](LICENSE) para detalles.

---

**¡Feliz aprendizaje! 🎓**

### **Documentación Técnica por Tecnología**
Cada tecnología tiene su propia guía detallada en la carpeta `docs/`:

- [☕ Java con Spring Boot](docs/java-spring.md) - Fundamentos de Spring Boot
- [🏗️ Arquitectura de Microservicios](docs/microservices.md) - Arquitectura completa implementada
- [🐳 Docker y Containerización](docs/docker.md) - Containerización y Docker Compose
- [🎛️ Portainer - Gestión de Docker](docs/portainer.md) - Gestión visual de contenedores
- [🐙 Docker Swarm - Orquestación](docs/docker-swarm.md) - Orquestación distribuida
- [📋 OpenAPI/Swagger - Documentación de APIs](docs/swagger.md) - APIs y documentación
- [🔄 GitLab CI/CD - Pipelines Automatizados](docs/gitlab-ci.md) - CI/CD con GitLab
- [📊 Grafana - Dashboards y Métricas](docs/grafana.md) - Visualización y monitoreo
- [📝 Loki - Agregación de Logs](docs/loki.md) - Sistema de logs centralizado
- [🔍 SonarQube - Análisis de Calidad](docs/sonarqube.md) - Calidad y análisis de código
- [⚡ JMeter - Pruebas de Carga](docs/jmeter.md) - Testing de performance
- [🗄️ Oracle Database](docs/oracle.md) - Base de datos relacional

### Materiales de Referencia y Teoría

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

### Rutas de Aprendizaje Recomendadas

#### Nivel Principiante
**Si nunca has usado estas tecnologías, comienza aquí:**

1. **Java y Spring Boot**
   - Lee: [Java con Spring Boot](docs/java-spring.md)
   - Crea tu primera aplicación web
   - Aprende sobre REST APIs

2. **Docker Básico**
   - Lee: [Docker y Containerización](docs/docker.md)
   - Instala Docker en tu máquina
   - Crea y ejecuta tu primer contenedor

3. **APIs y Documentación**
   - Lee: [OpenAPI/Swagger](docs/swagger.md)
   - Explora las APIs del proyecto
   - Prueba endpoints desde Swagger UI

4. **Bases de Datos**
   - Lee: [Oracle Database](docs/oracle.md)
   - Entiende JPA y Hibernate
   - Practica con consultas y relaciones

#### Nivel Intermedio
**Una vez que dominas lo básico:**

1. **Arquitectura de Microservicios**
   - Lee: [Microservicios](docs/microservices.md)
   - Entiende servicios independientes
   - Aprende sobre comunicación entre servicios

2. **Gestión con Portainer**
   - Lee: [Portainer](docs/portainer.md)
   - Gestiona contenedores visualmente
   - Configura stacks y redes

3. **Orquestación con Docker Swarm**
   - Lee: [Docker Swarm](docs/docker-swarm.md)
   - Despliega aplicaciones distribuidas
   - Aprende sobre clustering

4. **Monitoreo Básico**
   - Lee: [Grafana](docs/grafana.md)
   - Crea tus primeros dashboards
   - Visualiza métricas de aplicación

#### Nivel Avanzado
**Para dominar el desarrollo moderno:**

1. **CI/CD con GitLab**
   - Lee: [GitLab CI/CD](docs/gitlab-ci.md)
   - Configura pipelines automatizados
   - Implementa despliegue continuo

2. **Logs y Observabilidad**
   - Lee: [Loki](docs/loki.md)
   - Centraliza logs de todos los servicios
   - Implementa tracing distribuido

3. **Calidad de Código**
   - Lee: [SonarQube](docs/sonarqube.md)
   - Analiza calidad automáticamente
   - Implementa quality gates

4. **Pruebas de Performance**
   - Lee: [JMeter](docs/jmeter.md)
   - Crea pruebas de carga
   - Identifica cuellos de botella

### Certificaciones Recomendadas

- **Docker Certified Associate**
  - [🎓 Docker Certification](https://docker.com/certification)
- **Certified Kubernetes Administrator (CKA)**
  - [🎓 CNCF Certification](https://www.cncf.io/certification/cka/) - *Para aprendizaje avanzado*
- **AWS Certified DevOps Engineer**
  - [🎓 AWS Certification](https://aws.amazon.com/certification/)
- **Spring Professional Certification**
  - [🎓 Pivotal Certification](https://pivotal.io/training)

## Configuración del Entorno

### Prerrequisitos
- Docker y Docker Compose
- Java 11+
- Git
- Maven

### Variables de Entorno
Copia el archivo `.env.example` a `.env` y ajusta las variables según tu entorno.


## Despliegue

### **Desarrollo Local**
```bash
# Ejecutar desde la raíz del proyecto (directorio Proyecto_Tecnologias)
# Stack completo para desarrollo
docker-compose up -d

# Ver logs en tiempo real
docker-compose logs -f

# Ver estado de servicios
docker-compose ps
```

### **Producción con Docker Swarm**
```bash
# Ejecutar desde la raíz del proyecto (directorio Proyecto_Tecnologias)
# Requiere Docker Swarm inicializado (docker swarm init)
docker swarm init
docker stack deploy -c docker-compose.swarm.yml tech-stack

# Ver servicios en Swarm
docker stack services tech-stack
```

### **Despliegue por Componentes**
```bash
# Solo arquitectura principal
docker-compose up api-gateway config-server service-registry

# Solo servicios de negocio
docker-compose up usuario-service product-service order-service notification-service

# Solo monitoreo
docker-compose up grafana prometheus loki node-exporter

# Solo bases de datos
docker-compose up oracle postgres
```

### **Monitoreo del Despliegue**
```bash
# Ver estado de todos los contenedores
docker-compose ps

# Ver logs de un servicio específico
docker-compose logs api-gateway
docker-compose logs grafana

# Ver métricas de recursos
docker stats

# Health checks
curl http://localhost:8080/health
curl http://localhost:8081/actuator/health
```

### **Gestión y Limpieza de Contenedores**
```bash
# Detener todos los servicios
docker-compose down

# Detener y eliminar volúmenes (¡CUIDADO: elimina datos persistentes!)
docker-compose down -v

# Limpiar contenedores, imágenes y redes no utilizadas
docker system prune -a

# Limpiar volúmenes no utilizados
docker volume prune

# Limpiar imágenes no utilizadas
docker image prune -a

# Reiniciar todo desde cero
docker-compose down -v
docker system prune -a
docker volume prune
docker-compose up -d

# Verificar estado después del reinicio
docker-compose ps
```


## Mejores Prácticas Implementadas

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
- ✅ **Tests unitarios** con JUnit 5 y Mockito
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

## Próximos Pasos y Mejoras

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

## Contribución

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

## Licencia

Este proyecto está bajo la **Licencia MIT** - ver el archivo [LICENSE](LICENSE) para detalles.

---


### **Listo para usar**
```bash
# Levantar todos los servicios del sistema (microservicios, base de datos, monitoreo, etc.)
docker-compose up -d

# Accede inmediatamente a:
# - APIs: http://localhost:8080
# - Monitoreo: http://localhost:3000
# - Documentación: http://localhost:8080/swagger-ui.html
```


**¡Feliz aprendizaje! 🎓**
