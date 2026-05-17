package com.example.inventory.controller;

import com.google.zxing.WriterException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import com.example.inventory.model.Product;
import com.example.inventory.service.ProductService;
import com.example.inventory.dto.ProductDTO;
import com.example.inventory.dto.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService service;

    // ── GET all products ──────────────────────────────
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return service.getAllProducts().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ── POST add a new product ────────────────────────
    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(
            @Valid @RequestBody ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        Product saved = service.addProduct(product);
        return ResponseEntity.ok(ProductMapper.toDTO(saved));
    }

    // ── PUT update a product ──────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        Product updated = service.updateProduct(id, product);
        return ResponseEntity.ok(ProductMapper.toDTO(updated));
    }

    // ── DELETE a product ──────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    // ── GET search products by name ───────────────────
    @GetMapping("/search")
    public List<ProductDTO> searchProducts(
            @RequestParam String name) {
        return service.searchProducts(name).stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/page")
    public Page<ProductDTO> getProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return service.getProductsPaginated(page, size)
                .map(ProductMapper::toDTO);
    }

    // NEW ENDPOINT — Export to Excel
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportToExcel()
            throws IOException {

        ByteArrayInputStream excelFile = service.exportToExcel();

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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        Optional<Product> product = service.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(ProductMapper.toDTO(product.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/barcode")
    public ResponseEntity<byte[]> getBarcode(
            @PathVariable Long id)
            throws WriterException, IOException {

        byte[] barcodeImage = service.generateBarcode(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename=barcode-" + id + ".png");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_PNG) 
                .body(barcodeImage);
    }

}
