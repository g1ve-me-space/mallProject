package model;

import enums.TaskStatus;
import interfaces.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // Import
import jakarta.validation.constraints.NotNull;  // Import

@Entity
@Table(name = "maintenance_tasks")
public class MaintenanceTask implements Identifiable<String> {

    @Id
    private String id;

    @NotBlank(message = "Descrierea este obligatorie!")
    private String description;

    @NotNull(message = "Statusul este obligatoriu!")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    // Validăm manual în Controller pentru că vine ca String (floorId)
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;

    public MaintenanceTask() {}

    // ... Getters & Setters ...
    @Override public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }
}