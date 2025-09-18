package uy.bcu.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "Datos para crear un nuevo producto")
public class ProductCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del producto", example = "Laptop HP", required = true)
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Laptop de 14 pulgadas")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio del producto", example = "899.99", required = true)
    private Double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Stock inicial", example = "10")
    private Integer stock;

    // Constructores
    public ProductCreateDTO() {}

    public ProductCreateDTO(String nombre, String descripcion, Double precio, Integer stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
