package repository;

import model.AssetStatus;
import model.AssetType;
import model.ElectricalAsset;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ElectricalAssetRepository extends AbstractRepository<ElectricalAsset> {

    public List<ElectricalAsset> findByFloorId(String floorId) {
        return store.values().stream()
                .filter(asset -> asset.getFloorId() != null && asset.getFloorId().equals(floorId))
                .collect(Collectors.toList());
    }

    public List<ElectricalAsset> findByType(AssetType type) { // Use enum
        return store.values().stream()
                .filter(asset -> asset.getType() == type) // Direct enum comparison
                .collect(Collectors.toList());
    }

    public List<ElectricalAsset> findByStatus(AssetStatus status) { // Use enum
        return store.values().stream()
                .filter(asset -> asset.getStatus() == status) // Direct enum comparison
                .collect(Collectors.toList());
    }

    public List<ElectricalAsset> findWorkingAssets() {
        return findByStatus(AssetStatus.WORKING);
    }

    public List<ElectricalAsset> findDownAssets() {
        return findByStatus(AssetStatus.DOWN);
    }

    public boolean updateStatus(String assetId, AssetStatus newStatus) { // Use enum
        Optional<ElectricalAsset> assetOpt = findById(assetId);
        if (assetOpt.isPresent()) {
            ElectricalAsset asset = assetOpt.get();
            asset.setStatus(newStatus);
            save(asset);
            return true;
        }
        return false;
    }

    public void save(ElectricalAsset entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("ElectricalAsset ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }

    @Override
    public void delete(String id) {
        // Implemented in AppConfig
    }
}