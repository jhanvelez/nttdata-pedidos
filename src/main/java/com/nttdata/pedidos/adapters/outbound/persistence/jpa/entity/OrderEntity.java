package com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA para la persistencia de ordenes.
 */
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemEntity> items = new ArrayList<>();

    // Constructors
    public OrderEntity() {}

    public OrderEntity(Long id, Long userId, String status, BigDecimal totalAmount, 
                      LocalDateTime createdAt, LocalDateTime updatedAt, Long version, 
                      List<OrderItemEntity> items) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
        this.items = items != null ? items : new ArrayList<>();
    }

    // Builder
    public static OrderEntityBuilder builder() {
        return new OrderEntityBuilder();
    }

    // Helper methods
    public void addItem(OrderItemEntity item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItemEntity item) {
        items.remove(item);
        item.setOrder(null);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public List<OrderItemEntity> getItems() { return items; }
    public void setItems(List<OrderItemEntity> items) { this.items = items; }

    // Builder class
    public static class OrderEntityBuilder {
        private Long id;
        private Long userId;
        private String status;
        private BigDecimal totalAmount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long version;
        private List<OrderItemEntity> items;

        public OrderEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderEntityBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public OrderEntityBuilder status(String status) {
            this.status = status;
            return this;
        }

        public OrderEntityBuilder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public OrderEntityBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderEntityBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public OrderEntityBuilder version(Long version) {
            this.version = version;
            return this;
        }

        public OrderEntityBuilder items(List<OrderItemEntity> items) {
            this.items = items;
            return this;
        }

        public OrderEntity build() {
            return new OrderEntity(id, userId, status, totalAmount, createdAt, updatedAt, version, items);
        }
    }
}