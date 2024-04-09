package com.example.myapp.controller;

import com.example.myapp.model.Product;
import com.example.myapp.model.ProductBasicInfo;
import com.example.myapp.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService service;

    @CrossOrigin
    @GetMapping
    public List<ProductBasicInfo> getProductBasicInfo() throws IOException {
        return service.getProductsBasicInfo();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public Optional<Product> getProductBasicInfo(@PathVariable int id) throws IOException {
        Optional<Product> product = service.getProduct(id);
        return product;
    }
}
