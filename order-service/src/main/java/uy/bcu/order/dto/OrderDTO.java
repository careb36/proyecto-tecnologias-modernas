package uy.bcu.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para transferencia de datos de Pedido
 */
@Schema(description = "Información completa del pedido")
public class OrderDTO {

    @Schema(description = "ID único del pedido", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID del usuario que realizó el pedido", example = "1")
    private Long usuarioId;

    @Schema(description = "Nombre del usuario", example = "Juan Pérez", accessMode = Schema.AccessMode.READ_ONLY)
    private String nombreUsuario;

    @Schema(description = "Monto total del pedido", example = "299.99")
    private BigDecimal total;

    @Schema(description = "Estado actual del pedido", example = "COMPLETADO",
           allowableValues = {"PENDIENTE", "PROCESANDO", "COMPLETADO", "CANCELADO"})
    private String estado;

    @Schema(description = "Fecha de creación del pedido", example = "2023-10-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaPedido;

    @Schema(description = "Lista de productos incluidos en el pedido")
    private List<String> productos;

    @Schema(description = "Número de items en el pedido", example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer numeroItems;

    // Constructores
    public OrderDTO() {}

    public OrderDTO(Long id, Long usuarioId, String nombreUsuario, BigDecimal total,
                   String estado, LocalDateTime fechaPedido, List<String> productos) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.total = total;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.productos = productos;
        this.numeroItems = productos != null ? productos.size() : 0;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public List<String> getProductos() { return productos; }
    public void setProductos(List<String> productos) {
        this.productos = productos;
        this.numeroItems = productos != null ? productos.size() : 0;
    }

    public Integer getNumeroItems() { return numeroItems; }
    public void setNumeroItems(Integer numeroItems) { this.numeroItems = numeroItems; }
}
