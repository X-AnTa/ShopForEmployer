package com.anta.shop.controller;

import com.anta.shop.dto.ProductDTO;
import com.anta.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin controller", description = "all CRUD methods")
@RestController
@RequestMapping("/shop_admin")
public class AdminRESTController {

    private final ProductService productService;

    @Autowired
    public AdminRESTController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products")
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProductsForAdmin(), HttpStatus.OK);
    }

    @Operation(summary = "Get product by id")
    @GetMapping("products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) {
        ProductDTO productDTO = productService.getProductForAdmin(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @Operation(summary = "Add new product")
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO newProductDto = productService.addProduct(productDTO);
        return new ResponseEntity<>(newProductDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update product")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable int id) {
        Boolean delete = productService.deleteProduct(id);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}
