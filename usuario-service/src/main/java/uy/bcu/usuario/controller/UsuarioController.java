package uy.bcu.usuario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.bcu.usuario.dto.UsuarioDTO;
import uy.bcu.usuario.dto.UsuarioCreateDTO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios")
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        List<UsuarioDTO> usuarios = Arrays.asList(
            new UsuarioDTO(1L, "Juan Pérez", "juan@ejemplo.com", LocalDateTime.now(), true),
            new UsuarioDTO(2L, "María García", "maria@ejemplo.com", LocalDateTime.now(), true)
        );
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario")
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        UsuarioDTO usuario = new UsuarioDTO(999L, dto.getNombre(), dto.getEmail(), LocalDateTime.now(), true);
        return ResponseEntity.ok(usuario);
    }
}
