package com.example.food.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.food.entity.CookingMethod;
public interface CookingMethodRepository extends JpaRepository<CookingMethod, Long> {

}
