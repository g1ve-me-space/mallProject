package model;

import java.util.List;

public class MaintenanceStaff extends Staff {
    private List<StaffAssignment> assignments;
    private String type; // Electrical / Cleaning

    public MaintenanceStaff() {}

    public MaintenanceStaff(String id, String name, List<StaffAssignment> assignments, String type) {
        super(id, name);
        this.assignments = assignments;
        this.type = type;
    }

    public List<StaffAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<StaffAssignment> assignments) { this.assignments = assignments; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
