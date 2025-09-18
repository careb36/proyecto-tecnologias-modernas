# Docker - Guía de Aprendizaje

## ¿Qué es Docker?

Docker es una plataforma de contenedores que permite empaquetar aplicaciones con todas sus dependencias en contenedores ligeros y portables.

## Conceptos Clave

### 1. Contenedor vs Imagen
- **Imagen**: Plantilla inmutable que contiene el código, dependencias y configuración
- **Contenedor**: Instancia ejecutable de una imagen

### 2. Dockerfile
Archivo de texto que contiene instrucciones para construir una imagen Docker.

```dockerfile
# Ejemplo de nuestro Dockerfile
FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 3. Docker Compose
Herramienta para definir y ejecutar aplicaciones multi-contenedor.

## Comandos Básicos

### Gestión de Imágenes
```bash
# Construir imagen
docker build -t mi-app .

# Listar imágenes
docker images

# Eliminar imagen
docker rmi mi-app
```

### Gestión de Contenedores
```bash
# Ejecutar contenedor
docker run -p 8080:8080 mi-app

# Listar contenedores activos
docker ps

# Parar contenedor
docker stop <container-id>

# Ver logs
docker logs <container-id>
```

### Docker Compose
```bash
# Iniciar todos los servicios
docker-compose up -d

# Ver estado de servicios
docker-compose ps

# Ver logs de un servicio
docker-compose logs app

# Parar todos los servicios
docker-compose down
```

## Ejercicios Prácticos

### Ejercicio 1: Ejecutar la Aplicación
```bash
# 1. Construir la imagen
mvn clean package
docker build -t tech-stack-app .

# 2. Ejecutar solo la app (sin base de datos)
docker run -p 8080:8080 tech-stack-app
```

### Ejercicio 2: Stack Completo
```bash
# Ejecutar todo el stack
docker-compose up -d

# Verificar que todos los servicios estén corriendo
docker-compose ps

# Acceder a los servicios:
# - App: http://localhost:8080
# - Grafana: http://localhost:3000
# - SonarQube: http://localhost:9000
```

### Ejercicio 3: Debugging
```bash
# Entrar al contenedor de la aplicación
docker exec -it <app-container-id> /bin/bash

# Ver logs en tiempo real
docker-compose logs -f app

# Reiniciar un servicio específico
docker-compose restart app
```

## Mejores Prácticas

### 1. Optimización de Imágenes
- Usar imágenes base ligeras (alpine, slim)
- Usar multi-stage builds
- Minimizar layers
- No incluir secretos en la imagen

### 2. Seguridad
- No ejecutar como root
- Usar usuario no privilegiado
- Escanear vulnerabilidades
- Firmar imágenes

### 3. Desarrollo
- Usar .dockerignore
- Volumes para desarrollo
- Health checks
- Configuración por variables de entorno

## Troubleshooting Común

### Problemas de Red
```bash
# Verificar redes Docker
docker network ls

# Inspeccionar red específica
docker network inspect <network-name>
```

### Problemas de Volúmenes
```bash
# Listar volúmenes
docker volume ls

# Limpiar volúmenes no utilizados
docker volume prune
```

### Problemas de Memoria/CPU
```bash
# Ver uso de recursos
docker stats

# Limitar recursos de un contenedor
docker run --memory=512m --cpus=0.5 mi-app
```

## 📚 Recursos de Aprendizaje y Referencias

### Documentación Oficial

#### Docker Core
- **📖 [Docker Documentation](https://docs.docker.com/)** - Documentación completa oficial
- **🎓 [Docker Labs](https://labs.play-with-docker.com/)** - Entorno de práctica gratuito
- **📖 [Docker Hub](https://hub.docker.com/)** - Repositorio oficial de imágenes
- **📖 [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)**

#### Docker Compose
- **📖 [Compose Documentation](https://docs.docker.com/compose/)**
- **🎓 [Compose Getting Started](https://docs.docker.com/compose/gettingstarted/)**
- **📖 [Compose File Reference](https://docs.docker.com/compose/compose-file/)**

### Libros y Cursos

#### Libros Recomendados
- **"Docker Deep Dive" - Nigel Poulton**
  - **📚 [Comprar en Amazon](https://www.amazon.com/Docker-Deep-Dive-Nigel-Poulton/dp/1521822808)**
  - **Nivel**: Intermedio
  - **Contenido**: Conceptos avanzados, troubleshooting, producción

- **"The Docker Book" - James Turnbull**
  - **📚 [Versión Digital](https://dockerbook.com/)**
  - **Nivel**: Principiante/Intermedio
  - **Contenido**: Guía completa desde cero

#### Cursos Online
- **Docker for Beginners** (Udemy)
- **Docker Mastery** (Udemy) - Nigel Poulton
- **Docker and Kubernetes** (Coursera)
- **Learn Docker** (Codecademy)

### Comunidades y Blogs

#### Comunidades
- **Docker Community Forums**: [Foros oficiales](https://forums.docker.com/)
- **Reddit**: r/docker, r/devops
- **Stack Overflow**: Etiquetas `docker`, `docker-compose`
- **Docker Slack Community**

#### Blogs Técnicos
- **Docker Blog**: [Blog oficial](https://www.docker.com/blog/)
- **Nigel Poulton**: [Docker blogs](https://nigelpoulton.com/)
- **Ivan Velichko**: [Docker tips](https://iximiuz.com/)
- **Jessie Frazelle**: [Container security](https://blog.jessfraz.com/)

### Herramientas y Utilidades

#### Desarrollo
- **Docker Desktop**: IDE para desarrollo local
- **VS Code Docker Extension**: Integración con editor
- **Docker Scout**: Análisis de seguridad de imágenes

#### Monitoreo
- **Portainer**: Gestión visual de Docker
- **Docker Stats**: Monitoreo básico de contenedores
- **cAdvisor**: Métricas detalladas de contenedores

### Casos de Uso Avanzados

#### Desarrollo
```bash
# Hot reload para desarrollo
docker run -v $(pwd):/app -p 8080:8080 mi-app

# Multi-stage builds para optimización
FROM maven:3.8.1-openjdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Producción
```bash
# Health checks
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Logging estructurado
docker run --log-driver=json-file --log-opt max-size=10m --log-opt max-file=3 mi-app

# Secrets management
docker run --secret mysql_password mi-app
```

## 🎯 Rutas de Aprendizaje

### Nivel Principiante (1-2 semanas)
1. **Instalación y primeros pasos**
2. **Comandos básicos** (run, ps, logs, exec)
3. **Creación de Dockerfiles simples**
4. **Uso de Docker Hub**

### Nivel Intermedio (2-4 semanas)
1. **Docker Compose** para aplicaciones multi-contenedor
2. **Volúmenes y redes** Docker
3. **Optimización de imágenes**
4. **Debugging y troubleshooting**

### Nivel Avanzado (4-6 semanas)
1. **Docker Swarm** para orquestación
2. **Seguridad** de contenedores
3. **CI/CD** con Docker
4. **Kubernetes** como siguiente paso

## 🏆 Mejores Prácticas Implementadas

### Optimización de Imágenes
- ✅ **Multi-stage builds** para reducir tamaño
- ✅ **Imágenes base ligeras** (alpine, slim)
- ✅ **Cache de layers** inteligente
- ✅ **.dockerignore** para excluir archivos innecesarios

### Seguridad
- ✅ **Usuario no root** en contenedores
- ✅ **Imágenes oficiales** y verificadas
- ✅ **Actualizaciones regulares** de dependencias
- ✅ **Secrets management** apropiado

### Desarrollo
- ✅ **Hot reload** para desarrollo rápido
- ✅ **Volúmenes** para persistencia de datos
- ✅ **Health checks** automáticos
- ✅ **Logging** estructurado

## 🔧 Troubleshooting Común

### Problemas de Red
```bash
# Verificar conectividad
docker network ls
docker network inspect bridge

# Crear red personalizada
docker network create mi-red
docker run --network mi-red mi-app
```

### Problemas de Rendimiento
```bash
# Monitoreo de recursos
docker stats

# Limitar recursos
docker run --memory=512m --cpus=0.5 mi-app

# Ver logs detallados
docker logs --tail 100 -f mi-container
```

### Problemas de Almacenamiento
```bash
# Gestionar volúmenes
docker volume ls
docker volume prune

# Verificar espacio en disco
docker system df
docker system prune -a
```

## 🚀 Próximos Pasos

### Inmediatos
1. **Dominar Docker Compose** para stacks complejos
2. **Aprender Docker Swarm** para orquestación básica
3. **Implementar CI/CD** con Docker
4. **Explorar seguridad** de contenedores

### A Largo Plazo
1. **Portainer** para gestión avanzada de contenedores
2. **Docker Swarm** clustering avanzado
3. **Service Mesh** con Docker networking
4. **Edge computing** con containers
5. **Kubernetes** (opcional para migración futura)

## 📊 Métricas de Éxito

### Principiante
- ✅ Crear y ejecutar contenedores básicos
- ✅ Construir imágenes personalizadas
- ✅ Usar Docker Compose para aplicaciones simples

### Intermedio
- ✅ Optimizar imágenes para producción
- ✅ Implementar estrategias de logging
- ✅ Configurar redes y volúmenes complejos

### Avanzado
- ✅ Desplegar en clúster con Swarm/Kubernetes
- ✅ Implementar seguridad avanzada
- ✅ Automatizar pipelines de CI/CD

## 🎓 Certificaciones Recomendadas

- **Docker Certified Associate (DCA)**
  - [📋 Información](https://docker.com/certification)
  - [🎯 Preparación](https://docker.com/certification)

- **Certified Kubernetes Administrator (CKA)**
  - [📋 CNCF Certification](https://www.cncf.io/certification/cka/) - *Para aprendizaje avanzado*

## 📞 Comunidad y Soporte

- **🐙 [GitHub Docker](https://github.com/docker)** - Código fuente y issues
- **💬 [Docker Community](https://forums.docker.com/)** - Foros de soporte
- **📧 [Docker Support](https://docker.com/support)** - Soporte oficial
- **🎯 [Docker Meetups](https://www.meetup.com/cities)** - Eventos locales

---

*Docker ha revolucionado el desarrollo y despliegue de aplicaciones. Este proyecto demuestra las mejores prácticas para usar Docker en entornos de microservicios.*
