package com.nttdata.pedidos.adapters.inbound.rest.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de productos.
 * Expone solo los datos necesarios al cliente.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product response data")
public class ProductResponse {

    @Schema(description = "Product ID", example = "1")
    private Long id;

    @Schema(description = "Product name", example = "Laptop Gaming Pro")
    private String name;

    @Schema(description = "Product description", example = "High-performance gaming laptop with RTX 4080")
    private String description;

    @Schema(description = "Product price", example = "1299.99")
    private BigDecimal price;

    @Schema(description = "Product stock quantity", example = "50")
    private Integer stock;

    @Schema(description = "Product active status", example = "true")
    private Boolean active;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}