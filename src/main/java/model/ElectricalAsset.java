package model;

public class ElectricalAsset {
    private String id;
    private String floorId;
    private String type;   // Lift / AC / Light / Escalator
    private String status; // Working / Down

    public ElectricalAsset() {}

    public ElectricalAsset(String id, String floorId, String type, String status) {
        this.id = id;
        this.floorId = floorId;
        this.type = type;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFloorId() { return floorId; }
    public void setFloorId(String floorId) { this.floorId = floorId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
