package uy.bcu.usuario.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uy.bcu.usuario.dto.UsuarioCreateDTO;
import uy.bcu.usuario.dto.UsuarioDTO;
import uy.bcu.usuario.model.Usuario;
import uy.bcu.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtener todos los usuarios activos
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        logger.info("Obteniendo todos los usuarios activos");

        List<Usuario> usuarios = usuarioRepository.findAll().stream()
            .filter(Usuario::getActivo)
            .collect(Collectors.toList());

        logger.debug("Encontrados {} usuarios activos", usuarios.size());

        return usuarios.stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    /**
     * Obtener usuario por ID
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> obtenerUsuarioPorId(Long id) {
        logger.info("Buscando usuario con ID: {}", id);

        return usuarioRepository.findById(id)
            .filter(Usuario::getActivo)
            .map(this::convertirADTO);
    }

    /**
     * Crear nuevo usuario
     */
    public UsuarioDTO crearUsuario(UsuarioCreateDTO dto) {
        logger.info("Creando nuevo usuario: {}", dto.getEmail());

        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            logger.warn("Intento de crear usuario con email duplicado: {}", dto.getEmail());
            throw new IllegalArgumentException("Ya existe un usuario con este email");
        }

        Usuario usuario = new Usuario(dto.getNombre(), dto.getEmail());
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        logger.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getId());

        return convertirADTO(usuarioGuardado);
    }

    /**
     * Buscar usuarios por nombre
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> buscarPorNombre(String nombre) {
        logger.info("Buscando usuarios por nombre: {}", nombre);

        return usuarioRepository.findByNombreContainingIgnoreCase(nombre)
            .stream()
            .filter(Usuario::getActivo)
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    /**
     * Desactivar usuario (soft delete)
     */
    public void desactivarUsuario(Long id) {
        logger.info("Desactivando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);

        logger.info("Usuario desactivado exitosamente");
    }

    /**
     * Contar usuarios activos
     */
    @Transactional(readOnly = true)
    public long contarUsuariosActivos() {
        return usuarioRepository.countByActivoTrue();
    }

    /**
     * Convertir entidad Usuario a DTO
     */
    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getEmail(),
            usuario.getFechaCreacion(),
            usuario.getActivo()
        );
    }
}
