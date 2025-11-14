package com.nttdata.pedidos.application.product.port.in;

import com.nttdata.pedidos.domain.product.Product;

/**
 * Puerto de entrada para la actualización de productos.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
public interface UpdateProductUseCase {
    
    /**
     * Actualiza la información de un producto existente.
     * 
     * @param command comando con los datos a actualizar
     * @return el producto actualizado
     * @throws IllegalArgumentException si el producto no existe o los datos son inválidos
     */
    Product updateProduct(UpdateProductCommand command);
    
    /**
     * Actualiza el stock de un producto.
     * 
     * @param command comando con el nuevo stock
     * @return el producto actualizado
     * @throws IllegalArgumentException si el producto no existe o el stock es inválido
     */
    Product updateProductStock(UpdateProductStockCommand command);
}