package model;

import java.util.List;

public class Floor {
    private String id;
    private int number;
    private List<Shop> shops;
    private List<MaintenanceTask> tasks;
    private List<ElectricalAsset> electricals;
    private List<StaffAssignment> assignments;

    public Floor() {}

    public Floor(String id, int number, List<Shop> shops, List<MaintenanceTask> tasks,
                 List<ElectricalAsset> electricals, List<StaffAssignment> assignments) {
        this.id = id;
        this.number = number;
        this.shops = shops;
        this.tasks = tasks;
        this.electricals = electricals;
        this.assignments = assignments;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public List<Shop> getShops() { return shops; }
    public void setShops(List<Shop> shops) { this.shops = shops; }

    public List<MaintenanceTask> getTasks() { return tasks; }
    public void setTasks(List<MaintenanceTask> tasks) { this.tasks = tasks; }

    public List<ElectricalAsset> getElectricals() { return electricals; }
    public void setElectricals(List<ElectricalAsset> electricals) { this.electricals = electricals; }

    public List<StaffAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<StaffAssignment> assignments) { this.assignments = assignments; }
}
