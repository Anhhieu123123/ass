package com.restaurant.api.repository;

import com.restaurant.api.entity.Dish;
import com.restaurant.api.entity.DishStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DishSpecification {
    
    public static Specification<Dish> filterDishes(
            DishStatus status,
            String keyword,
            Long categoryId,
            Double minPrice,
            Double maxPrice) {
        
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Lọc theo status
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            
            // Không hiển thị món đã xóa
            predicates.add(criteriaBuilder.notEqual(root.get("status"), DishStatus.DELETED));
            
            // Tìm kiếm theo keyword (name hoặc description)
            if (keyword != null && !keyword.trim().isEmpty()) {
                String likePattern = "%" + keyword.toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), likePattern);
                Predicate descPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")), likePattern);
                predicates.add(criteriaBuilder.or(namePredicate, descPredicate));
            }
            
            // Lọc theo categoryId
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }
            
            // Lọc theo giá tối thiểu
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            
            // Lọc theo giá tối đa
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
