package com.example.food.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.food.entity.Step;

public interface StepRepository extends JpaRepository<Step, Long> {

}
