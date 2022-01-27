package com.anta.shop.controller;

import com.anta.shop.dto.ProductDTO;
import com.anta.shop.exception_handling.ProductIncorrectData;
import com.anta.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shop_client")
public class ClientRESTController {

    private static final Logger log = LoggerFactory.getLogger(ClientRESTController.class);

    @Resource
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProductsForClient(), HttpStatus.OK);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) throws Exception {
        return new ResponseEntity<>(productService.getProductForClient(id), HttpStatus.OK);
    }

    @GetMapping("/products/name/{name}")
    public ResponseEntity<List<ProductDTO>> getAllByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.getAllByName(name), HttpStatus.OK);
    }

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
