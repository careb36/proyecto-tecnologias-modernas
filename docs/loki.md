# Loki - Guía de Aprendizaje

## ¿Qué es Loki?

Loki es un sistema de agregación de logs diseñado para ser altamente eficiente y escalable. A diferencia de otras soluciones como Elasticsearch, Loki no indexa el contenido de los logs, sino solo las etiquetas (labels), lo que lo hace más económico y rápido.

## Conceptos Clave

### 1. Streams
Conjunto único de logs identificado por etiquetas (labels). Cada combinación única de labels crea un stream diferente.

### 2. Labels
Metadatos clave-valor que identifican y clasifican los logs:
```
{job="java-app", instance="localhost:8080", level="ERROR"}
```

### 3. Log Entry
Entrada individual de log con timestamp y mensaje.

### 4. LogQL
Lenguaje de consulta de Loki, similar a PromQL de Prometheus.

### 5. Promtail
Agente que recolecta logs y los envía a Loki.

## Arquitectura del Sistema

```
Aplicación → Logs → Promtail → Loki → Grafana
```

1. **Aplicación**: Genera logs en archivos o stdout
2. **Promtail**: Recolecta y envía logs a Loki
3. **Loki**: Almacena y indexa logs por labels
4. **Grafana**: Visualiza y consulta logs

## Configuración en el Proyecto

### 1. Loki Server
Configuración en `monitoring/loki/loki.yml`:

```yaml
auth_enabled: false

server:
  http_listen_port: 3100

ingester:
  lifecycler:
    ring:
      kvstore:
        store: inmemory
      replication_factor: 1
  chunk_idle_period: 1h
  max_chunk_age: 1h

schema_config:
  configs:
    - from: 2020-10-24
      store: boltdb-shipper
      object_store: filesystem
      schema: v11
      index:
        prefix: index_
        period: 24h
```

### 2. Promtail Agent
Configuración en `monitoring/promtail/promtail.yml`:

```yaml
server:
  http_listen_port: 9080

clients:
  - url: http://loki:3100/loki/api/v1/push

scrape_configs:
  - job_name: java-app
    static_configs:
      - targets:
          - localhost
        labels:
          job: java-app
          __path__: /app/logs/*.log
```

## LogQL - Lenguaje de Consulta

### 1. Sintaxis Básica
```logql
{label_name="label_value"}
```

### 2. Selectores de Streams
```logql
# Todos los logs de la aplicación Java
{job="java-app"}

# Logs de un nivel específico
{job="java-app", level="ERROR"}

# Múltiples jobs
{job=~"java-app|nginx"}
```

### 3. Filtros de Línea
```logql
# Contiene texto específico (case sensitive)
{job="java-app"} |= "database"

# No contiene texto
{job="java-app"} != "debug"

# Expresión regular
{job="java-app"} |~ "(?i)error|exception"

# Expresión regular negativa
{job="java-app"} !~ "debug|trace"
```

### 4. Parsers
```logql
# JSON parser
{job="java-app"} | json

# Logfmt parser
{job="java-app"} | logfmt

# Regex parser
{job="java-app"} | regexp "(?P<timestamp>\\S+) (?P<level>\\S+)"

# Pattern parser
{job="java-app"} | pattern "<timestamp> <level> <message>"
```

### 5. Filtros de Labels
```logql
# Después de parsear JSON, filtrar por nivel
{job="java-app"} | json | level="ERROR"

# Filtro numérico
{job="java-app"} | json | duration > 1000
```

### 6. Formatters
```logql
# Line format
{job="java-app"} | line_format "{{.timestamp}} - {{.level}} - {{.message}}"

# Label format
{job="java-app"} | label_format level=`{{ .level | upper }}`
```

## Funciones de Agregación

### 1. Rate Functions
```logql
# Rate de logs por segundo
rate({job="java-app"}[5m])

# Rate de errores
rate({job="java-app"} |= "ERROR" [5m])
```

### 2. Count Functions
```logql
# Conteo de logs en ventana de tiempo
count_over_time({job="java-app"}[5m])

# Conteo de errores
count_over_time({job="java-app"} |= "ERROR" [5m])
```

### 3. Bytes Functions
```logql
# Bytes procesados por segundo
bytes_rate({job="java-app"}[5m])

# Bytes totales en ventana de tiempo
bytes_over_time({job="java-app"}[5m])
```

### 4. Aggregation Operations
```logql
# Sum por job
sum(rate({job=~"java-app|nginx"}[5m])) by (job)

# Count por nivel
sum(count_over_time({job="java-app"} | json [5m])) by (level)

# Top K
topk(10, sum(rate({job="java-app"}[5m])) by (instance))
```

## Ejercicios Prácticos

### Ejercicio 1: Queries Básicas
Accede a Grafana → Explore → Loki:

```logql
# 1. Ver todos los logs de la aplicación
{job="java-app"}

# 2. Solo logs de ERROR
{job="java-app"} |= "ERROR"

# 3. Logs que contienen "database" (ignore case)
{job="java-app"} |~ "(?i)database"

# 4. Excluir logs de DEBUG
{job="java-app"} != "DEBUG"
```

### Ejercicio 2: Parsing y Filtrado
```logql
# 1. Parsear logs JSON y filtrar por nivel
{job="java-app"} | json | level="INFO"

# 2. Formatear salida
{job="java-app"} | json | line_format "{{.timestamp}} [{{.level}}] {{.message}}"

# 3. Crear nueva label
{job="java-app"} | json | label_format severity=`{{ if eq .level "ERROR" }}high{{ else }}low{{ end }}`
```

### Ejercicio 3: Métricas de Logs
```logql
# 1. Rate de logs por segundo
rate({job="java-app"}[5m])

# 2. Conteo de errores por minuto
sum(count_over_time({job="java-app"} |= "ERROR" [1m]))

# 3. Top 5 tipos de logs más frecuentes
topk(5, sum(count_over_time({job="java-app"} | json [5m])) by (level))
```

### Ejercicio 4: Dashboard de Logs
1. Crear nuevo dashboard en Grafana
2. Agregar panel tipo "Logs"
3. Configurar query: `{job="java-app"}`
4. Agregar panel "Time series" con rate de errores
5. Configurar variables para filtrar por nivel

## Configuración de Logging en Java

### 1. Logback Configuration
Crear `src/main/resources/logback-spring.xml`:

```xml
<configuration>
    <!-- Console Appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp/>
                <logLevel/>
                <loggerName/>
                <mdc/>
                <arguments/>
                <message/>
            </providers>
        </encoder>
    </appender>

    <!-- File Appender -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/application.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>logs/application.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxFileSize>10MB</maxFileSize>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp/>
                <logLevel/>
                <loggerName/>
                <mdc/>
                <arguments/>
                <message/>
            </providers>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

### 2. Structured Logging en Java
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Service
public class UsuarioService {
    
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    
    public Usuario crearUsuario(Usuario usuario) {
        MDC.put("operation", "create_user");
        MDC.put("user_email", usuario.getEmail());
        
        logger.info("Creating new user: {}", usuario.getEmail());
        
        try {
            // Lógica de creación
            Usuario saved = usuarioRepository.save(usuario);
            
            logger.info("User created successfully with ID: {}", saved.getId());
            MDC.put("user_id", saved.getId().toString());
            
            return saved;
        } catch (Exception e) {
            logger.error("Failed to create user: {}", e.getMessage(), e);
            throw e;
        } finally {
            MDC.clear();
        }
    }
}
```

## Mejores Prácticas

### 1. Labels Strategy
- **DO**: Usar pocos labels con baja cardinalidad
  ```logql
  {job="java-app", level="ERROR"}
  ```
- **DON'T**: Usar muchos labels o con alta cardinalidad
  ```logql
  {job="java-app", user_id="12345", request_id="abc-def"}
  ```

### 2. Log Format
- Usar formato estructurado (JSON)
- Incluir timestamp, level, mensaje
- Agregar contexto relevante
- Evitar información sensible

### 3. Retention
- Configurar retención apropiada
- Comprimir logs antiguos
- Archivar logs críticos por más tiempo

### 4. Performance
- Evitar queries muy amplias
- Usar time ranges específicos
- Filtrar por labels primero, luego por contenido

## Alerting en Grafana

### 1. Crear Alert Rule
```logql
# Query para rate de errores
sum(rate({job="java-app"} |= "ERROR" [5m]))
```

### 2. Configuración de Alerta
- Condition: `IS ABOVE 0.1` (0.1 errores por segundo)
- Evaluation: Every `1m` for `5m`
- Notification: Email, Slack, etc.

### 3. Alert con Context
```logql
# Incluir contexto en la alerta
{job="java-app"} |= "ERROR" | json | line_format "Error: {{.message}} in {{.logger}} at {{.timestamp}}"
```

## Troubleshooting

### 1. No Logs Appearing
```bash
# Verificar que Promtail esté enviando logs
curl http://localhost:9080/metrics

# Verificar conectividad con Loki
curl http://localhost:3100/ready

# Verificar configuración Promtail
docker logs promtail
```

### 2. High Cardinality Issues
```bash
# Verificar número de streams
curl -s http://localhost:3100/loki/api/v1/label | jq

# Ver streams activos
curl -s "http://localhost:3100/loki/api/v1/query_range?query={job=\"java-app\"}" | jq
```

### 3. Query Performance
- Usar filtros de tiempo específicos
- Filtrar por labels primero
- Evitar regex complejas
- Usar parsers eficientemente

## Integración con Alertmanager

### 1. Configurar Ruler en Loki
```yaml
ruler:
  storage:
    type: local
    local:
      directory: /loki/rules
  rule_path: /loki/rules-temp
  alertmanager_url: http://alertmanager:9093
  ring:
    kvstore:
      store: inmemory
  enable_api: true
```

### 2. Crear Reglas de Alerta
```yaml
groups:
  - name: loki-rules
    rules:
      - alert: HighErrorRate
        expr: sum(rate({job="java-app"} |= "ERROR" [5m])) > 0.1
        for: 5m
        labels:
          severity: warning
        annotations:
          summary: "High error rate detected"
          description: "Error rate is {{ $value }} errors per second"
```

## Recursos Adicionales

- [Loki Documentation](https://grafana.com/docs/loki/)
- [LogQL Documentation](https://grafana.com/docs/loki/latest/logql/)
- [Promtail Configuration](https://grafana.com/docs/loki/latest/clients/promtail/configuration/)
- [Best Practices](https://grafana.com/docs/loki/latest/best-practices/)

## Próximos Pasos

1. Implementar structured logging en la aplicación
2. Configurar alertas basadas en logs
3. Optimizar queries LogQL
4. Explorar integración con tracing
5. Implementar log aggregation strategies
