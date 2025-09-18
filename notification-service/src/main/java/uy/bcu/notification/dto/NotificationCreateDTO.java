package uy.bcu.notification.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para creación de notificaciones
 */
@Schema(description = "Datos para crear una nueva notificación")
public class NotificationCreateDTO {

    @NotBlank(message = "El destinatario es obligatorio")
    @Schema(description = "Email o número de teléfono del destinatario", example = "usuario@ejemplo.com", required = true)
    private String destinatario;

    @NotBlank(message = "El asunto es obligatorio")
    @Schema(description = "Asunto de la notificación", example = "Bienvenido al sistema", required = true)
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    @Schema(description = "Contenido del mensaje", example = "Gracias por registrarte", required = true)
    private String mensaje;

    @Schema(description = "Prioridad de la notificación", example = "NORMAL",
           allowableValues = {"BAJA", "NORMAL", "ALTA", "URGENTE"})
    private String prioridad = "NORMAL";

    // Constructores
    public NotificationCreateDTO() {}

    public NotificationCreateDTO(String destinatario, String asunto, String mensaje) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
}
