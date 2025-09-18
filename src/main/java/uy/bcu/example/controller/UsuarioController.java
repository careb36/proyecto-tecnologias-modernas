package uy.bcu.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.bcu.example.model.Usuario;
import uy.bcu.example.service.UsuarioService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de usuarios
 * Genera métricas para Prometheus y logs para Loki
 */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        logger.info("GET /api/usuarios - Obteniendo todos los usuarios");
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        logger.info("GET /api/usuarios/{} - Obteniendo usuario por ID", id);
        return usuarioService.obtenerUsuarioPorId(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    @Operation(summary = "Obtener usuarios activos")
    public ResponseEntity<List<Usuario>> obtenerUsuariosActivos() {
        logger.info("GET /api/usuarios/activos - Obteniendo usuarios activos");
        List<Usuario> usuarios = usuarioService.obtenerUsuariosActivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuarios por nombre")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorNombre(@RequestParam String nombre) {
        logger.info("GET /api/usuarios/buscar?nombre={} - Buscando usuarios por nombre", nombre);
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        logger.info("POST /api/usuarios - Creando nuevo usuario: {}", usuario.getEmail());
        try {
            Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear usuario: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id,
                                                    @Valid @RequestBody Usuario usuario) {
        logger.info("PUT /api/usuarios/{} - Actualizando usuario", id);
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar usuario: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar usuario")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
        logger.info("DELETE /api/usuarios/{} - Desactivando usuario", id);
        try {
            usuarioService.desactivarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al desactivar usuario: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estadisticas")
    @Operation(summary = "Obtener estadísticas de usuarios")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticas() {
        logger.info("GET /api/usuarios/estadisticas - Obteniendo estadísticas");
        Long usuariosActivos = usuarioService.contarUsuariosActivos();
        return ResponseEntity.ok(Map.of("usuariosActivos", usuariosActivos));
    }

    @GetMapping("/health")
    @Operation(summary = "Health check endpoint")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "usuario-service",
            "timestamp", java.time.LocalDateTime.now().toString()
        ));
    }
}
