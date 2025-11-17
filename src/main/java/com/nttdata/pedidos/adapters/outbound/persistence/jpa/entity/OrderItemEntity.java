package com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la persistencia de items de orden.
 * Representa cada producto individual dentro de una orden.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 * @since 2025-11-14
 */
@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    // Constructors
    public OrderItemEntity() {}

    public OrderItemEntity(Long id, OrderEntity order, Long productId, String productName, 
                          Integer quantity, BigDecimal unitPrice, BigDecimal subtotal,
                          LocalDateTime createdAt, LocalDateTime updatedAt, Long version) {
        this.id = id;
        this.order = order;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
        
        // Ensure subtotal is calculated if not provided
        if (this.subtotal == null) {
            calculateSubtotal();
        }
    }

    // Builder
    public static OrderItemEntityBuilder builder() {
        return new OrderItemEntityBuilder();
    }

    /**
     * Calcula el subtotal automáticamente antes de persistir o actualizar.
     * El subtotal es: unitPrice * quantity
     */
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (unitPrice != null && quantity != null && quantity > 0) {
            this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }

    /**
     * Valida que el item de orden sea válido.
     * 
     * @return true si el item tiene todos los campos requeridos
     */
    public boolean isValid() {
        return productId != null && 
               productName != null && 
               !productName.trim().isEmpty() &&
               quantity != null && 
               quantity > 0 && 
               unitPrice != null &&
               unitPrice.compareTo(BigDecimal.ZERO) >= 0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity; 
        calculateSubtotal(); // Recalculate subtotal when quantity changes
    }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { 
        this.unitPrice = unitPrice; 
        calculateSubtotal(); // Recalculate subtotal when unit price changes
    }

    public BigDecimal getSubtotal() { 
        if (subtotal == null) {
            calculateSubtotal();
        }
        return subtotal; 
    }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    // Builder class
    public static class OrderItemEntityBuilder {
        private Long id;
        private OrderEntity order;
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long version;

        public OrderItemEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderItemEntityBuilder order(OrderEntity order) {
            this.order = order;
            return this;
        }

        public OrderItemEntityBuilder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemEntityBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public OrderItemEntityBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemEntityBuilder unitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public OrderItemEntityBuilder subtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public OrderItemEntityBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderItemEntityBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public OrderItemEntityBuilder version(Long version) {
            this.version = version;
            return this;
        }

        public OrderItemEntity build() {
            OrderItemEntity entity = new OrderItemEntity(id, order, productId, productName, 
                    quantity, unitPrice, subtotal, createdAt, updatedAt, version);
            
            // Ensure subtotal is calculated
            if (entity.subtotal == null) {
                entity.calculateSubtotal();
            }
            
            return entity;
        }
    }
}