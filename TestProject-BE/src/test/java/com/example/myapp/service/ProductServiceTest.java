package com.example.myapp.service;

import com.example.myapp.model.Product;
import com.example.myapp.model.ProductBasicInfo;
import com.example.myapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    void getProductShouldReturnProductWithCorrectId() throws IOException {
        // Arrange
        int id = 5;
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(5, "Product 5", 10.0, "image_url_5", null));
        productList.add(new Product(6, "Product 6", 12.0, "image_url_6", null));
        productList.add(new Product(7, "Product 7", 15.0, "image_url_7", null));

        when(productRepository.getProductList()).thenReturn(productList);

        // Act
        Optional<Product> result = productService.getProduct(id);

        // Assert
        assertEquals(5, result.get().getId());
    }

    @Test
    void getProductShouldReturnNullForNonExistingId() throws IOException {
        // Arrange
        int id = 9;
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(5, "Product 5", 10.0, "image_url_5", null));
        productList.add(new Product(6, "Product 6", 12.0, "image_url_6", null));
        productList.add(new Product(7, "Product 7", 15.0, "image_url_7", null));

        when(productRepository.getProductList()).thenReturn(productList);

        // Act
        Optional<Product> result = productService.getProduct(id);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetProductsBasicInfoReturnsCorrectBasicInfo() throws IOException {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Product 1", 10.0, "image_url_1", null));
        productList.add(new Product(2, "Product 2", 12.0, "image_url_2", null));

        when(productRepository.getProductList()).thenReturn(productList);

        // Act
        List<ProductBasicInfo> result = productService.getProductsBasicInfo();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("image_url_2", result.get(1).getImageURL());
    }
}