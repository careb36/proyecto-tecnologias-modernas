package uy.bcu.usuario.service;

import org.springframework.stereotype.Service;
import uy.bcu.usuario.UsuarioCreateDTO;
import uy.bcu.usuario.UsuarioDTO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de usuarios
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final Map<Long, UsuarioDTO> usuarios = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Datos de ejemplo
    {
        usuarios.put(1L, new UsuarioDTO(1L, "Juan Pérez", "juan.perez@email.com", LocalDateTime.now().minusDays(30), true));
        usuarios.put(2L, new UsuarioDTO(2L, "María García", "maria.garcia@email.com", LocalDateTime.now().minusDays(20), true));
        usuarios.put(3L, new UsuarioDTO(3L, "Carlos López", "carlos.lopez@email.com", LocalDateTime.now().minusDays(10), false));
    }

    @Override
    public List<UsuarioDTO> obtenerTodosLosUsuarios(int page, int size) {
        return usuarios.values().stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    @Override
    public Optional<UsuarioDTO> obtenerUsuarioPorId(Long id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        // Validar email único
        boolean emailExists = usuarios.values().stream()
                .anyMatch(u -> u.getEmail().equals(usuarioCreateDTO.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Email ya existe: " + usuarioCreateDTO.getEmail());
        }

        Long id = idGenerator.getAndIncrement();
        UsuarioDTO nuevoUsuario = new UsuarioDTO(
                id,
                usuarioCreateDTO.getNombre(),
                usuarioCreateDTO.getEmail(),
                LocalDateTime.now(),
                usuarioCreateDTO.getActivo() != null ? usuarioCreateDTO.getActivo() : true
        );

        if (usuarioCreateDTO.getRol() != null) {
            nuevoUsuario.setRol(usuarioCreateDTO.getRol());
        } else {
            nuevoUsuario.setRol("USER");
        }

        usuarios.put(id, nuevoUsuario);
        return nuevoUsuario;
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioCreateDTO usuarioUpdateDTO) {
        UsuarioDTO usuario = usuarios.get(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + id);
        }

        // Validar email único (excluyendo el usuario actual)
        boolean emailExists = usuarios.values().stream()
                .anyMatch(u -> !u.getId().equals(id) && u.getEmail().equals(usuarioUpdateDTO.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Email ya existe: " + usuarioUpdateDTO.getEmail());
        }

        usuario.setNombre(usuarioUpdateDTO.getNombre());
        usuario.setEmail(usuarioUpdateDTO.getEmail());
        if (usuarioUpdateDTO.getRol() != null) {
            usuario.setRol(usuarioUpdateDTO.getRol());
        }
        if (usuarioUpdateDTO.getActivo() != null) {
            usuario.setActivo(usuarioUpdateDTO.getActivo());
        }

        return usuario;
    }

    @Override
    public void desactivarUsuario(Long id) {
        UsuarioDTO usuario = usuarios.get(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + id);
        }
        usuario.setActivo(false);
    }

    @Override
    public List<UsuarioDTO> buscarUsuarios(String nombre, String email, boolean soloActivos) {
        return usuarios.values().stream()
                .filter(u -> !soloActivos || u.getActivo())
                .filter(u -> nombre == null || u.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(u -> email == null || u.getEmail().toLowerCase().contains(email.toLowerCase()))
                .toList();
    }

    @Override
    public Map<String, Object> obtenerEstadisticas() {
        long totalUsuarios = usuarios.size();
        long usuariosActivos = usuarios.values().stream().mapToLong(u -> u.getActivo() ? 1 : 0).sum();
        long usuariosInactivos = totalUsuarios - usuariosActivos;

        return Map.of(
                "totalUsuarios", totalUsuarios,
                "usuariosActivos", usuariosActivos,
                "usuariosInactivos", usuariosInactivos,
                "fechaGeneracion", LocalDateTime.now()
        );
    }
}
