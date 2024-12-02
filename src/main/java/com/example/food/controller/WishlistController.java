package com.example.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.food.dto.WishlistDTO;
import com.example.food.service.WishlistService;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<WishlistDTO> addToWishlist(@RequestBody WishlistDTO wishlistDTO) {
        WishlistDTO savedWishlist = wishlistService.addToWishlist(wishlistDTO);
        return ResponseEntity.ok(savedWishlist);
    }
}
