package model;

import enums.Shift;
import interfaces.Identifiable;

public class StaffAssignment implements Identifiable<String> {

    private String id;
    private String staffId;
    private String floorId;
    private Shift shift;

    public StaffAssignment() {
    }

    public StaffAssignment(String id, String staffId, String floorId, Shift shift) {
        this.id = id;
        this.staffId = staffId;
        this.floorId = floorId;
        this.shift = shift;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}