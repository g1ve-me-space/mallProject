package repository;

import model.Mall;
import model.Floor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MallRepository extends InMemoryRepository<Mall, String> {

    // --- All custom methods are kept, but internal 'save' calls are fixed ---

    public Optional<Mall> findByName(String name) {
        return store.values().stream()
                .filter(mall -> mall.getName() != null && mall.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<Mall> findByCity(String city) {
        return store.values().stream()
                .filter(mall -> mall.getCity() != null && mall.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public boolean addFloor(String mallId, Floor floor) {
        Optional<Mall> mallOpt = findById(mallId);
        if (mallOpt.isPresent()) {
            Mall mall = mallOpt.get();
            if (mall.getFloors() == null) {
                mall.setFloors(new ArrayList<>());
            }
            mall.getFloors().add(floor);
            save(mall); // FIX: Call the new save(entity) method
            return true;
        }
        return false;
    }

    public boolean removeFloor(String mallId, String floorId) {
        Optional<Mall> mallOpt = findById(mallId);
        if (mallOpt.isPresent()) {
            Mall mall = mallOpt.get();
            if (mall.getFloors() != null) {
                boolean removed = mall.getFloors().removeIf(f -> f.getId().equals(floorId));
                if (removed) {
                    save(mall); // FIX: Call the new save(entity) method
                    return true;
                }
            }
        }
        return false;
    }

    // ... (keep all other custom 'find' and 'get' methods from your original file)

    // For example:
    public Optional<Mall> findByFloorId(String floorId) {
        return store.values().stream()
                .filter(mall -> mall.getFloors() != null &&
                        mall.getFloors().stream().anyMatch(floor -> floor.getId().equals(floorId)))
                .findFirst();
    }

    // IMPORTANT: The old custom 'save' methods are REMOVED because they are
    // no longer needed and conflict with the new pattern. The inherited save() is enough.
}