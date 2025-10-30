package com.restaurant.api.dto;

import com.restaurant.api.entity.DishStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishRequest {
    
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Long categoryId;
    private DishStatus status;
}
