package com.nttdata.pedidos.domain.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("Order not found with ID: " + orderId);
    }
    
    public OrderNotFoundException(Long orderId, Long userId) {
        super("Order not found with ID: " + orderId + " for user ID: " + userId);
    }
}