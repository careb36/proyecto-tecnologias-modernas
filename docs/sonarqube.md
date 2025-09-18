# SonarQube - Guía de Aprendizaje

## ¿Qué es SonarQube?

SonarQube es una plataforma de análisis estático de código que detecta bugs, vulnerabilidades de seguridad, code smells y proporciona métricas de calidad del código.

## Conceptos Clave

### 1. Quality Gate
Conjunto de condiciones que debe cumplir el código para ser considerado apto para producción.

### 2. Issues (Problemas)
- **Bugs**: Errores en el código que pueden causar comportamiento inesperado
- **Vulnerabilities**: Problemas de seguridad
- **Code Smells**: Código que funciona pero es difícil de mantener

### 3. Coverage (Cobertura)
Porcentaje de código cubierto por pruebas unitarias.

### 4. Duplications (Duplicaciones)
Bloques de código duplicado que deberían ser refactorizados.

### 5. Technical Debt (Deuda Técnica)
Tiempo estimado para arreglar todos los problemas de mantenibilidad.

## Configuración Inicial

### 1. Acceso
- URL: http://localhost:9000
- Usuario por defecto: `admin`
- Contraseña por defecto: `admin`

### 2. Configuración del Proyecto

#### Crear Proyecto
1. Login en SonarQube
2. Create new project → Manually
3. Project key: `tech-stack-project`
4. Display name: `Tech Stack Project`

#### Generar Token
1. My Account → Security
2. Generate Tokens
3. Name: `gitlab-ci`
4. Copiar el token generado

### 3. Configuración en GitLab CI
Agregar variables en GitLab → Settings → CI/CD → Variables:
```
SONAR_HOST_URL = http://localhost:9000
SONAR_TOKEN = <token-generado>
```

## Análisis del Código

### 1. Configuración Maven
El `pom.xml` ya incluye el plugin de SonarQube:

```xml
<plugin>
    <groupId>org.sonarsource.scanner.maven</groupId>
    <artifactId>sonar-maven-plugin</artifactId>
    <version>3.9.1.2184</version>
</plugin>
```

### 2. Ejecución Local
```bash
# Ejecutar análisis completo
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=tech-stack-project \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<tu-token>

# Solo análisis (sin tests)
mvn sonar:sonar \
  -Dsonar.projectKey=tech-stack-project \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<tu-token>
```

### 3. Integración con GitLab CI
El pipeline ya incluye el job de análisis:

```yaml
sonarqube-check:
  stage: quality
  image: maven:3.8.1-openjdk-11
  script:
    - mvn verify sonar:sonar
      -Dsonar.projectKey=$CI_PROJECT_NAME
      -Dsonar.host.url=$SONAR_HOST_URL
      -Dsonar.login=$SONAR_TOKEN
```

## Quality Gates

### 1. Default Quality Gate
Condiciones por defecto:
- Coverage < 80% → FAILED
- Duplicated lines > 3% → FAILED
- Maintainability rating worse than A → FAILED
- Reliability rating worse than A → FAILED
- Security rating worse than A → FAILED

### 2. Crear Quality Gate Personalizado
1. Quality Gates → Create
2. Name: `Tech Stack Gate`
3. Add Conditions:
   - Coverage on new code < 70%
   - Duplicated lines on new code > 3%
   - Bugs on new code > 0
   - Vulnerabilities on new code > 0

### 3. Asignar al Proyecto
1. Project → Administration → Quality Gate
2. Select: `Tech Stack Gate`

## Métricas Importantes

### 1. Reliability (Fiabilidad)
- **A**: 0 bugs
- **B**: Al menos 1 bug minor
- **C**: Al menos 1 bug major
- **D**: Al menos 1 bug critical
- **E**: Al menos 1 bug blocker

### 2. Security (Seguridad)
- **A**: 0 vulnerabilidades
- **B**: Al menos 1 vulnerabilidad minor
- **C**: Al menos 1 vulnerabilidad major
- **D**: Al menos 1 vulnerabilidad critical
- **E**: Al menos 1 vulnerabilidad blocker

### 3. Maintainability (Mantenibilidad)
Basado en la deuda técnica:
- **A**: ≤5%
- **B**: 6-10%
- **C**: 11-20%
- **D**: 21-50%
- **E**: >50%

## Ejercicios Prácticos

### Ejercicio 1: Primer Análisis
1. Ejecutar análisis local:
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=tech-stack-project \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<tu-token>
```

2. Revisar resultados en SonarQube
3. Identificar issues encontrados
4. Analizar métricas de coverage

### Ejercicio 2: Arreglar Code Smells
1. Ir a Issues → Type: Code Smell
2. Filtrar por: Severity = Major
3. Arreglar al menos 3 code smells
4. Ejecutar análisis nuevamente
5. Verificar mejora en métricas

### Ejercicio 3: Mejorar Coverage
1. Identificar clases con baja cobertura
2. Escribir tests unitarios adicionales
3. Ejecutar tests: `mvn test`
4. Verificar cobertura: `mvn jacoco:report`
5. Ejecutar análisis SonarQube
6. Comprobar mejora en coverage

### Ejercicio 4: Configurar Quality Gate
1. Crear un Quality Gate personalizado
2. Configurar condiciones específicas
3. Asignar al proyecto
4. Hacer que el pipeline falle si no pasa el Quality Gate

## Configuración Avanzada

### 1. Exclusiones
Configurar en `sonar-project.properties`:

```properties
# Excluir archivos de análisis
sonar.exclusions=src/main/java/uy/bcu/example/config/**/*,**/*Test.java

# Excluir de cobertura
sonar.coverage.exclusions=src/main/java/uy/bcu/example/model/**/*

# Excluir duplicaciones
sonar.cpd.exclusions=src/main/java/uy/bcu/example/dto/**/*
```

### 2. Configuración Maven
```xml
<properties>
    <sonar.projectKey>tech-stack-project</sonar.projectKey>
    <sonar.organization>mi-organizacion</sonar.organization>
    <sonar.host.url>http://localhost:9000</sonar.host.url>
    <sonar.coverage.jacoco.xmlReportPaths>target/site/jacoco/jacoco.xml</sonar.coverage.jacoco.xmlReportPaths>
</properties>
```

### 3. Integración con Pull Requests
```yaml
# GitLab CI para MR
sonarqube-check-mr:
  stage: quality
  script:
    - mvn sonar:sonar
      -Dsonar.merge-request.key=$CI_MERGE_REQUEST_IID
      -Dsonar.merge-request.branch=$CI_MERGE_REQUEST_SOURCE_BRANCH_NAME
      -Dsonar.merge-request.base=$CI_MERGE_REQUEST_TARGET_BRANCH_NAME
  only:
    - merge_requests
```

## Rules (Reglas)

### 1. Gestión de Reglas
1. Rules → Search rules
2. Filtrar por Language: Java
3. Activar/desactivar reglas
4. Crear perfiles de calidad personalizados

### 2. Reglas Importantes para Java
- **Cognitive Complexity**: Método muy complejo
- **Unused imports**: Imports no utilizados
- **Magic numbers**: Números mágicos
- **Empty catch blocks**: Bloques catch vacíos
- **System.out.println**: No usar en producción

### 3. Crear Quality Profile
1. Quality Profiles → Create
2. Name: `Tech Stack Java`
3. Copy from: `Sonar way`
4. Personalizar reglas
5. Set as Default

## Webhooks y Notificaciones

### 1. Configurar Webhook
1. Administration → Configuration → Webhooks
2. Name: `GitLab Integration`
3. URL: `https://gitlab.example.com/api/v4/projects/1/statuses/$SHA`
4. Secret: `<webhook-secret>`

### 2. Email Notifications
1. My Account → Notifications
2. Configurar para:
   - Quality Gate status changes
   - New issues assigned to me
   - Issues resolved as false-positive

## Troubleshooting Común

### 1. Analysis Fails
```bash
# Verificar logs detallados
mvn sonar:sonar -X

# Verificar conectividad
curl -u admin:admin http://localhost:9000/api/system/status
```

### 2. Coverage Not Showing
- Verificar que JaCoCo esté configurado
- Comprobar path del reporte XML
- Ejecutar tests antes del análisis

### 3. Authentication Issues
- Verificar token SonarQube
- Comprobar permisos del usuario
- Verificar URL del servidor

## Métricas y Reportes

### 1. Activity Tab
- Historial de análisis
- Evolución de métricas
- Comparación entre versiones

### 2. Measures Tab
- Overview de todas las métricas
- Drill-down por componentes
- Histórico de métricas

### 3. Issues Tab
- Lista de todos los issues
- Filtros avanzados
- Bulk change operations

## Mejores Prácticas

### 1. Desarrollo
- Ejecutar análisis regularmente
- Arreglar issues nuevos inmediatamente
- No ignorar issues sin justificación
- Mantener coverage > 70%

### 2. CI/CD Integration
- Incluir en todos los pipelines
- Fallar build si Quality Gate no pasa
- Analizar pull/merge requests
- Configurar notificaciones apropiadas

### 3. Team Management
- Definir ownership de componentes
- Regular code review de issues
- Training en reglas de calidad
- Establecer goals de mejora

## Plugins Útiles

### 1. Community Plugins
- **Dependency Check**: Análisis de dependencias vulnerables
- **PMD**: Reglas adicionales de PMD
- **Checkstyle**: Integración con Checkstyle
- **SpotBugs**: Análisis adicional de bugs

### 2. Instalación
1. Administration → Marketplace
2. Search plugin
3. Install
4. Restart SonarQube

## Recursos Adicionales

- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Clean as You Code](https://www.sonarsource.com/solutions/clean-as-you-code/)
- [SonarQube Rules](https://rules.sonarsource.com/)
- [Quality Gate Best Practices](https://docs.sonarqube.org/latest/user-guide/quality-gates/)

## Próximos Pasos

1. Configurar análisis automático en CI/CD
2. Establecer Quality Gates apropiados
3. Entrenar al equipo en reglas de calidad
4. Implementar estrategia "Clean as You Code"
5. Explorar análisis de seguridad avanzado
