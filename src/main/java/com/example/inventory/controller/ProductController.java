package com.example.inventory.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.data.domain.Page;
import com.example.inventory.model.Product;
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
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    // ── POST add a new product ────────────────────────
    // URL: POST http://localhost:8080/products
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestBody Product product) {
        Product saved = service.addProduct(product);
        return ResponseEntity.ok(saved);
    }

    // ── PUT update a product ──────────────────────────
    // URL: PUT http://localhost:8080/products/1
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        Product updated = service.updateProduct(id, product);
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
    public List<Product> searchProducts(
            @RequestParam String name) {
        return service.searchProducts(name);
    }

    @GetMapping("/page")
    public Page<Product> getProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return service.getProductsPaginated(page, size);
    }

    // NEW ENDPOINT — Export to Excel
    // URL: GET /products/export
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportToExcel()
            throws IOException {

        ByteArrayInputStream excelFile = service.exportToExcel();

        // Set headers so browser knows it's a file download
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "attachment; filename=products.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument" +
                                ".spreadsheetml.sheet"))
                .body(new InputStreamResource(excelFile));
    }

}
