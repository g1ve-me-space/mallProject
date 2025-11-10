package model;

import interfaces.Identifiable;

public class MaintenanceTask implements Identifiable { // Make sure it still implements Identifiable
    private String id;
    private String description;
    private TaskStatus status; // Changed from String to TaskStatus
    private String assignmentId;

    public MaintenanceTask() {}

    public MaintenanceTask(String id, String description, TaskStatus status, String assignmentId) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.assignmentId = assignmentId;
    }

    @Override // From Identifiable interface
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Getter and setter now use the enum
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public String getAssignmentId() { return assignmentId; }
    public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }
}