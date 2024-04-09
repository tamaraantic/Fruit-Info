package com.example.myapp.repository;

import com.example.myapp.controller.ProductController;
import com.example.myapp.model.Product;
import com.example.myapp.model.Vendor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class ProductRepository {
    public static final String productDataSource = "https://api.predic8.de/shop/v2/products/";
    public static final String vendorDataSource = "https://api.predic8.de/shop/v2/vendors/";
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private List<Product> productList = new ArrayList<>();

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public static ProductRepository productRepository() throws IOException {
        ProductRepository productRepository = new ProductRepository();
        productRepository.setProductList(getProducts());
        return productRepository;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    /**
     * Retrieves a list of products from a remote data source.
     * The data is fetched via an HTTP GET request and parsed from JSON format.
     */
    public static List<Product> getProducts() throws IOException {
        String content = getResponse(productDataSource);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonObject = objectMapper.readTree(content);
        JsonNode productsArray = jsonObject.get("products");

        List<Product> products = new ArrayList<>();
        for (JsonNode productNode : productsArray) {
            Product product = createProduct(productNode.get("id").asInt());
            products.add(product);
        }
        return products;
    }

    /**
     * Performs an HTTP GET request to retrieve the response from the specified URL.
     */

    public static String getResponse(String urlString) throws IOException {
        HttpURLConnection connection = createConnection(urlString);
        logger.info("HttpURLConnection with resourse " + urlString + " created.");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return response.toString();
        }
    }

    /**
     * Creates an HTTP connection for the given URL.
     */

    public static HttpURLConnection createConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    /**
     * Creates a product object based on the provided product ID.
     * Fetches detailed product information from the associated resource URL.
     */
    private static Product createProduct(int productID) throws IOException {
        String productDetails = getResponse(productDataSource + productID);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode productJson = objectMapper.readTree(productDetails);

        int id = productJson.get("id").asInt();
        String name = productJson.get("name").asText();
        double price = productJson.get("price").asDouble();
        String imageURL = "https://api.predic8.de" + productJson.get("image_link").asText();

        List<Vendor> vendorsForProduct = new ArrayList<>();
        JsonNode vendorsArray = productJson.get("vendors");
        for (JsonNode vendorNode : vendorsArray) {
            Vendor vendor = createVendor(vendorNode.get("id").asInt());
            vendorsForProduct.add(vendor);
        }

        return new Product(id, name, price, imageURL, vendorsForProduct);
    }
    /**
     * Creates a vendor object based on the provided vendor ID.
     * Fetches detailed vendor information from the associated resource URL.
     */
    private static Vendor createVendor(int vendorID) throws IOException {
        String vendorDetails = getResponse(vendorDataSource + vendorID);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode vendorJson = objectMapper.readTree(vendorDetails);

        int id = vendorJson.get("id").asInt();
        String name = vendorJson.get("name").asText();

        return new Vendor(id, name);
    }
}
