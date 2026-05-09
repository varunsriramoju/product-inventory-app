package com.example.inventory.controller;

import com.example.inventory.model.product;
import com.example.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService service;

    // ── GET all products ──────────────────────────────
    // URL: GET http://localhost:8080/products
    @GetMapping
    public List<product> getAllProducts() {
        return service.getAllProducts();
    }

    // ── POST add a new product ────────────────────────
    // URL: POST http://localhost:8080/products
    @PostMapping
    public ResponseEntity<product> addProduct(
            @RequestBody product product) {
        product saved = service.addProduct(product);
        return ResponseEntity.ok(saved);
    }

    // ── PUT update a product ──────────────────────────
    // URL: PUT http://localhost:8080/products/1
    @PutMapping("/{id}")
    public ResponseEntity<product> updateProduct(
            @PathVariable Long id,
            @RequestBody product product) {
        product updated = service.updateProduct(id, product);
        return ResponseEntity.ok(updated);
    }

    // ── DELETE a product ──────────────────────────────
    // URL: DELETE http://localhost:8080/products/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    // ── GET search products by name ───────────────────
    // URL: GET http://localhost:8080/products/search?name=laptop
    @GetMapping("/search")
    public List<product> searchProducts(
            @RequestParam String name) {
        return service.searchProducts(name);
    }

}
