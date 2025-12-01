package model;

import enums.Shift;
import interfaces.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull; // Import

@Entity
@Table(name = "staff_assignments")
public class StaffAssignment implements Identifiable<String> {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff; // Validat manual în Controller

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor; // Validat manual în Controller

    @NotNull(message = "Tura este obligatorie!")
    @Enumerated(EnumType.STRING)
    private Shift shift;

    public StaffAssignment() {}
    public StaffAssignment(String id, Staff staff, Floor floor, Shift shift) {
        this.id = id;
        this.staff = staff;
        this.floor = floor;
        this.shift = shift;
    }

    // ... Getters & Setters ...
    @Override public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }
    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }
}