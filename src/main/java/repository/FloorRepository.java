package repository;

import model.Floor;
import model.Shop;
import model.MaintenanceTask;
import model.ElectricalAsset;
import model.StaffAssignment;
import java.util.*;
import java.util.stream.Collectors;

public class FloorRepository extends AbstractRepository<Floor> {

    // Find floor by number
    public Optional<Floor> findByNumber(int number) {
        return store.values().stream()
                .filter(floor -> floor.getNumber() == number)
                .findFirst();
    }

    // Find all floors with shops
    public List<Floor> findFloorsWithShops() {
        return store.values().stream()
                .filter(floor -> floor.getShops() != null && !floor.getShops().isEmpty())
                .collect(Collectors.toList());
    }

    // Find all floors with pending maintenance tasks
    public List<Floor> findFloorsWithPendingTasks() {
        return store.values().stream()
                .filter(floor -> floor.getTasks() != null &&
                        floor.getTasks().stream().anyMatch(task ->
                                "Planned".equals(task.getStatus()) || "Active".equals(task.getStatus())))
                .collect(Collectors.toList());
    }

    // Find floors by minimum number of shops
    public List<Floor> findFloorsWithMinShops(int minShops) {
        return store.values().stream()
                .filter(floor -> floor.getShops() != null && floor.getShops().size() >= minShops)
                .collect(Collectors.toList());
    }

    // Find floors with electrical assets of specific type
    public List<Floor> findFloorsWithElectricalType(String electricalType) {
        return store.values().stream()
                .filter(floor -> floor.getElectricals() != null &&
                        floor.getElectricals().stream().anyMatch(electrical ->
                                electrical.getType() != null && electrical.getType().equalsIgnoreCase(electricalType)))
                .collect(Collectors.toList());
    }

    // Find floors with down electrical assets
    public List<Floor> findFloorsWithDownElectricals() {
        return store.values().stream()
                .filter(floor -> floor.getElectricals() != null &&
                        floor.getElectricals().stream().anyMatch(electrical ->
                                "Down".equals(electrical.getStatus())))
                .collect(Collectors.toList());
    }

    // Get all floor numbers
    public List<Integer> getAllFloorNumbers() {
        return store.values().stream()
                .map(Floor::getNumber)
                .sorted()
                .collect(Collectors.toList());
    }

    // Get floor numbers with shop count
    public Map<Integer, Integer> getFloorShopCounts() {
        return store.values().stream()
                .collect(Collectors.toMap(
                        Floor::getNumber,
                        floor -> floor.getShops() != null ? floor.getShops().size() : 0
                ));
    }

    // Find floors with staff assignments
    public List<Floor> findFloorsWithAssignments() {
        return store.values().stream()
                .filter(floor -> floor.getAssignments() != null && !floor.getAssignments().isEmpty())
                .collect(Collectors.toList());
    }

    // Add shop to floor
    public boolean addShopToFloor(String floorId, Shop shop) {
        Optional<Floor> floorOpt = findById(floorId);
        if (floorOpt.isPresent()) {
            Floor floor = floorOpt.get();
            if (floor.getShops() == null) {
                floor.setShops(new ArrayList<>());
            }
            floor.getShops().add(shop);
            save(floor.getId(), floor);
            return true;
        }
        return false;
    }

    // Add maintenance task to floor
    public boolean addTaskToFloor(String floorId, MaintenanceTask task) {
        Optional<Floor> floorOpt = findById(floorId);
        if (floorOpt.isPresent()) {
            Floor floor = floorOpt.get();
            if (floor.getTasks() == null) {
                floor.setTasks(new ArrayList<>());
            }
            floor.getTasks().add(task);
            save(floor.getId(), floor);
            return true;
        }
        return false;
    }

    // Add electrical asset to floor
    public boolean addElectricalToFloor(String floorId, ElectricalAsset electrical) {
        Optional<Floor> floorOpt = findById(floorId);
        if (floorOpt.isPresent()) {
            Floor floor = floorOpt.get();
            if (floor.getElectricals() == null) {
                floor.setElectricals(new ArrayList<>());
            }
            floor.getElectricals().add(electrical);
            save(floor.getId(), floor);
            return true;
        }
        return false;
    }

    // Remove shop from floor
    public boolean removeShopFromFloor(String floorId, String shopId) {
        Optional<Floor> floorOpt = findById(floorId);
        if (floorOpt.isPresent()) {
            Floor floor = floorOpt.get();
            if (floor.getShops() != null) {
                boolean removed = floor.getShops().removeIf(shop -> shop.getId().equals(shopId));
                if (removed) {
                    save(floor.getId(), floor);
                    return true;
                }
            }
        }
        return false;
    }

    // Remove maintenance task from floor
    public boolean removeTaskFromFloor(String floorId, String taskId) {
        Optional<Floor> floorOpt = findById(floorId);
        if (floorOpt.isPresent()) {
            Floor floor = floorOpt.get();
            if (floor.getTasks() != null) {
                boolean removed = floor.getTasks().removeIf(task -> task.getId().equals(taskId));
                if (removed) {
                    save(floor.getId(), floor);
                    return true;
                }
            }
        }
        return false;
    }

    // Remove electrical asset from floor
    public boolean removeElectricalFromFloor(String floorId, String electricalId) {
        Optional<Floor> floorOpt = findById(floorId);
        if (floorOpt.isPresent()) {
            Floor floor = floorOpt.get();
            if (floor.getElectricals() != null) {
                boolean removed = floor.getElectricals().removeIf(electrical -> electrical.getId().equals(electricalId));
                if (removed) {
                    save(floor.getId(), floor);
                    return true;
                }
            }
        }
        return false;
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, Floor entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    @Override
    public void delete(String id) {

    }

    // Alternative save method that uses the entity's own ID
    public void save(Floor entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Floor ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}