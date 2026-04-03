package com.restaurant.foodorder.controller;

import java.util.List;
import java.util.Optional; // <-- ADDED THIS IMPORT

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.foodorder.model.RestaurantOrder;
import com.restaurant.foodorder.model.StatusUpdateRequest;
import com.restaurant.foodorder.repository.RestaurantOrderRepository;

@RestController
@RequestMapping("/api/orders")
public class RestaurantOrderController {

    private final RestaurantOrderRepository restaurantOrderRepository;

    public RestaurantOrderController(RestaurantOrderRepository restaurantOrderRepository) {
        this.restaurantOrderRepository = restaurantOrderRepository;
    }

    @PostMapping
    public ResponseEntity<RestaurantOrder> createRestaurantOrder(@RequestBody RestaurantOrder order) {
        RestaurantOrder savedOrder = restaurantOrderRepository.save(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public List<RestaurantOrder> getAllRestaurantOrders() {
        return restaurantOrderRepository.findAll();
    }

    // --- UPDATED METHOD BELOW ---
    @PutMapping("/{id}/status")
    public ResponseEntity<RestaurantOrder> updateRestaurantOrderStatus(
            @PathVariable Long id, 
            @RequestBody StatusUpdateRequest request) { // <-- CHANGED THIS PARAMETER
            
        Optional<RestaurantOrder> orderOptional = restaurantOrderRepository.findById(id);
        
        if (orderOptional.isPresent()) {
            RestaurantOrder order = orderOptional.get();
            
            // Extract the clean string from the DTO instead of saving raw JSON
            order.setStatus(request.getStatus()); 
            
            RestaurantOrder updatedOrder = restaurantOrderRepository.save(order);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}