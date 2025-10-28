package model;

public class StaffAssignment {
    private String id;
    private String floorId;
    private String staffId;
    private String shift; // Morning / Evening / Night

    public StaffAssignment() {}

    public StaffAssignment(String id, String floorId, String staffId, String shift) {
        this.id = id;
        this.floorId = floorId;
        this.staffId = staffId;
        this.shift = shift;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFloorId() { return floorId; }
    public void setFloorId(String floorId) { this.floorId = floorId; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
}
