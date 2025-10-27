package com.example.mallproject.model;

import java.util.List;

public class Store {
    private String id;
    private String name;
    private String category;
    private String location;
    private double size;
    private Tenant tenant;
    private List<Sale> sales;

    // Constructors
    public Store() {}

    public Store(String id, String name, String category, String location) {}

    // Getters and Setters
    public String getId() { return ""; }
    public void setId(String id) {}

    public String getName() { return ""; }
    public void setName(String name) {}

    public String getCategory() { return ""; }
    public void setCategory(String category) {}

    public String getLocation() { return ""; }
    public void setLocation(String location) {}

    public double getSize() { return 0.0; }
    public void setSize(double size) {}

    public Tenant getTenant() { return null; }
    public void setTenant(Tenant tenant) {}

    public List<Sale> getSales() { return null; }
    public void setSales(List<Sale> sales) {}

    // Business methods
    public void addSale(Sale sale) {}
    public double calculateMonthlyRent() { return 0.0; }
    public boolean isAvailable() { return false; }
}