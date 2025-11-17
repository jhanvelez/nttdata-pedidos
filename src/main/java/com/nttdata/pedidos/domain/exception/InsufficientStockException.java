package com.nttdata.pedidos.domain.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String productName, Integer available, Integer requested) {
        super("Insufficient stock for product: " + productName + 
              ". Available: " + available + ", Requested: " + requested);
    }
    
    public InsufficientStockException(Long productId, Integer available, Integer requested) {
        super("Insufficient stock for product ID: " + productId + 
              ". Available: " + available + ", Requested: " + requested);
    }
}