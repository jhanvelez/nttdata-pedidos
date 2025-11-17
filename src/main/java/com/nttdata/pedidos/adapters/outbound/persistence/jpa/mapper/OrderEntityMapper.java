package com.nttdata.pedidos.adapters.outbound.persistence.jpa.mapper;

import com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity.OrderEntity;
import com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity.OrderItemEntity;
import com.nttdata.pedidos.domain.order.Order;
import com.nttdata.pedidos.domain.order.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
/**
 * Mapper para convertir entre Product (dominio) y ProductEntity (persistencia).
 * Sigue el principio de responsabilidad única.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Component
public class OrderEntityMapper {
  public Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        return Order.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .status(entity.getStatus())
                .totalAmount(entity.getTotalAmount())
                .createdAt(entity.getCreatedAt())
                .items(entity.getItems().stream()
                        .map(this::toDomainItem)
                        .collect(Collectors.toList()))
                .build();
    }

    public OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }

        OrderEntity entity = OrderEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .status(domain.getStatus())
                .totalAmount(domain.getTotalAmount())
                .createdAt(domain.getCreatedAt())
                .build();

        // Map items and set bidirectional relationship
        if (domain.getItems() != null) {
            domain.getItems().forEach(item -> {
                OrderItemEntity itemEntity = toEntityItem(item);
                entity.addItem(itemEntity);
            });
        }

        return entity;
    }

    private OrderItem toDomainItem(OrderItemEntity entity) {
        return OrderItem.builder()
                .productId(entity.getProductId())
                .productName(entity.getProductName())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .subtotal(entity.getSubtotal())
                .build();
    }

    private OrderItemEntity toEntityItem(OrderItem domain) {
        return OrderItemEntity.builder()
                .productId(domain.getProductId())
                .productName(domain.getProductName())
                .quantity(domain.getQuantity())
                .unitPrice(domain.getUnitPrice())
                .subtotal(domain.getSubtotal())
                .build();
    }  
}
