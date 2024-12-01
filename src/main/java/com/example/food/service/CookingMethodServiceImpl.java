package com.example.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food.dto.CookingMethodDTO;
import com.example.food.entity.CookingMethod;
import com.example.food.entity.Food;
import com.example.food.repository.CookingMethodRepository;
import com.example.food.repository.FoodRepository;


@Service
public class CookingMethodServiceImpl implements CookingMethodService {

    @Autowired
    private CookingMethodRepository cookingMethodRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public CookingMethodDTO addCookingMethod(CookingMethodDTO cookingMethodDTO) {
        // Fetch the Food entity by ID
        Food food = foodRepository.findById(cookingMethodDTO.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food with ID " + cookingMethodDTO.getFoodId() + " not found"));

        // Map DTO to Entity
        CookingMethod cookingMethod = CookingMethod.builder()
                .methodId(cookingMethodDTO.getMethodId()) // Set methodId if provided
                .rating(cookingMethodDTO.getRating())
                .description(cookingMethodDTO.getDescription())
                .image(cookingMethodDTO.getImage())
                .ingredient(cookingMethodDTO.getIngredient())
                .country(cookingMethodDTO.getCountry())
                .name(cookingMethodDTO.getName())
                .food(food)
                .build();

        // Save the CookingMethod entity
        CookingMethod savedMethod = cookingMethodRepository.save(cookingMethod);

        // Map Entity back to DTO
        return CookingMethodDTO.builder()
                .methodId(savedMethod.getMethodId())
                .rating(savedMethod.getRating())
                .description(savedMethod.getDescription())
                .image(savedMethod.getImage())
                .ingredient(savedMethod.getIngredient())
                .country(savedMethod.getCountry())
                .name(savedMethod.getName())
                .foodId(savedMethod.getFood().getFoodId())
                .build();
    }
}
