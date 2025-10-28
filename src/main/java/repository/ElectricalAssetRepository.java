package repository;

import model.ElectricalAsset;
import java.util.*;
import java.util.stream.Collectors;  // Add this import

public class ElectricalAssetRepository extends AbstractRepository<ElectricalAsset> {

    // Find all electrical assets by floor ID
    public List<ElectricalAsset> findByFloorId(String floorId) {
        return store.values().stream()
                .filter(asset -> asset.getFloorId() != null && asset.getFloorId().equals(floorId))
                .collect(Collectors.toList());
    }

    // Find all electrical assets by type
    public List<ElectricalAsset> findByType(String type) {
        return store.values().stream()
                .filter(asset -> asset.getType() != null && asset.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    // Find all electrical assets by status
    public List<ElectricalAsset> findByStatus(String status) {
        return store.values().stream()
                .filter(asset -> asset.getStatus() != null && asset.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    // Find all working electrical assets
    public List<ElectricalAsset> findWorkingAssets() {
        return findByStatus("Working");
    }

    // Find all down/not-working electrical assets
    public List<ElectricalAsset> findDownAssets() {
        return findByStatus("Down");
    }

    // Find electrical assets by type and status
    public List<ElectricalAsset> findByTypeAndStatus(String type, String status) {
        return store.values().stream()
                .filter(asset -> asset.getType() != null && asset.getType().equalsIgnoreCase(type) &&
                        asset.getStatus() != null && asset.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    // Count electrical assets by floor
    public Map<String, Long> countAssetsByFloor() {
        return store.values().stream()
                .filter(asset -> asset.getFloorId() != null)
                .collect(Collectors.groupingBy(ElectricalAsset::getFloorId, Collectors.counting()));
    }

    // Count electrical assets by type
    public Map<String, Long> countAssetsByType() {
        return store.values().stream()
                .filter(asset -> asset.getType() != null)
                .collect(Collectors.groupingBy(ElectricalAsset::getType, Collectors.counting()));
    }

    // Update status of an electrical asset
    public boolean updateStatus(String assetId, String newStatus) {
        Optional<ElectricalAsset> assetOpt = findById(assetId);
        if (assetOpt.isPresent()) {
            ElectricalAsset asset = assetOpt.get();
            asset.setStatus(newStatus);
            save(asset.getId(), asset);
            return true;
        }
        return false;
    }

    // Mark an asset as working
    public boolean markAsWorking(String assetId) {
        return updateStatus(assetId, "Working");
    }

    // Mark an asset as down
    public boolean markAsDown(String assetId) {
        return updateStatus(assetId, "Down");
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, ElectricalAsset entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    // Alternative save method that uses the entity's own ID
    public void save(ElectricalAsset entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("ElectricalAsset ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}