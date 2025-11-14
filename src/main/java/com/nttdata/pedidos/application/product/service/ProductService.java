package com.nttdata.pedidos.application.product.service;

import com.nttdata.pedidos.application.product.port.in.CreateProductUseCase;
import com.nttdata.pedidos.application.product.port.in.GetProductsUseCase;
import com.nttdata.pedidos.application.product.port.in.UpdateProductUseCase;
import com.nttdata.pedidos.application.product.port.in.CreateProductCommand;
import com.nttdata.pedidos.application.product.port.in.UpdateProductCommand;
import com.nttdata.pedidos.application.product.port.in.UpdateProductStockCommand;
import com.nttdata.pedidos.application.product.port.out.ProductPersistencePort;
import com.nttdata.pedidos.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para la gestión de productos.
 * Implementa los casos de uso y orquesta las operaciones de negocio.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 * @since 2025-11-14
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements CreateProductUseCase, GetProductsUseCase, UpdateProductUseCase {

    private final ProductPersistencePort productPersistencePort;

    /**
     * {@inheritDoc}
     */
    @Override
    public Product createProduct(CreateProductCommand command) {
        log.info("Creating new product with name: {}", command.name());
        
        // Validar unicidad del nombre
        if (productPersistencePort.existsByName(command.name())) {
            log.warn("Attempt to create product with existing name: {}", command.name());
            throw new IllegalArgumentException("Product with name '" + command.name() + "' already exists");
        }

        // Crear producto usando factory method del dominio
        Product product = Product.create(
            command.name(),
            command.description(),
            command.price(),
            command.stock()
        );

        // Persistir producto
        Product savedProduct = productPersistencePort.save(product);
        log.info("Product created successfully with ID: {}", savedProduct.getId());
        
        return savedProduct;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllActiveProducts() {
        log.debug("Retrieving all active products");
        return productPersistencePort.findAllActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        log.debug("Retrieving product by ID: {}", id);
        return productPersistencePort.findById(id)
                .filter(product -> Boolean.TRUE.equals(product.getActive()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        log.debug("Checking existence of product with name: {}", name);
        return productPersistencePort.existsByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product updateProduct(UpdateProductCommand command) {
        log.info("Updating product with ID: {}", command.productId());
        
        // Buscar producto existente
        Product existingProduct = productPersistencePort.findById(command.productId())
                .orElseThrow(() -> {
                    log.warn("Product not found for update with ID: {}", command.productId());
                    return new IllegalArgumentException("Product not found with ID: " + command.productId());
                });

        // Validar que el producto esté activo
        if (!existingProduct.getActive()) {
            log.warn("Attempt to update inactive product with ID: {}", command.productId());
            throw new IllegalStateException("Cannot update inactive product");
        }

        // Validar unicidad del nombre si se está cambiando
        if (command.name() != null && !command.name().equals(existingProduct.getName())) {
            if (productPersistencePort.existsByName(command.name())) {
                log.warn("Attempt to update product to existing name: {}", command.name());
                throw new IllegalArgumentException("Product with name '" + command.name() + "' already exists");
            }
        }

        // Actualizar producto usando método del dominio
        existingProduct.update(command.name(), command.description(), command.price());
        
        // Persistir cambios
        Product updatedProduct = productPersistencePort.save(existingProduct);
        log.info("Product updated successfully with ID: {}", updatedProduct.getId());
        
        return updatedProduct;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product updateProductStock(UpdateProductStockCommand command) {
        log.info("Updating stock for product with ID: {}", command.productId());
        
        // Buscar producto existente
        Product existingProduct = productPersistencePort.findById(command.productId())
                .orElseThrow(() -> {
                    log.warn("Product not found for stock update with ID: {}", command.productId());
                    return new IllegalArgumentException("Product not found with ID: " + command.productId());
                });

        // Validar que el producto esté activo
        if (!existingProduct.getActive()) {
            log.warn("Attempt to update stock of inactive product with ID: {}", command.productId());
            throw new IllegalStateException("Cannot update stock of inactive product");
        }

        // Actualizar stock usando método del dominio
        existingProduct.updateStock(command.stock());
        
        // Persistir cambios
        Product updatedProduct = productPersistencePort.save(existingProduct);
        log.info("Stock updated successfully for product ID: {}, new stock: {}", 
                 updatedProduct.getId(), updatedProduct.getStock());
        
        return updatedProduct;
    }

    /**
     * Reduce el stock de múltiples productos (para uso en creación de pedidos).
     * 
     * @param productQuantities mapa de ID de producto a cantidad a reducir
     * @throws IllegalStateException si algún producto no tiene stock suficiente
     */
    @Transactional
    public void reduceProductsStock(java.util.Map<Long, Integer> productQuantities) {
        log.info("Reducing stock for {} products", productQuantities.size());
        
        // Obtener todos los productos involucrados
        List<Product> products = productPersistencePort.findAllByIdIn(
            new java.util.ArrayList<>(productQuantities.keySet())
        );

        // Validar stock y preparar reducciones
        for (Product product : products) {
            Integer quantity = productQuantities.get(product.getId());
            if (!product.hasStock(quantity)) {
                log.error("Insufficient stock for product ID: {}, available: {}, requested: {}", 
                         product.getId(), product.getStock(), quantity);
                throw new IllegalStateException(
                    "Insufficient stock for product: " + product.getName()
                );
            }
        }

        // Aplicar reducciones de stock
        for (Product product : products) {
            Integer quantity = productQuantities.get(product.getId());
            product.reduceStock(quantity);
            productPersistencePort.save(product);
            log.debug("Reduced stock for product ID: {} by {}, remaining: {}", 
                     product.getId(), quantity, product.getStock());
        }
        
        log.info("Stock reduction completed successfully for {} products", products.size());
    }
}