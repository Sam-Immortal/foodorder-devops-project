package com.restaurant.foodorder.controller;

import com.restaurant.foodorder.model.RestaurantTable;
import com.restaurant.foodorder.repository.RestaurantTableRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tables")
public class RestaurantTableController {

    private final RestaurantTableRepository restaurantTableRepository;

    public RestaurantTableController(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    @GetMapping
    public List<RestaurantTable> getAllRestaurantTables() {
        return restaurantTableRepository.findAll();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RestaurantTable> updateRestaurantTableStatus(@PathVariable Long id, @RequestBody String status) {
        Optional<RestaurantTable> tableOptional = restaurantTableRepository.findById(id);
        if (tableOptional.isPresent()) {
            RestaurantTable table = tableOptional.get();
            table.setStatus(status);
            RestaurantTable updatedTable = restaurantTableRepository.save(table);
            return new ResponseEntity<>(updatedTable, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
