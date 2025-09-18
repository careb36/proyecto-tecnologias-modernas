package uy.bcu.product;

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
// DTOs are in the same package

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para el microservicio de productos
 * Gestiona el catálogo completo de productos con documentación Swagger
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API para gestión del catálogo de productos")
public class ProductController {

    @GetMapping
    @Operation(
        summary = "Obtener catálogo de productos",
        description = "Retorna una lista paginada de todos los productos disponibles en el catálogo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Catálogo obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ProductDTO>> obtenerProductos(
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Categoría de productos") @RequestParam(required = false) String categoria) {

        // Datos de ejemplo para demostrar la API
        List<ProductDTO> productos = Arrays.asList(
            new ProductDTO(1L, "Laptop Dell Inspiron", "Laptop para desarrollo",
                          new BigDecimal("1299.99"), 25, "ELECTRONICS", LocalDateTime.now()),
            new ProductDTO(2L, "Mouse Logitech MX", "Mouse ergonómico inalámbrico",
                          new BigDecimal("89.99"), 50, "ACCESSORIES", LocalDateTime.now()),
            new ProductDTO(3L, "Teclado Mecánico RGB", "Teclado para gaming",
                          new BigDecimal("159.99"), 15, "ACCESSORIES", LocalDateTime.now())
        );

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener producto por ID",
        description = "Retorna los detalles completos de un producto específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductDTO> obtenerProductoPorId(@PathVariable Long id) {
        ProductDTO producto = new ProductDTO(id, "Producto " + id, "Descripción del producto",
                                           new BigDecimal("99.99"), 10, "GENERAL", LocalDateTime.now());
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo producto",
        description = "Agrega un nuevo producto al catálogo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ProductDTO> crearProducto(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        ProductDTO producto = new ProductDTO(999L, productCreateDTO.getNombre(),
                                           productCreateDTO.getDescripcion(), productCreateDTO.getPrecio(),
                                           productCreateDTO.getStock(), productCreateDTO.getCategoria(),
                                           LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}/stock")
    @Operation(
        summary = "Actualizar stock de producto",
        description = "Actualiza la cantidad disponible de un producto específico"
    )
    public ResponseEntity<Map<String, Object>> actualizarStock(
            @PathVariable Long id,
            @Parameter(description = "Nueva cantidad de stock") @RequestParam int cantidad) {

        return ResponseEntity.ok(Map.of(
            "productId", id,
            "nuevoStock", cantidad,
            "timestamp", LocalDateTime.now()
        ));
    }

    @GetMapping("/categorias")
    @Operation(
        summary = "Obtener categorías disponibles",
        description = "Lista todas las categorías de productos disponibles"
    )
    public ResponseEntity<List<String>> obtenerCategorias() {
        List<String> categorias = Arrays.asList("ELECTRONICS", "ACCESSORIES", "CLOTHING", "BOOKS", "HOME");
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/buscar")
    @Operation(
        summary = "Buscar productos",
        description = "Búsqueda avanzada de productos por diferentes criterios"
    )
    public ResponseEntity<List<ProductDTO>> buscarProductos(
            @Parameter(description = "Texto a buscar en nombre o descripción") @RequestParam(required = false) String q,
            @Parameter(description = "Precio mínimo") @RequestParam(required = false) BigDecimal precioMin,
            @Parameter(description = "Precio máximo") @RequestParam(required = false) BigDecimal precioMax,
            @Parameter(description = "Solo productos con stock") @RequestParam(defaultValue = "false") boolean conStock) {

        List<ProductDTO> productos = Arrays.asList(
            new ProductDTO(1L, "Laptop Gaming", "Laptop de alto rendimiento",
                          new BigDecimal("1899.99"), 5, "ELECTRONICS", LocalDateTime.now())
        );

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/estadisticas")
    @Operation(
        summary = "Estadísticas del catálogo",
        description = "Métricas y estadísticas del catálogo de productos"
    )
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        return ResponseEntity.ok(Map.of(
            "totalProductos", 156,
            "productosConStock", 143,
            "productosSinStock", 13,
            "valorTotalInventario", new BigDecimal("125430.50"),
            "categorias", 8,
            "fechaActualizacion", LocalDateTime.now()
        ));
    }
}
