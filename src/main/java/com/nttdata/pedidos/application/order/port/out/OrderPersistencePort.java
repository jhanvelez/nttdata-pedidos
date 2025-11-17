package com.nttdata.pedidos.application.order.port.out;

import com.nttdata.pedidos.domain.order.Order;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para operaciones de persistencia de ordenes.
 * Define el contrato que debe implementar el adaptador de persistencia.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
public interface OrderPersistencePort {
    Order save(Order order);
    Optional<Order> findById(Long orderId);
    List<Order> findByUserId(Long userId);
    boolean existsByIdAndUserId(Long orderId, Long userId);
}