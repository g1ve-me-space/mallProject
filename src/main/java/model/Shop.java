package model;

import interfaces.Identifiable;
import java.util.List;

public class Shop implements Identifiable<String> {

    private String id;
    private String name;
    private String category;
    private String ownerName;
    private double areaSqm;
    private List<Purchase> purchases;

    // --- CONSTRUCTORS ---
    // (Your constructors go here)

    // --- GETTERS AND SETTERS ---

    // This @Override annotation tells the compiler we are intentionally
    // fulfilling the contract of the Identifiable interface.
    @Override
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public double getAreaSqm() {
        return areaSqm;
    }

    public void setAreaSqm(double areaSqm) {
        this.areaSqm = areaSqm;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}