package repository;

import model.Staff;
import java.util.*;
import java.util.stream.Collectors;

public abstract class StaffRepository extends AbstractRepository<Staff> {

    // Find staff by name (partial match)
    public List<Staff> findByNameContaining(String name) {
        return store.values().stream()
                .filter(staff -> staff.getName() != null &&
                        staff.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Find staff by exact name
    public List<Staff> findByName(String name) {
        return store.values().stream()
                .filter(staff -> staff.getName() != null && staff.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // Get all staff names
    public List<String> getAllStaffNames() {
        return store.values().stream()
                .filter(staff -> staff.getName() != null)
                .map(Staff::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Update staff name
    public boolean updateName(String staffId, String newName) {
        Optional<Staff> staffOpt = findById(staffId);
        if (staffOpt.isPresent()) {
            Staff staff = staffOpt.get();
            staff.setName(newName);
            save(staff.getId(), staff);
            return true;
        }
        return false;
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, Staff entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    // Alternative save method that uses the entity's own ID
    public void save(Staff entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Staff ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}