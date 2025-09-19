# Java con Spring Boot - Guía para Principiantes

## ¿Qué es Java y por qué es importante?

Java es uno de los lenguajes de programación más utilizados en el mundo empresarial. Fue creado en 1995 por Sun Microsystems y adquirido por Oracle en 2010.

### Ventajas de Java

- **Plataforma independiente**: "Escribe una vez, ejecuta en cualquier lugar"
- **Orientado a objetos**: Todo es un objeto
- **Robusto y seguro**: Manejo automático de memoria
- **Comunidad grande**: Millones de desarrolladores
- **Ecosistema maduro**: Frameworks y librerías para todo

## ¿Qué es Spring Boot?

Spring Boot es un framework que simplifica el desarrollo de aplicaciones Java. Antes de Spring Boot, configurar una aplicación Spring era muy complicado.

### ¿Por qué Spring Boot?

- **Configuración automática**: No necesitas configurar XML complejo
- **Servidor embebido**: Viene con Tomcat incluido
- **Producción lista**: Incluye métricas, health checks, etc.
- **Microservicios friendly**: Ideal para arquitecturas distribuidas

## Instalación y Configuración

### 1. Instalar Java 11+

- Windows (PowerShell)

```powershell
# Requiere Windows 10/11 con winget
winget install --id EclipseAdoptium.Temurin.11.JDK -e --source winget

# Configurar JAVA_HOME y PATH (persistente)
setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-11"
setx PATH "%PATH%;%JAVA_HOME%\bin"

# Verificar instalación
java -version
```

Alternativa (Windows con Chocolatey):

```powershell
choco install temurin11 -y
java -version
```

- macOS (Homebrew)

```bash
brew install --cask temurin11
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 11)' >> ~/.zshrc
echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Verificar
java -version
```

- Ubuntu/Debian

```bash
sudo apt update && sudo apt install -y openjdk-11-jdk
echo 'export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64' >> ~/.bashrc
echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc

# Verificar
java -version
```

Referencias:

- Adoptium (recomendado): <https://adoptium.net/>
- Oracle JDK: <https://www.oracle.com/java/>

### 2. Instalar Maven

- Windows (PowerShell)

```powershell
# Requiere winget
winget install --id Apache.Maven -e --source winget

# (Opcional) Configurar MAVEN_HOME si es necesario
# Ajusta el path si difiere en tu máquina
setx MAVEN_HOME "C:\Program Files\Apache\maven"
setx PATH "%PATH%;%MAVEN_HOME%\bin"

# Verificar
mvn -version
```

Alternativa (Windows con Chocolatey):

```powershell
choco install maven -y
mvn -version
```

- macOS (Homebrew)

```bash
brew install maven
mvn -version
```

- Ubuntu/Debian

```bash
sudo apt update && sudo apt install -y maven
mvn -version
```

### 3. IDE Recomendado

- **Visual Studio Code** (gratuito, recomendado para principiantes)
- **IntelliJ IDEA Community** (gratuito)
- **Eclipse** (gratuito)

## Tu Primera Aplicación Spring Boot

### Crear el Proyecto

#### Opción 1: Spring Initializr (Recomendado)

1. Ve a <https://start.spring.io/>
2. Selecciona:
   - **Project**: Maven Project
   - **Language**: Java
   - **Spring Boot**: 2.7.0
   - **Group**: uy.bcu
   - **Artifact**: mi-primera-app
   - **Name**: Mi Primera App
   - **Package name**: uy.bcu.miprimeraapp
   - **Packaging**: Jar
   - **Java**: 11

3. Agrega estas dependencias:
   - Spring Web
   - Spring Boot DevTools

4. Descarga el ZIP y descomprímelo

#### Opción 2: Línea de comandos

```bash
# Crear proyecto básico
mvn archetype:generate \
  -DgroupId=uy.bcu \
  -DartifactId=mi-primera-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

### Estructura del Proyecto

```
mi-primera-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── uy/
│   │   │       └── bcu/
│   │   │           └── miprimeraapp/
│   │   │               └── MiPrimeraAppApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── uy/
│               └── bcu/
│                   └── miprimeraapp/
│                       └── MiPrimeraAppApplicationTest.java
├── target/          # Archivos compilados (ignorado por Git)
├── pom.xml          # Configuración de Maven
└── README.md
```

### El Archivo Principal

```java
package uy.bcu.miprimeraapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiPrimeraAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiPrimeraAppApplication.class, args);
    }
}
```

**Explicación:**

- `@SpringBootApplication`: Anotación que configura todo automáticamente
- `SpringApplication.run()`: Inicia la aplicación
- Puerto por defecto: 8080

## Crear tu Primer Endpoint REST

### Paso 1: Crear un Controller

```java
package uy.bcu.miprimeraapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoController {

    @GetMapping("/hola")
    public String holaMundo() {
        return "¡Hola Mundo desde Spring Boot!";
    }
}
```

### Paso 2: Ejecutar la aplicación

```bash
# Desde la raíz del proyecto
mvn spring-boot:run
```

### Paso 3: Probar el endpoint

```bash
# En otra terminal
curl http://localhost:8080/hola
```

**Resultado esperado:**

```
¡Hola Mundo desde Spring Boot!
```

## Conceptos Básicos de Spring Boot

### 1. Inyección de Dependencias

Spring administra automáticamente la creación de objetos.

```java
@Service
public class UsuarioService {

    public String obtenerSaludo(String nombre) {
        return "Hola " + nombre + "!";
    }
}

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Spring inyecta automáticamente el servicio
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario/{nombre}")
    public String saludarUsuario(@PathVariable String nombre) {
        return usuarioService.obtenerSaludo(nombre);
    }
}
```

### 2. Application Properties

Archivo de configuración en `src/main/resources/application.properties`:

```properties
# Puerto del servidor
server.port=8080

# Configuración de logging
logging.level.uy.bcu=DEBUG

# Base de datos H2 (para desarrollo)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### 3. Profiles

Diferentes configuraciones para diferentes entornos:

```properties
# application-dev.properties
server.port=8080
spring.profiles.active=dev

# application-prod.properties
server.port=8080
spring.profiles.active=prod
spring.jpa.hibernate.ddl-auto=validate
```

## Trabajar con Bases de Datos

### Configuración Básica

```xml
<!-- En pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Crear una Entidad

```java
package uy.bcu.miprimeraapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    // Constructores, getters y setters
    public Usuario() {}

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y setters...
}
```

### Crear un Repository

```java
package uy.bcu.miprimeraapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.bcu.miprimeraapp.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Spring Data genera automáticamente los métodos CRUD
    // findById(), findAll(), save(), delete(), etc.
}
```

### Crear un Service

```java
package uy.bcu.miprimeraapp.service;

import org.springframework.stereotype.Service;
import uy.bcu.miprimeraapp.model.Usuario;
import uy.bcu.miprimeraapp.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
```

## Testing con Spring Boot

### Frameworks de Testing Utilizados

- **JUnit 5**: Framework principal para escribir y ejecutar tests
- **Mockito**: Framework para crear mocks y stubs de dependencias
- **Spring Boot Test**: Utilidades específicas para testing de aplicaciones Spring

### Dependencias en pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

Esta dependencia incluye automáticamente JUnit 5, Mockito, AssertJ y otras herramientas de testing.

### Test Unitario Básico

```java
package uy.bcu.miprimeraapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MiPrimeraAppApplicationTests {

    @Test
    void contextLoads() {
        // Este test verifica que la aplicación se carga correctamente
    }
}
```

### Test Unitario con Mockito

```java
package uy.bcu.miprimeraapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void testCrearUsuario() {
        // Given
        Usuario usuario = new Usuario("Juan", "juan@email.com");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // When
        Usuario resultado = usuarioService.crearUsuario(usuario);

        // Then
        assertThat(resultado.getNombre()).isEqualTo("Juan");
        assertThat(resultado.getEmail()).isEqualTo("juan@email.com");
    }
}
```

### Test de Controller con MockMvc

```java
package uy.bcu.miprimeraapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HolaMundoController.class)
public class HolaMundoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void testHolaMundo() throws Exception {
        mockMvc.perform(get("/hola"))
               .andExpect(status().isOk())
               .andExpect(content().string("¡Hola Mundo desde Spring Boot!"));
    }

    @Test
    public void testUsuarioConMock() throws Exception {
        // Given
        when(usuarioService.obtenerSaludo("Juan")).thenReturn("Hola Juan!");

        // When & Then
        mockMvc.perform(get("/usuario/Juan"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hola Juan!"));
    }
}
```

### Test de Integración con Testcontainers

```java
package uy.bcu.miprimeraapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class IntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testConBaseDeDatosReal() {
        // Test que usa una base de datos PostgreSQL real en contenedor
    }
}
```

## Debugging y Desarrollo

### Spring Boot DevTools

Agrega esta dependencia para desarrollo:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

**Beneficios:**

- Reinicio automático al cambiar código
- Live reload en el navegador
- Configuración automática para desarrollo

### Logs y Debugging

```java
package uy.bcu.miprimeraapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    @GetMapping("/debug")
    public String debug() {
        logger.info("Entrando al método debug");
        logger.debug("Este es un mensaje de debug");
        logger.warn("Este es un mensaje de warning");
        logger.error("Este es un mensaje de error");

        return "Revisa los logs en la consola";
    }
}
```

## Errores Comunes y Soluciones

### 1. Puerto ya en uso

```bash
# Ver qué proceso usa el puerto 8080
lsof -i :8080

# Matar el proceso
kill -9 <PID>

# O cambiar el puerto en application.properties
server.port=8081
```

### 2. Error de compilación

```bash
# Limpiar y recompilar
mvn clean compile

# Si hay problemas con dependencias
mvn dependency:resolve
```

### 3. Base de datos no conecta

```properties
# Verificar configuración en application.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Próximos Pasos

### Nivel Intermedio

1. **Spring Security**: Autenticación y autorización
2. **Spring Data JPA**: Consultas avanzadas
3. **Spring MVC**: Controladores más complejos
4. **Validación**: Bean Validation
5. **Excepciones**: Manejo global de errores

### Nivel Avanzado

1. **Spring Cloud**: Microservicios
2. **Spring Batch**: Procesamiento por lotes
3. **Spring Integration**: Integración de sistemas
4. **Spring WebFlux**: Programación reactiva

## Recursos de Aprendizaje

### Documentación Oficial

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Guides](https://spring.io/guides)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-boot)

### Cursos

- "Spring Boot Tutorial for Beginners" (YouTube)
- "Master Spring Boot" (Udemy)
- "Spring Framework Fundamentals" (Pluralsight)

### Libros

- "Spring Boot in Action" - Craig Walls
- "Pro Spring Boot 2" - Felipe Gutierrez

### Comunidad

- [Stack Overflow - Spring Boot](https://stackoverflow.com/questions/tagged/spring-boot)
- [Reddit - Java](https://www.reddit.com/r/java/)
- [Spring Forum](https://spring.io/community)

---

*Esta guía está diseñada para que alguien sin conocimientos previos de Java o Spring Boot pueda crear su primera aplicación web en menos de 30 minutos.*
