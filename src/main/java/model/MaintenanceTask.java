package model;

public class MaintenanceTask {
    private String id;
    private String description;
    private String status; // Planned / Active / Done
    private String assignmentId;

    public MaintenanceTask() {}

    public MaintenanceTask(String id, String description, String status, String assignmentId) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.assignmentId = assignmentId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAssignmentId() { return assignmentId; }
    public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }
}
