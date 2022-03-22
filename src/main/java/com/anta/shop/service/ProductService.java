package com.anta.shop.service;

import com.anta.shop.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    /**
     * Find all Products for AdminRestController
     * @return List
     */
    List<ProductDTO> getAllProductsForAdmin();

    /**
     * Find all Products for ClientRestController
     * @return List
     */
    List<ProductDTO> getAllProductsForClient();

    /**
     * Save new Product
     * @param productDTO new Product
     * @return new Product
     */
    ProductDTO addProduct(ProductDTO productDTO);

    /**
     * Save updated Product
     * @param id Product id
     * @param productDTO necessary Product
     * @return updated Product
     */
    ProductDTO updateProduct(int id, ProductDTO productDTO);

    /**
     * Find Product by id for AdminRestController
     * @param id Product id
     * @return Product
     */
    ProductDTO getProductForAdmin(int id);

    /**
     * Find Product by id for ClientRestController
     * @param id Product id
     * @return Product
     */
    ProductDTO getProductForClient(int id);

    /**
     * Delete Product
     * @param id Product id
     * @return true or false
     */
    boolean deleteProduct(int id);

    /**
     * Find Products by name
     * @param name Product name
     * @return List
     */
    List<ProductDTO> getAllByName(String name);

    /**
     * Find Products by description
     * @param description Product description
     * @return List
     */
    List<ProductDTO> getAllByDescription(String description);
}
