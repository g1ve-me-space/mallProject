package repository;

import enums.AssetStatus;
import enums.AssetType;
import model.ElectricalAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricalAssetRepository extends JpaRepository<ElectricalAsset, String> {

    // ⚠️ FIX: Adăugăm underscore "_" ca Spring să știe să caute ID-ul din obiectul Floor
    List<ElectricalAsset> findByFloor_Id(String floorId);

    List<ElectricalAsset> findByType(AssetType type);

    List<ElectricalAsset> findByStatus(AssetStatus status);
}