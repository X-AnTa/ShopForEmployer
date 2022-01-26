package com.anta.shop.service;

import com.anta.shop.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO updateProduct(int id, ProductDTO productDTO);

    ProductDTO getProduct(int id) throws Exception;

    boolean deleteProduct(int id);


}
