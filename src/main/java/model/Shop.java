package model;

import java.util.List;

public class Shop {
    private String id;
    private String name;
    private String ownerName;
    private double areaSqm;
    private List<Purchase> purchases;

    public Shop() {}

    public Shop(String id, String name, String ownerName, double areaSqm, List<Purchase> purchases) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
        this.areaSqm = areaSqm;
        this.purchases = purchases;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public double getAreaSqm() { return areaSqm; }
    public void setAreaSqm(double areaSqm) { this.areaSqm = areaSqm; }

    public List<Purchase> getPurchases() { return purchases; }
    public void setPurchases(List<Purchase> purchases) { this.purchases = purchases; }
}
