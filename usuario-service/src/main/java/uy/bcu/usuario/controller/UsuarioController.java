package uy.bcu.usuario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.bcu.usuario.dto.UsuarioDTO;
import uy.bcu.usuario.dto.UsuarioCreateDTO;
import uy.bcu.usuario.service.UsuarioService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios activos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    })
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        logger.info("Solicitud para obtener todos los usuarios");
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario")
            @PathVariable Long id) {
        logger.info("Solicitud para obtener usuario con ID: {}", id);
        return usuarioService.obtenerUsuarioPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Email ya existe")
    })
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        logger.info("Solicitud para crear usuario: {}", dto.getEmail());
        try {
            UsuarioDTO usuario = usuarioService.crearUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuarios por nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    public ResponseEntity<List<UsuarioDTO>> buscarPorNombre(
            @Parameter(description = "Nombre a buscar")
            @RequestParam String nombre) {
        logger.info("Solicitud de búsqueda por nombre: {}", nombre);
        List<UsuarioDTO> usuarios = usuarioService.buscarPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario desactivado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> desactivarUsuario(
            @Parameter(description = "ID del usuario a desactivar")
            @PathVariable Long id) {
        logger.info("Solicitud para desactivar usuario con ID: {}", id);
        try {
            usuarioService.desactivarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al desactivar usuario: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estadisticas")
    @Operation(summary = "Obtener estadísticas de usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    })
    public ResponseEntity<Long> obtenerEstadisticas() {
        logger.info("Solicitud de estadísticas de usuarios");
        long totalActivos = usuarioService.contarUsuariosActivos();
        return ResponseEntity.ok(totalActivos);
    }
}
