package com.nttdata.pedidos.adapters.inbound.rest.product;

import com.nttdata.pedidos.adapters.inbound.rest.product.dto.ProductRequest;
import com.nttdata.pedidos.adapters.inbound.rest.product.dto.ProductResponse;
import com.nttdata.pedidos.adapters.inbound.rest.product.dto.UpdateStockRequest;
import com.nttdata.pedidos.adapters.inbound.rest.product.mapper.ProductRestMapper;
import com.nttdata.pedidos.application.product.port.in.CreateProductUseCase;
import com.nttdata.pedidos.application.product.port.in.GetProductsUseCase;
import com.nttdata.pedidos.application.product.port.in.UpdateProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para la gestión de productos.
 * Expone endpoints siguiendo las mejores prácticas RESTful.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 * @since 2025-11-14
 */
@Slf4j
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "APIs for managing products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductsUseCase getProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final ProductRestMapper productRestMapper;

    /**
     * Crea un nuevo producto (solo administradores).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product", description = "Create a new product in the system. Admin role required.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Product with same name already exists"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Admin role required")
    })
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Received request to create product: {}", request.getName());
        
        var command = productRestMapper.toCreateCommand(request);
        var product = createProductUseCase.createProduct(command);
        var response = productRestMapper.toResponse(product);
        
        log.info("Product created successfully with ID: {}", product.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtiene todos los productos activos.
     */
    @GetMapping
    @Operation(summary = "Get all active products", description = "Retrieve a list of all active products")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
        @ApiResponse(responseCode = "204", description = "No products found")
    })
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.debug("Received request to get all active products");
        
        var products = getProductsUseCase.getAllActiveProducts();
        
        if (products.isEmpty()) {
            log.debug("No active products found");
            return ResponseEntity.noContent().build();
        }
        
        var response = products.stream()
                .map(productRestMapper::toResponse)
                .collect(Collectors.toList());
        
        log.debug("Returning {} active products", response.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un producto por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        log.debug("Received request to get product by ID: {}", id);
        
        var product = getProductsUseCase.getProductById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new IllegalArgumentException("Product not found with ID: " + id);
                });
        
        var response = productRestMapper.toResponse(product);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un producto existente (solo administradores).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a product", description = "Update an existing product. Admin role required.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "409", description = "Product with same name already exists"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Admin role required")
    })
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id, 
            @Valid @RequestBody ProductRequest request) {
        log.info("Received request to update product with ID: {}", id);
        
        var command = productRestMapper.toUpdateCommand(id, request);
        var product = updateProductUseCase.updateProduct(command);
        var response = productRestMapper.toResponse(product);
        
        log.info("Product updated successfully with ID: {}", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza el stock de un producto (solo administradores).
     */
    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product stock", description = "Update the stock quantity of a product. Admin role required.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Stock updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid stock value"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Admin role required")
    })
    public ResponseEntity<ProductResponse> updateProductStock(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStockRequest request) {
        log.info("Received request to update stock for product ID: {}, new stock: {}", id, request.getStock());
        
        var command = productRestMapper.toUpdateStockCommand(id, request.getStock());
        var product = updateProductUseCase.updateProductStock(command);
        var response = productRestMapper.toResponse(product);
        
        log.info("Stock updated successfully for product ID: {}", id);
        return ResponseEntity.ok(response);
    }
}