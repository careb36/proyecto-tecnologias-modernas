package uy.bcu.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.bcu.order.dto.OrderDTO;
import uy.bcu.order.dto.OrderCreateDTO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para el microservicio de pedidos
 * Maneja el procesamiento completo de pedidos con integración entre servicios
 */
@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para procesamiento y gestión de pedidos")
public class OrderController {

    @GetMapping
    @Operation(
        summary = "Obtener historial de pedidos",
        description = "Retorna una lista de todos los pedidos con información de usuario y productos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class)))
    })
    public ResponseEntity<List<OrderDTO>> obtenerPedidos(
            @Parameter(description = "Filtrar por usuario ID") @RequestParam(required = false) Long usuarioId,
            @Parameter(description = "Filtrar por estado") @RequestParam(required = false) String estado) {

        // Datos de ejemplo para demostrar la API
        List<OrderDTO> pedidos = Arrays.asList(
            new OrderDTO(1L, 1L, "Juan Pérez", new BigDecimal("299.99"), "COMPLETADO",
                        LocalDateTime.now().minusDays(2), Arrays.asList("Laptop Dell", "Mouse")),
            new OrderDTO(2L, 2L, "María García", new BigDecimal("159.99"), "PENDIENTE",
                        LocalDateTime.now().minusHours(3), Arrays.asList("Teclado RGB"))
        );

        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener pedido por ID",
        description = "Retorna los detalles completos de un pedido específico"
    )
    public ResponseEntity<OrderDTO> obtenerPedidoPorId(@PathVariable Long id) {
        OrderDTO pedido = new OrderDTO(id, 1L, "Usuario Ejemplo", new BigDecimal("99.99"),
                                      "PROCESANDO", LocalDateTime.now(), Arrays.asList("Producto 1"));
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo pedido",
        description = "Procesa un nuevo pedido validando usuario, productos y stock disponible"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente"),
        @ApiResponse(responseCode = "404", description = "Usuario o producto no encontrado")
    })
    public ResponseEntity<OrderDTO> crearPedido(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        OrderDTO pedido = new OrderDTO(999L, orderCreateDTO.getUsuarioId(), "Usuario",
                                      orderCreateDTO.getTotal(), "PENDIENTE", LocalDateTime.now(),
                                      Arrays.asList("Productos del pedido"));
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @PutMapping("/{id}/estado")
    @Operation(
        summary = "Actualizar estado del pedido",
        description = "Cambia el estado de procesamiento del pedido"
    )
    public ResponseEntity<Map<String, Object>> actualizarEstado(
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del pedido")
            @RequestParam String estado) {

        return ResponseEntity.ok(Map.of(
            "pedidoId", id,
            "estadoAnterior", "PENDIENTE",
            "estadoNuevo", estado,
            "fechaActualizacion", LocalDateTime.now()
        ));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(
        summary = "Pedidos por usuario",
        description = "Obtiene todos los pedidos de un usuario específico"
    )
    public ResponseEntity<List<OrderDTO>> obtenerPedidosPorUsuario(@PathVariable Long usuarioId) {
        List<OrderDTO> pedidos = Arrays.asList(
            new OrderDTO(1L, usuarioId, "Usuario", new BigDecimal("199.99"), "COMPLETADO",
                        LocalDateTime.now().minusDays(5), Arrays.asList("Producto A", "Producto B"))
        );
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/estadisticas")
    @Operation(
        summary = "Estadísticas de pedidos",
        description = "Métricas de rendimiento y estadísticas del procesamiento de pedidos"
    )
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        return ResponseEntity.ok(Map.of(
            "totalPedidos", 1245,
            "pedidosPendientes", 23,
            "pedidosCompletados", 1198,
            "pedidosCancelados", 24,
            "ventasHoy", new BigDecimal("15430.50"),
            "ventasMes", new BigDecimal("342100.25")
        ));
    }
}
