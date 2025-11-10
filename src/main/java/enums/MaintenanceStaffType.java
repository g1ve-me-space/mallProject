package enums;

public enum MaintenanceStaffType {
    ELECTRICAL("Electrical"),
    CLEANING("Cleaning"),
    PLUMBING("Plumbing"),
    HVAC("HVAC"); // Heating, Ventilation, and Air Conditioning

    private final String displayName;

    MaintenanceStaffType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}