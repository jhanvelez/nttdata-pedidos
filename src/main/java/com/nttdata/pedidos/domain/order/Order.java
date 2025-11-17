package com.nttdata.pedidos.domain.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItem> items;
    
    public void calculateTotal() {
        if (items != null) {
            this.totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
    
    public boolean isPending() {
        return "PENDING".equals(status);
    }
    
    public boolean belongsToUser(Long userId) {
        return this.userId != null && this.userId.equals(userId);
    }
}