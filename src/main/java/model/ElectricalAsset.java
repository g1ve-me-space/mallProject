package model;

import enums.AssetStatus;
import enums.AssetType;
import interfaces.Identifiable;
import jakarta.persistence.*; // Importurile pentru baza de date
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "electrical_assets")
public class ElectricalAsset implements Identifiable<String> {

    @Id
    private String id;

    // ⚠️ MODIFICARE IMPORTANTĂ:
    // În loc de "String floorId", avem o relație către entitatea Floor.
    // Un etaj poate avea multe echipamente electrice.
    @ManyToOne
    @JoinColumn(name = "floor_id") // Așa se va numi coloana în MySQL
    private Floor floor;

    @NotNull(message = "Trebuie să alegi un tip!")
    @Enumerated(EnumType.STRING) // Salvăm enum-ul ca text ("LIFT", "AC")
    private AssetType type;

    @NotNull(message = "Trebuie să alegi un status!")
    @Enumerated(EnumType.STRING) // Salvăm statusul ca text ("WORKING", "DOWN")
    private AssetStatus status;

    // --- CONSTRUCTORS ---
    public ElectricalAsset() {
    }

    public ElectricalAsset(String id, Floor floor, AssetType type, AssetStatus status) {
        this.id = id;
        this.floor = floor;
        this.type = type;
        this.status = status;
    }

    // --- GETTERS AND SETTERS ---

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter modificat: Returnăm obiectul Floor întreg
    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    // Metodă ajutătoare (opțional): Dacă ai nevoie doar de ID-ul etajului în HTML
    public String getFloorId() {
        return floor != null ? floor.getId() : null;
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