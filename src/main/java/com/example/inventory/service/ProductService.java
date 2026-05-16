package com.example.inventory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    // Get all products
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    // Add a new product
    public Product addProduct(Product product) {
        return repo.save(product);
    }

    // Update an existing product
    public Product updateProduct(Long id, Product product) {
        product.setId(id);
        return repo.save(product);
    }

    // Delete a product
    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }

    // Search products by name
    public List<Product> searchProducts(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public Page<Product> getProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

}
