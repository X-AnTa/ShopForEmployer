package com.anta.shop.controller;

import com.anta.shop.dto.ProductDTO;
import com.anta.shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shop_admin")
public class MyRESTController {

    @Resource
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) throws Exception {
        ProductDTO productDTO = productService.getProduct(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO addProductDto = productService.addProduct(productDTO);
        return new ResponseEntity<>(addProductDto, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        ProductDTO updateProductDTO = productService.updateProduct(id,productDTO);
        return new ResponseEntity<>(updateProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable int id) {
        Boolean delete = productService.deleteProduct(id);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}
