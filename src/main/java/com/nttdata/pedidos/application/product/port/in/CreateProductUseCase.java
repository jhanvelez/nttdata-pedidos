package com.nttdata.pedidos.application.product.port.in;

import com.nttdata.pedidos.domain.product.Product;

/**
 * Puerto de entrada para la creación de productos.
 * Sigue el principio de segregación de interfaces (SOLID).
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
public interface CreateProductUseCase {
    
    /**
     * Crea un nuevo producto en el sistema.
     * 
     * @param command comando con los datos del producto
     * @return el producto creado
     * @throws IllegalArgumentException si los datos no son válidos
     * @throws IllegalStateException si ya existe un producto con el mismo nombre
     */
    Product createProduct(CreateProductCommand command);
}