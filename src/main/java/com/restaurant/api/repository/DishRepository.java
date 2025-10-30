package com.restaurant.api.repository;

import com.restaurant.api.entity.Dish;
import com.restaurant.api.entity.DishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, String>, JpaSpecificationExecutor<Dish> {
    
    Optional<Dish> findByIdAndStatusNot(String id, DishStatus status);
}
