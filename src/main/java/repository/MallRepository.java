package repository;

import model.Mall;
import model.Floor;
import java.util.*;
import java.util.stream.Collectors;

public abstract class MallRepository extends AbstractRepository<Mall> {

    // Find mall by name
    public Optional<Mall> findByName(String name) {
        return store.values().stream()
                .filter(mall -> mall.getName() != null && mall.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Find malls by city
    public List<Mall> findByCity(String city) {
        return store.values().stream()
                .filter(mall -> mall.getCity() != null && mall.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    // Find malls with floors
    public List<Mall> findMallsWithFloors() {
        return store.values().stream()
                .filter(mall -> mall.getFloors() != null && !mall.getFloors().isEmpty())
                .collect(Collectors.toList());
    }

    // Find malls by minimum number of floors
    public List<Mall> findMallsWithMinFloors(int minFloors) {
        return store.values().stream()
                .filter(mall -> mall.getFloors() != null && mall.getFloors().size() >= minFloors)
                .collect(Collectors.toList());
    }

    // Add floor to mall
    public boolean addFloor(String mallId, Floor floor) {
        Optional<Mall> mallOpt = findById(mallId);
        if (mallOpt.isPresent()) {
            Mall mall = mallOpt.get();
            if (mall.getFloors() == null) {
                mall.setFloors(new ArrayList<>());
            }
            mall.getFloors().add(floor);
            save(mall.getId(), mall);
            return true;
        }
        return false;
    }

    // Remove floor from mall
    public boolean removeFloor(String mallId, String floorId) {
        Optional<Mall> mallOpt = findById(mallId);
        if (mallOpt.isPresent()) {
            Mall mall = mallOpt.get();
            if (mall.getFloors() != null) {
                boolean removed = mall.getFloors().removeIf(floor -> floor.getId().equals(floorId));
                if (removed) {
                    save(mall.getId(), mall);
                    return true;
                }
            }
        }
        return false;
    }

    // Get floor count per mall
    public Map<String, Integer> getFloorCounts() {
        return store.values().stream()
                .collect(Collectors.toMap(
                        Mall::getId,
                        mall -> mall.getFloors() != null ? mall.getFloors().size() : 0
                ));
    }

    // Find mall by floor ID
    public Optional<Mall> findByFloorId(String floorId) {
        return store.values().stream()
                .filter(mall -> mall.getFloors() != null &&
                        mall.getFloors().stream().anyMatch(floor -> floor.getId().equals(floorId)))
                .findFirst();
    }

    // Get total shops count across all malls
    public int getTotalShopsCount() {
        return store.values().stream()
                .filter(mall -> mall.getFloors() != null)
                .flatMap(mall -> mall.getFloors().stream())
                .filter(floor -> floor.getShops() != null)
                .mapToInt(floor -> floor.getShops().size())
                .sum();
    }

    // Get shops count per mall
    public Map<String, Integer> getShopsCountPerMall() {
        return store.values().stream()
                .collect(Collectors.toMap(
                        Mall::getId,
                        mall -> mall.getFloors() != null ?
                                mall.getFloors().stream()
                                        .filter(floor -> floor.getShops() != null)
                                        .mapToInt(floor -> floor.getShops().size())
                                        .sum() : 0
                ));
    }

    // Find mall with most floors
    public Optional<Mall> findMallWithMostFloors() {
        return store.values().stream()
                .max(Comparator.comparingInt(mall ->
                        mall.getFloors() != null ? mall.getFloors().size() : 0));
    }

    // Find mall with most shops
    public Optional<Mall> findMallWithMostShops() {
        return store.values().stream()
                .max(Comparator.comparingInt(mall ->
                        mall.getFloors() != null ?
                                mall.getFloors().stream()
                                        .filter(floor -> floor.getShops() != null)
                                        .mapToInt(floor -> floor.getShops().size())
                                        .sum() : 0));
    }

    // Get all cities with malls
    public Set<String> getAllCities() {
        return store.values().stream()
                .filter(mall -> mall.getCity() != null)
                .map(Mall::getCity)
                .collect(Collectors.toSet());
    }

    // Get mall count by city
    public Map<String, Long> getMallCountByCity() {
        return store.values().stream()
                .filter(mall -> mall.getCity() != null)
                .collect(Collectors.groupingBy(
                        Mall::getCity,
                        Collectors.counting()
                ));
    }

    // Update mall city
    public boolean updateCity(String mallId, String newCity) {
        Optional<Mall> mallOpt = findById(mallId);
        if (mallOpt.isPresent()) {
            Mall mall = mallOpt.get();
            mall.setCity(newCity);
            save(mall.getId(), mall);
            return true;
        }
        return false;
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, Mall entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    // Alternative save method that uses the entity's own ID
    public void save(Mall entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Mall ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}