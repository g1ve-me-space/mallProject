package model;

import interfaces.Identifiable;
import jakarta.persistence.*;

@Entity
@Table(name = "shops")
public class Shop implements Identifiable<String> {

    @Id
    private String id;

    private String name;
    private String category;

    // Câmpul corect
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;

    public Shop() {}

    // --- GETTERS & SETTERS ---

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    // ✅ Aceasta este partea care lipsea sau avea alt nume
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }
}