package com.nttdata.pedidos.adapters.inbound.rest.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para la creación y actualización de ordenes.
 * Incluye validaciones y documentación para Swagger.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for orders operations")
public class CreateOrderRequest {
    
    @NotNull(message = "Items list cannot be null")
    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemRequest> items;
}