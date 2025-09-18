# JMeter - Pruebas de Carga y Performance

## ¿Qué es JMeter?

Apache JMeter es una herramienta gratuita y open source diseñada para probar el rendimiento de aplicaciones web. Es especialmente útil para:

- **Pruebas de carga**: Simular múltiples usuarios accediendo simultáneamente
- **Pruebas de stress**: Encontrar los límites de capacidad del sistema
- **Pruebas de rendimiento**: Medir tiempos de respuesta y throughput

## ¿Por qué es importante?

En el mundo real, las aplicaciones deben manejar cientos o miles de usuarios simultáneos. JMeter nos ayuda a:

1. **Predecir comportamiento**: Antes de que la aplicación esté en producción
2. **Identificar cuellos de botella**: Dónde está el problema de rendimiento
3. **Planificar escalabilidad**: Cuántos servidores necesitamos
4. **Garantizar calidad**: Asegurar que cumple con los requisitos de performance

## Instalación

### Opción 1: Descarga directa
1. Ve a https://jmeter.apache.org/download_jmeter.cgi
2. Descarga la versión binaria (apache-jmeter-X.X.X.zip)
3. Descomprime el archivo
4. Ejecuta `jmeter.bat` (Windows) o `jmeter` (Linux/Mac)

### Opción 2: Usando Docker (Recomendado)
```bash
# Ejecutar JMeter en contenedor
docker run --rm -v ${PWD}:/jmeter -w /jmeter justb4/jmeter:latest -n -t test-plan.jmx -l results.jtl

# O usar la imagen oficial
docker run --rm -v ${PWD}:/jmeter -w /jmeter apache/jmeter:latest -n -t test-plan.jmx -l results.jtl
```

### Verificar instalación
```bash
# Ver versión de JMeter
jmeter -v
```

## Conceptos Básicos

### 1. Test Plan
Es el contenedor principal de todas las pruebas. Como un "proyecto" de JMeter.

### 2. Thread Group
Representa un grupo de usuarios virtuales que ejecutan las mismas acciones.

### 3. Samplers
Son las solicitudes HTTP que JMeter enviará a tu aplicación.

### 4. Listeners
Componentes que recolectan y muestran los resultados de las pruebas.

### 5. Assertions
Verificaciones que determinan si una respuesta es correcta.

## Tu Primera Prueba de Carga

### Paso 1: Crear un Test Plan básico

1. **Abrir JMeter**
   - Ejecuta `jmeter.bat` o `jmeter`
   - Se abre la interfaz gráfica

2. **Crear Test Plan**
   - Click derecho en "Test Plan" → Add → Threads (Users) → Thread Group
   - Configurar:
     - **Number of Threads**: 10 (usuarios simultáneos)
     - **Ramp-up period**: 10 (segundos para que inicien todos)
     - **Loop Count**: 5 (veces que se repite cada usuario)

3. **Agregar HTTP Request**
   - Click derecho en Thread Group → Add → Sampler → HTTP Request
   - Configurar:
     - **Server Name**: localhost
     - **Port Number**: 8080
     - **HTTP Request**: GET
     - **Path**: /api/usuarios

4. **Agregar Listener para ver resultados**
   - Click derecho en Thread Group → Add → Listener → View Results Tree
   - También agrega: Summary Report

### Paso 2: Ejecutar la prueba

1. **Guardar el Test Plan**
   - File → Save as → `primer-test.jmx`

2. **Ejecutar**
   - Click en el botón "Play" (triángulo verde)
   - O Ctrl + R

3. **Ver resultados**
   - En "View Results Tree": Ver cada respuesta individual
   - En "Summary Report": Estadísticas generales

## Interpretar los Resultados

### Métricas importantes

#### Throughput (Throughput)
- **Qué mide**: Solicitudes por segundo que el sistema puede manejar
- **Ideal**: Lo más alto posible
- **Ejemplo**: 150.5/min (150.5 solicitudes por minuto)

#### Response Time (Tiempo de respuesta)
- **Average**: Tiempo promedio de respuesta
- **Median**: Tiempo medio (más confiable que average)
- **90% Line**: 90% de las respuestas son más rápidas que este tiempo
- **Ideal**: Lo más bajo posible

#### Error Rate (Tasa de error)
- **Porcentaje de respuestas con error**
- **Ideal**: 0% (o muy cercano a 0)

### Ejemplo de resultados
```
Label      Samples  Average  Median  90% Line  Min  Max  Error%  Throughput
GET /api/usuarios  50       245      234     312       123  456  0.00%    45.2/sec
```

## Tipos de Pruebas

### 1. Prueba de Carga Básica
```yaml
Thread Group:
  - Number of Threads: 50
  - Ramp-up period: 30
  - Loop Count: Infinite
  - Duration: 300 seconds
```

### 2. Prueba de Stress
```yaml
Thread Group:
  - Number of Threads: 10-200 (incremental)
  - Ramp-up period: 10
  - Loop Count: Infinite
  - Duration: 600 seconds
```

### 3. Prueba de Spike
```yaml
Thread Group:
  - Number of Threads: 1000 (repentino)
  - Ramp-up period: 1
  - Loop Count: 1
```

## Pruebas Avanzadas

### Usar Variables
```properties
# En User Defined Variables
BASE_URL=localhost
PORT=8080
USUARIO_ID=1
```

### Headers Dinámicos
```properties
# HTTP Header Manager
Authorization: Bearer ${TOKEN}
Content-Type: application/json
```

### Assertions
```xml
<!-- Response Assertion -->
<assertion>
    <testname>Check Response Code</testname>
    <testclass>org.apache.jmeter.assertions.ResponseAssertion</testclass>
    <testfields>
        <testfield>Assertion.response_code</testfield>
    </testfields>
    <teststrings>
        <teststring>200</teststring>
    </teststrings>
</assertion>
```

### CSV Data Set
```csv
# usuarios.csv
usuario1,password1
usuario2,password2
usuario3,password3
```

```properties
# CSV Data Set Config
Filename: usuarios.csv
Variable Names: username,password
Delimiter: ,
Recycle on EOF: True
Stop thread on EOF: False
```

## Pruebas de Microservicios

### Configuración para nuestro proyecto

#### 1. Test del API Gateway
```yaml
HTTP Request:
  - Server Name: localhost
  - Port Number: 8080
  - HTTP Request: GET
  - Path: /api/usuarios
```

#### 2. Test directo a servicios
```yaml
HTTP Request Usuario Service:
  - Server Name: localhost
  - Port Number: 8081
  - HTTP Request: GET
  - Path: /api/usuarios

HTTP Request Product Service:
  - Server Name: localhost
  - Port Number: 8082
  - HTTP Request: GET
  - Path: /api/productos
```

#### 3. Test con autenticación
```yaml
HTTP Request:
  - Server Name: localhost
  - Port Number: 8080
  - HTTP Request: POST
  - Path: /api/auth/login

Body Data:
{
  "username": "${USERNAME}",
  "password": "${PASSWORD}"
}
```

## Integración con CI/CD

### Ejecutar JMeter en GitLab CI
```yaml
# .gitlab-ci.yml
stages:
  - test
  - performance

performance_test:
  stage: performance
  image: justb4/jmeter:latest
  script:
    - jmeter -n -t performance-test.jmx -l results.jtl -e -o report/
  artifacts:
    reports:
      performance: report/
    paths:
      - results.jtl
    expire_in: 1 week
  only:
    - merge_requests
```

### Ejecutar JMeter en Docker
```bash
# Ejecutar test y generar reporte HTML
docker run --rm \
  -v ${PWD}/tests:/jmeter/tests \
  -v ${PWD}/results:/jmeter/results \
  justb4/jmeter:latest \
  -n -t /jmeter/tests/performance-test.jmx \
  -l /jmeter/results/results.jtl \
  -e -o /jmeter/results/report
```

## Análisis de Resultados

### Métricas clave a monitorear

#### Rendimiento del Sistema
- **CPU Usage**: < 80%
- **Memory Usage**: < 85%
- **Disk I/O**: Sin cuellos de botella
- **Network I/O**: Sin saturación

#### Rendimiento de Aplicación
- **Response Time**: < 500ms para APIs
- **Throughput**: Según requisitos del negocio
- **Error Rate**: < 1%
- **Concurrent Users**: Según capacidad esperada

### Identificar Problemas

#### Problemas comunes
1. **Memory Leaks**: Uso de memoria creciente
2. **Database Bottlenecks**: Consultas lentas
3. **Thread Contention**: Esperas en locks
4. **Network Latency**: Problemas de conectividad

#### Herramientas complementarias
- **VisualVM**: Para profiling de Java
- **YourKit**: Profiler comercial
- **JConsole**: Monitoreo JVM básico
- **Grafana**: Visualización de métricas

## Mejores Prácticas

### Diseño de Pruebas
1. **Empezar pequeño**: Comenzar con pocos usuarios
2. **Incrementar gradualmente**: Aumentar carga progresivamente
3. **Pruebas realistas**: Usar datos similares a producción
4. **Monitoreo continuo**: Observar métricas durante las pruebas

### Entornos de Prueba
1. **Entorno dedicado**: No usar desarrollo para pruebas de carga
2. **Datos de prueba**: Usar volumen similar a producción
3. **Configuración similar**: Misma configuración que producción
4. **Aislamiento**: Evitar interferencias con otros sistemas

### Reportes y Comunicación
1. **Documentar hallazgos**: Registrar todos los problemas encontrados
2. **Priorizar issues**: Clasificar por impacto en el negocio
3. **Recomendaciones**: Sugerir soluciones específicas
4. **Seguimiento**: Monitorear correcciones en futuras pruebas

## Troubleshooting

### Problemas comunes

#### 1. OutOfMemoryError
```bash
# Aumentar memoria de JMeter
jmeter -Xmx2g -Xms1g

# O en jmeter.bat
set HEAP=-Xms1g -Xmx2g
```

#### 2. Too Many Open Files
```bash
# Aumentar límite de archivos abiertos
ulimit -n 65536

# En macOS
launchctl limit maxfiles 65536 65536
```

#### 3. Network Timeouts
```properties
# En jmeter.properties
httpclient.timeout=60000
http.socket.timeout=60000
```

#### 4. High CPU Usage
```bash
# Reducir listeners durante ejecución
# Usar modo no-GUI para mejor performance
jmeter -n -t test.jmx -l results.jtl
```

## Próximos Pasos

### Nivel Intermedio
1. **Plugins de JMeter**: Timers, preprocessors, postprocessors
2. **Pruebas distribuidas**: Usar múltiples máquinas JMeter
3. **Automatización**: Integración con pipelines CI/CD
4. **Análisis avanzado**: Correlación de métricas

### Nivel Avanzado
1. **Pruebas de caos**: Simular fallos del sistema
2. **Machine Learning**: Análisis predictivo de performance
3. **Cloud testing**: Pruebas en entornos cloud
4. **Performance monitoring**: Monitoreo continuo

## Recursos de Aprendizaje

### Documentación Oficial
- [JMeter User Manual](https://jmeter.apache.org/usermanual/)
- [JMeter Best Practices](https://jmeter.apache.org/usermanual/best-practices.html)
- [JMeter Tutorials](https://jmeter.apache.org/usermanual/jmeter_tutorial.pdf)

### Cursos y Tutoriales
- "JMeter Fundamentals" (Udemy)
- "Performance Testing with JMeter" (Coursera)
- "JMeter for Beginners" (YouTube - Testing World)

### Comunidad
- [JMeter User List](https://jmeter.apache.org/mail.html)
- [Stack Overflow - JMeter](https://stackoverflow.com/questions/tagged/jmeter)
- [Reddit - JMeter](https://www.reddit.com/r/jmeter/)

### Libros
- "Performance Testing with JMeter" - Bayo Erinle
- "Mastering JMeter" - Udemy Course

---

*JMeter es una herramienta esencial para garantizar que tus aplicaciones puedan manejar la carga esperada en producción. Esta guía te ayudará a dominar las pruebas de performance paso a paso.*
