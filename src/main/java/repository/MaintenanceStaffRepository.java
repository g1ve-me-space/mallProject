package repository;

import model.MaintenanceStaff;
import model.MaintenanceStaffType; // Import the new enum

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MaintenanceStaffRepository extends AbstractRepository<MaintenanceStaff> {

    // Find maintenance staff by their specialty type (FIXED)
    public List<MaintenanceStaff> findByType(MaintenanceStaffType type) {
        return store.values().stream()
                .filter(staff -> staff.getType() == type) // Direct enum comparison
                .collect(Collectors.toList());
    }

    // Count staff by their specialty type (FIXED)
    public Map<MaintenanceStaffType, Long> countByType() {
        return store.values().stream()
                .filter(staff -> staff.getType() != null)
                .collect(Collectors.groupingBy(MaintenanceStaff::getType, Collectors.counting()));
    }

    // Alternative save method that uses the entity's own ID
    public void save(MaintenanceStaff entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("MaintenanceStaff ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }

    @Override
    public void delete(String id) {
        // Implemented in AppConfig
    }
}