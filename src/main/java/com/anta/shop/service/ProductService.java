package com.anta.shop.service;

import com.anta.shop.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProductsForAdmin();

    List<ProductDTO> getAllProductsForClient();

    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO updateProduct(int id, ProductDTO productDTO);

    ProductDTO getProductForAdmin(int id);

    ProductDTO getProductForClient(int id);

    boolean deleteProduct(int id);

    List<ProductDTO> getAllByName(String name);

    List<ProductDTO> getAllByDescription(String description);


}
