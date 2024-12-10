package com.example.food.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenTypes = "Bearer ";

    public AuthResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }

}
