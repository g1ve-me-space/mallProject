package com.example.mallproject.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String id;
    private Customer customer;
    private LocalDateTime orderDate;
    private String status;
    private double totalPrice;
    private List<OrderLine> orderLines;

    // Constructors
    public Order() {}

    public Order(String id, Customer customer, LocalDateTime orderDate) {}

    // Getters and Setters
    public String getId() { return ""; }
    public void setId(String id) {}

    public Customer getCustomer() { return null; }
    public void setCustomer(Customer customer) {}

    public LocalDateTime getOrderDate() { return null; }
    public void setOrderDate(LocalDateTime orderDate) {}

    public String getStatus() { return ""; }
    public void setStatus(String status) {}

    public double getTotalPrice() { return 0.0; }
    public void setTotalPrice(double totalPrice) {}

    public List<OrderLine> getOrderLines() { return null; }
    public void setOrderLines(List<OrderLine> orderLines) {}

    // Business methods
    public void addOrderLine(OrderLine orderLine) {}
    public void calculateTotalPrice() {}
    public void updateStatus(String newStatus) {}
}