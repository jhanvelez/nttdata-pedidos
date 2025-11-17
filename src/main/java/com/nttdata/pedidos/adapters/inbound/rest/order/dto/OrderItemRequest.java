package com.nttdata.pedidos.adapters.inbound.rest.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación y actualización de itenes de ordenes.
 * Incluye validaciones y documentación para Swagger.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Order item request")
public class OrderItemRequest {
    
    @NotNull(message = "Product ID is required")
    @Schema(description = "Product ID", example = "1")
    private Long productId;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Quantity of the product", example = "2")
    private Integer quantity;
}