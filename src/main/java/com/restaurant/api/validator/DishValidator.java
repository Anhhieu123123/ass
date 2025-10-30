package com.restaurant.api.validator;

import com.restaurant.api.dto.CreateDishRequest;
import com.restaurant.api.dto.UpdateDishRequest;
import com.restaurant.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishValidator {
    
    private final CategoryRepository categoryRepository;
    
    /**
     * Validate CreateDishRequest
     */
    public ValidationResult validateCreateDish(CreateDishRequest request) {
        ValidationResult result = new ValidationResult();
        
        // Validate name
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            result.addError("name", "Tên món ăn không được để trống");
        } else if (request.getName().trim().length() <= 7) {
            result.addError("name", "Tên món ăn phải dài hơn 7 ký tự");
        }
        
        // Validate price
        if (request.getPrice() == null) {
            result.addError("price", "Giá món ăn không được để trống");
        } else if (request.getPrice() <= 0) {
            result.addError("price", "Giá món ăn phải lớn hơn 0");
        }
        
        // Validate categoryId
        if (request.getCategoryId() == null) {
            result.addError("categoryId", "Danh mục không được để trống");
        } else if (!categoryRepository.existsById(request.getCategoryId())) {
            result.addError("categoryId", "Không tìm thấy danh mục với ID: " + request.getCategoryId());
        }
        
        // Validate imageUrl (optional)
        if (request.getImageUrl() != null && !request.getImageUrl().trim().isEmpty()) {
            if (!isValidUrl(request.getImageUrl())) {
                result.addError("imageUrl", "URL hình ảnh không hợp lệ");
            }
        }
        
        return result;
    }
    
    /**
     * Validate UpdateDishRequest
     */
    public ValidationResult validateUpdateDish(UpdateDishRequest request) {
        ValidationResult result = new ValidationResult();
        
        // Validate name
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            result.addError("name", "Tên món ăn không được để trống");
        } else if (request.getName().trim().length() <= 7) {
            result.addError("name", "Tên món ăn phải dài hơn 7 ký tự");
        }
        
        // Validate price
        if (request.getPrice() == null) {
            result.addError("price", "Giá món ăn không được để trống");
        } else if (request.getPrice() <= 0) {
            result.addError("price", "Giá món ăn phải lớn hơn 0");
        }
        
        // Validate categoryId
        if (request.getCategoryId() == null) {
            result.addError("categoryId", "Danh mục không được để trống");
        } else if (!categoryRepository.existsById(request.getCategoryId())) {
            result.addError("categoryId", "Không tìm thấy danh mục với ID: " + request.getCategoryId());
        }
        
        // Validate imageUrl (optional)
        if (request.getImageUrl() != null && !request.getImageUrl().trim().isEmpty()) {
            if (!isValidUrl(request.getImageUrl())) {
                result.addError("imageUrl", "URL hình ảnh không hợp lệ");
            }
        }
        
        return result;
    }
    
    /**
     * Helper method to validate URL format
     */
    private boolean isValidUrl(String url) {
        try {
            return url.startsWith("http://") || url.startsWith("https://");
        } catch (Exception e) {
            return false;
        }
    }
}
