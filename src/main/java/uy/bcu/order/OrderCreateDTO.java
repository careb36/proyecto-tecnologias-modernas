package uy.bcu.order;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para creación de pedidos
 */
@Schema(description = "Datos necesarios para crear un nuevo pedido")
public class OrderCreateDTO {

    @NotNull(message = "El usuario ID es obligatorio")
    @Schema(description = "ID del usuario que realiza el pedido", example = "1", required = true)
    private Long usuarioId;

    @NotEmpty(message = "Debe incluir al menos un producto")
    @Schema(description = "Lista de IDs de productos a incluir en el pedido", required = true)
    private List<Long> productosIds;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    @Schema(description = "Monto total del pedido", example = "299.99", required = true)
    private BigDecimal total;

    @Schema(description = "Comentarios adicionales del pedido", example = "Entregar en horario de oficina")
    private String comentarios;

    // Constructores
    public OrderCreateDTO() {}

    public OrderCreateDTO(Long usuarioId, List<Long> productosIds, BigDecimal total) {
        this.usuarioId = usuarioId;
        this.productosIds = productosIds;
        this.total = total;
    }

    // Getters y Setters
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public List<Long> getProductosIds() { return productosIds; }
    public void setProductosIds(List<Long> productosIds) { this.productosIds = productosIds; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
