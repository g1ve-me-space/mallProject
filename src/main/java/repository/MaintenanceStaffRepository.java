package repository;

import model.MaintenanceStaff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MaintenanceStaffRepository {

    protected final Map<String, MaintenanceStaff> store = new ConcurrentHashMap<>();

    public List<MaintenanceStaff> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<MaintenanceStaff> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public void save(MaintenanceStaff item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            // In a real app, you'd probably want a more robust way to generate IDs
            throw new IllegalArgumentException("Entity ID cannot be null or empty.");
        }
        store.put(item.getId(), item);
    }

    // The delete method will be handled in the AppConfig bean definition,
    // just like all the other repositories.
    public void delete(String id) {
        throw new UnsupportedOperationException("Delete must be implemented in the bean definition.");
    }
}