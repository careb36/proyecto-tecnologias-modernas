package uy.bcu.usuario.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uy.bcu.usuario.UsuarioCreateDTO;
import uy.bcu.usuario.UsuarioDTO;

/**
 * Servicio para gestión de usuarios
 */
public interface UsuarioService {

    List<UsuarioDTO> obtenerTodosLosUsuarios(int page, int size);

    Optional<UsuarioDTO> obtenerUsuarioPorId(Long id);

    UsuarioDTO crearUsuario(UsuarioCreateDTO usuarioCreateDTO);

    UsuarioDTO actualizarUsuario(Long id, UsuarioCreateDTO usuarioUpdateDTO);

    void desactivarUsuario(Long id);

    List<UsuarioDTO> buscarUsuarios(String nombre, String email, boolean soloActivos);

    Map<String, Object> obtenerEstadisticas();
}
