package model;

import enums.MaintenanceStaffType;

// FIX: Make sure it extends the base Staff class
public class MaintenanceStaff extends Staff {

    private MaintenanceStaffType type;

    public MaintenanceStaff() {
        super();
    }

    // getId, setId, getName, and setName are inherited from Staff.

    public MaintenanceStaffType getType() {
        return type;
    }

    public void setType(MaintenanceStaffType type) {
        this.type = type;
    }
}