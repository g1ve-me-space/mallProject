package com.example.mallproject.model;

import java.time.LocalDateTime;
import java.util.List;

public class Sale {
    private String id;
    private Store store;
    private LocalDateTime saleDate;
    private double totalAmount;
    private List<OrderLine> orderLines;

    // Constructors
    public Sale() {}

    public Sale(String id, Store store, LocalDateTime saleDate) {}

    // Getters and Setters
    public String getId() { return ""; }
    public void setId(String id) {}

    public Store getStore() { return null; }
    public void setStore(Store store) {}

    public LocalDateTime getSaleDate() { return null; }
    public void setSaleDate(LocalDateTime saleDate) {}

    public double getTotalAmount() { return 0.0; }
    public void setTotalAmount(double totalAmount) {}

    public List<OrderLine> getOrderLines() { return null; }
    public void setOrderLines(List<OrderLine> orderLines) {}

    // Business methods
    public void addOrderLine(OrderLine orderLine) {}
    public void calculateTotalAmount() {}
    public boolean isFromToday() { return false; }
}