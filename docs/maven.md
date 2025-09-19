# Maven - Guía de Aprendizaje

## ¿Qué es Maven?

Maven es una herramienta de gestión de proyectos y comprensión que se utiliza principalmente para proyectos Java. Proporciona un modelo de construcción uniforme y maneja automáticamente la descarga de dependencias, compilación, testing, empaquetado y distribución.

## Conceptos Clave

### 1. POM (Project Object Model)
El archivo `pom.xml` es el corazón de un proyecto Maven. Define:
- **Group ID**: Identificador único del grupo/organización
- **Artifact ID**: Nombre del proyecto
- **Version**: Versión del proyecto
- **Dependencies**: Dependencias del proyecto
- **Plugins**: Plugins para extender funcionalidad

### 2. Repositorios
- **Local**: `~/.m2/repository` - Cache de dependencias descargadas
- **Central**: Repositorio oficial de Maven con miles de librerías
- **Remotos**: Repositorios corporativos o personalizados

### 3. Ciclo de Vida
Maven tiene tres ciclos de vida principales:
- **clean**: Limpia archivos generados
- **default**: Construye el proyecto
- **site**: Genera documentación del proyecto

## Configuración en el Proyecto

### Estructura del POM
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.0</version>
        <relativePath/>
    </parent>

    <groupId>uy.bcu.microservices</groupId>
    <artifactId>usuario-service</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <spring-cloud.version>2021.0.3</spring-cloud.version>
    </properties>

    <dependencies>
        <!-- Dependencias del proyecto -->
    </dependencies>

    <build>
        <plugins>
            <!-- Plugins de Maven -->
        </plugins>
    </build>
</project>
```

## Comandos Básicos

### Gestión de Dependencias
```bash
# Descargar dependencias
mvn dependency:resolve

# Analizar dependencias
mvn dependency:analyze

# Ver árbol de dependencias
mvn dependency:tree

# Actualizar versiones de dependencias
mvn versions:display-dependency-updates
```

### Compilación y Testing
```bash
# Compilar el proyecto
mvn compile

# Ejecutar tests
mvn test

# Compilar y ejecutar tests
mvn verify

# Limpiar y compilar
mvn clean compile

# Ejecutar solo tests específicos
mvn test -Dtest=UsuarioServiceTest

# Ejecutar tests con patrón
mvn test -Dtest="*ServiceTest"
```

### Empaquetado y Distribución
```bash
# Crear JAR
mvn package

# Instalar en repositorio local
mvn install

# Desplegar a repositorio remoto
mvn deploy

# Crear sitio de documentación
mvn site
```

### Análisis y Reportes
```bash
# Generar reportes de cobertura (JaCoCo)
mvn jacoco:report

# Ejecutar análisis estático (SpotBugs)
mvn spotbugs:check

# Verificar calidad del código
mvn checkstyle:check
```

## Plugins Utilizados en el Proyecto

### 1. Spring Boot Maven Plugin
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```
- Crea JAR ejecutable con todas las dependencias
- Soporta perfiles de Spring Boot
- Incluye `spring-boot:run` para desarrollo

### 2. JaCoCo Maven Plugin
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
- Mide cobertura de código en tests
- Genera reportes HTML/XML
- Integra con SonarQube

### 3. Maven Compiler Plugin
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.10.1</version>
    <configuration>
        <source>11</source>
        <target>11</target>
    </configuration>
</plugin>
```
- Compila código fuente Java
- Configura versión de Java

### 4. Maven Surefire Plugin
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
    <configuration>
        <includes>
            <include>**/*Test.java</include>
        </includes>
    </configuration>
</plugin>
```
- Ejecuta tests unitarios
- Genera reportes de tests

## Gestión de Dependencias

### Dependencias de Spring Boot
```xml
<dependencies>
    <!-- Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Versiones Gestionadas
Spring Boot Starter Parent maneja versiones de:
- Spring Framework
- Hibernate
- Jackson
- Tomcat
- Y muchas otras librerías

### Scopes de Dependencias
- **compile**: Disponible en todos los classpath (default)
- **test**: Solo para testing
- **runtime**: Necesario en runtime, no en compile
- **provided**: Proporcionado por el entorno (ej: servlet-api)

## Ejercicios Prácticos

### Ejercicio 1: Exploración del Proyecto
```bash
# Ver estructura del proyecto
mvn help:effective-pom | head -50

# Ver dependencias efectivas
mvn dependency:tree

# Ver plugins activos
mvn help:all-profiles
```

### Ejercicio 2: Ciclo de Desarrollo
```bash
# Desarrollo típico
mvn clean compile

# Ejecutar tests
mvn test

# Verificar calidad
mvn verify

# Crear paquete
mvn package

# Ejecutar aplicación
java -jar target/usuario-service-1.0.0.jar
```

### Ejercicio 3: Análisis de Dependencias
```bash
# Buscar dependencias vulnerables
mvn org.owasp:dependency-check-maven:check

# Verificar actualizaciones disponibles
mvn versions:display-dependency-updates

# Analizar uso de dependencias
mvn dependency:analyze
```

### Ejercicio 4: Perfiles Maven
```xml
<profiles>
    <profile>
        <id>dev</id>
        <properties>
            <spring.profiles.active>dev</spring.profiles.active>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <spring.profiles.active>prod</spring.profiles.active>
        </properties>
    </profile>
</profiles>
```

```bash
# Ejecutar con perfil específico
mvn clean package -Pprod
```

## Configuración Avanzada

### Repositorios Personalizados
```xml
<repositories>
    <repository>
        <id>company-repo</id>
        <url>https://repo.company.com/maven</url>
    </repository>
</repositories>
```

### Configuración de Proxy
```xml
<proxies>
    <proxy>
        <id>company-proxy</id>
        <active>true</active>
        <protocol>http</protocol>
        <host>proxy.company.com</host>
        <port>8080</port>
    </proxy>
</proxies>
```

### Variables Personalizadas
```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <custom.version>1.0.0</custom.version>
</properties>
```

## Integración con CI/CD

### GitLab CI con Maven
```yaml
stages:
  - build
  - test
  - quality

build:
  stage: build
  script:
    - mvn clean compile

test:
  stage: test
  script:
    - mvn test
  artifacts:
    reports:
      junit: target/surefire-reports/TEST-*.xml

quality:
  stage: quality
  script:
    - mvn sonar:sonar
```

## Mejores Prácticas

### 1. Estructura del Proyecto
- Seguir convención estándar de Maven
- Usar módulos para proyectos grandes
- Mantener POMs limpios y organizados

### 2. Gestión de Dependencias
- Usar BOMs (Bill of Materials) para versiones consistentes
- Evitar dependencias transitivas conflictivas
- Auditar dependencias regularmente

### 3. Plugins y Configuración
- Configurar plugins en pluginManagement
- Usar versiones específicas de plugins
- Documentar configuraciones personalizadas

### 4. Performance
- Usar `-T` para builds paralelos
- Configurar repositorios mirror
- Optimizar configuración de plugins

## Troubleshooting Común

### Problemas de Dependencias
```bash
# Limpiar repositorio local
rm -rf ~/.m2/repository

# Forzar actualización
mvn clean compile -U

# Ver conflictos de versiones
mvn dependency:tree -Dverbose
```

### Problemas de Memoria
```bash
# Aumentar memoria para Maven
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m"
mvn clean install
```

### Problemas de Red
```bash
# Verificar conectividad
mvn help:system

# Configurar proxy en settings.xml
```

### Problemas de Tests
```bash
# Ejecutar tests con debug
mvn test -DforkCount=1 -DreuseForks=false

# Ver logs detallados
mvn test -Dmaven.surefire.debug=true
```

## Recursos Adicionales

### Documentación Oficial
- [Maven Documentation](https://maven.apache.org/guides/index.html)
- [Maven Central Repository](https://search.maven.org/)
- [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/)

### Libros Recomendados
- **"Maven: The Complete Reference"** - Maven Developers
- **"Better Builds with Maven"** - Maven Team

### Comunidades
- [Maven Users Mailing List](https://maven.apache.org/mailing-lists.html)
- [Stack Overflow - Maven](https://stackoverflow.com/questions/tagged/maven)

## Próximos Pasos

1. Explorar Maven Wrapper para consistencia de versiones
2. Aprender sobre multi-module projects
3. Configurar repositorios corporativos
4. Implementar CI/CD avanzado con Maven
5. Optimizar builds para proyectos grandes

---

*Maven es fundamental en el ecosistema Java moderno. Este proyecto demuestra las mejores prácticas para usar Maven en aplicaciones Spring Boot y microservicios.*
