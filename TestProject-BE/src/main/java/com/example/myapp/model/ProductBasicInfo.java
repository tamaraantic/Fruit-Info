package com.example.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductBasicInfo {
    private int id;
    private String name;
    private String imageURL;
}
