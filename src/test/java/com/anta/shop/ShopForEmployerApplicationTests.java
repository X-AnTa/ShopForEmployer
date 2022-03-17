package com.anta.shop;

import com.anta.shop.repository.ProductRepository;
import com.anta.shop.entity.Currency;
import com.anta.shop.entity.Description;
import com.anta.shop.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class ShopForEmployerApplicationTests {

    @Resource
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void findAllByName_Product(){
        String name = "door";

        List<Product> products = productRepository.findAllByName(name);

        System.out.println(products);

    }

    @Test
    void addNewProduct_Product(){
        Product product1 = new Product();
        Currency currency1 = new Currency("RUB");
        Description description1 = new Description("RU");

        product1.setName("стол");
        product1.setDescription("мебель");
        product1.setPrice(BigDecimal.valueOf(2300));
        product1.setCurrencies(Collections.singleton(currency1));
        product1.setDescriptions(Collections.singleton(description1));

        Product newProduct1 = productRepository.save(product1);

        Product product2 = new Product();
        Currency currency2 = new Currency("USD");
        Description description2 = new Description("EN");

        product2.setName("chair");
        product2.setDescription("furniture");
        product2.setPrice(BigDecimal.valueOf(49.99));
        product2.setCurrencies(Collections.singleton(currency2));
        product2.setDescriptions(Collections.singleton(description2));

        Product newProduct2 = productRepository.save(product2);

        Product product3 = new Product();
        Currency currency3 = new Currency("USD");
        Description description3 = new Description("EN");

        product3.setName("sofa");
        product3.setDescription("furniture");
        product3.setPrice(BigDecimal.valueOf(79.69));
        product3.setCurrencies(Collections.singleton(currency3));
        product3.setDescriptions(Collections.singleton(description3));

        Product newProduct3 = productRepository.save(product3);

        System.out.println(newProduct1);
        System.out.println(newProduct2);
        System.out.println(newProduct3);
    }

}
