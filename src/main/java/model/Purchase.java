package model;

import interfaces.Identifiable;

public class Purchase implements Identifiable<String> {

    private String id;
    private String customerId;
    private String shopId;
    private double amount;

    /**
     * FIX: Add a no-argument constructor.
     * Spring MVC needs this to create a new Purchase object for the form.
     */
    public Purchase() {
    }

    // Your existing constructor
    public Purchase(String id, String customerId, String shopId, double amount) {
        this.id = id;
        this.customerId = customerId;
        this.shopId = shopId;
        this.amount = amount;
    }

    // --- GETTERS AND SETTERS ---

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}