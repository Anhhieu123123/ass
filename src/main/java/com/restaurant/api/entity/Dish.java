package com.restaurant.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "dishes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 500)
    private String imageUrl;
    
    @Column(nullable = false)
    private Double price;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime startDate;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DishStatus status = DishStatus.ON_SALE;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
