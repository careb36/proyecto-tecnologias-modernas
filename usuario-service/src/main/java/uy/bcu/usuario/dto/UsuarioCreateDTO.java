package uy.bcu.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "Datos para crear un nuevo usuario")
public class UsuarioCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo del usuario", example = "María García", required = true)
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Schema(description = "Email único del usuario", example = "maria@ejemplo.com", required = true)
    private String email;

    // Constructores
    public UsuarioCreateDTO() {}

    public UsuarioCreateDTO(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
