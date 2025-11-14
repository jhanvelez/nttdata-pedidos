package com.nttdata.pedidos.adapters.inbound.rest.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para la creación y actualización de productos.
 * Incluye validaciones y documentación para Swagger.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for product operations")
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Schema(description = "Product name", example = "Laptop Gaming Pro", required = true)
    private String name;

    @NotBlank(message = "Product description is required")
    @Size(min = 10, max = 500, message = "Product description must be between 10 and 500 characters")
    @Schema(description = "Product description", example = "High-performance gaming laptop with RTX 4080", required = true)
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.01", message = "Product price must be greater than 0")
    @Schema(description = "Product price", example = "1299.99", required = true)
    private BigDecimal price;

    @NotNull(message = "Product stock is required")
    @DecimalMin(value = "0", message = "Product stock cannot be negative")
    @Schema(description = "Product stock quantity", example = "50", required = true)
    private Integer stock;
}