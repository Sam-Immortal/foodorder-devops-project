package com.restaurant.foodorder;

import java.util.Arrays;

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
        MenuItem item = new MenuItem("Paneer Tikka", 250.0, true);
        item.setId(1L);
        Mockito.when(menuItemRepository.findAll()).thenReturn(Arrays.asList(item));

        // Act & Assert
        mockMvc.perform(get("/api/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Paneer Tikka"));
    }

    @Test
    public void testCreateMenuItem() throws Exception {
        // Arrange
        MenuItem item = new MenuItem("Garlic Naan", 60.0, true);
        item.setId(2L);
        Mockito.when(menuItemRepository.save(any(MenuItem.class))).thenReturn(item);

        // Act & Assert
        mockMvc.perform(post("/api/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Garlic Naan\", \"price\": 60.0, \"isAvailable\": true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Garlic Naan"));
    }
}