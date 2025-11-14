package com.nttdata.pedidos.application.product.port.in;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * Command object para la actualización de productos.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Builder
public record UpdateProductCommand(
    @NotNull(message = "Product ID is required")
    Long productId,
    
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    String name,
    
    @Size(min = 10, max = 500, message = "Product description must be between 10 and 500 characters")
    String description,
    
    @DecimalMin(value = "0.01", message = "Product price must be greater than 0")
    BigDecimal price
) {}