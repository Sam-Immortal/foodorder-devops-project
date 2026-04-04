package com.restaurant.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.foodorder.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    // Changed from findByEmailAndPassword
    Manager findByNameAndPassword(String name, String password);
}