package com.nttdata.pedidos.adapters.outbound.persistence;

import com.nttdata.pedidos.adapters.outbound.persistence.jpa.entity.OrderEntity;
import com.nttdata.pedidos.adapters.outbound.persistence.jpa.mapper.OrderEntityMapper;
import com.nttdata.pedidos.adapters.outbound.persistence.jpa.repository.SpringDataOrderRepository;
import com.nttdata.pedidos.application.order.port.out.OrderPersistencePort;
import com.nttdata.pedidos.domain.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia para Ordenes.
 * Implementa el puerto de salida usando JPA y Spring Data.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {

    private final SpringDataOrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Order save(Order order) {
        log.debug("Saving order for user: {}", order.getUserId());
        
        OrderEntity entity = orderEntityMapper.toEntity(order);
        OrderEntity savedEntity = orderRepository.save(entity);
        
        log.debug("Order saved successfully with ID: {}", savedEntity.getId());
        return orderEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        log.debug("Finding order by ID: {}", orderId);
        
        return orderRepository.findById(orderId)
                .map(orderEntityMapper::toDomain);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        log.debug("Finding orders for user ID: {}", userId);
        
        return orderRepository.findByUserId(userId).stream()
                .map(orderEntityMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByIdAndUserId(Long orderId, Long userId) {
        log.debug("Checking if order ID: {} exists for user ID: {}", orderId, userId);
        
        return orderRepository.existsByIdAndUserId(orderId, userId);
    }
}
