package com.anta.shop.repository;

import com.anta.shop.entity.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Integer> {
    /**
     * Search by language
     * @param language Product Language
     * @return Description
     */
    Description findByLanguage(String language);
}
