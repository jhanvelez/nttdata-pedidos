package com.nttdata.pedidos.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un Producto en el sistema.
 */
public class Product {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor vacío
    public Product() {}

    // Constructor con todos los campos
    public Product(Long id, String name, String description, BigDecimal price, 
                  Integer stock, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Builder static method
    public static ProductBuilder builder() {
        return new ProductBuilder();
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

    /**
     * Factory method para crear un nuevo producto.
     */
    public static Product create(String name, String description, BigDecimal price, Integer stock) {
        validateCreationParameters(name, description, price, stock);
        
        return Product.builder()
                .name(name.trim())
                .description(description.trim())
                .price(price)
                .stock(stock)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Actualiza la información del producto.
     */
    public void update(String name, String description, BigDecimal price) {
        this.name = name != null ? name.trim() : this.name;
        this.description = description != null ? description.trim() : this.description;
        this.price = price != null ? price : this.price;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Actualiza el stock del producto.
     */
    public void updateStock(Integer newStock) {
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = newStock;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Reduce el stock del producto cuando se realiza un pedido.
     */
    public void reduceStock(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (this.stock < quantity) {
            throw new IllegalStateException(
                String.format("Insufficient stock. Available: %d, Requested: %d", this.stock, quantity)
            );
        }
        this.stock -= quantity;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Verifica si el producto tiene stock disponible.
     */
    public boolean hasStock(Integer quantity) {
        return this.stock >= quantity && quantity > 0;
    }

    /**
     * Desactiva el producto (soft delete).
     */
    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Valida los parámetros de creación del producto.
     */
    private static void validateCreationParameters(String name, String description, BigDecimal price, Integer stock) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be null or empty");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
    }

    // Builder class
    public static class ProductBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private Boolean active;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public ProductBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public ProductBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Product build() {
            return new Product(id, name, description, price, stock, active, createdAt, updatedAt);
        }
    }
}