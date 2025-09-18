# Portainer - Guía de Aprendizaje

## ¿Qué es Portainer?

Portainer es una interfaz web ligera para gestionar entornos Docker. Proporciona una UI intuitiva para administrar contenedores, imágenes, volúmenes, redes y stacks Docker sin necesidad de usar la línea de comandos.

## Conceptos Clave

### 1. Endpoints
Conexiones a diferentes entornos Docker:
- Docker local
- Docker Swarm
- Kubernetes clusters
- Azure Container Instances

### 2. Stacks
Conjunto de servicios definidos en docker-compose que se despliegan juntos.

### 3. Services
Servicios individuales dentro de un Docker Swarm.

### 4. Environments
Diferentes entornos de contenedores que Portainer puede gestionar.

## Configuración Inicial

### 1. Acceso
- URL: http://localhost:9001
- Primera vez: Crear usuario admin
- Password: Mínimo 8 caracteres

### 2. Configuración del Endpoint
1. **Get Started** → **Docker**
2. Environment name: `Local Docker`
3. Environment URL: `/var/run/docker.sock`
4. **Connect**

## Funcionalidades Principales

### 1. Dashboard
Vista general del entorno:
- Número de contenedores (running/stopped)
- Imágenes disponibles
- Volúmenes y redes
- Uso de recursos

### 2. Containers
Gestión de contenedores:
- Iniciar/parar/reiniciar
- Ver logs en tiempo real
- Acceder al terminal (exec)
- Inspeccionar configuración
- Ver estadísticas de recursos

### 3. Images
Gestión de imágenes:
- Pull de Docker Hub
- Build desde Dockerfile
- Eliminar imágenes no utilizadas
- Ver historial de layers

### 4. Networks
Gestión de redes Docker:
- Crear redes personalizadas
- Conectar/desconectar contenedores
- Inspeccionar configuración de red

### 5. Volumes
Gestión de volúmenes:
- Crear volúmenes persistentes
- Backup y restore
- Limpiar volúmenes no utilizados

## Ejercicios Prácticos

### Ejercicio 1: Exploración Inicial
1. Accede a Portainer (http://localhost:9001)
2. Crea usuario admin
3. Conecta al endpoint local Docker
4. Explora el dashboard principal
5. Ve a **Containers** y familiarízate con el stack en ejecución

### Ejercicio 2: Gestión de Contenedores
1. Ve a **Containers**
2. Selecciona el contenedor de la aplicación Java
3. Ve **Logs** en tiempo real
4. Accede al **Console** del contenedor
5. Reinicia el contenedor desde Portainer
6. Verifica que se reinició correctamente

### Ejercicio 3: Gestión de Imágenes
1. Ve a **Images**
2. Pull una nueva imagen: `nginx:alpine`
3. Ejecuta un contenedor desde esa imagen:
   - Name: `test-nginx`
   - Port mapping: `8090:80`
4. Accede a http://localhost:8090
5. Elimina el contenedor y la imagen

### Ejercicio 4: Stack Management
1. Ve a **Stacks**
2. Observa el stack `proyecto_tecnologias`
3. Ve los servicios que lo componen
4. Edita el stack para agregar un nuevo servicio:

```yaml
  redis:
    image: redis:alpine
    ports:
      - "6379:6379"
    networks:
      - tech-stack
```

5. Update the stack
6. Verifica que Redis esté corriendo

### Ejercicio 5: Monitoreo de Recursos
1. Ve a **Containers**
2. Selecciona un contenedor con carga
3. Ve **Stats** para ver uso de CPU/memoria
4. Observa las métricas en tiempo real
5. Compara con las métricas de `docker stats`

## Gestión de Stacks

### 1. Deploy New Stack
1. **Stacks** → **Add stack**
2. Name: `monitoring-extra`
3. Web editor o Git repository
4. Ejemplo de stack:

```yaml
version: '3.8'
services:
  redis:
    image: redis:alpine
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    restart: unless-stopped

  redis-commander:
    image: rediscommander/redis-commander:latest
    ports:
      - "8081:8081"
    environment:
      - REDIS_HOSTS=local:redis:6379
    depends_on:
      - redis
    restart: unless-stopped

volumes:
  redis_data:
```

5. **Deploy the stack**

### 2. Stack Management
- **Start/Stop**: Controlar todo el stack
- **Edit**: Modificar docker-compose.yml
- **Update**: Aplicar cambios
- **Remove**: Eliminar stack completo

## Configuración de Usuarios y Equipos

### 1. User Management
1. **Settings** → **Users**
2. **Add user**
3. Configure roles:
   - **Administrator**: Acceso completo
   - **User**: Acceso limitado a recursos asignados
   - **Read-only**: Solo lectura

### 2. Teams
1. **Settings** → **Teams**
2. **Add team**
3. Asignar usuarios al equipo
4. Configurar permisos por equipo

### 3. Role-Based Access Control (RBAC)
```yaml
# Ejemplo de permisos por equipo
Team: Developers
Permissions:
  - Container operations (start/stop/restart)
  - View logs
  - Access console
  - Deploy stacks in specific namespace

Team: Operations
Permissions:
  - Full container management
  - Image management
  - Volume management
  - System settings
```

## Templates de Aplicaciones

### 1. App Templates
Portainer incluye templates predefinidos:
- WordPress
- MySQL
- PostgreSQL
- Nginx
- Redis
- RabbitMQ

### 2. Crear Custom Templates
1. **Settings** → **App Templates**
2. **Add template**
3. Configurar template personalizado:

```json
{
  "type": 3,
  "title": "Java Spring Boot App",
  "description": "Spring Boot application with PostgreSQL",
  "note": "Default credentials: admin/admin",
  "categories": ["development"],
  "platform": "linux",
  "logo": "https://spring.io/images/spring-logo.svg",
  "repository": {
    "url": "https://github.com/tu-usuario/spring-templates",
    "stackfile": "spring-postgres/docker-compose.yml"
  }
}
```

## Integración con Docker Swarm

### 1. Configurar Swarm Mode
```bash
# Inicializar Swarm (en el manager node)
docker swarm init

# Obtener token para workers
docker swarm join-token worker
```

### 2. Conectar Portainer a Swarm
1. **Environments** → **Add environment**
2. **Docker Swarm**
3. Name: `Production Swarm`
4. Endpoint URL: `tcp://swarm-manager:2376`
5. **Add environment**

### 3. Gestión de Servicios Swarm
- **Services**: Ver servicios del swarm
- **Stacks**: Deploy stacks en swarm mode
- **Networks**: Gestionar overlay networks
- **Secrets**: Gestionar Docker secrets
- **Configs**: Gestionar configuraciones

## Backup y Restore

### 1. Backup de Portainer
```bash
# Crear backup del volumen de Portainer
docker run --rm -v portainer_data:/data -v $(pwd):/backup alpine tar czf /backup/portainer-backup.tar.gz -C /data .

# Para Windows
docker run --rm -v portainer_data:/data -v %cd%:/backup alpine tar czf /backup/portainer-backup.tar.gz -C /data .
```

### 2. Restore de Portainer
```bash
# Restaurar backup
docker run --rm -v portainer_data:/data -v $(pwd):/backup alpine tar xzf /backup/portainer-backup.tar.gz -C /data

# Reiniciar Portainer
docker restart portainer
```

## Monitoreo y Alertas

### 1. Webhook Notifications
1. **Settings** → **Notifications**
2. **Add notification**
3. Configurar webhook:
   - URL: `https://hooks.slack.com/services/...`
   - Events: Container stopped, Stack updated

### 2. Edge Agent Monitoring
Para entornos remotos:
1. Deploy Edge Agent
2. Monitor remote environments
3. Receive notifications on connectivity issues

## Mejores Prácticas

### 1. Seguridad
- Usar HTTPS en producción
- Configurar authentication backend (LDAP/OAuth)
- Limitar acceso por IP
- Regular security updates
- No exponer Portainer públicamente

### 2. Gestión de Recursos
- Configurar resource limits en containers
- Monitor resource usage regularmente
- Clean up unused images/volumes
- Use health checks

### 3. Backup Strategy
- Backup regular de Portainer data
- Document environment configurations
- Version control stack definitions
- Test restore procedures

## Troubleshooting Común

### 1. Connection Issues
```bash
# Verificar Docker socket permissions
ls -la /var/run/docker.sock

# Verificar que Portainer tenga acceso
docker logs portainer
```

### 2. Performance Issues
- Check available resources
- Monitor container resource usage
- Review network connectivity
- Upgrade Portainer version

### 3. Stack Deploy Failures
- Verify docker-compose syntax
- Check image availability
- Verify network configuration
- Review service dependencies

## APIs y Automation

### 1. Portainer API
```bash
# Autenticar
curl -X POST http://localhost:9001/api/auth \
  -H "Content-Type: application/json" \
  -d '{"Username":"admin","Password":"adminpassword"}'

# Listar contenedores
curl -H "Authorization: Bearer <jwt-token>" \
  http://localhost:9001/api/endpoints/1/docker/containers/json
```

### 2. Webhook Integration
```bash
# Deploy stack via webhook
curl -X POST http://localhost:9001/api/webhooks/<webhook-id> \
  -H "Content-Type: application/json"
```

## Alternativas y Comparación

### 1. Portainer vs otros tools
| Feature | Portainer | Rancher | Docker Desktop |
|---------|-----------|---------|----------------|
| Web UI | ✅ | ✅ | ✅ |
| Multi-cluster | ✅ | ✅ | ❌ |
| Kubernetes | ✅ | ✅ | ✅ |
| Free tier | ✅ | ✅ | ✅ |
| Enterprise features | 💰 | 💰 | 💰 |

### 2. Cuándo usar Portainer
- **DO**: Gestión visual de Docker
- **DO**: Equipos mixtos (CLI + GUI users)
- **DO**: Múltiples entornos Docker
- **CONSIDER**: Kubernetes management
- **DON'T**: High-scale production only

## Recursos Adicionales

- [Portainer Documentation](https://documentation.portainer.io/)
- [Portainer API Reference](https://documentation.portainer.io/api/docs/)
- [Community Templates](https://github.com/portainer/templates)
- [Best Practices Guide](https://documentation.portainer.io/admin/best-practices/)

## Próximos Pasos

1. Configurar RBAC para diferentes equipos
2. Crear templates personalizados
3. Integrar con CI/CD pipelines
4. Explorar Portainer Business features
5. Configurar monitoring y alerting
