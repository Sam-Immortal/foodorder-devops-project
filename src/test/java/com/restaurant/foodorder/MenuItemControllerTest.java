package com.restaurant.foodorder;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.restaurant.foodorder.controller.MenuItemController;
import com.restaurant.foodorder.model.MenuItem;
import com.restaurant.foodorder.repository.MenuItemRepository;

@WebMvcTest(MenuItemController.class)
public class MenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuItemRepository menuItemRepository;

    @Test
    public void testGetAllMenuItems() throws Exception {
        // Arrange
        MenuItem item = new MenuItem("Paneer Tikka", "Type of Paneer Curry", 250.0, "http://test-image.com", true);
        item.setId(1L);
        Mockito.when(menuItemRepository.findAll()).thenReturn(Arrays.asList(item));

        // Act & Assert
        mockMvc.perform(get("/api/menu/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Paneer Tikka"))
                .andExpect(jsonPath("$[0].description").value("Type of Paneer Curry"));
    }

    @Test
    public void testCreateMenuItem() throws Exception {
        // Arrange
        MenuItem item = new MenuItem("Garlic Naan", "Soft and tasty flatbread", 50.0, "http://test-image.com", true);
        item.setId(2L);
        Mockito.when(menuItemRepository.save(any(MenuItem.class))).thenReturn(item);

        // Act & Assert
        mockMvc.perform(post("/api/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Garlic Naan\", \"description\": \"Soft and tasty flatbread\", \"price\": 50.0, \"imageUrl\": \"http://test-image.com\", \"isAvailable\": true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Garlic Naan"));
    }

    @Test
    public void testUpdateMenuItemAvailability() throws Exception {
        // Arrange
        MenuItem existingItem = new MenuItem("Butter Paneer Masala", "Type of Paneer Curry", 70.0, "http://test-image.com", true);
        existingItem.setId(3L);
        
        MenuItem updatedItem = new MenuItem("Butter Paneer Masala", "Type of Paneer Curry", 70.0, "http://test-image.com", false);
        updatedItem.setId(3L);

        Mockito.when(menuItemRepository.findById(3L)).thenReturn(Optional.of(existingItem));
        Mockito.when(menuItemRepository.save(any(MenuItem.class))).thenReturn(updatedItem);

        // Act & Assert
        mockMvc.perform(put("/api/menu/3/availability")
                .contentType(MediaType.APPLICATION_JSON)
                .content("false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAvailable").value(false));
    }
}