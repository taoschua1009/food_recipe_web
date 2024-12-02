
package com.example.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDTO {

    private Long wishlistId;
    private Long foodId; // Reference to Food entity
    private Long userid; // Reference to User entity
}


