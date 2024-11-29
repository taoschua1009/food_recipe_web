package com.example.food.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.food.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {

}
