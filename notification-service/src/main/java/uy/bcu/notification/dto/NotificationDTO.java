package uy.bcu.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * DTO para transferencia de datos de Notificación
 */
@Schema(description = "Información de notificación enviada")
public class NotificationDTO {

    @Schema(description = "ID único de la notificación", example = "1")
    private Long id;

    @Schema(description = "Tipo de notificación", example = "EMAIL",
           allowableValues = {"EMAIL", "SMS", "PUSH"})
    private String tipo;

    @Schema(description = "Asunto o título de la notificación", example = "Bienvenido al sistema")
    private String asunto;

    @Schema(description = "Estado de la notificación", example = "ENVIADO",
           allowableValues = {"PENDIENTE", "ENVIADO", "ERROR"})
    private String estado;

    @Schema(description = "Fecha de envío", example = "2023-10-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaEnvio;

    // Constructores
    public NotificationDTO() {}

    public NotificationDTO(Long id, String tipo, String asunto, String estado, LocalDateTime fechaEnvio) {
        this.id = id;
        this.tipo = tipo;
        this.asunto = asunto;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
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

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}
