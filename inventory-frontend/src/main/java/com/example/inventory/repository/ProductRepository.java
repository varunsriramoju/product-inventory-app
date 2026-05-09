package com.example.inventory.repository;

import com.example.inventory.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository
        extends JpaRepository<product, Long> {

    // This one line gives you a search feature!
    // Spring Boot auto-generates the SQL from the method name
    List<product> findByNameContainingIgnoreCase(String name);

}
