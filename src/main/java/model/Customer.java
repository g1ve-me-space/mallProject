package model;

import interfaces.Identifiable;
import java.util.List;

public class Customer implements Identifiable<String> {
    private String id;
    private String name;
    private String currency;
    private List<Purchase> purchases;

    // --- CONSTRUCTORS ---
    public Customer() {
    }

    // --- GETTERS AND SETTERS ---
    @Override // This annotation is important
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}