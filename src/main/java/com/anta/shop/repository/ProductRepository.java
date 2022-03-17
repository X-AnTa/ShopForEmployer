package com.anta.shop.repository;

import com.anta.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByName(String name);

    List<Product> findAllByDescription(String description);
}
