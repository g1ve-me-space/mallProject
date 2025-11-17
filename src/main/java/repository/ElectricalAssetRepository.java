package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.ElectricalAsset;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ElectricalAssetRepository extends InFileRepository<ElectricalAsset> {
    public ElectricalAssetRepository() {
        super("electricalAsset.json", new TypeReference<List<ElectricalAsset>>() {});
    }
}