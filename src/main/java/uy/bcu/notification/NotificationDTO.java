package uy.bcu.notification;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para respuesta de notificaciones
 */
@Schema(description = "Datos de una notificación")
public class NotificationDTO {

    @Schema(description = "ID único de la notificación", example = "1")
    private Long id;

    @Schema(description = "Tipo de notificación", example = "EMAIL",
            allowableValues = {"EMAIL", "SMS", "PUSH"})
    private String tipo;

    @Schema(description = "Asunto de la notificación", example = "Bienvenida")
    private String asunto;

    @Schema(description = "Estado de la notificación", example = "ENVIADO",
            allowableValues = {"PENDIENTE", "ENVIADO", "FALLIDO"})
    private String estado;

    @Schema(description = "Fecha y hora de envío")
    private LocalDateTime timestamp;

    // Constructores
    public NotificationDTO() {}

    public NotificationDTO(Long id, String tipo, String asunto, String estado, LocalDateTime timestamp) {
        this.id = id;
        this.tipo = tipo;
        this.asunto = asunto;
        this.estado = estado;
        this.timestamp = timestamp;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
