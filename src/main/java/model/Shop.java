package model;

import interfaces.Identifiable;

public class Shop implements Identifiable<String> {

    private String id;
    private String name;
    private String category;
    private String phoneNumber; // <-- NEW

    public Shop() {}

    // --- GETTERS & SETTERS ---

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    // --- New Getter & Setter ---
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}