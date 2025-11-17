package com.nttdata.pedidos.application.order.port.in;

import com.nttdata.pedidos.domain.order.Order;

import java.util.List;

public interface GetOrderUseCase {
    Order getOrderById(Long orderId, Long userId);
    List<Order> getOrdersByUser(Long userId);
}