package com.anta.shop.controller;

import com.anta.shop.dto.ProductDTO;
import com.anta.shop.exception_handling.ProductIncorrectData;
import com.anta.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client controller")
@RestController
@RequestMapping("/shop_client")
public class ClientRESTController {

    private static final Logger log = LoggerFactory.getLogger(ClientRESTController.class);

    private final ProductService productService;

    @Autowired
    public ClientRESTController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all Products
     * @return List
     */
    @Operation(summary = "Get all products")
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProductsForClient(), HttpStatus.OK);
    }

    /**
     * Get Product by id
     * @param id Product id
     * @return Product
     */
    @Operation(summary = "Get product by id")
    @GetMapping("products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) {
        return new ResponseEntity<>(productService.getProductForClient(id), HttpStatus.OK);
    }

    /**
     * Get all Products by name
     * @param name Product name
     * @return List
     */
    @Operation(summary = "Get all products by name")
    @GetMapping("/products/name/{name}")
    public ResponseEntity<List<ProductDTO>> getAllByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.getAllByName(name), HttpStatus.OK);
    }

    /**
     * Get all Products by description
     * @param description Product description
     * @return List
     */
    @Operation(summary = "Get all products by description")
    @GetMapping("/products/description/{description}")
    public ResponseEntity<List<ProductDTO>> getAllByDescription(@PathVariable String description) {
        return new ResponseEntity<>(productService.getAllByDescription(description), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ProductIncorrectData> handleException(Exception exception) {
        ProductIncorrectData data = new ProductIncorrectData();
        data.setErrorMessage("Product(s) not found");
        data.setErrorCode("404 NOT_FOUND");

        log.info("handleException: logging exception");

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
