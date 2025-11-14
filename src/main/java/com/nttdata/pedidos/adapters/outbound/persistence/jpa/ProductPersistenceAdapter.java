package com.nttdata.pedidos.adapters.outbound.persistence.jpa;

import com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity.ProductEntity;
import com.nttdata.pedidos.adapters.outbound.persistence.jpa.mapper.ProductEntityMapper;
import com.nttdata.pedidos.adapters.outbound.persistence.jpa.repository.SpringDataProductRepository;
import com.nttdata.pedidos.application.product.port.out.ProductPersistencePort;
import com.nttdata.pedidos.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia para productos.
 * Implementa el puerto de salida usando JPA y Spring Data.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final SpringDataProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Product save(Product product) {
        log.debug("Saving product: {}", product.getName());
        ProductEntity entity = productEntityMapper.toEntity(product);
        ProductEntity savedEntity = productRepository.save(entity);
        return productEntityMapper.toDomain(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> findById(Long id) {
        log.debug("Finding product by ID: {}", id);
        return productRepository.findById(id)
                .map(productEntityMapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAllActive() {
        log.debug("Finding all active products");
        return productRepository.findByActiveTrue().stream()
                .map(productEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByName(String name) {
        log.debug("Checking if product exists by name: {}", name);
        return productRepository.existsByNameIgnoreCase(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAllByIdIn(List<Long> ids) {
        log.debug("Finding products by IDs: {}", ids);
        return productRepository.findActiveByIdIn(ids).stream()
                .map(productEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}