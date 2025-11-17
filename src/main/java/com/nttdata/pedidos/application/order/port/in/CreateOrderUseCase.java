package com.nttdata.pedidos.application.order.port.in;
import com.nttdata.pedidos.domain.order.Order;

/**
 * Command object para la creación de ordenes.
 * Utiliza Bean Validation para validaciones de entrada.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
public interface CreateOrderUseCase {
    Order createOrder(CreateOrderCommand command);
}