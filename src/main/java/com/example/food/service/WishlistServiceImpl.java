package com.example.food.service;

import com.example.food.dto.WishlistDTO;
import com.example.food.entity.Food;
import com.example.food.entity.User;
import com.example.food.entity.Wishlist;
import com.example.food.repository.FoodRepository;
import com.example.food.repository.UserRepository;
import com.example.food.repository.WishListRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishListRepository wishlistRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public WishlistDTO addToWishlist(WishlistDTO wishlistDTO) {
        // Fetch related entities (Food and User)
        Food food = foodRepository.findById(wishlistDTO.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found with ID: " + wishlistDTO.getFoodId()));
    
        User user = userRepository.findById(wishlistDTO.getUserid()) // Use 'userid' instead of 'userId'
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + wishlistDTO.getUserid()));
    
        // Create and save the wishlist entry
        Wishlist wishlist = Wishlist.builder()
                .food(food)
                .user(user)
                .build();
    
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
    
        // Convert the saved entity to DTO and add food details
        return WishlistDTO.builder()
                .wishlistId(savedWishlist.getWishlistId())
                .foodId(savedWishlist.getFood().getFoodId())
                .userid(savedWishlist.getUser().getUserId())  // Use 'userid' here as well
                .build();
    }
    
}
