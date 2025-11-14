package com.nttdata.pedidos.adapters.outbound.persistence.jpa.mapper;

import com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity.ProductEntity;
import com.nttdata.pedidos.domain.product.Product;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Product (dominio) y ProductEntity (persistencia).
 * Sigue el principio de responsabilidad única.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Component
public class ProductEntityMapper {

    /**
     * Convierte ProductEntity a Product (dominio).
     * 
     * @param entity entidad de persistencia
     * @return objeto de dominio
     */
    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Convierte Product (dominio) a ProductEntity.
     * 
     * @param domain objeto de dominio
     * @return entidad de persistencia
     */
    public ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }

        return ProductEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .stock(domain.getStock())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}