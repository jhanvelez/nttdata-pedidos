package com.nttdata.pedidos.adapters.inbound.rest.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO específico para actualización de stock.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for updating product stock")
public class UpdateStockRequest {

    @NotNull(message = "Stock is required")
    @DecimalMin(value = "0", message = "Stock cannot be negative")
    @Schema(description = "New stock quantity", example = "100", required = true)
    private Integer stock;
}