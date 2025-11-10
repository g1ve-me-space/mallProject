package service;

import enums.AssetStatus;
import enums.AssetType;
import model.ElectricalAsset;
import org.springframework.stereotype.Service; // Import the Service annotation
import org.springframework.beans.factory.annotation.Autowired; // Import Autowired
import repository.ElectricalAssetRepository;

import java.util.List;
import java.util.Optional; // Import Optional
import java.util.UUID; // Import UUID
import java.util.stream.Collectors;

/**
 * FIX: This service no longer extends the old AbstractService.
 * It is now a standalone service consistent with our new architecture.
 */
@Service // Add the @Service annotation so Spring can manage it
public class ElectricalAssetService {

    private final ElectricalAssetRepository electricalAssetRepository;

    @Autowired // Add Autowired for dependency injection
    public ElectricalAssetService(ElectricalAssetRepository repository) {
        // The call to super() is removed
        this.electricalAssetRepository = repository;
    }

    // --- Standard methods (previously inherited from AbstractService) ---
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
        // You might want to add ID generation logic here if it's not always set
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            entity.setId(UUID.randomUUID().toString());
        }
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