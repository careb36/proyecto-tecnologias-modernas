package uy.bcu.usuario;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para transferencia de datos de Usuario
 * Incluye documentación Swagger/OpenAPI completa
 */
@Schema(description = "Información completa del usuario")
public class UsuarioDTO {

    @Schema(description = "ID único del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", required = true)
    private String nombre;

    @Schema(description = "Email único del usuario", example = "juan.perez@ejemplo.com", required = true)
    private String email;

    @Schema(description = "Fecha de creación del usuario", example = "2023-10-15T10:30:00",
           accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Estado del usuario (activo/inactivo)", example = "true")
    private Boolean activo;

    @Schema(description = "Rol del usuario en el sistema", example = "USER")
    private String rol;

    @Schema(description = "Número de pedidos realizados por el usuario", example = "5",
           accessMode = Schema.AccessMode.READ_ONLY)
    private Integer numeroPedidos;

    // Constructores
    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nombre, String email, LocalDateTime fechaCreacion, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.fechaCreacion = fechaCreacion;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Integer getNumeroPedidos() { return numeroPedidos; }
    public void setNumeroPedidos(Integer numeroPedidos) { this.numeroPedidos = numeroPedidos; }
}
