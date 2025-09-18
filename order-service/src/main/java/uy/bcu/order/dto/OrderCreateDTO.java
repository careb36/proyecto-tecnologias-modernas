package uy.bcu.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para creación de nuevos pedidos
 */
@Schema(description = "Datos para crear un nuevo pedido")
public class OrderCreateDTO {

    @Schema(description = "ID del usuario que realiza el pedido", example = "1", required = true)
    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser positivo")
    private Long usuarioId;

    @Schema(description = "Lista de IDs de productos a incluir en el pedido", required = true)
    @NotEmpty(message = "Debe incluir al menos un producto")
    private List<Long> productoIds;

    @Schema(description = "Monto total del pedido", example = "299.99", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal total;

    // Constructores
    public OrderCreateDTO() {}

    public OrderCreateDTO(Long usuarioId, List<Long> productoIds) {
        this.usuarioId = usuarioId;
        this.productoIds = productoIds;
    }

    // Getters y Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Long> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(List<Long> productoIds) {
        this.productoIds = productoIds;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
