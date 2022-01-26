package com.anta.shop.dao;

import com.anta.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findAllByName(String name);

    public List<Product> findAllByDescription(String description);
}
