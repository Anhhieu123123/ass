package com.restaurant.api.service;

import com.restaurant.api.dto.CreateDishRequest;
import com.restaurant.api.dto.DishDTO;
import com.restaurant.api.dto.PageResponse;
import com.restaurant.api.dto.UpdateDishRequest;
import com.restaurant.api.entity.Category;
import com.restaurant.api.entity.Dish;
import com.restaurant.api.entity.DishStatus;
import com.restaurant.api.exception.BadRequestException;
import com.restaurant.api.exception.ConflictException;
import com.restaurant.api.exception.ResourceNotFoundException;
import com.restaurant.api.repository.CategoryRepository;
import com.restaurant.api.repository.DishRepository;
import com.restaurant.api.repository.DishSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {
    
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    
    public PageResponse<DishDTO> getAllDishes(
            int page,
            int limit,
            String sortBy,
            String sortDir,
            DishStatus status,
            String keyword,
            Long categoryId,
            Double minPrice,
            Double maxPrice) {

        if (page < 1) {
            throw new BadRequestException("Page phai lon hơn hoặc bằng 1");
        }
        
        if (limit < 1) {
            throw new BadRequestException("Limit phai lớn hơn 0");
        }

        Sort sort = sortDir.equalsIgnoreCase("asc") 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Specification<Dish> spec = DishSpecification.filterDishes(
                status, keyword, categoryId, minPrice, maxPrice);

        Page<Dish> dishPage = dishRepository.findAll(spec, pageable);

        List<DishDTO> dishDTOs = dishPage.getContent().stream()
                .map(DishDTO::fromEntity)
                .collect(Collectors.toList());
        
        return new PageResponse<>(
                dishDTOs,
                page,
                limit,
                dishPage.getTotalElements()
        );
    }
    
    public DishDTO getDishById(String id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay id: " + id));
        
        if (dish.getStatus() == DishStatus.DELETED) {
            throw new ResourceNotFoundException("Mon an da bi xoa");
        }
        
        return DishDTO.fromEntity(dish);
    }
    
    @Transactional
    public DishDTO createDish(CreateDishRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new BadRequestException("Khong tim thay id: " + request.getCategoryId()));

        Dish dish = new Dish();
        dish.setId(generateDishId());
        dish.setName(request.getName());
        dish.setDescription(request.getDescription());
        dish.setImageUrl(request.getImageUrl());
        dish.setPrice(request.getPrice());
        dish.setStatus(DishStatus.ON_SALE);
        dish.setCategory(category);
        
        Dish savedDish = dishRepository.save(dish);
        return DishDTO.fromEntity(savedDish);
    }
    
    @Transactional
    public DishDTO updateDish(String id, UpdateDishRequest request) {

        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay mon an voi id: " + id));

        if (dish.getStatus() == DishStatus.DELETED) {
            throw new BadRequestException("Khong the sua mon an da bi xoa");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new BadRequestException("Khong tim thay id: " + request.getCategoryId()));

        dish.setName(request.getName());
        dish.setDescription(request.getDescription());
        dish.setImageUrl(request.getImageUrl());
        dish.setPrice(request.getPrice());
        dish.setCategory(category);

        if (request.getStatus() != null) {
            if (request.getStatus() == DishStatus.DELETED) {
                throw new BadRequestException("");
            }
            dish.setStatus(request.getStatus());
        }

        Dish updatedDish = dishRepository.save(dish);
        return DishDTO.fromEntity(updatedDish);
    }
    
    @Transactional
    public void deleteDish(String id) {

        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay id: " + id));

        if (dish.getStatus() == DishStatus.DELETED) {
            throw new ConflictException("Mon an da bi xoa");
        }

        dish.setStatus(DishStatus.DELETED);
        dishRepository.save(dish);
    }

    private String generateDishId() {
        return "MN" + String.format("%03d", (int) (Math.random() * 1000));
    }
}
