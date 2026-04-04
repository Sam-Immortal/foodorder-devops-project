package com.restaurant.foodorder;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.foodorder.controller.ChefController;
import com.restaurant.foodorder.model.Chef;
import com.restaurant.foodorder.model.LoginRequest;
import com.restaurant.foodorder.repository.ChefRepository;

@WebMvcTest(ChefController.class)
public class ChefControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChefRepository chefRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddChef() throws Exception {
        Chef newChef = new Chef("Gordon", "ramsay123");
        newChef.setId(1L);

        Mockito.when(chefRepository.save(any(Chef.class))).thenReturn(newChef);

        mockMvc.perform(post("/api/chef/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newChef)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Gordon"));
    }

    @Test
    public void testLoginChef_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("Gordon");
        loginRequest.setPassword("ramsay123");

        Chef mockChef = new Chef("Gordon", "ramsay123");

        Mockito.when(chefRepository.findByNameAndPassword(eq("Gordon"), eq("ramsay123")))
                .thenReturn(mockChef);

        mockMvc.perform(post("/api/chef/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gordon"));
    }

    @Test
    public void testGetAllChefs() throws Exception {
        Chef chef1 = new Chef("Gordon", "pass1");
        Chef chef2 = new Chef("Jamie", "pass2");

        Mockito.when(chefRepository.findAll()).thenReturn(Arrays.asList(chef1, chef2));

        mockMvc.perform(get("/api/chef/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Gordon"))
                .andExpect(jsonPath("$[1].name").value("Jamie"));
    }
}