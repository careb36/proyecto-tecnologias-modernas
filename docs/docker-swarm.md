# Docker Swarm - Guía de Aprendizaje

## ¿Qué es Docker Swarm?

Docker Swarm es la herramienta nativa de orquestación de contenedores de Docker que permite crear y gestionar un cluster de nodos Docker para ejecutar aplicaciones distribuidas con alta disponibilidad.

## Conceptos Clave

### 1. Swarm
Cluster de nodos Docker que funcionan juntos como una unidad.

### 2. Node Types
- **Manager Node**: Coordina el cluster, toma decisiones, mantiene el estado
- **Worker Node**: Ejecuta tareas (contenedores) asignadas por los managers

### 3. Service
Definición de cómo debe ejecutarse una aplicación en el swarm:
- Imagen a usar
- Número de réplicas
- Recursos asignados
- Redes y volúmenes

### 4. Task
Instancia individual de un contenedor corriendo en un nodo específico.

### 5. Stack
Conjunto de servicios relacionados que se despliegan juntos (equivalente a docker-compose en swarm mode).

### 6. Overlay Network
Red que conecta servicios corriendo en diferentes nodos del swarm.

## Inicialización del Swarm

### 1. Inicializar Swarm Mode
```bash
# En el nodo que será manager
docker swarm init

# Si tienes múltiples IPs, especifica cuál usar
docker swarm init --advertise-addr 192.168.1.100
```

### 2. Unir Worker Nodes
```bash
# En el manager, obtener el comando join para workers
docker swarm join-token worker

# En cada worker node, ejecutar el comando mostrado
docker swarm join --token SWMTKN-1-... 192.168.1.100:2377
```

### 3. Agregar Manager Nodes (Alta Disponibilidad)
```bash
# Obtener token para managers
docker swarm join-token manager

# En el nuevo manager node
docker swarm join --token SWMTKN-1-... 192.168.1.100:2377
```

## Gestión del Cluster

### 1. Ver Estado del Swarm
```bash
# Listar nodos
docker node ls

# Información detallada de un nodo
docker node inspect <node-id>

# Ver información del swarm
docker system info
```

### 2. Gestión de Nodos
```bash
# Promover worker a manager
docker node promote <node-id>

# Degradar manager a worker
docker node demote <node-id>

# Drenar nodo (no asignar nuevas tareas)
docker node update --availability drain <node-id>

# Reactivar nodo
docker node update --availability active <node-id>

# Remover nodo del swarm
docker node rm <node-id>
```

## Servicios en Docker Swarm

### 1. Crear Servicios
```bash
# Servicio básico
docker service create --name web-server --replicas 3 -p 80:80 nginx

# Servicio con configuración avanzada
docker service create \
  --name java-app \
  --replicas 3 \
  --network tech-stack \
  --mount type=volume,source=app-data,target=/app/data \
  --constraint 'node.role==worker' \
  --reserve-memory 512m \
  --limit-memory 1g \
  --env DATABASE_URL=jdbc:oracle:thin:@oracle:1521:xe \
  --publish 8080:8080 \
  tech-stack-app:latest
```

### 2. Gestión de Servicios
```bash
# Listar servicios
docker service ls

# Información detallada
docker service inspect java-app

# Ver tareas del servicio
docker service ps java-app

# Logs del servicio
docker service logs java-app

# Escalar servicio
docker service scale java-app=5

# Actualizar servicio
docker service update --image tech-stack-app:v2 java-app

# Rolling update con configuración
docker service update \
  --update-parallelism 1 \
  --update-delay 30s \
  --update-failure-action rollback \
  --image tech-stack-app:v2 \
  java-app
```

## Configuración con Docker Compose

### 1. Stack Deployment
El archivo `docker-compose.swarm.yml` ya está configurado para swarm mode:

```bash
# Desplegar stack
docker stack deploy -c docker-compose.swarm.yml tech-stack

# Listar stacks
docker stack ls

# Listar servicios del stack
docker stack services tech-stack

# Ver tareas del stack
docker stack ps tech-stack

# Remover stack
docker stack rm tech-stack
```

### 2. Diferencias con Docker Compose Regular
```yaml
# Características específicas de Swarm mode
version: '3.8'
services:
  app:
    image: tech-stack-app:latest
    deploy:
      replicas: 3
      restart_policy:
        condition: on-failure
        delay: 5s
        max_attempts: 3
      update_config:
        parallelism: 1
        delay: 10s
        failure_action: rollback
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
        reservations:
          cpus: '0.25'
          memory: 256M
      placement:
        constraints:
          - node.role == worker
          - node.labels.environment == production
```

## Redes en Docker Swarm

### 1. Overlay Networks
```bash
# Crear red overlay
docker network create --driver overlay --attachable tech-stack-net

# Listar redes
docker network ls

# Inspeccionar red
docker network inspect tech-stack-net
```

### 2. Service Discovery
Los servicios se pueden comunicar por nombre:
```yaml
services:
  app:
    # Se puede conectar a 'oracle' directamente
    environment:
      - DATABASE_URL=jdbc:oracle:thin:@oracle:1521:xe
  
  oracle:
    # Automáticamente disponible como 'oracle' en la red
```

## Secrets y Configs

### 1. Docker Secrets (Información Sensible)
```bash
# Crear secret desde archivo
echo "mi-password-super-secreto" | docker secret create db_password -

# Crear secret desde stdin
docker secret create ssl_cert ssl-certificate.pem

# Listar secrets
docker secret ls

# Usar secret en servicio
docker service create \
  --name db \
  --secret db_password \
  --env DATABASE_PASSWORD_FILE=/run/secrets/db_password \
  oracle/database:19.3.0-ee
```

### 2. Docker Configs (Archivos de Configuración)
```bash
# Crear config desde archivo
docker config create nginx_config nginx.conf

# Listar configs
docker config ls

# Usar config en servicio
docker service create \
  --name web \
  --config source=nginx_config,target=/etc/nginx/nginx.conf \
  nginx:alpine
```

### 3. En Docker Compose Stack
```yaml
version: '3.8'
services:
  app:
    image: tech-stack-app:latest
    secrets:
      - db_password
      - api_key
    configs:
      - source: app_config
        target: /app/application.properties
    environment:
      - DATABASE_PASSWORD_FILE=/run/secrets/db_password

secrets:
  db_password:
    external: true
  api_key:
    file: ./secrets/api_key.txt

configs:
  app_config:
    file: ./config/application.properties
```

## Load Balancing y Routing

### 1. Routing Mesh
Docker Swarm incluye load balancing automático:
- Requests a cualquier nodo del swarm se enrutan automáticamente
- Round-robin entre réplicas del servicio
- Health checks automáticos

### 2. Modos de Publicación
```bash
# Ingress mode (default) - load balancing automático
docker service create --publish 8080:8080 --name web nginx

# Host mode - puerto específico del nodo
docker service create --publish mode=host,target=80,published=8080 --name web nginx
```

## Ejercicios Prácticos

### Ejercicio 1: Cluster Básico (Simulado)
```bash
# 1. Inicializar swarm
docker swarm init

# 2. Verificar estado
docker node ls
docker system info | grep Swarm

# 3. Crear servicio simple
docker service create --name test-nginx --replicas 2 -p 8080:80 nginx

# 4. Verificar servicio
docker service ls
docker service ps test-nginx

# 5. Acceder al servicio
curl http://localhost:8080
```

### Ejercicio 2: Desplegar Stack Completo
```bash
# 1. Crear secrets necesarios
echo "oracle123" | docker secret create db_password -
echo "admin123" | docker secret create grafana_password -
echo "oraclePass" | docker secret create oracle_password -

# 2. Desplegar stack
docker stack deploy -c docker-compose.swarm.yml tech-stack

# 3. Verificar servicios
docker stack services tech-stack
docker stack ps tech-stack

# 4. Monitorear despliegue
watch docker service ls
```

### Ejercicio 3: Scaling y Updates
```bash
# 1. Escalar aplicación
docker service scale tech-stack_app=5

# 2. Verificar distribución
docker service ps tech-stack_app

# 3. Simular actualización
docker service update --image tech-stack-app:v2 tech-stack_app

# 4. Monitorear rolling update
watch docker service ps tech-stack_app
```

### Ejercicio 4: Gestión de Fallos
```bash
# 1. Simular fallo de contenedor
docker kill <container-id>

# 2. Observar recuperación automática
docker service ps tech-stack_app

# 3. Drenar nodo (simulate node failure)
docker node update --availability drain <node-id>

# 4. Observar redistribución de servicios
docker service ps tech-stack_app
```

## Monitoring y Logging

### 1. Health Checks
```yaml
version: '3.8'
services:
  app:
    image: tech-stack-app:latest
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s
```

### 2. Service Logs
```bash
# Logs de todos los contenedores del servicio
docker service logs -f tech-stack_app

# Logs con timestamps
docker service logs -t tech-stack_app

# Seguir logs en tiempo real
docker service logs --tail=100 -f tech-stack_app
```

### 3. Métricas del Cluster
```bash
# Estado general
docker system df
docker system events

# Por servicio
docker service inspect tech-stack_app --pretty

# Por nodo
docker node inspect <node-id> --pretty
```

## Alta Disponibilidad

### 1. Manager Nodes
- Mínimo 3 managers para tolerancia a fallos
- Siempre número impar (3, 5, 7)
- Distribución geográfica recomendada

### 2. Persistent Storage
```yaml
services:
  database:
    image: oracle/database:19.3.0-ee
    volumes:
      - oracle_data:/opt/oracle/oradata
    deploy:
      replicas: 1
      placement:
        constraints:
          - node.hostname == db-server-1
```

### 3. Backup Strategy
```bash
# Backup automático de Docker Swarm state
docker run --rm -v /var/lib/docker/swarm:/backup alpine tar czf /backup/swarm-backup.tar.gz .
```

## Troubleshooting Común

### 1. Network Issues
```bash
# Verificar conectividad entre nodos
docker node ls

# Verificar puertos necesarios (TCP 2377, 7946; UDP 7946, 4789)
telnet <manager-ip> 2377

# Limpiar redes no utilizadas
docker network prune
```

### 2. Service Deployment Issues
```bash
# Ver eventos del servicio
docker service ps <service-name> --no-trunc

# Logs detallados
docker service logs <service-name>

# Verificar constraints
docker service inspect <service-name> | grep -A 10 Placement
```

### 3. Performance Issues
```bash
# Ver resource usage
docker stats

# Verificar limits del servicio
docker service inspect <service-name> | grep -A 5 Resources
```

## Seguridad en Swarm

### 1. TLS Encryption
- Todo el tráfico entre nodos está encriptado
- Rotación automática de certificados
- Mutual TLS authentication

### 2. Secrets Management
```bash
# Rotar secret
echo "nuevo-password" | docker secret create db_password_v2 -
docker service update --secret-rm db_password --secret-add db_password_v2 myapp
docker secret rm db_password
```

### 3. Network Segmentation
```yaml
# Diferentes redes para diferentes tiers
networks:
  frontend:
    driver: overlay
  backend:
    driver: overlay
  database:
    driver: overlay
    internal: true  # No external access
```

## Comparación: Swarm vs Kubernetes

| Feature | Docker Swarm | Kubernetes |
|---------|--------------|------------|
| **Complexity** | Simple | Complex |
| **Learning Curve** | Easy | Steep |
| **Ecosystem** | Limited | Extensive |
| **Scalability** | Good | Excellent |
| **Features** | Basic | Advanced |
| **Community** | Smaller | Larger |
| **Enterprise Support** | Limited | Extensive |

### Cuándo usar Docker Swarm
- **DO**: Equipos pequeños, aplicaciones simples
- **DO**: Quick setup y deployment
- **DO**: Si ya usas Docker extensivamente
- **CONSIDER**: Kubernetes para aplicaciones complejas
- **DON'T**: Si necesitas features avanzadas de K8s

## Recursos Adicionales

- [Docker Swarm Documentation](https://docs.docker.com/engine/swarm/)
- [Swarm Mode Tutorial](https://docs.docker.com/engine/swarm/swarm-tutorial/)
- [Production Guide](https://docs.docker.com/engine/swarm/admin_guide/)
- [Best Practices](https://docs.docker.com/engine/swarm/how-swarm-mode-works/)

## Próximos Pasos

1. Practicar con cluster multi-nodo
2. Implementar estrategias de backup
3. Configurar monitoring avanzado
4. Explorar integración con CI/CD
5. Evaluar migración a Kubernetes cuando sea necesario
