package model;

public class Purchase {
    private String id;
    private String customerId;
    private String shopId;
    private double amount;

    public Purchase() {}

    public Purchase(String id, String customerId, String shopId, double amount) {
        this.id = id;
        this.customerId = customerId;
        this.shopId = shopId;
        this.amount = amount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
