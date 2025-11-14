package com.nttdata.pedidos.application.product.port.in;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * Command object para la actualización de stock.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Builder
public record UpdateProductStockCommand(
    @NotNull(message = "Product ID is required")
    Long productId,
    
    @NotNull(message = "Stock is required")
    @DecimalMin(value = "0", message = "Stock cannot be negative")
    Integer stock
) {}