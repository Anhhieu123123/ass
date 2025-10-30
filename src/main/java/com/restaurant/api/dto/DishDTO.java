package com.restaurant.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurant.api.entity.Dish;
import com.restaurant.api.entity.DishStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {
    
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime startDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastModifiedDate;
    
    private DishStatus status;
    private CategoryDTO category;
    
    public static DishDTO fromEntity(Dish dish) {
        if (dish == null) {
            return null;
        }
        
        DishDTO dto = new DishDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setImageUrl(dish.getImageUrl());
        dto.setPrice(dish.getPrice());
        dto.setStartDate(dish.getStartDate());
        dto.setLastModifiedDate(dish.getLastModifiedDate());
        dto.setStatus(dish.getStatus());
        dto.setCategory(CategoryDTO.fromEntity(dish.getCategory()));
        
        return dto;
    }
}
