package uy.bcu.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para transferencia de datos de Producto
 * Incluye documentación Swagger/OpenAPI completa
 */
@Schema(description = "Información completa del producto")
public class ProductDTO {

    @Schema(description = "ID único del producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre del producto", example = "Laptop Dell XPS 13", required = true)
    private String nombre;

    @Schema(description = "Descripción detallada del producto", example = "Laptop ultraportátil con procesador Intel i7")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "1299.99", required = true)
    private BigDecimal precio;

    @Schema(description = "Cantidad disponible en stock", example = "25", minimum = "0")
    private Integer stock;

    @Schema(description = "Categoría del producto", example = "ELECTRONICS",
           allowableValues = {"ELECTRONICS", "ACCESSORIES", "CLOTHING", "BOOKS", "HOME"})
    private String categoria;

    @Schema(description = "Fecha de creación del producto", example = "2023-10-15T10:30:00",
           accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Indica si el producto está disponible", example = "true",
           accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean disponible;

    @Schema(description = "Rating promedio del producto", example = "4.5", minimum = "0", maximum = "5")
    private Double rating;

    // Constructores
    public ProductDTO() {}

    public ProductDTO(Long id, String nombre, String descripcion, BigDecimal precio,
                     Integer stock, String categoria, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.fechaCreacion = fechaCreacion;
        this.disponible = stock > 0;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) {
        this.stock = stock;
        this.disponible = stock > 0;
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
