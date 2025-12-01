package service;

import enums.AssetStatus;
import enums.AssetType;
import model.ElectricalAsset;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import repository.ElectricalAssetRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ElectricalAssetService {

    private final ElectricalAssetRepository electricalAssetRepository;

    @Autowired
    public ElectricalAssetService(ElectricalAssetRepository repository) {
        this.electricalAssetRepository = repository;
    }

    public List<ElectricalAsset> findAll() {
        return electricalAssetRepository.findAll();
    }

    public Optional<ElectricalAsset> findById(String id) {
        return electricalAssetRepository.findById(id);
    }

    public void deleteById(String id) {
        electricalAssetRepository.deleteById(id);
    }

    public void save(ElectricalAsset entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            entity.setId(UUID.randomUUID().toString());
        }
        electricalAssetRepository.save(entity);
    }

    // --- Custom filter methods ---

    public List<ElectricalAsset> findByFloorId(String floorId) {
        // ⚠️ FIX: Apelăm noua metodă cu underscore din Repository
        return electricalAssetRepository.findByFloor_Id(floorId);
    }

    public List<ElectricalAsset> findByType(AssetType type) {
        return electricalAssetRepository.findByType(type);
    }

    public List<ElectricalAsset> findByStatus(AssetStatus status) {
        return electricalAssetRepository.findByStatus(status);
    }
}