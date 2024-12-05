package com.example.food.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.food.dto.WishlistDTO;
import com.example.food.dto.WishlistRequest;
import com.example.food.entity.Food;
import com.example.food.entity.User;
import com.example.food.entity.Wishlist;
import com.example.food.repository.FoodRepository;
import com.example.food.repository.UserRepository;
import com.example.food.repository.WishListRepository;
import com.example.food.utils.ResourceNotFoundException;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private  FoodRepository foodRepository;

    @Autowired
    private  UserRepository userRepository;


    @Override
    public List<WishlistDTO> getWishlistByUserId(Long userId) {
        List<Wishlist> wishlistItems = wishListRepository.findByUser_UserId(userId);
        return wishlistItems.stream()
                .map(wishlist -> {
                    Food food = wishlist.getFood();
                    WishlistDTO wishlistDTO = new WishlistDTO();
                    wishlistDTO.setName(food.getName());
                    wishlistDTO.setImage(food.getImage());
                    wishlistDTO.setFoodId(food.getFoodId());

                    return wishlistDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public WishlistDTO addWishlist(WishlistRequest wishlistRequest) {
        // Find the food and user based on the given IDs
        Food food = foodRepository.findById(wishlistRequest.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
        
        User user = userRepository.findById(wishlistRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Create and save the new wishlist entry
        Wishlist wishlist = new Wishlist();
        wishlist.setFood(food);
        wishlist.setUser(user);
        wishListRepository.save(wishlist);

        // Prepare the response DTO
        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setName(food.getName());
        wishlistDTO.setImage(food.getImage());
        

        return wishlistDTO;
    }
}