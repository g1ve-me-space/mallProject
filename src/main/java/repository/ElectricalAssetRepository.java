package repository;

import model.ElectricalAsset;
import org.springframework.stereotype.Repository;

@Repository
// Inherits all standard CRUD methods from InMemoryRepository.
public class ElectricalAssetRepository extends InMemoryRepository<ElectricalAsset, String> {
    // This body is intentionally left empty.
}