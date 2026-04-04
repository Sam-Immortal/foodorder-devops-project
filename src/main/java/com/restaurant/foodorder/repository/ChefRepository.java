package com.restaurant.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.foodorder.model.Chef;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {
    Chef findByNameAndPassword(String name, String password);
}