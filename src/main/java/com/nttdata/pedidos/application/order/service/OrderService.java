package com.nttdata.pedidos.application.order.service;

// ❌ IMPORTS INCORRECTOS:
// import com.nttdata.pedidos.application.port.in.CreateOrderCommand;
// import com.nttdata.pedidos.application.port.in.CreateOrderUseCase;
// import com.nttdata.pedidos.application.port.in.GetOrderUseCase;
// import com.nttdata.pedidos.application.port.out.OrderPersistencePort;
// import com.nttdata.pedidos.application.port.out.ProductPersistencePort;

// ✅ IMPORTS CORRECTOS:
import com.nttdata.pedidos.application.order.port.in.CreateOrderCommand;
import com.nttdata.pedidos.application.order.port.in.CreateOrderUseCase;
import com.nttdata.pedidos.application.order.port.in.GetOrderUseCase;
import com.nttdata.pedidos.application.order.port.in.OrderItemCommand;
import com.nttdata.pedidos.application.order.port.out.OrderPersistencePort;
import com.nttdata.pedidos.application.product.port.out.ProductPersistencePort;
import com.nttdata.pedidos.domain.order.Order;
import com.nttdata.pedidos.domain.order.OrderItem;
import com.nttdata.pedidos.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de aplicación para la gestión de ordenes.
 * Implementa los casos de uso y orquesta las operaciones de negocio.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 * @since 2025-11-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements CreateOrderUseCase, GetOrderUseCase {

    private final OrderPersistencePort orderPersistencePort;
    private final ProductPersistencePort productPersistencePort;

    @Override
    public Order createOrder(CreateOrderCommand command) {
        log.info("Creating order for user: {}", command.userId());

        if (!command.isValid()) {
            throw new IllegalArgumentException("Invalid order command");
        }

        List<OrderItem> orderItems = validateAndBuildItems(command.items());
        BigDecimal totalAmount = calculateTotal(orderItems);

        Order order = Order.builder()
                .userId(command.userId())
                .status("PENDING")
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .items(orderItems)
                .build();

        Order savedOrder = orderPersistencePort.save(order);
        log.info("Order created successfully with ID: {}", savedOrder.getId());
        
        return savedOrder;
    }

    // ✅ CORREGIR ESTA LÍNEA:
    private List<OrderItem> validateAndBuildItems(List<OrderItemCommand> items) {
    // ❌ LINEA ORIGINAL: private List<OrderItem> validateAndBuildItems(List<com.nttdata.pedidos.application.port.in.OrderItemCommand> items) {
    
        return items.stream().map(item -> {
            Product product = productPersistencePort.findById(item.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + item.productId()));

            if (product.getStock() < item.quantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName() + 
                                          ". Available: " + product.getStock() + ", Requested: " + item.quantity());
            }

            // Update product stock
            product.setStock(product.getStock() - item.quantity());
            productPersistencePort.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(item.quantity())
                    .unitPrice(product.getPrice())
                    .build();
            
            orderItem.calculateSubtotal();
            return orderItem;
        }).toList();
    }

    private BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId, Long userId) {
        log.info("Fetching order ID: {} for user ID: {}", orderId, userId);
        return orderPersistencePort.findById(orderId)
                .filter(order -> order.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {
        log.info("Fetching all orders for user ID: {}", userId);
        return orderPersistencePort.findByUserId(userId);
    }
}