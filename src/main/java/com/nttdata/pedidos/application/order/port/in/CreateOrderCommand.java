package com.nttdata.pedidos.application.order.port.in;

/**
 * Command object para la creación de ordenes.
 * Utiliza Bean Validation para validaciones de entrada.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
import java.util.List;

public record CreateOrderCommand(
    Long userId,
    List<OrderItemCommand> items
) {
    public boolean isValid() {
        return userId != null && 
               items != null && 
               !items.isEmpty() &&
               items.stream().allMatch(OrderItemCommand::isValid);
    }
}