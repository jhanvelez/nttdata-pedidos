package com.nttdata.pedidos.adapters.inbound.rest.order;

import com.nttdata.pedidos.application.order.port.in.CreateOrderUseCase;
import com.nttdata.pedidos.application.order.port.in.GetOrderUseCase;
import com.nttdata.pedidos.application.order.port.in.CreateOrderCommand;
import com.nttdata.pedidos.application.order.port.in.OrderItemCommand;
import com.nttdata.pedidos.adapters.inbound.rest.order.dto.CreateOrderRequest;
import com.nttdata.pedidos.adapters.inbound.rest.order.dto.OrderResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller REST para la gestión de pedidos.
 * Expone endpoints siguiendo las mejores prácticas RESTful.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 * @since 2025-11-14
 */
@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs for managing orders")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;

    /**
     * Crea un nuevo pedido para el usuario autenticado.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new order", description = "Create a new order for the authenticated user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data or insufficient stock"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody @Valid CreateOrderRequest request,
            Authentication authentication) {
        
        Long userId = getUserIdFromAuthentication(authentication);
        log.info("Received request to create order for user ID: {}", userId);

        CreateOrderCommand command = new CreateOrderCommand(
            userId,
            request.getItems().stream()
                .map(item -> new OrderItemCommand(item.getProductId(), item.getQuantity()))
                .toList()
        );

        var order = createOrderUseCase.createOrder(command);
        var response = OrderResponse.fromDomain(order);
        
        log.info("Order created successfully with ID: {}", order.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtiene un pedido específico por ID para el usuario autenticado.
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by its ID for the authenticated user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Order does not belong to user")
    })
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable Long id,
            Authentication authentication) {
        
        Long userId = getUserIdFromAuthentication(authentication);
        log.debug("Received request to get order ID: {} for user ID: {}", id, userId);

        var order = getOrderUseCase.getOrderById(id, userId);
        var response = OrderResponse.fromDomain(order);
        
        log.debug("Order found with ID: {}", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todos los pedidos del usuario autenticado.
     */
    @GetMapping("/my-orders")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get user orders", description = "Retrieve all orders for the authenticated user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "204", description = "No orders found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {
        
        Long userId = getUserIdFromAuthentication(authentication);
        log.debug("Received request to get all orders for user ID: {}", userId);

        var orders = getOrderUseCase.getOrdersByUser(userId);
        
        if (orders.isEmpty()) {
            log.debug("No orders found for user ID: {}", userId);
            return ResponseEntity.noContent().build();
        }
        
        var response = orders.stream()
                .map(OrderResponse::fromDomain)
                .toList();
                
        log.debug("Returning {} orders for user ID: {}", response.size(), userId);
        return ResponseEntity.ok(response);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        // TODO: Implementar lógica para extraer userId del token JWT
        // Por ahora retornamos un ID hardcodeado para pruebas
        log.warn("Using hardcoded user ID - Implement proper user extraction from JWT");
        return 1L; // Temporal - necesitas implementar la extracción real del userId del token
    }
}