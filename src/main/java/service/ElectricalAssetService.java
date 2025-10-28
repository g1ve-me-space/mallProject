package service;

import model.ElectricalAsset;
import repository.ElectricalAssetRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ElectricalAssetService extends AbstractService<ElectricalAsset> {
    private ElectricalAssetRepository electricalAssetRepository;

    public ElectricalAssetService(ElectricalAssetRepository repository) {
        super(repository);
        this.electricalAssetRepository = repository;
    }

    public List<ElectricalAsset> findByFloorId(String floorId) {
        return electricalAssetRepository.findByFloorId(floorId);
    }

    public List<ElectricalAsset> findByType(String type) {
        return electricalAssetRepository.findByType(type);
    }

    public List<ElectricalAsset> findByStatus(String status) {
        return electricalAssetRepository.findByStatus(status);
    }

    public List<ElectricalAsset> findWorkingAssets() {
        return electricalAssetRepository.findWorkingAssets();
    }

    public List<ElectricalAsset> findDownAssets() {
        return electricalAssetRepository.findDownAssets();
    }

    public List<ElectricalAsset> findByTypeAndStatus(String type, String status) {
        return electricalAssetRepository.findByTypeAndStatus(type, status);
    }

    public Map<String, Long> countAssetsByFloor() {
        return electricalAssetRepository.countAssetsByFloor();
    }

    public Map<String, Long> countAssetsByType() {
        return electricalAssetRepository.countAssetsByType();
    }

    public boolean updateStatus(String assetId, String newStatus) {
        return electricalAssetRepository.updateStatus(assetId, newStatus);
    }

    public boolean markAsWorking(String assetId) {
        return electricalAssetRepository.markAsWorking(assetId);
    }

    public boolean markAsDown(String assetId) {
        return electricalAssetRepository.markAsDown(assetId);
    }

    public void save(ElectricalAsset entity) {
        electricalAssetRepository.save(entity);
    }
}