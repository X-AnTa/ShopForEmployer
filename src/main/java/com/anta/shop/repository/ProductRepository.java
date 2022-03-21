package com.anta.shop.repository;

import com.anta.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Search all Products by name
     * @param name Product name
     * @return List Products by name
     */
    List<Product> findAllByName(String name);

    /**
     * Search all Products by description
     * @param description Product description
     * @return List Products by description
     */
    List<Product> findAllByDescription(String description);
}
