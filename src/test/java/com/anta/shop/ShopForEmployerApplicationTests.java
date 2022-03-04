package com.anta.shop;

import com.anta.shop.dao.ProductRepository;
import com.anta.shop.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ShopForEmployerApplicationTests {

    @Resource
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void findAllBaName_Product(){
        String name = "door";

        List<Product> products = productRepository.findAllByName(name);

        System.out.println(products);

    }

    @Test
    void addNewProduct_Product(){
        Product product = new Product();

        product.setName("Test");
        product.setDescription("test_Description");
        product.setPrice(BigDecimal.valueOf(56.99));

        Product newProduct = productRepository.save(product);

        System.out.println(newProduct);
    }

}
