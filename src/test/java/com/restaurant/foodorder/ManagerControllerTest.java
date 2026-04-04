package com.restaurant.foodorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.foodorder.controller.ManagerController;
import com.restaurant.foodorder.model.LoginRequest;
import com.restaurant.foodorder.model.Manager;
import com.restaurant.foodorder.repository.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerRepository managerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterManager_Success() throws Exception {
        Manager newManager = new Manager("Test Restaurant", "testpass");
        newManager.setId(1L);

        // Mock the database to say this restaurant doesn't exist yet, then save it
        Mockito.when(managerRepository.findByNameAndPassword(any(), any())).thenReturn(null);
        Mockito.when(managerRepository.save(any(Manager.class))).thenReturn(newManager);

        mockMvc.perform(post("/api/manager/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newManager)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Restaurant"));
    }

    @Test
    public void testLoginManager_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("Test Restaurant");
        loginRequest.setPassword("testpass");

        Manager mockManager = new Manager("Test Restaurant", "testpass");

        // Mock the database to successfully find the manager
        Mockito.when(managerRepository.findByNameAndPassword(eq("Test Restaurant"), eq("testpass")))
                .thenReturn(mockManager);

        mockMvc.perform(post("/api/manager/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Restaurant"));
    }

    @Test
    public void testLoginManager_Failure() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName("Wrong Name");
        loginRequest.setPassword("wrongpass");

        // Mock the database failing to find a match
        Mockito.when(managerRepository.findByNameAndPassword(any(), any())).thenReturn(null);

        mockMvc.perform(post("/api/manager/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()); // Expect 401
    }
}