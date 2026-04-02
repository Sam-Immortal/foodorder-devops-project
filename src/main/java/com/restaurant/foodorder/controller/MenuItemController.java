package com.restaurant.foodorder.controller;

import com.restaurant.foodorder.model.MenuItem;
import com.restaurant.foodorder.repository.MenuItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuItemController {

    private final MenuItemRepository menuItemRepository;

    public MenuItemController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return new ResponseEntity<>(savedMenuItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> updateMenuItemAvailability(@PathVariable Long id, @RequestBody Boolean isAvailable) {
        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);
        if (menuItemOptional.isPresent()) {
            MenuItem menuItem = menuItemOptional.get();
            menuItem.setIsAvailable(isAvailable);
            MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
