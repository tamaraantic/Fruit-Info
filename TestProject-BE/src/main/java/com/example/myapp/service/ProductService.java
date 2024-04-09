package com.example.myapp.service;

import com.example.myapp.model.ProductBasicInfo;
import com.example.myapp.model.Product;
import com.example.myapp.model.Vendor;
import com.example.myapp.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Optional<Product> getProduct(int id) throws IOException {
        List<Product> products = productRepository.getProductList();
        for (Product product: products) {
           if (product.getId() == id)
            {
               return Optional.of(product);
            }
        }
        return null;
    }

    public List<ProductBasicInfo> getProductsBasicInfo() throws IOException {
        List<Product> products = productRepository.getProductList();
        List<ProductBasicInfo> productBasicList = new ArrayList<>();
        for (Product product: products) {
            productBasicList.add(new ProductBasicInfo(product.getId(),product.getName(), product.getImageURL()));
        }
        return productBasicList;
    }

}
