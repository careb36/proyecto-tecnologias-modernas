package uy.bcu.usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para creación y actualización de usuarios
 * Incluye validaciones y documentación Swagger completa
 */
@Schema(description = "Datos necesarios para crear o actualizar un usuario")
public class UsuarioCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Schema(description = "Nombre completo del usuario",
           example = "María García",
           required = true,
           minLength = 2,
           maxLength = 100)
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    @Schema(description = "Email único del usuario (será validado por unicidad)",
           example = "maria.garcia@ejemplo.com",
           required = true,
           format = "email",
           maxLength = 255)
    private String email;

    @Schema(description = "Rol del usuario en el sistema",
           example = "USER",
           defaultValue = "USER",
           allowableValues = {"USER", "ADMIN", "MANAGER"})
    private String rol = "USER";

    @Schema(description = "Estado inicial del usuario",
           example = "true",
           defaultValue = "true")
    private Boolean activo = true;

    // Constructores
    public UsuarioCreateDTO() {}

    public UsuarioCreateDTO(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public UsuarioCreateDTO(String nombre, String email, String rol, Boolean activo) {
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.activo = activo;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "UsuarioCreateDTO{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", activo=" + activo +
                '}';
    }
}
