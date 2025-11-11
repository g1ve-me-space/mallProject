package model;

import interfaces.Identifiable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Identifiable<String> {

    private String id;
    private String name;
    private String currency;
    private String email; // <-- The new field
    private List<Purchase> purchases = new ArrayList<>(); // <-- The required field for your repository method

    public Customer() {}

    // --- GETTERS & SETTERS ---

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    // --- Getter & Setter for the new field ---
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // --- Getters & Setters for the purchases list ---
    public List<Purchase> getPurchases() { return purchases; }
    public void setPurchases(List<Purchase> purchases) { this.purchases = purchases; }
}