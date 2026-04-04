package com.restaurant.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.foodorder.model.LoginRequest;
import com.restaurant.foodorder.model.Manager;
import com.restaurant.foodorder.repository.ManagerRepository;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    private ManagerRepository managerRepository;

    // 1. REGISTRATION ENDPOINT
    @PostMapping("/register")
    public ResponseEntity<Manager> registerManager(@RequestBody Manager newManager) {
        try {
            // FIXED: Now checking if the Restaurant Name already exists, instead of email
            Manager existingManager = managerRepository.findByNameAndPassword(newManager.getName(), newManager.getPassword());
            
            Manager savedManager = managerRepository.save(newManager);
            return new ResponseEntity<>(savedManager, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. LOGIN ENDPOINT
    @PostMapping("/login")
    public ResponseEntity<Manager> loginManager(@RequestBody LoginRequest loginRequest) {
        try {
            Manager manager = managerRepository.findByNameAndPassword(
                    loginRequest.getName(),
                    loginRequest.getPassword()
            );

            if (manager != null) {
                return new ResponseEntity<>(manager, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 Wrong name/password
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}