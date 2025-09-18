# Grafana - Guía de Aprendizaje

## ¿Qué es Grafana?

Grafana es una plataforma de observabilidad que permite crear dashboards interactivos para visualizar métricas, logs y trazas de diferentes fuentes de datos.

## Conceptos Clave

### 1. Dashboard
Panel principal que contiene múltiples visualizaciones organizadas en paneles.

### 2. Panel
Visualización individual (gráfico, tabla, stat, etc.) dentro de un dashboard.

### 3. Data Source
Fuente de datos (Prometheus, Loki, Oracle, etc.) de donde Grafana obtiene información.

### 4. Query
Consulta específica para obtener datos de una fuente.

### 5. Alert
Notificación automática basada en condiciones de métricas.

## Configuración Inicial

### 1. Acceso
- URL: http://localhost:3000
- Usuario por defecto: `admin`
- Contraseña por defecto: `admin`

### 2. Configuración de Data Sources
Los data sources están preconfigurados via provisioning:
- **Prometheus**: http://prometheus:9090
- **Loki**: http://loki:3100
- **Oracle**: oracle:1521

## Tipos de Visualizaciones

### 1. Time Series (Gráficos de Línea)
Ideal para métricas que cambian en el tiempo.

```promql
# Ejemplo: Uso de memoria JVM
jvm_memory_used_bytes{job="java-app"}
```

### 2. Stat
Muestra un valor único con estado (UP/DOWN, porcentaje, etc.).

```promql
# Ejemplo: Estado de la aplicación
up{job="java-app"}
```

### 3. Gauge
Medidor circular para mostrar valores dentro de un rango.

```promql
# Ejemplo: Uso de CPU
100 - (avg(rate(cpu_idle_time_total[5m])) * 100)
```

### 4. Bar Chart
Gráfico de barras para comparar valores categóricos.

### 5. Heatmap
Mapa de calor para mostrar distribución de valores.

### 6. Logs
Panel específico para mostrar logs de Loki.

```logql
# Ejemplo: Logs de la aplicación Java
{job="java-app"} |= "ERROR"
```

## Queries Útiles

### Prometheus Queries (PromQL)

#### Métricas de Aplicación
```promql
# Requests por segundo
rate(http_requests_total{job="java-app"}[5m])

# Tiempo de respuesta promedio
rate(http_request_duration_seconds_sum{job="java-app"}[5m]) /
rate(http_request_duration_seconds_count{job="java-app"}[5m])

# Uso de memoria heap
jvm_memory_used_bytes{job="java-app", area="heap"}

# Threads JVM
jvm_threads_current{job="java-app"}

# Garbage Collection
rate(jvm_gc_collection_seconds_count{job="java-app"}[5m])
```

#### Métricas de Sistema
```promql
# Uso de CPU
100 - (avg(rate(node_cpu_seconds_total{mode="idle"}[5m])) * 100)

# Uso de memoria
(1 - (node_memory_MemAvailable_bytes / node_memory_MemTotal_bytes)) * 100

# Uso de disco
100 - ((node_filesystem_avail_bytes * 100) / node_filesystem_size_bytes)

# Load average
node_load1
```

### Loki Queries (LogQL)

#### Filtros Básicos
```logql
# Todos los logs de la aplicación
{job="java-app"}

# Solo logs de ERROR
{job="java-app"} |= "ERROR"

# Logs que contienen "database"
{job="java-app"} |~ "(?i)database"

# Logs de un nivel específico
{job="java-app"} | json | level="ERROR"
```

#### Agregaciones
```logql
# Conteo de logs por nivel
sum by (level) (count_over_time({job="java-app"} | json [5m]))

# Rate de errores
sum(rate({job="java-app"} |= "ERROR" [5m]))
```

## Dashboards Incluidos

### Tech Stack Overview
Dashboard principal que incluye:
- Estado de la aplicación
- Métricas JVM (memoria, threads, GC)
- Request rate y latencia
- Logs recientes
- Métricas de sistema

### Configuración del Dashboard
```json
{
  "dashboard": {
    "title": "Tech Stack Overview",
    "panels": [
      {
        "title": "Application Health",
        "type": "stat",
        "targets": [
          {
            "expr": "up{job=\"java-app\"}"
          }
        ]
      }
    ]
  }
}
```

## Ejercicios Prácticos

### Ejercicio 1: Explorar Dashboards Existentes
1. Accede a Grafana (http://localhost:3000)
2. Ve a Dashboards → Browse
3. Abre "Tech Stack Overview"
4. Explora los diferentes paneles
5. Cambia el rango de tiempo

### Ejercicio 2: Crear Panel Básico
1. Crea un nuevo dashboard
2. Agrega un panel tipo "Time series"
3. Configura query: `jvm_memory_used_bytes{job="java-app"}`
4. Personaliza título y unidades
5. Guarda el dashboard

### Ejercicio 3: Panel de Logs
1. Agrega un panel tipo "Logs"
2. Selecciona data source "Loki"
3. Query: `{job="java-app"} |= "INFO"`
4. Configura visualización de logs

### Ejercicio 4: Alertas Básicas
1. Crea una alerta en un panel existente
2. Configura condición: JVM memory > 80%
3. Configura notification channel
4. Prueba la alerta

## Variables de Dashboard

### 1. Query Variables
```
Name: instance
Type: Query
Data source: Prometheus
Query: label_values(up{job="java-app"}, instance)
```

### 2. Custom Variables
```
Name: environment
Type: Custom
Values: dev,staging,prod
```

### Uso en Queries
```promql
up{job="java-app", instance="$instance"}
```

## Alerting

### 1. Configuración de Notification Channels
```yaml
# Email
Type: Email
Settings:
  Addresses: admin@ejemplo.com
  Subject: Grafana Alert - {{.RuleName}}
```

### 2. Alert Rules
```
Name: High Memory Usage
Condition:
  Query: jvm_memory_used_bytes{job="java-app"} / jvm_memory_max_bytes{job="java-app"} * 100
  IS ABOVE: 80
  FOR: 5m
```

## Provisioning

### Datasources (datasources.yml)
```yaml
apiVersion: 1
datasources:
  - name: Prometheus
    type: prometheus
    url: http://prometheus:9090
    isDefault: true
```

### Dashboards (dashboards.yml)
```yaml
apiVersion: 1
providers:
  - name: 'default'
    folder: ''
    type: file
    path: /var/lib/grafana/dashboards
```

## Mejores Prácticas

### 1. Organización
- Usa folders para agrupar dashboards
- Nomenclatura consistente
- Tags descriptivos
- Documentación en paneles

### 2. Performance
- Evita queries muy complejas
- Usa intervalos apropiados
- Limita número de series
- Cache queries cuando sea posible

### 3. Visualización
- Colores consistentes
- Unidades apropiadas
- Títulos descriptivos
- Thresholds significativos

### 4. Alerting
- Evita alert fatigue
- Umbrales realistas
- Canales de notificación apropiados
- Documentar playbooks

## Troubleshooting Común

### Data Source Connection Issues
1. Verificar URLs y puertos
2. Comprobar conectividad de red
3. Revisar logs de Grafana
4. Validar credenciales

### Query Performance Issues
```promql
# Evitar esto (muy amplio)
up

# Mejor (específico)
up{job="java-app"}
```

### No Data in Panels
1. Verificar rango de tiempo
2. Comprobar que el servicio esté enviando métricas
3. Validar query syntax
4. Revisar data source configuration

## Plugins Útiles

### 1. Pie Chart Panel
```bash
grafana-cli plugins install grafana-piechart-panel
```

### 2. Worldmap Panel
```bash
grafana-cli plugins install grafana-worldmap-panel
```

### 3. Clock Panel
```bash
grafana-cli plugins install grafana-clock-panel
```

## Exportar/Importar Dashboards

### Exportar
1. Dashboard → Settings → JSON Model
2. Copiar JSON
3. Guardar en archivo .json

### Importar
1. Dashboard → Import
2. Pegar JSON o subir archivo
3. Configurar data sources
4. Importar

## 📚 Recursos de Aprendizaje y Referencias

### Documentación Oficial

#### Grafana Core
- **📖 [Grafana Documentation](https://grafana.com/docs/)** - Documentación completa oficial
- **🎓 [Grafana University](https://university.grafana.com/)** - Cursos gratuitos oficiales
- **📖 [Grafana Tutorials](https://grafana.com/tutorials/)** - Tutoriales paso a paso
- **🎯 [Grafana Play](https://play.grafana.com/)** - Entorno de práctica online

#### Lenguajes de Consulta
- **📖 [PromQL Documentation](https://prometheus.io/docs/prometheus/latest/querying/basics/)** - Lenguaje de Prometheus
- **📖 [LogQL Documentation](https://grafana.com/docs/loki/latest/logql/)** - Lenguaje de Loki
- **📖 [Flux Documentation](https://docs.influxdata.com/flux/)** - Lenguaje de InfluxDB

### Libros y Cursos

#### Libros Recomendados
- **"Monitoring with Prometheus" - James Turnbull**
  - **📚 [Comprar](https://www.amazon.com/Monitoring-Prometheus-Second-James-Turnbull/dp/1492034142)**
  - **Contenido**: Prometheus, métricas, alertas

- **"The Art of Monitoring" - James Turnbull**
  - **📚 [Versión Digital](https://www.oreilly.com/library/view/the-art-of/9781491952596/)**
  - **Contenido**: Filosofía del monitoreo, mejores prácticas

#### Cursos Online
- **Grafana Fundamentals** (Udemy)
- **Prometheus Monitoring** (Coursera)
- **Observability with Grafana** (Pluralsight)
- **Site Reliability Engineering** (LinkedIn Learning)

### Comunidades y Blogs

#### Comunidades
- **Grafana Community**: [Foros oficiales](https://community.grafana.com/)
- **Prometheus Users**: [Comunidad Prometheus](https://prometheus.io/community/)
- **Reddit**: r/grafana, r/prometheus, r/monitoring
- **Stack Overflow**: Etiquetas `grafana`, `prometheus`, `promql`

#### Blogs Técnicos
- **Grafana Blog**: [Actualizaciones y tutoriales](https://grafana.com/blog/)
- **Prometheus Blog**: [Blog oficial](https://prometheus.io/blog/)
- **Monitoring Weekly**: [Newsletter semanal](https://monitoringweekly.com/)

### Herramientas Complementarias

#### Alternativas a Grafana
- **Kibana**: [Visualización para Elasticsearch](https://www.elastic.co/kibana)
- **Datadog**: [Plataforma SaaS completa](https://www.datadoghq.com/)
- **New Relic**: [APM y monitoreo](https://newrelic.com/)
- **Dynatrace**: [Monitoreo inteligente](https://www.dynatrace.com/)

#### Herramientas de Métricas
- **Micrometer**: [Métricas para JVM](https://micrometer.io/)
- **OpenTelemetry**: [Estándar de observabilidad](https://opentelemetry.io/)
- **Jaeger**: [Tracing distribuido](https://www.jaegertracing.io/)
- **Zipkin**: [Tracing alternativo](https://zipkin.io/)

## 🎯 Rutas de Aprendizaje

### Nivel Principiante (1-2 semanas)
1. **Instalación y configuración** básica
2. **Primeros dashboards** simples
3. **Queries básicas** en PromQL
4. **Visualizaciones** fundamentales

### Nivel Intermedio (2-4 semanas)
1. **Queries avanzadas** y funciones
2. **Alerting** y notificaciones
3. **Dashboards complejos** con variables
4. **Log aggregation** con Loki

### Nivel Avanzado (4-6 semanas)
1. **Arquitectura de observabilidad**
2. **Service mesh** monitoring
3. **Machine learning** en métricas
4. **Custom plugins** y extensiones

## 🏆 Mejores Prácticas Implementadas

### Dashboard Design
- ✅ **Organización lógica** por equipos/servicios
- ✅ **Nomenclatura consistente** de paneles
- ✅ **Colores apropiados** para diferentes estados
- ✅ **Documentación** en paneles complejos

### Queries y Performance
- ✅ **Queries optimizadas** para rendimiento
- ✅ **Cache apropiado** de datos
- ✅ **Intervalos** de refresco óptimos
- ✅ **Límites de datos** para evitar sobrecarga

### Alerting
- ✅ **Umbrales realistas** basados en datos históricos
- ✅ **Canales apropiados** de notificación
- ✅ **Evitar alert fatigue** con agrupación
- ✅ **Runbooks** documentados

## 🔧 Configuración Avanzada

### Variables Dinámicas
```json
{
  "name": "service",
  "type": "query",
  "query": "label_values(up{job=\"$job\"}, service)",
  "refresh": 1
}
```

### Transformations
```json
{
  "transformations": [
    {
      "id": "organize",
      "options": {
        "excludeByName": {
          "__name__": true
        }
      }
    }
  ]
}
```

### Custom Plugins
```bash
# Instalar plugin
grafana-cli plugins install grafana-piechart-panel

# Reiniciar Grafana
sudo systemctl restart grafana-server
```

## 🚀 Próximos Pasos

### Inmediatos
1. **Implementar tracing** distribuido
2. **Agregar machine learning** para anomalías
3. **Configurar SLOs/SLIs** automáticos
4. **Integrar con service mesh**

### A Largo Plazo
1. **AI-powered monitoring**
2. **Predictive analytics**
3. **Automated remediation**
4. **Multi-cloud observability**

## 📊 Métricas de Éxito

### Dashboard Usage
- **Adoption Rate**: Porcentaje de equipos usando dashboards
- **Query Performance**: Tiempo de respuesta de queries
- **Alert Effectiveness**: Ratio de alertas útiles vs ruido
- **MTTR**: Mean Time To Resolution

### System Performance
- **Data Ingestion**: Velocidad de procesamiento de métricas
- **Storage Efficiency**: Optimización de almacenamiento
- **Scalability**: Capacidad de manejar crecimiento
- **Reliability**: Uptime del sistema de monitoreo

## 🎓 Certificaciones Recomendadas

- **Grafana Certified Associate**
  - [📋 Información](https://grafana.com/certification/)
- **Prometheus Certified Associate**
  - [📋 Cloud Native Computing Foundation](https://www.cncf.io/certification/prometheus/)
- **Certified Kubernetes Administrator (CKA)**
  - [📋 CNCF](https://www.cncf.io/certification/cka/) - *Para aprendizaje avanzado*

## 📞 Comunidad y Soporte

- **💬 [Grafana Community](https://community.grafana.com/)** - Foros oficiales
- **🐙 [Grafana GitHub](https://github.com/grafana/grafana)** - Código fuente
- **📧 [Grafana Support](https://grafana.com/support/)** - Soporte oficial
- **🎯 [Grafana Meetups](https://www.meetup.com/cities)** - Eventos locales

## 🔄 Comparación con Alternativas

| Característica | Grafana | Kibana | Datadog | New Relic |
|----------------|---------|--------|---------|-----------|
| **Open Source** | ✅ Gratuito | ✅ Gratuito | ❌ SaaS | ❌ SaaS |
| **Flexibilidad** | ✅ Alta | ⚠️ Limitada | ⚠️ Limitada | ⚠️ Limitada |
| **Ecosystem** | ✅ Extenso | ⚠️ Elasticsearch | ⚠️ Propio | ⚠️ Propio |
| **Learning Curve** | ⚠️ Moderada | ✅ Baja | ✅ Baja | ✅ Baja |
| **Enterprise** | ✅ Soporte | ✅ Soporte | ✅ Enterprise | ✅ Enterprise |
| **Cost** | ✅ Bajo | ✅ Bajo | ❌ Alto | ❌ Alto |

## 📋 Checklist de Implementación

### Configuración Básica
- [x] Data sources configurados
- [x] Primeros dashboards creados
- [x] Alerting básico implementado
- [x] Usuarios y permisos configurados

### Configuración Avanzada
- [x] Variables dinámicas
- [x] Transformations complejas
- [ ] Custom plugins
- [ ] API integrations
- [ ] Machine learning

### Operaciones
- [x] Backup de dashboards
- [x] Monitoreo del sistema
- [ ] High availability
- [ ] Disaster recovery
- [ ] Performance optimization

---

*Grafana ha establecido el estándar para visualización de métricas en el mundo cloud-native, combinando facilidad de uso con potencia excepcional.*

## Próximos Pasos

1. Familiarizarse con PromQL y LogQL
2. Crear dashboards personalizados
3. Configurar alerting efectivo
4. Explorar plugins avanzados
5. Implementar dashboards para diferentes equipos
