package uy.bcu.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uy.bcu.example.model.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario
 * Demuestra el uso de Spring Data JPA con Oracle Database
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Buscar usuario por email
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Buscar usuarios activos
     */
    List<Usuario> findByActivoTrue();

    /**
     * Buscar usuarios por nombre (ignorando mayúsculas/minúsculas)
     */
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Query personalizada usando JPQL
     */
    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %?1% AND u.activo = true")
    List<Usuario> buscarUsuariosActivosPorNombre(String nombre);

    /**
     * Query nativa para demostrar integración con Oracle
     */
    @Query(value = "SELECT COUNT(*) FROM appuser.usuarios WHERE activo = 1", nativeQuery = true)
    Long contarUsuariosActivos();
}
