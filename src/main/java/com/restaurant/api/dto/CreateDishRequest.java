package com.restaurant.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDishRequest {
    
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Long categoryId;
}
