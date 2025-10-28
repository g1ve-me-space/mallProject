package model;

import java.util.List;

public class Customer {
    private String id;
    private String name;
    private String currency;
    private List<Purchase> purchases;

    public Customer() {}

    public Customer(String id, String name, String currency, List<Purchase> purchases) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.purchases = purchases;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public List<Purchase> getPurchases() { return purchases; }
    public void setPurchases(List<Purchase> purchases) { this.purchases = purchases; }
}
