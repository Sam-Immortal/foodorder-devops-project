package com.restaurant.foodorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.foodorder.model.Chef;
import com.restaurant.foodorder.model.LoginRequest;
import com.restaurant.foodorder.repository.ChefRepository;

@RestController
@RequestMapping("/api/chef")
public class ChefController {

    @Autowired
    private ChefRepository chefRepository;

    // Manager uses this to add a new chef
    @PostMapping("/add")
    public ResponseEntity<Chef> addChef(@RequestBody Chef newChef) {
        try {
            Chef savedChef = chefRepository.save(newChef);
            return new ResponseEntity<>(savedChef, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Chef uses this later to log in
    @PostMapping("/login")
    public ResponseEntity<Chef> loginChef(@RequestBody LoginRequest loginRequest) {
        try {
            Chef chef = chefRepository.findByNameAndPassword(loginRequest.getName(), loginRequest.getPassword());
            if (chef != null) {
                return new ResponseEntity<>(chef, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add this to the bottom of ChefController.java
    @GetMapping("/all")
    public ResponseEntity<Iterable<Chef>> getAllChefs() {
        return new ResponseEntity<>(chefRepository.findAll(), HttpStatus.OK);
    }
}