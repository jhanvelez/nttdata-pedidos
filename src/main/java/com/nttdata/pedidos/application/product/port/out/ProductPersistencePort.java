package com.nttdata.pedidos.application.product.port.out;

import com.nttdata.pedidos.domain.product.Product;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para operaciones de persistencia de productos.
 * Define el contrato que debe implementar el adaptador de persistencia.
 * 
 * @author Jhan Robert Velez
 * @version 1.0
 */
public interface ProductPersistencePort {
    
    /**
     * Guarda un producto en la base de datos.
     * 
     * @param product producto a guardar
     * @return el producto guardado
     */
    Product save(Product product);
    
    /**
     * Busca un producto por su ID.
     * 
     * @param id identificador del producto
     * @return Optional con el producto si existe
     */
    Optional<Product> findById(Long id);
    
    /**
     * Obtiene todos los productos activos.
     * 
     * @return lista de productos activos
     */
    List<Product> findAllActive();
    
    /**
     * Verifica si existe un producto con el nombre especificado.
     * 
     * @param name nombre del producto
     * @return true si existe un producto con ese nombre
     */
    boolean existsByName(String name);
    
    /**
     * Busca productos por IDs.
     * 
     * @param ids lista de IDs de productos
     * @return lista de productos encontrados
     */
    List<Product> findAllByIdIn(List<Long> ids);
}