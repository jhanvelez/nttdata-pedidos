package com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la persistencia de productos.
 */
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    // Constructors
    public ProductEntity() {}

    public ProductEntity(Long id, String name, String description, BigDecimal price, 
                        Integer stock, Boolean active, LocalDateTime createdAt, 
                        LocalDateTime updatedAt, Long version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    // Builder
    public static ProductEntityBuilder builder() {
        return new ProductEntityBuilder();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    // Builder class
    public static class ProductEntityBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private Boolean active = true;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long version;

        public ProductEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductEntityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductEntityBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductEntityBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public ProductEntityBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public ProductEntityBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductEntityBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProductEntityBuilder version(Long version) {
            this.version = version;
            return this;
        }

        public ProductEntity build() {
            return new ProductEntity(id, name, description, price, stock, active, createdAt, updatedAt, version);
        }
    }
}