package com.example.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Represents a product that is sold by multiple vendors.
 *
 * The vendors field contains a list of vendors that sell this product.
 */
@Data
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
    private String imageURL;
    private List<Vendor> vendors;
}
