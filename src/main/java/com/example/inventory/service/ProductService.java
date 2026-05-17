package com.example.inventory.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    // Get all products
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    // Get product by id
    public Optional<Product> getProductById(Long id) {
        return repo.findById(id);
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

    // NEW METHOD — Export all products to Excel
    public ByteArrayInputStream exportToExcel() throws IOException {

        // Get all products from DB
        List<Product> products = repo.findAll();

        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet called 'Products'
        Sheet sheet = workbook.createSheet("Products");

        // ── Create header row ──
        Row headerRow = sheet.createRow(0);

        // Style the header — bold and blue background
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Write column headers
        String[] headers = { "ID", "Name", "Category", "Price", "Quantity" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // ── Write product data rows ──
        int rowNum = 1; // Start from row 1 (row 0 is header)
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory());
            row.createCell(3).setCellValue(product.getPrice());
            row.createCell(4).setCellValue(product.getQuantity());
        }

        // ── Auto-size all columns to fit content ──
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // ── Convert workbook to bytes and return ──
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public byte[] generateBarcode(Long productId)
            throws WriterException, IOException {

        // Step 1 — The text that gets encoded inside the barcode
        // Format: PROD-5 (for product with ID 5)
        String barcodeText = "PROD-" + productId;

        // Step 2 — Create the barcode writer
        // Code128 is the standard barcode format used in retail/warehouses
        Code128Writer writer = new Code128Writer();

        // Step 3 — Encode the text into a barcode grid
        // BitMatrix = a grid of black (true) and white (false) pixels
        BitMatrix bitMatrix = writer.encode(
                barcodeText, // text to encode
                BarcodeFormat.CODE_128, // barcode type
                350, // width in pixels
                120, // height in pixels
                null // no extra hints needed
        );

        // Step 4 — Convert the grid to a PNG image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(
                bitMatrix, // the barcode grid
                "PNG", // image format
                outputStream // write to memory
        );

        // Step 5 — Return image as raw bytes
        return outputStream.toByteArray();
    }

}
