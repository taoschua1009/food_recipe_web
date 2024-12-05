package com.example.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.food.entity.Wishlist;

public interface WishListRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser_UserId(Long userId);
  
    
}
