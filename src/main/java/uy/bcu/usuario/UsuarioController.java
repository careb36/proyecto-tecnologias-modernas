package uy.bcu.usuario;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import uy.bcu.usuario.service.UsuarioService;

/**
 * Controlador REST para el microservicio de usuarios
 * Incluye documentación completa con OpenAPI/Swagger
 */
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios en arquitectura de microservicios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna una lista paginada de todos los usuarios registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios(
            @Parameter(description = "Número de página (empezando en 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página")
            @RequestParam(defaultValue = "10") int size) {

        logger.info("GET /api/usuarios - Obteniendo todos los usuarios - page: {}, size: {}", page, size);
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarios(page, size);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Retorna los datos completos de un usuario específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(
            @Parameter(description = "ID único del usuario", example = "1")
            @PathVariable Long id) {

        logger.info("GET /api/usuarios/{} - Obteniendo usuario por ID", id);
        return usuarioService.obtenerUsuarioPorId(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo usuario",
        description = "Crea un nuevo usuario en el sistema con validaciones completas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "Email ya existe en el sistema")
    })
    public ResponseEntity<UsuarioDTO> crearUsuario(
            @Parameter(description = "Datos del usuario a crear")
            @Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {

        logger.info("POST /api/usuarios - Creando nuevo usuario: {}", usuarioCreateDTO.getEmail());
        try {
            UsuarioDTO usuarioCreado = usuarioService.crearUsuario(usuarioCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar usuario existente",
        description = "Actualiza los datos de un usuario existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @Parameter(description = "ID del usuario a actualizar")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del usuario")
            @Valid @RequestBody UsuarioCreateDTO usuarioUpdateDTO) {

        logger.info("PUT /api/usuarios/{} - Actualizando usuario", id);
        try {
            UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioUpdateDTO);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IllegalArgumentException e) {
            logger.error("Error al actualizar usuario: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Desactivar usuario",
        description = "Desactiva un usuario (soft delete) en lugar de eliminarlo permanentemente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario desactivado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> desactivarUsuario(
            @Parameter(description = "ID del usuario a desactivar")
            @PathVariable Long id) {

        logger.info("DELETE /api/usuarios/{} - Desactivando usuario", id);
        try {
            usuarioService.desactivarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error al desactivar usuario: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    @Operation(
        summary = "Buscar usuarios por criterios",
        description = "Permite buscar usuarios utilizando diferentes criterios como nombre o email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Criterios de búsqueda inválidos")
    })
    public ResponseEntity<List<UsuarioDTO>> buscarUsuarios(
            @Parameter(description = "Texto a buscar en nombre")
            @RequestParam(required = false) String nombre,
            @Parameter(description = "Email a buscar")
            @RequestParam(required = false) String email,
            @Parameter(description = "Solo usuarios activos")
            @RequestParam(defaultValue = "true") boolean soloActivos) {

        logger.info("GET /api/usuarios/buscar - nombre: {}, email: {}, soloActivos: {}",
                   nombre, email, soloActivos);
        List<UsuarioDTO> usuarios = usuarioService.buscarUsuarios(nombre, email, soloActivos);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/estadisticas")
    @Operation(
        summary = "Obtener estadísticas de usuarios",
        description = "Retorna métricas y estadísticas sobre los usuarios del sistema"
    )
    public ResponseEntity<Object> obtenerEstadisticas() {
        logger.info("GET /api/usuarios/estadisticas - Obteniendo estadísticas");
        var estadisticas = usuarioService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }
}
