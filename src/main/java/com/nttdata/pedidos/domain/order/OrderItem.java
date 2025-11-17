package com.nttdata.pedidos.domain.order;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    
    public void calculateSubtotal() {
        if (unitPrice != null && quantity != null) {
            this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
    
    public boolean isValid() {
        return productId != null && 
               quantity != null && 
               quantity > 0 && 
               unitPrice != null &&
               unitPrice.compareTo(BigDecimal.ZERO) >= 0;
    }
}