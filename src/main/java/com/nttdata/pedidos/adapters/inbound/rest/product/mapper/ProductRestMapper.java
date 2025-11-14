package com.nttdata.pedidos.adapters.inbound.rest.product.mapper;

import com.nttdata.pedidos.adapters.inbound.rest.product.dto.ProductRequest;
import com.nttdata.pedidos.adapters.inbound.rest.product.dto.ProductResponse;
import com.nttdata.pedidos.application.product.port.in.CreateProductCommand;
import com.nttdata.pedidos.application.product.port.in.UpdateProductCommand;
import com.nttdata.pedidos.application.product.port.in.UpdateProductStockCommand;
import com.nttdata.pedidos.domain.product.Product;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversiones entre DTOs, Commands y Entidades de dominio.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Component
public class ProductRestMapper {

    /**
     * Convierte ProductRequest a CreateProductCommand.
     */
    public CreateProductCommand toCreateCommand(ProductRequest request) {
        return CreateProductCommand.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();
    }

    /**
     * Convierte ProductRequest y ID a UpdateProductCommand.
     */
    public UpdateProductCommand toUpdateCommand(Long productId, ProductRequest request) {
        return UpdateProductCommand.builder()
                .productId(productId)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    /**
     * Convierte a UpdateProductStockCommand.
     */
    public UpdateProductStockCommand toUpdateStockCommand(Long productId, Integer stock) {
        return UpdateProductStockCommand.builder()
                .productId(productId)
                .stock(stock)
                .build();
    }

    /**
     * Convierte Product (dominio) a ProductResponse.
     */
    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}