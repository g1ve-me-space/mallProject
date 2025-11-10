package service;

import model.AssetStatus;
import model.AssetType;
import model.ElectricalAsset;
import repository.ElectricalAssetRepository;
import java.util.List;

public class ElectricalAssetService extends AbstractService<ElectricalAsset> {
    private final ElectricalAssetRepository electricalAssetRepository;

    public ElectricalAssetService(ElectricalAssetRepository repository) {
        super(repository);
        this.electricalAssetRepository = repository;
    }

    public List<ElectricalAsset> findByFloorId(String floorId) {
        return electricalAssetRepository.findByFloorId(floorId);
    }

    public List<ElectricalAsset> findByType(AssetType type) { // Use enum
        return electricalAssetRepository.findByType(type);
    }

    public List<ElectricalAsset> findByStatus(AssetStatus status) { // Use enum
        return electricalAssetRepository.findByStatus(status);
    }

    public void save(ElectricalAsset entity) {
        electricalAssetRepository.save(entity);
    }
}