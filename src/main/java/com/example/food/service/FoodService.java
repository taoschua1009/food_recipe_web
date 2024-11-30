package com.example.food.service;

import java.util.List;

import com.example.food.dto.Agenda;
import com.example.food.dto.AgendaRespond;
import com.example.food.dto.FoodDTO;
import com.example.food.dto.FoodRequest;
import com.example.food.dto.CommentDTO;

public interface FoodService {

    Agenda getAgentDataToday();

    public AgendaRespond addFood(FoodRequest foodRequest);

    List<FoodDTO> getFoodsByCountry(String country);

    List<FoodDTO> getFoodsByDietary(String dietary);

}
