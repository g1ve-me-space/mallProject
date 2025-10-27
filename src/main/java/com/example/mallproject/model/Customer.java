package com.example.mallproject.model;

import java.time.LocalDate;
import java.util.List;

public class Customer {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate registrationDate;
    private List<Order> orders;

    // Constructors
    public Customer() {}

    public Customer(String id, String name, String email, String phoneNumber) {}

    // Getters and Setters
    public String getId() { return ""; }
    public void setId(String id) {}

    public String getName() { return ""; }
    public void setName(String name) {}

    public String getEmail() { return ""; }
    public void setEmail(String email) {}

    public String getPhoneNumber() { return ""; }
    public void setPhoneNumber(String phoneNumber) {}

    public LocalDate getRegistrationDate() { return null; }
    public void setRegistrationDate(LocalDate registrationDate) {}

    public List<Order> getOrders() { return null; }
    public void setOrders(List<Order> orders) {}

    // Business methods
    public void addOrder(Order order) {}
    public void removeOrder(Order order) {}
    public double getTotalSpent() { return 0.0; }
}