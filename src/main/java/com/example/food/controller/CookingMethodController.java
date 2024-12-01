package com.example.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.food.dto.CookingMethodDTO;
import com.example.food.service.CookingMethodService;

@RestController
@RequestMapping("/api/cookingmethod")
public class CookingMethodController {

    @Autowired
    private CookingMethodService cookingMethodService;

    @PostMapping
    public ResponseEntity<CookingMethodDTO> addCookingMethod(@RequestBody CookingMethodDTO cookingMethodDTO) {
        CookingMethodDTO savedMethod = cookingMethodService.addCookingMethod(cookingMethodDTO);
        return ResponseEntity.ok(savedMethod);
    }

    @GetMapping
    public ResponseEntity<List<CookingMethodDTO>> getAllCookingMethods() {
        List<CookingMethodDTO> methods = cookingMethodService.getAllCookingMethods();
        return ResponseEntity.ok(methods);
    }
}
