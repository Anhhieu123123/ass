package com.restaurant.api.controller;

import com.restaurant.api.dto.*;
import com.restaurant.api.entity.DishStatus;
import com.restaurant.api.service.DishService;
import com.restaurant.api.validator.DishValidator;
import com.restaurant.api.validator.ValidationResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishController {
    
    private final DishService dishService;
    private final DishValidator dishValidator;
    
    @GetMapping

    public ResponseEntity<ApiResponse<PageResponse<DishDTO>>> getAllDishes(
            @Parameter(description = "Số trang (bắt đầu từ 1)")
            @RequestParam(defaultValue = "1") int page,
            
            @Parameter(description = "Số lượng món ăn mỗi trang")
            @RequestParam(defaultValue = "5") int limit,
            
            @Parameter(description = "Trường sắp xếp (name, price, startDate)")
            @RequestParam(defaultValue = "startDate") String sortBy,
            
            @Parameter(description = "Hướng sắp xếp (asc, desc)")
            @RequestParam(defaultValue = "desc") String sortDir,
            
            @Parameter(description = "Lọc theo trạng thái")
            @RequestParam(defaultValue = "ON_SALE") DishStatus status,
            
            @Parameter(description = "Tìm kiếm theo tên hoặc mô tả")
            @RequestParam(required = false) String keyword,
            
            @Parameter(description = "Lọc theo danh mục")
            @RequestParam(required = false) Long categoryId,
            
            @Parameter(description = "Giá tối thiểu")
            @RequestParam(required = false) Double minPrice,
            
            @Parameter(description = "Giá tối đa")
            @RequestParam(required = false) Double maxPrice) {
        
        PageResponse<DishDTO> response = dishService.getAllDishes(
                page, limit, sortBy, sortDir, status, keyword, categoryId, minPrice, maxPrice);
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/{id}")

    public ResponseEntity<ApiResponse<DishDTO>> getDishById(
            @Parameter(description = "Ma mon an", required = true)
            @PathVariable String id) {
        
        DishDTO dish = dishService.getDishById(id);
        return ResponseEntity.ok(ApiResponse.success(dish));
    }
    
    @PostMapping

    public ResponseEntity<ApiResponse<?>> createDish(@RequestBody CreateDishRequest request) {

        ValidationResult validationResult = dishValidator.validateCreateDish(request);
        if (validationResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Validation failed", validationResult.getErrors()));
        }
        
        DishDTO createdDish = dishService.createDish(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tao thanh cong", createdDish));
    }
    
    @PutMapping("/{id}")

    public ResponseEntity<ApiResponse<?>> updateDish(
            @Parameter(description = "Ma mon an", required = true)
            @PathVariable String id,
            @RequestBody UpdateDishRequest request) {
        

        ValidationResult validationResult = dishValidator.validateUpdateDish(request);
        if (validationResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Validation failed", validationResult.getErrors()));
        }
        
        DishDTO updatedDish = dishService.updateDish(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cap nhat thanh cong", updatedDish));
    }
    
    @DeleteMapping("/{id}")

    public ResponseEntity<ApiResponse<Void>> deleteDish(
            @Parameter(description = "Ma mon an", required = true)
            @PathVariable String id) {
        
        dishService.deleteDish(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("Xoa thanh cong", null));
    }
}
