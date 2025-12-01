package model;

import enums.MaintenanceStaffType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // Import
import jakarta.validation.constraints.NotNull;  // Import

@Entity
@Table(name = "maintenance_staff")
public class MaintenanceStaff extends Staff {

    @NotNull(message = "Tipul este obligatoriu!")
    @Enumerated(EnumType.STRING)
    private MaintenanceStaffType type;

    // Name este în Staff.java, dar trebuie validat acolo!
    // Asigură-te că în Staff.java ai pus @NotBlank pe name.

    public MaintenanceStaff() { super(); }

    public MaintenanceStaffType getType() { return type; }
    public void setType(MaintenanceStaffType type) { this.type = type; }
}