package com.example.food.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    private Long foodId;
    private String name;
    private String description;
    private String image;
    private String level;
    private String time;
    private String country;
    private String ingredient;
    private String dietary;
    @Builder
    public FoodDTO(Long foodId, String name, String description, String image,
                   String level, String time, String dietary, String country) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.level = level;
        this.time = time;
        this.dietary = dietary;
        this.country = country;
    }
}