package model;

import enums.TaskStatus;
import interfaces.Identifiable;

public class MaintenanceTask implements Identifiable<String> {

    private String id;
    private String description;
    private TaskStatus status;
    // Add other fields if you have them (e.g., floorId, assignedTo)

    // --- CONSTRUCTORS ---
    public MaintenanceTask() {
    }

    // --- GETTERS AND SETTERS ---

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}