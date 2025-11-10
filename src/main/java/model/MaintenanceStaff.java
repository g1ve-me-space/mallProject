package model;

import java.util.List;

public class MaintenanceStaff extends Staff {
    private List<StaffAssignment> assignments;
    private MaintenanceStaffType type; // Changed from String to the enum

    public MaintenanceStaff() {}

    // Constructor updated to accept the enum
    public MaintenanceStaff(String id, String name, List<StaffAssignment> assignments, MaintenanceStaffType type) {
        super(id, name);
        this.assignments = assignments;
        this.type = type;
    }

    public List<StaffAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<StaffAssignment> assignments) { this.assignments = assignments; }

    // Getter and Setter updated to use the enum
    public MaintenanceStaffType getType() { return type; }
    public void setType(MaintenanceStaffType type) { this.type = type; }
}