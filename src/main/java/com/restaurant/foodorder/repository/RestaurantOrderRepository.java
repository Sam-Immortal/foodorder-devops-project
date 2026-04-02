package com.restaurant.foodorder.repository;

import com.restaurant.foodorder.model.RestaurantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder, Long> {
}
