package uy.bcu.usuario.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.bcu.usuario.dto.UsuarioCreateDTO;
import uy.bcu.usuario.dto.UsuarioDTO;
import uy.bcu.usuario.model.Usuario;
import uy.bcu.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario1;
    private Usuario usuario2;
    private UsuarioCreateDTO createDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        usuario1 = new Usuario("Juan Pérez", "juan@email.com");
        usuario1.setId(1L);
        usuario1.setFechaCreacion(now);
        usuario1.setActivo(true);

        usuario2 = new Usuario("María García", "maria@email.com");
        usuario2.setId(2L);
        usuario2.setFechaCreacion(now);
        usuario2.setActivo(true);

        createDTO = new UsuarioCreateDTO("Ana López", "ana@email.com");
    }

    @Test
    void obtenerTodosLosUsuarios_debeRetornarListaDeUsuariosActivos() {
        // Given
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        // When
        List<UsuarioDTO> result = usuarioService.obtenerTodosLosUsuarios();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNombre()).isEqualTo("Juan Pérez");
        assertThat(result.get(1).getNombre()).isEqualTo("María García");
        verify(usuarioRepository).findAll();
    }

    @Test
    void obtenerUsuarioPorId_debeRetornarUsuarioCuandoExisteYEstaActivo() {
        // Given
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));

        // When
        Optional<UsuarioDTO> result = usuarioService.obtenerUsuarioPorId(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getNombre()).isEqualTo("Juan Pérez");
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void obtenerUsuarioPorId_debeRetornarEmptyCuandoUsuarioNoExiste() {
        // Given
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<UsuarioDTO> result = usuarioService.obtenerUsuarioPorId(999L);

        // Then
        assertThat(result).isEmpty();
        verify(usuarioRepository).findById(999L);
    }

    @Test
    void crearUsuario_debeCrearUsuarioExitosamente() {
        // Given
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        // When
        UsuarioDTO result = usuarioService.crearUsuario(createDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Juan Pérez");
        assertThat(result.getEmail()).isEqualTo("juan@email.com");
        verify(usuarioRepository).existsByEmail("ana@email.com");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void crearUsuario_debeLanzarExcepcionCuandoEmailYaExiste() {
        // Given
        when(usuarioRepository.existsByEmail("ana@email.com")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> usuarioService.crearUsuario(createDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Ya existe un usuario con este email");

        verify(usuarioRepository).existsByEmail("ana@email.com");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void buscarPorNombre_debeRetornarUsuariosFiltrados() {
        // Given
        when(usuarioRepository.findByNombreContainingIgnoreCase("Juan"))
            .thenReturn(Arrays.asList(usuario1));

        // When
        List<UsuarioDTO> result = usuarioService.buscarPorNombre("Juan");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("Juan Pérez");
        verify(usuarioRepository).findByNombreContainingIgnoreCase("Juan");
    }

    @Test
    void desactivarUsuario_debeDesactivarUsuarioExistente() {
        // Given
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        // When
        usuarioService.desactivarUsuario(1L);

        // Then
        assertThat(usuario1.getActivo()).isFalse();
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(usuario1);
    }

    @Test
    void desactivarUsuario_debeLanzarExcepcionCuandoUsuarioNoExiste() {
        // Given
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> usuarioService.desactivarUsuario(999L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Usuario no encontrado");

        verify(usuarioRepository).findById(999L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void contarUsuariosActivos_debeRetornarConteoCorrecto() {
        // Given
        when(usuarioRepository.countByActivoTrue()).thenReturn(5L);

        // When
        long result = usuarioService.contarUsuariosActivos();

        // Then
        assertThat(result).isEqualTo(5L);
        verify(usuarioRepository).countByActivoTrue();
    }
}
