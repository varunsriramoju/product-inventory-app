package com.example.inventory.service;

import com.example.inventory.model.product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    // Get all products
    public List<product> getAllProducts() {
        return repo.findAll();
    }

    // Add a new product
    public product addProduct(product product) {
        return repo.save(product);
    }

    // Update an existing product
    public product updateProduct(Long id, product product) {
        product.setId(id);
        return repo.save(product);
    }

    // Delete a product
    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }

    // Search products by name
    public List<product> searchProducts(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

}
