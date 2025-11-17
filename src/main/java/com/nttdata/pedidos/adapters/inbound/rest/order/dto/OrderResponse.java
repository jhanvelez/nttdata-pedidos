package com.nttdata.pedidos.adapters.inbound.rest.order.dto;

import com.nttdata.pedidos.domain.order.Order;
import com.nttdata.pedidos.domain.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para la respuesta de ordenes.
 * Expone solo los datos necesarios al cliente.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public static OrderResponse fromDomain(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());
        
        if (order.getItems() != null) {
            response.setItems(order.getItems().stream()
                    .map(OrderItemResponse::fromDomain)
                    .collect(Collectors.toList()));
        }
        
        return response;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;

        public static OrderItemResponse fromDomain(OrderItem item) {
            if (item == null) {
                return null;
            }

            OrderItemResponse response = new OrderItemResponse();
            response.setProductId(item.getProductId());
            response.setProductName(item.getProductName());
            response.setQuantity(item.getQuantity());
            response.setUnitPrice(item.getUnitPrice());
            response.setSubtotal(item.getSubtotal());
            return response;
        }
    }
}