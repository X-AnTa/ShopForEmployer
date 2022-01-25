package com.anta.shop.service;

import com.anta.shop.dao.CurrencyRepository;
import com.anta.shop.dao.DescriptionRepository;
import com.anta.shop.dao.ProductRepository;
import com.anta.shop.entity.Product;
import com.anta.shop.exception_handling.NoSuchProductException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Resource
    private ProductRepository productRepository;

    @Resource
    private CurrencyRepository currencyRepository;

    @Resource
    private DescriptionRepository descriptionRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(int id){
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()){
            return optional.get();
        }
        else {
            throw new NoSuchProductException();
        }
    }

    @Override
    public Product addProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public boolean deleteProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        else {
            throw new NoSuchProductException();
        }
    }


}
