package com.example.mallproject.model;

public class OrderLine {
    private String id;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double lineTotal;

    // Constructors
    public OrderLine() {}

    public OrderLine(String id, String productName, int quantity, double unitPrice) {}

    // Getters and Setters
    public String getId() { return ""; }
    public void setId(String id) {}

    public String getProductName() { return ""; }
    public void setProductName(String productName) {}

    public int getQuantity() { return 0; }
    public void setQuantity(int quantity) {}

    public double getUnitPrice() { return 0.0; }
    public void setUnitPrice(double unitPrice) {}

    public double getLineTotal() { return 0.0; }
    public void setLineTotal(double lineTotal) {}

    // Business methods
    public void calculateLineTotal() {}
}