package repository;

import model.SecurityStaff;
import java.util.*;
import java.util.stream.Collectors;

public class SecurityStaffRepository extends AbstractRepository<SecurityStaff> {

    // Find security staff by badge number
    public Optional<SecurityStaff> findByBadgeNo(String badgeNo) {
        return store.values().stream()
                .filter(staff -> staff.getBadgeNo() != null && staff.getBadgeNo().equals(badgeNo))
                .findFirst();
    }

    // Find security staff with badge numbers containing pattern
    public List<SecurityStaff> findByBadgeNoContaining(String pattern) {
        return store.values().stream()
                .filter(staff -> staff.getBadgeNo() != null && staff.getBadgeNo().contains(pattern))
                .collect(Collectors.toList());
    }

    // Update badge number
    public boolean updateBadgeNo(String staffId, String newBadgeNo) {
        Optional<SecurityStaff> staffOpt = findById(staffId);
        if (staffOpt.isPresent()) {
            SecurityStaff staff = staffOpt.get();
            staff.setBadgeNo(newBadgeNo);
            save(staff.getId(), staff);
            return true;
        }
        return false;
    }

    // Validate badge number uniqueness
    public boolean isBadgeNoUnique(String badgeNo) {
        return store.values().stream()
                .noneMatch(staff -> staff.getBadgeNo() != null && staff.getBadgeNo().equals(badgeNo));
    }

    // Get all badge numbers
    public List<String> getAllBadgeNumbers() {
        return store.values().stream()
                .filter(staff -> staff.getBadgeNo() != null)
                .map(SecurityStaff::getBadgeNo)
                .sorted()
                .collect(Collectors.toList());
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, SecurityStaff entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    // Alternative save method that uses the entity's own ID
    public void save(SecurityStaff entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("SecurityStaff ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}