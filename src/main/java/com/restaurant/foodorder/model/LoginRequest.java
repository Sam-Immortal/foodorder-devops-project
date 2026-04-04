package com.restaurant.foodorder.model;

public class LoginRequest {
    private String name; // Changed from email
    private String password;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}