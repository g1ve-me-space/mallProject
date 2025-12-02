package repository;

import enums.AssetStatus; // ⚠️ Import necesar
import enums.AssetType;   // ⚠️ Import necesar
import model.ElectricalAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ElectricalAssetRepository extends JpaRepository<ElectricalAsset, String> {

    // Metoda pentru filtrare după Etaj
    List<ElectricalAsset> findByFloor_Id(String floorId);

    // ⚠️ ACESTE METODE LIPSEAU și cauzau erorile în ElectricalAssetService
    List<ElectricalAsset> findByType(AssetType type);

    List<ElectricalAsset> findByStatus(AssetStatus status);
}