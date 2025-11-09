package repository;

import model.SecurityStaff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SecurityStaffRepository {

    protected final Map<String, SecurityStaff> store = new ConcurrentHashMap<>();

    // --- Basic Methods ---

    public List<SecurityStaff> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<SecurityStaff> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public void save(SecurityStaff item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            throw new IllegalArgumentException("Entity ID cannot be null or empty.");
        }
        store.put(item.getId(), item);
    }

    public void delete(String id) {
        // This will be implemented in the AppConfig @Bean definition
        throw new UnsupportedOperationException();
    }

    // --- Your Custom Methods (Preserved) ---

    public Optional<SecurityStaff> findByBadgeNo(String badgeNo) {
        return store.values().stream()
                .filter(staff -> staff.getBadgeNo() != null && staff.getBadgeNo().equals(badgeNo))
                .findFirst();
    }

    public List<SecurityStaff> findByBadgeNoContaining(String pattern) {
        return store.values().stream()
                .filter(staff -> staff.getBadgeNo() != null && staff.getBadgeNo().contains(pattern))
                .collect(Collectors.toList());
    }

    public boolean updateBadgeNo(String staffId, String newBadgeNo) {
        Optional<SecurityStaff> staffOpt = findById(staffId);
        if (staffOpt.isPresent()) {
            SecurityStaff staff = staffOpt.get();
            staff.setBadgeNo(newBadgeNo);
            save(staff);
            return true;
        }
        return false;
    }

    public boolean isBadgeNoUnique(String badgeNo) {
        return store.values().stream()
                .noneMatch(staff -> staff.getBadgeNo() != null && staff.getBadgeNo().equals(badgeNo));
    }

    public List<String> getAllBadgeNumbers() {
        return store.values().stream()
                .filter(staff -> staff.getBadgeNo() != null)
                .map(SecurityStaff::getBadgeNo)
                .sorted()
                .collect(Collectors.toList());
    }
}