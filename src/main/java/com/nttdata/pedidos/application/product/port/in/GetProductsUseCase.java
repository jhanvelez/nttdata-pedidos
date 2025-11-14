package com.nttdata.pedidos.application.product.port.in;

import com.nttdata.pedidos.domain.product.Product;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada para consultas de productos.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
public interface GetProductsUseCase {
    
    /**
     * Obtiene todos los productos activos del sistema.
     * 
     * @return lista de productos activos
     */
    List<Product> getAllActiveProducts();
    
    /**
     * Busca un producto por su ID.
     * 
     * @param id identificador del producto
     * @return Optional con el producto si existe
     */
    Optional<Product> getProductById(Long id);
    
    /**
     * Verifica si existe un producto con el nombre especificado.
     * 
     * @param name nombre del producto
     * @return true si existe un producto con ese nombre
     */
    boolean existsByName(String name);
}