package model;

import enums.AssetStatus;
import enums.AssetType;
import interfaces.Identifiable;

public class ElectricalAsset implements Identifiable<String> {

    private String id;
    private String floorId;
    private AssetType type;
    private AssetStatus status;

    // --- CONSTRUCTORS ---
    public ElectricalAsset() {
    }

    public ElectricalAsset(String id, String floorId, AssetType type, AssetStatus status) {
        this.id = id;
        this.floorId = floorId;
        this.type = type;
        this.status = status;
    }

    // --- GETTERS AND SETTERS ---

    @Override // This annotation confirms we are fulfilling the Identifiable interface contract
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }
}