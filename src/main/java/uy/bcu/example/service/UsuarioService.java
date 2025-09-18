package uy.bcu.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uy.bcu.example.model.Usuario;
import uy.bcu.example.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de usuarios
 * Incluye logging para demostrar integración con Loki
 */
@Service
@Transactional
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crear un nuevo usuario
     */
    public Usuario crearUsuario(Usuario usuario) {
        logger.info("Creando nuevo usuario: {}", usuario.getEmail());

        // Validar que el email no exista
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            logger.warn("Intento de crear usuario con email duplicado: {}", usuario.getEmail());
            throw new IllegalArgumentException("Ya existe un usuario con este email");
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        logger.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getId());

        return usuarioGuardado;
    }

    /**
     * Obtener todos los usuarios
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodosLosUsuarios() {
        logger.debug("Obteniendo todos los usuarios");
        return usuarioRepository.findAll();
    }

    /**
     * Obtener usuario por ID
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        logger.debug("Buscando usuario con ID: {}", id);
        return usuarioRepository.findById(id);
    }

    /**
     * Obtener usuarios activos
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerUsuariosActivos() {
        logger.debug("Obteniendo usuarios activos");
        return usuarioRepository.findByActivoTrue();
    }

    /**
     * Buscar usuarios por nombre
     */
    @Transactional(readOnly = true)
    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        logger.debug("Buscando usuarios por nombre: {}", nombre);
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Actualizar usuario
     */
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        logger.info("Actualizando usuario con ID: {}", id);

        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioActualizado.getNombre());
                    usuario.setEmail(usuarioActualizado.getEmail());
                    usuario.setActivo(usuarioActualizado.getActivo());

                    Usuario usuarioGuardado = usuarioRepository.save(usuario);
                    logger.info("Usuario actualizado exitosamente: {}", usuarioGuardado.getId());
                    return usuarioGuardado;
                })
                .orElseThrow(() -> {
                    logger.error("Usuario no encontrado con ID: {}", id);
                    return new IllegalArgumentException("Usuario no encontrado");
                });
    }

    /**
     * Desactivar usuario (soft delete)
     */
    public void desactivarUsuario(Long id) {
        logger.info("Desactivando usuario con ID: {}", id);

        usuarioRepository.findById(id)
                .ifPresentOrElse(
                        usuario -> {
                            usuario.setActivo(false);
                            usuarioRepository.save(usuario);
                            logger.info("Usuario desactivado exitosamente: {}", id);
                        },
                        () -> {
                            logger.error("Usuario no encontrado para desactivar con ID: {}", id);
                            throw new IllegalArgumentException("Usuario no encontrado");
                        }
                );
    }

    /**
     * Obtener estadísticas
     */
    @Transactional(readOnly = true)
    public Long contarUsuariosActivos() {
        logger.debug("Contando usuarios activos");
        return usuarioRepository.contarUsuariosActivos();
    }
}
