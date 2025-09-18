package uy.bcu.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.bcu.product.dto.ProductDTO;
import uy.bcu.product.dto.ProductCreateDTO;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductController {

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public ResponseEntity<List<ProductDTO>> obtenerTodosLosProductos() {
        List<ProductDTO> productos = Arrays.asList(
            new ProductDTO(1L, "Laptop Dell", "Laptop de 15 pulgadas", 999.99, 50),
            new ProductDTO(2L, "Mouse Logitech", "Mouse inalámbrico", 29.99, 100)
        );
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto")
    public ResponseEntity<ProductDTO> crearProducto(@Valid @RequestBody ProductCreateDTO dto) {
        ProductDTO producto = new ProductDTO(999L, dto.getNombre(), dto.getDescripcion(), dto.getPrecio(), dto.getStock());
        return ResponseEntity.ok(producto);
    }
}
