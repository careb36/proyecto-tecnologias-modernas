package uy.bcu.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.bcu.notification.dto.NotificationCreateDTO;
import uy.bcu.notification.dto.NotificationDTO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para el microservicio de notificaciones
 */
@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "API para gestión de notificaciones (Email, SMS, Push)")
public class NotificationController {

    @PostMapping("/email")
    @Operation(
        summary = "Enviar notificación por email",
        description = "Envía una notificación por correo electrónico"
    )
    public ResponseEntity<Map<String, Object>> enviarEmail(@Valid @RequestBody NotificationCreateDTO notification) {
        return ResponseEntity.ok(Map.of(
            "id", 123L,
            "tipo", "EMAIL",
            "estado", "ENVIADO",
            "timestamp", LocalDateTime.now()
        ));
    }

    @PostMapping("/sms")
    @Operation(summary = "Enviar SMS", description = "Envía una notificación por SMS")
    public ResponseEntity<Map<String, Object>> enviarSMS(@Valid @RequestBody NotificationCreateDTO notification) {
        return ResponseEntity.ok(Map.of(
            "id", 124L,
            "tipo", "SMS",
            "estado", "ENVIADO",
            "timestamp", LocalDateTime.now()
        ));
    }

    @GetMapping("/historial")
    @Operation(summary = "Historial de notificaciones")
    public ResponseEntity<List<NotificationDTO>> obtenerHistorial(
            @Parameter(description = "Filtrar por tipo") @RequestParam(required = false) String tipo) {

        List<NotificationDTO> notificaciones = Arrays.asList(
            new NotificationDTO(1L, "EMAIL", "Bienvenida", "ENVIADO", LocalDateTime.now()),
            new NotificationDTO(2L, "SMS", "Código de verificación", "ENVIADO", LocalDateTime.now())
        );
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/estadisticas")
    @Operation(summary = "Estadísticas de notificaciones")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        return ResponseEntity.ok(Map.of(
            "emailsEnviados", 1542,
            "smsEnviados", 324,
            "pushEnviadas", 892,
            "tasaExito", 98.5
        ));
    }
}
