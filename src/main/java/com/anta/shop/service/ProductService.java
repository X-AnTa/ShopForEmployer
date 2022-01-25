package com.anta.shop.service;

import com.anta.shop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product addProduct(Product product);

    Product updateProduct(Product product);

    Product getProduct(int id) throws Exception;

    boolean deleteProduct(int id);


}
