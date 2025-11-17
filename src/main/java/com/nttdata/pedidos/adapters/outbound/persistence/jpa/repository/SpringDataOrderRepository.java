package com.nttdata.pedidos.adapters.outbound.persistence.jpa.repository;

import com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository Spring Data JPA para la entidad OrderEntity.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Repository
public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserId(Long userId);
    Optional<OrderEntity> findByIdAndUserId(Long id, Long userId);
    boolean existsByIdAndUserId(Long id, Long userId);
}
