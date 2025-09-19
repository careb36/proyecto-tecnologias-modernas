package uy.bcu.usuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uy.bcu.usuario.dto.UsuarioCreateDTO;
import uy.bcu.usuario.dto.UsuarioDTO;
import uy.bcu.usuario.service.UsuarioService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void obtenerTodosLosUsuarios_debeRetornarListaDeUsuarios() throws Exception {
        // Given
        LocalDateTime now = LocalDateTime.now();
        List<UsuarioDTO> usuarios = Arrays.asList(
            new UsuarioDTO(1L, "Juan Pérez", "juan@email.com", now, true),
            new UsuarioDTO(2L, "María García", "maria@email.com", now, true)
        );
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(usuarios);

        // When & Then
        mockMvc.perform(get("/api/usuarios"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
            .andExpect(jsonPath("$[1].nombre").value("María García"));

        verify(usuarioService).obtenerTodosLosUsuarios();
    }

    @Test
    void obtenerUsuarioPorId_debeRetornarUsuarioCuandoExiste() throws Exception {
        // Given
        LocalDateTime now = LocalDateTime.now();
        UsuarioDTO usuario = new UsuarioDTO(1L, "Juan Pérez", "juan@email.com", now, true);
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));

        // When & Then
        mockMvc.perform(get("/api/usuarios/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
            .andExpect(jsonPath("$.email").value("juan@email.com"));

        verify(usuarioService).obtenerUsuarioPorId(1L);
    }

    @Test
    void obtenerUsuarioPorId_debeRetornar404CuandoNoExiste() throws Exception {
        // Given
        when(usuarioService.obtenerUsuarioPorId(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/usuarios/999"))
            .andExpect(status().isNotFound());

        verify(usuarioService).obtenerUsuarioPorId(999L);
    }

    @Test
    void crearUsuario_debeCrearUsuarioExitosamente() throws Exception {
        // Given
        UsuarioCreateDTO createDTO = new UsuarioCreateDTO("Ana López", "ana@email.com");
        LocalDateTime now = LocalDateTime.now();
        UsuarioDTO createdUsuario = new UsuarioDTO(3L, "Ana López", "ana@email.com", now, true);

        when(usuarioService.crearUsuario(any(UsuarioCreateDTO.class))).thenReturn(createdUsuario);

        // When & Then
        mockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createDTO)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.nombre").value("Ana López"))
            .andExpect(jsonPath("$.email").value("ana@email.com"));

        verify(usuarioService).crearUsuario(any(UsuarioCreateDTO.class));
    }

    @Test
    void crearUsuario_debeRetornar400CuandoDatosInvalidos() throws Exception {
        // Given - DTO con datos inválidos
        UsuarioCreateDTO invalidDTO = new UsuarioCreateDTO("", "invalid-email");

        // When & Then
        mockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidDTO)))
            .andExpect(status().isBadRequest());

        verify(usuarioService, never()).crearUsuario(any(UsuarioCreateDTO.class));
    }

    @Test
    void crearUsuario_debeRetornar409CuandoEmailYaExiste() throws Exception {
        // Given
        UsuarioCreateDTO createDTO = new UsuarioCreateDTO("Ana López", "ana@email.com");
        when(usuarioService.crearUsuario(any(UsuarioCreateDTO.class)))
            .thenThrow(new IllegalArgumentException("Ya existe un usuario con este email"));

        // When & Then
        mockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createDTO)))
            .andExpect(status().isConflict());

        verify(usuarioService).crearUsuario(any(UsuarioCreateDTO.class));
    }

    @Test
    void buscarPorNombre_debeRetornarResultadosDeBusqueda() throws Exception {
        // Given
        LocalDateTime now = LocalDateTime.now();
        List<UsuarioDTO> resultados = Arrays.asList(
            new UsuarioDTO(1L, "Juan Pérez", "juan@email.com", now, true)
        );
        when(usuarioService.buscarPorNombre("Juan")).thenReturn(resultados);

        // When & Then
        mockMvc.perform(get("/api/usuarios/buscar")
            .param("nombre", "Juan"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"));

        verify(usuarioService).buscarPorNombre("Juan");
    }

    @Test
    void desactivarUsuario_debeRetornar204CuandoExitoso() throws Exception {
        // Given
        doNothing().when(usuarioService).desactivarUsuario(1L);

        // When & Then
        mockMvc.perform(delete("/api/usuarios/1"))
            .andExpect(status().isNoContent());

        verify(usuarioService).desactivarUsuario(1L);
    }

    @Test
    void desactivarUsuario_debeRetornar404CuandoUsuarioNoExiste() throws Exception {
        // Given
        doThrow(new IllegalArgumentException("Usuario no encontrado"))
            .when(usuarioService).desactivarUsuario(999L);

        // When & Then
        mockMvc.perform(delete("/api/usuarios/999"))
            .andExpect(status().isNotFound());

        verify(usuarioService).desactivarUsuario(999L);
    }

    @Test
    void obtenerEstadisticas_debeRetornarConteoDeUsuariosActivos() throws Exception {
        // Given
        when(usuarioService.contarUsuariosActivos()).thenReturn(5L);

        // When & Then
        mockMvc.perform(get("/api/usuarios/estadisticas"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string("5"));

        verify(usuarioService).contarUsuariosActivos();
    }
}
