package com.nttdata.pedidos.application.order.port.in;

/**
 * Command object para la creación de items de la orden.
 * Utiliza Bean Validation para validaciones de entrada.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
public record OrderItemCommand(
    Long productId,
    Integer quantity
) {
    public boolean isValid() {
        return productId != null && 
               quantity != null && 
               quantity > 0;
    }
}