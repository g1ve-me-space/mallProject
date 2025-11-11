package model;

import interfaces.Identifiable;
import java.util.ArrayList;
import java.util.List;

public class Mall implements Identifiable<String> {

    private String id;
    private String name;
    private String city;
    private String address; // <-- The new field
    private List<Floor> floors = new ArrayList<>(); // <-- The required field for your repository methods

    public Mall() {}

    // --- GETTERS & SETTERS ---

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    // --- Getter & Setter for the new field ---
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // --- Getters & Setters for the floors list ---
    public List<Floor> getFloors() { return floors; }
    public void setFloors(List<Floor> floors) { this.floors = floors; }
}