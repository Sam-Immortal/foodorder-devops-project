package com.restaurant.foodorder.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.foodorder.model.MenuItem;
import com.restaurant.foodorder.repository.MenuItemRepository;

@RestController
@RequestMapping("/api/menu")
public class MenuItemController {

    private final MenuItemRepository menuItemRepository;

    public MenuItemController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
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

    @GetMapping("/all") // Or whatever path you used previously like "/"
    public ResponseEntity<Iterable<MenuItem>> getAllMenuItems() {
        return new ResponseEntity<>(menuItemRepository.findAll(), HttpStatus.OK);
    }
}
