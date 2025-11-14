package com.nttdata.pedidos.adapters.outbound.persistence.jpa.repository;

import com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA para la entidad ProductEntity.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Encuentra un producto por nombre (case-insensitive).
     * 
     * @param name nombre del producto
     * @return Optional con el producto si existe
     */
    Optional<ProductEntity> findByNameIgnoreCase(String name);

    /**
     * Verifica si existe un producto con el nombre especificado (case-insensitive).
     * 
     * @param name nombre del producto
     * @return true si existe un producto con ese nombre
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Encuentra todos los productos activos.
     * 
     * @return lista de productos activos
     */
    List<ProductEntity> findByActiveTrue();

    /**
     * Encuentra productos por IDs y que estén activos.
     * 
     * @param ids lista de IDs
     * @return lista de productos activos con los IDs especificados
     */
    @Query("SELECT p FROM ProductEntity p WHERE p.id IN :ids AND p.active = true")
    List<ProductEntity> findActiveByIdIn(List<Long> ids);
}