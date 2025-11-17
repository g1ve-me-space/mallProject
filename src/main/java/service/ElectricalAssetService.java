package service;

import enums.AssetStatus;
import enums.AssetType;
import model.ElectricalAsset;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import repository.ElectricalAssetRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectricalAssetService {

    private final ElectricalAssetRepository electricalAssetRepository;

    @Autowired
    public ElectricalAssetService(ElectricalAssetRepository repository) {
        this.electricalAssetRepository = repository;
    }

    // --- Standard CRUD methods ---
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
        // Let repository handle ID assignment.
        electricalAssetRepository.save(entity);
    }

    // --- Custom filter methods ---
    public List<ElectricalAsset> findByFloorId(String floorId) {
        return electricalAssetRepository.findAll().stream()
                .filter(asset -> floorId.equals(asset.getFloorId()))
                .collect(Collectors.toList());
    }

    public List<ElectricalAsset> findByType(AssetType type) {
        return electricalAssetRepository.findAll().stream()
                .filter(asset -> type.equals(asset.getType()))
                .collect(Collectors.toList());
    }

    public List<ElectricalAsset> findByStatus(AssetStatus status) {
        return electricalAssetRepository.findAll().stream()
                .filter(asset -> status.equals(asset.getStatus()))
                .collect(Collectors.toList());
    }
}