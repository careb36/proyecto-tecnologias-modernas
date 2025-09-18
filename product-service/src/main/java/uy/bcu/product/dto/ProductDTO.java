package uy.bcu.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información completa del producto")
public class ProductDTO {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Laptop Dell")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Laptop de 15 pulgadas")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "999.99")
    private Double precio;

    @Schema(description = "Stock disponible", example = "50")
    private Integer stock;

    // Constructores
    public ProductDTO() {}

    public ProductDTO(Long id, String nombre, String descripcion, Double precio, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
