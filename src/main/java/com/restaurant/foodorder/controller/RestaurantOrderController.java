package com.restaurant.foodorder.controller;

import com.restaurant.foodorder.model.RestaurantOrder;
import com.restaurant.foodorder.repository.RestaurantOrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("/{id}/status")
    public ResponseEntity<RestaurantOrder> updateRestaurantOrderStatus(@PathVariable Long id, @RequestBody String status) {
        Optional<RestaurantOrder> orderOptional = restaurantOrderRepository.findById(id);
        if (orderOptional.isPresent()) {
            RestaurantOrder order = orderOptional.get();
            order.setStatus(status);
            RestaurantOrder updatedOrder = restaurantOrderRepository.save(order);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
